package compiler.syntax.tables;

import compiler.output.Output;

import java.util.HashMap;

public class VariableTable {

    private static final HashMap<String, Variable> table = new HashMap<>();

    public void put(String key, Variable variable) {
        if (!table.containsKey(key)) {
            //Insertar variable en la tabla de variables
            table.put(key, variable);

            //Escribir variable en el fichero de la tabla de variables
            Output.writeVariable(key, variable);
        }
    }

    public Variable get(String key) {
        return table.get(key);
    }

    public static HashMap<String, Variable> getTable() {
        return table;
    }
}