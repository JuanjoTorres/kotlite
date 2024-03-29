package compiler.syntax.tables;

import java.util.HashMap;

public class ProcedureTable {

    private static HashMap<String, Procedure> table;

    public boolean put(String key, Procedure procedure) {

        if (table.containsKey(key))
            return false;

        // Insertar procedimiento en la tabla de procedimientos
        table.put(key, procedure);

        return true;
    }

    public Procedure get(String key) {
        return table.get(key);
    }

    public static HashMap<String, Procedure> getTable() {
        return table;
    }

    public static void init() {
        table = new HashMap<>();
    }
}
