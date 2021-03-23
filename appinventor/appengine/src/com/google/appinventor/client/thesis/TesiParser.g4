parser grammar TesiParser;

options {tokenVocab=TesiLexer;}

block: event condition? action+;

event: WHEN STRING VERB ACTION;

condition : IF statement;

action: THEN statement (THROW OPEN block CLOSE)?;

statement: STRING VERB ACTION;