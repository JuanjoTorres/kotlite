package compiler.assembly;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;
import compiler.syntax.tables.ProcedureTable;
import compiler.syntax.tables.VariableTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AssemblyGenerator {

    private static final String OUTPUT_FILENAME = "assembly.as";

    public VariableTable variableTable;
    public ProcedureTable procedureTable;

    //TODO Constructor vacio porque por ahora no se usan las tablas y además no se de donde sacar las referencias
    public AssemblyGenerator() {

    }

    public AssemblyGenerator(VariableTable variableTable, ProcedureTable procedureTable) {
        this.variableTable = variableTable;
        this.procedureTable = procedureTable;
    }

    public void toAssembly() throws IOException {
        ArrayList<ThreeAddressCode> threeAddressCodes = Generator.getThreeAddressCodes();

        StringBuilder stringBuilder = new StringBuilder();

        //Etiqueta de inicio
        stringBuilder.append("global_main\n");

        //TODO Hace falta el _gets? No tenemos función de entrada
        stringBuilder.append("extern _printf, _gets\n");

        // Assignacion de memoria para la pila.
        stringBuilder.append("section .bss\n");
        stringBuilder.append("DISP resb 1000\n");
        stringBuilder.append("section .text\n");

        //Escribir por cada instrucción
        //TODO Aquí dentro hay que llamar al switch, la cabecera se puede quedar aquí
        for (ThreeAddressCode tAC : threeAddressCodes) {
            stringBuilder.append("; ===== ===== ===== ===== =====\n");
            stringBuilder.append("; Instrucción ").append(tAC.getOperation()).append("\n");
            stringBuilder.append("; Op1: ").append(tAC.getOperand1());
            stringBuilder.append(" Op2: ").append(tAC.getOperand2());
            stringBuilder.append(" Dest: ").append(tAC.getDestination()).append("\n\n\n");
        }

        //Escribir fichero
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(OUTPUT_FILENAME)))) {
            bufferedWriter.write(stringBuilder.toString());
        }
    }
}
