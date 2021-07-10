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
	 * Enter a parse tree produced by {@link TesiParser#another_condition}.
	 * @param ctx the parse tree
	 */
	void enterAnother_condition(TesiParser.Another_conditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#another_condition}.
	 * @param ctx the parse tree
	 */
	void exitAnother_condition(TesiParser.Another_conditionContext ctx);
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
	 * Enter a parse tree produced by {@link TesiParser#another_action}.
	 * @param ctx the parse tree
	 */
	void enterAnother_action(TesiParser.Another_actionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#another_action}.
	 * @param ctx the parse tree
	 */
	void exitAnother_action(TesiParser.Another_actionContext ctx);
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
	 * Enter a parse tree produced by {@link TesiParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(TesiParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(TesiParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#subj}.
	 * @param ctx the parse tree
	 */
	void enterSubj(TesiParser.SubjContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#subj}.
	 * @param ctx the parse tree
	 */
	void exitSubj(TesiParser.SubjContext ctx);
	/**
	 * Enter a parse tree produced by {@link TesiParser#subj_type}.
	 * @param ctx the parse tree
	 */
	void enterSubj_type(TesiParser.Subj_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TesiParser#subj_type}.
	 * @param ctx the parse tree
	 */
	void exitSubj_type(TesiParser.Subj_typeContext ctx);
}