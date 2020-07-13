package compiler.assembly;

import compiler.syntax.tables.ProcedureTable;
import compiler.syntax.tables.VariableTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AssemblyGenerator {

    public StringBuilder writer;
    public BufferedReader reader;
    public VariableTable vt;
    public ProcedureTable pt;

    public AssemblyGenerator(String filename, VariableTable vt, ProcedureTable pt)
            throws FileNotFoundException {
        writer = new StringBuilder();
        reader = new BufferedReader(new FileReader(filename));
        this.vt = vt;
        this.pt = pt;
    }

    public void toAssembly() throws IOException {

        String line = reader.readLine();
        writer.append("global_main\n" +
                "extern _printf, _gets\n");

        // Asignacion de memoria a las variables.
        getSectionData();

        // Assignacion de memoria para la pila.
        writer.append("section .bss\n" +
                "DISP resb 1000\n");

        writer.append("section .text\n" +
                "");
    }

    private void getSectionData() {

        // TODO Agregar variables del programa
        writer.append("");
    }
}
