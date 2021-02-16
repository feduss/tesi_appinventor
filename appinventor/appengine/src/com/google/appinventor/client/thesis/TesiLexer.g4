lexer grammar TesiLexer;

WHITESPACE: [ \t\r\n]-> skip;

WHEN: 'when';

IF: 'if';

THEN: 'then';

ELSE: 'else';

AND: 'and';

ARTICLE: 'the'|'a';

//TYPE: 'button'|'label'|'input';

VERB : 'is';

COLON: ',';

SEMICOLON: ';';

ACTION : 'hidden'|'make visible'|'disabled'|'shown'|'clicked'|'visible';

STRING : [a-zA-Z0-9]+;