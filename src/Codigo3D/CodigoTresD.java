package Codigo3D;
import Entorno.Simbolo;

import java.util.*;

public class CodigoTresD {

    public ArrayList<String> codigo3d;
    //public ArrayList<String> codigo3d_subrFunc;
    public HashMap<String, String> codigo3d_subrFunc;
    private int temporal;
    private int label;
    int ultimaPosicion;
    int etiqueaSalida;
    int etiquetaPosNeg;
    int etiquetaParaAnd;
    int etiquetaParaOr;
    int etiquetaGlobalIf;

    public CodigoTresD() {
        this.codigo3d = new ArrayList<>();
        this.codigo3d_subrFunc = new HashMap<String, String>();
        this.temporal = -1;
        this.label = -1;
        this.ultimaPosicion = 0;
    }

    public String generateTemporal()
    {
        this.temporal++;
        return String.valueOf("t" + this.temporal);
    }

    public String lastTemporal()
    {
        return String.valueOf("t" + this.temporal);
    }

    public  int getNumberTemporal() { return this.temporal; }

    public String beforeTemporal(){
        return String.valueOf("t" + (this.temporal-1));
    }

    public String beforeBeforeTemporal(){
        return String.valueOf("t" + (this.temporal-2));
    }

    public String generateEtiqueta()
    {
        this.label++;
        return String.valueOf("L" + this.label);
    }

    public  int getNumberEtiqueta() { return this.label; }

    public String lastEtiqueta()
    {
        return String.valueOf("L" + this.label);
    }

    public String beforeEtiqueta(){
        return String.valueOf("L" + (this.label-1));
    }

    public String beforeBeforeEtiqueta(){
        return String.valueOf("L" + (this.label-2));
    }

    public String generateEtiquetaSalida()
    {
        this.label++;
        this.etiqueaSalida = this.label;
        return String.valueOf("L" + this.etiqueaSalida);
    }

    public int getNumberEtiquetaSalida() { return this.etiqueaSalida; }

    public String generateEtiquetaPosNeg()
    {
        this.label++;
        this.etiquetaPosNeg = this.label;
        return String.valueOf("L" + this.etiquetaPosNeg);
    }

    public int getNumberEtiquetaPosNeg() { return this.etiquetaPosNeg; }

    public String generateEtiquetaParaAnd()
    {
        this.label++;
        this.etiquetaParaAnd = this.label;
        return String.valueOf("L" + this.etiquetaParaAnd);
    }

    public int getNumberEtiquetaParaAnd() { return this.etiquetaParaAnd; }

    public String generateEtiquetaParaOr()
    {
        this.label++;
        this.etiquetaParaOr = this.label;
        return String.valueOf("L" + this.etiquetaParaOr);
    }

    public int getNumberEtiquetaParaOr() { return this.etiquetaParaOr; }

    public String getEtiquetaSalida()
    {
        return String.valueOf("L" + this.etiqueaSalida);
    }

    public String generateEtiquetaGlobalIf()
    {
        this.label++;
        this.etiquetaGlobalIf = this.label;
        return String.valueOf("L" + this.etiquetaGlobalIf);
    }

    public int getNumberEtiquetaGlobalIf() { return this.etiquetaGlobalIf; }

    public String getEtiquetaSalidaGlobalIf()
    {
        return String.valueOf("L" + (this.etiquetaGlobalIf));
    }

    public String getEtiquetaPosNeg()
    {
        return String.valueOf("L" + this.etiquetaPosNeg);
    }

    public String getEtiquetaParaAnd()
    {
        return String.valueOf("L" + this.etiquetaParaAnd);
    }

    public String getEtiquetaParaOr()
    {
        return String.valueOf("L" + this.etiquetaParaOr);
    }

    public int incrementPosicion(){
        return this.ultimaPosicion++;
    }

    public int getUltimaPosicion(){
        return this.ultimaPosicion;
    }

    public void setValores(int temp, int lab, int ultPos, int etiSal, int etiPosNeg, int etiAnd, int etiOr, int etiGlobIf){
        this.temporal = temp;
        this.label = lab;
        this.ultimaPosicion = ultPos;
        this.etiqueaSalida = etiSal;
        this.etiquetaPosNeg = etiPosNeg;
        this.etiquetaParaAnd = etiAnd;
        this.etiquetaParaOr = etiOr;
        this.etiquetaGlobalIf = etiGlobIf;
    }

    private String getPrintInt()
    {
        return "void imprimir_integer(){\n" +
                "   "+this.generateTemporal() + " = STACK[(int)P];\n" +
                "   printf(\"%d\\n\", (int)" + this.lastTemporal() + ");\n" +
                "   return;\n}\n";
    }

   private String getPrintFloat()
    {
        return "void imprimir_float(){\n" +
                "   "+this.generateTemporal() + " = STACK[(int)P];\n" +
                "   printf(\"%f\\n\", (double)" + this.lastTemporal() + ");\n" +
                "   return;\n}\n";
    }

    private String getPrintCharacter()
    {
        return "void imprimir_character(){\n" +
                "   "+this.generateTemporal() + " = STACK[(int)P];\n" +
                "   printf(\"%c\\n\", (char)" + this.lastTemporal() + ");\n" +
                "   return;\n}\n";
    }

    private String getPrintString()
    {
        String tempStart = this.generateTemporal();
        String labelStart = this.generateEtiqueta();
        return "void imprimir_cadena()\n" +
                "{\n" +
                "   "+tempStart + " = P;\n" +
                "   "+labelStart + ":\n" +
                "   "+this.generateTemporal() + " = HEAP[(int)" + tempStart + "];\n" +
                "   if (" + this.lastTemporal() + " != -1) goto L" + (this.label + 1) + ";\n" +
                "   goto L" + (this.label + 2) + ";\n" +
                "   "+this.generateEtiqueta() + ":\n" +
                "       printf(\"%c\", (char)" + this.lastTemporal() + ");\n" +
                "       "+tempStart + "=" + tempStart + " + 1;\n" +
                "   goto " + labelStart + ";\n" +
                "   "+this.generateEtiqueta() + ":\n" +
                "       printf(\"%c\\n\", (char)32);\n" +
                "   return;\n" +
                "}\n\n";
    }

    public String getSubrFunciones(){
        String sf = "";
        for (String ln: this.codigo3d_subrFunc.values())
            sf += ln + "\n";
        return sf;
    }

    public String buscarSubrFuncion(String nombre){
        if (this.codigo3d_subrFunc.containsKey(nombre)){
            return nombre;
        }
        return "null";
    }

    public String getHeader()
    {
        String metodosImpresion = this.getPrintInt() + this.getPrintFloat() + this.getPrintCharacter() + this.getPrintString();
        String temporales = "";
        for (int i = 0; i <= this.temporal; i++) // Todo: para obtener solo listado de temporales: t1, t2, t3, ... , tn;
            temporales += "t" + String.valueOf(i) + (i < this.temporal ? "," : ";\n");

        return "#include <stdio.h>\n" +
                "double STACK[30101999];\n" +
                "double HEAP[30101999];\n" +
                "double P;\n" +
                "double H;\n" +
                "double " + temporales +
                "\n" + metodosImpresion;
    }
}
