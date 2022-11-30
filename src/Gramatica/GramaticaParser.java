// Generated from D:/ARCHIVOS E/CHIPECH/PROGRAMACION/COMPILADORES2/LAB/olc2vj22_201404278/Proyecto1/src\Gramatica.g4 by ANTLR 4.10.1
package Gramatica;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GramaticaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, PROGRAM=43, END=44, PRINT=45, 
		DIMENSION=46, SIZEARR=47, MENORQUE=48, MAYORQUE=49, MENOR=50, MAYOR=51, 
		IGUALIGUAL=52, DIFIGUAL=53, CHTR1=54, CHTR2=55, LOGICL=56, ID=57, INT=58, 
		REAL=59, STRING1=60, COMMENT=61, WS=62;
	public static final int
		RULE_parse = 0, RULE_linstrucciones = 1, RULE_instruccion = 2, RULE_progmain = 3, 
		RULE_implicit = 4, RULE_declarationVar = 5, RULE_declarationVar2 = 6, 
		RULE_declarationVar3 = 7, RULE_asignationVar = 8, RULE_printt = 9, RULE_printt2 = 10, 
		RULE_printt3 = 11, RULE_lexpr = 12, RULE_decArrExtenso = 13, RULE_decArrCorto = 14, 
		RULE_declarationArray2 = 15, RULE_asigArrList = 16, RULE_asigArrPosEspecifico = 17, 
		RULE_asignationArray2 = 18, RULE_declarationArrayDinamico = 19, RULE_declarationArrayDinamico2 = 20, 
		RULE_asignationArrayDinamico = 21, RULE_desAsignationArrayDinamico = 22, 
		RULE_asignationArrayDinamico2 = 23, RULE_sentenciaIf = 24, RULE_lcondicionesIf = 25, 
		RULE_elseBloque = 26, RULE_doEstructura = 27, RULE_doWhileEstrctura = 28, 
		RULE_controlExit = 29, RULE_controlCycle = 30, RULE_etiquetaDo = 31, RULE_etiquetaDoWhile = 32, 
		RULE_subroutine = 33, RULE_ldeclP = 34, RULE_declParametros = 35, RULE_valorParametros = 36, 
		RULE_call = 37, RULE_funcion = 38, RULE_expr = 39, RULE_type = 40;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "linstrucciones", "instruccion", "progmain", "implicit", "declarationVar", 
			"declarationVar2", "declarationVar3", "asignationVar", "printt", "printt2", 
			"printt3", "lexpr", "decArrExtenso", "decArrCorto", "declarationArray2", 
			"asigArrList", "asigArrPosEspecifico", "asignationArray2", "declarationArrayDinamico", 
			"declarationArrayDinamico2", "asignationArrayDinamico", "desAsignationArrayDinamico", 
			"asignationArrayDinamico2", "sentenciaIf", "lcondicionesIf", "elseBloque", 
			"doEstructura", "doWhileEstrctura", "controlExit", "controlCycle", "etiquetaDo", 
			"etiquetaDoWhile", "subroutine", "ldeclP", "declParametros", "valorParametros", 
			"call", "funcion", "expr", "type"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'implicit'", "'none'", "'::'", "'='", "','", "'*,'", "'('", "')'", 
			"'(/'", "'/)'", "'['", "']'", "'allocatable'", "':'", "'allocate'", "'deallocate'", 
			"'if'", "'else'", "'then'", "'do'", "'while'", "'exit'", "'cycle'", "'subroutine'", 
			"'intent'", "'in'", "'call'", "'function'", "'result'", "'**'", "'-'", 
			"'.not.'", "'*'", "'/'", "'+'", "'.and.'", "'.or.'", "'integer'", "'real'", 
			"'complex'", "'character'", "'logical'", "'program'", "'end'", "'print'", 
			"'dimension'", "'size'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "PROGRAM", "END", "PRINT", 
			"DIMENSION", "SIZEARR", "MENORQUE", "MAYORQUE", "MENOR", "MAYOR", "IGUALIGUAL", 
			"DIFIGUAL", "CHTR1", "CHTR2", "LOGICL", "ID", "INT", "REAL", "STRING1", 
			"COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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
	public String getGrammarFileName() { return "Gramatica.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GramaticaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ParseContext extends ParserRuleContext {
		public LinstruccionesContext linstrucciones() {
			return getRuleContext(LinstruccionesContext.class,0);
		}
		public TerminalNode EOF() { return getToken(GramaticaParser.EOF, 0); }
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitParse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitParse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			linstrucciones();
			setState(83);
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

	public static class LinstruccionesContext extends ParserRuleContext {
		public List<InstruccionContext> instruccion() {
			return getRuleContexts(InstruccionContext.class);
		}
		public InstruccionContext instruccion(int i) {
			return getRuleContext(InstruccionContext.class,i);
		}
		public LinstruccionesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_linstrucciones; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterLinstrucciones(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitLinstrucciones(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitLinstrucciones(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LinstruccionesContext linstrucciones() throws RecognitionException {
		LinstruccionesContext _localctx = new LinstruccionesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_linstrucciones);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			instruccion();
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__19) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__26) | (1L << T__27) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << PROGRAM) | (1L << PRINT) | (1L << ID))) != 0)) {
				{
				{
				setState(86);
				instruccion();
				}
				}
				setState(91);
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

	public static class InstruccionContext extends ParserRuleContext {
		public ProgmainContext progmain() {
			return getRuleContext(ProgmainContext.class,0);
		}
		public DeclarationVarContext declarationVar() {
			return getRuleContext(DeclarationVarContext.class,0);
		}
		public AsignationVarContext asignationVar() {
			return getRuleContext(AsignationVarContext.class,0);
		}
		public PrinttContext printt() {
			return getRuleContext(PrinttContext.class,0);
		}
		public DecArrExtensoContext decArrExtenso() {
			return getRuleContext(DecArrExtensoContext.class,0);
		}
		public DecArrCortoContext decArrCorto() {
			return getRuleContext(DecArrCortoContext.class,0);
		}
		public AsigArrListContext asigArrList() {
			return getRuleContext(AsigArrListContext.class,0);
		}
		public AsigArrPosEspecificoContext asigArrPosEspecifico() {
			return getRuleContext(AsigArrPosEspecificoContext.class,0);
		}
		public DeclarationArrayDinamicoContext declarationArrayDinamico() {
			return getRuleContext(DeclarationArrayDinamicoContext.class,0);
		}
		public AsignationArrayDinamicoContext asignationArrayDinamico() {
			return getRuleContext(AsignationArrayDinamicoContext.class,0);
		}
		public DesAsignationArrayDinamicoContext desAsignationArrayDinamico() {
			return getRuleContext(DesAsignationArrayDinamicoContext.class,0);
		}
		public SentenciaIfContext sentenciaIf() {
			return getRuleContext(SentenciaIfContext.class,0);
		}
		public DoEstructuraContext doEstructura() {
			return getRuleContext(DoEstructuraContext.class,0);
		}
		public DoWhileEstrcturaContext doWhileEstrctura() {
			return getRuleContext(DoWhileEstrcturaContext.class,0);
		}
		public ControlExitContext controlExit() {
			return getRuleContext(ControlExitContext.class,0);
		}
		public ControlCycleContext controlCycle() {
			return getRuleContext(ControlCycleContext.class,0);
		}
		public EtiquetaDoContext etiquetaDo() {
			return getRuleContext(EtiquetaDoContext.class,0);
		}
		public EtiquetaDoWhileContext etiquetaDoWhile() {
			return getRuleContext(EtiquetaDoWhileContext.class,0);
		}
		public SubroutineContext subroutine() {
			return getRuleContext(SubroutineContext.class,0);
		}
		public CallContext call() {
			return getRuleContext(CallContext.class,0);
		}
		public FuncionContext funcion() {
			return getRuleContext(FuncionContext.class,0);
		}
		public InstruccionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruccion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterInstruccion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitInstruccion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitInstruccion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstruccionContext instruccion() throws RecognitionException {
		InstruccionContext _localctx = new InstruccionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_instruccion);
		try {
			setState(113);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(92);
				progmain();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(93);
				declarationVar();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(94);
				asignationVar();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(95);
				printt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(96);
				decArrExtenso();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(97);
				decArrCorto();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(98);
				asigArrList();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(99);
				asigArrPosEspecifico();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(100);
				declarationArrayDinamico();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(101);
				asignationArrayDinamico();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(102);
				desAsignationArrayDinamico();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(103);
				sentenciaIf();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(104);
				doEstructura();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(105);
				doWhileEstrctura();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(106);
				controlExit();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(107);
				controlCycle();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(108);
				etiquetaDo();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(109);
				etiquetaDoWhile();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(110);
				subroutine();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(111);
				call();
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(112);
				funcion();
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

	public static class ProgmainContext extends ParserRuleContext {
		public List<TerminalNode> PROGRAM() { return getTokens(GramaticaParser.PROGRAM); }
		public TerminalNode PROGRAM(int i) {
			return getToken(GramaticaParser.PROGRAM, i);
		}
		public List<TerminalNode> ID() { return getTokens(GramaticaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GramaticaParser.ID, i);
		}
		public ImplicitContext implicit() {
			return getRuleContext(ImplicitContext.class,0);
		}
		public LinstruccionesContext linstrucciones() {
			return getRuleContext(LinstruccionesContext.class,0);
		}
		public TerminalNode END() { return getToken(GramaticaParser.END, 0); }
		public ProgmainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_progmain; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterProgmain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitProgmain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitProgmain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgmainContext progmain() throws RecognitionException {
		ProgmainContext _localctx = new ProgmainContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_progmain);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(PROGRAM);
			setState(116);
			match(ID);
			setState(117);
			implicit();
			setState(118);
			linstrucciones();
			setState(119);
			match(END);
			setState(120);
			match(PROGRAM);
			setState(121);
			match(ID);
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

	public static class ImplicitContext extends ParserRuleContext {
		public ImplicitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_implicit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterImplicit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitImplicit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitImplicit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImplicitContext implicit() throws RecognitionException {
		ImplicitContext _localctx = new ImplicitContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_implicit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(T__0);
			setState(124);
			match(T__1);
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

	public static class DeclarationVarContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DeclarationVar2Context declarationVar2() {
			return getRuleContext(DeclarationVar2Context.class,0);
		}
		public DeclarationVar3Context declarationVar3() {
			return getRuleContext(DeclarationVar3Context.class,0);
		}
		public DeclarationVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationVar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDeclarationVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDeclarationVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDeclarationVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationVarContext declarationVar() throws RecognitionException {
		DeclarationVarContext _localctx = new DeclarationVarContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_declarationVar);
		try {
			setState(135);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(126);
				type();
				setState(127);
				match(T__2);
				setState(128);
				declarationVar2();
				setState(129);
				declarationVar3();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(131);
				type();
				setState(132);
				match(T__2);
				setState(133);
				declarationVar2();
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

	public static class DeclarationVar2Context extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DeclarationVar2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationVar2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDeclarationVar2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDeclarationVar2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDeclarationVar2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationVar2Context declarationVar2() throws RecognitionException {
		DeclarationVar2Context _localctx = new DeclarationVar2Context(_ctx, getState());
		enterRule(_localctx, 12, RULE_declarationVar2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(ID);
			setState(140);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(138);
				match(T__3);
				setState(139);
				expr(0);
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

	public static class DeclarationVar3Context extends ParserRuleContext {
		public DeclarationVar2Context declarationVar2() {
			return getRuleContext(DeclarationVar2Context.class,0);
		}
		public DeclarationVar3Context declarationVar3() {
			return getRuleContext(DeclarationVar3Context.class,0);
		}
		public DeclarationVar3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationVar3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDeclarationVar3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDeclarationVar3(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDeclarationVar3(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationVar3Context declarationVar3() throws RecognitionException {
		DeclarationVar3Context _localctx = new DeclarationVar3Context(_ctx, getState());
		enterRule(_localctx, 14, RULE_declarationVar3);
		try {
			setState(148);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(142);
				match(T__4);
				setState(143);
				declarationVar2();
				setState(144);
				declarationVar3();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(146);
				match(T__4);
				setState(147);
				declarationVar2();
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

	public static class AsignationVarContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AsignationVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asignationVar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterAsignationVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitAsignationVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitAsignationVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AsignationVarContext asignationVar() throws RecognitionException {
		AsignationVarContext _localctx = new AsignationVarContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_asignationVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			match(ID);
			setState(151);
			match(T__3);
			setState(152);
			expr(0);
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

	public static class PrinttContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(GramaticaParser.PRINT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Printt2Context printt2() {
			return getRuleContext(Printt2Context.class,0);
		}
		public PrinttContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterPrintt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitPrintt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitPrintt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrinttContext printt() throws RecognitionException {
		PrinttContext _localctx = new PrinttContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_printt);
		try {
			setState(162);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(154);
				match(PRINT);
				setState(155);
				match(T__5);
				setState(156);
				expr(0);
				setState(157);
				printt2();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(159);
				match(PRINT);
				setState(160);
				match(T__5);
				setState(161);
				expr(0);
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

	public static class Printt2Context extends ParserRuleContext {
		public Printt3Context printt3() {
			return getRuleContext(Printt3Context.class,0);
		}
		public Printt2Context printt2() {
			return getRuleContext(Printt2Context.class,0);
		}
		public Printt2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printt2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterPrintt2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitPrintt2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitPrintt2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Printt2Context printt2() throws RecognitionException {
		Printt2Context _localctx = new Printt2Context(_ctx, getState());
		enterRule(_localctx, 20, RULE_printt2);
		try {
			setState(168);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(164);
				printt3();
				setState(165);
				printt2();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(167);
				printt3();
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

	public static class Printt3Context extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Printt3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printt3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterPrintt3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitPrintt3(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitPrintt3(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Printt3Context printt3() throws RecognitionException {
		Printt3Context _localctx = new Printt3Context(_ctx, getState());
		enterRule(_localctx, 22, RULE_printt3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(T__4);
			setState(171);
			expr(0);
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

	public static class LexprContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public LexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterLexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitLexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitLexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexprContext lexpr() throws RecognitionException {
		LexprContext _localctx = new LexprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_lexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			expr(0);
			setState(178);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(174);
				match(T__4);
				setState(175);
				expr(0);
				}
				}
				setState(180);
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

	public static class DecArrExtensoContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode DIMENSION() { return getToken(GramaticaParser.DIMENSION, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public DeclarationArray2Context declarationArray2() {
			return getRuleContext(DeclarationArray2Context.class,0);
		}
		public DecArrExtensoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decArrExtenso; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDecArrExtenso(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDecArrExtenso(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDecArrExtenso(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecArrExtensoContext decArrExtenso() throws RecognitionException {
		DecArrExtensoContext _localctx = new DecArrExtensoContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_decArrExtenso);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			type();
			setState(182);
			match(T__4);
			setState(183);
			match(DIMENSION);
			setState(184);
			match(T__6);
			setState(185);
			expr(0);
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(186);
				declarationArray2();
				}
			}

			setState(189);
			match(T__7);
			setState(190);
			match(T__2);
			setState(191);
			match(ID);
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

	public static class DecArrCortoContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DeclarationArray2Context declarationArray2() {
			return getRuleContext(DeclarationArray2Context.class,0);
		}
		public DecArrCortoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decArrCorto; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDecArrCorto(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDecArrCorto(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDecArrCorto(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecArrCortoContext decArrCorto() throws RecognitionException {
		DecArrCortoContext _localctx = new DecArrCortoContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_decArrCorto);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			type();
			setState(194);
			match(T__2);
			setState(195);
			match(ID);
			setState(196);
			match(T__6);
			setState(197);
			expr(0);
			setState(199);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(198);
				declarationArray2();
				}
			}

			setState(201);
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

	public static class DeclarationArray2Context extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DeclarationArray2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationArray2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDeclarationArray2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDeclarationArray2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDeclarationArray2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationArray2Context declarationArray2() throws RecognitionException {
		DeclarationArray2Context _localctx = new DeclarationArray2Context(_ctx, getState());
		enterRule(_localctx, 30, RULE_declarationArray2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(T__4);
			setState(204);
			expr(0);
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

	public static class AsigArrListContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public LexprContext lexpr() {
			return getRuleContext(LexprContext.class,0);
		}
		public AsigArrListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asigArrList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterAsigArrList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitAsigArrList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitAsigArrList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AsigArrListContext asigArrList() throws RecognitionException {
		AsigArrListContext _localctx = new AsigArrListContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_asigArrList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(ID);
			setState(207);
			match(T__3);
			setState(208);
			match(T__8);
			setState(209);
			lexpr();
			setState(210);
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

	public static class AsigArrPosEspecificoContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public LexprContext lexpr() {
			return getRuleContext(LexprContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AsigArrPosEspecificoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asigArrPosEspecifico; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterAsigArrPosEspecifico(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitAsigArrPosEspecifico(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitAsigArrPosEspecifico(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AsigArrPosEspecificoContext asigArrPosEspecifico() throws RecognitionException {
		AsigArrPosEspecificoContext _localctx = new AsigArrPosEspecificoContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_asigArrPosEspecifico);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			match(ID);
			setState(213);
			match(T__10);
			setState(214);
			lexpr();
			setState(215);
			match(T__11);
			setState(216);
			match(T__3);
			setState(217);
			expr(0);
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

	public static class AsignationArray2Context extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AsignationArray2Context asignationArray2() {
			return getRuleContext(AsignationArray2Context.class,0);
		}
		public AsignationArray2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asignationArray2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterAsignationArray2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitAsignationArray2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitAsignationArray2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AsignationArray2Context asignationArray2() throws RecognitionException {
		AsignationArray2Context _localctx = new AsignationArray2Context(_ctx, getState());
		enterRule(_localctx, 36, RULE_asignationArray2);
		try {
			setState(225);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(219);
				match(T__4);
				setState(220);
				expr(0);
				setState(221);
				asignationArray2();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(223);
				match(T__4);
				setState(224);
				expr(0);
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

	public static class DeclarationArrayDinamicoContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public DeclarationArrayDinamico2Context declarationArrayDinamico2() {
			return getRuleContext(DeclarationArrayDinamico2Context.class,0);
		}
		public DeclarationArrayDinamicoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationArrayDinamico; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDeclarationArrayDinamico(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDeclarationArrayDinamico(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDeclarationArrayDinamico(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationArrayDinamicoContext declarationArrayDinamico() throws RecognitionException {
		DeclarationArrayDinamicoContext _localctx = new DeclarationArrayDinamicoContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_declarationArrayDinamico);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			type();
			setState(228);
			match(T__4);
			setState(229);
			match(T__12);
			setState(230);
			match(T__2);
			setState(231);
			match(ID);
			setState(232);
			match(T__6);
			setState(233);
			match(T__13);
			setState(235);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(234);
				declarationArrayDinamico2();
				}
			}

			setState(237);
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

	public static class DeclarationArrayDinamico2Context extends ParserRuleContext {
		public DeclarationArrayDinamico2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationArrayDinamico2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDeclarationArrayDinamico2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDeclarationArrayDinamico2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDeclarationArrayDinamico2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationArrayDinamico2Context declarationArrayDinamico2() throws RecognitionException {
		DeclarationArrayDinamico2Context _localctx = new DeclarationArrayDinamico2Context(_ctx, getState());
		enterRule(_localctx, 40, RULE_declarationArrayDinamico2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(T__4);
			setState(240);
			match(T__13);
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

	public static class AsignationArrayDinamicoContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AsignationArrayDinamico2Context asignationArrayDinamico2() {
			return getRuleContext(AsignationArrayDinamico2Context.class,0);
		}
		public AsignationArrayDinamicoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asignationArrayDinamico; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterAsignationArrayDinamico(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitAsignationArrayDinamico(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitAsignationArrayDinamico(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AsignationArrayDinamicoContext asignationArrayDinamico() throws RecognitionException {
		AsignationArrayDinamicoContext _localctx = new AsignationArrayDinamicoContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_asignationArrayDinamico);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			match(T__14);
			setState(243);
			match(T__6);
			setState(244);
			match(ID);
			setState(245);
			match(T__6);
			setState(246);
			expr(0);
			setState(248);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(247);
				asignationArrayDinamico2();
				}
			}

			setState(250);
			match(T__7);
			setState(251);
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

	public static class DesAsignationArrayDinamicoContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public DesAsignationArrayDinamicoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_desAsignationArrayDinamico; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDesAsignationArrayDinamico(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDesAsignationArrayDinamico(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDesAsignationArrayDinamico(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DesAsignationArrayDinamicoContext desAsignationArrayDinamico() throws RecognitionException {
		DesAsignationArrayDinamicoContext _localctx = new DesAsignationArrayDinamicoContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_desAsignationArrayDinamico);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(T__15);
			setState(254);
			match(T__6);
			setState(255);
			match(ID);
			setState(256);
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

	public static class AsignationArrayDinamico2Context extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AsignationArrayDinamico2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asignationArrayDinamico2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterAsignationArrayDinamico2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitAsignationArrayDinamico2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitAsignationArrayDinamico2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AsignationArrayDinamico2Context asignationArrayDinamico2() throws RecognitionException {
		AsignationArrayDinamico2Context _localctx = new AsignationArrayDinamico2Context(_ctx, getState());
		enterRule(_localctx, 46, RULE_asignationArrayDinamico2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			match(T__4);
			setState(259);
			expr(0);
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

	public static class SentenciaIfContext extends ParserRuleContext {
		public List<LcondicionesIfContext> lcondicionesIf() {
			return getRuleContexts(LcondicionesIfContext.class);
		}
		public LcondicionesIfContext lcondicionesIf(int i) {
			return getRuleContext(LcondicionesIfContext.class,i);
		}
		public TerminalNode END() { return getToken(GramaticaParser.END, 0); }
		public ElseBloqueContext elseBloque() {
			return getRuleContext(ElseBloqueContext.class,0);
		}
		public SentenciaIfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sentenciaIf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterSentenciaIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitSentenciaIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitSentenciaIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SentenciaIfContext sentenciaIf() throws RecognitionException {
		SentenciaIfContext _localctx = new SentenciaIfContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_sentenciaIf);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			match(T__16);
			setState(262);
			lcondicionesIf();
			setState(268);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(263);
					match(T__17);
					setState(264);
					match(T__16);
					setState(265);
					lcondicionesIf();
					}
					} 
				}
				setState(270);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			setState(273);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(271);
				match(T__17);
				setState(272);
				elseBloque();
				}
			}

			setState(275);
			match(END);
			setState(276);
			match(T__16);
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

	public static class LcondicionesIfContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public LinstruccionesContext linstrucciones() {
			return getRuleContext(LinstruccionesContext.class,0);
		}
		public LcondicionesIfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lcondicionesIf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterLcondicionesIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitLcondicionesIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitLcondicionesIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LcondicionesIfContext lcondicionesIf() throws RecognitionException {
		LcondicionesIfContext _localctx = new LcondicionesIfContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_lcondicionesIf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			match(T__6);
			setState(279);
			expr(0);
			setState(280);
			match(T__7);
			setState(281);
			match(T__18);
			setState(282);
			linstrucciones();
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

	public static class ElseBloqueContext extends ParserRuleContext {
		public LinstruccionesContext linstrucciones() {
			return getRuleContext(LinstruccionesContext.class,0);
		}
		public ElseBloqueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseBloque; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterElseBloque(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitElseBloque(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitElseBloque(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseBloqueContext elseBloque() throws RecognitionException {
		ElseBloqueContext _localctx = new ElseBloqueContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_elseBloque);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			linstrucciones();
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

	public static class DoEstructuraContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public LinstruccionesContext linstrucciones() {
			return getRuleContext(LinstruccionesContext.class,0);
		}
		public TerminalNode END() { return getToken(GramaticaParser.END, 0); }
		public DoEstructuraContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doEstructura; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDoEstructura(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDoEstructura(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDoEstructura(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoEstructuraContext doEstructura() throws RecognitionException {
		DoEstructuraContext _localctx = new DoEstructuraContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_doEstructura);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			match(T__19);
			setState(287);
			match(ID);
			setState(288);
			match(T__3);
			setState(289);
			expr(0);
			setState(290);
			match(T__4);
			setState(291);
			expr(0);
			setState(292);
			match(T__4);
			setState(293);
			expr(0);
			setState(294);
			linstrucciones();
			setState(295);
			match(END);
			setState(296);
			match(T__19);
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

	public static class DoWhileEstrcturaContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public LinstruccionesContext linstrucciones() {
			return getRuleContext(LinstruccionesContext.class,0);
		}
		public TerminalNode END() { return getToken(GramaticaParser.END, 0); }
		public DoWhileEstrcturaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileEstrctura; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDoWhileEstrctura(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDoWhileEstrctura(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDoWhileEstrctura(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileEstrcturaContext doWhileEstrctura() throws RecognitionException {
		DoWhileEstrcturaContext _localctx = new DoWhileEstrcturaContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_doWhileEstrctura);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			match(T__19);
			setState(299);
			match(T__20);
			setState(300);
			match(T__6);
			setState(301);
			expr(0);
			setState(302);
			match(T__7);
			setState(303);
			linstrucciones();
			setState(304);
			match(END);
			setState(305);
			match(T__19);
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

	public static class ControlExitContext extends ParserRuleContext {
		public ControlExitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_controlExit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterControlExit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitControlExit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitControlExit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ControlExitContext controlExit() throws RecognitionException {
		ControlExitContext _localctx = new ControlExitContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_controlExit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(T__21);
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

	public static class ControlCycleContext extends ParserRuleContext {
		public ControlCycleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_controlCycle; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterControlCycle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitControlCycle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitControlCycle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ControlCycleContext controlCycle() throws RecognitionException {
		ControlCycleContext _localctx = new ControlCycleContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_controlCycle);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			match(T__22);
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

	public static class EtiquetaDoContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(GramaticaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GramaticaParser.ID, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public LinstruccionesContext linstrucciones() {
			return getRuleContext(LinstruccionesContext.class,0);
		}
		public TerminalNode END() { return getToken(GramaticaParser.END, 0); }
		public EtiquetaDoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_etiquetaDo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterEtiquetaDo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitEtiquetaDo(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitEtiquetaDo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EtiquetaDoContext etiquetaDo() throws RecognitionException {
		EtiquetaDoContext _localctx = new EtiquetaDoContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_etiquetaDo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
			match(ID);
			setState(312);
			match(T__13);
			setState(313);
			match(T__19);
			setState(314);
			match(ID);
			setState(315);
			match(T__3);
			setState(316);
			expr(0);
			setState(317);
			match(T__4);
			setState(318);
			expr(0);
			setState(319);
			match(T__4);
			setState(320);
			expr(0);
			setState(321);
			linstrucciones();
			setState(322);
			match(END);
			setState(323);
			match(T__19);
			setState(324);
			match(ID);
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

	public static class EtiquetaDoWhileContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(GramaticaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GramaticaParser.ID, i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public LinstruccionesContext linstrucciones() {
			return getRuleContext(LinstruccionesContext.class,0);
		}
		public TerminalNode END() { return getToken(GramaticaParser.END, 0); }
		public EtiquetaDoWhileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_etiquetaDoWhile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterEtiquetaDoWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitEtiquetaDoWhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitEtiquetaDoWhile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EtiquetaDoWhileContext etiquetaDoWhile() throws RecognitionException {
		EtiquetaDoWhileContext _localctx = new EtiquetaDoWhileContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_etiquetaDoWhile);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			match(ID);
			setState(327);
			match(T__13);
			setState(328);
			match(T__19);
			setState(329);
			match(T__20);
			setState(330);
			match(T__6);
			setState(331);
			expr(0);
			setState(332);
			match(T__7);
			setState(333);
			linstrucciones();
			setState(334);
			match(END);
			setState(335);
			match(T__19);
			setState(336);
			match(ID);
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

	public static class SubroutineContext extends ParserRuleContext {
		public Token id1;
		public Token id2;
		public ImplicitContext implicit() {
			return getRuleContext(ImplicitContext.class,0);
		}
		public LinstruccionesContext linstrucciones() {
			return getRuleContext(LinstruccionesContext.class,0);
		}
		public TerminalNode END() { return getToken(GramaticaParser.END, 0); }
		public List<TerminalNode> ID() { return getTokens(GramaticaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GramaticaParser.ID, i);
		}
		public LexprContext lexpr() {
			return getRuleContext(LexprContext.class,0);
		}
		public LdeclPContext ldeclP() {
			return getRuleContext(LdeclPContext.class,0);
		}
		public SubroutineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subroutine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterSubroutine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitSubroutine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitSubroutine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubroutineContext subroutine() throws RecognitionException {
		SubroutineContext _localctx = new SubroutineContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_subroutine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(338);
			match(T__23);
			setState(339);
			((SubroutineContext)_localctx).id1 = match(ID);
			setState(340);
			match(T__6);
			setState(342);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__30) | (1L << T__31) | (1L << SIZEARR) | (1L << CHTR1) | (1L << CHTR2) | (1L << LOGICL) | (1L << ID) | (1L << INT) | (1L << REAL) | (1L << STRING1))) != 0)) {
				{
				setState(341);
				lexpr();
				}
			}

			setState(344);
			match(T__7);
			setState(345);
			implicit();
			setState(347);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(346);
				ldeclP();
				}
				break;
			}
			setState(349);
			linstrucciones();
			setState(350);
			match(END);
			setState(351);
			match(T__23);
			setState(352);
			((SubroutineContext)_localctx).id2 = match(ID);
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

	public static class LdeclPContext extends ParserRuleContext {
		public List<DeclParametrosContext> declParametros() {
			return getRuleContexts(DeclParametrosContext.class);
		}
		public DeclParametrosContext declParametros(int i) {
			return getRuleContext(DeclParametrosContext.class,i);
		}
		public LdeclPContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldeclP; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterLdeclP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitLdeclP(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitLdeclP(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LdeclPContext ldeclP() throws RecognitionException {
		LdeclPContext _localctx = new LdeclPContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_ldeclP);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(355); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(354);
					declParametros();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(357); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class DeclParametrosContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ValorParametrosContext valorParametros() {
			return getRuleContext(ValorParametrosContext.class,0);
		}
		public DeclParametrosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declParametros; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDeclParametros(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDeclParametros(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitDeclParametros(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclParametrosContext declParametros() throws RecognitionException {
		DeclParametrosContext _localctx = new DeclParametrosContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_declParametros);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(359);
			type();
			setState(360);
			match(T__4);
			setState(361);
			match(T__24);
			setState(362);
			match(T__6);
			setState(363);
			match(T__25);
			setState(364);
			match(T__7);
			setState(365);
			match(T__2);
			setState(366);
			valorParametros();
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

	public static class ValorParametrosContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ValorParametrosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valorParametros; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterValorParametros(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitValorParametros(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitValorParametros(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValorParametrosContext valorParametros() throws RecognitionException {
		ValorParametrosContext _localctx = new ValorParametrosContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_valorParametros);
		int _la;
		try {
			setState(378);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(368);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(369);
				match(ID);
				setState(370);
				match(T__6);
				setState(371);
				expr(0);
				setState(374);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(372);
					match(T__4);
					setState(373);
					expr(0);
					}
				}

				setState(376);
				match(T__7);
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

	public static class CallContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public LexprContext lexpr() {
			return getRuleContext(LexprContext.class,0);
		}
		public CallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallContext call() throws RecognitionException {
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(380);
			match(T__26);
			setState(381);
			match(ID);
			setState(382);
			match(T__6);
			setState(384);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__30) | (1L << T__31) | (1L << SIZEARR) | (1L << CHTR1) | (1L << CHTR2) | (1L << LOGICL) | (1L << ID) | (1L << INT) | (1L << REAL) | (1L << STRING1))) != 0)) {
				{
				setState(383);
				lexpr();
				}
			}

			setState(386);
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

	public static class FuncionContext extends ParserRuleContext {
		public Token id1;
		public Token idr;
		public Token id2;
		public ImplicitContext implicit() {
			return getRuleContext(ImplicitContext.class,0);
		}
		public LinstruccionesContext linstrucciones() {
			return getRuleContext(LinstruccionesContext.class,0);
		}
		public TerminalNode END() { return getToken(GramaticaParser.END, 0); }
		public List<TerminalNode> ID() { return getTokens(GramaticaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GramaticaParser.ID, i);
		}
		public LexprContext lexpr() {
			return getRuleContext(LexprContext.class,0);
		}
		public LdeclPContext ldeclP() {
			return getRuleContext(LdeclPContext.class,0);
		}
		public FuncionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterFuncion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitFuncion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitFuncion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncionContext funcion() throws RecognitionException {
		FuncionContext _localctx = new FuncionContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_funcion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(388);
			match(T__27);
			setState(389);
			((FuncionContext)_localctx).id1 = match(ID);
			setState(390);
			match(T__6);
			setState(392);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__30) | (1L << T__31) | (1L << SIZEARR) | (1L << CHTR1) | (1L << CHTR2) | (1L << LOGICL) | (1L << ID) | (1L << INT) | (1L << REAL) | (1L << STRING1))) != 0)) {
				{
				setState(391);
				lexpr();
				}
			}

			setState(394);
			match(T__7);
			setState(395);
			match(T__28);
			setState(396);
			match(T__6);
			setState(397);
			((FuncionContext)_localctx).idr = match(ID);
			setState(398);
			match(T__7);
			setState(399);
			implicit();
			setState(401);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(400);
				ldeclP();
				}
				break;
			}
			setState(403);
			linstrucciones();
			setState(404);
			match(END);
			setState(405);
			match(T__27);
			setState(406);
			((FuncionContext)_localctx).id2 = match(ID);
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
	public static class CharExprContext extends ExprContext {
		public Token chtr;
		public TerminalNode CHTR1() { return getToken(GramaticaParser.CHTR1, 0); }
		public TerminalNode CHTR2() { return getToken(GramaticaParser.CHTR2, 0); }
		public TerminalNode STRING1() { return getToken(GramaticaParser.STRING1, 0); }
		public CharExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterCharExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitCharExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitCharExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdAtomContext extends ExprContext {
		public Token idd;
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public IdAtomContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterIdAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitIdAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitIdAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ComplexExprContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ComplexExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterComplexExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitComplexExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitComplexExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncExprContext extends ExprContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public LexprContext lexpr() {
			return getRuleContext(LexprContext.class,0);
		}
		public FuncExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterFuncExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitFuncExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitFuncExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OpExprContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public OpExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterOpExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitOpExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitOpExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayAccesoExprContext extends ExprContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public LexprContext lexpr() {
			return getRuleContext(LexprContext.class,0);
		}
		public ArrayAccesoExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterArrayAccesoExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitArrayAccesoExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitArrayAccesoExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalExprContext extends ExprContext {
		public Token logicl;
		public TerminalNode LOGICL() { return getToken(GramaticaParser.LOGICL, 0); }
		public LogicalExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterLogicalExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitLogicalExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitLogicalExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RealExprContext extends ExprContext {
		public Token real;
		public TerminalNode REAL() { return getToken(GramaticaParser.REAL, 0); }
		public RealExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterRealExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitRealExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitRealExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrExprContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public OrExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RelationalExprContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MAYOR() { return getToken(GramaticaParser.MAYOR, 0); }
		public TerminalNode MENOR() { return getToken(GramaticaParser.MENOR, 0); }
		public TerminalNode MAYORQUE() { return getToken(GramaticaParser.MAYORQUE, 0); }
		public TerminalNode MENORQUE() { return getToken(GramaticaParser.MENORQUE, 0); }
		public RelationalExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterRelationalExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitRelationalExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitRelationalExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegExprContext extends ExprContext {
		public Token integ;
		public TerminalNode INT() { return getToken(GramaticaParser.INT, 0); }
		public IntegExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterIntegExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitIntegExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitIntegExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotExprContext extends ExprContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NotExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryMinusExprContext extends ExprContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryMinusExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterUnaryMinusExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitUnaryMinusExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitUnaryMinusExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParentExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParentExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterParentExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitParentExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitParentExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PowExprContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public PowExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterPowExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitPowExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitPowExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualityExprContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode IGUALIGUAL() { return getToken(GramaticaParser.IGUALIGUAL, 0); }
		public TerminalNode DIFIGUAL() { return getToken(GramaticaParser.DIFIGUAL, 0); }
		public EqualityExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterEqualityExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitEqualityExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitEqualityExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SizeArrContext extends ExprContext {
		public Token sizearr;
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public TerminalNode SIZEARR() { return getToken(GramaticaParser.SIZEARR, 0); }
		public SizeArrContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterSizeArr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitSizeArr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitSizeArr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndExprContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public AndExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitAndExpr(this);
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
		int _startState = 78;
		enterRecursionRule(_localctx, 78, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(443);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				_localctx = new UnaryMinusExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(409);
				((UnaryMinusExprContext)_localctx).op = match(T__30);
				setState(410);
				expr(18);
				}
				break;
			case 2:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(411);
				((NotExprContext)_localctx).op = match(T__31);
				setState(412);
				expr(17);
				}
				break;
			case 3:
				{
				_localctx = new ComplexExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(413);
				match(T__6);
				setState(414);
				((ComplexExprContext)_localctx).left = expr(0);
				setState(415);
				((ComplexExprContext)_localctx).op = match(T__4);
				setState(416);
				((ComplexExprContext)_localctx).right = expr(0);
				setState(417);
				match(T__7);
				}
				break;
			case 4:
				{
				_localctx = new ParentExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(419);
				match(T__6);
				setState(420);
				expr(0);
				setState(421);
				match(T__7);
				}
				break;
			case 5:
				{
				_localctx = new ArrayAccesoExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(423);
				match(ID);
				setState(424);
				match(T__10);
				setState(425);
				lexpr();
				setState(426);
				match(T__11);
				}
				break;
			case 6:
				{
				_localctx = new FuncExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(428);
				match(ID);
				setState(429);
				match(T__6);
				setState(431);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__30) | (1L << T__31) | (1L << SIZEARR) | (1L << CHTR1) | (1L << CHTR2) | (1L << LOGICL) | (1L << ID) | (1L << INT) | (1L << REAL) | (1L << STRING1))) != 0)) {
					{
					setState(430);
					lexpr();
					}
				}

				setState(433);
				match(T__7);
				}
				break;
			case 7:
				{
				_localctx = new CharExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(434);
				((CharExprContext)_localctx).chtr = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CHTR1) | (1L << CHTR2) | (1L << STRING1))) != 0)) ) {
					((CharExprContext)_localctx).chtr = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 8:
				{
				_localctx = new SizeArrContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(435);
				((SizeArrContext)_localctx).sizearr = match(SIZEARR);
				setState(436);
				match(T__6);
				setState(437);
				match(ID);
				setState(438);
				match(T__7);
				}
				break;
			case 9:
				{
				_localctx = new LogicalExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(439);
				((LogicalExprContext)_localctx).logicl = match(LOGICL);
				}
				break;
			case 10:
				{
				_localctx = new IdAtomContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(440);
				((IdAtomContext)_localctx).idd = match(ID);
				}
				break;
			case 11:
				{
				_localctx = new IntegExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(441);
				((IntegExprContext)_localctx).integ = match(INT);
				}
				break;
			case 12:
				{
				_localctx = new RealExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(442);
				((RealExprContext)_localctx).real = match(REAL);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(468);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(466);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						_localctx = new PowExprContext(new ExprContext(_parentctx, _parentState));
						((PowExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(445);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(446);
						((PowExprContext)_localctx).op = match(T__29);
						setState(447);
						((PowExprContext)_localctx).right = expr(20);
						}
						break;
					case 2:
						{
						_localctx = new OpExprContext(new ExprContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(448);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(449);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__32 || _la==T__33) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(450);
						((OpExprContext)_localctx).right = expr(17);
						}
						break;
					case 3:
						{
						_localctx = new OpExprContext(new ExprContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(451);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(452);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__30 || _la==T__34) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(453);
						((OpExprContext)_localctx).right = expr(16);
						}
						break;
					case 4:
						{
						_localctx = new RelationalExprContext(new ExprContext(_parentctx, _parentState));
						((RelationalExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(454);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(455);
						((RelationalExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MENORQUE) | (1L << MAYORQUE) | (1L << MENOR) | (1L << MAYOR))) != 0)) ) {
							((RelationalExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(456);
						((RelationalExprContext)_localctx).right = expr(15);
						}
						break;
					case 5:
						{
						_localctx = new EqualityExprContext(new ExprContext(_parentctx, _parentState));
						((EqualityExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(457);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(458);
						((EqualityExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==IGUALIGUAL || _la==DIFIGUAL) ) {
							((EqualityExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(459);
						((EqualityExprContext)_localctx).right = expr(14);
						}
						break;
					case 6:
						{
						_localctx = new AndExprContext(new ExprContext(_parentctx, _parentState));
						((AndExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(460);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(461);
						((AndExprContext)_localctx).op = match(T__35);
						setState(462);
						((AndExprContext)_localctx).right = expr(13);
						}
						break;
					case 7:
						{
						_localctx = new OrExprContext(new ExprContext(_parentctx, _parentState));
						((OrExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(463);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(464);
						((OrExprContext)_localctx).op = match(T__36);
						setState(465);
						((OrExprContext)_localctx).right = expr(12);
						}
						break;
					}
					} 
				}
				setState(470);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
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

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GramaticaVisitor ) return ((GramaticaVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(471);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41))) != 0)) ) {
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 39:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 19);
		case 1:
			return precpred(_ctx, 16);
		case 2:
			return precpred(_ctx, 15);
		case 3:
			return precpred(_ctx, 14);
		case 4:
			return precpred(_ctx, 13);
		case 5:
			return precpred(_ctx, 12);
		case 6:
			return precpred(_ctx, 11);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001>\u01da\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0005"+
		"\u0001X\b\u0001\n\u0001\f\u0001[\t\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0003\u0002r\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005\u0088"+
		"\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u008d\b\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0003\u0007\u0095\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u00a3\b\t\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0003\n\u00a9\b\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\f\u0005\f\u00b1\b\f\n\f\f\f\u00b4\t\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u00bc\b\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0003\u000e\u00c8\b\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u00e2\b\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0003\u0013\u00ec\b\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u00f9\b\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u010b\b\u0018\n\u0018\f\u0018"+
		"\u010e\t\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u0112\b\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001"+
		"\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001 \u0001 \u0001 \u0001"+
		" \u0001 \u0001 \u0001 \u0001 \u0001 \u0001 \u0001!\u0001!\u0001!\u0001"+
		"!\u0003!\u0157\b!\u0001!\u0001!\u0001!\u0003!\u015c\b!\u0001!\u0001!\u0001"+
		"!\u0001!\u0001!\u0001\"\u0004\"\u0164\b\"\u000b\"\f\"\u0165\u0001#\u0001"+
		"#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001$\u0001$\u0001"+
		"$\u0001$\u0001$\u0001$\u0003$\u0177\b$\u0001$\u0001$\u0003$\u017b\b$\u0001"+
		"%\u0001%\u0001%\u0001%\u0003%\u0181\b%\u0001%\u0001%\u0001&\u0001&\u0001"+
		"&\u0001&\u0003&\u0189\b&\u0001&\u0001&\u0001&\u0001&\u0001&\u0001&\u0001"+
		"&\u0003&\u0192\b&\u0001&\u0001&\u0001&\u0001&\u0001&\u0001\'\u0001\'\u0001"+
		"\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001"+
		"\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001"+
		"\'\u0001\'\u0001\'\u0003\'\u01b0\b\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001"+
		"\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0003\'\u01bc\b\'\u0001\'\u0001"+
		"\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001"+
		"\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001"+
		"\'\u0001\'\u0005\'\u01d3\b\'\n\'\f\'\u01d6\t\'\u0001(\u0001(\u0001(\u0000"+
		"\u0001N)\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018"+
		"\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJLNP\u0000\u0006\u0002\u00006"+
		"7<<\u0001\u0000!\"\u0002\u0000\u001f\u001f##\u0001\u000003\u0001\u0000"+
		"45\u0001\u0000&*\u01ed\u0000R\u0001\u0000\u0000\u0000\u0002U\u0001\u0000"+
		"\u0000\u0000\u0004q\u0001\u0000\u0000\u0000\u0006s\u0001\u0000\u0000\u0000"+
		"\b{\u0001\u0000\u0000\u0000\n\u0087\u0001\u0000\u0000\u0000\f\u0089\u0001"+
		"\u0000\u0000\u0000\u000e\u0094\u0001\u0000\u0000\u0000\u0010\u0096\u0001"+
		"\u0000\u0000\u0000\u0012\u00a2\u0001\u0000\u0000\u0000\u0014\u00a8\u0001"+
		"\u0000\u0000\u0000\u0016\u00aa\u0001\u0000\u0000\u0000\u0018\u00ad\u0001"+
		"\u0000\u0000\u0000\u001a\u00b5\u0001\u0000\u0000\u0000\u001c\u00c1\u0001"+
		"\u0000\u0000\u0000\u001e\u00cb\u0001\u0000\u0000\u0000 \u00ce\u0001\u0000"+
		"\u0000\u0000\"\u00d4\u0001\u0000\u0000\u0000$\u00e1\u0001\u0000\u0000"+
		"\u0000&\u00e3\u0001\u0000\u0000\u0000(\u00ef\u0001\u0000\u0000\u0000*"+
		"\u00f2\u0001\u0000\u0000\u0000,\u00fd\u0001\u0000\u0000\u0000.\u0102\u0001"+
		"\u0000\u0000\u00000\u0105\u0001\u0000\u0000\u00002\u0116\u0001\u0000\u0000"+
		"\u00004\u011c\u0001\u0000\u0000\u00006\u011e\u0001\u0000\u0000\u00008"+
		"\u012a\u0001\u0000\u0000\u0000:\u0133\u0001\u0000\u0000\u0000<\u0135\u0001"+
		"\u0000\u0000\u0000>\u0137\u0001\u0000\u0000\u0000@\u0146\u0001\u0000\u0000"+
		"\u0000B\u0152\u0001\u0000\u0000\u0000D\u0163\u0001\u0000\u0000\u0000F"+
		"\u0167\u0001\u0000\u0000\u0000H\u017a\u0001\u0000\u0000\u0000J\u017c\u0001"+
		"\u0000\u0000\u0000L\u0184\u0001\u0000\u0000\u0000N\u01bb\u0001\u0000\u0000"+
		"\u0000P\u01d7\u0001\u0000\u0000\u0000RS\u0003\u0002\u0001\u0000ST\u0005"+
		"\u0000\u0000\u0001T\u0001\u0001\u0000\u0000\u0000UY\u0003\u0004\u0002"+
		"\u0000VX\u0003\u0004\u0002\u0000WV\u0001\u0000\u0000\u0000X[\u0001\u0000"+
		"\u0000\u0000YW\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000\u0000Z\u0003"+
		"\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000\\r\u0003\u0006\u0003"+
		"\u0000]r\u0003\n\u0005\u0000^r\u0003\u0010\b\u0000_r\u0003\u0012\t\u0000"+
		"`r\u0003\u001a\r\u0000ar\u0003\u001c\u000e\u0000br\u0003 \u0010\u0000"+
		"cr\u0003\"\u0011\u0000dr\u0003&\u0013\u0000er\u0003*\u0015\u0000fr\u0003"+
		",\u0016\u0000gr\u00030\u0018\u0000hr\u00036\u001b\u0000ir\u00038\u001c"+
		"\u0000jr\u0003:\u001d\u0000kr\u0003<\u001e\u0000lr\u0003>\u001f\u0000"+
		"mr\u0003@ \u0000nr\u0003B!\u0000or\u0003J%\u0000pr\u0003L&\u0000q\\\u0001"+
		"\u0000\u0000\u0000q]\u0001\u0000\u0000\u0000q^\u0001\u0000\u0000\u0000"+
		"q_\u0001\u0000\u0000\u0000q`\u0001\u0000\u0000\u0000qa\u0001\u0000\u0000"+
		"\u0000qb\u0001\u0000\u0000\u0000qc\u0001\u0000\u0000\u0000qd\u0001\u0000"+
		"\u0000\u0000qe\u0001\u0000\u0000\u0000qf\u0001\u0000\u0000\u0000qg\u0001"+
		"\u0000\u0000\u0000qh\u0001\u0000\u0000\u0000qi\u0001\u0000\u0000\u0000"+
		"qj\u0001\u0000\u0000\u0000qk\u0001\u0000\u0000\u0000ql\u0001\u0000\u0000"+
		"\u0000qm\u0001\u0000\u0000\u0000qn\u0001\u0000\u0000\u0000qo\u0001\u0000"+
		"\u0000\u0000qp\u0001\u0000\u0000\u0000r\u0005\u0001\u0000\u0000\u0000"+
		"st\u0005+\u0000\u0000tu\u00059\u0000\u0000uv\u0003\b\u0004\u0000vw\u0003"+
		"\u0002\u0001\u0000wx\u0005,\u0000\u0000xy\u0005+\u0000\u0000yz\u00059"+
		"\u0000\u0000z\u0007\u0001\u0000\u0000\u0000{|\u0005\u0001\u0000\u0000"+
		"|}\u0005\u0002\u0000\u0000}\t\u0001\u0000\u0000\u0000~\u007f\u0003P(\u0000"+
		"\u007f\u0080\u0005\u0003\u0000\u0000\u0080\u0081\u0003\f\u0006\u0000\u0081"+
		"\u0082\u0003\u000e\u0007\u0000\u0082\u0088\u0001\u0000\u0000\u0000\u0083"+
		"\u0084\u0003P(\u0000\u0084\u0085\u0005\u0003\u0000\u0000\u0085\u0086\u0003"+
		"\f\u0006\u0000\u0086\u0088\u0001\u0000\u0000\u0000\u0087~\u0001\u0000"+
		"\u0000\u0000\u0087\u0083\u0001\u0000\u0000\u0000\u0088\u000b\u0001\u0000"+
		"\u0000\u0000\u0089\u008c\u00059\u0000\u0000\u008a\u008b\u0005\u0004\u0000"+
		"\u0000\u008b\u008d\u0003N\'\u0000\u008c\u008a\u0001\u0000\u0000\u0000"+
		"\u008c\u008d\u0001\u0000\u0000\u0000\u008d\r\u0001\u0000\u0000\u0000\u008e"+
		"\u008f\u0005\u0005\u0000\u0000\u008f\u0090\u0003\f\u0006\u0000\u0090\u0091"+
		"\u0003\u000e\u0007\u0000\u0091\u0095\u0001\u0000\u0000\u0000\u0092\u0093"+
		"\u0005\u0005\u0000\u0000\u0093\u0095\u0003\f\u0006\u0000\u0094\u008e\u0001"+
		"\u0000\u0000\u0000\u0094\u0092\u0001\u0000\u0000\u0000\u0095\u000f\u0001"+
		"\u0000\u0000\u0000\u0096\u0097\u00059\u0000\u0000\u0097\u0098\u0005\u0004"+
		"\u0000\u0000\u0098\u0099\u0003N\'\u0000\u0099\u0011\u0001\u0000\u0000"+
		"\u0000\u009a\u009b\u0005-\u0000\u0000\u009b\u009c\u0005\u0006\u0000\u0000"+
		"\u009c\u009d\u0003N\'\u0000\u009d\u009e\u0003\u0014\n\u0000\u009e\u00a3"+
		"\u0001\u0000\u0000\u0000\u009f\u00a0\u0005-\u0000\u0000\u00a0\u00a1\u0005"+
		"\u0006\u0000\u0000\u00a1\u00a3\u0003N\'\u0000\u00a2\u009a\u0001\u0000"+
		"\u0000\u0000\u00a2\u009f\u0001\u0000\u0000\u0000\u00a3\u0013\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a5\u0003\u0016\u000b\u0000\u00a5\u00a6\u0003\u0014"+
		"\n\u0000\u00a6\u00a9\u0001\u0000\u0000\u0000\u00a7\u00a9\u0003\u0016\u000b"+
		"\u0000\u00a8\u00a4\u0001\u0000\u0000\u0000\u00a8\u00a7\u0001\u0000\u0000"+
		"\u0000\u00a9\u0015\u0001\u0000\u0000\u0000\u00aa\u00ab\u0005\u0005\u0000"+
		"\u0000\u00ab\u00ac\u0003N\'\u0000\u00ac\u0017\u0001\u0000\u0000\u0000"+
		"\u00ad\u00b2\u0003N\'\u0000\u00ae\u00af\u0005\u0005\u0000\u0000\u00af"+
		"\u00b1\u0003N\'\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000\u00b1\u00b4"+
		"\u0001\u0000\u0000\u0000\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b2\u00b3"+
		"\u0001\u0000\u0000\u0000\u00b3\u0019\u0001\u0000\u0000\u0000\u00b4\u00b2"+
		"\u0001\u0000\u0000\u0000\u00b5\u00b6\u0003P(\u0000\u00b6\u00b7\u0005\u0005"+
		"\u0000\u0000\u00b7\u00b8\u0005.\u0000\u0000\u00b8\u00b9\u0005\u0007\u0000"+
		"\u0000\u00b9\u00bb\u0003N\'\u0000\u00ba\u00bc\u0003\u001e\u000f\u0000"+
		"\u00bb\u00ba\u0001\u0000\u0000\u0000\u00bb\u00bc\u0001\u0000\u0000\u0000"+
		"\u00bc\u00bd\u0001\u0000\u0000\u0000\u00bd\u00be\u0005\b\u0000\u0000\u00be"+
		"\u00bf\u0005\u0003\u0000\u0000\u00bf\u00c0\u00059\u0000\u0000\u00c0\u001b"+
		"\u0001\u0000\u0000\u0000\u00c1\u00c2\u0003P(\u0000\u00c2\u00c3\u0005\u0003"+
		"\u0000\u0000\u00c3\u00c4\u00059\u0000\u0000\u00c4\u00c5\u0005\u0007\u0000"+
		"\u0000\u00c5\u00c7\u0003N\'\u0000\u00c6\u00c8\u0003\u001e\u000f\u0000"+
		"\u00c7\u00c6\u0001\u0000\u0000\u0000\u00c7\u00c8\u0001\u0000\u0000\u0000"+
		"\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c9\u00ca\u0005\b\u0000\u0000\u00ca"+
		"\u001d\u0001\u0000\u0000\u0000\u00cb\u00cc\u0005\u0005\u0000\u0000\u00cc"+
		"\u00cd\u0003N\'\u0000\u00cd\u001f\u0001\u0000\u0000\u0000\u00ce\u00cf"+
		"\u00059\u0000\u0000\u00cf\u00d0\u0005\u0004\u0000\u0000\u00d0\u00d1\u0005"+
		"\t\u0000\u0000\u00d1\u00d2\u0003\u0018\f\u0000\u00d2\u00d3\u0005\n\u0000"+
		"\u0000\u00d3!\u0001\u0000\u0000\u0000\u00d4\u00d5\u00059\u0000\u0000\u00d5"+
		"\u00d6\u0005\u000b\u0000\u0000\u00d6\u00d7\u0003\u0018\f\u0000\u00d7\u00d8"+
		"\u0005\f\u0000\u0000\u00d8\u00d9\u0005\u0004\u0000\u0000\u00d9\u00da\u0003"+
		"N\'\u0000\u00da#\u0001\u0000\u0000\u0000\u00db\u00dc\u0005\u0005\u0000"+
		"\u0000\u00dc\u00dd\u0003N\'\u0000\u00dd\u00de\u0003$\u0012\u0000\u00de"+
		"\u00e2\u0001\u0000\u0000\u0000\u00df\u00e0\u0005\u0005\u0000\u0000\u00e0"+
		"\u00e2\u0003N\'\u0000\u00e1\u00db\u0001\u0000\u0000\u0000\u00e1\u00df"+
		"\u0001\u0000\u0000\u0000\u00e2%\u0001\u0000\u0000\u0000\u00e3\u00e4\u0003"+
		"P(\u0000\u00e4\u00e5\u0005\u0005\u0000\u0000\u00e5\u00e6\u0005\r\u0000"+
		"\u0000\u00e6\u00e7\u0005\u0003\u0000\u0000\u00e7\u00e8\u00059\u0000\u0000"+
		"\u00e8\u00e9\u0005\u0007\u0000\u0000\u00e9\u00eb\u0005\u000e\u0000\u0000"+
		"\u00ea\u00ec\u0003(\u0014\u0000\u00eb\u00ea\u0001\u0000\u0000\u0000\u00eb"+
		"\u00ec\u0001\u0000\u0000\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000\u00ed"+
		"\u00ee\u0005\b\u0000\u0000\u00ee\'\u0001\u0000\u0000\u0000\u00ef\u00f0"+
		"\u0005\u0005\u0000\u0000\u00f0\u00f1\u0005\u000e\u0000\u0000\u00f1)\u0001"+
		"\u0000\u0000\u0000\u00f2\u00f3\u0005\u000f\u0000\u0000\u00f3\u00f4\u0005"+
		"\u0007\u0000\u0000\u00f4\u00f5\u00059\u0000\u0000\u00f5\u00f6\u0005\u0007"+
		"\u0000\u0000\u00f6\u00f8\u0003N\'\u0000\u00f7\u00f9\u0003.\u0017\u0000"+
		"\u00f8\u00f7\u0001\u0000\u0000\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000"+
		"\u00f9\u00fa\u0001\u0000\u0000\u0000\u00fa\u00fb\u0005\b\u0000\u0000\u00fb"+
		"\u00fc\u0005\b\u0000\u0000\u00fc+\u0001\u0000\u0000\u0000\u00fd\u00fe"+
		"\u0005\u0010\u0000\u0000\u00fe\u00ff\u0005\u0007\u0000\u0000\u00ff\u0100"+
		"\u00059\u0000\u0000\u0100\u0101\u0005\b\u0000\u0000\u0101-\u0001\u0000"+
		"\u0000\u0000\u0102\u0103\u0005\u0005\u0000\u0000\u0103\u0104\u0003N\'"+
		"\u0000\u0104/\u0001\u0000\u0000\u0000\u0105\u0106\u0005\u0011\u0000\u0000"+
		"\u0106\u010c\u00032\u0019\u0000\u0107\u0108\u0005\u0012\u0000\u0000\u0108"+
		"\u0109\u0005\u0011\u0000\u0000\u0109\u010b\u00032\u0019\u0000\u010a\u0107"+
		"\u0001\u0000\u0000\u0000\u010b\u010e\u0001\u0000\u0000\u0000\u010c\u010a"+
		"\u0001\u0000\u0000\u0000\u010c\u010d\u0001\u0000\u0000\u0000\u010d\u0111"+
		"\u0001\u0000\u0000\u0000\u010e\u010c\u0001\u0000\u0000\u0000\u010f\u0110"+
		"\u0005\u0012\u0000\u0000\u0110\u0112\u00034\u001a\u0000\u0111\u010f\u0001"+
		"\u0000\u0000\u0000\u0111\u0112\u0001\u0000\u0000\u0000\u0112\u0113\u0001"+
		"\u0000\u0000\u0000\u0113\u0114\u0005,\u0000\u0000\u0114\u0115\u0005\u0011"+
		"\u0000\u0000\u01151\u0001\u0000\u0000\u0000\u0116\u0117\u0005\u0007\u0000"+
		"\u0000\u0117\u0118\u0003N\'\u0000\u0118\u0119\u0005\b\u0000\u0000\u0119"+
		"\u011a\u0005\u0013\u0000\u0000\u011a\u011b\u0003\u0002\u0001\u0000\u011b"+
		"3\u0001\u0000\u0000\u0000\u011c\u011d\u0003\u0002\u0001\u0000\u011d5\u0001"+
		"\u0000\u0000\u0000\u011e\u011f\u0005\u0014\u0000\u0000\u011f\u0120\u0005"+
		"9\u0000\u0000\u0120\u0121\u0005\u0004\u0000\u0000\u0121\u0122\u0003N\'"+
		"\u0000\u0122\u0123\u0005\u0005\u0000\u0000\u0123\u0124\u0003N\'\u0000"+
		"\u0124\u0125\u0005\u0005\u0000\u0000\u0125\u0126\u0003N\'\u0000\u0126"+
		"\u0127\u0003\u0002\u0001\u0000\u0127\u0128\u0005,\u0000\u0000\u0128\u0129"+
		"\u0005\u0014\u0000\u0000\u01297\u0001\u0000\u0000\u0000\u012a\u012b\u0005"+
		"\u0014\u0000\u0000\u012b\u012c\u0005\u0015\u0000\u0000\u012c\u012d\u0005"+
		"\u0007\u0000\u0000\u012d\u012e\u0003N\'\u0000\u012e\u012f\u0005\b\u0000"+
		"\u0000\u012f\u0130\u0003\u0002\u0001\u0000\u0130\u0131\u0005,\u0000\u0000"+
		"\u0131\u0132\u0005\u0014\u0000\u0000\u01329\u0001\u0000\u0000\u0000\u0133"+
		"\u0134\u0005\u0016\u0000\u0000\u0134;\u0001\u0000\u0000\u0000\u0135\u0136"+
		"\u0005\u0017\u0000\u0000\u0136=\u0001\u0000\u0000\u0000\u0137\u0138\u0005"+
		"9\u0000\u0000\u0138\u0139\u0005\u000e\u0000\u0000\u0139\u013a\u0005\u0014"+
		"\u0000\u0000\u013a\u013b\u00059\u0000\u0000\u013b\u013c\u0005\u0004\u0000"+
		"\u0000\u013c\u013d\u0003N\'\u0000\u013d\u013e\u0005\u0005\u0000\u0000"+
		"\u013e\u013f\u0003N\'\u0000\u013f\u0140\u0005\u0005\u0000\u0000\u0140"+
		"\u0141\u0003N\'\u0000\u0141\u0142\u0003\u0002\u0001\u0000\u0142\u0143"+
		"\u0005,\u0000\u0000\u0143\u0144\u0005\u0014\u0000\u0000\u0144\u0145\u0005"+
		"9\u0000\u0000\u0145?\u0001\u0000\u0000\u0000\u0146\u0147\u00059\u0000"+
		"\u0000\u0147\u0148\u0005\u000e\u0000\u0000\u0148\u0149\u0005\u0014\u0000"+
		"\u0000\u0149\u014a\u0005\u0015\u0000\u0000\u014a\u014b\u0005\u0007\u0000"+
		"\u0000\u014b\u014c\u0003N\'\u0000\u014c\u014d\u0005\b\u0000\u0000\u014d"+
		"\u014e\u0003\u0002\u0001\u0000\u014e\u014f\u0005,\u0000\u0000\u014f\u0150"+
		"\u0005\u0014\u0000\u0000\u0150\u0151\u00059\u0000\u0000\u0151A\u0001\u0000"+
		"\u0000\u0000\u0152\u0153\u0005\u0018\u0000\u0000\u0153\u0154\u00059\u0000"+
		"\u0000\u0154\u0156\u0005\u0007\u0000\u0000\u0155\u0157\u0003\u0018\f\u0000"+
		"\u0156\u0155\u0001\u0000\u0000\u0000\u0156\u0157\u0001\u0000\u0000\u0000"+
		"\u0157\u0158\u0001\u0000\u0000\u0000\u0158\u0159\u0005\b\u0000\u0000\u0159"+
		"\u015b\u0003\b\u0004\u0000\u015a\u015c\u0003D\"\u0000\u015b\u015a\u0001"+
		"\u0000\u0000\u0000\u015b\u015c\u0001\u0000\u0000\u0000\u015c\u015d\u0001"+
		"\u0000\u0000\u0000\u015d\u015e\u0003\u0002\u0001\u0000\u015e\u015f\u0005"+
		",\u0000\u0000\u015f\u0160\u0005\u0018\u0000\u0000\u0160\u0161\u00059\u0000"+
		"\u0000\u0161C\u0001\u0000\u0000\u0000\u0162\u0164\u0003F#\u0000\u0163"+
		"\u0162\u0001\u0000\u0000\u0000\u0164\u0165\u0001\u0000\u0000\u0000\u0165"+
		"\u0163\u0001\u0000\u0000\u0000\u0165\u0166\u0001\u0000\u0000\u0000\u0166"+
		"E\u0001\u0000\u0000\u0000\u0167\u0168\u0003P(\u0000\u0168\u0169\u0005"+
		"\u0005\u0000\u0000\u0169\u016a\u0005\u0019\u0000\u0000\u016a\u016b\u0005"+
		"\u0007\u0000\u0000\u016b\u016c\u0005\u001a\u0000\u0000\u016c\u016d\u0005"+
		"\b\u0000\u0000\u016d\u016e\u0005\u0003\u0000\u0000\u016e\u016f\u0003H"+
		"$\u0000\u016fG\u0001\u0000\u0000\u0000\u0170\u017b\u00059\u0000\u0000"+
		"\u0171\u0172\u00059\u0000\u0000\u0172\u0173\u0005\u0007\u0000\u0000\u0173"+
		"\u0176\u0003N\'\u0000\u0174\u0175\u0005\u0005\u0000\u0000\u0175\u0177"+
		"\u0003N\'\u0000\u0176\u0174\u0001\u0000\u0000\u0000\u0176\u0177\u0001"+
		"\u0000\u0000\u0000\u0177\u0178\u0001\u0000\u0000\u0000\u0178\u0179\u0005"+
		"\b\u0000\u0000\u0179\u017b\u0001\u0000\u0000\u0000\u017a\u0170\u0001\u0000"+
		"\u0000\u0000\u017a\u0171\u0001\u0000\u0000\u0000\u017bI\u0001\u0000\u0000"+
		"\u0000\u017c\u017d\u0005\u001b\u0000\u0000\u017d\u017e\u00059\u0000\u0000"+
		"\u017e\u0180\u0005\u0007\u0000\u0000\u017f\u0181\u0003\u0018\f\u0000\u0180"+
		"\u017f\u0001\u0000\u0000\u0000\u0180\u0181\u0001\u0000\u0000\u0000\u0181"+
		"\u0182\u0001\u0000\u0000\u0000\u0182\u0183\u0005\b\u0000\u0000\u0183K"+
		"\u0001\u0000\u0000\u0000\u0184\u0185\u0005\u001c\u0000\u0000\u0185\u0186"+
		"\u00059\u0000\u0000\u0186\u0188\u0005\u0007\u0000\u0000\u0187\u0189\u0003"+
		"\u0018\f\u0000\u0188\u0187\u0001\u0000\u0000\u0000\u0188\u0189\u0001\u0000"+
		"\u0000\u0000\u0189\u018a\u0001\u0000\u0000\u0000\u018a\u018b\u0005\b\u0000"+
		"\u0000\u018b\u018c\u0005\u001d\u0000\u0000\u018c\u018d\u0005\u0007\u0000"+
		"\u0000\u018d\u018e\u00059\u0000\u0000\u018e\u018f\u0005\b\u0000\u0000"+
		"\u018f\u0191\u0003\b\u0004\u0000\u0190\u0192\u0003D\"\u0000\u0191\u0190"+
		"\u0001\u0000\u0000\u0000\u0191\u0192\u0001\u0000\u0000\u0000\u0192\u0193"+
		"\u0001\u0000\u0000\u0000\u0193\u0194\u0003\u0002\u0001\u0000\u0194\u0195"+
		"\u0005,\u0000\u0000\u0195\u0196\u0005\u001c\u0000\u0000\u0196\u0197\u0005"+
		"9\u0000\u0000\u0197M\u0001\u0000\u0000\u0000\u0198\u0199\u0006\'\uffff"+
		"\uffff\u0000\u0199\u019a\u0005\u001f\u0000\u0000\u019a\u01bc\u0003N\'"+
		"\u0012\u019b\u019c\u0005 \u0000\u0000\u019c\u01bc\u0003N\'\u0011\u019d"+
		"\u019e\u0005\u0007\u0000\u0000\u019e\u019f\u0003N\'\u0000\u019f\u01a0"+
		"\u0005\u0005\u0000\u0000\u01a0\u01a1\u0003N\'\u0000\u01a1\u01a2\u0005"+
		"\b\u0000\u0000\u01a2\u01bc\u0001\u0000\u0000\u0000\u01a3\u01a4\u0005\u0007"+
		"\u0000\u0000\u01a4\u01a5\u0003N\'\u0000\u01a5\u01a6\u0005\b\u0000\u0000"+
		"\u01a6\u01bc\u0001\u0000\u0000\u0000\u01a7\u01a8\u00059\u0000\u0000\u01a8"+
		"\u01a9\u0005\u000b\u0000\u0000\u01a9\u01aa\u0003\u0018\f\u0000\u01aa\u01ab"+
		"\u0005\f\u0000\u0000\u01ab\u01bc\u0001\u0000\u0000\u0000\u01ac\u01ad\u0005"+
		"9\u0000\u0000\u01ad\u01af\u0005\u0007\u0000\u0000\u01ae\u01b0\u0003\u0018"+
		"\f\u0000\u01af\u01ae\u0001\u0000\u0000\u0000\u01af\u01b0\u0001\u0000\u0000"+
		"\u0000\u01b0\u01b1\u0001\u0000\u0000\u0000\u01b1\u01bc\u0005\b\u0000\u0000"+
		"\u01b2\u01bc\u0007\u0000\u0000\u0000\u01b3\u01b4\u0005/\u0000\u0000\u01b4"+
		"\u01b5\u0005\u0007\u0000\u0000\u01b5\u01b6\u00059\u0000\u0000\u01b6\u01bc"+
		"\u0005\b\u0000\u0000\u01b7\u01bc\u00058\u0000\u0000\u01b8\u01bc\u0005"+
		"9\u0000\u0000\u01b9\u01bc\u0005:\u0000\u0000\u01ba\u01bc\u0005;\u0000"+
		"\u0000\u01bb\u0198\u0001\u0000\u0000\u0000\u01bb\u019b\u0001\u0000\u0000"+
		"\u0000\u01bb\u019d\u0001\u0000\u0000\u0000\u01bb\u01a3\u0001\u0000\u0000"+
		"\u0000\u01bb\u01a7\u0001\u0000\u0000\u0000\u01bb\u01ac\u0001\u0000\u0000"+
		"\u0000\u01bb\u01b2\u0001\u0000\u0000\u0000\u01bb\u01b3\u0001\u0000\u0000"+
		"\u0000\u01bb\u01b7\u0001\u0000\u0000\u0000\u01bb\u01b8\u0001\u0000\u0000"+
		"\u0000\u01bb\u01b9\u0001\u0000\u0000\u0000\u01bb\u01ba\u0001\u0000\u0000"+
		"\u0000\u01bc\u01d4\u0001\u0000\u0000\u0000\u01bd\u01be\n\u0013\u0000\u0000"+
		"\u01be\u01bf\u0005\u001e\u0000\u0000\u01bf\u01d3\u0003N\'\u0014\u01c0"+
		"\u01c1\n\u0010\u0000\u0000\u01c1\u01c2\u0007\u0001\u0000\u0000\u01c2\u01d3"+
		"\u0003N\'\u0011\u01c3\u01c4\n\u000f\u0000\u0000\u01c4\u01c5\u0007\u0002"+
		"\u0000\u0000\u01c5\u01d3\u0003N\'\u0010\u01c6\u01c7\n\u000e\u0000\u0000"+
		"\u01c7\u01c8\u0007\u0003\u0000\u0000\u01c8\u01d3\u0003N\'\u000f\u01c9"+
		"\u01ca\n\r\u0000\u0000\u01ca\u01cb\u0007\u0004\u0000\u0000\u01cb\u01d3"+
		"\u0003N\'\u000e\u01cc\u01cd\n\f\u0000\u0000\u01cd\u01ce\u0005$\u0000\u0000"+
		"\u01ce\u01d3\u0003N\'\r\u01cf\u01d0\n\u000b\u0000\u0000\u01d0\u01d1\u0005"+
		"%\u0000\u0000\u01d1\u01d3\u0003N\'\f\u01d2\u01bd\u0001\u0000\u0000\u0000"+
		"\u01d2\u01c0\u0001\u0000\u0000\u0000\u01d2\u01c3\u0001\u0000\u0000\u0000"+
		"\u01d2\u01c6\u0001\u0000\u0000\u0000\u01d2\u01c9\u0001\u0000\u0000\u0000"+
		"\u01d2\u01cc\u0001\u0000\u0000\u0000\u01d2\u01cf\u0001\u0000\u0000\u0000"+
		"\u01d3\u01d6\u0001\u0000\u0000\u0000\u01d4\u01d2\u0001\u0000\u0000\u0000"+
		"\u01d4\u01d5\u0001\u0000\u0000\u0000\u01d5O\u0001\u0000\u0000\u0000\u01d6"+
		"\u01d4\u0001\u0000\u0000\u0000\u01d7\u01d8\u0007\u0005\u0000\u0000\u01d8"+
		"Q\u0001\u0000\u0000\u0000\u001bYq\u0087\u008c\u0094\u00a2\u00a8\u00b2"+
		"\u00bb\u00c7\u00e1\u00eb\u00f8\u010c\u0111\u0156\u015b\u0165\u0176\u017a"+
		"\u0180\u0188\u0191\u01af\u01bb\u01d2\u01d4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}