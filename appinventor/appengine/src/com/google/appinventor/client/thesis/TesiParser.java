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
		WHITESPACE=1, WHEN=2, IF=3, AND=4, OR=5, NOT=6, THEN=7, ELSE=8, THROW=9, 
		ARTICLE=10, VERB=11, SET=12, CALL=13, ACTION_WHEN_OBJ=14, ACTION_IF_OBJ=15, 
		ACTION_SET_OBJ=16, ACTION_CALL_OBJ=17, ACTION_OPEN_OBJ=18, COLON=19, SEMICOLON=20, 
		OPEN_BRACKET=21, CLOSE_BRACKET=22, OPEN=23, STRING=24;
	public static final int
		RULE_block = 0, RULE_event = 1, RULE_condition = 2, RULE_another_condition = 3, 
		RULE_condition_statement = 4, RULE_action = 5, RULE_another_action = 6, 
		RULE_action_statement = 7, RULE_value = 8, RULE_subj = 9, RULE_subj_type = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"block", "event", "condition", "another_condition", "condition_statement", 
			"action", "another_action", "action_statement", "value", "subj", "subj_type"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'when'", "'if'", "'AND'", "'OR'", "'not'", "'then'", "'else'", 
			"'throw'", null, "'is'", "'set'", "'call'", null, null, null, null, "'another screen with name'", 
			"','", "';'", "'('", "')'", "'open'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "WHEN", "IF", "AND", "OR", "NOT", "THEN", "ELSE", 
			"THROW", "ARTICLE", "VERB", "SET", "CALL", "ACTION_WHEN_OBJ", "ACTION_IF_OBJ", 
			"ACTION_SET_OBJ", "ACTION_CALL_OBJ", "ACTION_OPEN_OBJ", "COLON", "SEMICOLON", 
			"OPEN_BRACKET", "CLOSE_BRACKET", "OPEN", "STRING"
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
			setState(22);
			event();
			setState(24);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IF) {
				{
				setState(23);
				condition();
				}
			}

			setState(26);
			action();
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
		public SubjContext subj() {
			return getRuleContext(SubjContext.class,0);
		}
		public Subj_typeContext subj_type() {
			return getRuleContext(Subj_typeContext.class,0);
		}
		public TerminalNode ACTION_WHEN_OBJ() { return getToken(TesiParser.ACTION_WHEN_OBJ, 0); }
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
			setState(28);
			match(WHEN);
			setState(29);
			subj();
			setState(30);
			subj_type();
			setState(31);
			match(ACTION_WHEN_OBJ);
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
		public List<Another_conditionContext> another_condition() {
			return getRuleContexts(Another_conditionContext.class);
		}
		public Another_conditionContext another_condition(int i) {
			return getRuleContext(Another_conditionContext.class,i);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(IF);
			setState(34);
			condition_statement();
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(35);
				another_condition();
				}
				}
				setState(40);
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

	public static class Another_conditionContext extends ParserRuleContext {
		public Condition_statementContext condition_statement() {
			return getRuleContext(Condition_statementContext.class,0);
		}
		public TerminalNode AND() { return getToken(TesiParser.AND, 0); }
		public TerminalNode OR() { return getToken(TesiParser.OR, 0); }
		public Another_conditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_another_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterAnother_condition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitAnother_condition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitAnother_condition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Another_conditionContext another_condition() throws RecognitionException {
		Another_conditionContext _localctx = new Another_conditionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_another_condition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(41);
			_la = _input.LA(1);
			if ( !(_la==AND || _la==OR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(42);
			condition_statement();
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

	public static class Condition_statementContext extends ParserRuleContext {
		public SubjContext subj() {
			return getRuleContext(SubjContext.class,0);
		}
		public Subj_typeContext subj_type() {
			return getRuleContext(Subj_typeContext.class,0);
		}
		public TerminalNode VERB() { return getToken(TesiParser.VERB, 0); }
		public TerminalNode ACTION_IF_OBJ() { return getToken(TesiParser.ACTION_IF_OBJ, 0); }
		public TerminalNode NOT() { return getToken(TesiParser.NOT, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitCondition_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Condition_statementContext condition_statement() throws RecognitionException {
		Condition_statementContext _localctx = new Condition_statementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_condition_statement);
		int _la;
		try {
			setState(62);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				subj();
				setState(45);
				subj_type();
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(46);
					match(NOT);
					}
				}

				setState(49);
				match(VERB);
				setState(50);
				match(ACTION_IF_OBJ);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(52);
				subj();
				setState(53);
				subj_type();
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(54);
					match(NOT);
					}
				}

				setState(57);
				match(ACTION_IF_OBJ);
				setState(58);
				match(VERB);
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STRING) {
					{
					setState(59);
					value();
					}
				}

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

	public static class ActionContext extends ParserRuleContext {
		public TerminalNode THEN() { return getToken(TesiParser.THEN, 0); }
		public Action_statementContext action_statement() {
			return getRuleContext(Action_statementContext.class,0);
		}
		public List<Another_actionContext> another_action() {
			return getRuleContexts(Another_actionContext.class);
		}
		public Another_actionContext another_action(int i) {
			return getRuleContext(Another_actionContext.class,i);
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
		enterRule(_localctx, 10, RULE_action);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(THEN);
			setState(65);
			action_statement();
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(66);
				another_action();
				}
				}
				setState(71);
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

	public static class Another_actionContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(TesiParser.AND, 0); }
		public Action_statementContext action_statement() {
			return getRuleContext(Action_statementContext.class,0);
		}
		public Another_actionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_another_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterAnother_action(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitAnother_action(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitAnother_action(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Another_actionContext another_action() throws RecognitionException {
		Another_actionContext _localctx = new Another_actionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_another_action);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(AND);
			setState(73);
			action_statement();
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
		public TerminalNode SET() { return getToken(TesiParser.SET, 0); }
		public SubjContext subj() {
			return getRuleContext(SubjContext.class,0);
		}
		public Subj_typeContext subj_type() {
			return getRuleContext(Subj_typeContext.class,0);
		}
		public TerminalNode ACTION_SET_OBJ() { return getToken(TesiParser.ACTION_SET_OBJ, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode CALL() { return getToken(TesiParser.CALL, 0); }
		public TerminalNode ACTION_CALL_OBJ() { return getToken(TesiParser.ACTION_CALL_OBJ, 0); }
		public TerminalNode OPEN() { return getToken(TesiParser.OPEN, 0); }
		public TerminalNode ACTION_OPEN_OBJ() { return getToken(TesiParser.ACTION_OPEN_OBJ, 0); }
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitAction_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Action_statementContext action_statement() throws RecognitionException {
		Action_statementContext _localctx = new Action_statementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_action_statement);
		try {
			setState(90);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SET:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(75);
				match(SET);
				setState(76);
				subj();
				setState(77);
				subj_type();
				setState(78);
				match(ACTION_SET_OBJ);
				setState(79);
				value();
				}
				}
				break;
			case CALL:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(81);
				match(CALL);
				setState(82);
				subj();
				setState(83);
				subj_type();
				setState(84);
				match(ACTION_CALL_OBJ);
				setState(85);
				value();
				}
				}
				break;
			case OPEN:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(87);
				match(OPEN);
				setState(88);
				match(ACTION_OPEN_OBJ);
				setState(89);
				value();
				}
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

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(TesiParser.STRING, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
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

	public static class SubjContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(TesiParser.STRING, 0); }
		public SubjContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subj; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterSubj(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitSubj(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitSubj(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubjContext subj() throws RecognitionException {
		SubjContext _localctx = new SubjContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_subj);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
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

	public static class Subj_typeContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(TesiParser.STRING, 0); }
		public Subj_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subj_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).enterSubj_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TesiParserListener ) ((TesiParserListener)listener).exitSubj_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TesiParserVisitor ) return ((TesiParserVisitor<? extends T>)visitor).visitSubj_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Subj_typeContext subj_type() throws RecognitionException {
		Subj_typeContext _localctx = new Subj_typeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_subj_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\32e\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\3\2\5\2\33\n\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\7\4"+
		"\'\n\4\f\4\16\4*\13\4\3\5\3\5\3\5\3\6\3\6\3\6\5\6\62\n\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\5\6:\n\6\3\6\3\6\3\6\5\6?\n\6\5\6A\n\6\3\7\3\7\3\7\7\7F\n\7"+
		"\f\7\16\7I\13\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\5\t]\n\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\2\2\r\2\4\6"+
		"\b\n\f\16\20\22\24\26\2\3\3\2\6\7\2b\2\30\3\2\2\2\4\36\3\2\2\2\6#\3\2"+
		"\2\2\b+\3\2\2\2\n@\3\2\2\2\fB\3\2\2\2\16J\3\2\2\2\20\\\3\2\2\2\22^\3\2"+
		"\2\2\24`\3\2\2\2\26b\3\2\2\2\30\32\5\4\3\2\31\33\5\6\4\2\32\31\3\2\2\2"+
		"\32\33\3\2\2\2\33\34\3\2\2\2\34\35\5\f\7\2\35\3\3\2\2\2\36\37\7\4\2\2"+
		"\37 \5\24\13\2 !\5\26\f\2!\"\7\20\2\2\"\5\3\2\2\2#$\7\5\2\2$(\5\n\6\2"+
		"%\'\5\b\5\2&%\3\2\2\2\'*\3\2\2\2(&\3\2\2\2()\3\2\2\2)\7\3\2\2\2*(\3\2"+
		"\2\2+,\t\2\2\2,-\5\n\6\2-\t\3\2\2\2./\5\24\13\2/\61\5\26\f\2\60\62\7\b"+
		"\2\2\61\60\3\2\2\2\61\62\3\2\2\2\62\63\3\2\2\2\63\64\7\r\2\2\64\65\7\21"+
		"\2\2\65A\3\2\2\2\66\67\5\24\13\2\679\5\26\f\28:\7\b\2\298\3\2\2\29:\3"+
		"\2\2\2:;\3\2\2\2;<\7\21\2\2<>\7\r\2\2=?\5\22\n\2>=\3\2\2\2>?\3\2\2\2?"+
		"A\3\2\2\2@.\3\2\2\2@\66\3\2\2\2A\13\3\2\2\2BC\7\t\2\2CG\5\20\t\2DF\5\16"+
		"\b\2ED\3\2\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2H\r\3\2\2\2IG\3\2\2\2JK\7"+
		"\6\2\2KL\5\20\t\2L\17\3\2\2\2MN\7\16\2\2NO\5\24\13\2OP\5\26\f\2PQ\7\22"+
		"\2\2QR\5\22\n\2R]\3\2\2\2ST\7\17\2\2TU\5\24\13\2UV\5\26\f\2VW\7\23\2\2"+
		"WX\5\22\n\2X]\3\2\2\2YZ\7\31\2\2Z[\7\24\2\2[]\5\22\n\2\\M\3\2\2\2\\S\3"+
		"\2\2\2\\Y\3\2\2\2]\21\3\2\2\2^_\7\32\2\2_\23\3\2\2\2`a\7\32\2\2a\25\3"+
		"\2\2\2bc\7\32\2\2c\27\3\2\2\2\n\32(\619>@G\\";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}