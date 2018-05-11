package cd.backend.interpreter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cd.backend.ExitCode;
import cd.backend.interpreter.Interpreter.MethodInterp.EarlyReturnException;
import cd.ir.Ast;
import cd.ir.Ast.Assign;
import cd.ir.Ast.BinaryOp;
import cd.ir.Ast.BooleanConst;
import cd.ir.Ast.BuiltInRead;
import cd.ir.Ast.BuiltInWrite;
import cd.ir.Ast.BuiltInWriteln;
import cd.ir.Ast.Cast;
import cd.ir.Ast.ClassDecl;
import cd.ir.Ast.Expr;
import cd.ir.Ast.Field;
import cd.ir.Ast.IfElse;
import cd.ir.Ast.Index;
import cd.ir.Ast.IntConst;
import cd.ir.Ast.MethodCall;
import cd.ir.Ast.MethodCallExpr;
import cd.ir.Ast.MethodDecl;
import cd.ir.Ast.NewArray;
import cd.ir.Ast.NewObject;
import cd.ir.Ast.NullConst;
import cd.ir.Ast.ReturnStmt;
import cd.ir.Ast.Stmt;
import cd.ir.Ast.ThisRef;
import cd.ir.Ast.UnaryOp;
import cd.ir.Ast.Var;
import cd.ir.Ast.WhileLoop;
import cd.ir.AstVisitor;
import cd.ir.BasicBlock;
import cd.ir.ControlFlowGraph;
import cd.ir.Symbol.ArrayTypeSymbol;
import cd.ir.Symbol.ClassSymbol;
import cd.ir.Symbol.TypeSymbol;
import cd.ir.Symbol.VariableSymbol;

/**
 * An interpreter for the Javali IR. It requires that the IR be fully
 * semantically analyzed -- in particular, that symbols be assigned to Var and
 * Field nodes.
 * 
 * It can interpret either the AST used in CD1 or the CFG from CD2. It detects
 * infinite loops and also tracks how many operations of each kind were
 * performed.
 */
public class Interpreter {

	private static final long MAX_STEPS = 100000000;

	private long steps = 0;
	private final JlNull nullPointer = new JlNull();

	private final List<ClassDecl> classDecls;
	private final Writer output;
	private final Scanner input;
	
	public Interpreter(List<ClassDecl> classDecls, Reader in, Writer out) {
		this.classDecls = classDecls;
		this.input = new Scanner(in);
		this.output = out;
	}

	public void execute() {
		ClassSymbol mainType = findMainClass();
		invokeMethod(mainType, "main", new JlObject(mainType),
				Collections.<JlValue> emptyList());
	}

	// Optimization detection:
	//
	// We count the number of binary and unary operations that
	// occurred during execution and compare this to a fully-optimized version.
	// The key to this hashtable is either a BinaryOp.BOp or UnaryOp.UOp.
	private final Map<Object, Integer> opCounts = new HashMap<Object, Integer>();

	private void increment(Object operator) {
		Integer current = opCounts.get(operator);
		if (current == null)
			opCounts.put(operator, 1);
		else
			opCounts.put(operator, current + 1);
	}

	public String operationSummary() {

		List<String> operationSummaries = new ArrayList<String>();

		for (Object operation : opCounts.keySet()) {
			operationSummaries.add(String.format("%s: %s\n", operation,
					opCounts.get(operation)));
		}

		Collections.sort(operationSummaries);

		StringBuilder sb = new StringBuilder();
		for (String summary : operationSummaries) {
			sb.append(summary);
		}

		return sb.toString();
	}

	public void step() {

		// Stop after taking too many evaluation steps!
		if (++steps > MAX_STEPS) {
			throw new DynamicError("Infinite Loop!",
					ExitCode.INFINITE_LOOP);
		}

	}

	// The interpreter proper:
	class ExprInterp extends AstVisitor<JlValue, StackFrame> {

