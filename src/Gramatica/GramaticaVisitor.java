// Generated from D:/ARCHIVOS E/CHIPECH/PROGRAMACION/COMPILADORES2/LAB/olc2vj22_201404278/Proyecto1/src\Gramatica.g4 by ANTLR 4.10.1
package Gramatica;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GramaticaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GramaticaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#parse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParse(GramaticaParser.ParseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#linstrucciones}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLinstrucciones(GramaticaParser.LinstruccionesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#instruccion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion(GramaticaParser.InstruccionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#progmain}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgmain(GramaticaParser.ProgmainContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#implicit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImplicit(GramaticaParser.ImplicitContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#declarationVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationVar(GramaticaParser.DeclarationVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#declarationVar2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationVar2(GramaticaParser.DeclarationVar2Context ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#declarationVar3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationVar3(GramaticaParser.DeclarationVar3Context ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#asignationVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignationVar(GramaticaParser.AsignationVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#printt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintt(GramaticaParser.PrinttContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#printt2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintt2(GramaticaParser.Printt2Context ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#printt3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintt3(GramaticaParser.Printt3Context ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#lexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexpr(GramaticaParser.LexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#decArrExtenso}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecArrExtenso(GramaticaParser.DecArrExtensoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#decArrCorto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecArrCorto(GramaticaParser.DecArrCortoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#declarationArray2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationArray2(GramaticaParser.DeclarationArray2Context ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#asigArrList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsigArrList(GramaticaParser.AsigArrListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#asigArrPosEspecifico}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsigArrPosEspecifico(GramaticaParser.AsigArrPosEspecificoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#asignationArray2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignationArray2(GramaticaParser.AsignationArray2Context ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#declarationArrayDinamico}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationArrayDinamico(GramaticaParser.DeclarationArrayDinamicoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#declarationArrayDinamico2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationArrayDinamico2(GramaticaParser.DeclarationArrayDinamico2Context ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#asignationArrayDinamico}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignationArrayDinamico(GramaticaParser.AsignationArrayDinamicoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#desAsignationArrayDinamico}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDesAsignationArrayDinamico(GramaticaParser.DesAsignationArrayDinamicoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#asignationArrayDinamico2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignationArrayDinamico2(GramaticaParser.AsignationArrayDinamico2Context ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#sentenciaIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSentenciaIf(GramaticaParser.SentenciaIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#lcondicionesIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLcondicionesIf(GramaticaParser.LcondicionesIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#elseBloque}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseBloque(GramaticaParser.ElseBloqueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#doEstructura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoEstructura(GramaticaParser.DoEstructuraContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#doWhileEstrctura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileEstrctura(GramaticaParser.DoWhileEstrcturaContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#controlExit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControlExit(GramaticaParser.ControlExitContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#controlCycle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControlCycle(GramaticaParser.ControlCycleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#etiquetaDo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEtiquetaDo(GramaticaParser.EtiquetaDoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#etiquetaDoWhile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEtiquetaDoWhile(GramaticaParser.EtiquetaDoWhileContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#subroutine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubroutine(GramaticaParser.SubroutineContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#ldeclP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdeclP(GramaticaParser.LdeclPContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#declParametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclParametros(GramaticaParser.DeclParametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#valorParametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValorParametros(GramaticaParser.ValorParametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(GramaticaParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#funcion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncion(GramaticaParser.FuncionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code charExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharExpr(GramaticaParser.CharExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdAtom(GramaticaParser.IdAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code complexExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComplexExpr(GramaticaParser.ComplexExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncExpr(GramaticaParser.FuncExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpExpr(GramaticaParser.OpExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayAccesoExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccesoExpr(GramaticaParser.ArrayAccesoExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalExpr(GramaticaParser.LogicalExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code realExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealExpr(GramaticaParser.RealExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(GramaticaParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(GramaticaParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code integExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegExpr(GramaticaParser.IntegExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(GramaticaParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinusExpr(GramaticaParser.UnaryMinusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parentExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentExpr(GramaticaParser.ParentExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code powExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowExpr(GramaticaParser.PowExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(GramaticaParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sizeArr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSizeArr(GramaticaParser.SizeArrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(GramaticaParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GramaticaParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(GramaticaParser.TypeContext ctx);
}