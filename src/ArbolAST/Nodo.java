package ArbolAST;

public class Nodo {

    String identificador; //TODO: identificador unico del nodo
    String nombreNodo; //TODO: nombre que va mostrar en el graphiz
    String padre; //TODO: padre del nodo (sera el identificador)
    int nohijos; //TODO: cantidad de hijos que tiene un nodo
    Nodo hijos[]; //TODO: gurda los hijos de un nodo
    Nodo hijosT[]; //TODO: guarda temporalmente los hijos de un nodo, para asi poder redimensionar el array

    public Nodo(String ident, String dato, String pad){
        identificador = ident;
        nombreNodo=dato;
        padre = pad;
        this.nohijos=0;
    }

    public void copiarHijos(){
        //TODO: aumenta en 1 los hijos con el arreglo temporal
        hijosT = new Nodo[nohijos+1];
        for (int i = 0; i < this.nohijos; i++) {
            hijosT[i] = hijos[i];
        }
    }

    public void aumentarHijos(Nodo nodo) {
        copiarHijos();
        hijosT[this.nohijos] = nodo;
        hijos = hijosT;
        this.nohijos++;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void verNodo() {
        System.out.println("{" + nombreNodo + "}");
    }

    public String verIdentificadorNodo() {
        return identificador;
    }
    public String verNombreNodo() {
        return nombreNodo;
    }

    public String verNombrePadre() {
        return padre;
    }

}
