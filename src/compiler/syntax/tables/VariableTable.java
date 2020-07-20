package compiler.syntax.tables;

import java.util.HashMap;

public class VariableTable {

    private static HashMap<String, Variable> table;

    public void put(String key, Variable variable) {
        if (!table.containsKey(key)) {
            //Insertar variable en la tabla de variables
            table.put(key, variable);
        }
    }

    public Variable get(String key) {
        return table.get(key);
    }

    public static HashMap<String, Variable> getTable() {
        return table;
    }

    public static void init() {
        table = new HashMap<>();
    }
}