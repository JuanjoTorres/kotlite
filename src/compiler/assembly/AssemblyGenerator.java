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

    private static final String FILENAME = "assembly_output";
    private static final String FILE_EXTENSION = ".asm";
    private static final String STRING_MAX_SIZE = "512";

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
        stringBuilder.append("    temp resb " + STRING_MAX_SIZE + "\n");

        stringBuilder.append("; Sección de memoria para las variables inicializadas\n");
        stringBuilder.append("section .data\n");

        //TODO Recorrer tabla de variables para reservar memoria
        stringBuilder.append("    msg db 'Hello world!',10,0\n\n");

        //Código
        stringBuilder.append("section .text\n\n");
        stringBuilder.append("main:\n");

        //Escribir por cada instrucción
        //TODO Aquí dentro hay que llamar al switch, la cabecera se puede quedar aquí
        for (ThreeAddressCode tAC : threeAddressCodes) {
            if (true)
                break;
            stringBuilder.append("; ===== ===== ===== ===== =====\n");
            stringBuilder.append("; Instrucción ").append(tAC.getOperation()).append("\n");
            stringBuilder.append("; Op1: ").append(tAC.getOperand1());
            stringBuilder.append(" Op2: ").append(tAC.getOperand2());
            stringBuilder.append(" Dest: ").append(tAC.getDestination()).append("\n\n\n");
        }

        //Print
        //TODO No hace falta la variable temp para strings ya inicializados
        stringBuilder.append("; ===== ===== ===== ===== =====\n");
        stringBuilder.append("; printf msg\n");
        stringBuilder.append("    mov eax, msg\n");
        //stringBuilder.append("    mov [temp], eax\n");
        //stringBuilder.append("    mov eax, [temp]\n");
        stringBuilder.append("    push eax\n");
        stringBuilder.append("    call printf\n");
        stringBuilder.append("    pop eax\n");

        //Exit
        stringBuilder.append("; ===== ===== ===== ===== =====\n");
        stringBuilder.append("; exit(0)\n\n");
        stringBuilder.append("    mov ebx, 0\n");
        stringBuilder.append("    mov eax, 1\n");
        stringBuilder.append("    int 0x80\n");

        //Escribir fichero
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(FILENAME + FILE_EXTENSION)))) {
            bufferedWriter.write(stringBuilder.toString());
        }
    }
}
