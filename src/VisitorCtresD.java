import Codigo3D.CodigoTresD;
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

public class VisitorCtresD extends GramaticaBaseVisitor<Value> {

    public Queue<Value> colaConcatenar = new LinkedList<Value>();//TODO: Print actual para concatenar lo separado por comas
    public LinkedList<Value> hmDeclaracionVariables = new LinkedList<Value>(); //TODO: Guarda tempralmente la lista de declaracion separado por coma
    public LinkedList<Value> llListaExpresiones = new LinkedList<Value>(); //TODO: Guarda tempralmente la lista de expresiones
    Stack<Entorno> pilaEntornos = new Stack<Entorno>(); //TODO: Guarda los entornos
    CodigoTresD listC3D = new CodigoTresD();
    public int banderaC3D_OpAND = 0;
    public int banderaC3D_OpOr = 0;
    Stack<String> pilaExit = new Stack<String>();
    Stack<String> pilaCycle = new Stack<String>();

    public VisitorCtresD(Entorno ent){
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
        listC3D.codigo3d.add("int main() {");
        visitLinstrucciones(ctx.linstrucciones());
        listC3D.codigo3d.add("return 0; \n}");
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

        listC3D.generateEtiquetaPosNeg();
        listC3D.generateEtiquetaSalida();

        Entorno ent = pilaEntornos.peek();

        switch (tipoVar.value.toString().toLowerCase()){
            case "integer":
                for(Value v: hmDeclaracionVariables){
                    //System.out.println(k+"<><>");
                    if(v.value!=null){ //TODO: Se le asigno el valor definido a la variable
                        if(!ent.TablaSimbolo.containsKey(v.identificador.toLowerCase())) { //TODO: valido si ya existe la variable en el entorno
                            try {
                                //TODO: c3d
                                listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.getUltimaPosicion() + " + 0;");
                                listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                listC3D.codigo3d.add("STACK[(int)P] = " + v.temporal + ";");
                                String tempVar = listC3D.generateTemporal(); //declaro el temporal de la variable, ya que en el WHILE hay que manipularla
                                listC3D.codigo3d.add(tempVar + " = " + v.temporal + ";");
                                //TODO: end c3d
                                Simbolo nuevo = new Simbolo(v.identificador,"integer", Integer.parseInt(v.value.toString()), TipoSimbolo.Variable, listC3D.getUltimaPosicion(), tempVar);
                                ent.nuevoSimbolo(v.identificador,nuevo);
                                listC3D.incrementPosicion();
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
                            //TODO: c3d
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = 0;");
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.getUltimaPosicion() + " + 0;");
                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add("STACK[(int)P] = " + listC3D.beforeTemporal() + ";");
                            String tempVar = listC3D.generateTemporal(); //declaro el temporal de la variable, ya que en el WHILE hay que manipularla
                            listC3D.codigo3d.add(tempVar + " = 0;");
                            //TODO: end c3d
                            Simbolo nuevo = new Simbolo(v.identificador,"integer", (int)0, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), tempVar);
                            ent.nuevoSimbolo(v.identificador,nuevo);
                            listC3D.incrementPosicion();
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
                                //TODO: c3d
                                listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.getUltimaPosicion() + " + 0;");
                                listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                listC3D.codigo3d.add("STACK[(int)P] = " + v.temporal + ";");
                                String tempVar = listC3D.generateTemporal(); //declaro el temporal de la variable, ya que en el WHILE hay que manipularla
                                listC3D.codigo3d.add(tempVar + " = " + v.temporal + ";");
                                //TODO: end c3d
                                Simbolo nuevo = new Simbolo(v.identificador,"real", (double)v.value, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), tempVar);
                                ent.nuevoSimbolo(v.identificador,nuevo);
                                listC3D.incrementPosicion();
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
                            //TODO: c3d
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = 0.0;");
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.getUltimaPosicion() + " + 0;");
                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add("STACK[(int)P] = " + listC3D.beforeTemporal() + ";");
                            String tempVar = listC3D.generateTemporal(); //declaro el temporal de la variable, ya que en el WHILE hay que manipularla
                            listC3D.codigo3d.add(tempVar + " = 0.0;");
                            //TODO: end c3d
                            Simbolo nuevo = new Simbolo(v.identificador,"real", (double)0.00000000, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), tempVar);
                            ent.nuevoSimbolo(v.identificador,nuevo);
                            listC3D.incrementPosicion();
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
                                //TODO: c3d
                                char b = String.valueOf(v.value.toString()).toCharArray()[0];
                                listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.getUltimaPosicion() + " + 0;");
                                listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                listC3D.codigo3d.add("STACK[(int)P] = " + (int)b + ";");
                                String tempVar = listC3D.generateTemporal(); //declaro el temporal de la variable, ya que en el WHILE hay que manipularla
                                listC3D.codigo3d.add(tempVar + " = " + (int)b + ";");
                                //TODO: end c3d
                                Simbolo nuevo = new Simbolo(v.identificador,"character", v.value.toString(), TipoSimbolo.Variable, listC3D.getUltimaPosicion(), tempVar);
                                ent.nuevoSimbolo(v.identificador,nuevo);
                                listC3D.incrementPosicion();
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
                            //TODO: c3d
                            char b = ' ';
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.getUltimaPosicion() + " + 0;");
                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add("STACK[(int)P] = " + (int)b + ";");
                            String tempVar = listC3D.generateTemporal(); //declaro el temporal de la variable, ya que en el WHILE hay que manipularla
                            listC3D.codigo3d.add(tempVar + " = " + (int)b + ";");
                            //TODO: end c3d
                            Simbolo nuevo = new Simbolo(v.identificador,"character", " ", TipoSimbolo.Variable, listC3D.getUltimaPosicion(), tempVar);
                            ent.nuevoSimbolo(v.identificador,nuevo);
                            listC3D.incrementPosicion();
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
                                //TODO: c3d
                                listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.getUltimaPosicion() + " + 0;");
                                listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                listC3D.codigo3d.add("STACK[(int)P] = " + v.temporal + ";");
                                String tempVar = listC3D.generateTemporal(); //declaro el temporal de la variable, ya que en el WHILE hay que manipularla
                                listC3D.codigo3d.add(tempVar + " = " + v.temporal + ";");
                                //TODO: end c3d
                                Simbolo nuevo = new Simbolo(v.identificador,"logical", (boolean)v.value, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), tempVar);
                                ent.nuevoSimbolo(v.identificador,nuevo);
                                listC3D.incrementPosicion();
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
                            //TODO: c3d
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = 0;");
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.getUltimaPosicion() + " + 0;");
                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add("STACK[(int)P] = " + listC3D.beforeTemporal() + ";");
                            String tempVar = listC3D.generateTemporal(); //declaro el temporal de la variable, ya que en el WHILE hay que manipularla
                            listC3D.codigo3d.add(tempVar + " = 0;");
                            //TODO: end c3d
                            Simbolo nuevo = new Simbolo(v.identificador,"logical", (boolean)false, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), tempVar);
                            ent.nuevoSimbolo(v.identificador,nuevo);
                            listC3D.incrementPosicion();
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
            if(ctx.expr().getClass().getSimpleName().toLowerCase().equals("orexprcontext")
                    || ctx.expr().getClass().getSimpleName().toLowerCase().equals("andexprcontext")
                    || ctx.expr().getClass().getSimpleName().toLowerCase().equals("equalityexprcontext")
                    || ctx.expr().getClass().getSimpleName().toLowerCase().equals("relationalexprcontext")){
                listC3D.generateTemporal();
                listC3D.generateEtiquetaSalida();
                listC3D.generateEtiquetaPosNeg();
            }
            Value val = visit(ctx.expr());
            if (val.tipo.equals("integer") || val.tipo.equals("real")){
                if(val.c3d.equals("")) { listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.value + ";" ); }//TODO: viene de la forma, var = 3, entonces no entra en opExpr para crear el c3d
            }else if(val.tipo.equals("logical")){
                if (val.deDondeViene.equals("or")){
                    listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 0;");
                    listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalida()+";");
                    listC3D.codigo3d.add(listC3D.getEtiquetaPosNeg()+":");
                    listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 1;");
                    listC3D.codigo3d.add(listC3D.getEtiquetaSalida()+":");
                }else if(val.deDondeViene.equals("and")){
                    listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 1;");
                    listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalida()+";");
                    listC3D.codigo3d.add(listC3D.getEtiquetaPosNeg()+":");
                    listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 0;");
                    listC3D.codigo3d.add(listC3D.getEtiquetaSalida()+":");
                }else if(val.deDondeViene.equals("")){ //viene un ==, !=, <, <=, >, >=
                    listC3D.codigo3d.add("if(" + val.c3d + ") goto " + listC3D.generateEtiqueta() + ";");
                    listC3D.codigo3d.add("  goto " + listC3D.getEtiquetaPosNeg() + ";");
                    listC3D.codigo3d.add(listC3D.lastEtiqueta() + ":");
                    listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 1;");
                    listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalida()+";");
                    listC3D.codigo3d.add(listC3D.getEtiquetaPosNeg() + ":");
                    listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 0;");
                    listC3D.codigo3d.add(listC3D.getEtiquetaSalida()+":");
                }else if(val.deDondeViene.equals("log")){
                    if(Boolean.parseBoolean(val.value.toString())){
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = 1;");
                    }else{
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = 0;");
                    }
                }else if(val.deDondeViene.equals("id")){
                    if(Boolean.parseBoolean(val.value.toString())){
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = 1;");
                    }else{
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = 0;");
                    }
                }
                banderaC3D_OpAND=0;
                banderaC3D_OpOr=0;
            }

            hmDeclaracionVariables.add(new Value(idVar,val.value,val.tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(), listC3D.lastTemporal()));
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

        //TODO: c3d
        if(ctx.expr().getClass().getSimpleName().toLowerCase().equals("orexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("andexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("equalityexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("relationalexprcontext")){
            listC3D.generateTemporal();
            listC3D.generateEtiquetaSalida();
            listC3D.generateEtiquetaPosNeg();
        }
        //TODO: end c3d

        Value val = visit(ctx.expr());

        if(val.value.equals("")){ return new Value("","",0,0); }

        Entorno ent = pilaEntornos.peek();
        Simbolo simb = ent.Buscar(idVar);

        if(simb!=null){
            if(val.tipo.equals(simb.tipo)){
                //TODO: c3d
                if(val.tipo.equals("integer") || val.tipo.equals("real")) {
                    if(val.linea == -1 && val.columna == -1){ //todo: asignar variable a variable, el -1 lo retorna el idExpr
                        Simbolo simb2 = ent.Buscar(val.identificador);
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = STACK[(int)"+ simb2.posicion +"];");
                        listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                        listC3D.codigo3d.add(simb.temporal + " = " + listC3D.lastTemporal() + ";"); //reasignamos valor al temporal de la variable tambien
                    }else if(val.deDondeViene.equals("int_arr_1dim")){
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = HEAP[(int)P];");
                        listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                        listC3D.codigo3d.add(simb.temporal + " = " + listC3D.lastTemporal() + ";");
                    }else{ //todo asignar numero a variable
                        if(val.c3d.equals("")){ //viene solo un numero
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.value + ";");
                            listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add(simb.temporal + " = " + listC3D.lastTemporal() + ";"); //reasignamos valor al temporal de la variable tambien
                        }else{// viene de una operacion aritmetica
                            listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add(simb.temporal + " = " + listC3D.lastTemporal() + ";"); //reasignamos valor al temporal de la variable tambien
                        }
                    }
                }else if(val.tipo.equals("logical")){
                    if(val.linea == -1 && val.columna == -1){ //todo: asignar variable a variable, el -1 lo retorna el idExpr
                        System.out.println("aaj");
                        Simbolo simb2 = ent.Buscar(val.identificador);
                        //System.out.println(simb2.valor);
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = STACK[(int)"+ simb2.posicion +"];");
                        listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                        listC3D.codigo3d.add(simb.temporal + " = " + listC3D.lastTemporal() + ";"); //reasignamos valor al temporal de la variable tambien
                    }else{ //todo asignar valor a variable
                        if (val.deDondeViene.equals("or")){
                            listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 0;");
                            listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalida()+";");
                            listC3D.codigo3d.add(listC3D.getEtiquetaPosNeg()+":");
                            listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 1;");
                            listC3D.codigo3d.add(listC3D.getEtiquetaSalida()+":");
                            listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add(simb.temporal + " = " + listC3D.lastTemporal() + ";"); //reasignamos valor al temporal de la variable tambien
                        }else if(val.deDondeViene.equals("and")){
                            listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 1;");
                            listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalida()+";");
                            listC3D.codigo3d.add(listC3D.getEtiquetaPosNeg()+":");
                            listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 0;");
                            listC3D.codigo3d.add(listC3D.getEtiquetaSalida()+":");
                            listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add(simb.temporal + " = " + listC3D.lastTemporal() + ";"); //reasignamos valor al temporal de la variable tambien
                        }else if(val.deDondeViene.equals("")){ //viene un ==, !=, <, <=, >, >=
                            listC3D.codigo3d.add("if(" + val.c3d + ") goto " + listC3D.generateEtiqueta() + ";");
                            listC3D.codigo3d.add("  goto " + listC3D.getEtiquetaPosNeg() + ";");
                            listC3D.codigo3d.add(listC3D.lastEtiqueta() + ":");
                            listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 1;");
                            listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalida()+";");
                            listC3D.codigo3d.add(listC3D.getEtiquetaPosNeg() + ":");
                            listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 0;");
                            listC3D.codigo3d.add(listC3D.getEtiquetaSalida()+":");
                            listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add(simb.temporal + " = " + listC3D.lastTemporal() + ";"); //reasignamos valor al temporal de la variable tambien
                        }else if(val.deDondeViene.equals("log")){
                            if(Boolean.parseBoolean(val.value.toString())){
                                listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = 1;");
                                listC3D.codigo3d.add(simb.temporal + " = 1;"); //reasignamos valor al temporal de la variable tambien
                            }else{
                                listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = 0;");
                                listC3D.codigo3d.add(simb.temporal + " = 0;"); //reasignamos valor al temporal de la variable tambien
                            }
                        }
                        banderaC3D_OpAND=0;
                        banderaC3D_OpOr=0;
                    }
                }else if(val.tipo.equals("character")){
                    if(val.linea == -1 && val.columna == -1){ //todo: asignar variable a variable, el -1 lo retorna el idExpr
                        Simbolo simb2 = ent.Buscar(val.identificador);
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = STACK[(int)"+ simb2.posicion +"];");
                        listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                        listC3D.codigo3d.add(simb.temporal + " = " + listC3D.lastTemporal() + ";"); //reasignamos valor al temporal de la variable tambien
                    }else{ //todo asignar caracter
                        if(val.c3d.equals("")){
                            char b = String.valueOf(val.value.toString()).toCharArray()[0];
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + (int)b + ";");
                            listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add(simb.temporal + " = " + listC3D.lastTemporal() + ";"); //reasignamos valor al temporal de la variable tambien
                        }
                    }
                }
                //TODO: end c3d
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

        //TODO: c3d
        if(ctx.expr().getClass().getSimpleName().toLowerCase().equals("orexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("andexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("equalityexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("relationalexprcontext")){
            listC3D.generateTemporal();
            listC3D.generateEtiquetaSalida();
            listC3D.generateEtiquetaPosNeg();
        }
        //TODO: end c3d

        Value val = visit(ctx.expr());

        //TODO: c3d
        if(val.tipo.equals("logical")){
            if (val.deDondeViene.equals("or")){
                listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 0;");
                listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalida()+";");
                listC3D.codigo3d.add(listC3D.getEtiquetaPosNeg()+":");
                listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 1;");
                listC3D.codigo3d.add(listC3D.getEtiquetaSalida()+":");
            }else if(val.deDondeViene.equals("and")){
                listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 1;");
                listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalida()+";");
                listC3D.codigo3d.add(listC3D.getEtiquetaPosNeg()+":");
                listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 0;");
                listC3D.codigo3d.add(listC3D.getEtiquetaSalida()+":");
            }
            banderaC3D_OpAND=0;
            banderaC3D_OpOr=0;
        }
        //TODO: end c3d

        if(!val.value.equals("")){
            colaConcatenar.add(val);
        }

        //Hay que imprimir mas de una variable
        if (ctx.printt2()!=null){
            visit(ctx.printt2());
        }

        //TODO: c3d
        if(colaConcatenar.size()>1){ //TODO: viene para imprimir concatenado
            String txtTemp = "";
            for(Value v: colaConcatenar){
                if(v.tipo.equals("logical")){
                    if(Boolean.parseBoolean(v.value.toString())){
                        txtTemp += "1";
                    }else {
                        txtTemp += "0";
                    }
                }else{
                    txtTemp += v.value;
                }
            }
            listC3D.codigo3d.add(listC3D.generateTemporal() + " = H;");
            for (char i : txtTemp.toCharArray())
            {
                listC3D.codigo3d.add("HEAP[(int)H] = " + (int)i + ";");
                listC3D.codigo3d.add("H = H + 1;");
            }
            listC3D.codigo3d.add("HEAP[(int)H] = -1;");
            listC3D.codigo3d.add("H = H + 1;");
            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
            listC3D.codigo3d.add("imprimir_cadena();");
        }else if (colaConcatenar.size() == 1){ //TODO: viene solo una cosa a imprimir
            for(Value v: colaConcatenar){
                if(v.deDondeViene.equals("integer")){ //TODO: integer
                    listC3D.codigo3d.add("printf(\"%d\\n\", (int)" + v.value + ");");
                } else if(v.deDondeViene.equals("real")){ //TODO: tipo real
                    listC3D.codigo3d.add("printf(\"%f\\n\", (double)" + v.value + ");");
                } else if(v.deDondeViene.equals("character")){ //TODO: tipo character
                    if(v.value.toString().length() > 1){
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = H;");
                        for (char i : String.valueOf(v.value).toCharArray())
                        {
                            listC3D.codigo3d.add("HEAP[(int)H] = " + (int)i + ";");
                            listC3D.codigo3d.add("H = H + 1;");
                        }
                        listC3D.codigo3d.add("HEAP[(int)H] = -1;");
                        listC3D.codigo3d.add("H = H + 1;");
                        listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                        listC3D.codigo3d.add("imprimir_cadena();");
                    }else{
                        char b = String.valueOf(v.value.toString()).toCharArray()[0];
                        listC3D.codigo3d.add("printf(\"%c\\n\", (char)" + (int)b + ");");
                    }
                } else if(v.deDondeViene.equals("log") || v.deDondeViene.equals("or") || v.deDondeViene.equals("and")){ //TODO: tipo logical
                    if(Boolean.parseBoolean(v.value.toString())){
                        listC3D.codigo3d.add("printf(\"%d\\n\", (int)1);");
                    }else{
                        listC3D.codigo3d.add("printf(\"%d\\n\", (int)0);");
                    }
                } else if(v.deDondeViene.equals("id")){ //TODO: tipo id
                    if(v.tipo.equals("integer")){
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.posicion + " + 0 ;");
                        listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                        listC3D.codigo3d.add("imprimir_integer();");
                    } else if(v.tipo.equals("real")){
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.posicion + " + 0 ;");
                        listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                        listC3D.codigo3d.add("imprimir_float();");
                    } else if(v.tipo.equals("character")){
                        if(v.value.toString().length() > 1){
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = H;");
                            for (char i : String.valueOf(v.value).toCharArray())
                            {
                                listC3D.codigo3d.add("HEAP[(int)H] = " + (int)i + ";");
                                listC3D.codigo3d.add("H = H + 1;");
                            }
                            listC3D.codigo3d.add("HEAP[(int)H] = -1;");
                            listC3D.codigo3d.add("H = H + 1;");
                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add("imprimir_cadena();");
                        }else{
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.posicion + " + 0 ;");
                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add("imprimir_character();");
                        }
                    } else if(v.tipo.equals("logical")){
                        if(Boolean.parseBoolean(v.value.toString())){
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.posicion + " + 0 ;");
                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add("imprimir_integer();");
                        }else{
                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.posicion + " + 0 ;");
                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                            listC3D.codigo3d.add("imprimir_integer();");
                        }
                    }
                } else if(v.deDondeViene.equals("operacion")){ //TODO: operacion aritmetica
                    if(v.tipo.equals("integer")){
                        listC3D.codigo3d.add("printf(\"%d\\n\", (int)" + listC3D.lastTemporal() + ");");
                    } else if(v.tipo.equals("real")){
                        listC3D.codigo3d.add("printf(\"%f\\n\", (double)" + listC3D.lastTemporal() + ");");
                    }
                } else if(v.linea == -2 && v.columna == -2){ //TODO: ==, !=
                    if(Boolean.parseBoolean(v.value.toString())){
                        listC3D.codigo3d.add("printf(\"%d\\n\", (int)1);");
                    }else{
                        listC3D.codigo3d.add("printf(\"%d\\n\", (int)0);");
                    }
                } else if(v.linea == -3 && v.columna == -3){ //TODO: <, <=, >, >=
                    if(Boolean.parseBoolean(v.value.toString())){
                        listC3D.codigo3d.add("printf(\"%d\\n\", (int)1);");
                    }else{
                        listC3D.codigo3d.add("printf(\"%d\\n\", (int)0);");
                    }
                } else if(v.deDondeViene.equals("int_arr_1dim")){
                    listC3D.codigo3d.add(listC3D.generateTemporal() + " = P;"); //P tiene la posicion actual de H donde esta el valor del array
                    listC3D.codigo3d.add(listC3D.generateTemporal() + " = HEAP[(int)" + listC3D.beforeTemporal() +"];"); //obtengo ese valor de H
                    listC3D.codigo3d.add("printf(\"%d\\n\", (int)" + listC3D.lastTemporal() + ");"); //imprimo
                }else if(v.deDondeViene.equals("real_arr_1dim")){
                    listC3D.codigo3d.add(listC3D.generateTemporal() + " = P;"); //P tiene la posicion actual de H donde esta el valor del array
                    listC3D.codigo3d.add(listC3D.generateTemporal() + " = HEAP[(int)" + listC3D.beforeTemporal() +"];"); //obtengo ese valor de H
                    listC3D.codigo3d.add("printf(\"%f\\n\", (double)" + listC3D.lastTemporal() + ");"); //imprimo
                }else if(v.deDondeViene.equals("character_arr_1dim")){
                    listC3D.codigo3d.add(listC3D.generateTemporal() + " = P;"); //P tiene la posicion actual de H donde esta el valor del array
                    listC3D.codigo3d.add(listC3D.generateTemporal() + " = HEAP[(int)" + listC3D.beforeTemporal() +"];"); //obtengo ese valor de H
                    listC3D.codigo3d.add("printf(\"%c\\n\", (char)" + listC3D.lastTemporal() + ");"); //imprimo
                }else if(v.deDondeViene.equals("arr_completo")){
                    listC3D.codigo3d.add(listC3D.generateTemporal() + " = H;");
                    for (char i : String.valueOf(val.value).toCharArray())
                    {
                        listC3D.codigo3d.add("HEAP[(int)H] = " + (int)i + ";");
                        listC3D.codigo3d.add("H = H + 1;");
                    }
                    listC3D.codigo3d.add("HEAP[(int)H] = -1;");
                    listC3D.codigo3d.add("H = H + 1;");
                    listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                    listC3D.codigo3d.add("imprimir_cadena();");
                }
            }
        }
        //TODO: end c3d

        String txtTemp = "";
        for(Value v: colaConcatenar){
            txtTemp += v.value;
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
        //TODO: c3d
        if(ctx.expr().getClass().getSimpleName().toLowerCase().equals("orexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("andexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("equalityexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("relationalexprcontext")){
            listC3D.generateTemporal();
            listC3D.generateEtiquetaSalida();
            listC3D.generateEtiquetaPosNeg();
        }
        //TODO: end c3d

        Value val = visit(ctx.expr());

        //TODO: c3d
        if(val.tipo.equals("logical")){
            if (val.deDondeViene.equals("or")){
                listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 0;");
                listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalida()+";");
                listC3D.codigo3d.add(listC3D.getEtiquetaPosNeg()+":");
                listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 1;");
                listC3D.codigo3d.add(listC3D.getEtiquetaSalida()+":");
            }else if(val.deDondeViene.equals("and")){
                listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 1;");
                listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalida()+";");
                listC3D.codigo3d.add(listC3D.getEtiquetaPosNeg()+":");
                listC3D.codigo3d.add("  "+listC3D.lastTemporal()+" = 0;");
                listC3D.codigo3d.add(listC3D.getEtiquetaSalida()+":");
            }
            banderaC3D_OpAND=0;
            banderaC3D_OpOr=0;
        }
        //TODO: end c3d

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
                        //TODO: c3d
                        String tempi = listC3D.generateTemporal();
                        listC3D.codigo3d.add(tempi + " = H;");
                        for (int i = 0; i < (int)indice1.value; i++){
                            listC3D.codigo3d.add("HEAP[(int)H] = 0;");
                            listC3D.codigo3d.add("H = H + 1;");
                        }
                        listC3D.codigo3d.add("HEAP[(int)H] = -1;");
                        listC3D.codigo3d.add("H = H + 1;");
                        int[] arrI = new int[(int)indice1.value];
                        Arreglos ni = new Arreglos(idArr,arrI,1,false,false);
                        Simbolo nuevoI = new Simbolo(idArr,"integer", ni, TipoSimbolo.Arreglo, listC3D.getUltimaPosicion(), tempi);
                        ent.nuevoSimbolo(idArr,nuevoI);
                        listC3D.incrementPosicion();
                        //TODO: c3d
//                        int[] arrI = new int[(int)indice1.value];
//                        Arreglos ni = new Arreglos(idArr,arrI,1,false,false);
//                        Simbolo nuevoI = new Simbolo(idArr,"integer", ni, TipoSimbolo.Arreglo);
//                        ent.nuevoSimbolo(idArr,nuevoI);
                        return new Value("","",0,0);
                    case "real":
                        //TODO: c3d
                        String tempr = listC3D.generateTemporal();
                        listC3D.codigo3d.add(tempr + " = H;");
                        for (int i = 0; i < (int)indice1.value; i++){
                            listC3D.codigo3d.add("HEAP[(int)H] = 0.0;");
                            listC3D.codigo3d.add("H = H + 1;");
                        }
                        listC3D.codigo3d.add("HEAP[(int)H] = -1;");
                        listC3D.codigo3d.add("H = H + 1;");
                        double[] arrR = new double[(int)indice1.value];
                        Arreglos nr = new Arreglos(idArr,arrR,1,false,false);
                        Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo, listC3D.getUltimaPosicion(), tempr);
                        ent.nuevoSimbolo(idArr,nuevoR);
                        listC3D.incrementPosicion();
                        //TODO: c3d
                        return new Value("","",0,0);
                    case "character":
                        //TODO: c3d
                        String tempc = listC3D.generateTemporal();
                        listC3D.codigo3d.add(tempc + " = H;");
                        char ch = ' ';
                        for (int i = 0; i < (int)indice1.value; i++){
                            listC3D.codigo3d.add("HEAP[(int)H] = "+ (int)ch +";");
                            listC3D.codigo3d.add("H = H + 1;");
                        }
                        listC3D.codigo3d.add("HEAP[(int)H] = -1;");
                        listC3D.codigo3d.add("H = H + 1;");
                        char[] arrC = new char[(int)indice1.value];
                        Arreglos nc = new Arreglos(idArr,arrC,1,false,false);
                        Simbolo nuevoC = new Simbolo(idArr,"character", nc, TipoSimbolo.Arreglo, listC3D.getUltimaPosicion(), tempc);
                        ent.nuevoSimbolo(idArr,nuevoC);
                        listC3D.incrementPosicion();
                        //TODO: c3d
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
                        //TODO: c3d
                        String tempi = listC3D.generateTemporal();
                        listC3D.codigo3d.add(tempi + " = H;");
                        for (int i = 0; i < (int)indice1.value; i++){
                            listC3D.codigo3d.add("HEAP[(int)H] = 0;");
                            listC3D.codigo3d.add("H = H + 1;");
                        }
                        listC3D.codigo3d.add("HEAP[(int)H] = -1;");
                        listC3D.codigo3d.add("H = H + 1;");
                        int[] arrI = new int[(int)indice1.value];
                        Arreglos ni = new Arreglos(idArr,arrI,1,false,false);
                        Simbolo nuevoI = new Simbolo(idArr,"integer", ni, TipoSimbolo.Arreglo, listC3D.getUltimaPosicion(), tempi);
                        ent.nuevoSimbolo(idArr,nuevoI);
                        listC3D.incrementPosicion();
                        //TODO: c3d
                        return new Value("","",0,0);
                    case "real":
                        //TODO: c3d
                        String tempr = listC3D.generateTemporal();
                        listC3D.codigo3d.add(tempr + " = H;");
                        for (int i = 0; i < (int)indice1.value; i++){
                            listC3D.codigo3d.add("HEAP[(int)H] = 0.0;");
                            listC3D.codigo3d.add("H = H + 1;");
                        }
                        listC3D.codigo3d.add("HEAP[(int)H] = -1;");
                        listC3D.codigo3d.add("H = H + 1;");
                        double[] arrR = new double[(int)indice1.value];
                        Arreglos nr = new Arreglos(idArr,arrR,1,false,false);
                        Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo, listC3D.getUltimaPosicion(), tempr);
                        ent.nuevoSimbolo(idArr,nuevoR);
                        listC3D.incrementPosicion();
                        //TODO: c3d
                        return new Value("","",0,0);
                    case "character":
                        //TODO: c3d
                        String tempc = listC3D.generateTemporal();
                        listC3D.codigo3d.add(tempc + " = H;");
                        char ch = ' ';
                        for (int i = 0; i < (int)indice1.value; i++){
                            listC3D.codigo3d.add("HEAP[(int)H] = "+ (int)ch +";");
                            listC3D.codigo3d.add("H = H + 1;");
                        }
                        listC3D.codigo3d.add("HEAP[(int)H] = -1;");
                        listC3D.codigo3d.add("H = H + 1;");
                        char[] arrC = new char[(int)indice1.value];
                        Arreglos nc = new Arreglos(idArr,arrC,1,false,false);
                        Simbolo nuevoC = new Simbolo(idArr,"character", nc, TipoSimbolo.Arreglo, listC3D.getUltimaPosicion(), tempc);
                        ent.nuevoSimbolo(idArr,nuevoC);
                        listC3D.incrementPosicion();
                        //TODO: c3d
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
                                //TODO: c3d
                                String tempAumentando = listC3D.generateTemporal();
                                listC3D.codigo3d.add("P = " + sim.temporal + ";");
                                listC3D.codigo3d.add(tempAumentando + " = P;");
                                for (int i=0; i<miArr.length;i++){
                                    Value val = visit(ctx.lexpr().expr().get(i));
                                    if(val.tipo.equals("integer")){
                                        listC3D.codigo3d.add("HEAP[(int)" + tempAumentando + "] = " + val.value + ";");
                                        listC3D.codigo3d.add(tempAumentando + " = " + tempAumentando + " + 1;");
                                        miArr[i] = (int)val.value;
                                    }else{
                                        Errores.addError("El arreglo " + ctx.ID().getText() + " es tipo integer, debe ingresar valores integer:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }
                                Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                Simbolo nuevoR = new Simbolo(idArr,"integer", nr, TipoSimbolo.Arreglo, sim.posicion, sim.temporal);
                                ent.AsignarValorVariable(idArr,nuevoR);
                                //TODO: end c3d
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else if(sim.tipo.equals("real")){
                            double[] miArr = (double[]) objArr.valor;
                            if(miArr.length==ctx.lexpr().expr().size()){
                                //TODO: c3d
                                String tempAumentando = listC3D.generateTemporal();
                                listC3D.codigo3d.add("P = " + sim.temporal + ";");
                                listC3D.codigo3d.add(tempAumentando + " = P;");
                                for (int i=0; i<miArr.length;i++){
                                    Value val = visit(ctx.lexpr().expr().get(i));
                                    if(val.tipo.equals("real")){
                                        listC3D.codigo3d.add("HEAP[(int)" + tempAumentando + "] = " + val.value + ";");
                                        listC3D.codigo3d.add(tempAumentando + " = " + tempAumentando + " + 1;");
                                        miArr[i] = (double)val.value;
                                    }else{
                                        Errores.addError("El arreglo " + ctx.ID().getText() + " es tipo real, debe ingresar valores real:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }
                                Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo, sim.posicion, sim.temporal);
                                ent.AsignarValorVariable(idArr,nuevoR);
                                //TODO: end c3d
                                return new Value("","",0,0);
                            }else{
                                Errores.addError("Error: La cantidad de valores no coincide con el tamano del arreglo " + ctx.ID().getText() +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else if(sim.tipo.equals("character")){
                            char[] miArr = (char[]) objArr.valor;
                            if(miArr.length==ctx.lexpr().expr().size()){
                                //TODO: c3d
                                String tempAumentando = listC3D.generateTemporal();
                                listC3D.codigo3d.add("P = " + sim.temporal + ";");
                                listC3D.codigo3d.add(tempAumentando + " = P;");
                                for (int i=0; i<miArr.length;i++){
                                    Value val = visit(ctx.lexpr().expr().get(i));
                                    if(val.tipo.equals("character")){
                                        char ch = String.valueOf(val.value).toCharArray()[0];
                                        listC3D.codigo3d.add("HEAP[(int)" + tempAumentando + "] = " + (int)ch + ";");
                                        listC3D.codigo3d.add(tempAumentando + " = " + tempAumentando + " + 1;");
                                        miArr[i] = val.value.toString().charAt(0);
                                    }else{
                                        Errores.addError("El arreglo " + ctx.ID().getText() + " es tipo character, debe ingresar valores character:" +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                        return new Value("","",0,0);
                                    }
                                }
                                Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                Simbolo nuevoR = new Simbolo(idArr,"character", nr, TipoSimbolo.Arreglo, sim.posicion, sim.temporal);
                                ent.AsignarValorVariable(idArr,nuevoR);
                                //TODO: end c3d
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
                if(objArr.dimensiones==1){ //TODO: una dimension
                    if(!objArr.esDinamico){
                        if(sim.tipo.equals("integer")){
                            int[] miArr = (int[]) objArr.valor;
                            if(ctx.lexpr().expr().size()==1){
                                Value val = visit(ctx.lexpr().expr().get(0));//Todo: valor que va entre los corchetes del array
                                if(val.tipo.equals("integer")){
                                    Value valAsig = visit(ctx.expr()); //TODO: expresion asignar al arreglo
                                    if(valAsig.tipo.equals("integer")){
                                        try {
                                            //TODO: c3d
                                            if (val.deDondeViene.equals("id") && valAsig.deDondeViene.equals("id")){
                                                String tempPosicion = listC3D.generateTemporal();
                                                listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.temporalAsignadaVar + " - 1; // Asignacion Array, id id");
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + valAsig.temporalAsignadaVar + ";");
                                            }else if(val.deDondeViene.equals("id") && !valAsig.deDondeViene.equals("id")){
                                                String tempPosicion = listC3D.generateTemporal();
                                                listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.temporalAsignadaVar + " - 1; // Asignacion Array, id number");
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + valAsig.value + ";");
//                                                if(valAsig.deDondeViene.equals("int_arr_1dim")){
//                                                    System.out.println("siuu" + sim.temporal + "  " + val.temporalAsignadaVar);
//                                                }
                                            }else if(!val.deDondeViene.equals("id") && valAsig.deDondeViene.equals("id")){
                                                String tempPosicion = listC3D.generateTemporal();
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)val.value-1) + "; // Asignacion Array, number id");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + valAsig.temporalAsignadaVar + ";");
                                            }else{
                                                String tempPosicion = listC3D.generateTemporal();
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)val.value-1) + "; // Asignacion Array, number number");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + valAsig.value + ";");
                                            }
                                            //TODO: end c3d
                                            miArr[(int)val.value-1] = (int)valAsig.value;
                                            Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                            Simbolo nuevoR = new Simbolo(idArr,"integer", nr, TipoSimbolo.Arreglo, sim.posicion, sim.temporal);
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
                                            //TODO: c3d
                                            if (val.deDondeViene.equals("id") && valAsig.deDondeViene.equals("id")){
                                                String tempPosicion = listC3D.generateTemporal();
                                                listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.temporalAsignadaVar + " - 1; // Asignacion Array, id id");
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + valAsig.temporalAsignadaVar + ";");
                                            }else if(val.deDondeViene.equals("id") && !valAsig.deDondeViene.equals("id")){
                                                String tempPosicion = listC3D.generateTemporal();
                                                listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.temporalAsignadaVar + " - 1; // Asignacion Array, id number");
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + valAsig.value + ";");
                                            }else if(!val.deDondeViene.equals("id") && valAsig.deDondeViene.equals("id")){
                                                String tempPosicion = listC3D.generateTemporal();
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)val.value-1) + "; // Asignacion Array, number id");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + valAsig.temporalAsignadaVar + ";");
                                            }else{
                                                String tempPosicion = listC3D.generateTemporal();
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)val.value-1) + "; // Asignacion Array, number number");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + valAsig.value + ";");
                                            }
                                            //TODO: end c3d
                                            miArr[(int)val.value-1] = (double)valAsig.value;
                                            Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                            Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo, sim.posicion, sim.temporal);
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
                                            //TODO: c3d
                                            if (val.deDondeViene.equals("id") && valAsig.deDondeViene.equals("id")){
                                                String tempPosicion = listC3D.generateTemporal();
                                                char ch = String.valueOf(valAsig.value).toCharArray()[0];
                                                listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.temporalAsignadaVar + " - 1; // Asignacion Array, id id");
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + (int)ch + ";");
                                            }else if(val.deDondeViene.equals("id") && !valAsig.deDondeViene.equals("id")){
                                                String tempPosicion = listC3D.generateTemporal();
                                                char ch = String.valueOf(valAsig.value).toCharArray()[0];
                                                listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + val.temporalAsignadaVar + " - 1; // Asignacion Array, id char");
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + (int)ch  + ";");
                                            }else{
                                                String tempPosicion = listC3D.generateTemporal();
                                                char ch = String.valueOf(valAsig.value).toCharArray()[0];
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)val.value-1) + "; // Asignacion Array, number char");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + (int)ch  + ";");
                                            }
                                            //TODO: end c3d
                                            miArr[(int)val.value-1] = valAsig.value.toString().charAt(0);
                                            Arreglos nr = new Arreglos(idArr,miArr,1,false,false);
                                            Simbolo nuevoR = new Simbolo(idArr,"character", nr, TipoSimbolo.Arreglo, sim.posicion, sim.temporal);
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
                                                //TODO: c3d
                                                String tempPosicion = listC3D.generateTemporal();
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)val.value-1) + ";");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + valAsig.value + ";");
                                                //TODO: end c3d
                                                miArr[(int)val.value-1] = (int)valAsig.value;
                                                Arreglos nr = new Arreglos(idArr,miArr,1,true,true);
                                                Simbolo nuevoR = new Simbolo(idArr,"integer", nr, TipoSimbolo.Arreglo, sim.posicion, sim.temporal);
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
                                                //TODO: c3d
                                                String tempPosicion = listC3D.generateTemporal();
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)val.value-1) + ";");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + valAsig.value + ";");
                                                //TODO: end c3d
                                                miArr[(int)val.value-1] = (double)valAsig.value;
                                                Arreglos nr = new Arreglos(idArr,miArr,1,true,true);
                                                Simbolo nuevoR = new Simbolo(idArr,"real", nr, TipoSimbolo.Arreglo, sim.posicion, sim.temporal);
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
                                                //TODO: c3d
                                                String tempPosicion = listC3D.generateTemporal();
                                                char ch = String.valueOf(valAsig.value).toCharArray()[0];
                                                listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)val.value-1) + ";");
                                                listC3D.codigo3d.add(tempPosicion + " = P;");
                                                listC3D.codigo3d.add("HEAP[(int)" + tempPosicion + "] = " + (int)ch + ";");
                                                //TODO: end c3d
                                                miArr[(int)val.value-1] = valAsig.value.toString().charAt(0);
                                                Arreglos nr = new Arreglos(idArr,miArr,1,true,true);
                                                Simbolo nuevoR = new Simbolo(idArr,"character", nr, TipoSimbolo.Arreglo, sim.posicion, sim.temporal);
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
                } else if(objArr.dimensiones==2){ //TODO: dos dimensiones
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
        listC3D.generateEtiquetaGlobalIf();
        for (GramaticaParser.LcondicionesIfContext condicion: condiciones){
            if(condicion.expr().getClass().getSimpleName().toLowerCase().equals("orexprcontext")
                    || condicion.expr().getClass().getSimpleName().toLowerCase().equals("andexprcontext")
                    || condicion.expr().getClass().getSimpleName().toLowerCase().equals("equalityexprcontext")
                    || condicion.expr().getClass().getSimpleName().toLowerCase().equals("relationalexprcontext")
                    || condicion.expr().getClass().getSimpleName().toLowerCase().equals("idatomcontext")
                    || condicion.expr().getClass().getSimpleName().toLowerCase().equals("logicalexprcontext")){
                listC3D.generateTemporal();
                listC3D.generateEtiquetaSalida();
                listC3D.generateEtiquetaPosNeg();
            }
            Value val = visit(condicion.expr());
            try {
                //TODO: c3d
                if (val.deDondeViene.equals("or")){
                    String getEtiquetaSalida = listC3D.getEtiquetaSalida();
                    String getEtiquetaPosNeg = listC3D.getEtiquetaPosNeg();
                    listC3D.generateEtiquetaSalida();
                    String getEtiquetaSalidaGlobalIf = listC3D.getEtiquetaSalidaGlobalIf();
                    listC3D.codigo3d.add("  goto "+getEtiquetaSalida+";");
                    listC3D.codigo3d.add(getEtiquetaPosNeg+":");
                    //TODO: aca si la condicion es positiva
                    pilaEntornos.push(new Entorno(pilaEntornos.peek()));
                    Value res = visitLinstrucciones(condicion.linstrucciones());
                    pilaEntornos.pop();
                    listC3D.codigo3d.add("  goto "+getEtiquetaSalidaGlobalIf+";");
                    listC3D.codigo3d.add(getEtiquetaSalida+":");
                }else if(val.deDondeViene.equals("and")){
                    //Todo: para no duplicar etiquetas
                    String getEtiquetaPosNeg = listC3D.getEtiquetaPosNeg();
                    listC3D.generateEtiquetaSalida();
                    String getEtiquetaSalidaGlobalIf = listC3D.getEtiquetaSalidaGlobalIf();
                    //TODO: aca si la condicion es positiva
                    pilaEntornos.push(new Entorno(pilaEntornos.peek()));
                    Value res = visitLinstrucciones(condicion.linstrucciones());
                    pilaEntornos.pop();
                    listC3D.codigo3d.add("  goto "+getEtiquetaSalidaGlobalIf+";");
                    listC3D.codigo3d.add(getEtiquetaPosNeg+":");
                }else if(val.deDondeViene.equals("")){ //viene un ==, !=, <, <=, >, >=
                    //Todo: para no duplicar etiquetas
                    String generateEtiqueta = listC3D.generateEtiqueta();
                    String getEtiquetaPosNeg = listC3D.getEtiquetaPosNeg();
                    String lastEtiqueta = listC3D.lastEtiqueta();
                    listC3D.generateEtiquetaSalida();
                    String getEtiquetaSalidaGlobalIf = listC3D.getEtiquetaSalidaGlobalIf();
                    listC3D.codigo3d.add("if(" + val.c3d + ") goto " + generateEtiqueta+ ";");
                    listC3D.codigo3d.add("  goto " + getEtiquetaPosNeg + ";");
                    listC3D.codigo3d.add(lastEtiqueta + ":");
                    //TODO: aca si la condicion es positiva
                    pilaEntornos.push(new Entorno(pilaEntornos.peek()));
                    Value res = visitLinstrucciones(condicion.linstrucciones());
                    pilaEntornos.pop();
                    listC3D.codigo3d.add("  goto "+getEtiquetaSalidaGlobalIf+";");
                    listC3D.codigo3d.add(getEtiquetaPosNeg + ":");
                }else if(val.deDondeViene.equals("id")){
                    //Todo: para no duplicar etiquetas
                    String generateEtiqueta = listC3D.generateEtiqueta();
                    String getEtiquetaPosNeg = listC3D.getEtiquetaPosNeg();
                    String lastEtiqueta = listC3D.lastEtiqueta();
                    listC3D.generateEtiquetaSalida();
                    String getEtiquetaSalidaGlobalIf = listC3D.getEtiquetaSalidaGlobalIf();
                    if(Boolean.parseBoolean(val.value.toString())){
                        listC3D.codigo3d.add("if(1 == 1) goto " + generateEtiqueta+ ";");
                    }else{
                        listC3D.codigo3d.add("if(0 == 1) goto " + generateEtiqueta+ ";");
                    }
                    listC3D.codigo3d.add("  goto " + getEtiquetaPosNeg + ";");
                    listC3D.codigo3d.add(lastEtiqueta + ":");
                    pilaEntornos.push(new Entorno(pilaEntornos.peek()));
                    Value res = visitLinstrucciones(condicion.linstrucciones());
                    pilaEntornos.pop();
                    listC3D.codigo3d.add("  goto "+getEtiquetaSalidaGlobalIf+";");
                    listC3D.codigo3d.add(getEtiquetaPosNeg + ":");
                }else if(val.deDondeViene.equals("log")){
                    //Todo: para no duplicar etiquetas
                    String generateEtiqueta = listC3D.generateEtiqueta();
                    String getEtiquetaPosNeg = listC3D.getEtiquetaPosNeg();
                    String lastEtiqueta = listC3D.lastEtiqueta();
                    listC3D.generateEtiquetaSalida();
                    String getEtiquetaSalidaGlobalIf = listC3D.getEtiquetaSalidaGlobalIf();
                    if(Boolean.parseBoolean(val.value.toString())){
                        listC3D.codigo3d.add("if(1 == 1) goto " + generateEtiqueta+ ";");
                    }else{
                        listC3D.codigo3d.add("if(0 == 1) goto " + generateEtiqueta+ ";");
                    }
                    listC3D.codigo3d.add("  goto " + getEtiquetaPosNeg + ";");
                    listC3D.codigo3d.add(lastEtiqueta + ":");
                    pilaEntornos.push(new Entorno(pilaEntornos.peek()));
                    Value res = visitLinstrucciones(condicion.linstrucciones());
                    pilaEntornos.pop();
                    listC3D.codigo3d.add("  goto "+getEtiquetaSalidaGlobalIf+";");
                    listC3D.codigo3d.add(getEtiquetaPosNeg + ":");
                }
                banderaC3D_OpAND=0;
                banderaC3D_OpOr=0;
                //TODO end c3d

//                if((boolean)val.value){ //TODO: si la expresion es true evalua el bloque del IF
//                    pilaEntornos.push(new Entorno(pilaEntornos.peek()));
//                    evaluarBloqueIf = true;
//
//                    Value res = visitLinstrucciones(condicion.linstrucciones()); //TODO: ejecuto las instrucciones, esperando que venga un exit o cycle
//                    if ((res.value.toString().equals("exit") && res.tipo.equals("exit"))
//                            ||(res.value.toString().equals("cycle") && res.tipo.equals("cycle"))){
//                        return res;
//                    }
//
//                    pilaEntornos.pop();
//                    break;
//                }
            }catch (Exception e){
                evaluarBloqueIf = true; //TODO: para que no entre al else
                break;
            }
        }

        //TODO: si ninguna de las condiciones del IF se cumple y este contiene el bloque ELSE, entonces ejecuta ese bloque

        //TODO c3d
        if(ctx.elseBloque() != null){ //si viene el else, ejecuta instrucciones y crea etiqueta global de salida if
            Value res = visitLinstrucciones(ctx.elseBloque().linstrucciones());
            listC3D.codigo3d.add("  goto "+listC3D.getEtiquetaSalidaGlobalIf()+";");
            listC3D.codigo3d.add(listC3D.getEtiquetaSalidaGlobalIf() + ":");
        }else{ //solo crea etiqueta global de salida if
            listC3D.codigo3d.add(listC3D.getEtiquetaSalidaGlobalIf() + ":");
        }
        //TODO end c3d

//        if(!evaluarBloqueIf && ctx.elseBloque() != null){
//            Value res = visitLinstrucciones(ctx.elseBloque().linstrucciones()); //TODO: ejecuto las instrucciones, esperando que venga un exit o cycle
//            if ((res.value.toString().equals("exit") && res.tipo.equals("exit"))
//                    ||(res.value.toString().equals("cycle") && res.tipo.equals("cycle"))){
//                return res;
//            }
//        }

        return new Value("","",0,0);
    }

    public Value visitDoEstructura(GramaticaParser.DoEstructuraContext ctx) {

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();
        List<GramaticaParser.ExprContext> expresiones = ctx.expr();
        String id = ctx.ID().getText();

        Entorno ent = pilaEntornos.peek();

        Value indice1 = visit(expresiones.get(0)); //TODO: valor inicial
        String ddv = listC3D.lastTemporal(); //temporal de hacer la suma (si viene de operacion)
        //System.out.println(indice1.value + " --------------" + indice1.deDondeViene + " ---  " + listC3D.lastTemporal());
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
                                Simbolo rv = new Simbolo(id, sim.tipo, (int)indice1.value, sim.tipoSimbolo, sim.posicion, sim.temporal);
                                act.AsignarValorVariable(id,rv);
                                boolean bandera = true;

                                //TODO: c3d
                                //TODO: aignacion
                                Simbolo simb = act.Buscar(id);
                                if(indice1.deDondeViene.equals("operacion")){ listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + ddv + ";"); }
                                else { listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + indice1.value + ";"); }
                                //listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + indice1.value + ";");
                                listC3D.codigo3d.add("STACK[(int)"+ simb.posicion +"] = "+ listC3D.lastTemporal() + ";");
                                //TODO: nuevo valor
                                String posicionVar = simb.temporal;
                                listC3D.codigo3d.add(posicionVar + " = STACK[(int)" + simb.posicion + "];");

                                String Lregreso = listC3D.generateEtiqueta();
                                String Lv = listC3D.generateEtiqueta();
                                String Lf = listC3D.generateEtiqueta();
                                String Lactualizar = listC3D.generateEtiqueta();
                                pilaCycle.push(Lactualizar); //TODO: para cuando encuentre un cycle haga un goto a la etiqueta de inicio
                                pilaExit.push(Lf); //TODO: para cuando encuentre un exit haga un goto a la etiqueta salida
                                listC3D.codigo3d.add(Lregreso + ": if(" + posicionVar + " <= " + indice2.value + ") goto "+ Lv +";");
                                listC3D.codigo3d.add("  goto " + Lf + ";");
                                listC3D.codigo3d.add(Lactualizar + ":");
                                    //actualizar
                                listC3D.codigo3d.add("  "+posicionVar + " = " + posicionVar +" + " + indice3.value + ";");
                                listC3D.codigo3d.add("  STACK[(int)"+ simb.posicion +"] = "+ posicionVar + ";");
                                    //end actualizar
                                listC3D.codigo3d.add("  goto " + Lregreso + ";");
                                listC3D.codigo3d.add(Lv + ":");
                                    //instrucciones, verdadero
                                pilaEntornos.push(new Entorno(pilaEntornos.peek()));
                                Value res = visitLinstrucciones(ctx.linstrucciones());
                                pilaEntornos.pop();
                                    //end instrucciones
                                listC3D.codigo3d.add("  goto " + Lactualizar + ";");
                                listC3D.codigo3d.add(Lf + ":");

                                pilaCycle.pop();
                                pilaExit.pop();
                                //TODO: end c3d

