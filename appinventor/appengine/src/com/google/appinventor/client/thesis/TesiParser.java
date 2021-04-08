// Generated from TesiParser.g4 by ANTLR 4.9.2
package com.google.appinventor.client.thesis;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TesiParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, WHEN=2, IF=3, AND=4, THEN=5, ELSE=6, THROW=7, ARTICLE=8, 
		VERB=9, COLON=10, SEMICOLON=11, OPEN=12, CLOSE=13, ACTION=14, ACTION_PAGE=15, 
		STRING=16;
	public static final int
		RULE_block = 0, RULE_event = 1, RULE_condition = 2, RULE_action_body = 3, 
		RULE_action = 4, RULE_anotherAction = 5, RULE_statement = 6, RULE_open_page = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"block", "event", "condition", "action_body", "action", "anotherAction", 
			"statement", "open_page"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'when'", "'if'", "'and'", "'then'", "'else'", "'throw'", 
			null, "'is'", "','", "';'", "'('", "')'", null, "'open'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "WHEN", "IF", "AND", "THEN", "ELSE", "THROW", "ARTICLE", 
			"VERB", "COLON", "SEMICOLON", "OPEN", "CLOSE", "ACTION", "ACTION_PAGE", 
			"STRING"
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

	public static class BlockContext extends ParserRuleContext {
		public EventContext event() {
			return getRuleContext(EventContext.class,0);
		}
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public List<AnotherActionContext> anotherAction() {
			return getRuleContexts(AnotherActionContext.class);
		}
		public AnotherActionContext anotherAction(int i) {
			return getRuleContext(AnotherActionContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			event();
			setState(18);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IF) {
				{
				setState(17);
				condition();
				}
			}

			setState(20);
			action();
			setState(24);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(21);
				anotherAction();
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

	public static class EventContext extends ParserRuleContext {
		public TerminalNode WHEN() { return getToken(TesiParser.WHEN, 0); }
		public TerminalNode STRING() { return getToken(TesiParser.STRING, 0); }
		public TerminalNode VERB() { return getToken(TesiParser.VERB, 0); }
		public TerminalNode ACTION() { return getToken(TesiParser.ACTION, 0); }
		public EventContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_event; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterEvent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitEvent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitEvent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EventContext event() throws RecognitionException {
		EventContext _localctx = new EventContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_event);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			match(WHEN);
			setState(28);
			match(STRING);
			setState(29);
			match(VERB);
			setState(30);
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

	public static class ConditionContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(TesiParser.IF, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			match(IF);
			setState(33);
			statement();
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

	public static class Action_bodyContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode THROW() { return getToken(TesiParser.THROW, 0); }
		public TerminalNode OPEN() { return getToken(TesiParser.OPEN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode CLOSE() { return getToken(TesiParser.CLOSE, 0); }
		public Open_pageContext open_page() {
			return getRuleContext(Open_pageContext.class,0);
		}
		public Action_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterAction_body(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitAction_body(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitAction_body(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Action_bodyContext action_body() throws RecognitionException {
		Action_bodyContext _localctx = new Action_bodyContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_action_body);
		int _la;
		try {
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(35);
				statement();
				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==THROW) {
					{
					setState(36);
					match(THROW);
					setState(37);
					match(OPEN);
					setState(38);
					block();
					setState(39);
					match(CLOSE);
					}
				}

				}
				break;
			case ACTION_PAGE:
				enterOuterAlt(_localctx, 2);
				{
				setState(43);
				open_page();
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

	public static class ActionContext extends ParserRuleContext {
		public TerminalNode THEN() { return getToken(TesiParser.THEN, 0); }
		public Action_bodyContext action_body() {
			return getRuleContext(Action_bodyContext.class,0);
		}
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_action);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(THEN);
			setState(47);
			action_body();
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

	public static class AnotherActionContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(TesiParser.AND, 0); }
		public Action_bodyContext action_body() {
			return getRuleContext(Action_bodyContext.class,0);
		}
		public AnotherActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anotherAction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterAnotherAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitAnotherAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitAnotherAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnotherActionContext anotherAction() throws RecognitionException {
		AnotherActionContext _localctx = new AnotherActionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_anotherAction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(AND);
			setState(50);
			action_body();
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
		public TerminalNode STRING() { return getToken(TesiParser.STRING, 0); }
		public TerminalNode VERB() { return getToken(TesiParser.VERB, 0); }
		public TerminalNode ACTION() { return getToken(TesiParser.ACTION, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(STRING);
			setState(53);
			match(VERB);
			setState(54);
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

	public static class Open_pageContext extends ParserRuleContext {
		public TerminalNode ACTION_PAGE() { return getToken(TesiParser.ACTION_PAGE, 0); }
		public TerminalNode STRING() { return getToken(TesiParser.STRING, 0); }
		public Open_pageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_open_page; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterOpen_page(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitOpen_page(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitOpen_page(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Open_pageContext open_page() throws RecognitionException {
		Open_pageContext _localctx = new Open_pageContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_open_page);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(ACTION_PAGE);
			setState(57);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\22>\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\5\2\25\n\2"+
		"\3\2\3\2\7\2\31\n\2\f\2\16\2\34\13\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\5\5,\n\5\3\5\5\5/\n\5\3\6\3\6\3\6\3\7\3\7\3\7\3"+
		"\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\2\2\n\2\4\6\b\n\f\16\20\2\2\29\2\22\3\2"+
		"\2\2\4\35\3\2\2\2\6\"\3\2\2\2\b.\3\2\2\2\n\60\3\2\2\2\f\63\3\2\2\2\16"+
		"\66\3\2\2\2\20:\3\2\2\2\22\24\5\4\3\2\23\25\5\6\4\2\24\23\3\2\2\2\24\25"+
		"\3\2\2\2\25\26\3\2\2\2\26\32\5\n\6\2\27\31\5\f\7\2\30\27\3\2\2\2\31\34"+
		"\3\2\2\2\32\30\3\2\2\2\32\33\3\2\2\2\33\3\3\2\2\2\34\32\3\2\2\2\35\36"+
		"\7\4\2\2\36\37\7\22\2\2\37 \7\13\2\2 !\7\20\2\2!\5\3\2\2\2\"#\7\5\2\2"+
		"#$\5\16\b\2$\7\3\2\2\2%+\5\16\b\2&\'\7\t\2\2\'(\7\16\2\2()\5\2\2\2)*\7"+
		"\17\2\2*,\3\2\2\2+&\3\2\2\2+,\3\2\2\2,/\3\2\2\2-/\5\20\t\2.%\3\2\2\2."+
		"-\3\2\2\2/\t\3\2\2\2\60\61\7\7\2\2\61\62\5\b\5\2\62\13\3\2\2\2\63\64\7"+
		"\6\2\2\64\65\5\b\5\2\65\r\3\2\2\2\66\67\7\22\2\2\678\7\13\2\289\7\20\2"+
		"\29\17\3\2\2\2:;\7\21\2\2;<\7\22\2\2<\21\3\2\2\2\6\24\32+.";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}