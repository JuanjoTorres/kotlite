package compiler.assembly;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;
import compiler.syntax.tables.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AssemblyGenerator {

    private static final String FILENAME = "assembly_output";
    private static final String FILE_EXTENSION = ".asm";
    private static final String STRING_MAX_SIZE = "512";

    private HashMap<String, Variable> variableTable;
    private HashMap<String, Procedure> procedureTable;

    /**
     * Constructor sin parámetros
     */
    public AssemblyGenerator() {
        this.variableTable = VariableTable.getTable();
        this.procedureTable = ProcedureTable.getTable();
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
        variableTable.forEach((key, value) -> {
            System.out.println("Key: " + key + " Value: " + value);

            if (value.getSubtype() == Subtype.STRING && value.getValue() == null)
                stringBuilder.append("    ").append(key).append(" resb " + STRING_MAX_SIZE + "\n");

        });

        stringBuilder.append("\n; Sección de memoria para las variables inicializadas\n");
        stringBuilder.append("section .data\n");

        //Declarar todas las variables excepto las de tipo String sin inicializar
        variableTable.forEach((id, variable) -> {
            if (variable.getValue() == null)
                return;

            if (variable.getSubtype() == Subtype.STRING)
                stringBuilder.append("    ").append(id).append(" db " + variable.getValue() + ", 10, 0\n");
            else if (variable.getSubtype() == Subtype.BOOLEAN)
                //Booleanos: true = 1, false = 0
                if (variable.getValue().equals("true"))
                    stringBuilder.append("    ").append(id).append(" dd 1\n");
                else
                    stringBuilder.append("    ").append(id).append(" dd 0\n");
            else if (variable.getSubtype() == Subtype.NONE)
                stringBuilder.append("    ").append(id).append(" dd 0\n");
            else {
                //Por descarte es de tipo entero

                //Comprobar literales numéricos dentro del margen de valores máximos y mínimos permitidos
                if (Long.parseLong(variable.getValue()) > Integer.MAX_VALUE)
                    stringBuilder.append("    ").append(id).append(" dd ").append(Integer.MAX_VALUE).append("\n");
                else if (Long.parseLong(variable.getValue()) < Integer.MIN_VALUE)
                    stringBuilder.append("    ").append(id).append(" dd ").append(Integer.MIN_VALUE).append("\n");
                else
                    stringBuilder.append("    ").append(id).append(" dd ").append(variable.getValue()).append("\n");
            }

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

        String operation = tAC.getOperation();
        String operand1 = tAC.getOperand1();
        String operand2 = tAC.getOperand2();
        String destination = tAC.getDestination();

        //Escribir cabecera
        stringBuilder.append("\n    ; ===== ===== ===== ===== =====\n");
        stringBuilder.append("    ; Instrucción ").append(operation).append("\n");
        stringBuilder.append("    ; Op1: ").append(operand1);
        stringBuilder.append("    Op2: ").append(operand2);
        stringBuilder.append("    Dest: ").append(destination).append("\n");

        switch (operation) {
            case "SKIP":
                stringBuilder.append("    ").append(destination).append(": nop\n");
                break;

            case "COPY":
                //Copia entre dos variables (Dos direcciones de memoria)
                stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                stringBuilder.append("    mov [").append(operand1).append("], eax\n");
                break;

            case "PRINT":
                stringBuilder.append("    mov eax, ").append(operand1).append("\n");
                stringBuilder.append("    push eax\n");
                stringBuilder.append("    call printf\n");
                stringBuilder.append("    pop eax\n");
                break;

            case "PRINTBOOL":
            case "PRINTINT":
                stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                stringBuilder.append("    push eax\n");
                stringBuilder.append("    mov ebx, [").append(operand2).append("]\n");
                stringBuilder.append("    push ebx\n");
                stringBuilder.append("    call printf\n");
                stringBuilder.append("    pop eax\n");
                stringBuilder.append("    pop ebx\n");
                break;
        }

    }
}
