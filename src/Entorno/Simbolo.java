package Entorno;

public class Simbolo {
    public String tipo;
    public Object valor;
    public String identificador;
    public TipoSimbolo tipoSimbolo;
    public int posicion;
    public String temporal;

    public enum TipoSimbolo{
        Variable,
        Funcion,
        Subrutina,
        Parametro,
        Arreglo
    }

    public Simbolo(String id, String tipo, Object valor, TipoSimbolo tipoS) {
        this.identificador=id;
        this.tipo = tipo;
        this.valor = valor;
        this.tipoSimbolo = tipoS;
    }

    public Simbolo(String id, String tipo, Object valor, TipoSimbolo tipoS, int posicion, String temporal) {
        this.identificador=id;
        this.tipo = tipo;
        this.valor = valor;
        this.tipoSimbolo = tipoS;
        this.posicion = posicion;
        this.temporal = temporal;
    }

}
