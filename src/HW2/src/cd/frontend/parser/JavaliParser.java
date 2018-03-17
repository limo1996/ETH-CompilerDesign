// Generated from /Users/limo/Documents/eth-courses/CompilerDesign/homework/src/HW2/src/cd/frontend/parser/Javali.g4 by ANTLR 4.7.1

	// Java header
	package cd.frontend.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JavaliParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		Ident=39, Literal=40, COMMENT=41, LINE_COMMENT=42, WS=43;
	public static final int
		RULE_primitiveType = 0, RULE_type = 1, RULE_referenceType = 2, RULE_arrayType = 3, 
		RULE_unit = 4, RULE_classDecl = 5, RULE_memberList = 6, RULE_varDecl = 7, 
		RULE_methodDecl = 8, RULE_formalParamList = 9, RULE_stmt = 10, RULE_stmtBlock = 11, 
		RULE_methodCallStmt = 12, RULE_assignmentStmt = 13, RULE_writeStmt = 14, 
		RULE_ifStmt = 15, RULE_whileStmt = 16, RULE_returnStmt = 17, RULE_newExpr = 18, 
		RULE_readExpr = 19, RULE_actualParamList = 20, RULE_identAccess = 21, 
		RULE_expr = 22;
	public static final String[] ruleNames = {
		"primitiveType", "type", "referenceType", "arrayType", "unit", "classDecl", 
		"memberList", "varDecl", "methodDecl", "formalParamList", "stmt", "stmtBlock", 
		"methodCallStmt", "assignmentStmt", "writeStmt", "ifStmt", "whileStmt", 
		"returnStmt", "newExpr", "readExpr", "actualParamList", "identAccess", 
		"expr"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'boolean'", "'int'", "'['", "']'", "'class'", "'extends'", "'{'", 
		"'}'", "','", "';'", "'void'", "'('", "')'", "'.'", "'='", "'write'", 
		"'writeln'", "'if'", "'else'", "'while'", "'return'", "'new'", "'read'", 
		"'this'", "'+'", "'-'", "'!'", "'*'", "'/'", "'%'", "'<'", "'<='", "'>'", 
		"'>='", "'=='", "'!='", "'&&'", "'||'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "Ident", "Literal", "COMMENT", "LINE_COMMENT", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Javali.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JavaliParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class PrimitiveTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			_la = _input.LA(1);
			if ( !(_la==T__0 || _la==T__1) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_type);
		try {
			setState(50);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(48);
				primitiveType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(49);
				referenceType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReferenceTypeContext extends ParserRuleContext {
		public TerminalNode Ident() { return getToken(JavaliParser.Ident, 0); }
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public ReferenceTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_referenceType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitReferenceType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenceTypeContext referenceType() throws RecognitionException {
		ReferenceTypeContext _localctx = new ReferenceTypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_referenceType);
		try {
			setState(54);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				match(Ident);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(53);
				arrayType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeContext extends ParserRuleContext {
		public TerminalNode Ident() { return getToken(JavaliParser.Ident, 0); }
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_arrayType);
		try {
			setState(63);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Ident:
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				match(Ident);
				setState(57);
				match(T__2);
				setState(58);
				match(T__3);
				}
				break;
			case T__0:
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(59);
				primitiveType();
				setState(60);
				match(T__2);
				setState(61);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JavaliParser.EOF, 0); }
		public List<ClassDeclContext> classDecl() {
			return getRuleContexts(ClassDeclContext.class);
		}
		public ClassDeclContext classDecl(int i) {
			return getRuleContext(ClassDeclContext.class,i);
		}
		public UnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnitContext unit() throws RecognitionException {
		UnitContext _localctx = new UnitContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_unit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(65);
				classDecl();
				}
				}
				setState(68); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__4 );
			setState(70);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclContext extends ParserRuleContext {
		public List<TerminalNode> Ident() { return getTokens(JavaliParser.Ident); }
		public TerminalNode Ident(int i) {
			return getToken(JavaliParser.Ident, i);
		}
		public MemberListContext memberList() {
			return getRuleContext(MemberListContext.class,0);
		}
		public ClassDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitClassDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclContext classDecl() throws RecognitionException {
		ClassDeclContext _localctx = new ClassDeclContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(T__4);
			setState(73);
			match(Ident);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(74);
				match(T__5);
				setState(75);
				match(Ident);
				}
			}

			setState(78);
			match(T__6);
			setState(79);
			memberList();
			setState(80);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberListContext extends ParserRuleContext {
		public List<VarDeclContext> varDecl() {
			return getRuleContexts(VarDeclContext.class);
		}
		public VarDeclContext varDecl(int i) {
			return getRuleContext(VarDeclContext.class,i);
		}
		public List<MethodDeclContext> methodDecl() {
			return getRuleContexts(MethodDeclContext.class);
		}
		public MethodDeclContext methodDecl(int i) {
			return getRuleContext(MethodDeclContext.class,i);
		}
		public MemberListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitMemberList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberListContext memberList() throws RecognitionException {
		MemberListContext _localctx = new MemberListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_memberList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__10) | (1L << Ident))) != 0)) {
				{
				setState(84);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(82);
					varDecl();
					}
					break;
				case 2:
					{
					setState(83);
					methodDecl();
					}
					break;
				}
				}
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> Ident() { return getTokens(JavaliParser.Ident); }
		public TerminalNode Ident(int i) {
			return getToken(JavaliParser.Ident, i);
		}
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			type();
			setState(90);
			match(Ident);
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(91);
				match(T__8);
				setState(92);
				match(Ident);
				}
				}
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(98);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclContext extends ParserRuleContext {
		public TerminalNode Ident() { return getToken(JavaliParser.Ident, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FormalParamListContext formalParamList() {
			return getRuleContext(FormalParamListContext.class,0);
		}
		public List<VarDeclContext> varDecl() {
			return getRuleContexts(VarDeclContext.class);
		}
		public VarDeclContext varDecl(int i) {
			return getRuleContext(VarDeclContext.class,i);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public MethodDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitMethodDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclContext methodDecl() throws RecognitionException {
		MethodDeclContext _localctx = new MethodDeclContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_methodDecl);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case Ident:
				{
				setState(100);
				type();
				}
				break;
			case T__10:
				{
				setState(101);
				match(T__10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(104);
			match(Ident);
			setState(105);
			match(T__11);
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << Ident))) != 0)) {
				{
				setState(106);
				formalParamList();
				}
			}

			setState(109);
			match(T__12);
			setState(110);
			match(T__6);
			setState(114);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(111);
					varDecl();
					}
					} 
				}
				setState(116);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__19) | (1L << T__20) | (1L << T__23) | (1L << Ident))) != 0)) {
				{
				{
				setState(117);
				stmt();
				}
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(123);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParamListContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> Ident() { return getTokens(JavaliParser.Ident); }
		public TerminalNode Ident(int i) {
			return getToken(JavaliParser.Ident, i);
		}
		public FormalParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParamList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitFormalParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParamListContext formalParamList() throws RecognitionException {
		FormalParamListContext _localctx = new FormalParamListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_formalParamList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			type();
			setState(126);
			match(Ident);
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(127);
				match(T__8);
				setState(128);
				type();
				setState(129);
				match(Ident);
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	 
		public StmtContext() { }
		public void copyFrom(StmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StmtWhileContext extends StmtContext {
		public WhileStmtContext whileStmt() {
			return getRuleContext(WhileStmtContext.class,0);
		}
		public StmtWhileContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtIfContext extends StmtContext {
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public StmtIfContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtIf(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtMetCallContext extends StmtContext {
		public MethodCallStmtContext methodCallStmt() {
			return getRuleContext(MethodCallStmtContext.class,0);
		}
		public StmtMetCallContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtMetCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtWriteContext extends StmtContext {
		public WriteStmtContext writeStmt() {
			return getRuleContext(WriteStmtContext.class,0);
		}
		public StmtWriteContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtWrite(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtReturnContext extends StmtContext {
		public ReturnStmtContext returnStmt() {
			return getRuleContext(ReturnStmtContext.class,0);
		}
		public StmtReturnContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtReturn(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtAssignContext extends StmtContext {
		public AssignmentStmtContext assignmentStmt() {
			return getRuleContext(AssignmentStmtContext.class,0);
		}
		public StmtAssignContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtAssign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_stmt);
		try {
			setState(142);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new StmtAssignContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				assignmentStmt();
				}
				break;
			case 2:
				_localctx = new StmtMetCallContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(137);
				methodCallStmt();
				}
				break;
			case 3:
				_localctx = new StmtIfContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(138);
				ifStmt();
				}
				break;
			case 4:
				_localctx = new StmtWhileContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(139);
				whileStmt();
				}
				break;
			case 5:
				_localctx = new StmtReturnContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(140);
				returnStmt();
				}
				break;
			case 6:
				_localctx = new StmtWriteContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(141);
				writeStmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtBlockContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public StmtBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmtBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtBlockContext stmtBlock() throws RecognitionException {
		StmtBlockContext _localctx = new StmtBlockContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_stmtBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(T__6);
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__19) | (1L << T__20) | (1L << T__23) | (1L << Ident))) != 0)) {
				{
				{
				setState(145);
				stmt();
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(151);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodCallStmtContext extends ParserRuleContext {
		public TerminalNode Ident() { return getToken(JavaliParser.Ident, 0); }
		public ActualParamListContext actualParamList() {
			return getRuleContext(ActualParamListContext.class,0);
		}
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public MethodCallStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodCallStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitMethodCallStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodCallStmtContext methodCallStmt() throws RecognitionException {
		MethodCallStmtContext _localctx = new MethodCallStmtContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_methodCallStmt);
		int _la;
		try {
			setState(169);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(153);
				match(Ident);
				setState(154);
				match(T__11);
				setState(156);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << Ident) | (1L << Literal))) != 0)) {
					{
					setState(155);
					actualParamList();
					}
				}

				setState(158);
				match(T__12);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(159);
				identAccess(0);
				setState(160);
				match(T__13);
				setState(161);
				match(Ident);
				setState(162);
				match(T__11);
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << Ident) | (1L << Literal))) != 0)) {
					{
					setState(163);
					actualParamList();
					}
				}

				setState(166);
				match(T__12);
				setState(167);
				match(T__9);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentStmtContext extends ParserRuleContext {
		public AssignmentStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentStmt; }
	 
		public AssignmentStmtContext() { }
		public void copyFrom(AssignmentStmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssignReadContext extends AssignmentStmtContext {
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public ReadExprContext readExpr() {
			return getRuleContext(ReadExprContext.class,0);
		}
		public AssignReadContext(AssignmentStmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitAssignRead(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignNewContext extends AssignmentStmtContext {
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public NewExprContext newExpr() {
			return getRuleContext(NewExprContext.class,0);
		}
		public AssignNewContext(AssignmentStmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitAssignNew(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignExprContext extends AssignmentStmtContext {
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignExprContext(AssignmentStmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitAssignExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentStmtContext assignmentStmt() throws RecognitionException {
		AssignmentStmtContext _localctx = new AssignmentStmtContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_assignmentStmt);
		try {
			setState(186);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				_localctx = new AssignExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(171);
				identAccess(0);
				setState(172);
				match(T__14);
				setState(173);
				expr(0);
				setState(174);
				match(T__9);
				}
				break;
			case 2:
				_localctx = new AssignNewContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(176);
				identAccess(0);
				setState(177);
				match(T__14);
				setState(178);
				newExpr();
				setState(179);
				match(T__9);
				}
				break;
			case 3:
				_localctx = new AssignReadContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(181);
				identAccess(0);
				setState(182);
				match(T__14);
				setState(183);
				readExpr();
				setState(184);
				match(T__9);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WriteStmtContext extends ParserRuleContext {
		public WriteStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_writeStmt; }
	 
		public WriteStmtContext() { }
		public void copyFrom(WriteStmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WritelnContext extends WriteStmtContext {
		public WritelnContext(WriteStmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitWriteln(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WriteContext extends WriteStmtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public WriteContext(WriteStmtContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitWrite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WriteStmtContext writeStmt() throws RecognitionException {
		WriteStmtContext _localctx = new WriteStmtContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_writeStmt);
		try {
			setState(198);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__15:
				_localctx = new WriteContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(188);
				match(T__15);
				setState(189);
				match(T__11);
				setState(190);
				expr(0);
				setState(191);
				match(T__12);
				setState(192);
				match(T__9);
				}
				break;
			case T__16:
				_localctx = new WritelnContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(194);
				match(T__16);
				setState(195);
				match(T__11);
				setState(196);
				match(T__12);
				setState(197);
				match(T__9);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStmtContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StmtBlockContext> stmtBlock() {
			return getRuleContexts(StmtBlockContext.class);
		}
		public StmtBlockContext stmtBlock(int i) {
			return getRuleContext(StmtBlockContext.class,i);
		}
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ifStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(T__17);
			setState(201);
			match(T__11);
			setState(202);
			expr(0);
			setState(203);
			match(T__12);
			setState(204);
			stmtBlock();
			setState(207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__18) {
				{
				setState(205);
				match(T__18);
				setState(206);
				stmtBlock();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStmtContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StmtBlockContext stmtBlock() {
			return getRuleContext(StmtBlockContext.class,0);
		}
		public WhileStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStmtContext whileStmt() throws RecognitionException {
		WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_whileStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(T__19);
			setState(210);
			match(T__11);
			setState(211);
			expr(0);
			setState(212);
			match(T__12);
			setState(213);
			stmtBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStmtContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStmtContext returnStmt() throws RecognitionException {
		ReturnStmtContext _localctx = new ReturnStmtContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_returnStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			match(T__20);
			setState(217);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << Ident) | (1L << Literal))) != 0)) {
				{
				setState(216);
				expr(0);
				}
			}

			setState(219);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewExprContext extends ParserRuleContext {
		public TerminalNode Ident() { return getToken(JavaliParser.Ident, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public NewExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitNewExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewExprContext newExpr() throws RecognitionException {
		NewExprContext _localctx = new NewExprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_newExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			match(T__21);
			setState(235);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(222);
				match(Ident);
				setState(223);
				match(T__11);
				setState(224);
				match(T__12);
				}
				break;
			case 2:
				{
				setState(225);
				match(Ident);
				setState(226);
				match(T__2);
				setState(227);
				expr(0);
				setState(228);
				match(T__3);
				}
				break;
			case 3:
				{
				setState(230);
				primitiveType();
				setState(231);
				match(T__2);
				setState(232);
				expr(0);
				setState(233);
				match(T__3);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReadExprContext extends ParserRuleContext {
		public ReadExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_readExpr; }
	 
		public ReadExprContext() { }
		public void copyFrom(ReadExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ReadContext extends ReadExprContext {
		public ReadContext(ReadExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitRead(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReadExprContext readExpr() throws RecognitionException {
		ReadExprContext _localctx = new ReadExprContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_readExpr);
		try {
			_localctx = new ReadContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			match(T__22);
			setState(238);
			match(T__11);
			setState(239);
			match(T__12);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActualParamListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ActualParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actualParamList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitActualParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActualParamListContext actualParamList() throws RecognitionException {
		ActualParamListContext _localctx = new ActualParamListContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_actualParamList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			expr(0);
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(242);
				match(T__8);
				setState(243);
				expr(0);
				}
				}
				setState(248);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentAccessContext extends ParserRuleContext {
		public TerminalNode Ident() { return getToken(JavaliParser.Ident, 0); }
		public ActualParamListContext actualParamList() {
			return getRuleContext(ActualParamListContext.class,0);
		}
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public IdentAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identAccess; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIdentAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentAccessContext identAccess() throws RecognitionException {
		return identAccess(0);
	}

	private IdentAccessContext identAccess(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		IdentAccessContext _localctx = new IdentAccessContext(_ctx, _parentState);
		IdentAccessContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_identAccess, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(250);
				match(Ident);
				}
				break;
			case 2:
				{
				setState(251);
				match(T__23);
				}
				break;
			case 3:
				{
				setState(252);
				match(Ident);
				setState(253);
				match(T__11);
				setState(255);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << Ident) | (1L << Literal))) != 0)) {
					{
					setState(254);
					actualParamList();
					}
				}

				setState(257);
				match(T__12);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(278);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(276);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
					case 1:
						{
						_localctx = new IdentAccessContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identAccess);
						setState(260);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(261);
						match(T__13);
						setState(262);
						match(Ident);
						}
						break;
					case 2:
						{
						_localctx = new IdentAccessContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identAccess);
						setState(263);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(264);
						match(T__2);
						setState(265);
						expr(0);
						setState(266);
						match(T__3);
						}
						break;
					case 3:
						{
						_localctx = new IdentAccessContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identAccess);
						setState(268);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(269);
						match(T__13);
						setState(270);
						match(Ident);
						setState(271);
						match(T__11);
						setState(273);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << Ident) | (1L << Literal))) != 0)) {
							{
							setState(272);
							actualParamList();
							}
						}

						setState(275);
						match(T__12);
						}
						break;
					}
					} 
				}
				setState(280);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CASTContext extends ExprContext {
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public CASTContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitCAST(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class COMPContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public COMPContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitCOMP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BRACKETSContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BRACKETSContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitBRACKETS(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ORContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ORContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitOR(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LITContext extends ExprContext {
		public TerminalNode Literal() { return getToken(JavaliParser.Literal, 0); }
		public LITContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitLIT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ADDIContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ADDIContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitADDI(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ANDContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ANDContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitAND(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TERMContext extends ExprContext {
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public TERMContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitTERM(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EQIContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public EQIContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitEQI(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UNARYContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UNARYContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitUNARY(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MULTIContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public MULTIContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitMULTI(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				_localctx = new LITContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(282);
				match(Literal);
				}
				break;
			case 2:
				{
				_localctx = new TERMContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(283);
				identAccess(0);
				}
				break;
			case 3:
				{
				_localctx = new BRACKETSContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(284);
				match(T__11);
				setState(285);
				expr(0);
				setState(286);
				match(T__12);
				}
				break;
			case 4:
				{
				_localctx = new UNARYContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(288);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__24) | (1L << T__25) | (1L << T__26))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(289);
				expr(8);
				}
				break;
			case 5:
				{
				_localctx = new CASTContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(290);
				match(T__11);
				setState(291);
				referenceType();
				setState(292);
				match(T__12);
				setState(293);
				expr(7);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(317);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(315);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
					case 1:
						{
						_localctx = new MULTIContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(297);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(298);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__27) | (1L << T__28) | (1L << T__29))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(299);
						expr(7);
						}
						break;
					case 2:
						{
						_localctx = new ADDIContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(300);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(301);
						_la = _input.LA(1);
						if ( !(_la==T__24 || _la==T__25) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(302);
						expr(6);
						}
						break;
					case 3:
						{
						_localctx = new COMPContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(303);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(304);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(305);
						expr(5);
						}
						break;
					case 4:
						{
						_localctx = new EQIContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(306);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(307);
						_la = _input.LA(1);
						if ( !(_la==T__34 || _la==T__35) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(308);
						expr(4);
						}
						break;
					case 5:
						{
						_localctx = new ANDContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(309);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(310);
						match(T__36);
						setState(311);
						expr(3);
						}
						break;
					case 6:
						{
						_localctx = new ORContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(312);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(313);
						match(T__37);
						setState(314);
						expr(2);
						}
						break;
					}
					} 
				}
				setState(319);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 21:
			return identAccess_sempred((IdentAccessContext)_localctx, predIndex);
		case 22:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean identAccess_sempred(IdentAccessContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 6);
		case 4:
			return precpred(_ctx, 5);
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 2);
		case 8:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3-\u0143\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2\3\2\3"+
		"\3\3\3\5\3\65\n\3\3\4\3\4\5\49\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5B\n"+
		"\5\3\6\6\6E\n\6\r\6\16\6F\3\6\3\6\3\7\3\7\3\7\3\7\5\7O\n\7\3\7\3\7\3\7"+
		"\3\7\3\b\3\b\7\bW\n\b\f\b\16\bZ\13\b\3\t\3\t\3\t\3\t\7\t`\n\t\f\t\16\t"+
		"c\13\t\3\t\3\t\3\n\3\n\5\ni\n\n\3\n\3\n\3\n\5\nn\n\n\3\n\3\n\3\n\7\ns"+
		"\n\n\f\n\16\nv\13\n\3\n\7\ny\n\n\f\n\16\n|\13\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\7\13\u0086\n\13\f\13\16\13\u0089\13\13\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\5\f\u0091\n\f\3\r\3\r\7\r\u0095\n\r\f\r\16\r\u0098\13\r\3\r\3"+
		"\r\3\16\3\16\3\16\5\16\u009f\n\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00a7"+
		"\n\16\3\16\3\16\3\16\5\16\u00ac\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00bd\n\17\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00c9\n\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\5\21\u00d2\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23"+
		"\5\23\u00dc\n\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\5\24\u00ee\n\24\3\25\3\25\3\25\3\25\3\26\3\26"+
		"\3\26\7\26\u00f7\n\26\f\26\16\26\u00fa\13\26\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\5\27\u0102\n\27\3\27\5\27\u0105\n\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u0114\n\27\3\27\7\27\u0117"+
		"\n\27\f\27\16\27\u011a\13\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\5\30\u012a\n\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\7\30"+
		"\u013e\n\30\f\30\16\30\u0141\13\30\3\30\2\4,.\31\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\2\b\3\2\3\4\3\2\33\35\3\2\36 \3\2\33\34\3"+
		"\2!$\3\2%&\2\u015a\2\60\3\2\2\2\4\64\3\2\2\2\68\3\2\2\2\bA\3\2\2\2\nD"+
		"\3\2\2\2\fJ\3\2\2\2\16X\3\2\2\2\20[\3\2\2\2\22h\3\2\2\2\24\177\3\2\2\2"+
		"\26\u0090\3\2\2\2\30\u0092\3\2\2\2\32\u00ab\3\2\2\2\34\u00bc\3\2\2\2\36"+
		"\u00c8\3\2\2\2 \u00ca\3\2\2\2\"\u00d3\3\2\2\2$\u00d9\3\2\2\2&\u00df\3"+
		"\2\2\2(\u00ef\3\2\2\2*\u00f3\3\2\2\2,\u0104\3\2\2\2.\u0129\3\2\2\2\60"+
		"\61\t\2\2\2\61\3\3\2\2\2\62\65\5\2\2\2\63\65\5\6\4\2\64\62\3\2\2\2\64"+
		"\63\3\2\2\2\65\5\3\2\2\2\669\7)\2\2\679\5\b\5\28\66\3\2\2\28\67\3\2\2"+
		"\29\7\3\2\2\2:;\7)\2\2;<\7\5\2\2<B\7\6\2\2=>\5\2\2\2>?\7\5\2\2?@\7\6\2"+
		"\2@B\3\2\2\2A:\3\2\2\2A=\3\2\2\2B\t\3\2\2\2CE\5\f\7\2DC\3\2\2\2EF\3\2"+
		"\2\2FD\3\2\2\2FG\3\2\2\2GH\3\2\2\2HI\7\2\2\3I\13\3\2\2\2JK\7\7\2\2KN\7"+
		")\2\2LM\7\b\2\2MO\7)\2\2NL\3\2\2\2NO\3\2\2\2OP\3\2\2\2PQ\7\t\2\2QR\5\16"+
		"\b\2RS\7\n\2\2S\r\3\2\2\2TW\5\20\t\2UW\5\22\n\2VT\3\2\2\2VU\3\2\2\2WZ"+
		"\3\2\2\2XV\3\2\2\2XY\3\2\2\2Y\17\3\2\2\2ZX\3\2\2\2[\\\5\4\3\2\\a\7)\2"+
		"\2]^\7\13\2\2^`\7)\2\2_]\3\2\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2bd\3\2\2"+
		"\2ca\3\2\2\2de\7\f\2\2e\21\3\2\2\2fi\5\4\3\2gi\7\r\2\2hf\3\2\2\2hg\3\2"+
		"\2\2ij\3\2\2\2jk\7)\2\2km\7\16\2\2ln\5\24\13\2ml\3\2\2\2mn\3\2\2\2no\3"+
		"\2\2\2op\7\17\2\2pt\7\t\2\2qs\5\20\t\2rq\3\2\2\2sv\3\2\2\2tr\3\2\2\2t"+
		"u\3\2\2\2uz\3\2\2\2vt\3\2\2\2wy\5\26\f\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2\2"+
		"z{\3\2\2\2{}\3\2\2\2|z\3\2\2\2}~\7\n\2\2~\23\3\2\2\2\177\u0080\5\4\3\2"+
		"\u0080\u0087\7)\2\2\u0081\u0082\7\13\2\2\u0082\u0083\5\4\3\2\u0083\u0084"+
		"\7)\2\2\u0084\u0086\3\2\2\2\u0085\u0081\3\2\2\2\u0086\u0089\3\2\2\2\u0087"+
		"\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\25\3\2\2\2\u0089\u0087\3\2\2"+
		"\2\u008a\u0091\5\34\17\2\u008b\u0091\5\32\16\2\u008c\u0091\5 \21\2\u008d"+
		"\u0091\5\"\22\2\u008e\u0091\5$\23\2\u008f\u0091\5\36\20\2\u0090\u008a"+
		"\3\2\2\2\u0090\u008b\3\2\2\2\u0090\u008c\3\2\2\2\u0090\u008d\3\2\2\2\u0090"+
		"\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\27\3\2\2\2\u0092\u0096\7\t\2"+
		"\2\u0093\u0095\5\26\f\2\u0094\u0093\3\2\2\2\u0095\u0098\3\2\2\2\u0096"+
		"\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0099\3\2\2\2\u0098\u0096\3\2"+
		"\2\2\u0099\u009a\7\n\2\2\u009a\31\3\2\2\2\u009b\u009c\7)\2\2\u009c\u009e"+
		"\7\16\2\2\u009d\u009f\5*\26\2\u009e\u009d\3\2\2\2\u009e\u009f\3\2\2\2"+
		"\u009f\u00a0\3\2\2\2\u00a0\u00ac\7\17\2\2\u00a1\u00a2\5,\27\2\u00a2\u00a3"+
		"\7\20\2\2\u00a3\u00a4\7)\2\2\u00a4\u00a6\7\16\2\2\u00a5\u00a7\5*\26\2"+
		"\u00a6\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9"+
		"\7\17\2\2\u00a9\u00aa\7\f\2\2\u00aa\u00ac\3\2\2\2\u00ab\u009b\3\2\2\2"+
		"\u00ab\u00a1\3\2\2\2\u00ac\33\3\2\2\2\u00ad\u00ae\5,\27\2\u00ae\u00af"+
		"\7\21\2\2\u00af\u00b0\5.\30\2\u00b0\u00b1\7\f\2\2\u00b1\u00bd\3\2\2\2"+
		"\u00b2\u00b3\5,\27\2\u00b3\u00b4\7\21\2\2\u00b4\u00b5\5&\24\2\u00b5\u00b6"+
		"\7\f\2\2\u00b6\u00bd\3\2\2\2\u00b7\u00b8\5,\27\2\u00b8\u00b9\7\21\2\2"+
		"\u00b9\u00ba\5(\25\2\u00ba\u00bb\7\f\2\2\u00bb\u00bd\3\2\2\2\u00bc\u00ad"+
		"\3\2\2\2\u00bc\u00b2\3\2\2\2\u00bc\u00b7\3\2\2\2\u00bd\35\3\2\2\2\u00be"+
		"\u00bf\7\22\2\2\u00bf\u00c0\7\16\2\2\u00c0\u00c1\5.\30\2\u00c1\u00c2\7"+
		"\17\2\2\u00c2\u00c3\7\f\2\2\u00c3\u00c9\3\2\2\2\u00c4\u00c5\7\23\2\2\u00c5"+
		"\u00c6\7\16\2\2\u00c6\u00c7\7\17\2\2\u00c7\u00c9\7\f\2\2\u00c8\u00be\3"+
		"\2\2\2\u00c8\u00c4\3\2\2\2\u00c9\37\3\2\2\2\u00ca\u00cb\7\24\2\2\u00cb"+
		"\u00cc\7\16\2\2\u00cc\u00cd\5.\30\2\u00cd\u00ce\7\17\2\2\u00ce\u00d1\5"+
		"\30\r\2\u00cf\u00d0\7\25\2\2\u00d0\u00d2\5\30\r\2\u00d1\u00cf\3\2\2\2"+
		"\u00d1\u00d2\3\2\2\2\u00d2!\3\2\2\2\u00d3\u00d4\7\26\2\2\u00d4\u00d5\7"+
		"\16\2\2\u00d5\u00d6\5.\30\2\u00d6\u00d7\7\17\2\2\u00d7\u00d8\5\30\r\2"+
		"\u00d8#\3\2\2\2\u00d9\u00db\7\27\2\2\u00da\u00dc\5.\30\2\u00db\u00da\3"+
		"\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00de\7\f\2\2\u00de"+
		"%\3\2\2\2\u00df\u00ed\7\30\2\2\u00e0\u00e1\7)\2\2\u00e1\u00e2\7\16\2\2"+
		"\u00e2\u00ee\7\17\2\2\u00e3\u00e4\7)\2\2\u00e4\u00e5\7\5\2\2\u00e5\u00e6"+
		"\5.\30\2\u00e6\u00e7\7\6\2\2\u00e7\u00ee\3\2\2\2\u00e8\u00e9\5\2\2\2\u00e9"+
		"\u00ea\7\5\2\2\u00ea\u00eb\5.\30\2\u00eb\u00ec\7\6\2\2\u00ec\u00ee\3\2"+
		"\2\2\u00ed\u00e0\3\2\2\2\u00ed\u00e3\3\2\2\2\u00ed\u00e8\3\2\2\2\u00ee"+
		"\'\3\2\2\2\u00ef\u00f0\7\31\2\2\u00f0\u00f1\7\16\2\2\u00f1\u00f2\7\17"+
		"\2\2\u00f2)\3\2\2\2\u00f3\u00f8\5.\30\2\u00f4\u00f5\7\13\2\2\u00f5\u00f7"+
		"\5.\30\2\u00f6\u00f4\3\2\2\2\u00f7\u00fa\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8"+
		"\u00f9\3\2\2\2\u00f9+\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fb\u00fc\b\27\1\2"+
		"\u00fc\u0105\7)\2\2\u00fd\u0105\7\32\2\2\u00fe\u00ff\7)\2\2\u00ff\u0101"+
		"\7\16\2\2\u0100\u0102\5*\26\2\u0101\u0100\3\2\2\2\u0101\u0102\3\2\2\2"+
		"\u0102\u0103\3\2\2\2\u0103\u0105\7\17\2\2\u0104\u00fb\3\2\2\2\u0104\u00fd"+
		"\3\2\2\2\u0104\u00fe\3\2\2\2\u0105\u0118\3\2\2\2\u0106\u0107\f\6\2\2\u0107"+
		"\u0108\7\20\2\2\u0108\u0117\7)\2\2\u0109\u010a\f\5\2\2\u010a\u010b\7\5"+
		"\2\2\u010b\u010c\5.\30\2\u010c\u010d\7\6\2\2\u010d\u0117\3\2\2\2\u010e"+
		"\u010f\f\3\2\2\u010f\u0110\7\20\2\2\u0110\u0111\7)\2\2\u0111\u0113\7\16"+
		"\2\2\u0112\u0114\5*\26\2\u0113\u0112\3\2\2\2\u0113\u0114\3\2\2\2\u0114"+
		"\u0115\3\2\2\2\u0115\u0117\7\17\2\2\u0116\u0106\3\2\2\2\u0116\u0109\3"+
		"\2\2\2\u0116\u010e\3\2\2\2\u0117\u011a\3\2\2\2\u0118\u0116\3\2\2\2\u0118"+
		"\u0119\3\2\2\2\u0119-\3\2\2\2\u011a\u0118\3\2\2\2\u011b\u011c\b\30\1\2"+
		"\u011c\u012a\7*\2\2\u011d\u012a\5,\27\2\u011e\u011f\7\16\2\2\u011f\u0120"+
		"\5.\30\2\u0120\u0121\7\17\2\2\u0121\u012a\3\2\2\2\u0122\u0123\t\3\2\2"+
		"\u0123\u012a\5.\30\n\u0124\u0125\7\16\2\2\u0125\u0126\5\6\4\2\u0126\u0127"+
		"\7\17\2\2\u0127\u0128\5.\30\t\u0128\u012a\3\2\2\2\u0129\u011b\3\2\2\2"+
		"\u0129\u011d\3\2\2\2\u0129\u011e\3\2\2\2\u0129\u0122\3\2\2\2\u0129\u0124"+
		"\3\2\2\2\u012a\u013f\3\2\2\2\u012b\u012c\f\b\2\2\u012c\u012d\t\4\2\2\u012d"+
		"\u013e\5.\30\t\u012e\u012f\f\7\2\2\u012f\u0130\t\5\2\2\u0130\u013e\5."+
		"\30\b\u0131\u0132\f\6\2\2\u0132\u0133\t\6\2\2\u0133\u013e\5.\30\7\u0134"+
		"\u0135\f\5\2\2\u0135\u0136\t\7\2\2\u0136\u013e\5.\30\6\u0137\u0138\f\4"+
		"\2\2\u0138\u0139\7\'\2\2\u0139\u013e\5.\30\5\u013a\u013b\f\3\2\2\u013b"+
		"\u013c\7(\2\2\u013c\u013e\5.\30\4\u013d\u012b\3\2\2\2\u013d\u012e\3\2"+
		"\2\2\u013d\u0131\3\2\2\2\u013d\u0134\3\2\2\2\u013d\u0137\3\2\2\2\u013d"+
		"\u013a\3\2\2\2\u013e\u0141\3\2\2\2\u013f\u013d\3\2\2\2\u013f\u0140\3\2"+
		"\2\2\u0140/\3\2\2\2\u0141\u013f\3\2\2\2\"\648AFNVXahmtz\u0087\u0090\u0096"+
		"\u009e\u00a6\u00ab\u00bc\u00c8\u00d1\u00db\u00ed\u00f8\u0101\u0104\u0113"+
		"\u0116\u0118\u0129\u013d\u013f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}