parser grammar TesiParser;

options {tokenVocab=TesiLexer;}

block: event condition? action other_action*;

event: WHEN STRING VERB ACTION;

condition : IF statement; //THEN statement (ELSE statement)*;

action: THEN statement (AND block)*;

other_action: COLON statement (AND block)*;

statement: STRING VERB ACTION;

//assignment: view ASSIGN STRING; //STRING is the value