		private JlValue v(int value) {
			return new JlInt(value);
		}

		private JlValue v(boolean b) {
			return new JlBoolean(b);
		}

		@Override
		public JlValue visit(Ast ast, StackFrame arg) {
			step();
			return super.visit(ast, arg);
		}

		@Override
		public JlValue binaryOp(BinaryOp ast, StackFrame arg) {

			try {

				final JlValue left = visit(ast.left(), arg);
				final JlValue right = visit(ast.right(), arg);

				// TODO Only increment this operator for integers
				increment(ast.operator);
				switch (ast.operator) {
					case B_TIMES :
						return left.times(right);
					case B_DIV :
						return left.div(right);
					case B_MOD :
						return left.mod(right);
					case B_PLUS :
						return left.add(right);
					case B_MINUS :
						return left.subtract(right);
					case B_AND :
						return left.and(right);
					case B_OR :
						return left.or(right);
					case B_EQUAL :
						return v(left.equals(right));
					case B_NOT_EQUAL :
						return v(!left.equals(right));
					case B_LESS_THAN :
						return left.less(right);
					case B_LESS_OR_EQUAL :
						return left.lessOrEqual(right);
					case B_GREATER_THAN :
						return left.greater(right);
					case B_GREATER_OR_EQUAL :
						return left.greaterOrEqual(right);
				}

				throw new DynamicError("Unhandled binary operator",
						ExitCode.INTERNAL_ERROR);

			} catch (ArithmeticException e) {
				throw new DynamicError("Division by zero",
						ExitCode.DIVISION_BY_ZERO);
			}
		}

		@Override
		public JlValue booleanConst(BooleanConst ast, StackFrame arg) {
			return v(ast.value);
		}

		@Override
		public JlValue builtInRead(BuiltInRead ast, StackFrame arg) {
			try {
				return v(input.nextInt());
			} catch (InputMismatchException e)  {
				throw new DynamicError("Your .javali.in file is malformed.", 
						ExitCode.INTERNAL_ERROR);
			} catch (NoSuchElementException e) {
				throw new DynamicError("Your .javali.in does not contain enough numbers"
						+ " or may not exist at all.\nMake sure that test cases that contain "
						+ "read() expressions provide a [testcasename].javali.in file.", 
						ExitCode.INTERNAL_ERROR);
			}
		}

		@Override
		public JlValue cast(Cast ast, StackFrame arg) {

			JlReference ref = visit(ast.arg(), arg).asRef();

			if (ref.canBeCastTo(ast.typeName)) {
				return ref;
			}

			throw new DynamicError("Cast failure: cannot cast " + ref.typeSym
					+ " to " + ast.typeName, ExitCode.INVALID_DOWNCAST);

		}

		@Override
		public JlValue field(Field ast, StackFrame arg) {
			JlValue lhs = visit(ast.arg(), arg);
			return lhs.asRef().field(ast.sym);
		}

		@Override
		public JlValue index(Index ast, StackFrame arg) {
			JlValue lhs = visit(ast.left(), arg);
			JlValue idx = visit(ast.right(), arg);
			return lhs.asRef().deref(idx.asInt());
		}

		@Override
		public JlValue intConst(IntConst ast, StackFrame arg) {
			return v(ast.value);
		}

		@Override
		public JlValue newArray(NewArray ast, StackFrame arg) {
			JlValue size = visit(ast.arg(), arg);
			return new JlArray((ArrayTypeSymbol) ast.type, size.asInt());
		}

		@Override
		public JlValue newObject(NewObject ast, StackFrame arg) {
			return new JlObject((ClassSymbol) ast.type);
		}

		@Override
		public JlValue nullConst(NullConst ast, StackFrame arg) {
			return nullPointer;
		}

		@Override
		public JlValue thisRef(ThisRef ast, StackFrame arg) {
			return arg.getThisPointer();
		}

