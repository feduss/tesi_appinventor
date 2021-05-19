// Generated from TesiParser.g4 by ANTLR 4.9.2
package com.google.appinventor.client.thesis;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TesiParser}.
 */
public interface TesiParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TesiParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(TesiParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(TesiParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#event}.
	 * @param ctx the parse tree
	 */
	void enterEvent(TesiParser.EventContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#event}.
	 * @param ctx the parse tree
	 */
	void exitEvent(TesiParser.EventContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(TesiParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(TesiParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#action_body}.
	 * @param ctx the parse tree
	 */
	void enterAction_body(TesiParser.Action_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#action_body}.
	 * @param ctx the parse tree
	 */
	void exitAction_body(TesiParser.Action_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#action}.
	 * @param ctx the parse tree
	 */
	void enterAction(TesiParser.ActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#action}.
	 * @param ctx the parse tree
	 */
	void exitAction(TesiParser.ActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#anotherAction}.
	 * @param ctx the parse tree
	 */
	void enterAnotherAction(TesiParser.AnotherActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#anotherAction}.
	 * @param ctx the parse tree
	 */
	void exitAnotherAction(TesiParser.AnotherActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#condition_statement}.
	 * @param ctx the parse tree
	 */
	void enterCondition_statement(TesiParser.Condition_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#condition_statement}.
	 * @param ctx the parse tree
	 */
	void exitCondition_statement(TesiParser.Condition_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#action_statement}.
	 * @param ctx the parse tree
	 */
	void enterAction_statement(TesiParser.Action_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#action_statement}.
	 * @param ctx the parse tree
	 */
	void exitAction_statement(TesiParser.Action_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#open_page}.
	 * @param ctx the parse tree
	 */
	void enterOpen_page(TesiParser.Open_pageContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#open_page}.
	 * @param ctx the parse tree
	 */
	void exitOpen_page(TesiParser.Open_pageContext ctx);
}