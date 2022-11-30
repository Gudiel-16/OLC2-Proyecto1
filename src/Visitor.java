import Entorno.*;
import Entorno.Simbolo.*;
import Gramatica.*;
import Gramatica.GramaticaBaseVisitor;
import Tipos.Arreglos;
import Tipos.Funciones;
import Tipos.Subrutina;
import Utilidades.Consola;
import Utilidades.Errores;

import java.util.*;

public class Visitor extends GramaticaBaseVisitor<Value> {

    public Queue<Value> colaConcatenar = new LinkedList<Value>();//TODO: Print actual para concatenar lo separado por comas
    public LinkedList<Value> hmDeclaracionVariables = new LinkedList<Value>(); //TODO: Guarda tempralmente la lista de declaracion separado por coma
    public LinkedList<Value> llListaExpresiones = new LinkedList<Value>(); //TODO: Guarda tempralmente la lista de expresiones

    Stack<Entorno> pilaEntornos = new Stack<Entorno>(); //TODO: Guarda los entornos

    public Visitor(Entorno ent){
        this.pilaEntornos.push(ent);
    }

    public Value visitParse(GramaticaParser.ParseContext ctx) {
        return visitLinstrucciones(ctx.linstrucciones());
    }

    public Value visitLinstrucciones(GramaticaParser.LinstruccionesContext ctx) {
        for (GramaticaParser.InstruccionContext ictx: ctx.instruccion()){
            Value val = visitInstruccion(ictx); //TODO: voy guardando lo que retorna, cada instruccion que ejuta, esperando que venga un exit o cycle
            if ((val.value.toString().equals("exit") && val.tipo.equals("exit")) //TODO: verifico si viene exit o cycle para retornar
                ||(val.value.toString().equals("cycle") && val.tipo.equals("cycle"))){
                return val;
            }
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
        visitLinstrucciones(ctx.linstrucciones());
        return new Value("","",0,0);
    }

    public Value visitDeclarationVar(GramaticaParser.DeclarationVarContext ctx) {
        Value tipoVar = visit(ctx.type());

        if(ctx.declarationVar2()!=null){
            visit(ctx.declarationVar2());
        }

        if(ctx.declarationVar3()!=null){
            visit(ctx.declarationVar3());
        }

        Entorno ent = pilaEntornos.peek();

        switch (tipoVar.value.toString().toLowerCase()){
            case "integer":
                for(Value v: hmDeclaracionVariables){
                    //System.out.println(k+"<><>");
                    if(v.value!=null){ //TODO: Se le asigno el valor definido a la variable
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) { //TODO: valido si ya existe la variable en el entorno
                            try {
                                Simbolo nuevo = new Simbolo(v.identificador,"integer", Integer.parseInt(v.value.toString()), TipoSimbolo.Variable);
                                ent.nuevoSimbolo(v.identificador,nuevo);
                            }catch (Exception e){
                                Errores.addError("Error: Una(s) variables(s) no fue declarada correctamente por su tipo:" +
                                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                            }
                        }else{
                            Errores.addError("Error: La variable "+ v.identificador + " ya existe" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        }
                    }else{ //TODO: se asigna valor por defecto
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) { //TODO: valido si ya existe la variable en el entorno
                            Simbolo nuevo = new Simbolo(v.identificador,"integer", (int)0, TipoSimbolo.Variable);
                            ent.nuevoSimbolo(v.identificador,nuevo);
                        }else{
                            Errores.addError("Error: La variable "+ v.identificador + " ya existe" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        }
                        //System.out.println(k + " = " + (int)0);
                    }
                }
                break;
            case "real":
                for(Value v: hmDeclaracionVariables){
                    if(v.value!=null){
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) {
                            try {
                                Simbolo nuevo = new Simbolo(v.identificador,"real", (double)v.value, TipoSimbolo.Variable);
                                ent.nuevoSimbolo(v.identificador,nuevo);
                            }catch (Exception e){
                                Errores.addError("Error: Una(s) variables(s) no fue declarada correctamente por su tipo:" +
                                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                            }
                        }else{
                            Errores.addError("Error: La variable "+ v.identificador + " ya existe" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        }
                    }else{
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) {
                            Simbolo nuevo = new Simbolo(v.identificador,"real", (double)0.00000000, TipoSimbolo.Variable);
                            ent.nuevoSimbolo(v.identificador,nuevo);
                        }else{
                            Errores.addError("Error: La variable "+ v.identificador + " ya existe" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        }
                        //System.out.println(k + " = " + (double)0.00000000);
                    }
                }
                break;
            case "complex":
                for(Value v: hmDeclaracionVariables){
                    if(v.value!=null){
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) {
                            try {
                                Simbolo nuevo = new Simbolo(v.identificador,"complex", Double.parseDouble(v.value.toString()), TipoSimbolo.Variable);
                                ent.nuevoSimbolo(v.identificador,nuevo);
                            }catch (Exception e){
                                Errores.addError("Error: Una(s) variables(s) no fue declarada correctamente por su tipo:" +
                                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                            }
                        }else{
                            Errores.addError("Error: La variable "+ v.identificador + " ya existe" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        }
                    }else{
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) {
                            Simbolo nuevo = new Simbolo(v.identificador,"complex", (double)9.192517926E-43, TipoSimbolo.Variable);
                            ent.nuevoSimbolo(v.identificador,nuevo);
                        }else{
                            Errores.addError("Error: La variable "+ v.identificador + " ya existe" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        }
                        //System.out.println(k + " = " +(double)9.192517926E-43);
                    }
                }
                break;
            case "character":
                for(Value v: hmDeclaracionVariables){
                    if(v.value!=null){
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) {
                            try {
                                Simbolo nuevo = new Simbolo(v.identificador,"character", v.value.toString(), TipoSimbolo.Variable);
                                ent.nuevoSimbolo(v.identificador,nuevo);
                            }catch (Exception e){
                                Errores.addError("Error: Una(s) variables(s) no fue declarada correctamente por su tipo:" +
                                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                            }
                        }else{
                            Errores.addError("Error: La variable "+ v.identificador + " ya existe" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        }
                    }else{
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) {
                            Simbolo nuevo = new Simbolo(v.identificador,"character", " ", TipoSimbolo.Variable);
                            ent.nuevoSimbolo(v.identificador,nuevo);
                        }else{
                            Errores.addError("Error: La variable "+ v.identificador + " ya existe" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        }
                        //System.out.println(k + " = " + " ");
                    }
                }
                break;
            case "logical":
                for(Value v: hmDeclaracionVariables){
                    if(v.value!=null){
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) {
                            try {
                                Simbolo nuevo = new Simbolo(v.identificador,"logical", (boolean)v.value, TipoSimbolo.Variable);
                                ent.nuevoSimbolo(v.identificador,nuevo);
                            }catch (Exception e){
                                Errores.addError("Error: Una(s) variables(s) no fue declarada correctamente por su tipo:" +
                                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                            }
                        }else{
                            Errores.addError("Error: La variable "+ v.identificador + " ya existe" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        }
                    }else{
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) {
                            Simbolo nuevo = new Simbolo(v.identificador,"logical", (boolean)false, TipoSimbolo.Variable);
                            ent.nuevoSimbolo(v.identificador,nuevo);
                        }else{
                            Errores.addError("Error: La variable "+ v.identificador + " ya existe" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        }
                        //System.out.println(k + " = " +(boolean)false);
                    }
                }
                break;
            default:
                System.out.println("Tipo de dato no admitido al declarar variable");
        }

        hmDeclaracionVariables.clear();

        return new Value("");
    }

    public Value visitType(GramaticaParser.TypeContext ctx) {
        return new Value(ctx.getText());
    }

    public Value visitDeclarationVar2(GramaticaParser.DeclarationVar2Context ctx) {
        String idVar = ctx.ID().getText();
        Entorno ent = pilaEntornos.peek();

        //TODO: si trae valor para asignar a variable
        if(ctx.expr()!=null){
            Value val = visit(ctx.expr());
            //System.out.println(idVar+" = "+val.value + " <<<<<");
            hmDeclaracionVariables.add(new Value(idVar,val.value,val.tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine()));
            return new Value("");
        }

        //TODO: si no trae valor para asignar a variable
        if(!ent.TablaSimbolo.containsKey(idVar.toLowerCase())) { //TODO: valido si ya existe la variable en el entorno
            hmDeclaracionVariables.add(new Value(idVar,null,"",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine()));
            //System.out.println(idVar + " <<>>");
        }

        return new Value("");
    }

    public Value visitDeclarationVar3(GramaticaParser.DeclarationVar3Context ctx) {

        if(ctx.declarationVar2()!=null){
            visit(ctx.declarationVar2());
        }

        if(ctx.declarationVar3()!=null){
            visit(ctx.declarationVar3());
        }
        return new Value("");
    }

    public Value visitAsignationVar(GramaticaParser.AsignationVarContext ctx) {
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();
        String idVar = ctx.ID().getText();
        Value val = visit(ctx.expr());

        if(val.value.equals("")){ return new Value("","",0,0); }

        Entorno ent = pilaEntornos.peek();
        Simbolo simb = ent.Buscar(idVar);

        if(simb!=null){
            if(val.tipo.equals(simb.tipo)){
                simb.valor = val.value;
                ent.AsignarValorVariable(idVar, simb);
            }else{
                Errores.addError("Error: La variable " + idVar + " no se le puede asignar un valor que no sea del mismo tipo de dato con que la fue declarada: " +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        }else{
            Errores.addError("Error: La variable " + idVar + " para asignar un valor, no existe: " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }
        return new Value("","",0,0);
    }

    public Value visitPrintt(GramaticaParser.PrinttContext ctx) {
        Value val = visit(ctx.expr());

        if(!val.value.equals("")){
            colaConcatenar.add(val);
        }

        //Hay que imprimir mas de una variable
        if (ctx.printt2()!=null){
            visit(ctx.printt2());
        }

        String txtTemp = "";
        for(Value v: colaConcatenar){
            if(v.tipo.equals("logical")){
                if (Boolean.parseBoolean(v.value.toString())){
                    txtTemp += "T";
                }else{
                    txtTemp += "F";
                }
            }else{
                txtTemp += v.value;
            }
        }
        colaConcatenar.clear();
        Consola.addConsola(txtTemp);

        return Value.VOID;
    }

    public Value visitPrintt2(GramaticaParser.Printt2Context ctx) {

        if(ctx.printt3()!=null){
            visit(ctx.printt3());
        }

        if(ctx.printt2()!=null){
            visit(ctx.printt2());
        }
        return new Value("");
    }

    public Value visitPrintt3(GramaticaParser.Printt3Context ctx) {
        Value val = visit(ctx.expr());
        if (!val.value.equals("")){
            colaConcatenar.add(val);
        }
        return new Value("");
    }

    public Value visitLexpr(GramaticaParser.LexprContext ctx) {

        //TODO: obtengo todas las expresiones de la lista de expresiones
        List<GramaticaParser.ExprContext> expresiones = ctx.expr();

        for (GramaticaParser.ExprContext expresion: expresiones){
            Value val = visit(expresion);
            llListaExpresiones.add(val);
        }

        return new Value("","",0,0);
    }

    public Value visitDecArrExtenso(GramaticaParser.DecArrExtensoContext ctx) {

        Value tipoArr = visit(ctx.type());
        String idArr = ctx.ID().getText();
        Value indice1 = visit(ctx.expr());

        if(indice1.value.equals("")){ return new Value("","",0,0); }

        Entorno ent = pilaEntornos.peek();

        if(ctx.declarationArray2()!=null){ //TODO: es de dos dimensiones
            Value indice2 = visit(ctx.declarationArray2());
            if(indice1.tipo.equals("integer") && indice2.tipo.equals("integer")){ //TODO: valido que la dimension sea integer
                if(!ent.TablaSimbolo.containsKey(idArr.toLowerCase())) { //TODO: valido si ya existe la variable en el entorno
                    switch (tipoArr.value.toString().toLowerCase()){
                        case "integer":
                            int[][] arrI = new int[(int)indice1.value][(int)indice2.value];
                            Arreglos ni = new Arreglos(idArr,arrI,2,false,false);
                            Simbolo nuevoI = new Simbolo(idArr,"integer", ni, TipoSimbolo.Arreglo);
                            ent.nuevoSimbolo(idArr,nuevoI);
                            return new Value("","",0,0);
                        case "real":
                            double[][] arrR = new double[(int)indice1.value][(int)indice2.value];
                            Arreglos nr = new Arreglos(idArr,arrR,2,false,false);
                            Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                            ent.nuevoSimbolo(idArr,nuevoR);
                            return new Value("","",0,0);
                        case "character":
                            char[][] arrC = new char[(int)indice1.value][(int)indice2.value];
                            Arreglos nc = new Arreglos(idArr,arrC,2,false,false);
                            Simbolo nuevoC = new Simbolo(idArr,"character", nc, TipoSimbolo.Arreglo);
                            ent.nuevoSimbolo(idArr,nuevoC);
                            return new Value("","",0,0);
                        default:
                            Errores.addError("Error: El tipo de dato del array "+ idArr + " no es admitido:" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                            return new Value("","",0,0);
                    }
                }else{
                    Errores.addError("Error: El nombre "+ idArr + " del array ya existe" +
                            "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                    return new Value("","",0,0);
                }

            }else{
                Errores.addError("Error: Las dimensiones del array "+ idArr + " tienen que ser tipo integer:" +
                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                return new Value("","",0,0);
            }
        }

        //TODO: es de una dimension
        if(indice1.tipo.equals("integer")){ //TODO: valido que la dimension sea integer
            if(!ent.TablaSimbolo.containsKey(idArr.toLowerCase())) { //TODO: valido si ya existe la variable en el entorno
                switch (tipoArr.value.toString().toLowerCase()){
                    case "integer":
                        int[] arrI = new int[(int)indice1.value];
                        Arreglos ni = new Arreglos(idArr,arrI,1,false,false);
                        Simbolo nuevoI = new Simbolo(idArr,"integer", ni, TipoSimbolo.Arreglo);
                        ent.nuevoSimbolo(idArr,nuevoI);
                        return new Value("","",0,0);
                    case "real":
                        double[] arrR = new double[(int)indice1.value];
                        Arreglos nr = new Arreglos(idArr,arrR,1,false,false);
                        Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                        ent.nuevoSimbolo(idArr,nuevoR);
                        return new Value("","",0,0);
                    case "character":
                        char[] arrC = new char[(int)indice1.value];
                        Arreglos nc = new Arreglos(idArr,arrC,1,false,false);
                        Simbolo nuevoC = new Simbolo(idArr,"character", nc, TipoSimbolo.Arreglo);
                        ent.nuevoSimbolo(idArr,nuevoC);
                        return new Value("","",0,0);
                    default:
                        Errores.addError("Error: El tipo de dato del array "+ idArr + " no es admitido:" +
                                "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        return new Value("","",0,0);
                }
            }else{
                Errores.addError("Error: El nombre "+ idArr + " del array ya existe" +
                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                return new Value("","",0,0);
            }

        }else{
            Errores.addError("Error: Las dimensiones del array "+ idArr + " tienen que ser tipo integer:" +
                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
            return new Value("","",0,0);
        }
    }

    public Value visitDecArrCorto(GramaticaParser.DecArrCortoContext ctx) {
        Value tipoArr = visit(ctx.type());
        String idArr = ctx.ID().getText();
        Value indice1 = visit(ctx.expr());

        if(indice1.value.equals("")){ return new Value("","",0,0); }

        Entorno ent = pilaEntornos.peek();

        if(ctx.declarationArray2()!=null){ //TODO: es de dos dimensiones
            Value indice2 = visit(ctx.declarationArray2());
            if(indice1.tipo.equals("integer") && indice2.tipo.equals("integer")){ //TODO: valido que la dimension sea integer
                if(!ent.TablaSimbolo.containsKey(idArr.toLowerCase())) { //TODO: valido si ya existe la variable en el entorno
                    switch (tipoArr.value.toString().toLowerCase()){
                        case "integer":
                            int[][] arrI = new int[(int)indice1.value][(int)indice2.value];
                            Arreglos ni = new Arreglos(idArr,arrI,2,false,false);
                            Simbolo nuevoI = new Simbolo(idArr,"integer", ni, TipoSimbolo.Arreglo);
                            ent.nuevoSimbolo(idArr,nuevoI);
                            return new Value("","",0,0);
                        case "real":
                            double[][] arrR = new double[(int)indice1.value][(int)indice2.value];
                            Arreglos nr = new Arreglos(idArr,arrR,2,false,false);
                            Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                            ent.nuevoSimbolo(idArr,nuevoR);
                            return new Value("","",0,0);
                        case "character":
                            char[][] arrC = new char[(int)indice1.value][(int)indice2.value];
                            Arreglos nc = new Arreglos(idArr,arrC,2,false,false);
                            Simbolo nuevoC = new Simbolo(idArr,"character", nc, TipoSimbolo.Arreglo);
                            ent.nuevoSimbolo(idArr,nuevoC);
                            return new Value("","",0,0);
                        default:
                            Errores.addError("Error: El tipo de dato del array "+ idArr + " no es admitido:" +
                                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                            return new Value("","",0,0);
                    }
                }else{
                    Errores.addError("Error: El nombre "+ idArr + " del array ya existe" +
                            "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                    return new Value("","",0,0);
                }

            }else{
                Errores.addError("Error: Las dimensiones del array "+ idArr + " tienen que ser tipo integer:" +
                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                return new Value("","",0,0);
            }
        }

        //TODO: es de una dimension
        if(indice1.tipo.equals("integer")){ //TODO: valido que la dimension sea integer
            if(!ent.TablaSimbolo.containsKey(idArr.toLowerCase())) { //TODO: valido si ya existe la variable en el entorno
                switch (tipoArr.value.toString().toLowerCase()){
                    case "integer":
                        int[] arrI = new int[(int)indice1.value];
                        Arreglos ni = new Arreglos(idArr,arrI,1,false,false);
                        Simbolo nuevoI = new Simbolo(idArr,"integer", ni, TipoSimbolo.Arreglo);
                        ent.nuevoSimbolo(idArr,nuevoI);
                        return new Value("","",0,0);
                    case "real":
                        double[] arrR = new double[(int)indice1.value];
                        Arreglos nr = new Arreglos(idArr,arrR,1,false,false);
                        Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                        ent.nuevoSimbolo(idArr,nuevoR);
                        return new Value("","",0,0);
                    case "character":
                        char[] arrC = new char[(int)indice1.value];
                        Arreglos nc = new Arreglos(idArr,arrC,1,false,false);
                        Simbolo nuevoC = new Simbolo(idArr,"character", nc, TipoSimbolo.Arreglo);
                        ent.nuevoSimbolo(idArr,nuevoC);
                        return new Value("","",0,0);
                    default:
                        Errores.addError("Error: El tipo de dato del array "+ idArr + " no es admitido:" +
                                "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        return new Value("","",0,0);
                }
            }else{
                Errores.addError("Error: El nombre "+ idArr + " del array ya existe" +
                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                return new Value("","",0,0);
            }

        }else{
            Errores.addError("Error: Las dimensiones del array "+ idArr + " tienen que ser tipo integer:" +
                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
            return new Value("","",0,0);
        }
    }

    public Value visitDeclarationArray2(GramaticaParser.DeclarationArray2Context ctx) {
        return visit(ctx.expr());
    }

    public Value visitAsigArrList(GramaticaParser.AsigArrListContext ctx) {
        String idArr = ctx.ID().getText();
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        Entorno ent = pilaEntornos.peek();

        Simbolo sim = ent.Buscar(ctx.ID().getText());
        if(sim!=null){
            if(sim.tipoSimbolo.name().toLowerCase().equals("arreglo")){
                Arreglos objArr = (Arreglos) sim.valor;
                if(objArr.dimensiones==1){
                    if(!objArr.esDinamico){
                        if(sim.tipo.equals("integer")){
                            int[] miArr = (int[]) objArr.valor;
                            if(miArr.length==ctx.lexpr().expr().size()){
                                for (int i=0; i<miArr.length;i++){
                                    Value val = visit(ctx.lexpr().expr().get(i));
                                    if(val.tipo.equals("integer")){
                                        miArr[i] = (int)val.value;
                                    }else{
                                        Errores.addError("El arreglo " + ctx.ID().getText() + " es tipo integer, debe ingresar valores integer:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }
                                Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                Simbolo nuevoR = new Simbolo(idArr,"integer", nr, TipoSimbolo.Arreglo);
                                ent.AsignarValorVariable(idArr,nuevoR);
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else if(sim.tipo.equals("real")){
                            double[] miArr = (double[]) objArr.valor;
                            if(miArr.length==ctx.lexpr().expr().size()){
                                for (int i=0; i<miArr.length;i++){
                                    Value val = visit(ctx.lexpr().expr().get(i));
                                    if(val.tipo.equals("real")){
                                        miArr[i] = (double)val.value;
                                    }else{
                                        Errores.addError("El arreglo " + ctx.ID().getText() + " es tipo real, debe ingresar valores real:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }
                                Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                                ent.AsignarValorVariable(idArr,nuevoR);
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else if(sim.tipo.equals("character")){
                            char[] miArr = (char[]) objArr.valor;
                            if(miArr.length==ctx.lexpr().expr().size()){
                                for (int i=0; i<miArr.length;i++){
                                    Value val = visit(ctx.lexpr().expr().get(i));
                                    if(val.tipo.equals("character")){
                                        miArr[i] = val.value.toString().charAt(0);
                                    }else{
                                        Errores.addError("El arreglo " + ctx.ID().getText() + " es tipo character, debe ingresar valores character:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }
                                Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                Simbolo nuevoR = new Simbolo(idArr,"character", nr, TipoSimbolo.Arreglo);
                                ent.AsignarValorVariable(idArr,nuevoR);
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }
                    }else{
                        Errores.addError("Error: Esta tratando de asignar valores a un arreglo dinamico " + ctx.ID().getText() +
                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                    }
                }else{
                    Errores.addError("Error: Esta tratando de asignar valores a una array mayor de 1 dimension: " + ctx.ID().getText() +
                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                }
            }else{
                Errores.addError("Error: Esta tratando de asignar valores a " + ctx.ID().getText() + " cuando no es arreglo: " +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        }else{
            Errores.addError("Error: El array " + ctx.ID().getText() + " no existe: " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }

        return new Value("","",0,0);
    }

    public Value visitAsigArrPosEspecifico(GramaticaParser.AsigArrPosEspecificoContext ctx) {
        String idArr = ctx.ID().getText();
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        Entorno ent = pilaEntornos.peek();

        Simbolo sim = ent.Buscar(ctx.ID().getText());
        if(sim!=null){
            if(sim.tipoSimbolo.name().toLowerCase().equals("arreglo")){
                Arreglos objArr = (Arreglos) sim.valor;
                if(objArr.dimensiones==1){
                    if(!objArr.esDinamico){
                        if(sim.tipo.equals("integer")){
                            int[] miArr = (int[]) objArr.valor;
                            if(ctx.lexpr().expr().size()==1){
                                Value val = visit(ctx.lexpr().expr().get(0));//Todo: valor que va entre los corchetes del array
                                if(val.tipo.equals("integer")){
                                    Value valAsig = visit(ctx.expr()); //TODO: expresion asignar al arreglo
                                    if(valAsig.tipo.equals("integer")){
                                        try {
                                            miArr[(int)val.value-1] = (int)valAsig.value;
                                            Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                            Simbolo nuevoR = new Simbolo(idArr,"integer", nr, TipoSimbolo.Arreglo);
                                            ent.AsignarValorVariable(idArr,nuevoR);
                                            return new Value("","",0,0);
                                        }catch (Exception e){
                                            Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo integer, debe asignar valores integer:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else if(sim.tipo.equals("real")){
                            double[] miArr = (double[]) objArr.valor;
                            if(ctx.lexpr().expr().size()==1){
                                Value val = visit(ctx.lexpr().expr().get(0));//Todo: valor que va entre los corchetes del array
                                if(val.tipo.equals("integer")){
                                    Value valAsig = visit(ctx.expr());//TODO: expresion asignar al arreglo
                                    if(valAsig.tipo.equals("real")){
                                        try {
                                            miArr[(int)val.value-1] = (double)valAsig.value;
                                            Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                            Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                                            ent.AsignarValorVariable(idArr,nuevoR);
                                            return new Value("","",0,0);
                                        }catch (Exception e){
                                            Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo real, debe asignar valores real:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else if(sim.tipo.equals("character")){
                            char[] miArr = (char[]) objArr.valor;
                            if(ctx.lexpr().expr().size()==1){
                                Value val = visit(ctx.lexpr().expr().get(0));//Todo: valor que va entre los corchetes del array
                                if(val.tipo.equals("integer")){
                                    Value valAsig = visit(ctx.expr());//TODO: expresion asignar al arreglo
                                    if(valAsig.tipo.equals("character") && valAsig.value.toString().length()==1){
                                        try {
                                            miArr[(int)val.value-1] = valAsig.value.toString().charAt(0);
                                            Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                            Simbolo nuevoR = new Simbolo(idArr,"character", nr, TipoSimbolo.Arreglo);
                                            ent.AsignarValorVariable(idArr,nuevoR);
                                            return new Value("","",0,0);
                                        }catch (Exception e){
                                            //System.out.println(e);
                                            Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo character, debe asignar valores character, o el valor de dos o mas caracteres:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }
                    }else{ //TODO: es dinamico
                        if(objArr.deAllocate){
                            if(sim.tipo.equals("integer")){
                                int[] miArr = (int[]) objArr.valor;
                                if(ctx.lexpr().expr().size()==1){
                                    Value val = visit(ctx.lexpr().expr().get(0));//Todo: valor que va entre los corchetes del array
                                    if(val.tipo.equals("integer")){
                                        Value valAsig = visit(ctx.expr()); //TODO: expresion asignar al arreglo
                                        if(valAsig.tipo.equals("integer")){
                                            try {
                                                miArr[(int)val.value-1] = (int)valAsig.value;
                                                Arreglos nr = new Arreglos(idArr,miArr,1,true,true);
                                                Simbolo nuevoR = new Simbolo(idArr,"integer", nr, TipoSimbolo.Arreglo);
                                                ent.AsignarValorVariable(idArr,nuevoR);
                                                return new Value("","",0,0);
                                            }catch (Exception e){
                                                Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                return new Value("","",0,0);
                                            }
                                        }else{
                                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo integer, debe asignar valores integer:" +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                }
                            }else if(sim.tipo.equals("real")){
                                double[] miArr = (double[]) objArr.valor;
                                if(ctx.lexpr().expr().size()==1){
                                    Value val = visit(ctx.lexpr().expr().get(0));//Todo: valor que va entre los corchetes del array
                                    if(val.tipo.equals("integer")){
                                        Value valAsig = visit(ctx.expr());//TODO: expresion asignar al arreglo
                                        if(valAsig.tipo.equals("real")){
                                            try {
                                                miArr[(int)val.value-1] = (double)valAsig.value;
                                                Arreglos nr = new Arreglos(idArr,miArr,1,true,true);
                                                Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                                                ent.AsignarValorVariable(idArr,nuevoR);
                                                return new Value("","",0,0);
                                            }catch (Exception e){
                                                Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                return new Value("","",0,0);
                                            }
                                        }else{
                                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo real, debe asignar valores real:" +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                }
                            }else if(sim.tipo.equals("character")){
                                char[] miArr = (char[]) objArr.valor;
                                if(ctx.lexpr().expr().size()==1){
                                    Value val = visit(ctx.lexpr().expr().get(0));//Todo: valor que va entre los corchetes del array
                                    if(val.tipo.equals("integer")){
                                        Value valAsig = visit(ctx.expr());//TODO: expresion asignar al arreglo
                                        if(valAsig.tipo.equals("character") && valAsig.value.toString().length()==1){
                                            try {
                                                miArr[(int)val.value-1] = valAsig.value.toString().charAt(0);
                                                Arreglos nr = new Arreglos(idArr,miArr,1,true,true);
                                                Simbolo nuevoR = new Simbolo(idArr,"character", nr, TipoSimbolo.Arreglo);
                                                ent.AsignarValorVariable(idArr,nuevoR);
                                                return new Value("","",0,0);
                                            }catch (Exception e){
                                                //System.out.println(e);
                                                Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                return new Value("","",0,0);
                                            }
                                        }else{
                                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo real, debe asignar valores real, o el valor de dos o mas caracteres:" +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                }
                            }
                        }else{
                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no tiene un tamano asignado, realizar un allocate: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }
                } else if(objArr.dimensiones==2){
                    if(!objArr.esDinamico){
                        if(sim.tipo.equals("integer")){
                            int[][] miArr = (int[][]) objArr.valor;
                            if(ctx.lexpr().expr().size()==2){
                                Value val = visit(ctx.lexpr().expr().get(0));//Todo: indice 1 del array
                                Value val2 = visit(ctx.lexpr().expr().get(1));//Todo: indice 2 del array
                                if(val.tipo.equals("integer") && val2.tipo.equals("integer")){
                                    Value valAsig = visit(ctx.expr()); //TODO: expresion asignar al arreglo
                                    if(valAsig.tipo.equals("integer")){
                                        try {
                                            miArr[(int)val.value-1][(int)val2.value-1] = (int)valAsig.value;
                                            Arreglos nr = new Arreglos(idArr,miArr,2,false,false);
                                            Simbolo nuevoR = new Simbolo(idArr,"integer", nr, TipoSimbolo.Arreglo);
                                            ent.AsignarValorVariable(idArr,nuevoR);
                                            return new Value("","",0,0);
                                        }catch (Exception e){
                                            Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo integer, debe asignar valores integer:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else if(sim.tipo.equals("real")){
                            double[][] miArr = (double[][]) objArr.valor;
                            if(ctx.lexpr().expr().size()==2){
                                Value val = visit(ctx.lexpr().expr().get(0));//Todo: indice 1 del array
                                Value val2 = visit(ctx.lexpr().expr().get(1));//Todo: indice 2 del array
                                if(val.tipo.equals("integer") && val2.tipo.equals("integer")){
                                    Value valAsig = visit(ctx.expr());//TODO: expresion asignar al arreglo
                                    if(valAsig.tipo.equals("real")){
                                        try {
                                            miArr[(int)val.value-1][(int)val2.value-1] = (double)valAsig.value;
                                            Arreglos nr = new Arreglos(idArr,miArr,2,false,false);
                                            Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                                            ent.AsignarValorVariable(idArr,nuevoR);
                                            return new Value("","",0,0);
                                        }catch (Exception e){
                                            Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo real, debe asignar valores real:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else if(sim.tipo.equals("character")){
                            char[][] miArr = (char[][]) objArr.valor;
                            if(ctx.lexpr().expr().size()==2){
                                Value val = visit(ctx.lexpr().expr().get(0));//Todo: indice 1 del array
                                Value val2 = visit(ctx.lexpr().expr().get(1));//Todo: indice 2 del array
                                if(val.tipo.equals("integer") && val2.tipo.equals("integer")){
                                    Value valAsig = visit(ctx.expr());//TODO: expresion asignar al arreglo
                                    if(valAsig.tipo.equals("character") && valAsig.value.toString().length()==1){
                                        try {
                                            miArr[(int)val.value-1][(int)val2.value-1] = valAsig.value.toString().charAt(0);
                                            Arreglos nr = new Arreglos(idArr,miArr,2,false,false);
                                            Simbolo nuevoR = new Simbolo(idArr,"character", nr, TipoSimbolo.Arreglo);
                                            ent.AsignarValorVariable(idArr,nuevoR);
                                            return new Value("","",0,0);
                                        }catch (Exception e){
                                            //System.out.println(e);
                                            Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo real, debe asignar valores real, o el valor de dos o mas caracteres:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }
                    }else{ //TODO: es dinamico
                        if(objArr.deAllocate){
                            if(sim.tipo.equals("integer")){
                                int[][] miArr = (int[][]) objArr.valor;
                                if(ctx.lexpr().expr().size()==2){
                                    Value val = visit(ctx.lexpr().expr().get(0));//Todo: indice 1 del array
                                    Value val2 = visit(ctx.lexpr().expr().get(1));//Todo: indice 2 del array
                                    if(val.tipo.equals("integer") && val2.tipo.equals("integer")){
                                        Value valAsig = visit(ctx.expr()); //TODO: expresion asignar al arreglo
                                        if(valAsig.tipo.equals("integer")){
                                            try {
                                                miArr[(int)val.value-1][(int)val2.value-1] = (int)valAsig.value;
                                                Arreglos nr = new Arreglos(idArr,miArr,2,true,true);
                                                Simbolo nuevoR = new Simbolo(idArr,"integer", nr, TipoSimbolo.Arreglo);
                                                ent.AsignarValorVariable(idArr,nuevoR);
                                                return new Value("","",0,0);
                                            }catch (Exception e){
                                                Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                return new Value("","",0,0);
                                            }
                                        }else{
                                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo integer, debe asignar valores integer:" +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                }
                            }else if(sim.tipo.equals("real")){
                                double[][] miArr = (double[][]) objArr.valor;
                                if(ctx.lexpr().expr().size()==2){
                                    Value val = visit(ctx.lexpr().expr().get(0));//Todo: indice 1 del array
                                    Value val2 = visit(ctx.lexpr().expr().get(1));//Todo: indice 2 del array
                                    if(val.tipo.equals("integer") && val2.tipo.equals("integer")){
                                        Value valAsig = visit(ctx.expr());//TODO: expresion asignar al arreglo
                                        if(valAsig.tipo.equals("real")){
                                            try {
                                                miArr[(int)val.value-1][(int)val2.value-1] = (double)valAsig.value;
                                                Arreglos nr = new Arreglos(idArr,miArr,2,true,true);
                                                Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                                                ent.AsignarValorVariable(idArr,nuevoR);
                                                return new Value("","",0,0);
                                            }catch (Exception e){
                                                Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                return new Value("","",0,0);
                                            }
                                        }else{
                                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo real, debe asignar valores real:" +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                }
                            }else if(sim.tipo.equals("character")){
                                char[][] miArr = (char[][]) objArr.valor;
                                if(ctx.lexpr().expr().size()==2){
                                    Value val = visit(ctx.lexpr().expr().get(0));//Todo: indice 1 del array
                                    Value val2 = visit(ctx.lexpr().expr().get(1));//Todo: indice 2 del array
                                    if(val.tipo.equals("integer") && val2.tipo.equals("integer")){
                                        Value valAsig = visit(ctx.expr());//TODO: expresion asignar al arreglo
                                        if(valAsig.tipo.equals("character") && valAsig.value.toString().length()==1){
                                            try {
                                                miArr[(int)val.value-1][(int)val2.value-1] = valAsig.value.toString().charAt(0);
                                                Arreglos nr = new Arreglos(idArr,miArr,2,true,true);
                                                Simbolo nuevoR = new Simbolo(idArr,"character", nr, TipoSimbolo.Arreglo);
                                                ent.AsignarValorVariable(idArr,nuevoR);
                                                return new Value("","",0,0);
                                            }catch (Exception e){
                                                //System.out.println(e);
                                                Errores.addError("Error: El indice no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                return new Value("","",0,0);
                                            }
                                        }else{
                                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " es tipo character, debe asignar valores character, o el valor de dos o mas caracteres:" +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: Los indices de los arreglos deben ser tipo integer: " + ctx.ID().getText() +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                }
                            }
                        }else{
                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no tiene un tamano asignado, realizar un allocate: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }
                }
            }else{
                Errores.addError("Error: Esta tratando de asignar valores a " + ctx.ID().getText() + " cuando no es arreglo: " +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        }else{
            Errores.addError("Error: El array " + ctx.ID().getText() + " no existe: " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }

        return new Value("","",0,0);
    }

    public Value visitDeclarationArrayDinamico(GramaticaParser.DeclarationArrayDinamicoContext ctx) {
        Value tipoArr = visit(ctx.type());
        String idArr = ctx.ID().getText();

        Entorno ent = pilaEntornos.peek();

        if(ctx.declarationArrayDinamico2()!=null){ //TODO: es de dos dimensiones
            if(!ent.TablaSimbolo.containsKey(idArr.toLowerCase())) { //TODO: valido si ya existe la variable en el entorno
                switch (tipoArr.value.toString().toLowerCase()){
                    case "integer":
                        int[][] arrI = new int[(int)1][(int)1];
                        Arreglos ni = new Arreglos(idArr,arrI,2,true,false);
                        Simbolo nuevoI = new Simbolo(idArr,"integer", ni, TipoSimbolo.Arreglo);
                        ent.nuevoSimbolo(idArr,nuevoI);
                        return new Value("","",0,0);
                    case "real":
                        double[][] arrR = new double[(int)1][(int)1];
                        Arreglos nr = new Arreglos(idArr,arrR,2,true,false);
                        Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                        ent.nuevoSimbolo(idArr,nuevoR);
                        return new Value("","",0,0);
                    case "character":
                        char[][] arrC = new char[(int)1][(int)1];
                        Arreglos nc = new Arreglos(idArr,arrC,2,true,false);
                        Simbolo nuevoC = new Simbolo(idArr,"character", nc, TipoSimbolo.Arreglo);
                        ent.nuevoSimbolo(idArr,nuevoC);
                        return new Value("","",0,0);
                    default:
                        Errores.addError("Error: El tipo de dato del array "+ idArr + " no es admitido:" +
                                "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                        return new Value("","",0,0);
                }
            }else{
                Errores.addError("Error: El nombre "+ idArr + " del array ya existe" +
                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                return new Value("","",0,0);
            }
        }

        //TODO: es de una dimension
        if(!ent.TablaSimbolo.containsKey(idArr.toLowerCase())) { //TODO: valido si ya existe la variable en el entorno
            switch (tipoArr.value.toString().toLowerCase()){
                case "integer":
                    int[][] arrI = new int[(int)1][(int)1];
                    Arreglos ni = new Arreglos(idArr,arrI,1,true,false);
                    Simbolo nuevoI = new Simbolo(idArr,"integer", ni, TipoSimbolo.Arreglo);
                    ent.nuevoSimbolo(idArr,nuevoI);
                    return new Value("","",0,0);
                case "real":
                    double[][] arrR = new double[(int)1][(int)1];
                    Arreglos nr = new Arreglos(idArr,arrR,1,true,false);
                    Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo);
                    ent.nuevoSimbolo(idArr,nuevoR);
                    return new Value("","",0,0);
                case "character":
                    char[][] arrC = new char[(int)1][(int)1];
                    Arreglos nc = new Arreglos(idArr,arrC,1,true,false);
                    Simbolo nuevoC = new Simbolo(idArr,"character", nc, TipoSimbolo.Arreglo);
                    ent.nuevoSimbolo(idArr,nuevoC);
                    return new Value("","",0,0);
                default:
                    Errores.addError("Error: El tipo de dato del array "+ idArr + " no es admitido:" +
                            "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                    return new Value("","",0,0);
            }
        }else{
            Errores.addError("Error: El nombre "+ idArr + " del array ya existe" +
                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
            return new Value("","",0,0);
        }
    }

    public Value visitAsignationArrayDinamico(GramaticaParser.AsignationArrayDinamicoContext ctx) {
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();
        String idArr = ctx.ID().getText();
        Value indice1 = visit(ctx.expr());

        if(indice1.value.equals("")){ return new Value("","",0,0); }

        Entorno ent = pilaEntornos.peek();

        if(ctx.asignationArrayDinamico2()!=null){ //TODO: es de dos dimensiones
            Value indice2 = visit(ctx.asignationArrayDinamico2());

            if(indice1.tipo.equals("integer") && indice2.tipo.equals("integer")) { //TODO: valido que la dimension sea integer
                Simbolo sim = ent.Buscar(ctx.ID().getText());
                if(sim!=null){
                    if (sim.tipoSimbolo.name().toLowerCase().equals("arreglo")) { //TODO: si es tipo arreglo
                        if(sim.tipo.toLowerCase().equals("integer")){
                            Arreglos objArr = (Arreglos) sim.valor;
                            if(objArr.dimensiones==2){
                                if(objArr.esDinamico) {// TODO: SI es dinamico
                                    if(!objArr.deAllocate){
                                        int[][] arrI = new int[(int)indice1.value][(int)indice2.value];
                                        Arreglos ni = new Arreglos(idArr, arrI,2,true,true);
                                        Simbolo nuevoI = new Simbolo(idArr,"integer",ni,TipoSimbolo.Arreglo);
                                        ent.AsignarValorVariable(idArr,nuevoI);
                                        return new Value("","",0,0);
                                    }else{
                                        Errores.addError("Error: El arreglo " + ctx.ID().getText() + " ya tiene asignado un valor, realizar un deallocate:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else if(sim.tipo.toLowerCase().equals("real")){
                            Arreglos objArr = (Arreglos) sim.valor;
                            if(objArr.dimensiones==2){
                                if(objArr.esDinamico) {// TODO: SI es dinamico
                                    if(!objArr.deAllocate){
                                        double[][] arrI = new double[(int)indice1.value][(int)indice2.value];
                                        Arreglos ni = new Arreglos(idArr, arrI,2,true,true);
                                        Simbolo nuevoI = new Simbolo(idArr,"real",ni,TipoSimbolo.Arreglo);
                                        ent.AsignarValorVariable(idArr,nuevoI);
                                        return new Value("","",0,0);
                                    }else{
                                        Errores.addError("Error: El arreglo " + ctx.ID().getText() + " ya tiene asignado un valor, realizar un deallocate:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else if(sim.tipo.toLowerCase().equals("character")){
                            Arreglos objArr = (Arreglos) sim.valor;
                            if(objArr.dimensiones==2){
                                if(objArr.esDinamico) {// TODO: SI es dinamico
                                    if(!objArr.deAllocate){
                                        char[][] arrI = new char[(int)indice1.value][(int)indice2.value];
                                        Arreglos ni = new Arreglos(idArr, arrI,2,true,true);
                                        Simbolo nuevoI = new Simbolo(idArr,"character",ni,TipoSimbolo.Arreglo);
                                        ent.AsignarValorVariable(idArr,nuevoI);
                                        return new Value("","",0,0);
                                    }else{
                                        Errores.addError("Error: El arreglo " + ctx.ID().getText() + " ya tiene asignado un valor, realizar un deallocate:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }else{
                                    Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }
                    }else{
                        Errores.addError("Error: Esta tratando de asignar valores, a una variable que no es arreglo: " + ctx.ID().getText() +
                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        return new Value("","",0,0);
                    }
                }else{
                    Errores.addError("Error: El array " + ctx.ID().getText() + " no existe: " +
                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                    return new Value("","",0,0);
                }
            }else{
                Errores.addError("Error: Las dimensiones del array "+ idArr + " tienen que ser tipo integer:" +
                        "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
                return new Value("","",0,0);
            }
        }

        //TODO: es de una dimension
        if(indice1.tipo.equals("integer")) { //TODO: valido que la dimension sea integer
            Simbolo sim = ent.Buscar(ctx.ID().getText());
            if(sim!=null){
                if (sim.tipoSimbolo.name().toLowerCase().equals("arreglo")) { //TODO: si es tipo arreglo
                    if(sim.tipo.toLowerCase().equals("integer")){
                        Arreglos objArr = (Arreglos) sim.valor;
                        if(objArr.dimensiones==1){
                            if(objArr.esDinamico) {// TODO: SI es dinamico
                                if(!objArr.deAllocate){
                                    int[] arrI = new int[(int)indice1.value];
                                    Arreglos ni = new Arreglos(idArr, arrI,1,true,true);
                                    Simbolo nuevoI = new Simbolo(idArr,"integer",ni,TipoSimbolo.Arreglo);
                                    ent.AsignarValorVariable(idArr,nuevoI);
                                    return new Value("","",0,0);
                                }else{
                                    Errores.addError("Error: El arreglo " + ctx.ID().getText() + " ya tiene asignado un valor, realizar un deallocate:" +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else{
                            Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            return new Value("","",0,0);
                        }
                    }else if(sim.tipo.toLowerCase().equals("real")){
                        Arreglos objArr = (Arreglos) sim.valor;
                        if(objArr.dimensiones==1){
                            if(objArr.esDinamico) {// TODO: SI es dinamico
                                if(!objArr.deAllocate){
                                    double[] arrI = new double[(int)indice1.value];
                                    Arreglos ni = new Arreglos(idArr, arrI,1,true,true);
                                    Simbolo nuevoI = new Simbolo(idArr,"real",ni,TipoSimbolo.Arreglo);
                                    ent.AsignarValorVariable(idArr,nuevoI);
                                    return new Value("","",0,0);
                                }else{
                                    Errores.addError("Error: El arreglo " + ctx.ID().getText() + " ya tiene asignado un valor, realizar un deallocate:" +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else{
                            Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            return new Value("","",0,0);
                        }
                    }else if(sim.tipo.toLowerCase().equals("character")){
                        Arreglos objArr = (Arreglos) sim.valor;
                        if(objArr.dimensiones==1){
                            if(objArr.esDinamico) {// TODO: SI es dinamico
                                if(!objArr.deAllocate){
                                    char[] arrI = new char[(int)indice1.value];
                                    Arreglos ni = new Arreglos(idArr, arrI,1,true,true);
                                    Simbolo nuevoI = new Simbolo(idArr,"character",ni,TipoSimbolo.Arreglo);
                                    ent.AsignarValorVariable(idArr,nuevoI);
                                    return new Value("","",0,0);
                                }else{
                                    Errores.addError("Error: El arreglo " + ctx.ID().getText() + " ya tiene asignado un valor, realizar un deallocate:" +
                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    return new Value("","",0,0);
                                }
                            }else{
                                Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else{
                            Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            return new Value("","",0,0);
                        }
                    }
                }else{
                    Errores.addError("Error: Esta tratando de asignar valores, a una variable que no es arreglo: " + ctx.ID().getText() +
                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                    return new Value("","",0,0);
                }
            }else{
                Errores.addError("Error: El array " + ctx.ID().getText() + " no existe: " +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                return new Value("","",0,0);
            }
        }else{
            Errores.addError("Error: Las dimensiones del array "+ idArr + " tienen que ser tipo integer:" +
                    "\n     Linea: " + ctx.getStart().getLine() + ", Columna: " + ctx.getStart().getCharPositionInLine());
            return new Value("","",0,0);
        }

        return new Value("","",0,0);
    }

    public Value visitAsignationArrayDinamico2(GramaticaParser.AsignationArrayDinamico2Context ctx) {
        return visit(ctx.expr());
    }

    public Value visitDesAsignationArrayDinamico(GramaticaParser.DesAsignationArrayDinamicoContext ctx) {
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();
        String idArr = ctx.ID().getText();

        Entorno ent = pilaEntornos.peek();

        Simbolo sim = ent.Buscar(idArr);
        if(sim!=null){
            if (sim.tipoSimbolo.name().toLowerCase().equals("arreglo")) { //TODO: si es tipo arreglo
                if(sim.tipo.toLowerCase().equals("integer")){
                    Arreglos objArr = (Arreglos) sim.valor;
                    if(objArr.dimensiones==2){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                int[][] arrI = new int[(int)1][(int)1];
                                Arreglos ni = new Arreglos(idArr, arrI,2,true,false);
                                Simbolo nuevoI = new Simbolo(idArr,"integer",ni,TipoSimbolo.Arreglo);
                                ent.AsignarValorVariable(idArr,nuevoI);
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no tiene asignado un valor, realizar un allocate:" +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else{
                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            return new Value("","",0,0);
                        }
                    }else if(objArr.dimensiones==1){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                int[] arrI = new int[(int)1];
                                Arreglos ni = new Arreglos(idArr, arrI,1,true,false);
                                Simbolo nuevoI = new Simbolo(idArr,"integer",ni,TipoSimbolo.Arreglo);
                                ent.AsignarValorVariable(idArr,nuevoI);
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no tiene asignado un valor, realizar un allocate:" +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else{
                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            return new Value("","",0,0);
                        }
                    }
                }else if(sim.tipo.toLowerCase().equals("real")){
                    Arreglos objArr = (Arreglos) sim.valor;
                    if(objArr.dimensiones==2){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                double[][] arrI = new double[(int)1][(int)1];
                                Arreglos ni = new Arreglos(idArr, arrI,2,true,false);
                                Simbolo nuevoI = new Simbolo(idArr,"real",ni,TipoSimbolo.Arreglo);
                                ent.AsignarValorVariable(idArr,nuevoI);
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no tiene asignado un valor, realizar un allocate:" +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else{
                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            return new Value("","",0,0);
                        }
                    }else if(objArr.dimensiones==1){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                double[] arrI = new double[(int)1];
                                Arreglos ni = new Arreglos(idArr, arrI,1,true,false);
                                Simbolo nuevoI = new Simbolo(idArr,"real",ni,TipoSimbolo.Arreglo);
                                ent.AsignarValorVariable(idArr,nuevoI);
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no tiene asignado un valor, realizar un allocate:" +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else{
                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            return new Value("","",0,0);
                        }
                    }
                }else if(sim.tipo.toLowerCase().equals("character")){
                    Arreglos objArr = (Arreglos) sim.valor;
                    if(objArr.dimensiones==2){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                char[][] arrI = new char[(int)1][(int)1];
                                Arreglos ni = new Arreglos(idArr, arrI,2,true,false);
                                Simbolo nuevoI = new Simbolo(idArr,"character",ni,TipoSimbolo.Arreglo);
                                ent.AsignarValorVariable(idArr,nuevoI);
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no tiene asignado un valor, realizar un allocate:" +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else{
                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            return new Value("","",0,0);
                        }
                    }else if(objArr.dimensiones==1){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                char[] arrI = new char[(int)1];
                                Arreglos ni = new Arreglos(idArr, arrI,1,true,false);
                                Simbolo nuevoI = new Simbolo(idArr,"character",ni,TipoSimbolo.Arreglo);
                                ent.AsignarValorVariable(idArr,nuevoI);
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no tiene asignado un valor, realizar un allocate:" +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                return new Value("","",0,0);
                            }
                        }else{
                            Errores.addError("Error: El arreglo " + ctx.ID().getText() + " no es dinamico:" +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            return new Value("","",0,0);
                        }
                    }
                }
            }else{
                Errores.addError("Error: Esta tratando de deasignar valores, a una variable que no es arreglo: " + ctx.ID().getText() +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                return new Value("","",0,0);
            }
        }else{
            Errores.addError("Error: El array " + ctx.ID().getText() + " no existe: " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            return new Value("","",0,0);
        }

        return new Value("","",0,0);
    }

    public Value visitSentenciaIf(GramaticaParser.SentenciaIfContext ctx) {

        //TODO: obtengo la lista de todas las condiciones
        List<GramaticaParser.LcondicionesIfContext> condiciones = ctx.lcondicionesIf();
        boolean evaluarBloqueIf = false;

        //TODO: recorro la lista de condiciones para evular la expresion
        for (GramaticaParser.LcondicionesIfContext condicion: condiciones){
            Value val = visit(condicion.expr());
            try {
                if((boolean)val.value){ //TODO: si la expresion es true evalua el bloque del IF
                    pilaEntornos.push(new Entorno(pilaEntornos.peek()));
                    evaluarBloqueIf = true;

                    Value res = visitLinstrucciones(condicion.linstrucciones()); //TODO: ejecuto las instrucciones, esperando que venga un exit o cycle
                    if ((res.value.toString().equals("exit") && res.tipo.equals("exit"))
                            ||(res.value.toString().equals("cycle") && res.tipo.equals("cycle"))){
                        return res;
                    }

                    pilaEntornos.pop();
                    break;
                }
            }catch (Exception e){
                evaluarBloqueIf = true; //TODO: para que no entre al else
                break;
            }
        }

        //TODO: si ninguna de las condiciones del IF se cumple y este contiene el bloque ELSE, entonces ejecuta ese bloque
        if(!evaluarBloqueIf && ctx.elseBloque() != null){
            Value res = visitLinstrucciones(ctx.elseBloque().linstrucciones()); //TODO: ejecuto las instrucciones, esperando que venga un exit o cycle
            if ((res.value.toString().equals("exit") && res.tipo.equals("exit"))
                ||(res.value.toString().equals("cycle") && res.tipo.equals("cycle"))){
                return res;
            }
        }

        return new Value("","",0,0);
    }

    public Value visitDoEstructura(GramaticaParser.DoEstructuraContext ctx) {

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();
        List<GramaticaParser.ExprContext> expresiones = ctx.expr();
        String id = ctx.ID().getText();

        Entorno ent = pilaEntornos.peek();

        Value indice1 = visit(expresiones.get(0)); //TODO: valor inicial
        Value indice2 = visit(expresiones.get(1)); //TODO: valor, hasta donde llegar
        Value indice3 = visit(expresiones.get(2)); //TODO: valor, cuanto incrementar

        Simbolo sim = ent.Buscar(id);

        try{
            if(sim!=null){
                if(sim.tipoSimbolo.name().toLowerCase().equals("variable")){
                    if(indice1.tipo.equals("integer") && indice2.tipo.equals("integer") && indice3.tipo.equals("integer")){
                        if(sim.tipo.toLowerCase().equals("integer")){
                            if((int)indice1.value <= (int)indice2.value){
                                //TODO: Asignamos el valor inicial a la variable del indice 1
                                Entorno act = pilaEntornos.peek();
                                Simbolo rv = new Simbolo(id, sim.tipo, (int)indice1.value, sim.tipoSimbolo);
                                act.AsignarValorVariable(id,rv);
                                boolean bandera = true;

                                while(bandera){
                                    pilaEntornos.push(new Entorno(pilaEntornos.peek()));

                                    //TODO: ejecutamos
                                    Value res = visitLinstrucciones(ctx.linstrucciones());

                                    //TODO: break antes para que ya no reasigne nuevamente la variable
                                    if (res.value.toString().equals("exit") && res.tipo.equals("exit")){
                                        break;
                                    }

                                    //TODO: reasignando nuevo valor a la variable del indice 1
                                    Entorno ne = pilaEntornos.peek();
                                    int getNewIndice = (int)ne.Buscar(id).valor+(int)indice3.value;
                                    Simbolo ra = new Simbolo(id, sim.tipo, getNewIndice, sim.tipoSimbolo);
                                    ne.AsignarValorVariable(id,ra);

                                    if(getNewIndice > (int)indice2.value){
                                        bandera=false;
                                        //System.out.println("parar yaaa");
                                    }

                                    //TODO: continue despues para que vuelva reasignar nuevamente la variable y no se encicle
                                    if (res.value.toString().equals("cycle") && res.tipo.equals("cycle")){
                                        continue;
                                    }

                                    pilaEntornos.pop();
                                }
                            }else{
                                Errores.addError("Error: EL indice uno del ciclo do, tiene que ser menor al indice dos: " + indice1.value.toString() + " > " + indice2.value.toString() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            Errores.addError("Error: La variable asignada al indice uno, tiene que ser tipo integer: " + id +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }else{
                        Errores.addError("Error: Los valores asignados a los indices, tienen que ser tipo integer: " +
                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                    }
                }else{
                    System.out.println("");
                    Errores.addError("Error: La variable asignada al indice uno, tiene que ser tipo variable: " + id +
                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                }
            }else{
                Errores.addError("Error: No existe la variable del indice inicial, tiene que declararla antes: " + id +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        }catch (Exception e){
            Errores.addError("Error: En ciclo do: " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }

        return new Value("","",0,0);
    }

    public Value visitDoWhileEstrctura(GramaticaParser.DoWhileEstrcturaContext ctx) {

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        Value condicion = visit(ctx.expr());

        //TODO: verificar si la condicion es correcta
        if(condicion.value.toString().equals("")){
            return new Value("","",0,0);
        }

        try {
            while ((boolean)condicion.value){
                pilaEntornos.push(new Entorno(pilaEntornos.peek()));

                Value res = visitLinstrucciones(ctx.linstrucciones());

                if ((res.value.toString().equals("exit") && res.tipo.equals("exit"))
                        ||(res.value.toString().equals("cycle") && res.tipo.equals("cycle"))){
                    break;
                }
                if ((res.value.toString().equals("cycle") && res.tipo.equals("cycle"))
                        ||(res.value.toString().equals("cycle") && res.tipo.equals("cycle"))){
                    continue;
                }

                condicion = visit(ctx.expr());

                if(condicion.value.toString().equals("")){
                    break;
                }
                pilaEntornos.pop();
            }
        }catch (Exception e){
            Errores.addError("Error: En ciclo do while: " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }

        return new Value("","",0,0);
    }

    public Value visitControlExit(GramaticaParser.ControlExitContext ctx) {
        return new Value("exit","exit",0,0);
    }

    public Value visitControlCycle(GramaticaParser.ControlCycleContext ctx) {
        return new Value("cycle","cycle",0,0);
    }

    public Value visitEtiquetaDo(GramaticaParser.EtiquetaDoContext ctx) {
        return new Value("","",0,0);
    }

    public Value visitEtiquetaDoWhile(GramaticaParser.EtiquetaDoWhileContext ctx) {
        return new Value("","",0,0);
    }

    public Value visitSubroutine(GramaticaParser.SubroutineContext ctx) {

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        if(ctx.id1.getText().equals(ctx.id2.getText())){ //TODO; verifico los identificadores si son iguales
            if(!pilaEntornos.peek().TablaSimbolo.containsKey(ctx.id1.getText().toLowerCase())){ //TODO: si no existe la subrutina
                ArrayList<Simbolo> parametros = new ArrayList<Simbolo>();
                if(ctx.lexpr()!=null){ //TODO: por si no vienen parametros
                    for (GramaticaParser.ExprContext expCtx: ctx.lexpr().expr()){ //TODO: recorro los parametros(expr), para guardarlas en una lista
                        parametros.add(new Simbolo(expCtx.getText(),"",null, TipoSimbolo.Parametro));
                    }
                }
                Subrutina subr = new Subrutina(ctx.getText(), parametros, ctx.linstrucciones(), ctx.ldeclP()); //TODO: creo el objecto subrituna y le paso lista de instrucciones y declaraciones de parametros
                pilaEntornos.peek().nuevoSimbolo(ctx.id1.getText(), new Simbolo(ctx.id1.getText(),"subrutina",subr,TipoSimbolo.Subrutina)); //Todo guardo en tabla de simbolos
            }else{
                Errores.addError("Error: La subrutina ya existe: " + ctx.id1.getText() +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        }else{
            Errores.addError("Error: Los identificadores de la subrutina no coinciden: " + ctx.id1.getText() + " != " + ctx.id2.getText() +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }

        return new Value("","",0,0);
    }

    public Value visitCall(GramaticaParser.CallContext ctx) {

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        Entorno ent = pilaEntornos.peek();

        Simbolo simRutina = ent.Buscar(ctx.ID().getText());

        if(simRutina!=null){
            if(simRutina.tipo.equals("subrutina")){
                Entorno entSubr = new Entorno(ent);
                Subrutina subr = (Subrutina) simRutina.valor;

                if(ctx.lexpr()!=null){ //TODO: vienen parametros

                    if(subr.ldeclaracionParam!=null){ //TODO: si hay parametros, pero no declaracion del tipo intent(in)
                        //TODO: (cantidad de parametros de la funcion == cantidad expresioneso parametros en el call) && (cantidad de parametros de la funcion == lista de declaraciones de la funcion, lo de intent)
                        if ((subr.lparametros.size() == ctx.lexpr().expr().size()) && (subr.lparametros.size() == subr.ldeclaracionParam.getChildCount())){
                            //TODO: Por si viene alguna variable o arreglo como parametro, verifico que existan
                            for (int i = 0; i < ctx.lexpr().expr().size(); i++){
                                Value val = visit(ctx.lexpr().expr().get(i));
                                if((val.value.toString().equals("") && val.tipo.equals(""))){
                                    return new Value("","",0,0);
                                }
                            }

                            pilaEntornos.push(entSubr);

                            //TODO: empiezo a guardar parametros
                            for (int i = 0; i < ctx.lexpr().expr().size(); i++){
                                Value val = visit(ctx.lexpr().expr().get(i)); //TODO: valor del parametro que envian
                                if((val.tipo.equals("arreglo"))){
                                    //System.out.println("arreglo -> " + ctx.lexpr().expr().get(i).getText()); //TODO: nombre de arreglo que envian
                                    Simbolo objArr = pilaEntornos.peek().Buscar(ctx.lexpr().expr().get(i).getText());
                                    Arreglos miArr = (Arreglos)objArr.valor;
                                    List<GramaticaParser.ExprContext> cantidadExpr = subr.ldeclaracionParam.declParametros(i).valorParametros().expr();
                                    if(miArr.dimensiones == cantidadExpr.size()){
                                        if(objArr.tipo.equals(subr.ldeclaracionParam.declParametros(i).type().getText())){
                                            ArrayList<Value> temporal = new ArrayList<Value>(); //TODO: lista para guardar los valores que envian en los parametros
                                            for (GramaticaParser.ExprContext exprr: cantidadExpr){
                                                Value ve = visit(exprr);
                                                temporal.add(ve);
                                                if(!ve.tipo.equals("integer")){
                                                    Errores.addError("Error: Los indices del array, en la declaracion de parametros en subroutine tienen que ser tipo integer: " + ctx.ID().getText() +
                                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                    pilaEntornos.pop(); //TODO: elimino entorno, ya que antes se hace un push, por si encuentra un error vuelve a sacar ese entorno, ya que si no se declara antes el entorno no guarda las variables, que vienen como parametros
                                                    return new Value("","",0,0);
                                                }
                                            }
                                            if(miArr.dimensiones==2){
                                                if(objArr.tipo.equals("integer")){
                                                    int[][] at= (int[][]) miArr.valor;
                                                    int op1 = at.length * at[0].length;
                                                    int op2 = (int)temporal.get(0).value * (int)temporal.get(1).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                } else if(objArr.tipo.equals("real")) {
                                                    double[][] at= (double[][]) miArr.valor;
                                                    int op1 = at.length * at[0].length;
                                                    int op2 = (int)temporal.get(0).value * (int)temporal.get(1).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                }else if(objArr.tipo.equals("character")) {
                                                    char[][] at= (char[][]) miArr.valor;
                                                    int op1 = at.length * at[0].length;
                                                    int op2 = (int)temporal.get(0).value * (int)temporal.get(1).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                }
                                            }else if (miArr.dimensiones==1){
                                                if(objArr.tipo.equals("integer")){
                                                    int[] at= (int[]) miArr.valor;
                                                    int op1 = at.length;
                                                    int op2 = (int)temporal.get(0).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                } else if(objArr.tipo.equals("real")) {
                                                    double[] at= (double[]) miArr.valor;
                                                    int op1 = at.length;
                                                    int op2 = (int)temporal.get(0).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                }else if(objArr.tipo.equals("character")) {
                                                    char[] at= (char[]) miArr.valor;
                                                    int op1 = at.length;
                                                    int op2 = (int)temporal.get(0).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                }
                                            }
                                        }else{
                                            Errores.addError("Error: El tipo de dato del arreglo no coinciden: " + ctx.ID().getText() +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            pilaEntornos.pop();
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: La cantidad de indices del arreglo no coinciden " + ctx.ID().getText() +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        pilaEntornos.pop();
                                        return new Value("","",0,0);
                                    }
                                    subr.lparametros.get(i).valor = objArr.valor;
                                    subr.lparametros.get(i).tipo = subr.ldeclaracionParam.declParametros(i).type().getText();
                                    subr.lparametros.get(i).tipoSimbolo = TipoSimbolo.Arreglo;
                                    pilaEntornos.peek().nuevoSimbolo(subr.lparametros.get(i).identificador, subr.lparametros.get(i));
                                }else{
                                    subr.lparametros.get(i).valor = val.value;
                                    subr.lparametros.get(i).tipo = subr.ldeclaracionParam.declParametros(i).type().getText();
                                    subr.lparametros.get(i).tipoSimbolo = TipoSimbolo.Variable;
                                    pilaEntornos.peek().nuevoSimbolo(subr.lparametros.get(i).identificador, subr.lparametros.get(i));
                                }
                            }

                            //TODO: ejecutamos lista de instrucciones
                            visitLinstrucciones((GramaticaParser.LinstruccionesContext)subr.linstrucciones);
                            pilaEntornos.pop();

                        }else{
                            Errores.addError("Error: La cantidad de parametros o declaraciones no coinciden " + ctx.ID().getText() +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }else{
                        Errores.addError("Error: Debe declarar las variables que vienen como parametros en la subroutine, de la forma intent(in): " + ctx.ID().getText() +
                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        return new Value("","",0,0);
                    }
                }else{ //TODO: no vienen parametros
                    if (subr.lparametros.size()>0){
                        Errores.addError("Error: La cantidad de parametros o declaraciones no coinciden " + ctx.ID().getText() +
                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                    }else{
                        if(subr.ldeclaracionParam!=null){ //TODO: si no hay parametros, no tienen que haber declaraciones intent(in)
                            Errores.addError("Error: No existen parametros en la subrutina, no tienen que haber declaraciones intent(in): " + ctx.ID().getText() +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                        pilaEntornos.push(entSubr);
                        visitLinstrucciones((GramaticaParser.LinstruccionesContext)subr.linstrucciones);
                        pilaEntornos.pop();
                    }
                }
            }else{
                Errores.addError("Error: La subrutina no existe: " + ctx.ID().getText() +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        }else{
            Errores.addError("Error: La subrutina no existe: " + ctx.ID().getText() +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }

        return new Value("","",0,0);
    }

    public Value visitFuncion(GramaticaParser.FuncionContext ctx) {

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        if(ctx.id1.getText().equals(ctx.id2.getText())) { //TODO; verifico los identificadores si son iguales
            if(!pilaEntornos.peek().TablaSimbolo.containsKey(ctx.id1.getText().toLowerCase())){ //TODO: si no existe la funcion
                ArrayList<Simbolo> parametros = new ArrayList<Simbolo>();
                if(ctx.lexpr()!=null){ //TODO: por si no vienen parametros
                    for (GramaticaParser.ExprContext expCtx: ctx.lexpr().expr()){ //TODO: recorro los parametros(expr), para guardarlas en una lista
                        parametros.add(new Simbolo(expCtx.getText(),"",null, TipoSimbolo.Parametro));
                    }
                }
                Funciones func = new Funciones(ctx.getText(), ctx.idr.getText(), parametros, ctx.linstrucciones(), ctx.ldeclP()); //TODO: creo el objecto funcion y le paso lista de instrucciones y declaraciones de parametros
                pilaEntornos.peek().nuevoSimbolo(ctx.id1.getText(), new Simbolo(ctx.id1.getText(),"funcion", func, TipoSimbolo.Funcion)); //Todo guardo en tabla de simbolos
            }else{
                Errores.addError("Error: La funcion ya existe: " + ctx.id1.getText() +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        }else{
            Errores.addError("Error: Los identificadores de la funcion no coinciden: " + ctx.id1.getText() + " != " + ctx.id2.getText() +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }

        return new Value("","",0,0);
    }

    public Value visitFuncExpr(GramaticaParser.FuncExprContext ctx) {

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        Entorno ent = pilaEntornos.peek();

        Simbolo simFuncion = ent.Buscar(ctx.ID().getText());

        if(simFuncion!=null){
            if(simFuncion.tipo.equals("funcion")){
                Entorno entFunc = new Entorno(ent);
                Funciones func = (Funciones) simFuncion.valor;
                if(ctx.lexpr()!=null) { //TODO: vienen parametros
                    if(func.ldeclaracionParam!=null){ //TODO: si hay parametros, pero no declaracion del tipo intent(in)

                        //TODO: (cantidad de parametros de la funcion == cantidad expresioneso parametros en el call) && (cantidad de parametros de la funcion == lista de declaraciones de la funcion, lo de intent)
                        if ((func.lparametros.size() == ctx.lexpr().expr().size()) && (func.lparametros.size() == func.ldeclaracionParam.getChildCount())){
                            //TODO: Por si viene alguna variable o arreglo como parametro, verifico que existan
                            for (int i = 0; i < ctx.lexpr().expr().size(); i++){
                                Value val = visit(ctx.lexpr().expr().get(i));
                                if((val.value.toString().equals("") && val.tipo.equals(""))){
                                    return new Value("","",0,0);
                                }
                            }

                            pilaEntornos.push(entFunc);

                            //TODO: empiezo a guardar parametros
                            for (int i = 0; i < ctx.lexpr().expr().size(); i++){
                                Value val = visit(ctx.lexpr().expr().get(i)); //TODO: valor del parametro que envian
                                if((val.tipo.equals("arreglo"))){
                                    //System.out.println("arreglo -> " + ctx.lexpr().expr().get(i).getText()); //TODO: nombre de arreglo que envian
                                    Simbolo objArr = pilaEntornos.peek().Buscar(ctx.lexpr().expr().get(i).getText());
                                    Arreglos miArr = (Arreglos)objArr.valor;
                                    List<GramaticaParser.ExprContext> cantidadExpr = func.ldeclaracionParam.declParametros(i).valorParametros().expr();
                                    if(miArr.dimensiones == cantidadExpr.size()){
                                        if(objArr.tipo.equals(func.ldeclaracionParam.declParametros(i).type().getText())){
                                            ArrayList<Value> temporal = new ArrayList<Value>(); //TODO: lista para guardar los valores que envian en los parametros
                                            for (GramaticaParser.ExprContext exprr: cantidadExpr){
                                                Value ve = visit(exprr);
                                                temporal.add(ve);
                                                if(!ve.tipo.equals("integer")){
                                                    Errores.addError("Error: Los indices del array, en la declaracion de parametros en funcion tienen que ser tipo integer: " + ctx.ID().getText() +
                                                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                    pilaEntornos.pop(); //TODO: elimino entorno, ya que antes se hace un push, por si encuentra un error vuelve a sacar ese entorno, ya que si no se declara antes el entorno no guarda las variables, que vienen como parametros
                                                    return new Value("","",0,0);
                                                }
                                            }
                                            if(miArr.dimensiones==2){
                                                if(objArr.tipo.equals("integer")){
                                                    int[][] at= (int[][]) miArr.valor;
                                                    int op1 = at.length * at[0].length;
                                                    int op2 = (int)temporal.get(0).value * (int)temporal.get(1).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                } else if(objArr.tipo.equals("real")) {
                                                    double[][] at= (double[][]) miArr.valor;
                                                    int op1 = at.length * at[0].length;
                                                    int op2 = (int)temporal.get(0).value * (int)temporal.get(1).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                }else if(objArr.tipo.equals("character")) {
                                                    char[][] at= (char[][]) miArr.valor;
                                                    int op1 = at.length * at[0].length;
                                                    int op2 = (int)temporal.get(0).value * (int)temporal.get(1).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                }
                                            }else if (miArr.dimensiones==1){
                                                if(objArr.tipo.equals("integer")){
                                                    int[] at= (int[]) miArr.valor;
                                                    int op1 = at.length;
                                                    int op2 = (int)temporal.get(0).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                } else if(objArr.tipo.equals("real")) {
                                                    double[] at= (double[]) miArr.valor;
                                                    int op1 = at.length;
                                                    int op2 = (int)temporal.get(0).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                }else if(objArr.tipo.equals("character")) {
                                                    char[] at= (char[]) miArr.valor;
                                                    int op1 = at.length;
                                                    int op2 = (int)temporal.get(0).value;
                                                    if(!(op1==op2)){
                                                        temporal.clear();
                                                        Errores.addError("Error: Las dimensiones del array, tienen que ser del mismo tamano: " + ctx.ID().getText() +
                                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                                        pilaEntornos.pop();
                                                        return new Value("","",0,0);
                                                    }
                                                }
                                            }
                                        }else{
                                            Errores.addError("Error: El tipo de dato del arreglo no coinciden: " + ctx.ID().getText() +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                            pilaEntornos.pop();
                                            return new Value("","",0,0);
                                        }
                                    }else{
                                        Errores.addError("Error: La cantidad de indices del arreglo no coinciden " + ctx.ID().getText() +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        pilaEntornos.pop();
                                        return new Value("","",0,0);
                                    }
                                    func.lparametros.get(i).valor = objArr.valor;
                                    func.lparametros.get(i).tipo = func.ldeclaracionParam.declParametros(i).type().getText();
                                    func.lparametros.get(i).tipoSimbolo = TipoSimbolo.Arreglo;
                                    pilaEntornos.peek().nuevoSimbolo(func.lparametros.get(i).identificador, func.lparametros.get(i));
                                }else{
                                    func.lparametros.get(i).valor = val.value;
                                    func.lparametros.get(i).tipo = func.ldeclaracionParam.declParametros(i).type().getText();
                                    func.lparametros.get(i).tipoSimbolo = TipoSimbolo.Variable;
                                    pilaEntornos.peek().nuevoSimbolo(func.lparametros.get(i).identificador, func.lparametros.get(i));
                                }
                            }
                            //TODO: ejecutamos lista de instrucciones
                            visitLinstrucciones((GramaticaParser.LinstruccionesContext)func.linstrucciones);

                            //TODO: valido si fue declarada la variable a retornar
                            Simbolo miVar = pilaEntornos.peek().Buscar(func.nombreVariableRetornar);
                            if(miVar!=null){
                                pilaEntornos.pop();
                                return new Value(miVar.valor, miVar.tipo, 0, 0);
                            }else{
                                Errores.addError("Error: La variable a retornar de la funcion, tiene que ser declarada: " + func.nombreVariableRetornar +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                            pilaEntornos.pop();

                        }else{
                            Errores.addError("Error: La cantidad de parametros o declaraciones no coinciden " + ctx.ID().getText() +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }else{
                        Errores.addError("Error: Debe declarar las variables que vienen como parametros en la funcion, de la forma intent(in): " + ctx.ID().getText() +
                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        return new Value("","",0,0);
                    }
                }else { //TODO: no vienen parametros
                    if (func.lparametros.size()>0){
                        Errores.addError("Error: La cantidad de parametros o declaraciones no coinciden " + ctx.ID().getText() +
                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                    }else{
                        if(func.ldeclaracionParam!=null){ //TODO: si no hay parametros, no tienen que haber declaraciones intent(in)
                            Errores.addError("Error: No existen parametros en la funcion, no tienen que haber declaraciones intent(in): " + ctx.ID().getText() +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                        //TODO: ejecuto
                        pilaEntornos.push(entFunc);
                        visitLinstrucciones((GramaticaParser.LinstruccionesContext)func.linstrucciones);

                        //TODO: valido si fue declarada la variable a retornar
                        Simbolo miVar = pilaEntornos.peek().Buscar(func.nombreVariableRetornar);
                        if(miVar!=null){
                            pilaEntornos.pop();
                            return new Value(miVar.valor, miVar.tipo, 0, 0);
                        }else{
                            Errores.addError("Error: La variable a retornar de la funcion, tiene que ser declarada: " + func.nombreVariableRetornar +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                        pilaEntornos.pop();
                    }
                }
            }else{
                Errores.addError("Error: La subrutina no existe: " + ctx.ID().getText() +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        }else{
            Errores.addError("Error: La funcion no existe: " + ctx.ID().getText() +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }

        return new Value("","",0,0);
    }

    public Value visitArrayAccesoExpr(GramaticaParser.ArrayAccesoExprContext ctx) {

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();
        String id = ctx.ID().getText();
        Value val = visit(ctx.lexpr());

        Entorno ent = pilaEntornos.peek();

        if(llListaExpresiones.size()==2){ //TODO si es de dos dimensiones
            Simbolo sim = ent.Buscar(ctx.ID().getText());
            if(sim!=null){
                if (sim.tipoSimbolo.name().toLowerCase().equals("arreglo")){ //TODO: si es tipo arreglo
                    if (sim.tipo.equals("integer")){ //TODO: si es integer
                        if(llListaExpresiones.get(0).tipo.toLowerCase().equals("integer") && llListaExpresiones.get(1).tipo.toLowerCase().equals("integer")){ //TODO: valido tipo de indices
                            Arreglos objArr = (Arreglos) sim.valor;
                            if(objArr.dimensiones==2){ //TODO: valido si es de 2 dimensiones
                                if(!objArr.esDinamico){// TODO: No es dinamico
                                    int miArr[][] = (int[][]) objArr.valor;
                                    try {
                                        //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                        int temp = miArr[(int)llListaExpresiones.get(0).value-1][(int)llListaExpresiones.get(1).value-1];
                                        llListaExpresiones.clear();
                                        return new Value(temp,"integer",lineaExp,columnaExp);
                                    }catch (Exception e){
                                        Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }else{ //TODO: SI es dinamico
                                    if(objArr.deAllocate){
                                        int miArr[][] = (int[][]) objArr.valor;
                                        try {
                                            //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                            int temp = miArr[(int)llListaExpresiones.get(0).value-1][(int)llListaExpresiones.get(1).value-1];
                                            llListaExpresiones.clear();
                                            return new Value(temp,"integer",lineaExp,columnaExp);
                                        }catch (Exception e){
                                            Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }
                            }else{
                                Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            Errores.addError("Error: Los valores de indices de " + ctx.ID().getText() + " tienen que ser de tipo integer: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }else if (sim.tipo.equals("real")){ //TODO: si es real
                        if(llListaExpresiones.get(0).tipo.toLowerCase().equals("integer") && llListaExpresiones.get(1).tipo.toLowerCase().equals("integer")){
                            Arreglos objArr = (Arreglos) sim.valor;
                            if(objArr.dimensiones==2){ //TODO: valido si es de 2 dimensiones
                                if(!objArr.esDinamico){// TODO: No es dinamico
                                    double miArr[][] = (double[][]) objArr.valor;
                                    try {
                                        //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                        double temp = miArr[(int)llListaExpresiones.get(0).value-1][(int)llListaExpresiones.get(1).value-1];
                                        llListaExpresiones.clear();
                                        return new Value(temp,"real",lineaExp,columnaExp);
                                    }catch (Exception e){
                                        Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }else{ //TODO: SI es dinamico
                                    if(objArr.deAllocate){
                                        double miArr[][] = (double[][]) objArr.valor;
                                        try {
                                            //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                            double temp = miArr[(int)llListaExpresiones.get(0).value-1][(int)llListaExpresiones.get(1).value-1];
                                            llListaExpresiones.clear();
                                            return new Value(temp,"real",lineaExp,columnaExp);
                                        }catch (Exception e){
                                            Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }
                            }else{
                                Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            Errores.addError("Error: Los valores de indices de " + ctx.ID().getText() + " tienen que ser de tipo integer: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }else if (sim.tipo.equals("character")){ //TODO: si es char
                        if(llListaExpresiones.get(0).tipo.toLowerCase().equals("integer") && llListaExpresiones.get(1).tipo.toLowerCase().equals("integer")){
                            Arreglos objArr = (Arreglos) sim.valor;
                            if(objArr.dimensiones==2){ //TODO: valido si es de 2 dimensiones
                                if(!objArr.esDinamico){// TODO: No es dinamico
                                    char miArr[][] = (char[][]) objArr.valor;
                                    try {
                                        //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                        char temp = miArr[(int)llListaExpresiones.get(0).value-1][(int)llListaExpresiones.get(1).value-1];
                                        llListaExpresiones.clear();
                                        return new Value(temp,"character",lineaExp,columnaExp);
                                    }catch (Exception e){
                                        Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }else{ //TODO: SI es dinamico
                                    if(objArr.deAllocate){
                                        char miArr[][] = (char[][]) objArr.valor;
                                        try {
                                            //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                            char temp = miArr[(int)llListaExpresiones.get(0).value-1][(int)llListaExpresiones.get(1).value-1];
                                            llListaExpresiones.clear();
                                            return new Value(temp,"character",lineaExp,columnaExp);
                                        }catch (Exception e){
                                            Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }
                            }else{
                                Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            Errores.addError("Error: Los valores de indices de " + ctx.ID().getText() + " tienen que ser de tipo integer: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }
                }else{
                    Errores.addError("Error: Esta tratando de acceder a " + ctx.ID().getText() + " en forma de arreglo: " +
                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                }
            }else{
                Errores.addError("Error: El array " + ctx.ID().getText() + " no existe: " +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        }else if(llListaExpresiones.size()==1){
            Simbolo sim = ent.Buscar(ctx.ID().getText());
            if(sim!=null){
                if (sim.tipoSimbolo.name().toLowerCase().equals("arreglo")){ //TODO: si es tipo arreglo
                    if (sim.tipo.equals("integer")){ //TODO: si es integer
                        if(llListaExpresiones.get(0).tipo.toLowerCase().equals("integer")){ //TODO: valido tipo de indices
                            Arreglos objArr = (Arreglos) sim.valor;
                            if(objArr.dimensiones==1){ //TODO: valido si es de 1 dimension
                                if(!objArr.esDinamico){// TODO: No es dinamico
                                    int miArr[] = (int[]) objArr.valor;
                                    try {
                                        //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                        int temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                        llListaExpresiones.clear();
                                        return new Value(temp,"integer",lineaExp,columnaExp);
                                    }catch (Exception e){
                                        Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }else{ //TODO: SI es dinamico
                                    if(objArr.deAllocate){
                                        int miArr[] = (int[]) objArr.valor;
                                        try {
                                            //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                            int temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                            llListaExpresiones.clear();
                                            return new Value(temp,"integer",lineaExp,columnaExp);
                                        }catch (Exception e){
                                            Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }
                            }else{
                                Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            Errores.addError("Error: Los valores de indices de " + ctx.ID().getText() + " tienen que ser de tipo integer: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }else if (sim.tipo.equals("real")){ //TODO: si es real
                        if(llListaExpresiones.get(0).tipo.toLowerCase().equals("integer")){
                            Arreglos objArr = (Arreglos) sim.valor;
                            if(objArr.dimensiones==1){ //TODO: valido si es de 1 dimension
                                if(objArr.esDinamico==false){// TODO: No es dinamico
                                    double miArr[] = (double[]) objArr.valor;
                                    try {
                                        //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                        double temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                        llListaExpresiones.clear();
                                        return new Value(temp,"real",lineaExp,columnaExp);
                                    }catch (Exception e){
                                        Errores.addError("Error: Los valores de indices de " + ctx.ID().getText() + " tienen que ser de tipo integer: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }else{ //TODO: SI es dinamico
                                    if(objArr.deAllocate==true){
                                        double miArr[] = (double[]) objArr.valor;
                                        try {
                                            //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                            double temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                            llListaExpresiones.clear();
                                            return new Value(temp,"real",lineaExp,columnaExp);
                                        }catch (Exception e){
                                            Errores.addError("Error: Los valores de indices de " + ctx.ID().getText() + " tienen que ser de tipo integer: " +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }
                            }else{
                                Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            Errores.addError("Error: Los valores de indices de " + ctx.ID().getText() + " no coinciden con el tipo del arreglo: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }else if (sim.tipo.equals("character")){ //TODO: si es char
                        if(llListaExpresiones.get(0).tipo.toLowerCase().equals("integer")){
                            Arreglos objArr = (Arreglos) sim.valor;
                            if(objArr.dimensiones==1){ //TODO: valido si es de 1 dimension
                                if(!objArr.esDinamico){// TODO: No es dinamico
                                    char miArr[] = (char[]) objArr.valor;
                                    try {
                                        //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                        char temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                        llListaExpresiones.clear();
                                        return new Value(temp,"character",lineaExp,columnaExp);
                                    }catch (Exception e){
                                        Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }else{ //TODO: SI es dinamico
                                    if(objArr.deAllocate){
                                        char miArr[] = (char[]) objArr.valor;
                                        try {
                                            //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                            char temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                            llListaExpresiones.clear();
                                            return new Value(temp,"character",lineaExp,columnaExp);
                                        }catch (Exception e){
                                            Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        }
                                    }else{
                                        Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }
                            }else{
                                Errores.addError("Error: El numero de expresiones de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            Errores.addError("Error: Los valores de indices de " + ctx.ID().getText() + " tienen que ser de tipo integer: " +
                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        }
                    }
                }else{
                    Errores.addError("Error: Esta tratando de acceder a " + ctx.ID().getText() + " en forma de arreglo: " +
                            "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                }
            }else{
                Errores.addError("Error: El array " + ctx.ID().getText() + " no existe: " +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        }else{
            Errores.addError("Error: El array " + id + " tiene que ser de 1 o 2 dimensiones" +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }
        llListaExpresiones.clear();
        return new Value("","",0,0);
    }

    public Value visitUnaryMinusExpr(GramaticaParser.UnaryMinusExprContext ctx) {
        Value val = visit(ctx.expr());
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

            if(val.tipo.equals("integer")){
                int val2 = (int)val.value * -1;
                return new Value(val2,"integer",val.linea,val.columna);
            }else if(val.tipo.equals("real")){
                double val2 = (double)val.value * -1;
                return new Value(val2,"real",val.linea,val.columna);
            } else if(val.tipo.equals("complex")){
                int val2 = (int)val.value * -1;
                return new Value(val2,"complex",val.linea,val.columna);
            }else{
                Errores.addError("Error: No se puede convertir un valor tipo 'logical' o 'character' usando la expresion unaria '-'" +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            }
        return new Value("","",0,0);
    }

    public Value visitPowExpr(GramaticaParser.PowExprContext ctx) {
        Value left = visit(ctx.left);
        Value right = visit(ctx.right);
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        if (left.value.equals("")||right.value.equals("")){return new Value("","",0,0);}

        if(left.tipo.equals("integer") && right.tipo.equals("integer")){ //retorna integer
            int op1 = Integer.parseInt(left.value.toString());
            int op2 = Integer.parseInt(right.value.toString());
            int op3 = (int)Math.pow(op1,op2);
            return  new Value(op3,"integer",lineaExp,columnaExp);
        }else if((left.tipo.equals("integer") && right.tipo.equals("real")) //retorna un real
                || (left.tipo.equals("real") && right.tipo.equals("integer"))
                || (left.tipo.equals("real") && right.tipo.equals("real"))){

            return  new Value(Math.pow(Double.parseDouble(left.value.toString()),Double.parseDouble(right.value.toString())),"real",lineaExp,columnaExp);

        }else if((left.tipo.equals("integer") && right.tipo.equals("complex")) //retorna complex
                || (left.tipo.equals("real") && right.tipo.equals("complex"))
                || (left.tipo.equals("complex") && right.tipo.equals("integer"))
                || (left.tipo.equals("complex") && right.tipo.equals("real"))
                || (left.tipo.equals("complex") && right.tipo.equals("complex"))){

            return  new Value(Math.pow(Double.parseDouble(left.value.toString()),Double.parseDouble(right.value.toString())),"complex",lineaExp,columnaExp);

        }else{
            Errores.addError("Error: Los tipos de datos de los valores a evaluar en una expresion de potencia tienen que ser numericos: " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            return new Value("","",0,0);
        }
    }

    public Value visitOpExpr(GramaticaParser.OpExprContext ctx) {
        Value left = visit(ctx.left);
        Value right = visit(ctx.right);
        String operacion = ctx.op.getText();
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        if (left.value.equals("")||right.value.equals("")){return new Value("","",0,0);}

        if(left.tipo.equals("integer") && right.tipo.equals("integer")){ //retorna integer
            switch (operacion.charAt(0))
            {
                case '*':
                    return  new Value(Integer.parseInt(left.value.toString()) * Integer.parseInt(right.value.toString()),"integer",lineaExp,columnaExp);
                case '/':
                    int op1 = Integer.parseInt(left.value.toString());
                    int op2 = Integer.parseInt(right.value.toString());
                    double op3 = 0;
                    try {
                        op3 = (op1/op2);
                    }catch (Exception e){
                        //System.out.println("/ 0");
                    }
                    return new Value(Math.round(op3),"integer",lineaExp,columnaExp);
                case '+':
                    int op4 = Integer.parseInt(left.value.toString());
                    int op5 = Integer.parseInt(right.value.toString());
                    int op6 = op4+op5;
                    return  new Value(Integer.parseInt(left.value.toString()) + Integer.parseInt(right.value.toString()),"integer",lineaExp,columnaExp);
                case '-':
                    return  new Value(Integer.parseInt(left.value.toString()) - Integer.parseInt(right.value.toString()),"integer",lineaExp,columnaExp);
                default:
                    System.out.println("Operacion no valida");
            }
        }else if((left.tipo.equals("integer") && right.tipo.equals("real")) //retorna un real
                || (left.tipo.equals("real") && right.tipo.equals("integer"))
                || (left.tipo.equals("real") && right.tipo.equals("real"))){

            switch (operacion.charAt(0))
            {
                case '*':
                    return  new Value(Double.parseDouble(left.value.toString()) * Double.parseDouble(right.value.toString()),"real",lineaExp,columnaExp);
                case '/':
                    double op1 = Double.parseDouble(left.value.toString());
                    double op2 = Double.parseDouble(right.value.toString());
                    double op3 = (op1/op2);
                    return new Value(op3,"real",lineaExp,columnaExp);
                case '+':
                    return  new Value(Double.parseDouble(left.value.toString()) + Double.parseDouble(right.value.toString()),"real",lineaExp,columnaExp);
                case '-':
                    return  new Value(Double.parseDouble(left.value.toString()) - Double.parseDouble(right.value.toString()),"real",lineaExp,columnaExp);
                default:
                    System.out.println("Operacion no valida a retornar real");
            }
        }else if((left.tipo.equals("integer") && right.tipo.equals("complex")) //retorna complex
                || (left.tipo.equals("real") && right.tipo.equals("complex"))
                || (left.tipo.equals("complex") && right.tipo.equals("integer"))
                || (left.tipo.equals("complex") && right.tipo.equals("real"))
                || (left.tipo.equals("complex") && right.tipo.equals("complex"))){

            switch (operacion.charAt(0))
            {
                case '*':
                    return  new Value(Double.parseDouble(left.value.toString()) * Double.parseDouble(right.value.toString()),"complex",lineaExp,columnaExp);
                case '/':
                    double op1 = Double.parseDouble(left.value.toString());
                    double op2 = Double.parseDouble(right.value.toString());
                    double op3 = (op1/op2);
                    return new Value(op3,"complex",lineaExp,columnaExp);
                case '+':
                    return  new Value(Double.parseDouble(left.value.toString()) + Double.parseDouble(right.value.toString()),"complex",lineaExp,columnaExp);
                case '-':
                    return  new Value(Double.parseDouble(left.value.toString()) - Double.parseDouble(right.value.toString()),"complex",lineaExp,columnaExp);
                default:
                    System.out.println("Operacion no valida a retornar real");
            }
        }else{
             Errores.addError("Error: Los tipos de datos de los valores en expresion aritmetica tienen que ser del mismo tipo: " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            return new Value("","",0,0);
        }
        return new Value("","",0,0);
    }

    public Value visitNotExpr(GramaticaParser.NotExprContext ctx) {
        Value val = visit(ctx.expr());
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        if (val.value.equals("")){return new Value("","",0,0);}

        if(val.tipo.equals("logical")){
            return new Value(!(boolean)val.value,"logical",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
        }else{
            Errores.addError("Error: Los tipos de datos de los valores a evaluar en una expresion .not. tienen que ser 'logical' " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            return new Value("","",0,0);
        }
    }

    public Value visitRelationalExpr(GramaticaParser.RelationalExprContext ctx) {
        Value left = visit(ctx.left);
        Value right = visit(ctx.right);
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        if (left.value.equals("")||right.value.equals("")){return new Value("","",0,0);}

        try {
            if(left.tipo.equals("integer") && right.tipo.equals("integer")){
                switch (ctx.op.getType()){
                    case GramaticaParser.MAYOR:
                        return new Value(Integer.parseInt(left.value.toString()) > Integer.parseInt(right.value.toString()),"logical",lineaExp,columnaExp);
                    case GramaticaParser.MAYORQUE:
                        return new Value(Integer.parseInt(left.value.toString()) >= Integer.parseInt(right.value.toString()),"logical",lineaExp,columnaExp);
                    case GramaticaParser.MENOR:
                        return new Value(Integer.parseInt(left.value.toString()) < Integer.parseInt(right.value.toString()),"logical",lineaExp,columnaExp);
                    case GramaticaParser.MENORQUE:
                        return new Value(Integer.parseInt(left.value.toString()) <= Integer.parseInt(right.value.toString()),"logical",lineaExp,columnaExp);
                    default:
                        System.out.println("ERROR en visitRelationalExpr :(");
                }
            }else if((left.tipo.equals("real") && right.tipo.equals("real")) || (left.tipo.equals("complex") && right.tipo.equals("complex"))){
                switch (ctx.op.getType()){
                    case GramaticaParser.MAYOR:
                        return new Value(Double.parseDouble(left.value.toString()) > Double.parseDouble(right.value.toString()),"logical",lineaExp, columnaExp);
                    case GramaticaParser.MAYORQUE:
                        return new Value(Double.parseDouble(left.value.toString()) >= Double.parseDouble(right.value.toString()),"logical",lineaExp, columnaExp);
                    case GramaticaParser.MENOR:
                        return new Value(Double.parseDouble(left.value.toString()) < Double.parseDouble(right.value.toString()),"logical",lineaExp, columnaExp);
                    case GramaticaParser.MENORQUE:
                        return new Value(Double.parseDouble(left.value.toString()) <= Double.parseDouble(right.value.toString()),"logical",lineaExp, columnaExp);
                    default:
                        System.out.println("ERROR en visitRelationalExpr :(");
                }
            }else{
                Errores.addError("Error: Los tipos de datos de los valores a evaluar en una expresion relacion tienen que ser del mismo tipo " +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                return new Value("","",0,0);
            }
        }catch (Exception e){
            System.out.println("Error visitRelationalExpr: "+e);
        }
        return new Value("","",0,0);
    }

    public Value visitEqualityExpr(GramaticaParser.EqualityExprContext ctx) {
        Value left = visit(ctx.left);
        Value right = visit(ctx.right);
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        Object izq = "";
        Object der = "";

        if (left.value.equals("")||right.value.equals("")){return new Value("","",0,0);}

        try {
            if(left.tipo.equals("logical") && right.tipo.equals("logical")) {
                izq = Boolean.parseBoolean(left.value.toString());
                der = Boolean.parseBoolean(right.value.toString());
            }else if(left.tipo.equals("integer") && right.tipo.equals("integer")){
                izq = Integer.parseInt(left.value.toString());
                der = Integer.parseInt(right.value.toString());
            }else if((left.tipo.equals("real") && right.tipo.equals("real")) || (left.tipo.equals("complex") && right.tipo.equals("complex"))) {
                switch (ctx.op.getType()){
                    case GramaticaParser.IGUALIGUAL:
                        return new Value(Math.abs(Double.parseDouble(left.value.toString())-Double.parseDouble(right.value.toString()))<=0.000001,"logical",lineaExp,columnaExp);
                    case GramaticaParser.DIFIGUAL:
                        return new Value(!(Math.abs(Double.parseDouble(left.value.toString())-Double.parseDouble(right.value.toString()))<=0.000001),"logical",lineaExp,columnaExp);
                    default:
                        System.out.println("ERROR en visitEqualityExpr :(");
                }
            }else if(left.tipo.equals("character") && right.tipo.equals("character")){
                izq = left.value.toString();
                der = right.value.toString();
                switch (ctx.op.getType()){
                    case GramaticaParser.IGUALIGUAL:
                        return new Value(izq.equals(der),"logical",lineaExp,columnaExp); //hago return aca, porque en el switch compara con ==
                    case GramaticaParser.DIFIGUAL:
                        return new Value(!izq.equals(der),"logical",lineaExp,columnaExp); //hago return aca, porque en el switch compara con ==
                    default:
                        System.out.println("ERROR en visitEqualityExpr :(");
                }
            }else{
                Errores.addError("Error: Los tipos de datos de los valores a evaluar en expresion relacional no coinciden: " +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                return new Value("","",0,0);
            }

            switch (ctx.op.getType()){
                case GramaticaParser.IGUALIGUAL:
                    return new Value(izq == der,"logical",lineaExp,columnaExp);
                case GramaticaParser.DIFIGUAL:
                    return new Value(izq != der,"logical",lineaExp,columnaExp);
                default:
                    System.out.println("ERROR en visitEqualityExpr :(");
            }
        }catch (Exception e){
            System.out.println("Error visitEqualityExpr: "+e);
        }
        return new Value("","",0,0);
    }

    public Value visitAndExpr(GramaticaParser.AndExprContext ctx) {
        Value left = visit(ctx.left);
        Value right = visit(ctx.right);
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        if (left.value.equals("")||right.value.equals("")){return new Value("","",0,0);}

        if(left.tipo.equals("logical") && right.tipo.equals("logical")){
            return new Value(((boolean)left.value && (boolean)right.value),"logical",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
        }else{
            Errores.addError("Error: Los tipos de datos de los valores a evaluar en una expresion .and. tienen que ser 'logical' " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            return new Value("","",0,0);
        }
    }

    public Value visitOrExpr(GramaticaParser.OrExprContext ctx) {
        Value left = visit(ctx.left);
        Value right = visit(ctx.right);
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        if (left.value.equals("")||right.value.equals("")){return new Value("","",0,0);}

        if(left.tipo.equals("logical") && right.tipo.equals("logical")){
            return new Value(((boolean)left.value || (boolean)right.value),"logical",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
        }else{
            Errores.addError("Error: Los tipos de datos de los valores a evaluar en una expresion .or. tienen que ser 'logical' " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            return new Value("","",0,0);
        }
    }

    public Value visitParentExpr(GramaticaParser.ParentExprContext ctx) {
        Value val = visit(ctx.expr());
        return new Value(val.value,val.tipo,val.linea,val.columna);
    }

    public Value visitComplexExpr(GramaticaParser.ComplexExprContext ctx) {
        Value left = visit(ctx.left);
        //Value right = visit(ctx.right);
        return new Value(left.value,"complex",left.linea,left.columna);
    }

    public Value visitCharExpr(GramaticaParser.CharExprContext ctx) {
        String str = ctx.getText();
        str = str.substring(1, str.length() - 1);
        //System.out.println(str);
        return new Value(str,"character",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
    }

    public Value visitSizeArr(GramaticaParser.SizeArrContext ctx) {

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();
        String idArr = ctx.ID().getText();

        Entorno ent = pilaEntornos.peek();

        Simbolo sim = ent.Buscar(idArr);
        if(sim!=null){
            if (sim.tipoSimbolo.name().toLowerCase().equals("arreglo")) { //TODO: si es tipo arreglo
                if (sim.tipo.equals("integer")){
                    Arreglos objArr = (Arreglos) sim.valor;
                    if(objArr.dimensiones==2){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                int miArr[][] = (int[][]) objArr.valor;
                                int tam = miArr.length * miArr[0].length;
                                return new Value(tam,"integer",lineaExp,columnaExp);
                            }else{
                                Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            int miArr[][] = (int[][]) objArr.valor;
                            int tam = miArr.length * miArr[0].length;
                            return new Value(tam,"integer",lineaExp,columnaExp);
                        }
                    }else if(objArr.dimensiones==1){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                int miArr[] = (int[]) objArr.valor;
                                return new Value(miArr.length,"integer",lineaExp,columnaExp);
                            }else{
                                Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            int miArr[] = (int[]) objArr.valor;
                            return new Value(miArr.length,"integer",lineaExp,columnaExp);
                        }
                    }
                }else if (sim.tipo.equals("real")){
                    Arreglos objArr = (Arreglos) sim.valor;
                    if(objArr.dimensiones==2){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                double miArr[][] = (double[][]) objArr.valor;
                                int tam = miArr.length * miArr[0].length;
                                return new Value(tam,"integer",lineaExp,columnaExp);
                            }else{
                                Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            double miArr[][] = (double[][]) objArr.valor;
                            int tam = miArr.length * miArr[0].length;
                            return new Value(tam,"integer",lineaExp,columnaExp);
                        }
                    }else if(objArr.dimensiones==1){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                double miArr[] = (double[]) objArr.valor;
                                return new Value(miArr.length,"integer",lineaExp,columnaExp);
                            }else{
                                Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            double miArr[] = (double[]) objArr.valor;
                            return new Value(miArr.length,"integer",lineaExp,columnaExp);
                        }
                    }
                }else if (sim.tipo.equals("character")){
                    Arreglos objArr = (Arreglos) sim.valor;
                    if(objArr.dimensiones==2){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                char miArr[][] = (char[][]) objArr.valor;
                                int tam = miArr.length * miArr[0].length;
                                return new Value(tam,"integer",lineaExp,columnaExp);
                            }else{
                                Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            char miArr[][] = (char[][]) objArr.valor;
                            int tam = miArr.length * miArr[0].length;
                            return new Value(tam,"integer",lineaExp,columnaExp);
                        }
                    }else if(objArr.dimensiones==1){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                char miArr[] = (char[]) objArr.valor;
                                return new Value(miArr.length,"integer",lineaExp,columnaExp);
                            }else{
                                Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            char miArr[] = (char[]) objArr.valor;
                            return new Value(miArr.length,"integer",lineaExp,columnaExp);
                        }
                    }
                }
            }else{
                Errores.addError("Error: Esta tratando de obtener tamano de una variable que no es arreglo: " + ctx.ID().getText() +
                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                return new Value("","",0,0);
            }
        }else{
            Errores.addError("Error: El array " + ctx.ID().getText() + " no existe: " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            return new Value("","",0,0);
        }

        return new Value("","",0,0);
    }

    public Value visitLogicalExpr(GramaticaParser.LogicalExprContext ctx) {
        String logical = ctx.getText();
        logical = logical.substring(1, logical.length() - 1);
        //System.out.println(logical);
        return new Value(Boolean.valueOf(logical),"logical",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
    }

    public Value visitIdAtom(GramaticaParser.IdAtomContext ctx) {
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        Entorno ent = pilaEntornos.peek();
        Simbolo id = ent.Buscar(ctx.ID().getText());

        if(id!=null){
            if(id.tipoSimbolo.name().toLowerCase().equals("variable")){
                return new Value(id.valor,id.tipo,lineaExp,columnaExp);
            }else if(id.tipoSimbolo.name().toLowerCase().equals("arreglo")){
                Arreglos objArr = (Arreglos) id.valor;
                if(objArr.esDinamico){
                    if(objArr.deAllocate){
                        if(objArr.dimensiones==1){
                            if(id.tipo.equals("integer")){
                                int[] miArr = (int[]) objArr.valor;
                                String tem = "";
                                for (int i = 0; i < miArr.length; i++ ){
                                    tem += miArr[i] + " ";
                                }
                                return new Value(tem,"arreglo",lineaExp,columnaExp);
                            }else if(id.tipo.equals("real")){
                                double[] miArr = (double[]) objArr.valor;
                                String tem = "";
                                for (int i = 0; i < miArr.length; i++ ){
                                    tem += miArr[i] + " ";
                                }
                                return new Value(tem,"arreglo",lineaExp,columnaExp);
                            }else if(id.tipo.equals("character")){
                                char[] miArr = (char[]) objArr.valor;
                                String tem = "";
                                for (int i = 0; i < miArr.length; i++ ){
                                    tem += miArr[i] + " ";
                                }
                                return new Value(tem,"arreglo",lineaExp,columnaExp);
                            }
                        }else if(objArr.dimensiones==2){
                            if(id.tipo.equals("integer")){
                                int[][] miArr = (int[][]) objArr.valor;
                                String tem = "";
                                for (int i = 0; i < miArr.length; i++ ){
                                    for (int j=0; j < miArr[0].length; j++){
                                        tem += miArr[i][j] + " ";
                                    }
                                }
                                return new Value(tem,"arreglo",lineaExp,columnaExp);
                            }else if(id.tipo.equals("real")){
                                double[][] miArr = (double[][]) objArr.valor;
                                String tem = "";
                                for (int i = 0; i < miArr.length; i++ ){
                                    for (int j=0; j < miArr[0].length; j++){
                                        tem += miArr[i][j] + " ";
                                    }
                                }
                                return new Value(tem,"arreglo",lineaExp,columnaExp);
                            }else if(id.tipo.equals("character")){
                                char[][] miArr = (char[][]) objArr.valor;
                                String tem = "";
                                for (int i = 0; i < miArr.length; i++ ){
                                    for (int j=0; j < miArr[0].length; j++){
                                        tem += miArr[i][j] + " ";
                                    }
                                }
                                return new Value(tem,"arreglo",lineaExp,columnaExp);
                            }
                        }
                    }else{
                        Errores.addError("El arreglo no tiene un tamano asignado, realizar un allocate: " + ctx.ID().getText() +
                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                        return new Value("","",0,0);
                    }
                }else{
                    if(objArr.dimensiones==1){
                        if(id.tipo.equals("integer")){
                            int[] miArr = (int[]) objArr.valor;
                            String tem = "";
                            for (int i = 0; i < miArr.length; i++ ){
                                tem += miArr[i] + " ";
                            }
                            return new Value(tem,"arreglo",lineaExp,columnaExp);
                        }else if(id.tipo.equals("real")){
                            double[] miArr = (double[]) objArr.valor;
                            String tem = "";
                            for (int i = 0; i < miArr.length; i++ ){
                                tem += miArr[i] + " ";
                            }
                            return new Value(tem,"arreglo",lineaExp,columnaExp);
                        }else if(id.tipo.equals("character")){
                            char[] miArr = (char[]) objArr.valor;
                            String tem = "";
                            for (int i = 0; i < miArr.length; i++ ){
                                tem += miArr[i] + " ";
                            }
                            return new Value(tem,"arreglo",lineaExp,columnaExp);
                        }
                    }else if(objArr.dimensiones==2){
                        if(id.tipo.equals("integer")){
                            int[][] miArr = (int[][]) objArr.valor;
                            String tem = "";
                            for (int i = 0; i < miArr.length; i++ ){
                                for (int j=0; j < miArr[0].length; j++){
                                    tem += miArr[i][j] + " ";
                                }
                            }
                            return new Value(tem,"arreglo",lineaExp,columnaExp);
                        }else if(id.tipo.equals("real")){
                            double[][] miArr = (double[][]) objArr.valor;
                            String tem = "";
                            for (int i = 0; i < miArr.length; i++ ){
                                for (int j=0; j < miArr[0].length; j++){
                                    tem += miArr[i][j] + " ";
                                }
                            }
                            return new Value(tem,"arreglo",lineaExp,columnaExp);
                        }else if(id.tipo.equals("character")){
                            char[][] miArr = (char[][]) objArr.valor;
                            String tem = "";
                            for (int i = 0; i < miArr.length; i++ ){
                                for (int j=0; j < miArr[0].length; j++){
                                    tem += miArr[i][j] + " ";
                                }
                            }
                            return new Value(tem,"arreglo",lineaExp,columnaExp);
                        }
                    }
                }
            }
        }else{
            Errores.addError("Error: La variable " + ctx.ID().getText() + " no existe: " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
        }
        return new Value("","",0,0);
    }

    public Value visitIntegExpr(GramaticaParser.IntegExprContext ctx) {
        //System.out.println(ctx.getText());
        return new Value(Integer.valueOf(ctx.getText()),"integer",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
    }
    public Value visitRealExpr(GramaticaParser.RealExprContext ctx) {
        //System.out.println(ctx.getText());
        return new Value(Double.valueOf(ctx.getText()),"real",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
    }
}
