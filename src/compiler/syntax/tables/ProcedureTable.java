package compiler.syntax.tables;

import compiler.output.Output;

import java.util.Hashtable;

public class ProcedureTable {

    private Hashtable<String, Procedure> table;

    public ProcedureTable() {
        table = new Hashtable<>();

        //Añadir funciones predefinidas a la tabla de procedimientos
        Procedure procedure = new Procedure();
        table.put("print", procedure);
    }

    public boolean put(String key, Procedure procedure) {

        if (table.containsKey(key))
            return false;

        // Insertar procedimiento en la tabla de procedimientos
        table.put(key, procedure);

        // Escribir procedimiento en el fichero de la tabla de procedimientos
        Output.writeProcedure(key, procedure);

        return true;
    }

    public void close() {
        // TODO Cerrar table HTML con Output
    }

    public Procedure get(String key) {
        return table.get(key);
    }
}
