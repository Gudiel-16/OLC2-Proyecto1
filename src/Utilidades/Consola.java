package Utilidades;

import java.util.Stack;

public class Consola {

    public static Stack<String> pilaImpresion = new Stack<String>(); //TODO: Print a mostrar en consola

    public static void addConsola(String imp){
        pilaImpresion.push(imp);
    }

    public static String getConsola(){
        String impresiones = "";
        for (int i = 0; i < pilaImpresion.size(); i++){
            if(!pilaImpresion.get(i).equals("")){
                if(pilaImpresion.get(i).equals("true")){
                    impresiones += "T" + "\n";
                }else if(pilaImpresion.get(i).equals("false")){
                    impresiones += "F" + "\n";
                }else{
                    impresiones += pilaImpresion.get(i) + "\n";
                }
            }
        }
        return impresiones;
    }

    public static void clearPilaConsola(){
        pilaImpresion.clear();
    }

}