//                                while(bandera){
//                                    pilaEntornos.push(new Entorno(pilaEntornos.peek()));
//
//                                    //TODO: ejecutamos
//                                    Value res = visitLinstrucciones(ctx.linstrucciones());
//
//                                    //TODO: break antes para que ya no reasigne nuevamente la variable
//                                    if (res.value.toString().equals("exit") && res.tipo.equals("exit")){
//                                        break;
//                                    }
//
//                                    //TODO: reasignando nuevo valor a la variable del indice 1
//                                    Entorno ne = pilaEntornos.peek();
//                                    int getNewIndice = (int)ne.Buscar(id).valor+(int)indice3.value;
//                                    Simbolo ra = new Simbolo(id, sim.tipo, getNewIndice, sim.tipoSimbolo);
//                                    ne.AsignarValorVariable(id,ra);
//
//                                    if(getNewIndice > (int)indice2.value){
//                                        bandera=false;
//                                        //System.out.println("parar yaaa");
//                                    }
//
//                                    //TODO: continue despues para que vuelva reasignar nuevamente la variable y no se encicle
//                                    if (res.value.toString().equals("cycle") && res.tipo.equals("cycle")){
//                                        continue;
//                                    }
//
//                                    pilaEntornos.pop();
//                                }
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

        //Todo: c3d
        if(ctx.expr().getClass().getSimpleName().toLowerCase().equals("orexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("andexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("equalityexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("relationalexprcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("idatomcontext")
                || ctx.expr().getClass().getSimpleName().toLowerCase().equals("logicalexprcontext")){
            listC3D.generateTemporal();
            listC3D.generateEtiquetaSalida();
            listC3D.generateEtiquetaPosNeg();
        }
        //TODO: end c3d

        String Lregreso = listC3D.generateEtiqueta();
        listC3D.codigo3d.add(Lregreso + ":");

//        Entorno act = pilaEntornos.peek();
//        Simbolo simb = act.Buscar(ctx.)
//        String posicionVar = listC3D.generateTemporal();
//        listC3D.codigo3d.add(posicionVar + " = STACK[(int)" + simb.posicion + "];");

        Value val = visit(ctx.expr());

        //TODO: c3d
        if (val.deDondeViene.equals("or")){
            String getEtiquetaSalida = listC3D.getEtiquetaSalida();
            String getEtiquetaPosNeg = listC3D.getEtiquetaPosNeg();
            listC3D.generateEtiquetaSalida();
            pilaCycle.push(Lregreso); //TODO: para cuando encuentre un cycle haga un goto a la etiqueta de inicio
            pilaExit.push(getEtiquetaSalida); //TODO: para cuando encuentre un exit haga un goto a la etiqueta salida
            String getEtiquetaSalidaGlobalIf = listC3D.getEtiquetaSalidaGlobalIf();
            listC3D.codigo3d.add("  goto "+getEtiquetaSalida+";");
            listC3D.codigo3d.add(getEtiquetaPosNeg+":");
            //TODO: aca si la condicion es positiva
            pilaEntornos.push(new Entorno(pilaEntornos.peek()));
            Value res = visitLinstrucciones(ctx.linstrucciones());
            pilaEntornos.pop();
            listC3D.codigo3d.add("  goto "+Lregreso+";");
            listC3D.codigo3d.add(getEtiquetaSalida+":");
            pilaCycle.pop();
            pilaExit.pop();
        }else if(val.deDondeViene.equals("and")){
            //Todo: para no duplicar etiquetas
            String getEtiquetaPosNeg = listC3D.getEtiquetaPosNeg();
            listC3D.generateEtiquetaSalida();
            String getEtiquetaSalidaGlobalIf = listC3D.getEtiquetaSalidaGlobalIf();
            pilaCycle.push(Lregreso); //TODO: para cuando encuentre un cycle haga un goto a la etiqueta de inicio
            pilaExit.push(getEtiquetaPosNeg); //TODO: para cuando encuentre un exit haga un goto a la etiqueta salida
            //TODO: aca si la condicion es positiva
            pilaEntornos.push(new Entorno(pilaEntornos.peek()));
            Value res = visitLinstrucciones(ctx.linstrucciones());
            pilaEntornos.pop();
            listC3D.codigo3d.add("  goto "+Lregreso+";");
            listC3D.codigo3d.add(getEtiquetaPosNeg+":");
            pilaCycle.pop();
            pilaExit.pop();
        }else if(val.deDondeViene.equals("")){ //viene un ==, !=, <, <=, >, >=
            //Todo: para no duplicar etiquetas
            String generateEtiqueta = listC3D.generateEtiqueta();
            String getEtiquetaPosNeg = listC3D.getEtiquetaPosNeg();
            String lastEtiqueta = listC3D.lastEtiqueta();
            listC3D.generateEtiquetaSalida();
            String getEtiquetaSalidaGlobalIf = listC3D.getEtiquetaSalidaGlobalIf();
            pilaCycle.push(Lregreso); //TODO: para cuando encuentre un cycle haga un goto a la etiqueta de inicio
            pilaExit.push(getEtiquetaPosNeg); //TODO: para cuando encuentre un exit haga un goto a la etiqueta salida
            listC3D.codigo3d.add("if(" + val.c3d + ") goto " + generateEtiqueta+ ";");
            listC3D.codigo3d.add("  goto " + getEtiquetaPosNeg + ";");
            listC3D.codigo3d.add(lastEtiqueta + ":");
            //TODO: aca si la condicion es positiva
            pilaEntornos.push(new Entorno(pilaEntornos.peek()));
            Value res = visitLinstrucciones(ctx.linstrucciones());
            pilaEntornos.pop();
            listC3D.codigo3d.add("  goto "+Lregreso+";");
            listC3D.codigo3d.add(getEtiquetaPosNeg + ":");
            pilaCycle.pop();
            pilaExit.pop();
        }else if(val.deDondeViene.equals("id")){
            //Todo: para no duplicar etiquetas
            String generateEtiqueta = listC3D.generateEtiqueta();
            String getEtiquetaPosNeg = listC3D.getEtiquetaPosNeg();
            String lastEtiqueta = listC3D.lastEtiqueta();
            listC3D.generateEtiquetaSalida();
            String getEtiquetaSalidaGlobalIf = listC3D.getEtiquetaSalidaGlobalIf();
            pilaCycle.push(Lregreso); //TODO: para cuando encuentre un cycle haga un goto a la etiqueta de inicio
            pilaExit.push(getEtiquetaPosNeg); //TODO: para cuando encuentre un exit haga un goto a la etiqueta salida
            if(Boolean.parseBoolean(val.value.toString())){
                listC3D.codigo3d.add("if("+ val.temporalAsignadaVar +" == 1) goto " + generateEtiqueta+ ";");
            }else{
                listC3D.codigo3d.add("if("+ val.temporalAsignadaVar +" == 1) goto " + generateEtiqueta+ ";");
            }
            listC3D.codigo3d.add("  goto " + getEtiquetaPosNeg + ";");
            listC3D.codigo3d.add(lastEtiqueta + ":");
            pilaEntornos.push(new Entorno(pilaEntornos.peek()));
            Value res = visitLinstrucciones(ctx.linstrucciones());
            pilaEntornos.pop();
            listC3D.codigo3d.add("  goto "+Lregreso+";");
            listC3D.codigo3d.add(getEtiquetaPosNeg + ":");
            pilaCycle.pop();
            pilaExit.pop();
        }else if(val.deDondeViene.equals("log")){ //TODO: seria un ciclo infinito
            //Todo: para no duplicar etiquetas
            String generateEtiqueta = listC3D.generateEtiqueta();
            String getEtiquetaPosNeg = listC3D.getEtiquetaPosNeg();
            String lastEtiqueta = listC3D.lastEtiqueta();
            listC3D.generateEtiquetaSalida();
            String getEtiquetaSalidaGlobalIf = listC3D.getEtiquetaSalidaGlobalIf();
            if(Boolean.parseBoolean(val.value.toString())){
                listC3D.codigo3d.add("if(1 == 1) goto " + generateEtiqueta+ ";");
            }else{
                listC3D.codigo3d.add("if(0 == 1) goto " + generateEtiqueta+ ";");
            }
            listC3D.codigo3d.add("  goto " + getEtiquetaPosNeg + ";");
            listC3D.codigo3d.add(lastEtiqueta + ":");
            pilaEntornos.push(new Entorno(pilaEntornos.peek()));
            Value res = visitLinstrucciones(ctx.linstrucciones());
            pilaEntornos.pop();
            listC3D.codigo3d.add("  goto "+Lregreso+";");
            listC3D.codigo3d.add(getEtiquetaPosNeg + ":");
        }
        banderaC3D_OpAND=0;
        banderaC3D_OpOr=0;

//        //TODO: verificar si la condicion es correcta
//        if(condicion.value.toString().equals("")){
//            return new Value("","",0,0);
//        }
//
//        try {
//            while ((boolean)condicion.value){
//                pilaEntornos.push(new Entorno(pilaEntornos.peek()));
//
//                Value res = visitLinstrucciones(ctx.linstrucciones());
//
//                if ((res.value.toString().equals("exit") && res.tipo.equals("exit"))
//                        ||(res.value.toString().equals("cycle") && res.tipo.equals("cycle"))){
//                    break;
//                }
//                if ((res.value.toString().equals("cycle") && res.tipo.equals("cycle"))
//                        ||(res.value.toString().equals("cycle") && res.tipo.equals("cycle"))){
//                    continue;
//                }
//
//                condicion = visit(ctx.expr());
//
//                if(condicion.value.toString().equals("")){
//                    break;
//                }
//                pilaEntornos.pop();
//            }
//        }catch (Exception e){
//            Errores.addError("Error: En ciclo do while: " +
//                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
//        }

        return new Value("","",0,0);
    }

    public Value visitControlExit(GramaticaParser.ControlExitContext ctx) {
        listC3D.codigo3d.add("  goto " + pilaExit.peek()+";");
        return new Value("exit","exit",0,0);
    }

    public Value visitControlCycle(GramaticaParser.ControlCycleContext ctx) {
        listC3D.codigo3d.add("  goto " + pilaCycle.peek()+";");
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
//                if(ctx.lexpr()!=null){ //TODO: por si no vienen parametros
//                    for (GramaticaParser.ExprContext expCtx: ctx.lexpr().expr()){ //TODO: recorro los parametros(expr), para guardarlas en una lista
//                        parametros.add(new Simbolo(expCtx.getText(),"",null, TipoSimbolo.Parametro));
//                    }
//                }

                //TODO: c3d
                Entorno ne = new Entorno(pilaEntornos.peek()); //agregamos los parametros al entorno exclusivo de la subr
                if(ctx.lexpr()!=null) { //TODO: por si no vienen parametros
                    for (int i = 0; i < ctx.lexpr().expr().size(); i++) { //guardo parametros con su tipo, OJO: segun el orden en el que vengan
                        String nomVar = ctx.lexpr().expr().get(i).getText();
                        String tipoVar = ctx.ldeclP().declParametros().get(i).type().getText();
                        if (tipoVar.equals("integer")) {
                            Simbolo ns = new Simbolo(nomVar, tipoVar.toLowerCase(), 0, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), listC3D.generateTemporal());
                            ne.nuevoSimbolo(nomVar, ns);
                            parametros.add(ns);
                            //listC3D.codigo3d.add(listC3D.lastTemporal() + " = 0;");
                            listC3D.incrementPosicion();
                        } else if (tipoVar.equals("real")) {
                            Simbolo ns = new Simbolo(nomVar, tipoVar.toLowerCase(), 0.0, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), listC3D.generateTemporal());
                            ne.nuevoSimbolo(nomVar, ns);
                            parametros.add(ns);
                            //listC3D.codigo3d.add(listC3D.lastTemporal() + " = 0;");
                            listC3D.incrementPosicion();
                        } else if (ctx.ldeclP().declParametros().get(i).type().getText().toLowerCase().equals("character")) {
                            Simbolo ns = new Simbolo(nomVar, tipoVar.toLowerCase(), " ", TipoSimbolo.Variable, listC3D.getUltimaPosicion(), listC3D.generateTemporal());
                            ne.nuevoSimbolo(nomVar, ns);
                            parametros.add(ns);
                            //listC3D.codigo3d.add(listC3D.lastTemporal() + " = 32;");
                            listC3D.incrementPosicion();
                        } else if (ctx.ldeclP().declParametros().get(i).type().getText().toLowerCase().equals("logical")) {
                            Simbolo ns = new Simbolo(nomVar, tipoVar.toLowerCase(), false, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), listC3D.generateTemporal());
                            ne.nuevoSimbolo(nomVar, ns);
                            parametros.add(ns);
                            //listC3D.codigo3d.add(listC3D.lastTemporal() + " = 0;");
                            listC3D.incrementPosicion();
                        }
                    }
                }
                //TODO: end c3d

                Subrutina subr = new Subrutina(ctx.id1.getText(), parametros, ctx.linstrucciones(), ctx.ldeclP()); //TODO: creo el objecto subrituna y le paso lista de instrucciones y declaraciones de parametros
                subr.entornoParaSubr = ne; //pasamos entorno exclusivo
                pilaEntornos.peek().nuevoSimbolo(ctx.id1.getText(), new Simbolo(ctx.id1.getText(),"subrutina",subr,TipoSimbolo.Subrutina, listC3D.getUltimaPosicion(), listC3D.generateTemporal())); //Todo guardo en tabla de simbolos
                listC3D.incrementPosicion();

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

                //TODO: creo objeto para ejecutar lista de instrucciones de subr y le paso todos los parametros
                VisitorCtresDsf vctsf = new VisitorCtresDsf(subr.entornoParaSubr, listC3D.getNumberTemporal(), listC3D.getNumberEtiqueta(),
                        listC3D.getUltimaPosicion(), listC3D.getNumberEtiquetaSalida(), listC3D.getNumberEtiquetaPosNeg(),
                        listC3D.getNumberEtiquetaParaAnd(), listC3D.getNumberEtiquetaParaOr(), listC3D.getNumberEtiquetaGlobalIf());

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

                            //pilaEntornos.push(entSubr);

                            //TODO: empiezo a guardar parametros
                            for (int i = 0; i < ctx.lexpr().expr().size(); i++){
                                Value val = visit(ctx.lexpr().expr().get(i)); //TODO: valor del parametro que envian
                                if((val.tipo.equals("arreglo"))){
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
//                                    subr.lparametros.get(i).valor = val.value;
//                                    subr.lparametros.get(i).tipo = subr.ldeclaracionParam.declParametros(i).type().getText();
//                                    subr.lparametros.get(i).tipoSimbolo = TipoSimbolo.Variable;
//                                    pilaEntornos.peek().nuevoSimbolo(subr.lparametros.get(i).identificador, subr.lparametros.get(i));
                                    //TODO: c3d
                                    //TODO: obtengo el tipo de dato, y asigno el valor en su espacio de memoria
                                    Simbolo ns = subr.lparametros.get(i);
                                    if(ns.tipo.equals("integer") || ns.tipo.equals("real")){
                                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + ns.posicion + " + 0;");
                                        listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                        listC3D.codigo3d.add("STACK[(int)P] = " + val.value + ";");
                                        listC3D.codigo3d.add(ns.temporal + " = " + val.value + ";");
                                    }else if(ns.tipo.equals("character")){
                                        char v = val.value.toString().toCharArray()[0];
                                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + ns.posicion + " + 0;");
                                        listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                        listC3D.codigo3d.add("STACK[(int)P] = " + (int)v + ";");
                                        listC3D.codigo3d.add(ns.temporal + " = " + (int)v + ";");
                                    }else if(ns.tipo.equals("logical")){
                                        if(Boolean.parseBoolean(val.value.toString())){
                                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + ns.posicion + " + 0;");
                                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                            listC3D.codigo3d.add("STACK[(int)P] = 1;");
                                            listC3D.codigo3d.add(ns.temporal + " = 1;");
                                        }else{
                                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + ns.posicion + " + 0;");
                                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                            listC3D.codigo3d.add("STACK[(int)P] = 0;");
                                            listC3D.codigo3d.add(ns.temporal + " = 0;");
                                        }
                                    }
                                    //TODO: asigno el valor que envian al parametro, y asigno el valor a la variable en tabla de simbolos
                                    subr.lparametros.get(i).valor = val.value;
                                    vctsf.pilaEntornos.peek().AsignarValorVariable(ns.identificador, subr.lparametros.get(i));
                                    //TODO: end c3d
                                }
                            }
                            //TODO: c3d
                            //TODO: ejecuto las instrucciones de la subrutina, y guardo datos en la nueva lista
                            vctsf.visitLinstrucciones((GramaticaParser.LinstruccionesContext)subr.linstrucciones);
                            if(listC3D.buscarSubrFuncion(subr.nombre.toLowerCase()).equals("null")){
                                String txtSubr = "";
                                txtSubr += "void " + subr.nombre + "() {\n";
                                for (String ln: vctsf.listC3D.codigo3d)
                                    txtSubr += ln + "\n";
                                txtSubr += "return;\n}\n";
                                listC3D.codigo3d_subrFunc.put(subr.nombre.toLowerCase(), txtSubr);

                                //TODO: como dentro de la subrituna pueden crearse nuevas etiquetas, label, etc, obtenemos la cantidad de nuevo
                                listC3D.setValores(vctsf.listC3D.getNumberTemporal(), vctsf.listC3D.getNumberEtiqueta(),
                                        vctsf.listC3D.getUltimaPosicion(), vctsf.listC3D.getNumberEtiquetaSalida(), vctsf.listC3D.getNumberEtiquetaPosNeg(),
                                        vctsf.listC3D.getNumberEtiquetaParaAnd(), vctsf.listC3D.getNumberEtiquetaParaOr(), vctsf.listC3D.getNumberEtiquetaGlobalIf());
                                //TODO: c3d
                            }
                            listC3D.codigo3d.add(subr.nombre.toLowerCase() + "();");


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

                        //TODO: c3d, no tiene parametros, solo se ejecuta
                        //TODO: ejecuto las instrucciones de la subrutina, y guardo datos en la nueva lista
                        if(listC3D.buscarSubrFuncion(subr.nombre.toLowerCase()).equals("null")){
                            vctsf.visitLinstrucciones((GramaticaParser.LinstruccionesContext)subr.linstrucciones);
                            String txtSubr = "";
                            txtSubr += "void " + subr.nombre + "() {\n";
                            for (String ln: vctsf.listC3D.codigo3d)
                                txtSubr += ln + "\n";
                            txtSubr += "return;\n}\n";
                            listC3D.codigo3d_subrFunc.put(subr.nombre.toLowerCase(), txtSubr);
                        }
                        listC3D.codigo3d.add(subr.nombre.toLowerCase() + "();");
                        //TODO: como dentro de la subrituna pueden crearse nuevas etiquetas, label, etc, obtenemos la cantidad de nuevo
                        listC3D.setValores(vctsf.listC3D.getNumberTemporal(), vctsf.listC3D.getNumberEtiqueta(),
                                vctsf.listC3D.getUltimaPosicion(), vctsf.listC3D.getNumberEtiquetaSalida(), vctsf.listC3D.getNumberEtiquetaPosNeg(),
                                vctsf.listC3D.getNumberEtiquetaParaAnd(), vctsf.listC3D.getNumberEtiquetaParaOr(), vctsf.listC3D.getNumberEtiquetaGlobalIf());
                        //TODO: end c3d

