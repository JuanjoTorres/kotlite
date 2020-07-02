package compiler.syntax.table;

import compiler.output.Output;

import java.util.Hashtable;

public class VariableTable {

    private Hashtable<String, Variable> table;

    public VariableTable() {
        table = new Hashtable<>();
    }

    public void put(String key, Variable variable) {
        if (!table.containsKey(key)) {
            //Insertar variable en la tabla de variables
            table.put(key, variable);

            //Escribir variable en el fichero de la tabla de variables
            Output.writeVariable(key, variable);
        }
    }

    public void close() {
        //TODO Cerrar tble HTML con Output
    }

    public Variable get(String key) {
        return table.get(key);
    }
}