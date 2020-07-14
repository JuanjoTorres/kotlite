package compiler.syntax.tables;

import compiler.output.Output;

import java.util.Hashtable;

public class ProcedureTable {

    private Hashtable<String, Procedure> table;

    public ProcedureTable() {
        table = new Hashtable<>();

        //Añadir funciones predefinidas a la tabla de procedimientos
        table.put("print", new Procedure("print"));
        table.put("printInt", new Procedure("printInt"));
        table.put("printBool", new Procedure("printBool"));
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
