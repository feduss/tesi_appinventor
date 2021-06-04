parser grammar TesiParser;

options {tokenVocab=TesiLexer;}

block: event condition? action another_action*;

event: WHEN subj subj_type ACTION_WHEN_OBJ;

condition : IF condition_statement (another_condition)*;

another_condition: ((AND|OR) condition_statement);

action_body: action_statement (THROW OPEN_BRACKET block CLOSE_BRACKET)?;

action: THEN action_body;

another_action: AND action_body;

condition_statement: subj subj_type (NOT)? VERB ACTION_IF_OBJ | subj subj_type (NOT)? ACTION_IF_OBJ VERB value?;

action_statement: (SET subj subj_type ACTION_SET_OBJ value)|(CALL subj subj_type ACTION_CALL_OBJ value)
                  |(OPEN ACTION_OPEN_OBJ value); //TYPE: set, ACTION_OBJ: background color to, value.STRING: blue (for ex.)

value: STRING;

subj: STRING;

subj_type: STRING;

