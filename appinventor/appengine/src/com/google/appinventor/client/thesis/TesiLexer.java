// Generated from TesiLexer.g4 by ANTLR 4.8
package com.google.appinventor.client.thesis;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TesiLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, WHEN=2, IF=3, THEN=4, ELSE=5, THROW=6, ARTICLE=7, VERB=8, 
		COLON=9, SEMICOLON=10, OPEN=11, CLOSE=12, ACTION=13, STRING=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WHITESPACE", "WHEN", "IF", "THEN", "ELSE", "THROW", "ARTICLE", "VERB", 
			"COLON", "SEMICOLON", "OPEN", "CLOSE", "ACTION", "STRING"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'when'", "'if'", "'then'", "'else'", "'throw'", null, "'is'", 
			"','", "';'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "WHEN", "IF", "THEN", "ELSE", "THROW", "ARTICLE", 
			"VERB", "COLON", "SEMICOLON", "OPEN", "CLOSE", "ACTION", "STRING"
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


	public TesiLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "TesiLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20\u0080\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\2\3\3\3\3\3"+
		"\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\5\b@\n\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\5\16z\n\16\3\17\6\17}\n\17\r\17\16\17~"+
		"\2\2\20\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17"+
		"\35\20\3\2\4\5\2\13\f\17\17\"\"\5\2\62;C\\c|\2\u0086\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\3\37\3\2\2\2\5#\3\2\2\2\7(\3\2\2\2\t+\3\2\2\2\13\60"+
		"\3\2\2\2\r\65\3\2\2\2\17?\3\2\2\2\21A\3\2\2\2\23D\3\2\2\2\25F\3\2\2\2"+
		"\27H\3\2\2\2\31J\3\2\2\2\33y\3\2\2\2\35|\3\2\2\2\37 \t\2\2\2 !\3\2\2\2"+
		"!\"\b\2\2\2\"\4\3\2\2\2#$\7y\2\2$%\7j\2\2%&\7g\2\2&\'\7p\2\2\'\6\3\2\2"+
		"\2()\7k\2\2)*\7h\2\2*\b\3\2\2\2+,\7v\2\2,-\7j\2\2-.\7g\2\2./\7p\2\2/\n"+
		"\3\2\2\2\60\61\7g\2\2\61\62\7n\2\2\62\63\7u\2\2\63\64\7g\2\2\64\f\3\2"+
		"\2\2\65\66\7v\2\2\66\67\7j\2\2\678\7t\2\289\7q\2\29:\7y\2\2:\16\3\2\2"+
		"\2;<\7v\2\2<=\7j\2\2=@\7g\2\2>@\7c\2\2?;\3\2\2\2?>\3\2\2\2@\20\3\2\2\2"+
		"AB\7k\2\2BC\7u\2\2C\22\3\2\2\2DE\7.\2\2E\24\3\2\2\2FG\7=\2\2G\26\3\2\2"+
		"\2HI\7*\2\2I\30\3\2\2\2JK\7+\2\2K\32\3\2\2\2LM\7j\2\2MN\7k\2\2NO\7f\2"+
		"\2OP\7f\2\2PQ\7g\2\2Qz\7p\2\2RS\7o\2\2ST\7c\2\2TU\7m\2\2UV\7g\2\2VW\7"+
		"\"\2\2WX\7x\2\2XY\7k\2\2YZ\7u\2\2Z[\7k\2\2[\\\7d\2\2\\]\7n\2\2]z\7g\2"+
		"\2^_\7f\2\2_`\7k\2\2`a\7u\2\2ab\7c\2\2bc\7d\2\2cd\7n\2\2de\7g\2\2ez\7"+
		"f\2\2fg\7u\2\2gh\7j\2\2hi\7q\2\2ij\7y\2\2jz\7p\2\2kl\7e\2\2lm\7n\2\2m"+
		"n\7k\2\2no\7e\2\2op\7m\2\2pq\7g\2\2qz\7f\2\2rs\7x\2\2st\7k\2\2tu\7u\2"+
		"\2uv\7k\2\2vw\7d\2\2wx\7n\2\2xz\7g\2\2yL\3\2\2\2yR\3\2\2\2y^\3\2\2\2y"+
		"f\3\2\2\2yk\3\2\2\2yr\3\2\2\2z\34\3\2\2\2{}\t\3\2\2|{\3\2\2\2}~\3\2\2"+
		"\2~|\3\2\2\2~\177\3\2\2\2\177\36\3\2\2\2\6\2?y~\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}