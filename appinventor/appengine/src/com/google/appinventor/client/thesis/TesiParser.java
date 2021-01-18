package com.google.appinventor.client.thesis;// Generated from TesiParser.g4 by ANTLR 4.9

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TesiParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, IF=2, THEN=3, ELSE=4, ARTICLE=5, TYPE=6, VERB=7, COLON=8, 
		SEMICOLON=9, ASSIGN=10, ACTION=11, STRING=12;
	public static final int
		RULE_blocks = 0, RULE_if_else = 1, RULE_view = 2, RULE_statement = 3, 
		RULE_assignment = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"blocks", "if_else", "view", "statement", "assignment"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'then'", "'else'", null, null, "' is'", "','", "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "IF", "THEN", "ELSE", "ARTICLE", "TYPE", "VERB", 
			"COLON", "SEMICOLON", "ASSIGN", "ACTION", "STRING"
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
	public String getGrammarFileName() { return "TesiParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TesiParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class BlocksContext extends ParserRuleContext {
		public If_elseContext if_else() {
			return getRuleContext(If_elseContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ViewContext view() {
			return getRuleContext(ViewContext.class,0);
		}
		public BlocksContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blocks; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener) ((TesiParserListener)listener).enterBlocks(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener) ((TesiParserListener)listener).exitBlocks(this);
		}
	}

	public final BlocksContext blocks() throws RecognitionException {
		BlocksContext _localctx = new BlocksContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_blocks);
		try {
			setState(14);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(10);
				if_else();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(11);
				assignment();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(12);
				statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(13);
				view();
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

	public static class If_elseContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(TesiParser.IF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode THEN() { return getToken(TesiParser.THEN, 0); }
		public List<TerminalNode> ELSE() { return getTokens(TesiParser.ELSE); }
		public TerminalNode ELSE(int i) {
			return getToken(TesiParser.ELSE, i);
		}
		public If_elseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_else; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener) ((TesiParserListener)listener).enterIf_else(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener) ((TesiParserListener)listener).exitIf_else(this);
		}
	}

	public final If_elseContext if_else() throws RecognitionException {
		If_elseContext _localctx = new If_elseContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_if_else);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			match(IF);
			setState(17);
			statement();
			setState(18);
			match(THEN);
			setState(19);
			statement();
			setState(24);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ELSE) {
				{
				{
				setState(20);
				match(ELSE);
				setState(21);
				statement();
				}
				}
				setState(26);
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

	public static class ViewContext extends ParserRuleContext {
		public TerminalNode ARTICLE() { return getToken(TesiParser.ARTICLE, 0); }
		public TerminalNode TYPE() { return getToken(TesiParser.TYPE, 0); }
		public TerminalNode STRING() { return getToken(TesiParser.STRING, 0); }
		public ViewContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_view; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener) ((TesiParserListener)listener).enterView(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener) ((TesiParserListener)listener).exitView(this);
		}
	}

	public final ViewContext view() throws RecognitionException {
		ViewContext _localctx = new ViewContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_view);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			match(ARTICLE);
			setState(28);
			match(TYPE);
			setState(29);
			match(STRING);
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

	public static class StatementContext extends ParserRuleContext {
		public ViewContext view() {
			return getRuleContext(ViewContext.class,0);
		}
		public TerminalNode VERB() { return getToken(TesiParser.VERB, 0); }
		public TerminalNode ACTION() { return getToken(TesiParser.ACTION, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener) ((TesiParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener) ((TesiParserListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			view();
			setState(32);
			match(VERB);
			setState(33);
			match(ACTION);
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

	public static class AssignmentContext extends ParserRuleContext {
		public ViewContext view() {
			return getRuleContext(ViewContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(TesiParser.ASSIGN, 0); }
		public TerminalNode STRING() { return getToken(TesiParser.STRING, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener) ((TesiParserListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener) ((TesiParserListener)listener).exitAssignment(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			view();
			setState(36);
			match(ASSIGN);
			setState(37);
			match(STRING);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\16*\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\2\5\2\21\n\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\7\3\31\n\3\f\3\16\3\34\13\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3"+
		"\6\3\6\3\6\3\6\3\6\2\2\7\2\4\6\b\n\2\2\2(\2\20\3\2\2\2\4\22\3\2\2\2\6"+
		"\35\3\2\2\2\b!\3\2\2\2\n%\3\2\2\2\f\21\5\4\3\2\r\21\5\n\6\2\16\21\5\b"+
		"\5\2\17\21\5\6\4\2\20\f\3\2\2\2\20\r\3\2\2\2\20\16\3\2\2\2\20\17\3\2\2"+
		"\2\21\3\3\2\2\2\22\23\7\4\2\2\23\24\5\b\5\2\24\25\7\5\2\2\25\32\5\b\5"+
		"\2\26\27\7\6\2\2\27\31\5\b\5\2\30\26\3\2\2\2\31\34\3\2\2\2\32\30\3\2\2"+
		"\2\32\33\3\2\2\2\33\5\3\2\2\2\34\32\3\2\2\2\35\36\7\7\2\2\36\37\7\b\2"+
		"\2\37 \7\16\2\2 \7\3\2\2\2!\"\5\6\4\2\"#\7\t\2\2#$\7\r\2\2$\t\3\2\2\2"+
		"%&\5\6\4\2&\'\7\f\2\2\'(\7\16\2\2(\13\3\2\2\2\4\20\32";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}