//                        pilaEntornos.push(entSubr);
//                        visitLinstrucciones((GramaticaParser.LinstruccionesContext)subr.linstrucciones);
//                        pilaEntornos.pop();
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
//                if(ctx.lexpr()!=null){ //TODO: por si no vienen parametros
//                    for (GramaticaParser.ExprContext expCtx: ctx.lexpr().expr()){ //TODO: recorro los parametros(expr), para guardarlas en una lista
//                        parametros.add(new Simbolo(expCtx.getText(),"",null, TipoSimbolo.Parametro));
//                    }
//                }

                //TODO: c3d
                Entorno ne = new Entorno(pilaEntornos.peek());
                if(ctx.lexpr()!=null){ //TODO: por si no vienen parametros
                    for (int i = 0; i < ctx.lexpr().expr().size(); i++) { //guardo parametros con su tipo, OJO: segun el orden en el que vengan
                        String nomVar = ctx.lexpr().expr().get(i).getText();
                        String tipoVar = ctx.ldeclP().declParametros().get(i).type().getText();
                        if (tipoVar.equals("integer")) {
                            Simbolo ns = new Simbolo(nomVar, tipoVar.toLowerCase(), 0, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), listC3D.generateTemporal());
                            ne.nuevoSimbolo(nomVar, ns);
                            parametros.add(ns);
                            //listC3D.codigo3d.add(listC3D.lastTemporal() + " = 0;");
                            listC3D.incrementPosicion();
                        } else if (tipoVar.equals("real")) {
                            Simbolo ns = new Simbolo(nomVar, tipoVar.toLowerCase(), 0.0, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), listC3D.generateTemporal());
                            ne.nuevoSimbolo(nomVar, ns);
                            parametros.add(ns);
                            //listC3D.codigo3d.add(listC3D.lastTemporal() + " = 0;");
                            listC3D.incrementPosicion();
                        } else if (ctx.ldeclP().declParametros().get(i).type().getText().toLowerCase().equals("character")) {
                            Simbolo ns = new Simbolo(nomVar, tipoVar.toLowerCase(), " ", TipoSimbolo.Variable, listC3D.getUltimaPosicion(), listC3D.generateTemporal());
                            ne.nuevoSimbolo(nomVar, ns);
                            parametros.add(ns);
                            //listC3D.codigo3d.add(listC3D.lastTemporal() + " = 32;");
                            listC3D.incrementPosicion();
                        } else if (ctx.ldeclP().declParametros().get(i).type().getText().toLowerCase().equals("logical")) {
                            Simbolo ns = new Simbolo(nomVar, tipoVar.toLowerCase(), false, TipoSimbolo.Variable, listC3D.getUltimaPosicion(), listC3D.generateTemporal());
                            ne.nuevoSimbolo(nomVar, ns);
                            parametros.add(ns);
                            //listC3D.codigo3d.add(listC3D.lastTemporal() + " = 0;");
                            listC3D.incrementPosicion();
                        }
                    }
                }
                //TODO: end c3d

                Funciones func = new Funciones(ctx.id1.getText(), ctx.idr.getText(), parametros, ctx.linstrucciones(), ctx.ldeclP()); //TODO: creo el objecto funcion y le paso lista de instrucciones y declaraciones de parametros
                func.entornoParaFunc = ne;
                pilaEntornos.peek().nuevoSimbolo(ctx.id1.getText(), new Simbolo(ctx.id1.getText(),"funcion", func, TipoSimbolo.Funcion, listC3D.getUltimaPosicion(), listC3D.generateTemporal())); //Todo guardo en tabla de simbolos
                listC3D.incrementPosicion();
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

                //TODO: creo objeto para ejecutar lista de instrucciones de subr y le paso todos los parametros
                VisitorCtresDsf vctsf = new VisitorCtresDsf(func.entornoParaFunc, listC3D.getNumberTemporal(), listC3D.getNumberEtiqueta(),
                        listC3D.getUltimaPosicion(), listC3D.getNumberEtiquetaSalida(), listC3D.getNumberEtiquetaPosNeg(),
                        listC3D.getNumberEtiquetaParaAnd(), listC3D.getNumberEtiquetaParaOr(), listC3D.getNumberEtiquetaGlobalIf());

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

                            //pilaEntornos.push(entFunc);

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
//                                    func.lparametros.get(i).valor = val.value;
//                                    func.lparametros.get(i).tipo = func.ldeclaracionParam.declParametros(i).type().getText();
//                                    func.lparametros.get(i).tipoSimbolo = TipoSimbolo.Variable;
//                                    pilaEntornos.peek().nuevoSimbolo(func.lparametros.get(i).identificador, func.lparametros.get(i));
                                    //TODO: c3d
                                    //TODO: obtengo el tipo de dato, y asigno el valor en su espacio de memoria
                                    Simbolo ns = func.lparametros.get(i);
                                    if(ns.tipo.equals("integer") || ns.tipo.equals("real")){
                                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + ns.posicion + " + 0;");
                                        listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                        listC3D.codigo3d.add("STACK[(int)P] = " + val.value + ";");
                                        listC3D.codigo3d.add(ns.temporal + " = " + val.value + ";");
                                    }else if(ns.tipo.equals("character")){
                                        char v = val.value.toString().toCharArray()[0];
                                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + ns.posicion + " + 0;");
                                        listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                        listC3D.codigo3d.add("STACK[(int)P] = " + (int)v + ";");
                                        listC3D.codigo3d.add(ns.temporal + " = " + (int)v + ";");
                                    }else if(ns.tipo.equals("logical")){
                                        if(Boolean.parseBoolean(val.value.toString())){
                                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + ns.posicion + " + 0;");
                                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                            listC3D.codigo3d.add("STACK[(int)P] = 1;");
                                            listC3D.codigo3d.add(ns.temporal + " = 1;");
                                        }else{
                                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + ns.posicion + " + 0;");
                                            listC3D.codigo3d.add("P = " + listC3D.lastTemporal() + ";");
                                            listC3D.codigo3d.add("STACK[(int)P] = 0;");
                                            listC3D.codigo3d.add(ns.temporal + " = 0;");
                                        }
                                    }
                                    //TODO: asigno el valor que envian al parametro, y asigno el valor a la variable en tabla de simbolos
                                    func.lparametros.get(i).valor = val.value;
                                    vctsf.pilaEntornos.peek().AsignarValorVariable(ns.identificador, func.lparametros.get(i));
                                    //TODO: end c3d
                                }
                            }

                            //TODO: c3d
                            //TODO: ejecuto las instrucciones de la funcion, y guardo datos en la nueva lista
                            vctsf.visitLinstrucciones((GramaticaParser.LinstruccionesContext)func.linstrucciones);
                            if(listC3D.buscarSubrFuncion(func.nombre.toLowerCase()).equals("null")){
                                String txtSubr = "";
                                txtSubr += "void " + func.nombre + "() {\n";
                                for (String ln: vctsf.listC3D.codigo3d)
                                    txtSubr += ln + "\n";
                                txtSubr += "return;\n}\n";
                                listC3D.codigo3d_subrFunc.put(func.nombre.toLowerCase(), txtSubr);

                                //TODO: como dentro de la funcion pueden crearse nuevas etiquetas, label, etc, obtenemos la cantidad de nuevo
                                listC3D.setValores(vctsf.listC3D.getNumberTemporal(), vctsf.listC3D.getNumberEtiqueta(),
                                        vctsf.listC3D.getUltimaPosicion(), vctsf.listC3D.getNumberEtiquetaSalida(), vctsf.listC3D.getNumberEtiquetaPosNeg(),
                                        vctsf.listC3D.getNumberEtiquetaParaAnd(), vctsf.listC3D.getNumberEtiquetaParaOr(), vctsf.listC3D.getNumberEtiquetaGlobalIf());
                                //TODO: c3d

                            }

                            listC3D.codigo3d.add(func.nombre.toLowerCase() + "();");

                            //TODO: c3d
                            Simbolo varReturn = func.entornoParaFunc.Buscar(func.nombreVariableRetornar);
                            if(varReturn!=null){
                                if(varReturn.tipo.equals("integer")){
                                    return new Value(varReturn.valor, varReturn.tipo,1,1,"","integer");
                                }else if(varReturn.tipo.equals("real")){
                                    return new Value(varReturn.valor, varReturn.tipo,1,1,"","real");
                                }else if(varReturn.tipo.equals("character")){
                                    return new Value(varReturn.valor, varReturn.tipo,1,1,"","character");
                                }else if(varReturn.tipo.equals("logical")){
                                    return new Value(varReturn.valor, varReturn.tipo,1,1,"","log");
                                }
                            }
                            //TODO: end c3d

