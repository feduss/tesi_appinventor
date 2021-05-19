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
		VERB=9, COLON=10, SEMICOLON=11, OPEN=12, CLOSE=13, ACTION=14, TYPE=15, 
		ACTION_PAGE=16, STRING=17;
	public static final int
		RULE_block = 0, RULE_event = 1, RULE_condition = 2, RULE_action_body = 3, 
		RULE_action = 4, RULE_anotherAction = 5, RULE_condition_statement = 6, 
		RULE_action_statement = 7, RULE_open_page = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"block", "event", "condition", "action_body", "action", "anotherAction", 
			"condition_statement", "action_statement", "open_page"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'when'", "'if'", "'and'", "'then'", "'else'", "'throw'", 
			null, null, "','", "';'", "'('", "')'", null, null, "'open'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "WHEN", "IF", "AND", "THEN", "ELSE", "THROW", "ARTICLE", 
			"VERB", "COLON", "SEMICOLON", "OPEN", "CLOSE", "ACTION", "TYPE", "ACTION_PAGE", 
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
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			event();
			setState(20);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IF) {
				{
				setState(19);
				condition();
				}
			}

			setState(22);
			action();
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(23);
				anotherAction();
				}
				}
				setState(28);
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
	}

	public final EventContext event() throws RecognitionException {
		EventContext _localctx = new EventContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_event);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			match(WHEN);
			setState(30);
			match(STRING);
			setState(31);
			match(VERB);
			setState(32);
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
		public Condition_statementContext condition_statement() {
			return getRuleContext(Condition_statementContext.class,0);
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
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(IF);
			setState(35);
			condition_statement();
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
		public Action_statementContext action_statement() {
			return getRuleContext(Action_statementContext.class,0);
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
	}

	public final Action_bodyContext action_body() throws RecognitionException {
		Action_bodyContext _localctx = new Action_bodyContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_action_body);
		int _la;
		try {
			setState(46);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TYPE:
				enterOuterAlt(_localctx, 1);
				{
				setState(37);
				action_statement();
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==THROW) {
					{
					setState(38);
					match(THROW);
					setState(39);
					match(OPEN);
					setState(40);
					block();
					setState(41);
					match(CLOSE);
					}
				}

				}
				break;
			case ACTION_PAGE:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
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
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_action);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(THEN);
			setState(49);
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
	}

	public final AnotherActionContext anotherAction() throws RecognitionException {
		AnotherActionContext _localctx = new AnotherActionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_anotherAction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(AND);
			setState(52);
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

	public static class Condition_statementContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(TesiParser.STRING, 0); }
		public TerminalNode VERB() { return getToken(TesiParser.VERB, 0); }
		public TerminalNode ACTION() { return getToken(TesiParser.ACTION, 0); }
		public Condition_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterCondition_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitCondition_statement(this);
		}
	}

	public final Condition_statementContext condition_statement() throws RecognitionException {
		Condition_statementContext _localctx = new Condition_statementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_condition_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(STRING);
			setState(55);
			match(VERB);
			setState(56);
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

	public static class Action_statementContext extends ParserRuleContext {
		public TerminalNode TYPE() { return getToken(TesiParser.TYPE, 0); }
		public List<TerminalNode> STRING() { return getTokens(TesiParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(TesiParser.STRING, i);
		}
		public TerminalNode VERB() { return getToken(TesiParser.VERB, 0); }
		public Action_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterAction_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitAction_statement(this);
		}
	}

	public final Action_statementContext action_statement() throws RecognitionException {
		Action_statementContext _localctx = new Action_statementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_action_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(TYPE);
			setState(59);
			match(STRING);
			setState(60);
			match(VERB);
			setState(61);
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
	}

	public final Open_pageContext open_page() throws RecognitionException {
		Open_pageContext _localctx = new Open_pageContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_open_page);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(ACTION_PAGE);
			setState(64);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23E\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\5\2"+
		"\27\n\2\3\2\3\2\7\2\33\n\2\f\2\16\2\36\13\2\3\3\3\3\3\3\3\3\3\3\3\4\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\5\5.\n\5\3\5\5\5\61\n\5\3\6\3\6\3\6\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\2\2\13\2"+
		"\4\6\b\n\f\16\20\22\2\2\2?\2\24\3\2\2\2\4\37\3\2\2\2\6$\3\2\2\2\b\60\3"+
		"\2\2\2\n\62\3\2\2\2\f\65\3\2\2\2\168\3\2\2\2\20<\3\2\2\2\22A\3\2\2\2\24"+
		"\26\5\4\3\2\25\27\5\6\4\2\26\25\3\2\2\2\26\27\3\2\2\2\27\30\3\2\2\2\30"+
		"\34\5\n\6\2\31\33\5\f\7\2\32\31\3\2\2\2\33\36\3\2\2\2\34\32\3\2\2\2\34"+
		"\35\3\2\2\2\35\3\3\2\2\2\36\34\3\2\2\2\37 \7\4\2\2 !\7\23\2\2!\"\7\13"+
		"\2\2\"#\7\20\2\2#\5\3\2\2\2$%\7\5\2\2%&\5\16\b\2&\7\3\2\2\2\'-\5\20\t"+
		"\2()\7\t\2\2)*\7\16\2\2*+\5\2\2\2+,\7\17\2\2,.\3\2\2\2-(\3\2\2\2-.\3\2"+
		"\2\2.\61\3\2\2\2/\61\5\22\n\2\60\'\3\2\2\2\60/\3\2\2\2\61\t\3\2\2\2\62"+
		"\63\7\7\2\2\63\64\5\b\5\2\64\13\3\2\2\2\65\66\7\6\2\2\66\67\5\b\5\2\67"+
		"\r\3\2\2\289\7\23\2\29:\7\13\2\2:;\7\20\2\2;\17\3\2\2\2<=\7\21\2\2=>\7"+
		"\23\2\2>?\7\13\2\2?@\7\23\2\2@\21\3\2\2\2AB\7\22\2\2BC\7\23\2\2C\23\3"+
		"\2\2\2\6\26\34-\60";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}