		@Override
		public JlValue methodCall(MethodCallExpr ast, StackFrame frame) {

			JlObject rcvr = expr(ast.receiver(), frame).asObject();

			List<JlValue> arguments = new ArrayList<JlValue>();
			for (Ast arg : ast.argumentsWithoutReceiver()) {
				arguments.add(expr(arg, frame));
			}

			return invokeMethod((ClassSymbol) rcvr.typeSym, ast.methodName,
					rcvr, arguments);

		}

		@Override
		public JlValue unaryOp(UnaryOp ast, StackFrame arg) {

			JlValue val = visit(ast.arg(), arg);

			// TODO Increment this only when is an int
			increment(ast.operator);

			switch (ast.operator) {
				case U_PLUS :
					return val.plus();
				case U_MINUS :
					return val.minus();
				case U_BOOL_NOT :
					return val.not();
			}

			throw new DynamicError("Unhandled unary operator " + ast.operator,
					ExitCode.INTERNAL_ERROR);
		}

		@Override
		public JlValue var(Var ast, StackFrame arg) {

			if (ast.sym == null) {
				throw new DynamicError("Var node with null symbol",
						ExitCode.INTERNAL_ERROR);
			}

			switch (ast.sym.kind) {

				case LOCAL :
				case PARAM :
					return arg.var(ast.sym);
				case FIELD :
					return arg.getThisPointer().field(ast.sym);
			}

			throw new DynamicError("Unhandled VariableSymbol kind: "
					+ ast.sym.kind, ExitCode.INTERNAL_ERROR);

		}

	}

	public JlValue expr(Ast ast, StackFrame frame) {
		return new ExprInterp().visit(ast, frame);
	}

	private ClassSymbol findMainClass() {
		for (ClassDecl classDecl : classDecls) {
			if (classDecl.name.equals("Main"))
				return classDecl.sym;
		}
		throw new StaticError("No Main class found");
	}

	public ClassDecl findClassDecl(TypeSymbol typeSym) {
		for (ClassDecl cd : classDecls) {
			if (cd.sym == typeSym)
				return cd;
		}

		throw new StaticError("No such type " + typeSym.name);
	}

	class MethodInterp extends cd.ir.AstVisitor<JlValue, StackFrame> {

		public boolean earlyReturn = false;

		@SuppressWarnings("serial")
		class EarlyReturnException extends RuntimeException {
			public JlValue value;
			public EarlyReturnException(final JlValue value) {
				this.value = value;
			}
		}

		@Override
		public JlValue assign(Assign ast, final StackFrame frame) {

			new AstVisitor<Void, Expr>() {

				@Override
				public Void field(Field ast, Expr right) {
					JlValue obj = expr(ast.arg(), frame);
					assert obj != null && obj.asRef() != null;
					final JlValue val = expr(right, frame);
					assert val != null;
					obj.asRef().setField(ast.sym, val);
					return null;
				}

				@Override
				public Void index(Index ast, Expr right) {
					JlValue obj = expr(ast.left(), frame);
					JlValue idx = expr(ast.right(), frame);
					final JlValue val = expr(right, frame);
					assert val != null;
					obj.asRef().setDeref(idx.asInt(), val);
					return null;
				}

				@Override
				public Void var(Var ast, Expr right) {
					final JlValue val = expr(right, frame);
					assert val != null;
					frame.setVar(ast.sym, val);
					return null;
				}

				@Override
				protected Void dflt(Ast ast, Expr arg) {
					throw new StaticError("Malformed l-value in AST");
				}

			}.visit(ast.left(), ast.right());

			return null;
		}

		@Override
		public JlValue builtInWrite(BuiltInWrite ast, StackFrame frame) {

			JlValue val = expr(ast.arg(), frame);

			try {
				output.write(Integer.toString(val.asInt()));
			} catch (IOException e) {
				throw new DynamicError(e, ExitCode.INTERNAL_ERROR);
			}

			return null;

		}