//                            //TODO: ejecutamos lista de instrucciones
//                            visitLinstrucciones((GramaticaParser.LinstruccionesContext)func.linstrucciones);
//
//                            //TODO: valido si fue declarada la variable a retornar
//                            Simbolo miVar = pilaEntornos.peek().Buscar(func.nombreVariableRetornar);
//                            if(miVar!=null){
//                                pilaEntornos.pop();
//                                return new Value(miVar.valor, miVar.tipo, 0, 0);
//                            }else{
//                                Errores.addError("Error: La variable a retornar de la funcion, tiene que ser declarada: " + func.nombreVariableRetornar +
//                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
//                            }
//                            pilaEntornos.pop();

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

                        //TODO: c3d
                        //TODO: ejecuto las instrucciones de la funcion, y guardo datos en la nueva lista
                        vctsf.visitLinstrucciones((GramaticaParser.LinstruccionesContext)func.linstrucciones);
                        if(listC3D.buscarSubrFuncion(func.nombre.toLowerCase()).equals("null")){
                            String txtSubr = "";
                            txtSubr += "void " + func.nombre + "() {\n";
                            for (String ln: vctsf.listC3D.codigo3d)
                                txtSubr += ln + "\n";
                            txtSubr += "return;\n}\n";
                            listC3D.codigo3d_subrFunc.put(func.nombre.toLowerCase(), txtSubr);
                        }

                        listC3D.codigo3d.add(func.nombre.toLowerCase() + "();");
                        //TODO: como dentro de la funcion pueden crearse nuevas etiquetas, label, etc, obtenemos la cantidad de nuevo
                        listC3D.setValores(vctsf.listC3D.getNumberTemporal(), vctsf.listC3D.getNumberEtiqueta(),
                                vctsf.listC3D.getUltimaPosicion(), vctsf.listC3D.getNumberEtiquetaSalida(), vctsf.listC3D.getNumberEtiquetaPosNeg(),
                                vctsf.listC3D.getNumberEtiquetaParaAnd(), vctsf.listC3D.getNumberEtiquetaParaOr(), vctsf.listC3D.getNumberEtiquetaGlobalIf());
                        //TODO: c3d

                        //TODO: c3d
                        Simbolo varReturn = func.entornoParaFunc.Buscar(func.nombreVariableRetornar);
                        if(varReturn!=null){
                            if(varReturn.tipo.equals("integer")){
                                return new Value(varReturn.valor, varReturn.tipo,1,1,"","integer");
                            }else if(varReturn.tipo.equals("real")){
                                return new Value(varReturn.valor, varReturn.tipo,1,1,"","real");
                            }else if(varReturn.tipo.equals("character")){
                                return new Value(varReturn.valor, varReturn.tipo,1,1,"","character");
                            }else if(varReturn.tipo.equals("logical")){
                                return new Value(varReturn.valor, varReturn.tipo,1,1,"","log");
                            }
                        }
                        //TODO: end c3d

