package compiler.syntax.table;

import compiler.KotliteException;

import java.io.*;
import java.util.Hashtable;

public class SymbolTable {

    final int MAX_DIM_EXP = 64;  // Capacidad maxima de la tabla de expansion
    final int MAX_DEEP = 16;     // Profundidad maxima de ambito

    Hashtable tableDes; // Tabla de descripcion
    Symbol[] tableExp;  // Tabla de expansion
    int[] tableSco;     // Tabla de ambito
    int level;

    private static Writer writer;
    private static BufferedWriter buffer;
    private static OutputStreamWriter stream;
    private static FileOutputStream file;

    public SymbolTable () {

        reset();

        try {

            file = new FileOutputStream("symbols_table.html");
            stream = new OutputStreamWriter(file, "UTF-8");
            buffer = new BufferedWriter(stream);
            writer = buffer;

            // HACER VOLCADO DE MEMORIA QUE PIDE EL PROFE
            // FORMATO PDF
        } catch (IOException e) {

            System.err.println("[SYNTAX ERROR] Write Error in Symbol Table: "
                    + e.getMessage());
        }

        tableDes  = new Hashtable<String, Symbol>();
        tableExp = new Symbol[MAX_DIM_EXP];

    }

    public void reset() {
        level = 0;
        tableDes.clear();
        tableSco[level] = 0;
        level++;
        tableSco[level] = 0;
    }

    public void startBlock() {
        level++;
        tableSco[level] = tableSco[level -1];
    }

    public void endBlock() throws KotliteException.SymbolTableException {

        if (level == 0) {
            throw new KotliteException.SymbolTableException("[SYNTAX ERROR] " +
                    "Scope Table index (TA) OutOfBoundsException() in Symbol Table");
        }

        int iniLength, finLength;
        iniLength = tableSco[level];
        level--;
        finLength = tableSco[level];

        for (int length = iniLength; length < finLength; iniLength--) {
            String id = tableExp[length].d.id;
            tableDes.replace(id, tableExp[length].d);
            // FALTA AÑADIR EL ATRIBUTO NP
        }
    }

    public void add(String id, String d) {

        if (isSameLevel(key)) {

            boolean err = false; // COMPARAR TD[ID].NP == N
            if (!err) {
                int idxe = tableSco[level];
                // si tableScop[n] indica el ultimo lugar ocupado
                idxe++;
                tableSco[level] = idxe;
                // tableExp[idxe].d = tableDes.get(id);  // D ES IGUAL EN TD COMO EN TE
                // tableExp[idxe].np = tableDes.get(id); // FALTA AÑADIR NP
            }
        }

        tableDes.put(id , d);
        // FALTA AÑADIR EL ATRIBUTO NP
    }
}
