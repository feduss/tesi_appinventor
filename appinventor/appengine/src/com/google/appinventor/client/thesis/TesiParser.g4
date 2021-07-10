parser grammar TesiParser;

options {tokenVocab=TesiLexer;}

block: event condition? action;

event: WHEN subj subj_type ACTION_WHEN_OBJ;

condition : IF condition_statement (another_condition)*;

another_condition: ((AND|OR) condition_statement);

condition_statement: subj subj_type (NOT)? VERB ACTION_IF_OBJ | subj subj_type (NOT)? ACTION_IF_OBJ VERB value?;

action: THEN action_statement (another_action)*;

another_action: AND action_statement;

action_statement: (SET subj subj_type ACTION_SET_OBJ value)|(CALL subj subj_type ACTION_CALL_OBJ value)
                  |(OPEN ACTION_OPEN_OBJ value);

value: STRING;

subj: STRING;

subj_type: STRING;

