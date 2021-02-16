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
		WHITESPACE=1, WHEN=2, IF=3, THEN=4, ELSE=5, ARTICLE=6, VERB=7, COLON=8, 
		SEMICOLON=9, ACTION=10, STRING=11;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WHITESPACE", "WHEN", "IF", "THEN", "ELSE", "ARTICLE", "VERB", "COLON", 
			"SEMICOLON", "ACTION", "STRING"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'when'", "'if'", "'then'", "'else'", null, "'is'", "','", 
			"';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "WHEN", "IF", "THEN", "ELSE", "ARTICLE", "VERB", 
			"COLON", "SEMICOLON", "ACTION", "STRING"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\rp\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\5\7\64\n\7\3\b\3\b\3\b"+
		"\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\5\13j\n\13\3\f\6\fm\n\f\r\f\16\fn\2\2\r"+
		"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\3\2\4\5\2\13\f\17"+
		"\17\"\"\5\2\62;C\\c|\2v\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\3\31\3\2\2\2\5\35\3\2\2\2\7\"\3\2\2\2\t%\3\2\2\2"+
		"\13*\3\2\2\2\r\63\3\2\2\2\17\65\3\2\2\2\218\3\2\2\2\23:\3\2\2\2\25i\3"+
		"\2\2\2\27l\3\2\2\2\31\32\t\2\2\2\32\33\3\2\2\2\33\34\b\2\2\2\34\4\3\2"+
		"\2\2\35\36\7y\2\2\36\37\7j\2\2\37 \7g\2\2 !\7p\2\2!\6\3\2\2\2\"#\7k\2"+
		"\2#$\7h\2\2$\b\3\2\2\2%&\7v\2\2&\'\7j\2\2\'(\7g\2\2()\7p\2\2)\n\3\2\2"+
		"\2*+\7g\2\2+,\7n\2\2,-\7u\2\2-.\7g\2\2.\f\3\2\2\2/\60\7v\2\2\60\61\7j"+
		"\2\2\61\64\7g\2\2\62\64\7c\2\2\63/\3\2\2\2\63\62\3\2\2\2\64\16\3\2\2\2"+
		"\65\66\7k\2\2\66\67\7u\2\2\67\20\3\2\2\289\7.\2\29\22\3\2\2\2:;\7=\2\2"+
		";\24\3\2\2\2<=\7j\2\2=>\7k\2\2>?\7f\2\2?@\7f\2\2@A\7g\2\2Aj\7p\2\2BC\7"+
		"o\2\2CD\7c\2\2DE\7m\2\2EF\7g\2\2FG\7\"\2\2GH\7x\2\2HI\7k\2\2IJ\7u\2\2"+
		"JK\7k\2\2KL\7d\2\2LM\7n\2\2Mj\7g\2\2NO\7f\2\2OP\7k\2\2PQ\7u\2\2QR\7c\2"+
		"\2RS\7d\2\2ST\7n\2\2TU\7g\2\2Uj\7f\2\2VW\7u\2\2WX\7j\2\2XY\7q\2\2YZ\7"+
		"y\2\2Zj\7p\2\2[\\\7e\2\2\\]\7n\2\2]^\7k\2\2^_\7e\2\2_`\7m\2\2`a\7g\2\2"+
		"aj\7f\2\2bc\7x\2\2cd\7k\2\2de\7u\2\2ef\7k\2\2fg\7d\2\2gh\7n\2\2hj\7g\2"+
		"\2i<\3\2\2\2iB\3\2\2\2iN\3\2\2\2iV\3\2\2\2i[\3\2\2\2ib\3\2\2\2j\26\3\2"+
		"\2\2km\t\3\2\2lk\3\2\2\2mn\3\2\2\2nl\3\2\2\2no\3\2\2\2o\30\3\2\2\2\6\2"+
		"\63in\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}