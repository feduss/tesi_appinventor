// Generated from TesiParser.g4 by ANTLR 4.8
package com.google.appinventor.client.thesis;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TesiParser}.
 */
public interface TesiParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TesiParser#blocks}.
	 * @param ctx the parse tree
	 */
	void enterBlocks(TesiParser.BlocksContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#blocks}.
	 * @param ctx the parse tree
	 */
	void exitBlocks(TesiParser.BlocksContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#if_else}.
	 * @param ctx the parse tree
	 */
	void enterIf_else(TesiParser.If_elseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#if_else}.
	 * @param ctx the parse tree
	 */
	void exitIf_else(TesiParser.If_elseContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#view}.
	 * @param ctx the parse tree
	 */
	void enterView(TesiParser.ViewContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#view}.
	 * @param ctx the parse tree
	 */
	void exitView(TesiParser.ViewContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(TesiParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(TesiParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(TesiParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(TesiParser.AssignmentContext ctx);
}