parser grammar TesiParser;

options {tokenVocab=TesiLexer;}

blocks: if_else | assignment | statement | view;

if_else : IF statement THEN statement (ELSE statement)*;

view: ARTICLE TYPE STRING; //STRING is the ID of the ui component

statement: view VERB ACTION;

assignment: view ASSIGN STRING; //STRING is the value

