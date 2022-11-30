package Tipos;

import Entorno.Entorno;
import Entorno.Simbolo;
import Gramatica.GramaticaParser;
import java.util.ArrayList;

public class Funciones {

    public String nombre;
    public String nombreVariableRetornar;
    public ArrayList<Simbolo> lparametros;
    public Object linstrucciones;
    public GramaticaParser.LdeclPContext ldeclaracionParam;
    public Entorno entornoParaFunc;

    public Funciones(String nombre, String nombreVariableRetornar, ArrayList<Simbolo> lparametros, Object linstrucciones, GramaticaParser.LdeclPContext ldeclaracionParam) {
        this.nombre = nombre;
        this.nombreVariableRetornar = nombreVariableRetornar;
        this.lparametros = lparametros;
        this.linstrucciones = linstrucciones;
        this.ldeclaracionParam = ldeclaracionParam;
    }
}
