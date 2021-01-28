// Generated from TesiLexer.g4 by ANTLR 4.8
package com.google.appinventor.client.thesis;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TesiLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, IF=2, THEN=3, ELSE=4, ARTICLE=5, TYPE=6, VERB=7, COLON=8, 
		SEMICOLON=9, ASSIGN=10, ACTION=11, STRING=12;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WHITESPACE", "IF", "THEN", "ELSE", "ARTICLE", "TYPE", "VERB", "COLON", 
			"SEMICOLON", "ASSIGN", "ACTION", "STRING"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\16\u0083\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\5\3&"+
		"\n\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\5\6\66\n"+
		"\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7"+
		"H\n\7\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\5\13U\n\13\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\5\f}\n\f\3\r\6\r\u0080\n\r\r\r\16\r\u0081\2\2\16\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\3\2\4\5\2\13\f\17\17\"\"\5"+
		"\2\62;C\\c|\2\u008c\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\3\33\3\2\2\2\5%\3\2\2\2\7\'\3\2\2\2\t"+
		",\3\2\2\2\13\65\3\2\2\2\rG\3\2\2\2\17I\3\2\2\2\21M\3\2\2\2\23O\3\2\2\2"+
		"\25T\3\2\2\2\27|\3\2\2\2\31\177\3\2\2\2\33\34\t\2\2\2\34\35\3\2\2\2\35"+
		"\36\b\2\2\2\36\4\3\2\2\2\37 \7k\2\2 &\7h\2\2!\"\7y\2\2\"#\7j\2\2#$\7g"+
		"\2\2$&\7p\2\2%\37\3\2\2\2%!\3\2\2\2&\6\3\2\2\2\'(\7v\2\2()\7j\2\2)*\7"+
		"g\2\2*+\7p\2\2+\b\3\2\2\2,-\7g\2\2-.\7n\2\2./\7u\2\2/\60\7g\2\2\60\n\3"+
		"\2\2\2\61\62\7v\2\2\62\63\7j\2\2\63\66\7g\2\2\64\66\7c\2\2\65\61\3\2\2"+
		"\2\65\64\3\2\2\2\66\f\3\2\2\2\678\7d\2\289\7w\2\29:\7v\2\2:;\7v\2\2;<"+
		"\7q\2\2<H\7p\2\2=>\7n\2\2>?\7c\2\2?@\7d\2\2@A\7g\2\2AH\7n\2\2BC\7k\2\2"+
		"CD\7p\2\2DE\7r\2\2EF\7w\2\2FH\7v\2\2G\67\3\2\2\2G=\3\2\2\2GB\3\2\2\2H"+
		"\16\3\2\2\2IJ\7\"\2\2JK\7k\2\2KL\7u\2\2L\20\3\2\2\2MN\7.\2\2N\22\3\2\2"+
		"\2OP\7=\2\2P\24\3\2\2\2QU\7?\2\2RS\7k\2\2SU\7u\2\2TQ\3\2\2\2TR\3\2\2\2"+
		"U\26\3\2\2\2VW\7j\2\2WX\7k\2\2XY\7f\2\2YZ\7f\2\2Z[\7g\2\2[}\7p\2\2\\]"+
		"\7o\2\2]^\7c\2\2^_\7m\2\2_`\7g\2\2`a\7\"\2\2ab\7x\2\2bc\7k\2\2cd\7u\2"+
		"\2de\7k\2\2ef\7d\2\2fg\7n\2\2g}\7g\2\2hi\7f\2\2ij\7k\2\2jk\7u\2\2kl\7"+
		"c\2\2lm\7d\2\2mn\7n\2\2no\7g\2\2o}\7f\2\2pq\7u\2\2qr\7j\2\2rs\7q\2\2s"+
		"t\7y\2\2t}\7p\2\2uv\7e\2\2vw\7n\2\2wx\7k\2\2xy\7e\2\2yz\7m\2\2z{\7g\2"+
		"\2{}\7f\2\2|V\3\2\2\2|\\\3\2\2\2|h\3\2\2\2|p\3\2\2\2|u\3\2\2\2}\30\3\2"+
		"\2\2~\u0080\t\3\2\2\177~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\177\3\2\2\2"+
		"\u0081\u0082\3\2\2\2\u0082\32\3\2\2\2\t\2%\65GT|\u0081\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}