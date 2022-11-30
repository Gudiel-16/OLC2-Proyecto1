package ArbolAST;

import java.util.LinkedList;

public class Ast {

    public Nodo raiz;
    public LinkedList<String> dotAst = new LinkedList<String>();

    public Nodo InsertarRaiz(String identificador, String dato) {
        raiz = new Nodo(identificador,dato,"");
        return raiz;
    }

    public void InsertarRecursivo(Nodo nodo, String ident, String dato, String padre) {

        Nodo nuevo = new Nodo(ident, dato, padre);
        if (nodo.getIdentificador().equals(padre)) {
            nodo.aumentarHijos(nuevo);
        } else {
            for (int i = 0; i < nodo.nohijos; i++) {
                if (nodo.hijos[i].getIdentificador().equals(padre)) {
                    nodo.hijos[i].aumentarHijos(nuevo);
                } else {
                    InsertarRecursivo(nodo.hijos[i], ident , dato, padre);
                }
            }
        }
    }

    public void VerHijosRecursivo(Nodo nodo) {
        for (int i = 0; i < nodo.nohijos; i++) {
            nodo.hijos[i].verNodo();
            VerHijosRecursivo(nodo.hijos[i]);
        }
    }

    public void PrintReporte(Nodo nodo) {
        for (int i = 0; i < nodo.nohijos; i++) {
            //System.out.println(nodo.hijos[i].verNombreNodo() + " -> " + nodo.hijos[i].verNombrePadre());
            System.out.println(nodo.hijos[i].verIdentificadorNodo()+"[label = \""+nodo.hijos[i].verNombreNodo() +"\"];");
            System.out.println(nodo.hijos[i].verNombrePadre()+"->"+nodo.hijos[i].verIdentificadorNodo());
            PrintReporte(nodo.hijos[i]);
        }
    }

    public void CrearTextoDot(Nodo nodo) {
        for (int i = 0; i < nodo.nohijos; i++) {
            dotAst.add(nodo.hijos[i].verIdentificadorNodo()+"[label = \""+nodo.hijos[i].verNombreNodo() +"\"];\n");
            dotAst.add(nodo.hijos[i].verNombrePadre()+"->"+nodo.hijos[i].verIdentificadorNodo()+"\n");
            CrearTextoDot(nodo.hijos[i]);
        }
    }

    public String GetTextoDot(){
        String ret ="";
        for (String act: dotAst){
            ret+=act;
        }
        return ret;
    }

    public void LimpiarListaDot(){
        dotAst.clear();
    }


}
