package compiler.assembly;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;
import compiler.syntax.tables.ProcedureTable;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.VariableTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AssemblyGenerator {

    private static final String FILENAME = "assembly_output";
    private static final String FILE_EXTENSION = ".asm";
    private static final String STRING_MAX_SIZE = "512";

    private VariableTable variableTable;
    private ProcedureTable procedureTable;

    /**
     * Constructor con puntero a tablas de variables y procedimientos
     *
     * @param variableTable
     * @param procedureTable
     */
    public AssemblyGenerator(VariableTable variableTable, ProcedureTable procedureTable) {
        this.variableTable = variableTable;
        this.procedureTable = procedureTable;
    }

    public void toAssembly() throws IOException {
        ArrayList<ThreeAddressCode> threeAddressCodes = Generator.getThreeAddressCodes();

        StringBuilder stringBuilder = new StringBuilder();

        //TODO Mirar enlace: https://www.devdungeon.com/content/hello-world-nasm-assembler

        //Escribir cabecera del fichero
        stringBuilder.append("; ------------------------------------\n");
        stringBuilder.append("; Código ensamblador en NASM para Linux 32 bits (i386)\n");
        stringBuilder.append(";\n");
        stringBuilder.append("; Requisitos en Ubuntu 18.04:\n");
        stringBuilder.append(";   sudo apt install build-essential gcc-multilib\n");
        stringBuilder.append(";\n");
        stringBuilder.append("; Comando para compilar:\n");
        stringBuilder.append(";   nasm -felf64 " + FILENAME + ".asm && gcc -m32 " + FILENAME + ".o -o " + FILENAME + "\n");
        stringBuilder.append(";\n");
        stringBuilder.append("; Comando para ejecutar:\n");
        stringBuilder.append(";   ./" + FILENAME + "\n");
        stringBuilder.append(";\n");

        //Etiqueta de inicio
        stringBuilder.append("global main\n\n");

        //TODO Poner también gets? No tenemos función de entrada
        stringBuilder.append("extern printf\n\n");

        stringBuilder.append("; Sección de memoria para las variables no inicializadas\n");
        stringBuilder.append("section .bss\n");

        //Declarar todas las variables de tipo String sin inicializar
        VariableTable.getTable().forEach((key, value) -> {
            System.out.println("Key: " + key + " Value: " + value);

            if (value.getSubtype() == Subtype.STRING && value.getValue() == null)
                stringBuilder.append("    ").append(key).append(" resb " + STRING_MAX_SIZE + "\n");

        });

        stringBuilder.append("\n; Sección de memoria para las variables inicializadas\n");
        stringBuilder.append("section .data\n");

        //Declarar todas las variables excepto las de tipo String sin inicializar
        VariableTable.getTable().forEach((id, variable) -> {
            if (variable.getSubtype() == Subtype.STRING && variable.getValue() != null)
                stringBuilder.append("    ").append(id).append(" db " + variable.getValue() + ", 10, 0\n");

            if (variable.getSubtype() != Subtype.STRING)
                stringBuilder.append("    ").append(id).append(" dd 0\n");
        });

        //Código
        stringBuilder.append("\nsection .text\n\n");
        stringBuilder.append("main:\n");

        //Escribir cada instrucción
        for (ThreeAddressCode tAC : threeAddressCodes) {
            writeOp(stringBuilder, tAC);
        }

        //Exit
        stringBuilder.append("\n    ; ===== ===== ===== ===== =====\n");
        stringBuilder.append("    ; exit(0)\n");
        stringBuilder.append("    mov ebx, 0\n");
        stringBuilder.append("    mov eax, 1\n");
        stringBuilder.append("    int 0x80\n");

        //Escribir fichero
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(FILENAME + FILE_EXTENSION)))) {
            bufferedWriter.write(stringBuilder.toString());
        }
    }


    private void writeOp(StringBuilder stringBuilder, ThreeAddressCode tAC) {

        //Escribir cabecera
        stringBuilder.append("\n    ; ===== ===== ===== ===== =====\n");
        stringBuilder.append("    ; Instrucción ").append(tAC.getOperation()).append("\n");
        stringBuilder.append("    ; Op1: ").append(tAC.getOperand1());
        stringBuilder.append("    Op2: ").append(tAC.getOperand2());
        stringBuilder.append("    Dest: ").append(tAC.getDestination()).append("\n");

        switch (tAC.getOperation()) {
            case "SKIP":
                //Ignorar main
                if (tAC.getDestination().equals("fun#main"))
                    break;

                stringBuilder.append("    ").append(tAC.getDestination()).append(": nop\n");
                break;

            case "COPY":
                break;

            case "PRINT":
                stringBuilder.append("    mov eax, ").append(tAC.getOperand1()).append("\n");
                stringBuilder.append("    push eax\n");
                stringBuilder.append("    call printf\n");
                stringBuilder.append("    pop eax\n");
                break;

            case "PRINTINT":
                stringBuilder.append("    mov eax, ").append(tAC.getOperand1()).append("\n");
                stringBuilder.append("    push eax\n");
                stringBuilder.append("    mov ebx, [").append(tAC.getOperand2()).append("]\n");
                stringBuilder.append("    push ebx\n");
                stringBuilder.append("    call printf\n");
                stringBuilder.append("    pop eax\n");
                stringBuilder.append("    pop ebx\n");
                break;

        }

    }
}
