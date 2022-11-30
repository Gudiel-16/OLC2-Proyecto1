// Generated from D:/ARCHIVOS E/CHIPECH/PROGRAMACION/COMPILADORES2/LAB/olc2vj22_201404278/Proyecto1/src\Gramatica.g4 by ANTLR 4.10.1
package Gramatica;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GramaticaParser}.
 */
public interface GramaticaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(GramaticaParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(GramaticaParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#linstrucciones}.
	 * @param ctx the parse tree
	 */
	void enterLinstrucciones(GramaticaParser.LinstruccionesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#linstrucciones}.
	 * @param ctx the parse tree
	 */
	void exitLinstrucciones(GramaticaParser.LinstruccionesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#instruccion}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion(GramaticaParser.InstruccionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#instruccion}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion(GramaticaParser.InstruccionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#progmain}.
	 * @param ctx the parse tree
	 */
	void enterProgmain(GramaticaParser.ProgmainContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#progmain}.
	 * @param ctx the parse tree
	 */
	void exitProgmain(GramaticaParser.ProgmainContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#implicit}.
	 * @param ctx the parse tree
	 */
	void enterImplicit(GramaticaParser.ImplicitContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#implicit}.
	 * @param ctx the parse tree
	 */
	void exitImplicit(GramaticaParser.ImplicitContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declarationVar}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationVar(GramaticaParser.DeclarationVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declarationVar}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationVar(GramaticaParser.DeclarationVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declarationVar2}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationVar2(GramaticaParser.DeclarationVar2Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declarationVar2}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationVar2(GramaticaParser.DeclarationVar2Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declarationVar3}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationVar3(GramaticaParser.DeclarationVar3Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declarationVar3}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationVar3(GramaticaParser.DeclarationVar3Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#asignationVar}.
	 * @param ctx the parse tree
	 */
	void enterAsignationVar(GramaticaParser.AsignationVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#asignationVar}.
	 * @param ctx the parse tree
	 */
	void exitAsignationVar(GramaticaParser.AsignationVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#printt}.
	 * @param ctx the parse tree
	 */
	void enterPrintt(GramaticaParser.PrinttContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#printt}.
	 * @param ctx the parse tree
	 */
	void exitPrintt(GramaticaParser.PrinttContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#printt2}.
	 * @param ctx the parse tree
	 */
	void enterPrintt2(GramaticaParser.Printt2Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#printt2}.
	 * @param ctx the parse tree
	 */
	void exitPrintt2(GramaticaParser.Printt2Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#printt3}.
	 * @param ctx the parse tree
	 */
	void enterPrintt3(GramaticaParser.Printt3Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#printt3}.
	 * @param ctx the parse tree
	 */
	void exitPrintt3(GramaticaParser.Printt3Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#lexpr}.
	 * @param ctx the parse tree
	 */
	void enterLexpr(GramaticaParser.LexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#lexpr}.
	 * @param ctx the parse tree
	 */
	void exitLexpr(GramaticaParser.LexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#decArrExtenso}.
	 * @param ctx the parse tree
	 */
	void enterDecArrExtenso(GramaticaParser.DecArrExtensoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#decArrExtenso}.
	 * @param ctx the parse tree
	 */
	void exitDecArrExtenso(GramaticaParser.DecArrExtensoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#decArrCorto}.
	 * @param ctx the parse tree
	 */
	void enterDecArrCorto(GramaticaParser.DecArrCortoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#decArrCorto}.
	 * @param ctx the parse tree
	 */
	void exitDecArrCorto(GramaticaParser.DecArrCortoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declarationArray2}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationArray2(GramaticaParser.DeclarationArray2Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declarationArray2}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationArray2(GramaticaParser.DeclarationArray2Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#asigArrList}.
	 * @param ctx the parse tree
	 */
	void enterAsigArrList(GramaticaParser.AsigArrListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#asigArrList}.
	 * @param ctx the parse tree
	 */
	void exitAsigArrList(GramaticaParser.AsigArrListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#asigArrPosEspecifico}.
	 * @param ctx the parse tree
	 */
	void enterAsigArrPosEspecifico(GramaticaParser.AsigArrPosEspecificoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#asigArrPosEspecifico}.
	 * @param ctx the parse tree
	 */
	void exitAsigArrPosEspecifico(GramaticaParser.AsigArrPosEspecificoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#asignationArray2}.
	 * @param ctx the parse tree
	 */
	void enterAsignationArray2(GramaticaParser.AsignationArray2Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#asignationArray2}.
	 * @param ctx the parse tree
	 */
	void exitAsignationArray2(GramaticaParser.AsignationArray2Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declarationArrayDinamico}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationArrayDinamico(GramaticaParser.DeclarationArrayDinamicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declarationArrayDinamico}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationArrayDinamico(GramaticaParser.DeclarationArrayDinamicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declarationArrayDinamico2}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationArrayDinamico2(GramaticaParser.DeclarationArrayDinamico2Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declarationArrayDinamico2}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationArrayDinamico2(GramaticaParser.DeclarationArrayDinamico2Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#asignationArrayDinamico}.
	 * @param ctx the parse tree
	 */
	void enterAsignationArrayDinamico(GramaticaParser.AsignationArrayDinamicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#asignationArrayDinamico}.
	 * @param ctx the parse tree
	 */
	void exitAsignationArrayDinamico(GramaticaParser.AsignationArrayDinamicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#desAsignationArrayDinamico}.
	 * @param ctx the parse tree
	 */
	void enterDesAsignationArrayDinamico(GramaticaParser.DesAsignationArrayDinamicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#desAsignationArrayDinamico}.
	 * @param ctx the parse tree
	 */
	void exitDesAsignationArrayDinamico(GramaticaParser.DesAsignationArrayDinamicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#asignationArrayDinamico2}.
	 * @param ctx the parse tree
	 */
	void enterAsignationArrayDinamico2(GramaticaParser.AsignationArrayDinamico2Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#asignationArrayDinamico2}.
	 * @param ctx the parse tree
	 */
	void exitAsignationArrayDinamico2(GramaticaParser.AsignationArrayDinamico2Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#sentenciaIf}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaIf(GramaticaParser.SentenciaIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#sentenciaIf}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaIf(GramaticaParser.SentenciaIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#lcondicionesIf}.
	 * @param ctx the parse tree
	 */
	void enterLcondicionesIf(GramaticaParser.LcondicionesIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#lcondicionesIf}.
	 * @param ctx the parse tree
	 */
	void exitLcondicionesIf(GramaticaParser.LcondicionesIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#elseBloque}.
	 * @param ctx the parse tree
	 */
	void enterElseBloque(GramaticaParser.ElseBloqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#elseBloque}.
	 * @param ctx the parse tree
	 */
	void exitElseBloque(GramaticaParser.ElseBloqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#doEstructura}.
	 * @param ctx the parse tree
	 */
	void enterDoEstructura(GramaticaParser.DoEstructuraContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#doEstructura}.
	 * @param ctx the parse tree
	 */
	void exitDoEstructura(GramaticaParser.DoEstructuraContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#doWhileEstrctura}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileEstrctura(GramaticaParser.DoWhileEstrcturaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#doWhileEstrctura}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileEstrctura(GramaticaParser.DoWhileEstrcturaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#controlExit}.
	 * @param ctx the parse tree
	 */
	void enterControlExit(GramaticaParser.ControlExitContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#controlExit}.
	 * @param ctx the parse tree
	 */
	void exitControlExit(GramaticaParser.ControlExitContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#controlCycle}.
	 * @param ctx the parse tree
	 */
	void enterControlCycle(GramaticaParser.ControlCycleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#controlCycle}.
	 * @param ctx the parse tree
	 */
	void exitControlCycle(GramaticaParser.ControlCycleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#etiquetaDo}.
	 * @param ctx the parse tree
	 */
	void enterEtiquetaDo(GramaticaParser.EtiquetaDoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#etiquetaDo}.
	 * @param ctx the parse tree
	 */
	void exitEtiquetaDo(GramaticaParser.EtiquetaDoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#etiquetaDoWhile}.
	 * @param ctx the parse tree
	 */
	void enterEtiquetaDoWhile(GramaticaParser.EtiquetaDoWhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#etiquetaDoWhile}.
	 * @param ctx the parse tree
	 */
	void exitEtiquetaDoWhile(GramaticaParser.EtiquetaDoWhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#subroutine}.
	 * @param ctx the parse tree
	 */
	void enterSubroutine(GramaticaParser.SubroutineContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#subroutine}.
	 * @param ctx the parse tree
	 */
	void exitSubroutine(GramaticaParser.SubroutineContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#ldeclP}.
	 * @param ctx the parse tree
	 */
	void enterLdeclP(GramaticaParser.LdeclPContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#ldeclP}.
	 * @param ctx the parse tree
	 */
	void exitLdeclP(GramaticaParser.LdeclPContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declParametros}.
	 * @param ctx the parse tree
	 */
	void enterDeclParametros(GramaticaParser.DeclParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declParametros}.
	 * @param ctx the parse tree
	 */
	void exitDeclParametros(GramaticaParser.DeclParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#valorParametros}.
	 * @param ctx the parse tree
	 */
	void enterValorParametros(GramaticaParser.ValorParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#valorParametros}.
	 * @param ctx the parse tree
	 */
	void exitValorParametros(GramaticaParser.ValorParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(GramaticaParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(GramaticaParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#funcion}.
	 * @param ctx the parse tree
	 */
	void enterFuncion(GramaticaParser.FuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#funcion}.
	 * @param ctx the parse tree
	 */
	void exitFuncion(GramaticaParser.FuncionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code charExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCharExpr(GramaticaParser.CharExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code charExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCharExpr(GramaticaParser.CharExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdAtom(GramaticaParser.IdAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdAtom(GramaticaParser.IdAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code complexExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterComplexExpr(GramaticaParser.ComplexExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code complexExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitComplexExpr(GramaticaParser.ComplexExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFuncExpr(GramaticaParser.FuncExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFuncExpr(GramaticaParser.FuncExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOpExpr(GramaticaParser.OpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOpExpr(GramaticaParser.OpExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAccesoExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccesoExpr(GramaticaParser.ArrayAccesoExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAccesoExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccesoExpr(GramaticaParser.ArrayAccesoExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExpr(GramaticaParser.LogicalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalExpr(GramaticaParser.LogicalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code realExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRealExpr(GramaticaParser.RealExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code realExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRealExpr(GramaticaParser.RealExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(GramaticaParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(GramaticaParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(GramaticaParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(GramaticaParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code integExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIntegExpr(GramaticaParser.IntegExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code integExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIntegExpr(GramaticaParser.IntegExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(GramaticaParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(GramaticaParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpr(GramaticaParser.UnaryMinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpr(GramaticaParser.UnaryMinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parentExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParentExpr(GramaticaParser.ParentExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParentExpr(GramaticaParser.ParentExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPowExpr(GramaticaParser.PowExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPowExpr(GramaticaParser.PowExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(GramaticaParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(GramaticaParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sizeArr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSizeArr(GramaticaParser.SizeArrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sizeArr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSizeArr(GramaticaParser.SizeArrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(GramaticaParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(GramaticaParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(GramaticaParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(GramaticaParser.TypeContext ctx);
}