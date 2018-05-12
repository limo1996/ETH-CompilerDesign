package cd;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import cd.backend.ConstantFolderVisitor;
import cd.backend.codegen.CfgCodeGenerator;
import cd.frontend.parser.JavaliAstVisitor;
import cd.frontend.parser.JavaliLexer;
import cd.frontend.parser.JavaliParser;
import cd.frontend.parser.JavaliParser.UnitContext;
import cd.frontend.parser.ParseFailure;
import cd.frontend.semantic.SemanticAnalyzer;
import cd.ir.Ast.ClassDecl;
import cd.ir.Ast.MethodDecl;
import cd.ir.Symbol;
import cd.ir.Symbol.TypeSymbol;
import cd.transform.CfgBuilder;
import cd.util.debug.AstDump;
import cd.util.debug.CfgDump;

/**
 * The main entrypoint for the compiler. Consists of a series of routines which must be invoked in
 * order. The main() routine here invokes these routines, as does the unit testing code. This is not
 * the <b>best</b> programming practice, as the series of calls to be invoked is duplicated in two
 * places in the code, but it will do for now.
 */
public class Main {

	// Set to non-null to write debug info out
	public Writer debug = null;

	// Set to non-null to write dump of control flow graph
	public File cfgdumpbase;

	/** Symbol for the Main type */
	public Symbol.ClassSymbol mainType;

	/** List of all type symbols, used by code generator. */
	public List<TypeSymbol> allTypeSymbols;

	public void debug(String format, Object... args) {
		if (debug != null) {
			String result = String.format(format, args);
			try {
				debug.write(result);
				debug.write('\n');
				debug.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/** Parse command line, invoke compile() routine */
	public static void main(String args[]) throws IOException {
		Main m = new Main();

		for (String arg : args) {
			if (arg.equals("-d"))
				m.debug = new OutputStreamWriter(System.err);
			else {
				if (m.debug != null)
					m.cfgdumpbase = new File(arg);

				FileReader fin = new FileReader(arg);

				// Parse:
				List<ClassDecl> astRoots = m.parse(fin);
				
				// Run the semantic check:
				m.semanticCheck(astRoots);

				// Generate code:
				String sFile = arg + Config.ASMEXT;
				try (FileWriter fout = new FileWriter(sFile)) {
					m.generateCode(astRoots, fout);
				}
			}
		}
	}

	/**
	 * Parses an input stream into an AST
	 * 
	 * @throws IOException
	 */
	public List<ClassDecl> parse(Reader reader) throws IOException {
		List<ClassDecl> result = new ArrayList<ClassDecl>();

		try {
			JavaliLexer lexer = new JavaliLexer(new ANTLRInputStream(reader));
			JavaliParser parser = new JavaliParser(new CommonTokenStream(lexer));
			parser.setErrorHandler(new BailErrorStrategy());
			UnitContext unit = parser.unit();

			JavaliAstVisitor visitor = new JavaliAstVisitor();
			visitor.visit(unit);
			result = visitor.classDecls;
		} catch (ParseCancellationException e) {
			ParseFailure pf = new ParseFailure(0, "?");
			pf.initCause(e);
			throw pf;
		}

		debug("AST Resulting From Parsing Stage:");
		dumpAst(result);

		return result;
	}

	public void semanticCheck(List<ClassDecl> astRoots) {
		new SemanticAnalyzer(this).check(astRoots);

		// constant folding 
		ConstantFolderVisitor constV = new ConstantFolderVisitor();
		for(ClassDecl cd: astRoots) {
			constV.visit(cd, null);
		}

		// Build control flow graph:
		for (ClassDecl cd : astRoots)
			for (MethodDecl md : cd.methods())
				new CfgBuilder().build(md);
		CfgDump.toString(astRoots, ".cfg", cfgdumpbase, false);
	}

	public void generateCode(List<ClassDecl> astRoots, Writer out) {
		CfgCodeGenerator cg = new CfgCodeGenerator(this, out);
		cg.go(astRoots);
	}

	/** Dumps the AST to the debug stream */
	private void dumpAst(List<ClassDecl> astRoots) throws IOException {
		if (this.debug == null)
			return;
		this.debug.write(AstDump.toString(astRoots));
	}
}
