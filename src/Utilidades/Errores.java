package Utilidades;

import java.util.Stack;

public class Errores {

    public static Stack<String> pilaErrores = new Stack<String>(); //TODO: Errores a mostrar

    public static void addError(String imp){
        pilaErrores.push(imp);
    }

    public static String getErrores(){
        String impresiones = "";
        for (int i = 0; i < pilaErrores.size(); i++){
            if(!pilaErrores.get(i).equals("")){
                impresiones += pilaErrores.get(i) + "\n";
            }
        }
        return impresiones;
    }

    public static String geteErroresSemanticos(){
        String impresiones = "";
        for (int i = 0; i < pilaErrores.size(); i++){
            if(!pilaErrores.get(i).equals("")){
                impresiones += pilaErrores.get(i) + "<br><br>";
            }
        }
        return impresiones;
    }

    public static void clearPilaErrores(){
        pilaErrores.clear();
    }
}
