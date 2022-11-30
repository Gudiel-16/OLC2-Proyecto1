import Gramatica.GramaticaBaseVisitor;
import Gramatica.GramaticaParser;
import ArbolAST.Ast;
import ArbolAST.Nodo;
import java.util.List;

public class VisitorAST extends GramaticaBaseVisitor<Value> {

    int identificador = 10000; //TODO: identificador de cada nodo del arbol
    String bloqueActual = ""; //TODO: para saber en que bloque se ejecuta la lista de istrucciones, program o if, while etc..
    public Ast miArbol = new Ast();
    String padreActual = ""; //TODO: padre del nodo que se va insertar
    public Nodo nodo;

    public Value visitParse(GramaticaParser.ParseContext ctx) {
        nodo = miArbol.InsertarRaiz("Parse"+identificador, "Parse");
        padreActual = "Parse"+identificador;
        identificador++;
//        System.out.println("parse");
        return visitLinstrucciones(ctx.linstrucciones());
    }

    public Value visitLinstrucciones(GramaticaParser.LinstruccionesContext ctx) {
        for (GramaticaParser.InstruccionContext ictx: ctx.instruccion()){
            Value val = visitInstruccion(ictx); //TODO: voy guardando lo que retorna, cada instruccion que ejuta, esperando que venga un exit o cycle
            padreActual = bloqueActual;
        }
        return new Value("","",0,0);
    }

    public Value visitInstruccion(GramaticaParser.InstruccionContext ctx) {

        if(ctx.progmain()!=null)
            visitProgmain(ctx.progmain());
        else if(ctx.declarationVar()!=null)
            visitDeclarationVar(ctx.declarationVar());
        else if(ctx.asignationVar()!=null)
            visitAsignationVar(ctx.asignationVar());
        else if(ctx.printt()!=null)
            visitPrintt(ctx.printt());
        else if(ctx.decArrExtenso()!=null)
            visitDecArrExtenso(ctx.decArrExtenso());
        else if(ctx.decArrCorto()!=null)
            visitDecArrCorto(ctx.decArrCorto());
        else if(ctx.asigArrList()!=null)
            visitAsigArrList(ctx.asigArrList());
        else if(ctx.asigArrPosEspecifico()!=null)
            visitAsigArrPosEspecifico(ctx.asigArrPosEspecifico());
        else if(ctx.declarationArrayDinamico()!=null)
            visitDeclarationArrayDinamico(ctx.declarationArrayDinamico());
        else if(ctx.asignationArrayDinamico()!=null)
            visitAsignationArrayDinamico(ctx.asignationArrayDinamico());
        else if(ctx.desAsignationArrayDinamico()!=null)
            visitDesAsignationArrayDinamico(ctx.desAsignationArrayDinamico());
        else if(ctx.sentenciaIf()!=null) //TODO: hago un return solo en if, do, do-while, control-exit, control-cycle, para saber cuando parar dentro del ciclo
            return visitSentenciaIf(ctx.sentenciaIf());
        else if(ctx.doEstructura()!=null)
            return visitDoEstructura(ctx.doEstructura());
        else if(ctx.doWhileEstrctura()!=null)
            return visitDoWhileEstrctura(ctx.doWhileEstrctura());
        else if(ctx.controlExit()!=null)
            return visitControlExit(ctx.controlExit());
        else if(ctx.controlCycle()!=null)
            return visitControlCycle(ctx.controlCycle());
        else if(ctx.etiquetaDo()!=null)
            visitEtiquetaDo(ctx.etiquetaDo());
        else if(ctx.etiquetaDoWhile()!=null)
            visitEtiquetaDoWhile(ctx.etiquetaDoWhile());
        else if(ctx.subroutine()!=null)
            visitSubroutine(ctx.subroutine());
        else if(ctx.call()!=null)
            visitCall(ctx.call());
        else if(ctx.funcion()!=null){
            visitFuncion(ctx.funcion());
        }

        return new Value("","",0,0);
    }

    public Value visitProgmain(GramaticaParser.ProgmainContext ctx) {

        String aux = padreActual; //TODO: para que regrese al padre, luego de ejcutar todo.

        String nombre1 = ctx.ID().get(0).getText(); //TODO: primer nombre
        String nombre2 = ctx.ID().get(1).getText(); //TODO: segundo nombre

        miArbol.InsertarRecursivo(nodo, "program"+identificador,"program", padreActual); //TODO: Ingreso Ingreso nodo program
        String idProg = "program"+identificador; //TODO; va servir para ingresar el segundo nombre
        padreActual = "program"+identificador;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "program"+identificador,nombre1, padreActual); //TODO: ingreso nombre 1 del program
        identificador++;

        miArbol.InsertarRecursivo(nodo, "linstrucciones"+identificador,"linstrucciones", padreActual); //TODO: ingreso nodo lista instrucciones
        padreActual = "linstrucciones"+identificador;
        identificador++;

        bloqueActual = padreActual; //TODO: para diferenciar que bloque de lista de instruciiones se esta ejecutando, si es if, while, funcion, etc.
        //System.out.println("program");
        visitLinstrucciones(ctx.linstrucciones());

        miArbol.InsertarRecursivo(nodo, "program"+identificador,nombre2, idProg); //TODO: luego de ejecutar instrucciones, ingreso nombre 2 del program
        identificador++;

        bloqueActual = aux;

