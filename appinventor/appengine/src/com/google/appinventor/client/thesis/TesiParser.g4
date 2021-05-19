parser grammar TesiParser;

options {tokenVocab=TesiLexer;}

block: event condition? action anotherAction*;

event: WHEN STRING VERB ACTION;

condition : IF condition_statement;// (AND condition)*;

action_body: action_statement (THROW OPEN block CLOSE)? | open_page;

action: THEN action_body;

anotherAction: AND action_body;

condition_statement: STRING VERB ACTION;

action_statement: TYPE STRING VERB STRING;

open_page: ACTION_PAGE STRING;

