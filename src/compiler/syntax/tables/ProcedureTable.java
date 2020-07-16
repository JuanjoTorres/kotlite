package compiler.syntax.tables;

import compiler.output.Output;

import java.util.HashMap;

public class ProcedureTable {

    private static HashMap<String, Procedure> table = new HashMap<>();

    public boolean put(String key, Procedure procedure) {

        if (table.containsKey(key))
            return false;

        // Insertar procedimiento en la tabla de procedimientos
        table.put(key, procedure);

        // Escribir procedimiento en el fichero de la tabla de procedimientos
        Output.writeProcedure(key, procedure);

        return true;
    }

    public Procedure get(String key) {
        return table.get(key);
    }


    public static HashMap<String, Procedure> getTable() {
        return table;
    }
}
