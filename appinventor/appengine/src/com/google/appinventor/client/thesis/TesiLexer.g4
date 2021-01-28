lexer grammar TesiLexer;

WHITESPACE: [ \t\r\n]-> skip;

IF: 'if'|'when';

THEN: 'then';

ELSE: 'else';

ARTICLE: 'the'|'a';

TYPE: 'button'|'label'|'input';

VERB : ' is';

COLON: ',';

SEMICOLON: ';';

ASSIGN : '='|'is';

ACTION : 'hidden'|'make visible'|'disabled'|'shown'|'clicked';

STRING : [a-zA-Z0-9]+;