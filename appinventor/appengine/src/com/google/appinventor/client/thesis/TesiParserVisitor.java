// Generated from TesiParser.g4 by ANTLR 4.9.2
package com.google.appinventor.client.thesis;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TesiParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TesiParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TesiParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(TesiParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link TesiParser#event}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvent(TesiParser.EventContext ctx);
	/**
	 * Visit a parse tree produced by {@link TesiParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(TesiParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TesiParser#action_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAction_body(TesiParser.Action_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TesiParser#action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAction(TesiParser.ActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TesiParser#anotherAction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnotherAction(TesiParser.AnotherActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TesiParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(TesiParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TesiParser#open_page}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpen_page(TesiParser.Open_pageContext ctx);
}