package Entorno;

import java.util.HashMap;
import java.util.Iterator;

public class Entorno {
    public HashMap<String, Simbolo> TablaSimbolo;

    public Entorno padre;

    public Entorno(Entorno padre) {
        this.padre = padre;
        TablaSimbolo = new HashMap<String, Simbolo>();
    }

    public void nuevoSimbolo(String nombre, Simbolo nuevo){
        if(TablaSimbolo.containsKey(nombre.toLowerCase())){
            //Agregar a la lista de error
            System.out.println("La variable "+ nombre+ " Ya existe class entorno.");
        }else{
            TablaSimbolo.put(nombre.toLowerCase(), nuevo);
        }
    }

    public Simbolo Buscar(String nombre){
        for(Entorno ent = this; ent != null; ent = ent.padre){
            if(ent.TablaSimbolo.containsKey(nombre.toLowerCase())){
                return ent.TablaSimbolo.get(nombre.toLowerCase());
            }
        }
        return null;
    }

    public void AsignarValorVariable(String nombre, Simbolo s){
        for(Entorno ent = this; ent != null; ent = ent.padre){
            if(ent.TablaSimbolo.containsKey(nombre.toLowerCase())){
                ent.TablaSimbolo.put(nombre.toLowerCase(), s);
                break;
            }
        }
    }

    public String getReporteSimbolos(){
        String report = "";
        for(Entorno ent = this; ent != null; ent = ent.padre){
            for (Simbolo misim: ent.TablaSimbolo.values()){
                //System.out.println(misim.identificador+" "+misim.valor.toString()+" " + misim.tipo + " " + misim.tipoSimbolo.name());
                //impresiones+=misim.identificador+" "+misim.valor.toString()+" " + misim.tipo + " " + misim.tipoSimbolo.name()+"<br><br>";
                if(misim.tipoSimbolo.name().toLowerCase().equals("funcion") || misim.tipoSimbolo.name().toLowerCase().equals("subrutina")
                || misim.tipoSimbolo.name().toLowerCase().equals("arreglo")){

                    report += "<tr>"+
                            "<td>" + misim.identificador + "</td>" +
                            "<td>" + "---" + "</td>" +
                            "<td>" + misim.tipo + "</td>" +
                            "<td>" + misim.tipoSimbolo.name() + "</td>" +
                            "</tr>";
                }else{
                    report += "<tr>"+
                            "<td>" + misim.identificador + "</td>" +
                            "<td>" + misim.valor + "</td>" +
                            "<td>" + misim.tipo + "</td>" +
                            "<td>" + misim.tipoSimbolo.name() + "</td>" +
                            "</tr>";
                }
            }
        }
        return report;
    }

}
