package compiler.syntax.tables;

import compiler.output.Output;

import java.util.Hashtable;

public class ProcedureTable {

    private Hashtable<String, Procedure> table;

    public ProcedureTable() {
        table = new Hashtable<>();
    }

    public void put (String key, Procedure procedure) {
        if(!table.containsKey(key)) {
            // Insertar procedimiento en la tabla de procedimientos
            table.put(key, procedure);

            // Escribir procedimiento en el fichero de la tabla de procedimientos
            Output.writeProcedure(key, procedure);
        }
    }

    public void close() {
        // TODO Cerrar table HTML con Output
    }

    public Procedure get(String key) {
        return table.get(key);
    }
}