        return new Value("","",0,0);
    }

    public Value visitDeclarationVar(GramaticaParser.DeclarationVarContext ctx) {

        miArbol.InsertarRecursivo(nodo, "declarationVar"+identificador, "declarationVar", padreActual);
        padreActual = "declarationVar"+identificador;
        identificador++;

        Value tipoVar = visit(ctx.type());
        miArbol.InsertarRecursivo(nodo, tipoVar.value.toString()+identificador, tipoVar.value.toString(), padreActual);
        identificador++;

        //System.out.println("visitDeclarationVar");

        if(ctx.declarationVar2()!=null){
            visit(ctx.declarationVar2());
        }

        if(ctx.declarationVar3()!=null){
            visit(ctx.declarationVar3());
        }

        return new Value("");
    }

    public Value visitType(GramaticaParser.TypeContext ctx) {

        return new Value(ctx.getText());
    }

    public Value visitDeclarationVar2(GramaticaParser.DeclarationVar2Context ctx) {
        String idVar = ctx.ID().getText();
        miArbol.InsertarRecursivo(nodo, idVar+identificador, idVar, padreActual);
        identificador++;

        //System.out.println("visitDeclarationVar2");

        //TODO: si trae valor para asignar a variable
        if(ctx.expr()!=null){
            Value val = visit(ctx.expr());
            if(!val.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "var"+identificador, val.value.toString(), padreActual); //TODO: var porque char y logical da error en graphiz por . y ''
                identificador++;
            }
            return new Value("");
        }

        return new Value("");
    }

    public Value visitDeclarationVar3(GramaticaParser.DeclarationVar3Context ctx) {

        miArbol.InsertarRecursivo(nodo, "declarationVar"+identificador, "declarationVar", padreActual);
        padreActual = "declarationVar"+identificador;
        identificador++;

        if(ctx.declarationVar2()!=null){
            visit(ctx.declarationVar2());
        }

        if(ctx.declarationVar3()!=null){
            visit(ctx.declarationVar3());
        }
        return new Value("");
    }

    public Value visitAsignationVar(GramaticaParser.AsignationVarContext ctx) {

        String idVar = ctx.ID().getText();
        miArbol.InsertarRecursivo(nodo, "asigvar"+identificador, "asignacionVar", padreActual); //TODO: nodo asignacion
        padreActual = "asigvar"+identificador;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "asigvar"+identificador, idVar, padreActual); //TODO: ingreso hijo id
        identificador++;

        Value val = visit(ctx.expr());
        if (!val.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "asigvar"+identificador, val.value.toString(), padreActual);
            identificador++;
        }

        return new Value("","",0,0);
    }

    public Value visitPrintt(GramaticaParser.PrinttContext ctx) {

        miArbol.InsertarRecursivo(nodo, "print"+identificador, "print", padreActual); //TODO: inserto nodo print
        padreActual = "print"+identificador;
        identificador++;

        Value val = visit(ctx.expr());
        if(!val.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "var"+identificador, val.value.toString(), padreActual); //TODO: inserto nodo hijo
            identificador++;
        }

        //Hay que imprimir mas de una variable
        if (ctx.printt2()!=null){
            visit(ctx.printt2());
        }

        return new Value("");
    }

    public Value visitPrintt2(GramaticaParser.Printt2Context ctx) {

        miArbol.InsertarRecursivo(nodo, "print"+identificador, "print", padreActual); //TODO: inserto nodo print
        String pa = "print"+identificador;
        padreActual = pa;
        identificador++;

        if(ctx.printt3()!=null){
            visit(ctx.printt3());
        }
        padreActual = pa;

        if(ctx.printt2()!=null){
            visit(ctx.printt2());
        }
        return new Value("");
    }

    public Value visitPrintt3(GramaticaParser.Printt3Context ctx) {
        Value val = visit(ctx.expr());
        if(!val.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "var"+identificador, val.value.toString(), padreActual); //TODO: inserto nodo hijo
            identificador++;
        }
        return new Value("");
    }

    public Value visitDecArrExtenso(GramaticaParser.DecArrExtensoContext ctx) {

        miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, "decArrExtenso", padreActual); //TODO: nodo declaracion
        int ida = identificador;
        padreActual = "asigarray"+ida;
        identificador++;

        Value tipoArr = visit(ctx.type());
        String idArr = ctx.ID().getText();

        miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, tipoArr.value.toString(), padreActual); //TODO: ingresamos tipo
        identificador++;

        if(ctx.declarationArray2()!=null){ //TODO: es de dos dimensiones
            Value indice1 = visit(ctx.expr());
            if (!indice1.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, indice1.value.toString(), padreActual); //TODO: nodo declaracion
                identificador++;
            }
            padreActual = "asigarray"+ida;
            Value indice2 = visit(ctx.declarationArray2());
            if (!indice2.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, indice2.value.toString(), padreActual); //TODO: nodo declaracion
                identificador++;
            }
            padreActual = "asigarray"+ida;
        }else{ //TODO: es de una dimension
            Value indice1 = visit(ctx.expr());
            if (!indice1.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, indice1.value.toString(), padreActual); //TODO: nodo declaracion
                identificador++;
            }
            padreActual = "asigarray"+ida;
        }

        miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, idArr, padreActual); //TODO: sea cual sea la dimension, insertamos nombre de array
        identificador++;

        return new Value("","",0,0);

    }

    public Value visitDecArrCorto(GramaticaParser.DecArrCortoContext ctx) {

        miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, "decArrCorto", padreActual); //TODO: nodo declaracion
        int ida = identificador;
        padreActual = "asigarray"+ida;
        identificador++;

        Value tipoArr = visit(ctx.type());
        String idArr = ctx.ID().getText();

        miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, tipoArr.value.toString(), padreActual); //TODO: ingresamos tipo
        identificador++;

        miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, idArr, padreActual); //TODO: sea cual sea la dimension, insertamos nombre de array
        identificador++;

        if(ctx.declarationArray2()!=null){ //TODO: es de dos dimensiones
            Value indice1 = visit(ctx.expr());
            if (!indice1.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, indice1.value.toString(), padreActual); //TODO: nodo declaracion
                identificador++;
            }
            padreActual = "asigarray"+ida;
            Value indice2 = visit(ctx.declarationArray2());
            if (!indice2.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, indice2.value.toString(), padreActual); //TODO: nodo declaracion
                identificador++;
            }
            padreActual = "asigarray"+ida;
        }else{ //TODO: es de una dimension
            Value indice1 = visit(ctx.expr());
            if (!indice1.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "asigarray"+identificador, indice1.value.toString(), padreActual); //TODO: nodo declaracion
                identificador++;
            }
            padreActual = "asigarray"+ida;
        }

        return new Value("","",0,0);
    }

    public Value visitDeclarationArray2(GramaticaParser.DeclarationArray2Context ctx) {
        return visit(ctx.expr());
    }

    public Value visitAsigArrList(GramaticaParser.AsigArrListContext ctx) {

        miArbol.InsertarRecursivo(nodo, "asigarraylist"+identificador, "asigArrList", padreActual); //TODO: nodo asignacion
        int ida = identificador;
        padreActual = "asigarraylist"+ida;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "asigarraylist"+identificador, ctx.ID().getText(), padreActual); //TODO: nombre array
        identificador++;

        for (int i = 0; i < ctx.lexpr().expr().size(); i++){
            Value val = visit(ctx.lexpr().expr().get(i));
            if(!val.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "asigarraylist"+identificador, val.value.toString(), padreActual); //TODO: nombre array
                identificador++;
            }
            padreActual = "asigarraylist"+ida;
        }

        return new Value("","",0,0);
    }

    public Value visitAsigArrPosEspecifico(GramaticaParser.AsigArrPosEspecificoContext ctx) {

        miArbol.InsertarRecursivo(nodo, "asigarrayPosEspecifico"+identificador, "asigarrayPosEspecifico", padreActual); //TODO: nodo asignacion
        int ida = identificador;
        padreActual = "asigarrayPosEspecifico"+ida;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "asigarrayPosEspecifico"+identificador, ctx.ID().getText(), padreActual); //TODO: nombre array
        identificador++;

        for (int i = 0; i < ctx.lexpr().expr().size(); i++){
            Value val = visit(ctx.lexpr().expr().get(i));
            if(!val.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "asigarrayPosEspecifico"+identificador, val.value.toString(), padreActual); //TODO: nombre array
                identificador++;
            }
            padreActual = "asigarrayPosEspecifico"+ida;
        }

        Value val = visit(ctx.expr());
        if (!val.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "asigarrayPosEspecifico"+identificador, val.value.toString(), padreActual); //TODO: inserto expresion
            identificador++;
        }

        return new Value("","",0,0);
    }

    public Value visitDeclarationArrayDinamico(GramaticaParser.DeclarationArrayDinamicoContext ctx) {

        miArbol.InsertarRecursivo(nodo, "declarationArrayDinamico"+identificador, "declarationArrayDinamico", padreActual); //TODO: nodo declaracion
        int ida = identificador;
        padreActual = "declarationArrayDinamico"+ida;
        identificador++;

        Value tipoArr = visit(ctx.type());
        miArbol.InsertarRecursivo(nodo, "declarationArrayDinamico"+identificador, tipoArr.value.toString(), padreActual); //TODO: tipo array
        identificador++;

        miArbol.InsertarRecursivo(nodo, "declarationArrayDinamico"+identificador, ctx.ID().getText(), padreActual); //TODO: nombre array
        identificador++;

        if(ctx.declarationArrayDinamico2()!=null){ //TODO: es de dos dimensiones
            miArbol.InsertarRecursivo(nodo, "declarationArrayDinamico"+identificador, ":", padreActual); //TODO: dos puntos
            identificador++;
            miArbol.InsertarRecursivo(nodo, "declarationArrayDinamico"+identificador, ":", padreActual); //TODO: dos puntos
            identificador++;
        }else{ //TODO: es de una dimension
            miArbol.InsertarRecursivo(nodo, "declarationArrayDinamico"+identificador, ":", padreActual); //TODO: dos puntos
            identificador++;
        }

        return new Value("","",0,0);
    }

    public Value visitAsignationArrayDinamico(GramaticaParser.AsignationArrayDinamicoContext ctx) {

        miArbol.InsertarRecursivo(nodo, "allocate"+identificador, "allocate", padreActual); //TODO: nodo allocate
        int ida = identificador;
        padreActual = "allocate"+ida;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "allocate"+identificador, ctx.ID().getText(), padreActual); //TODO: nombre array
        identificador++;

        if(ctx.asignationArrayDinamico2()!=null){ //TODO: es de dos dimensiones
            Value indice1 = visit(ctx.expr());
            if (!indice1.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "allocate"+identificador, indice1.value.toString(), padreActual);
                identificador++;
            }
            padreActual = "allocate"+ida;
            Value indice2 = visit(ctx.asignationArrayDinamico2().expr());
            if (!indice2.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "allocate"+identificador, indice2.value.toString(), padreActual);
                identificador++;
            }
            padreActual = "allocate"+ida;
        }else{ //TODO: es de una dimension
            Value indice1 = visit(ctx.expr());
            if (!indice1.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "allocate"+identificador, indice1.value.toString(), padreActual);
                identificador++;
            }
            padreActual = "allocate"+ida;
        }

        return new Value("","",0,0);
    }

    public Value visitDesAsignationArrayDinamico(GramaticaParser.DesAsignationArrayDinamicoContext ctx) {

        miArbol.InsertarRecursivo(nodo, "deallocate"+identificador, "deallocate", padreActual); //TODO: nodo deallocate
        int ida = identificador;
        padreActual = "deallocate"+ida;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "deallocate"+identificador, ctx.ID().getText(), padreActual); //TODO: nombre array
        identificador++;

        return new Value("","",0,0);
    }

    public Value visitSentenciaIf(GramaticaParser.SentenciaIfContext ctx) {

        String aux = padreActual; //TODO: para que regrese al padre, luego de ejcutar todo.

        miArbol.InsertarRecursivo(nodo, "sentenciaif"+identificador, "sentenciaif", padreActual); //TODO: nodo sentenciaif
        int ida = identificador;
        padreActual = "sentenciaif"+ida;
        identificador++;

        //TODO: obtengo la lista de todas las condiciones
        List<GramaticaParser.LcondicionesIfContext> condiciones = ctx.lcondicionesIf();

        //TODO: recorro la lista de condiciones para evular la expresion
        for (GramaticaParser.LcondicionesIfContext condicion: condiciones){
            miArbol.InsertarRecursivo(nodo, "if"+identificador, "if", padreActual);
            int idb = identificador;
            padreActual = "if"+idb;
            identificador++;

            Value val = visit(condicion.expr());
            if(!val.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "if"+identificador, val.value.toString(), padreActual);
                identificador++;
            }
            padreActual = "if"+idb;

            miArbol.InsertarRecursivo(nodo, "linstrucciones"+identificador,"linstrucciones", padreActual); //TODO: ingreso nodo lista instrucciones
            padreActual = "linstrucciones"+identificador;
            identificador++;

            bloqueActual = padreActual;
            visitLinstrucciones(condicion.linstrucciones());

            padreActual = "sentenciaif"+ida;
        }

        //TODO: bloque ELSE
        if(ctx.elseBloque() != null){
            miArbol.InsertarRecursivo(nodo, "else"+identificador, "else", padreActual);
            padreActual = "else"+identificador;
            identificador++;

            miArbol.InsertarRecursivo(nodo, "linstrucciones"+identificador,"linstrucciones", padreActual); //TODO: ingreso nodo lista instrucciones
            padreActual = "linstrucciones"+identificador;
            identificador++;

            bloqueActual = padreActual;
            visitLinstrucciones(ctx.elseBloque().linstrucciones()); //TODO: ejecuto las instrucciones
            padreActual = "sentenciaif"+ida;
        }

        bloqueActual = aux;

        return new Value("","",0,0);
    }

    public Value visitDoEstructura(GramaticaParser.DoEstructuraContext ctx) {

        String aux = padreActual; //TODO: para que regrese al padre, luego de ejcutar todo.

        miArbol.InsertarRecursivo(nodo, "do"+identificador, "do", padreActual); //TODO: nodo do
        int ida = identificador;
        padreActual = "do"+ida;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "do"+identificador,ctx.ID().getText(), padreActual); //TODO: inserto id de variable
        identificador++;

        List<GramaticaParser.ExprContext> expresiones = ctx.expr();
        Value indice1 = visit(expresiones.get(0)); //TODO: valor inicial
        if (!indice1.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "do"+identificador, indice1.value.toString(), padreActual); //TODO: inserto id de variable
            identificador++;
        }
        padreActual = "do"+ida;

        Value indice2 = visit(expresiones.get(1)); //TODO: valor, hasta donde llegar
        if (!indice2.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "do"+identificador, indice2.value.toString(), padreActual); //TODO: inserto id de variable
            identificador++;
        }
        padreActual = "do"+ida;

        Value indice3 = visit(expresiones.get(2)); //TODO: valor, cuanto incrementar
        if (!indice3.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "do"+identificador, indice3.value.toString(), padreActual); //TODO: inserto id de variable
            identificador++;
        }
        padreActual = "do"+ida;

        miArbol.InsertarRecursivo(nodo, "linstrucciones"+identificador,"linstrucciones", padreActual); //TODO: ingreso nodo lista instrucciones
        padreActual = "linstrucciones"+identificador;
        identificador++;

        bloqueActual = padreActual;
        visitLinstrucciones(ctx.linstrucciones());
        bloqueActual = aux;

        return new Value("","",0,0);
    }

    public Value visitDoWhileEstrctura(GramaticaParser.DoWhileEstrcturaContext ctx) {

        String aux = padreActual; //TODO: para que regrese al padre, luego de ejcutar todo.

        miArbol.InsertarRecursivo(nodo, "dowhile"+identificador, "dowhile", padreActual); //TODO: nodo dowhile
        int ida = identificador;
        padreActual = "dowhile"+ida;
        identificador++;

        Value condicion = visit(ctx.expr());
        if(!condicion.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "dowhile"+identificador,condicion.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "dowhile"+ida;

        miArbol.InsertarRecursivo(nodo, "linstrucciones"+identificador,"linstrucciones", padreActual); //TODO: ingreso nodo lista instrucciones
        padreActual = "linstrucciones"+identificador;
        identificador++;

        bloqueActual = padreActual;
        visitLinstrucciones(ctx.linstrucciones());
        bloqueActual = aux;

        return new Value("","",0,0);
    }

    public Value visitControlExit(GramaticaParser.ControlExitContext ctx) {

        miArbol.InsertarRecursivo(nodo, "exit"+identificador, "exit", padreActual);
        identificador++;

        return new Value("","",0,0);
    }

    public Value visitControlCycle(GramaticaParser.ControlCycleContext ctx) {

        miArbol.InsertarRecursivo(nodo, "cycle"+identificador, "cycle", padreActual);
        identificador++;

        return new Value("","",0,0);
    }

    public Value visitEtiquetaDo(GramaticaParser.EtiquetaDoContext ctx) {

        String aux = padreActual; //TODO: para que regrese al padre, luego de ejcutar todo.

        miArbol.InsertarRecursivo(nodo, "etiquetaDo"+identificador, "etiquetaDo", padreActual); //TODO: nodo
        int ida = identificador;
        padreActual = "etiquetaDo"+ida;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "etiquetaDo"+identificador,ctx.ID().get(0).getText(), padreActual); //TODO: inserto nombre 1
        identificador++;

        miArbol.InsertarRecursivo(nodo, "etiquetaDo"+identificador,ctx.ID().get(1).getText(), padreActual); //TODO: inserto id de variable
        identificador++;

        List<GramaticaParser.ExprContext> expresiones = ctx.expr();
        Value indice1 = visit(expresiones.get(0)); //TODO: valor inicial
        if (!indice1.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "etiquetaDo"+identificador, indice1.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "etiquetaDo"+ida;

        Value indice2 = visit(expresiones.get(1)); //TODO: valor, hasta donde llegar
        if (!indice2.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "etiquetaDo"+identificador, indice2.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "etiquetaDo"+ida;

        Value indice3 = visit(expresiones.get(2)); //TODO: valor, cuanto incrementar
        if (!indice3.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "etiquetaDo"+identificador, indice3.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "etiquetaDo"+ida;

        miArbol.InsertarRecursivo(nodo, "linstrucciones"+identificador,"linstrucciones", padreActual); //TODO: ingreso nodo lista instrucciones
        padreActual = "linstrucciones"+identificador;
        identificador++;

        bloqueActual = padreActual;
        visitLinstrucciones(ctx.linstrucciones());

        padreActual = "etiquetaDo"+ida;

        miArbol.InsertarRecursivo(nodo, "etiquetaDo"+identificador,ctx.ID().get(2).getText(), padreActual); //TODO: inserto nombre 2
        identificador++;

        bloqueActual = aux;

        return new Value("","",0,0);
    }

    public Value visitEtiquetaDoWhile(GramaticaParser.EtiquetaDoWhileContext ctx) {

        String aux = padreActual; //TODO: para que regrese al padre, luego de ejcutar todo.

        miArbol.InsertarRecursivo(nodo, "etiquetaDoWhile"+identificador, "etiquetaDoWhile", padreActual); //TODO: nodo
        int ida = identificador;
        padreActual = "etiquetaDoWhile"+ida;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "etiquetaDoWhile"+identificador,ctx.ID().get(0).getText(), padreActual); //TODO: inserto nombre 1
        identificador++;

        Value condicion = visit(ctx.expr());
        if(!condicion.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "etiquetaDoWhile"+identificador,condicion.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "etiquetaDoWhile"+ida;

        miArbol.InsertarRecursivo(nodo, "linstrucciones"+identificador,"linstrucciones", padreActual); //TODO: ingreso nodo lista instrucciones
        padreActual = "linstrucciones"+identificador;
        identificador++;

        bloqueActual = padreActual;
        visitLinstrucciones(ctx.linstrucciones());

        padreActual = "etiquetaDoWhile"+ida;

        miArbol.InsertarRecursivo(nodo, "etiquetaDoWhile"+identificador,ctx.ID().get(1).getText(), padreActual); //TODO: inserto nombre 2
        identificador++;

        bloqueActual = aux;

        return new Value("","",0,0);
    }

    public Value visitSubroutine(GramaticaParser.SubroutineContext ctx) {

        String aux = padreActual; //TODO: para que regrese al padre, luego de ejcutar todo.

        miArbol.InsertarRecursivo(nodo, "subroutine"+identificador, "subroutine", padreActual); //TODO: nodo
        int ida = identificador;
        padreActual = "subroutine"+ida;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "subroutine"+identificador,ctx.ID().get(0).getText(), padreActual); //TODO: inserto nombre 1
        identificador++;

        miArbol.InsertarRecursivo(nodo, "lparametros"+identificador,"lparametros", padreActual); //TODO: nodo lparametros
        int idb = identificador;
        padreActual = "lparametros"+idb;
        identificador++;

        if(ctx.lexpr()!=null){
            for (int i = 0; i < ctx.lexpr().expr().size(); i++){
                Value val = visit(ctx.lexpr().expr().get(i));
                if(!val.value.toString().equals("")){
                    miArbol.InsertarRecursivo(nodo, "lparametros"+identificador, val.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "lparametros"+idb;
            }//
        }

        padreActual = "subroutine"+ida;

        miArbol.InsertarRecursivo(nodo, "ldeclaraciones"+identificador,"ldeclaraciones", padreActual); //TODO: nodo ldeclaraciones
        int idc = identificador;
        padreActual = "ldeclaraciones"+idc;
        identificador++;

        //TODO: recorro lista de parametros, osea cada 'declParametros'
        if (ctx.ldeclP()!=null){
            for (int i = 0; i < ctx.ldeclP().declParametros().size(); i++){

                miArbol.InsertarRecursivo(nodo, "declaracion"+identificador,"declaracion", padreActual); //TODO: nodo parametro
                padreActual = "declaracion"+identificador;
                identificador++;

                miArbol.InsertarRecursivo(nodo, "tipo"+identificador,ctx.ldeclP().declParametros(i).type().getText(), padreActual); //TODO: nodo parametro
                identificador++;

                if(ctx.ldeclP().declParametros(i).valorParametros().getChildCount() == 1){ //TODO: Viene solo nombre de variable normal
                    miArbol.InsertarRecursivo(nodo, "variable"+identificador,"variable", padreActual); //TODO: nodo variable
                    padreActual = "variable"+identificador;
                    identificador++;
                    miArbol.InsertarRecursivo(nodo, "variable"+identificador,ctx.ldeclP().declParametros(i).valorParametros().ID().getText(), padreActual); //TODO: nombre variable
                    identificador++;
                }else if(ctx.ldeclP().declParametros(i).valorParametros().getChildCount() == 4){ //TODO: array 1 dimension arr(a) -> 4 hijos
                    miArbol.InsertarRecursivo(nodo, "decArray1"+identificador,"decArray1", padreActual); //TODO: nodo array 1 dimension
                    padreActual = "decArray1"+identificador;
                    identificador++;
                    miArbol.InsertarRecursivo(nodo, "decArray1"+identificador,ctx.ldeclP().declParametros(i).valorParametros().ID().getText(), padreActual); //TODO: nombre array
                    identificador++;
                    Value indice1 = visit(ctx.ldeclP().declParametros(i).valorParametros().expr().get(0)); //TODO: ingreso expresion
                    if(!indice1.value.toString().equals("")){
                        miArbol.InsertarRecursivo(nodo, "decArray1"+identificador,indice1.value.toString(), padreActual);
                        identificador++;
                    }
                }else if(ctx.ldeclP().declParametros(i).valorParametros().getChildCount() >= 6){ //TODO: array 2 dimension array(a,b) -> 6 hijos
                    miArbol.InsertarRecursivo(nodo, "decArray2"+identificador,"decArray2", padreActual); //TODO: nodo array 1 dimension
                    int idd = identificador;
                    padreActual = "decArray2"+idd;
                    identificador++;
                    miArbol.InsertarRecursivo(nodo, "decArray2"+identificador,ctx.ldeclP().declParametros(i).valorParametros().ID().getText(), padreActual); //TODO: nombre array
                    identificador++;
                    Value indice1 = visit(ctx.ldeclP().declParametros(i).valorParametros().expr().get(0)); //TODO: ingreso expresion
                    if(!indice1.value.toString().equals("")){
                        miArbol.InsertarRecursivo(nodo, "decArray1"+identificador,indice1.value.toString(), padreActual);
                        identificador++;
                    }
                    padreActual = "decArray2"+idd;
                    Value indice2 = visit(ctx.ldeclP().declParametros(i).valorParametros().expr().get(1)); //TODO: ingreso expresion
                    if(!indice2.value.toString().equals("")){
                        miArbol.InsertarRecursivo(nodo, "decArray1"+identificador,indice2.value.toString(), padreActual);
                        identificador++;
                    }
                }

                padreActual = "ldeclaraciones"+idc;
            }
        }

        padreActual = "subroutine"+ida;

        miArbol.InsertarRecursivo(nodo, "linstrucciones"+identificador,"linstrucciones", padreActual); //TODO: ingreso nodo lista instrucciones
        padreActual = "linstrucciones"+identificador;
        identificador++;

        bloqueActual = padreActual;
        visitLinstrucciones(ctx.linstrucciones());

        padreActual = "subroutine"+ida;

        miArbol.InsertarRecursivo(nodo, "subroutine"+identificador,ctx.ID().get(1).getText(), padreActual); //TODO: inserto nombre 2
        identificador++;

        bloqueActual = aux;

        return new Value("","",0,0);
    }

    public Value visitCall(GramaticaParser.CallContext ctx) {

        miArbol.InsertarRecursivo(nodo, "call"+identificador, "call", padreActual); //TODO: nodo
        padreActual = "call"+identificador;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "call"+identificador, ctx.ID().getText(), padreActual); //TODO: nombre sub
        identificador++;

        miArbol.InsertarRecursivo(nodo, "lparametros"+identificador, "lparametros", padreActual); //TODO: nodo lparametros
        int ida = identificador;
        padreActual = "lparametros"+ ida;
        identificador++;

        if(ctx.lexpr()!=null){ //TODO: al menos un parametro
            for (int i = 0; i < ctx.lexpr().expr().size(); i++){
                Value val = visit(ctx.lexpr().expr().get(i));
                if (!val.value.toString().equals("")){
                    miArbol.InsertarRecursivo(nodo, "lparametros"+identificador, val.value.toString(), padreActual); //TODO: nombre sub
                    identificador++;
                }
                padreActual = "lparametros"+ ida;
            }
        }

        return new Value("","",0,0);
    }

    public Value visitFuncion(GramaticaParser.FuncionContext ctx) {

        String aux = padreActual; //TODO: para que regrese al padre, luego de ejcutar todo.

        miArbol.InsertarRecursivo(nodo, "funcion"+identificador, "funcion", padreActual); //TODO: nodo
        int ida = identificador;
        padreActual = "funcion"+ida;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "funcion"+identificador,ctx.ID().get(0).getText(), padreActual); //TODO: inserto nombre 1
        identificador++;

        miArbol.InsertarRecursivo(nodo, "lparametros"+identificador,"lparametros", padreActual); //TODO: nodo lparametros
        int idb = identificador;
        padreActual = "lparametros"+idb;
        identificador++;

        if(ctx.lexpr()!=null){
            for (int i = 0; i < ctx.lexpr().expr().size(); i++){
                Value val = visit(ctx.lexpr().expr().get(i));
                if(!val.value.toString().equals("")){
                    miArbol.InsertarRecursivo(nodo, "lparametros"+identificador, val.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "lparametros"+idb;
            }//
        }

        padreActual = "funcion"+ida;

        miArbol.InsertarRecursivo(nodo, "result"+identificador,"result", padreActual); //TODO: inserta nodo result
        padreActual = "result"+identificador;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "result"+identificador,ctx.ID().get(1).getText(), padreActual); //TODO: inserto nombre 1
        identificador++;

        padreActual = "funcion"+ida;

        miArbol.InsertarRecursivo(nodo, "ldeclaraciones"+identificador,"ldeclaraciones", padreActual); //TODO: nodo ldeclaraciones
        int idc = identificador;
        padreActual = "ldeclaraciones"+idc;
        identificador++;

        if(ctx.ldeclP()!=null){
            //TODO: recorro lista de parametros, osea cada 'declParametros'
            for (int i = 0; i < ctx.ldeclP().declParametros().size(); i++){

                miArbol.InsertarRecursivo(nodo, "declaracion"+identificador,"declaracion", padreActual); //TODO: nodo parametro
                padreActual = "declaracion"+identificador;
                identificador++;

                miArbol.InsertarRecursivo(nodo, "tipo"+identificador,ctx.ldeclP().declParametros(i).type().getText(), padreActual); //TODO: nodo parametro
                identificador++;

                if(ctx.ldeclP().declParametros(i).valorParametros().getChildCount() == 1){ //TODO: Viene solo nombre de variable normal
                    miArbol.InsertarRecursivo(nodo, "variable"+identificador,"variable", padreActual); //TODO: nodo variable
                    padreActual = "variable"+identificador;
                    identificador++;
                    miArbol.InsertarRecursivo(nodo, "variable"+identificador,ctx.ldeclP().declParametros(i).valorParametros().ID().getText(), padreActual); //TODO: nombre variable
                    identificador++;
                }else if(ctx.ldeclP().declParametros(i).valorParametros().getChildCount() == 4){ //TODO: array 1 dimension arr(a) -> 4 hijos
                    miArbol.InsertarRecursivo(nodo, "decArray1"+identificador,"decArray1", padreActual); //TODO: nodo array 1 dimension
                    padreActual = "decArray1"+identificador;
                    identificador++;
                    miArbol.InsertarRecursivo(nodo, "decArray1"+identificador,ctx.ldeclP().declParametros(i).valorParametros().ID().getText(), padreActual); //TODO: nombre array
                    identificador++;
                    Value indice1 = visit(ctx.ldeclP().declParametros(i).valorParametros().expr().get(0)); //TODO: ingreso expresion
                    if(!indice1.value.toString().equals("")){
                        miArbol.InsertarRecursivo(nodo, "decArray1"+identificador,indice1.value.toString(), padreActual);
                        identificador++;
                    }
                }else if(ctx.ldeclP().declParametros(i).valorParametros().getChildCount() >= 6){ //TODO: array 2 dimension array(a,b) -> 6 hijos
                    miArbol.InsertarRecursivo(nodo, "decArray2"+identificador,"decArray2", padreActual); //TODO: nodo array 1 dimension
                    int idd = identificador;
                    padreActual = "decArray2"+idd;
                    identificador++;
                    miArbol.InsertarRecursivo(nodo, "decArray2"+identificador,ctx.ldeclP().declParametros(i).valorParametros().ID().getText(), padreActual); //TODO: nombre array
                    identificador++;
                    Value indice1 = visit(ctx.ldeclP().declParametros(i).valorParametros().expr().get(0)); //TODO: ingreso expresion
                    if(!indice1.value.toString().equals("")){
                        miArbol.InsertarRecursivo(nodo, "decArray1"+identificador,indice1.value.toString(), padreActual);
                        identificador++;
                    }
                    padreActual = "decArray2"+idd;
                    Value indice2 = visit(ctx.ldeclP().declParametros(i).valorParametros().expr().get(1)); //TODO: ingreso expresion
                    if(!indice2.value.toString().equals("")){
                        miArbol.InsertarRecursivo(nodo, "decArray1"+identificador,indice2.value.toString(), padreActual);
                        identificador++;
                    }
                }

                padreActual = "ldeclaraciones"+idc;
            }
        }

        padreActual = "funcion"+ida;

        miArbol.InsertarRecursivo(nodo, "linstrucciones"+identificador,"linstrucciones", padreActual); //TODO: ingreso nodo lista instrucciones
        padreActual = "linstrucciones"+identificador;
        identificador++;

        bloqueActual = padreActual;
        visitLinstrucciones(ctx.linstrucciones());

        padreActual = "funcion"+ida;

        miArbol.InsertarRecursivo(nodo, "subroutine"+identificador,ctx.ID().get(2).getText(), padreActual); //TODO: inserto nombre 2
        identificador++;

        bloqueActual = aux;

        return new Value("","",0,0);
    }

    public Value visitFuncExpr(GramaticaParser.FuncExprContext ctx) {

        miArbol.InsertarRecursivo(nodo, "funcExpr"+identificador, "funcExpr", padreActual); //TODO: nodo
        padreActual = "funcExpr"+identificador;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "funcExpr"+identificador, ctx.ID().getText(), padreActual); //TODO: nombre funcion
        identificador++;

        miArbol.InsertarRecursivo(nodo, "lparametros"+identificador, "lparametros", padreActual); //TODO: nodo lparametros
        int ida = identificador;
        padreActual = "lparametros"+ ida;
        identificador++;

        if(ctx.lexpr()!=null){ //TODO: al menos un parametro
            for (int i = 0; i < ctx.lexpr().expr().size(); i++){
                Value val = visit(ctx.lexpr().expr().get(i));
                if (!val.value.toString().equals("")){
                    miArbol.InsertarRecursivo(nodo, "lparametros"+identificador, val.value.toString(), padreActual); //TODO: nombre sub
                    identificador++;
                }
                padreActual = "lparametros"+ ida;
            }
        }

        return new Value("","",0,0);
    }

    public Value visitArrayAccesoExpr(GramaticaParser.ArrayAccesoExprContext ctx) {

        miArbol.InsertarRecursivo(nodo, "arrayAccesoExpr"+identificador, "arrayAccesoExpr", padreActual); //TODO: nodo expresion
        int ida = identificador;
        padreActual = "arrayAccesoExpr"+ida;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "arrayAccesoExpr"+identificador, ctx.ID().getText(), padreActual); //TODO: nombre array
        identificador++;

        for (int i = 0; i < ctx.lexpr().expr().size(); i++){
            Value val = visit(ctx.lexpr().expr().get(i));
            if(!val.value.toString().equals("")){
                miArbol.InsertarRecursivo(nodo, "arrayAccesoExpr"+identificador, val.value.toString(), padreActual); //TODO: nombre array
                identificador++;
            }
            padreActual = "arrayAccesoExpr"+ida;
        }

        return new Value("","",0,0);
    }

    public Value visitOpExpr(GramaticaParser.OpExprContext ctx) {
        String operacion = ctx.op.getText();

        switch (operacion.charAt(0))
        {
            case '*':
                miArbol.InsertarRecursivo(nodo, "por"+identificador,"*", padreActual); //TODO: guardo nodo *
                int ida = identificador; //TODO: guardo identificador actual
                padreActual = "por"+ida; //TODO: el padre actual sera ahora *
                identificador++;
                Value left = visit(ctx.left); //TODO: visito el nodo izquiero
                if (!left.value.equals("")){ //TODO: esto es debido a que un hijo puede ser expresion, entonces ese retorna "", si no es vacio guardo nodo
                    miArbol.InsertarRecursivo(nodo, "por"+identificador, left.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "por"+ida; //TODO: vuelvo resetear el padre para que vuelva hacer *
                Value right = visit(ctx.right); //TODO: visito el nodo derecho
                if (!right.value.equals("")){ //TODO: esto es debido a que un hijo puede ser expresion, entonces ese retorna "", si no es vacio guardo nodo
                    miArbol.InsertarRecursivo(nodo, "por"+identificador, right.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "por"+ida; ////TODO: vuelvo a resetear el padre

                return  new Value("","",0,0);
            case '/':
                miArbol.InsertarRecursivo(nodo, "div"+identificador,"/", padreActual);
                int ida1 = identificador;
                padreActual = "div"+ida1;
                identificador++;
                Value left1 = visit(ctx.left);
                if (!left1.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "div"+identificador, left1.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "div"+ida1;
                Value right1 = visit(ctx.right);
                if (!right1.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "div"+identificador, right1.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "div"+ida1;
                return  new Value("","",0,0);
            case '+':
                miArbol.InsertarRecursivo(nodo, "mas"+identificador,"+", padreActual);
                int ida2 = identificador;
                padreActual = "mas"+ida2;
                identificador++;
                Value left2 = visit(ctx.left);
                if (!left2.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "mas"+identificador, left2.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "mas"+ida2;
                Value right2 = visit(ctx.right);
                if(!right2.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "mas"+identificador, right2.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "mas"+ida2;
                return  new Value("","",0,0);
            case '-':
                miArbol.InsertarRecursivo(nodo, "menos"+identificador ,"-", padreActual);
                int ida3 = identificador;
                padreActual = "menos"+ida3;
                identificador++;
                Value left3 = visit(ctx.left);
                if(!left3.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "menos"+identificador, left3.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "menos"+ida3;
                Value right3 = visit(ctx.right);
                if(!right3.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "menos"+identificador, right3.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "menos"+ida3;
                return  new Value("","",0,0);
            default:
                System.out.println("Operacion no valida");
        }

        return new Value("","",0,0);
    }

    public Value visitUnaryMinusExpr(GramaticaParser.UnaryMinusExprContext ctx) {
        miArbol.InsertarRecursivo(nodo, "unary"+identificador, "unary", padreActual);
        padreActual = "unary"+identificador;
        identificador++;

        miArbol.InsertarRecursivo(nodo, "unary"+identificador, "-", padreActual); //TODO: inserto el negativo
        identificador++;

        Value val = visit(ctx.expr());
        if (!val.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "unary"+identificador, val.value.toString(), padreActual); //TODO: inserto el negativo
            identificador++;
        }

        return new Value("","",0,0);
    }

    public Value visitPowExpr(GramaticaParser.PowExprContext ctx) {

        miArbol.InsertarRecursivo(nodo, "pow"+identificador,"**", padreActual);
        int ida = identificador;
        padreActual = "pow"+ida;
        identificador++;
        Value left = visit(ctx.left);
        if (!left.value.equals("")){
            miArbol.InsertarRecursivo(nodo, "pow"+identificador, left.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "pow"+ida;
        Value right = visit(ctx.right);
        if (!right.value.equals("")){
            miArbol.InsertarRecursivo(nodo, "pow"+identificador, right.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "pow"+ida;

        return new Value("","",0,0);

    }

    public Value visitNotExpr(GramaticaParser.NotExprContext ctx) {
        miArbol.InsertarRecursivo(nodo, "not"+identificador, ".not.", padreActual);
        padreActual = "not"+identificador;
        identificador++;

        Value val = visit(ctx.expr());

        if (!val.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "not"+identificador, val.value.toString(), padreActual); //TODO: inserto nodo hijo
            identificador++;
        }

        return new Value("","",0,0);

    }

    public Value visitRelationalExpr(GramaticaParser.RelationalExprContext ctx) {

        switch (ctx.op.getType()){
            case GramaticaParser.MAYOR:
                miArbol.InsertarRecursivo(nodo, "mayor"+identificador,">", padreActual);
                int ida = identificador;
                padreActual = "mayor"+ida;
                identificador++;
                Value left = visit(ctx.left);
                if (!left.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "mayor"+identificador, left.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "mayor"+ida;
                Value right = visit(ctx.right);
                if (!right.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "mayor"+identificador, right.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "mayor"+ida;
                return new Value("","",0,0);
            case GramaticaParser.MAYORQUE:
                miArbol.InsertarRecursivo(nodo, "mayorque"+identificador,">=", padreActual);
                int ida1 = identificador;
                padreActual = "mayorque"+ida1;
                identificador++;
                Value left1 = visit(ctx.left);
                if (!left1.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "mayorque"+identificador, left1.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "mayorque"+ida1;
                Value right1 = visit(ctx.right);
                if (!right1.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "mayorque"+identificador, right1.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "mayorque"+ida1;
                return  new Value("","",0,0);
            case GramaticaParser.MENOR:
                miArbol.InsertarRecursivo(nodo, "menor"+identificador,"<", padreActual);
                int ida2 = identificador;
                padreActual = "menor"+ida2;
                identificador++;
                Value left2 = visit(ctx.left);
                if (!left2.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "menor"+identificador, left2.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "menor"+ida2;
                Value right2 = visit(ctx.right);
                if(!right2.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "menor"+identificador, right2.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "menor"+ida2;
                return new Value("","",0,0);
            case GramaticaParser.MENORQUE:
                miArbol.InsertarRecursivo(nodo, "menorque"+identificador ,"<=", padreActual);
                int ida3 = identificador;
                padreActual = "menorque"+ida3;
                identificador++;
                Value left3 = visit(ctx.left);
                if(!left3.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "menorque"+identificador, left3.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "menorque"+ida3;
                Value right3 = visit(ctx.right);
                if(!right3.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "menorque"+identificador, right3.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "menorque"+ida3;
                return  new Value("","",0,0);
            default:
                System.out.println("ERROR en visitRelationalExpr :(");
        }

        return new Value("","",0,0);
    }

    public Value visitEqualityExpr(GramaticaParser.EqualityExprContext ctx) {

        switch (ctx.op.getType()){
            case GramaticaParser.IGUALIGUAL:
                miArbol.InsertarRecursivo(nodo, "igualigual"+identificador, ctx.op.getText(), padreActual);
                int ida = identificador;
                padreActual = "igualigual"+ida;
                identificador++;

                Value left = visit(ctx.left);
                if(!left.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "igualigual"+identificador, left.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "igualigual"+ida;

                Value right = visit(ctx.right);
                if(!right.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "menos"+identificador, right.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "igualigual"+ida;
                return new Value("","",0,0);
            case GramaticaParser.DIFIGUAL:
                miArbol.InsertarRecursivo(nodo, "difigual"+identificador, ctx.op.getText(), padreActual);
                int ida2 = identificador;
                padreActual = "difigual"+ida2;
                identificador++;
                Value left2 = visit(ctx.left);
                if (!left2.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "difigual"+identificador, left2.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "difigual"+ida2;
                Value right2 = visit(ctx.right);
                if(!right2.value.equals("")){
                    miArbol.InsertarRecursivo(nodo, "difigual"+identificador, right2.value.toString(), padreActual);
                    identificador++;
                }
                padreActual = "difigual"+ida2;
                return  new Value("","",0,0);
            default:
                System.out.println("ERROR en visitEqualityExpr :(");
        }

        return new Value("","",0,0);
    }

    public Value visitAndExpr(GramaticaParser.AndExprContext ctx) {
        miArbol.InsertarRecursivo(nodo, "and"+identificador ,".and.", padreActual);
        int ida = identificador;
        padreActual = "and"+ida;
        identificador++;

        Value left = visit(ctx.left);
        if (!left.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "and"+identificador, left.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "and"+ida;

        Value right = visit(ctx.right);
        if (!right.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "and"+identificador, right.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "and"+ida;

        return new Value("","",0,0);

    }

    public Value visitOrExpr(GramaticaParser.OrExprContext ctx) {

        miArbol.InsertarRecursivo(nodo, "or"+identificador ,".or.", padreActual);
        int ida = identificador;
        padreActual = "or"+ida;
        identificador++;

        Value left = visit(ctx.left);
        if (!left.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "or"+identificador, left.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "or"+ida;

        Value right = visit(ctx.right);
        if (!right.value.toString().equals("")){
            miArbol.InsertarRecursivo(nodo, "or"+identificador, right.value.toString(), padreActual);
            identificador++;
        }
        padreActual = "or"+ida;

        return new Value("","",0,0);
    }

    public Value visitParentExpr(GramaticaParser.ParentExprContext ctx) {
        Value val = visit(ctx.expr());
        return new Value("","",0,0);
    }

    public Value visitSizeArr(GramaticaParser.SizeArrContext ctx) {
        miArbol.InsertarRecursivo(nodo, "size"+identificador, "size", padreActual); //TODO: inserto nodo size
        String padreArray = "size"+identificador;
        identificador++;
        miArbol.InsertarRecursivo(nodo, "size"+identificador, ctx.ID().getText(), padreArray); //TODO: inserto nodo hijo (nombre de array)
        identificador++;
        return new Value("");
    }

    public Value visitComplexExpr(GramaticaParser.ComplexExprContext ctx) {
        return new Value(ctx.getText());
    }

    public Value visitCharExpr(GramaticaParser.CharExprContext ctx) {
        String str = ctx.getText();
        str = str.substring(1, str.length() - 1);
        return new Value(str);
    }

    public Value visitLogicalExpr(GramaticaParser.LogicalExprContext ctx) {
        return new Value(ctx.getText());
    }

    public Value visitIdAtom(GramaticaParser.IdAtomContext ctx) {
        return new Value(ctx.ID().getText());
    }

    public Value visitIntegExpr(GramaticaParser.IntegExprContext ctx) {
        return new Value(ctx.getText());
    }

    public Value visitRealExpr(GramaticaParser.RealExprContext ctx) {
        return new Value(ctx.getText());
    }

}