package Tipos;

public class Arreglos {

    public String nombre;
    public Object valor;
    public int dimensiones;
    public boolean esDinamico; //TODO: true si es dinamico, false si no
    public boolean deAllocate; //TODO: true si ya se le asigno un tamano, false si no, true = allocate, false = deallocate

    public Arreglos(String nombre, Object valor, int dimensiones, boolean esDinamico, boolean deAllocate) {
        this.nombre = nombre;
        this.valor = valor;
        this.dimensiones = dimensiones;
        this.esDinamico = esDinamico;
        this.deAllocate = deAllocate;
    }
}
