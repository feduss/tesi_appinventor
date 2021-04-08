lexer grammar TesiLexer;

WHITESPACE: [ \t\r\n]-> skip;

WHEN: 'when';

IF: 'if';

AND: 'and';

THEN: 'then';

ELSE: 'else';

THROW: 'throw';

ARTICLE: 'the'|'a';

//TYPE: 'button'|'label'|'input';

VERB : 'is';

COLON: ',';

SEMICOLON: ';';

OPEN: '(';

CLOSE: ')';

ACTION : 'hidden'|'make visible'|'disabled'|'shown'|'clicked'|'visible';

ACTION_PAGE : 'open';

STRING : [a-zA-Z0-9]+;