		@Override
		public JlValue builtInWriteln(BuiltInWriteln ast, StackFrame arg) {

			try {
				output.write("\n");
			} catch (IOException e) {
				throw new DynamicError(e, ExitCode.INTERNAL_ERROR);
			}

			return null;
		}

		@Override
		public JlValue ifElse(IfElse ast, StackFrame frame) {

			JlValue cond = expr(ast.condition(), frame);

			if (cond.asBoolean()) {
				return visit(ast.then(), frame);
			} else {
				return visit(ast.otherwise(), frame);
			}

		}

		@Override
		public JlValue methodCall(MethodCall ast, final StackFrame frame) {
			return expr(ast.getMethodCallExpr(), frame);
		}

		@Override
		public JlValue whileLoop(WhileLoop ast, StackFrame frame) {

			while (true) {

				JlValue cond = expr(ast.condition(), frame);

				if (!cond.asBoolean()) {
					return null;
				}

				visit(ast.body(), frame);
			}

		}

		@Override
		public JlValue returnStmt(ReturnStmt ast, StackFrame frame) {

			JlValue ret = null;

			if (ast.arg() != null) {
				ret = expr(ast.arg(), frame);
			} else {
				ret = new JlNull();
			}

			if (!earlyReturn) {
				return ret;
			} else {
				throw new EarlyReturnException(ret);
			}

		}

	}

	public MethodDecl findMethodDecl(ClassSymbol typeSym,
			final String methodName) {
		ClassSymbol currSym = typeSym;
		while (currSym != ClassSymbol.objectType) {
			ClassDecl cd = findClassDecl(currSym);
			final List<MethodDecl> result = new ArrayList<MethodDecl>();

			for (Ast mem : cd.members()) {
				AstVisitor<Void, Void> vis = new AstVisitor<Void, Void>() {
					@Override
					public Void methodDecl(MethodDecl ast, Void arg) {

						if (!ast.name.equals(methodName)) {
							return null;
						}
						result.add(ast);

						return null;
					}
				};

				vis.visit(mem, null);
			}

			if (result.size() == 1) {
				return result.get(0);
			}

			if (result.size() > 1) {
				throw new StaticError(result.size()
						+ " implementations of method " + methodName
						+ " found in type " + currSym.name);
			}

			currSym = currSym.superClass;
		}

		throw new StaticError("No method " + methodName + " in type " + typeSym);
	}

	// Note: does not interpret phis!
	public JlValue interpretCfg(ControlFlowGraph cfg, final StackFrame frame) {

		BasicBlock current = cfg.start;
		MethodInterp minterp = new MethodInterp();
		JlValue res = null;

		while (true) {
			step();

			for(Stmt stmt : current.stmts) {
				res = minterp.visit(stmt, frame);
				if(stmt instanceof ReturnStmt)
					return res;
			}

			if (current == cfg.end) {
				return res;
			} else if (current.condition == null) {
				current = current.successors.get(0);
			} else {

				JlValue cond = expr(current.condition, frame);

				if (cond.asBoolean()) {
					current = current.trueSuccessor();
				} else {
					current = current.falseSuccessor();
				}

			}

		}

	}

	
	public JlValue invokeMethod(final ClassSymbol typeSym,
			final String methodName, final JlObject rcvr,
			final List<JlValue> arguments) {
		MethodDecl mdecl = findMethodDecl(typeSym, methodName);
		StackFrame newFrame = new StackFrame(rcvr);
		int idx = 0;

		for (VariableSymbol sym : mdecl.sym.parameters) {
			newFrame.setVar(sym, arguments.get(idx++));
		}

		if (mdecl.cfg != null) {
			return interpretCfg(mdecl.cfg, newFrame);
		} else {
			final MethodInterp mthInterp = new MethodInterp();
			mthInterp.earlyReturn = true;
			try {
				return mthInterp.visit(mdecl.body(), newFrame);
			} catch (final EarlyReturnException ex) {
				return ex.value;
			}

		}

	}
}
