package Utilidades;

public class ErroresLexSint {
    public int fila;
    public int columna;
    public String descripcion;
    public ErrorTipo tipo;

    public enum ErrorTipo {
        Lexico,
        Sintactico
    }

    public ErroresLexSint(int fila, int columna, String descripcion, ErrorTipo tipo) {
        this.fila = fila;
        this.columna = columna;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return  "<tr><td>" + fila + "</td>" +
                "<td>" + columna + "</td>" +
                "<td>" + descripcion + "</td>" +
                "<td>" + tipo + "</td></tr>";
    }
}
