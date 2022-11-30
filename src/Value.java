public class Value {
    public static Value VOID = new Value(new Object());

    final Object value;
    public String tipo;
    public int linea;
    public int columna;
    public String identificador;
    public String c3d;
    public String temporal;
    public String deDondeViene;
    public int posicion;
    public String temporalAsignadaVar;


    public Value(Object value) {
        this.value = value;
    }

    //TODO: Utilizado para las expresiones
    public Value(Object value, String tipo, int linea, int columna) {
        this.value = value;
        this.tipo = tipo;
        this.linea=linea;
        this.columna=columna;
    }

    //TODO: Utilizado para la lista de declaracion de variables
    public Value(String id, Object value, String tipo, int linea, int columna) {
        this.identificador=id;
        this.value = value;
        this.tipo = tipo;
        this.linea=linea;
        this.columna=columna;
    }

    //TODO: Utilizado para la lista de declaracion de variables en c3d, guarda el ultimo temporal para saber que temporal meter en el stack
    public Value(String id, Object value, String tipo, int linea, int columna, String temporal) {
        this.identificador=id;
        this.value = value;
        this.tipo = tipo;
        this.linea=linea;
        this.columna=columna;
        this.temporal = temporal;
    }

    //TODO: utilizado para el c3d, para operaciones aritmeticas
    public Value(Object value, String tipo, int linea, int columna, String c3d) {
        this.value = value;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.c3d = c3d;
    }

    //TODO: utilizado para c3d, para operaciones relacionales
    public Value(Object value, String tipo, int linea, int columna, String c3d, String deDondeViene) {
        this.value = value;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.c3d = c3d;
        this.deDondeViene = deDondeViene;
    }

    //TODO: utilizado para c3d, retorna el id, cuando se quiere asignar una variable, a == b
    public Value(Object value, String tipo, int linea, int columna, String c3d, String deDondeViene, String identificador, int posicion, String temporalAsignadaVar) {
        this.value = value;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.c3d = c3d;
        this.deDondeViene = deDondeViene;
        this.identificador = identificador;
        this.posicion = posicion;
        this.temporalAsignadaVar = temporalAsignadaVar;
    }



    public Boolean asBoolean() {
        return (Boolean)value;
    }

    public Double asDouble() {
        return (Double)value;
    }
}