//                        //TODO: ejecuto
//                        pilaEntornos.push(entFunc);
//                        visitLinstrucciones((GramaticaParser.LinstruccionesContext)func.linstrucciones);
//
//                        //TODO: valido si fue declarada la variable a retornar
//                        Simbolo miVar = pilaEntornos.peek().Buscar(func.nombreVariableRetornar);
//                        if(miVar!=null){
//                            pilaEntornos.pop();
//                            return new Value(miVar.valor, miVar.tipo, 0, 0);
//                        }else{
//                            Errores.addError("Error: La variable a retornar de la funcion, tiene que ser declarada: " + func.nombreVariableRetornar +
//                                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
//                        }
//                        pilaEntornos.pop();
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

        return new Value("","",0,0, "", "funcion");
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
                                    //TODO: c3d
                                    if(llListaExpresiones.get(0).deDondeViene.equals("id")){
                                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + llListaExpresiones.get(0).temporalAsignadaVar + " - 1;");
                                        listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                    }else{
                                        listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)llListaExpresiones.get(0).value-1) + ";");
                                    }
                                    //TODO: end c3d
                                    int miArr[] = (int[]) objArr.valor;
                                    try {
                                        //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                        int temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                        llListaExpresiones.clear();
                                        return new Value(temp,"integer",lineaExp,columnaExp,"","int_arr_1dim");
                                    }catch (Exception e){
                                        Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }else{ //TODO: SI es dinamico
                                    if(objArr.deAllocate){
                                        //TODO: c3d
                                        if(llListaExpresiones.get(0).deDondeViene.equals("id")){
                                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + llListaExpresiones.get(0).temporalAsignadaVar + " - 1;");
                                            listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                        }else{
                                            listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)llListaExpresiones.get(0).value-1) + ";");
                                        }
                                        //TODO: end c3d
                                        int miArr[] = (int[]) objArr.valor;
                                        try {
                                            //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                            int temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                            llListaExpresiones.clear();
                                            return new Value(temp,"integer",lineaExp,columnaExp,"","int_arr_1dim");
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
                                    //TODO: c3d
                                    if(llListaExpresiones.get(0).deDondeViene.equals("id")){
                                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + llListaExpresiones.get(0).temporalAsignadaVar + " - 1;");
                                        listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                    }else{
                                        listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)llListaExpresiones.get(0).value-1) + ";");
                                    }
                                    //TODO: end c3d
                                    double miArr[] = (double[]) objArr.valor;
                                    try {
                                        //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                        double temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                        llListaExpresiones.clear();
                                        return new Value(temp,"real",lineaExp,columnaExp,"","real_arr_1dim");
                                    }catch (Exception e){
                                        Errores.addError("Error: Los valores de indices de " + ctx.ID().getText() + " tienen que ser de tipo integer: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }else{ //TODO: SI es dinamico
                                    if(objArr.deAllocate==true){
                                        //TODO: c3d
                                        if(llListaExpresiones.get(0).deDondeViene.equals("id")){
                                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + llListaExpresiones.get(0).temporalAsignadaVar + " - 1;");
                                            listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                        }else{
                                            listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)llListaExpresiones.get(0).value-1) + ";");
                                        }
                                        //TODO: end c3d
                                        double miArr[] = (double[]) objArr.valor;
                                        try {
                                            //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                            double temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                            llListaExpresiones.clear();
                                            return new Value(temp,"real",lineaExp,columnaExp,"","real_arr_1dim");
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
                                    //TODO: c3d
                                    if(llListaExpresiones.get(0).deDondeViene.equals("id")){
                                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + llListaExpresiones.get(0).temporalAsignadaVar + " - 1;");
                                        listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                    }else{
                                        listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)llListaExpresiones.get(0).value-1) + ";");
                                    }
                                    //TODO: end c3d
                                    char miArr[] = (char[]) objArr.valor;
                                    try {
                                        //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                        char temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                        llListaExpresiones.clear();
                                        return new Value(temp,"character",lineaExp,columnaExp,"","character_arr_1dim");
                                    }catch (Exception e){
                                        Errores.addError("Error: Los indices de " + ctx.ID().getText() + " no coinciden con el tamano del arreglo: " +
                                                "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                                    }
                                }else{ //TODO: SI es dinamico
                                    if(objArr.deAllocate){
                                        //TODO: c3d
                                        if(llListaExpresiones.get(0).deDondeViene.equals("id")){
                                            listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + llListaExpresiones.get(0).temporalAsignadaVar + " - 1;");
                                            listC3D.codigo3d.add("P = " + sim.temporal + " + " + listC3D.lastTemporal() + ";");
                                        }else{
                                            listC3D.codigo3d.add("P = " + sim.temporal + " + " + ((int)llListaExpresiones.get(0).value-1) + ";");
                                        }
                                        //TODO: end c3d
                                        char miArr[] = (char[]) objArr.valor;
                                        try {
                                            //TODO: el -1  el primer elemento está en el índice 1, segun el enunciado
                                            char temp = miArr[(int)llListaExpresiones.get(0).value-1];
                                            llListaExpresiones.clear();
                                            return new Value(temp,"character",lineaExp,columnaExp,"","character_arr_1dim");
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
            return  new Value(op3,"integer",lineaExp,columnaExp, "", "");
        }else if((left.tipo.equals("integer") && right.tipo.equals("real")) //retorna un real
                || (left.tipo.equals("real") && right.tipo.equals("integer"))
                || (left.tipo.equals("real") && right.tipo.equals("real"))){

            return  new Value(Math.pow(Double.parseDouble(left.value.toString()),Double.parseDouble(right.value.toString())),"real",lineaExp,columnaExp,"","");

        }else if((left.tipo.equals("integer") && right.tipo.equals("complex")) //retorna complex
                || (left.tipo.equals("real") && right.tipo.equals("complex"))
                || (left.tipo.equals("complex") && right.tipo.equals("integer"))
                || (left.tipo.equals("complex") && right.tipo.equals("real"))
                || (left.tipo.equals("complex") && right.tipo.equals("complex"))){

            return  new Value(Math.pow(Double.parseDouble(left.value.toString()),Double.parseDouble(right.value.toString())),"complex",lineaExp,columnaExp,"","");

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
                    //TODO: c3d
                    String bandera0 = "";
                    if(left.c3d.equals("") && right.c3d.equals("")){
                        String izquierda = left.value.toString();
                        String derecha = right.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera0 = "vdo"; // -> viene de operacion entre dos hojas
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " * " + derecha + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("")){
                        String derecha = right.value.toString();
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera0 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeTemporal()+ " * " + derecha + ";");
                    }else if(left.c3d.equals("") && right.c3d.equals("vdo")){
                        String izquierda = left.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        bandera0 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " * " + listC3D.beforeTemporal() + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("vdo")){
                        bandera0 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeBeforeTemporal() + " * " + listC3D.beforeTemporal() + ";");
                    }
                    //TODO: end c3d

                    return  new Value(Integer.parseInt(left.value.toString()) * Integer.parseInt(right.value.toString()),"integer",lineaExp,columnaExp, bandera0, "operacion");
                case '/':
                    int op1 = Integer.parseInt(left.value.toString());
                    int op2 = Integer.parseInt(right.value.toString());
                    double op3 = 0;
                    try {
                        op3 = (op1/op2);
                    }catch (Exception e){
                        //System.out.println("/ 0");
                    }

                    //TODO: c3d
                    String bandera1= "";
                    if(left.c3d.equals("") && right.c3d.equals("")){
                        String izquierda = left.value.toString();
                        String derecha = right.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera1 = "vdo"; // -> viene de operacion entre dos hojas
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " / " + derecha + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("")){
                        String derecha = right.value.toString();
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera1 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeTemporal()+ " / " + derecha + ";");
                    }else if(left.c3d.equals("") && right.c3d.equals("vdo")){
                        String izquierda = left.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        bandera1 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " / " + listC3D.beforeTemporal() + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("vdo")){
                        bandera1 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeBeforeTemporal() + " / " + listC3D.beforeTemporal() + ";");
                    }
                    //TODO: end c3d

                    return new Value(Math.round(op3),"integer",lineaExp,columnaExp, bandera1, "operacion");
                case '+':
                    int op4 = Integer.parseInt(left.value.toString());
                    int op5 = Integer.parseInt(right.value.toString());
                    int op6 = op4+op5;

                    //TODO: c3d
                    String bandera2 = "";
                    if(left.c3d.equals("") && right.c3d.equals("")){
                        String izquierda = left.value.toString();
                        String derecha = right.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera2 = "vdo"; // -> viene de operacion entre dos hojas
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " + " + derecha + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("")){
                        String derecha = right.value.toString();
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera2 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeTemporal()+ " + " + derecha + ";");
                    }else if(left.c3d.equals("") && right.c3d.equals("vdo")){
                        String izquierda = left.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        bandera2 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " + " + listC3D.beforeTemporal() + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("vdo")){
                        bandera2 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeBeforeTemporal() + " + " + listC3D.beforeTemporal() + ";");
                    }
                    //TODO: end c3d

                    return  new Value(Integer.parseInt(left.value.toString()) + Integer.parseInt(right.value.toString()),"integer",lineaExp,columnaExp, bandera2, "operacion");
                case '-':
                    //TODO: c3d
                    String bandera3 = "";
                    if(left.c3d.equals("") && right.c3d.equals("")){
                        String izquierda = left.value.toString();
                        String derecha = right.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera3 = "vdo"; // -> viene de operacion entre dos hojas
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " - " + derecha + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("")){
                        String derecha = right.value.toString();
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera3 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeTemporal()+ " - " + derecha + ";");
                    }else if(left.c3d.equals("") && right.c3d.equals("vdo")){
                        String izquierda = left.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        bandera3 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " - " + listC3D.beforeTemporal() + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("vdo")){
                        bandera3 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeBeforeTemporal() + " - " + listC3D.beforeTemporal() + ";");
                    }
                    //TODO: end c3d
                    return  new Value(Integer.parseInt(left.value.toString()) - Integer.parseInt(right.value.toString()),"integer",lineaExp,columnaExp, bandera3, "operacion");
                default:
                    System.out.println("Operacion no valida");
            }
        }else if((left.tipo.equals("integer") && right.tipo.equals("real")) //retorna un real
                || (left.tipo.equals("real") && right.tipo.equals("integer"))
                || (left.tipo.equals("real") && right.tipo.equals("real"))){

            switch (operacion.charAt(0))
            {
                case '*':
                    //TODO: c3d
                    String bandera0 = "";
                    if(left.c3d.equals("") && right.c3d.equals("")){
                        String izquierda = left.value.toString();
                        String derecha = right.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera0 = "vdo"; // -> viene de operacion entre dos hojas
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " * " + derecha + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("")){
                        String derecha = right.value.toString();
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera0 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeTemporal()+ " * " + derecha + ";");
                    }else if(left.c3d.equals("") && right.c3d.equals("vdo")){
                        String izquierda = left.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        bandera0 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " * " + listC3D.beforeTemporal() + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("vdo")){
                        bandera0 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeBeforeTemporal() + " * " + listC3D.beforeTemporal() + ";");
                    }
                    //TODO: end c3d

                    return  new Value(Double.parseDouble(left.value.toString()) * Double.parseDouble(right.value.toString()),"real",lineaExp,columnaExp, bandera0, "operacion");
                case '/':
                    double op1 = Double.parseDouble(left.value.toString());
                    double op2 = Double.parseDouble(right.value.toString());
                    double op3 = (op1/op2);

                    //TODO: c3d
                    String bandera1 = "";
                    if(left.c3d.equals("") && right.c3d.equals("")){
                        String izquierda = left.value.toString();
                        String derecha = right.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera1 = "vdo"; // -> viene de operacion entre dos hojas
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " / " + derecha + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("")){
                        String derecha = right.value.toString();
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera1 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeTemporal()+ " / " + derecha + ";");
                    }else if(left.c3d.equals("") && right.c3d.equals("vdo")){
                        String izquierda = left.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        bandera1 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " / " + listC3D.beforeTemporal() + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("vdo")){
                        bandera1 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeBeforeTemporal() + " / " + listC3D.beforeTemporal() + ";");
                    }
                    //TODO: end c3d

                    return new Value(op3,"real",lineaExp,columnaExp,bandera1, "operacion");
                case '+':
                    //TODO: c3d
                    String bandera2 = "";
                    if(left.c3d.equals("") && right.c3d.equals("")){
                        String izquierda = left.value.toString();
                        String derecha = right.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera2 = "vdo"; // -> viene de operacion entre dos hojas
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " + " + derecha + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("")){
                        String derecha = right.value.toString();
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera2 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeTemporal()+ " + " + derecha + ";");
                    }else if(left.c3d.equals("") && right.c3d.equals("vdo")){
                        String izquierda = left.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        bandera2 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " + " + listC3D.beforeTemporal() + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("vdo")){
                        bandera2 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeBeforeTemporal() + " + " + listC3D.beforeTemporal() + ";");
                    }
                    //TODO: end c3d
                    return  new Value(Double.parseDouble(left.value.toString()) + Double.parseDouble(right.value.toString()),"real",lineaExp,columnaExp, bandera2, "operacion");
                case '-':
                    //TODO: c3d
                    String bandera3 = "";
                    if(left.c3d.equals("") && right.c3d.equals("")){
                        String izquierda = left.value.toString();
                        String derecha = right.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera3 = "vdo"; // -> viene de operacion entre dos hojas
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " - " + derecha + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("")){
                        String derecha = right.value.toString();
                        if(right.deDondeViene.equals("id")){
                            derecha = right.temporalAsignadaVar;
                        }
                        bandera3 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeTemporal()+ " - " + derecha + ";");
                    }else if(left.c3d.equals("") && right.c3d.equals("vdo")){
                        String izquierda = left.value.toString();
                        if (left.deDondeViene.equals("id")){
                            izquierda = left.temporalAsignadaVar;
                        }
                        bandera3 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + izquierda + " - " + listC3D.beforeTemporal() + ";");
                    }else if(left.c3d.equals("vdo") && right.c3d.equals("vdo")){
                        bandera3 = "vdo";
                        listC3D.codigo3d.add(listC3D.generateTemporal() + " = " + listC3D.beforeBeforeTemporal() + " - " + listC3D.beforeTemporal() + ";");
                    }
                    //TODO: end c3d
                    return  new Value(Double.parseDouble(left.value.toString()) - Double.parseDouble(right.value.toString()),"real",lineaExp,columnaExp, bandera3, "operacion");
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

        String izquierda = left.value.toString();
        if (left.deDondeViene.equals("id")){
            izquierda = left.temporalAsignadaVar;
        }
        if (left.deDondeViene.equals("int_arr_1dim")){
            listC3D.codigo3d.add(listC3D.generateTemporal() + " = P;");
            izquierda = listC3D.lastTemporal();
        }

        Value right = visit(ctx.right);

        String derecha = right.value.toString();
        if(right.deDondeViene.equals("id")){
            derecha = right.temporalAsignadaVar;
        }
        if(right.deDondeViene.equals("int_arr_1dim")){
            listC3D.codigo3d.add(listC3D.generateTemporal() + " = P;");
            derecha = listC3D.lastTemporal();
        }

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

