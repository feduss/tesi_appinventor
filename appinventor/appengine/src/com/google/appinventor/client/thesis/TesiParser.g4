parser grammar TesiParser;

options {tokenVocab=TesiLexer;}

block: event condition? action anotherAction*;

event: WHEN STRING VERB ACTION;

condition : IF statement;

action_body: statement (THROW OPEN block CLOSE)? | open_page;

action: THEN action_body;

anotherAction: AND action_body;

statement: STRING VERB ACTION;

open_page: ACTION_PAGE STRING;