//        String izquierda = left.value.toString();
//        String derecha = right.value.toString();
//
//        //TODO: c3d (para while)
//        if (left.deDondeViene.equals("id")){
//            izquierda = left.temporalAsignadaVar;
//        }
//        if(right.deDondeViene.equals("id")){
//            derecha = right.temporalAsignadaVar;
//        }
//        if (left.deDondeViene.equals("int_arr_1dim")){
//            listC3D.codigo3d.add(listC3D.generateTemporal() + " = P;");
//            izquierda = listC3D.lastTemporal();
//        }
//        if(right.deDondeViene.equals("int_arr_1dim")){
//            listC3D.codigo3d.add(listC3D.generateTemporal() + " = P;");
//            izquierda = listC3D.lastTemporal();
//        }
//        //TODO: end c3d

        //TODO: c3d
        String mayor = izquierda + " > " + derecha;
        String mayorigual = izquierda + " >= " + derecha;
        String menor = izquierda + " < " + derecha;
        String menorigual = izquierda + " <= " + derecha;
        //TODO: end c3d
        if (left.value.equals("")||right.value.equals("")){return new Value("","",0,0);}

        try {
            if(left.tipo.equals("integer") && right.tipo.equals("integer")){
                switch (ctx.op.getType()){
                    case GramaticaParser.MAYOR:
                        return new Value(Integer.parseInt(left.value.toString()) > Integer.parseInt(right.value.toString()),"logical",-3,-3, mayor, "");
                    case GramaticaParser.MAYORQUE:
                        return new Value(Integer.parseInt(left.value.toString()) >= Integer.parseInt(right.value.toString()),"logical",-3,-3, mayorigual, "");
                    case GramaticaParser.MENOR:
                        return new Value(Integer.parseInt(left.value.toString()) < Integer.parseInt(right.value.toString()),"logical",-3,-3, menor, "");
                    case GramaticaParser.MENORQUE:
                        return new Value(Integer.parseInt(left.value.toString()) <= Integer.parseInt(right.value.toString()),"logical",-3,-3, menorigual, "");
                    default:
                        System.out.println("ERROR en visitRelationalExpr :(");
                }
            }else if((left.tipo.equals("real") && right.tipo.equals("real")) || (left.tipo.equals("complex") && right.tipo.equals("complex"))){
                switch (ctx.op.getType()){
                    case GramaticaParser.MAYOR:
                        return new Value(Double.parseDouble(left.value.toString()) > Double.parseDouble(right.value.toString()),"logical",-3, -3, mayor, "");
                    case GramaticaParser.MAYORQUE:
                        return new Value(Double.parseDouble(left.value.toString()) >= Double.parseDouble(right.value.toString()),"logical",-3, -3, mayorigual, "");
                    case GramaticaParser.MENOR:
                        return new Value(Double.parseDouble(left.value.toString()) < Double.parseDouble(right.value.toString()),"logical",-3, -3, menor, "");
                    case GramaticaParser.MENORQUE:
                        return new Value(Double.parseDouble(left.value.toString()) <= Double.parseDouble(right.value.toString()),"logical",-3, -3, menorigual, "");
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

        String izquierda = left.value.toString();
        String derecha = right.value.toString();

        Object izq = "";
        Object der = "";

        //TODO: c3d (para while)
        if (left.deDondeViene.equals("id")){
            izquierda = left.temporalAsignadaVar;
        }
        if(right.deDondeViene.equals("id")){
            derecha = right.temporalAsignadaVar;
        }
        //TODO: end c3d

        //TODO: c3d
        String igig = izquierda + " == " + derecha;
        String difig = izquierda + " != " + derecha;
        //TODO: end c3d

        if (left.value.equals("")||right.value.equals("")){return new Value("","",0,0);}

        try {
            if(left.tipo.equals("logical") && right.tipo.equals("logical")) { //TODO: cuando viene por ejemplo: .true. == .true.
                izq = Boolean.parseBoolean(left.value.toString());
                der = Boolean.parseBoolean(right.value.toString());
            }else if(left.tipo.equals("integer") && right.tipo.equals("integer")){
                izq = Integer.parseInt(left.value.toString());
                der = Integer.parseInt(right.value.toString());
            }else if((left.tipo.equals("real") && right.tipo.equals("real")) || (left.tipo.equals("complex") && right.tipo.equals("complex"))) {
                switch (ctx.op.getType()){
                    case GramaticaParser.IGUALIGUAL:
                        return new Value(Math.abs(Double.parseDouble(left.value.toString())-Double.parseDouble(right.value.toString()))<=0.000001,"logical",-2,-2,igig,"");
                    case GramaticaParser.DIFIGUAL:
                        return new Value(!(Math.abs(Double.parseDouble(left.value.toString())-Double.parseDouble(right.value.toString()))<=0.000001),"logical",-2,-2, difig, "");
                    default:
                        System.out.println("ERROR en visitEqualityExpr :(");
                }
            }else if(left.tipo.equals("character") && right.tipo.equals("character")){
                izq = left.value.toString();
                der = right.value.toString();
                switch (ctx.op.getType()){
                    case GramaticaParser.IGUALIGUAL:
                        //TODO: c3d
                        String izq2 = left.value.toString();
                        String der2 = right.value.toString();
                        char izq3 = izq2.toCharArray()[0];
                        char der3 = der2.toCharArray()[0];
                        String izq4 = String.valueOf((int)izq3);
                        String der4 = String.valueOf((int)der3);
                        if (left.deDondeViene.equals("id")){
                            izq4 = left.temporalAsignadaVar;
                        }
                        if(right.deDondeViene.equals("id")){
                            der4 = right.temporalAsignadaVar;
                        }
                        String igig2 = izq4 + " == " + der4;
                        //TODO: end c3d
                        return new Value(izq.equals(der),"logical",-2,-2,igig2,""); //hago return aca, porque en el switch compara con ==
                    case GramaticaParser.DIFIGUAL:
                        //TODO: c3d
                        String izq5 = left.value.toString();
                        String der5 = right.value.toString();
                        char izq6 = izq5.toCharArray()[0];
                        char der6 = der5.toCharArray()[0];
                        String izq7 = String.valueOf((int)izq6);
                        String der7 = String.valueOf((int)der6);
                        if (left.deDondeViene.equals("id")){
                            izq7 = left.temporalAsignadaVar;
                        }
                        if(right.deDondeViene.equals("id")){
                            der7 = right.temporalAsignadaVar;
                        }
                        String difig2 = izq7 + " != " + der7;
                        //TODO: end c3d
                        return new Value(!izq.equals(der),"logical",-2,-2,difig2,""); //hago return aca, porque en el switch compara con ==
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
                    return new Value(izq == der,"logical",-2,-2, igig, "");
                case GramaticaParser.DIFIGUAL:
                    return new Value(izq != der,"logical",-2,-2, difig, "");
                default:
                    System.out.println("ERROR en visitEqualityExpr :(");
            }
        }catch (Exception e){
            System.out.println("Error visitEqualityExpr: "+e);
        }
        return new Value("","",0,0);
    }

    public Value visitAndExpr(GramaticaParser.AndExprContext ctx) {
        //TODO: c3d
        banderaC3D_OpAND++;
        listC3D.generateEtiquetaParaAnd();
        //TODO: end c3d

        Value left = visit(ctx.left);
        if(left.deDondeViene.equals("log") || left.deDondeViene.equals("id")){
            if(Boolean.parseBoolean(left.value.toString())){
                left = new Value(left.value,"logical",left.linea,left.columna, "1 == 1", "");
            }else{
                left = new Value(left.value,"logical",left.linea,left.columna, "0 == 1", "");
            }
        }

        //TODO: c3d
        if(banderaC3D_OpOr==0){
            if(left.deDondeViene.equals("")){
                listC3D.codigo3d.add("if("+left.c3d+") goto "+ listC3D.generateEtiqueta() + ";");
                listC3D.codigo3d.add("  goto " + listC3D.getEtiquetaPosNeg()  + ";");
                listC3D.codigo3d.add(listC3D.lastEtiqueta()+":");
            }
        }else{
            if(left.deDondeViene.equals("")){
                listC3D.codigo3d.add("if("+left.c3d+") goto "+ listC3D.generateEtiqueta() + ";");
                listC3D.codigo3d.add("  goto " + listC3D.getEtiquetaParaOr()  + ";");
                listC3D.codigo3d.add(listC3D.lastEtiqueta()+":");
            }
        }
        //TODO: end c3d

        listC3D.generateEtiquetaParaAnd();
        Value right = visit(ctx.right);
        if(right.deDondeViene.equals("log") || right.deDondeViene.equals("id")){
            if(Boolean.parseBoolean(right.value.toString())){
                right = new Value(right.value,"logical",right.linea,right.columna, "1 == 1", "");
            }else{
                right = new Value(right.value,"logical",right.linea,right.columna, "0 == 1", "");
            }
        }

        //TODO: c3d
        if(banderaC3D_OpOr==0){
            if(right.deDondeViene.equals("")){
                listC3D.codigo3d.add("if("+right.c3d+") goto "+ listC3D.generateEtiqueta() + ";");
                listC3D.codigo3d.add("  goto " + listC3D.getEtiquetaPosNeg() + ";");
                listC3D.codigo3d.add(listC3D.lastEtiqueta()+":");
            }
        }else{
            if(right.deDondeViene.equals("")){
                listC3D.codigo3d.add("if("+right.c3d+") goto "+ listC3D.getEtiquetaPosNeg() + ";");
                listC3D.codigo3d.add("  goto " + listC3D.getEtiquetaParaOr() + ";");
                listC3D.codigo3d.add(listC3D.getEtiquetaParaOr()+":");
            }
        }

        banderaC3D_OpAND--;

        //TODO: end c3d
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        if (left.value.equals("")||right.value.equals("")){return new Value("","",0,0);}

        if(left.tipo.equals("logical") && right.tipo.equals("logical")){
            return new Value(((boolean)left.value && (boolean)right.value),"logical",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),"","and");
        }else{
            Errores.addError("Error: Los tipos de datos de los valores a evaluar en una expresion .and. tienen que ser 'logical' " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            return new Value("","",0,0);
        }
    }

    public Value visitOrExpr(GramaticaParser.OrExprContext ctx) {
        //TODO: c3d
        banderaC3D_OpOr++;
        listC3D.generateEtiquetaParaOr();
        //TODO: end c3d

        Value left = visit(ctx.left);
        if(left.deDondeViene.equals("log") || left.deDondeViene.equals("id")){
            if(Boolean.parseBoolean(left.value.toString())){
                left = new Value(left.value,"logical",left.linea,left.columna, "1 == 1", "");
            }else{
                left = new Value(left.value,"logical",left.linea,left.columna, "0 == 1", "");
            }
        }

        //TODO: c3d
        if(banderaC3D_OpAND==0){
            if(left.deDondeViene.equals("")) {
                listC3D.codigo3d.add("if(" + left.c3d + ") goto " + listC3D.getEtiquetaPosNeg() + ";");
                listC3D.codigo3d.add("  goto " + listC3D.generateEtiqueta() + ";");
                listC3D.codigo3d.add(listC3D.lastEtiqueta() + ":");
            }
        }else{
            if(left.deDondeViene.equals("")){
                listC3D.codigo3d.add("if("+left.c3d+") goto "+ listC3D.getEtiquetaParaAnd() + ";");
                listC3D.codigo3d.add("  goto " + listC3D.generateEtiqueta()  + ";");
                listC3D.codigo3d.add(listC3D.lastEtiqueta()+":");
            }
        }
        //TODO: end c3d

        Value right = visit(ctx.right);
        if(right.deDondeViene.equals("log") || right.deDondeViene.equals("id")){
            if(Boolean.parseBoolean(right.value.toString())){
                right = new Value(right.value,"logical",right.linea,right.columna, "1 == 1", "");
            }else{
                right = new Value(right.value,"logical",right.linea,right.columna, "0 == 1", "");
            }
        }

        //TODO: c3d
        if(banderaC3D_OpAND==0){
            if(right.deDondeViene.equals("")) {
                listC3D.codigo3d.add("if(" + right.c3d + ") goto " + listC3D.getEtiquetaPosNeg() + ";");
                listC3D.codigo3d.add("  goto " + listC3D.generateEtiqueta() + ";");
                listC3D.codigo3d.add(listC3D.lastEtiqueta() + ":");
            }
        }else{
            if(right.deDondeViene.equals("")){
                listC3D.codigo3d.add("if("+right.c3d+") goto "+ listC3D.getEtiquetaParaAnd() + ";");
                listC3D.codigo3d.add("  goto " + listC3D.getEtiquetaPosNeg() + ";");
                listC3D.codigo3d.add(listC3D.getEtiquetaParaAnd()+":");
            }
        }
        //TODO: end c3d

        banderaC3D_OpOr--;

        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        if (left.value.equals("")||right.value.equals("")){return new Value("","",0,0);}

        if(left.tipo.equals("logical") && right.tipo.equals("logical")){
            return new Value(((boolean)left.value || (boolean)right.value),"logical",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(), "", "or");
        }else{
            Errores.addError("Error: Los tipos de datos de los valores a evaluar en una expresion .or. tienen que ser 'logical' " +
                    "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
            return new Value("","",0,0);
        }
    }

    public Value visitParentExpr(GramaticaParser.ParentExprContext ctx) {
        //Value val = visit(ctx.expr());
        //return new Value(val.value,val.tipo,val.linea,val.columna);
        return visit(ctx.expr());
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
        return new Value(str,"character",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(), "", "character");
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
                                return new Value(tam,"integer",lineaExp,columnaExp,"", "integer");
                            }else{
                                Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            int miArr[][] = (int[][]) objArr.valor;
                            int tam = miArr.length * miArr[0].length;
                            return new Value(tam,"integer",lineaExp,columnaExp,"", "integer");
                        }
                    }else if(objArr.dimensiones==1){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                int miArr[] = (int[]) objArr.valor;
                                return new Value(miArr.length,"integer",lineaExp,columnaExp,"", "integer");
                            }else{
                                Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            int miArr[] = (int[]) objArr.valor;
                            return new Value(miArr.length,"integer",lineaExp,columnaExp,"", "integer");
                        }
                    }
                }else if (sim.tipo.equals("real")){
                    Arreglos objArr = (Arreglos) sim.valor;
                    if(objArr.dimensiones==2){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                double miArr[][] = (double[][]) objArr.valor;
                                int tam = miArr.length * miArr[0].length;
                                return new Value(tam,"integer",lineaExp,columnaExp,"", "integer");
                            }else{
                                Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            double miArr[][] = (double[][]) objArr.valor;
                            int tam = miArr.length * miArr[0].length;
                            return new Value(tam,"integer",lineaExp,columnaExp,"", "integer");
                        }
                    }else if(objArr.dimensiones==1){
                        if(objArr.esDinamico) {// TODO: SI es dinamico
                            if(objArr.deAllocate){
                                double miArr[] = (double[]) objArr.valor;
                                return new Value(miArr.length,"integer",lineaExp,columnaExp,"", "integer");
                            }else{
                                Errores.addError("Error: El arreglo dinamico " + ctx.ID().getText() + " aun no se le hace un allocate: " +
                                        "\n     Linea: " + lineaExp + ", Columna: " + columnaExp);
                            }
                        }else{
                            double miArr[] = (double[]) objArr.valor;
                            return new Value(miArr.length,"integer",lineaExp,columnaExp,"", "integer");
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
                            return new Value(tam,"integer",lineaExp,columnaExp,"", "integer");
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
                            return new Value(miArr.length,"integer",lineaExp,columnaExp,"", "integer");
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
        return new Value(Boolean.valueOf(logical),"logical",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(), "", "log");
    }

    public Value visitIdAtom(GramaticaParser.IdAtomContext ctx) {
        int lineaExp = ctx.getStart().getLine();
        int columnaExp = ctx.getStart().getCharPositionInLine();

        Entorno ent = pilaEntornos.peek();
        Simbolo id = ent.Buscar(ctx.ID().getText());

        if(id!=null){
            if(id.tipoSimbolo.name().toLowerCase().equals("variable")){
                //TODO: c3d, -1, para saber si es variable
                return new Value(id.valor,id.tipo,-1,-1, "", "id", ctx.ID().getText(), id.posicion, id.temporal);
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
                                return new Value(tem,"arreglo",lineaExp,columnaExp,"","arr_completo");
                            }else if(id.tipo.equals("real")){
                                double[] miArr = (double[]) objArr.valor;
                                String tem = "";
                                for (int i = 0; i < miArr.length; i++ ){
                                    tem += miArr[i] + " ";
                                }
                                return new Value(tem,"arreglo",lineaExp,columnaExp, "","arr_completo");
                            }else if(id.tipo.equals("character")){
                                char[] miArr = (char[]) objArr.valor;
                                String tem = "";
                                for (int i = 0; i < miArr.length; i++ ){
                                    tem += miArr[i] + " ";
                                }
                                return new Value(tem,"arreglo",lineaExp,columnaExp, "","arr_completo");
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
                                return new Value(tem,"arreglo",lineaExp,columnaExp,"","arr_completo");
                            }else if(id.tipo.equals("real")){
                                double[][] miArr = (double[][]) objArr.valor;
                                String tem = "";
                                for (int i = 0; i < miArr.length; i++ ){
                                    for (int j=0; j < miArr[0].length; j++){
                                        tem += miArr[i][j] + " ";
                                    }
                                }
                                return new Value(tem,"arreglo",lineaExp,columnaExp,"","arr_completo");
                            }else if(id.tipo.equals("character")){
                                char[][] miArr = (char[][]) objArr.valor;
                                String tem = "";
                                for (int i = 0; i < miArr.length; i++ ){
                                    for (int j=0; j < miArr[0].length; j++){
                                        tem += miArr[i][j] + " ";
                                    }
                                }
                                return new Value(tem,"arreglo",lineaExp,columnaExp,"","arr_completo");
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
                            return new Value(tem,"arreglo",lineaExp,columnaExp,"","arr_completo");
                        }else if(id.tipo.equals("real")){
                            double[] miArr = (double[]) objArr.valor;
                            String tem = "";
                            for (int i = 0; i < miArr.length; i++ ){
                                tem += miArr[i] + " ";
                            }
                            return new Value(tem,"arreglo",lineaExp,columnaExp,"","arr_completo");
                        }else if(id.tipo.equals("character")){
                            char[] miArr = (char[]) objArr.valor;
                            String tem = "";
                            for (int i = 0; i < miArr.length; i++ ){
                                tem += miArr[i] + " ";
                            }
                            return new Value(tem,"arreglo",lineaExp,columnaExp,"","arr_completo");
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
                            return new Value(tem,"arreglo",lineaExp,columnaExp,"","arr_completo");
                        }else if(id.tipo.equals("real")){
                            double[][] miArr = (double[][]) objArr.valor;
                            String tem = "";
                            for (int i = 0; i < miArr.length; i++ ){
                                for (int j=0; j < miArr[0].length; j++){
                                    tem += miArr[i][j] + " ";
                                }
                            }
                            return new Value(tem,"arreglo",lineaExp,columnaExp,"","arr_completo");
                        }else if(id.tipo.equals("character")){
                            char[][] miArr = (char[][]) objArr.valor;
                            String tem = "";
                            for (int i = 0; i < miArr.length; i++ ){
                                for (int j=0; j < miArr[0].length; j++){
                                    tem += miArr[i][j] + " ";
                                }
                            }
                            return new Value(tem,"arreglo",lineaExp,columnaExp,"","arr_completo");
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
        return new Value(Integer.valueOf(ctx.getText()),"integer",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(), "", "integer");
    }
    public Value visitRealExpr(GramaticaParser.RealExprContext ctx) {
        //System.out.println(ctx.getText());
        return new Value(Double.valueOf(ctx.getText()),"real",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(), "", "real");
    }

}
