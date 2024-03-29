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

    private static final String FILENAME = "assembly";
    private static final String FILENAME_OPTIMIZED = "assembly_optim";
    private static final String FILE_EXTENSION = ".asm";
    private static final String STRING_MAX_SIZE = "512";
    private static final int VARIABLE_SIZE = 4;

    private HashMap<String, Variable> variableTable;
    private HashMap<String, Procedure> procedureTable;

    private String fileName;
    private boolean optimized;
    private boolean gotoMain = false;

    /**
     * Constructor sin parámetros
     */
    public AssemblyGenerator(boolean optimized) {
        this.optimized = optimized;

        fileName = optimized ? FILENAME_OPTIMIZED : FILENAME;

        this.variableTable = VariableTable.getTable();
        this.procedureTable = ProcedureTable.getTable();
    }

    public void toAssembly() throws IOException {
        ArrayList<ThreeAddressCode> threeAddressCodes = Generator.getThreeAddressCodes();

        StringBuilder stringBuilder = new StringBuilder();

        //TODO Mirar enlace: https://www.devdungeon.com/content/hello-world-nasm-assembler

        //Escribir cabecera del fichero
        stringBuilder.append("; ----------------------------------------------------\n");
        stringBuilder.append("; Código ensamblador ").append(optimized ? " optimizado " : "").append("en NASM para Linux 32 bits (i386)\n");
        stringBuilder.append(";\n");
        stringBuilder.append("; Requisitos en Ubuntu 18.04 y 20.04:\n");
        stringBuilder.append(";   sudo apt install build-essential gcc-multilib nasm\n");
        stringBuilder.append(";\n");
        stringBuilder.append("; Comando para compilar:\n");
        stringBuilder.append(";   nasm -f elf " + fileName + ".asm && gcc -m32 " + fileName + ".o -o " + fileName + " && rm " + fileName + ".o\n");
        stringBuilder.append(";\n");
        stringBuilder.append("; Comando para ejecutar:\n");
        stringBuilder.append(";   ./").append(fileName).append("\n");
        stringBuilder.append("; ----------------------------------------------------\n");

        //Etiqueta de inicio
        stringBuilder.append("global main\n\n");

        stringBuilder.append("extern printf, scanf\n\n");

        stringBuilder.append("; Sección de memoria para las variables no inicializadas\n");
        stringBuilder.append("section .bss\n");

        //Declarar todas las variables de tipo String sin inicializar
        variableTable.forEach((id, variable) -> {
            if (variable.getSubtype() == Subtype.STRING && variable.getValue() == null)
                stringBuilder.append("    ").append(id).append(" resb " + STRING_MAX_SIZE + "\n");
        });

        stringBuilder.append("\n; Sección de memoria para las variables inicializadas\n");
        stringBuilder.append("section .data\n");

        //Declarar todas las variables excepto las de tipo String sin inicializar
        variableTable.forEach((id, variable) -> {

            //Strings sin inicializar, se ignora. Van en sección .bss
            if (variable.getSubtype() == Subtype.STRING && variable.getValue() == null)
                return;

            if (variable.getSubtype() == Subtype.STRING) {
                stringBuilder.append("    ").append(id).append(" db ").append(variable.getValue()).append(", 10, 0\n");
            } else {
                //Si no es String, inicializar en 0
                stringBuilder.append("    ").append(id).append(" dd 0").append("\n");
            }
        });
        stringBuilder.append("\n    errorByOverflow db \"KOTLITE - ERROR DE EJECUCION: Operacion con Overflow\", 10, 0\n");
        stringBuilder.append("    errorByZero db \"KOTLITE - ERROR DE EJECUCION: Operacion Division por Zero\", 10, 0\n");
        stringBuilder.append("    errorByCarry db \"KOTLITE - ERROR DE EJECUCION: Operacion con Carry\", 10, 0\n");
        stringBuilder.append("\n    inputFormat db \"%d\", 0\n");

        //Código
        stringBuilder.append("\nsection .text\n");

        //Control de errores Overflow
        stringBuilder.append("\n    ; ===== ===== ===== ===== =====\n");
        stringBuilder.append("    ; Control de Error: Overflow\n");

        stringBuilder.append("handlerErrorByOverflow: nop\n");
        stringBuilder.append("    cmp bl, 0\n");
        stringBuilder.append("    je checkedOverflow\n");

        stringBuilder.append("\n    mov eax, errorByOverflow\n");
        stringBuilder.append("    push eax\n");
        stringBuilder.append("    call printf\n");
        stringBuilder.append("    pop eax\n");

        stringBuilder.append("\n    mov ebx, 1\n");
        stringBuilder.append("    mov eax, 1\n");
        stringBuilder.append("    int 0x80\n");
        stringBuilder.append("    mov eax, 1\n");

        stringBuilder.append("\ncheckedOverflow: nop\n");
        stringBuilder.append("    ret\n");

        //Control de errores Carry
        stringBuilder.append("\n    ; ===== ===== ===== ===== =====\n");
        stringBuilder.append("    ; Control de Error: Carry\n");

        stringBuilder.append("handlerErrorByCarry: nop\n");
        stringBuilder.append("    cmp bl, 0\n");
        stringBuilder.append("    je checkedCarry\n");

        stringBuilder.append("\n    mov eax, errorByCarry\n");
        stringBuilder.append("    push eax\n");
        stringBuilder.append("    call printf\n");
        stringBuilder.append("    pop eax\n");

        stringBuilder.append("\n    mov ebx, 1\n");
        stringBuilder.append("    mov eax, 1\n");
        stringBuilder.append("    int 0x80\n");
        stringBuilder.append("    mov eax, 1\n");

        stringBuilder.append("\ncheckedCarry: nop\n");
        stringBuilder.append("    ret\n");

        //Control de errores Division By Zero
        stringBuilder.append("\n    ; ===== ===== ===== ===== =====\n");
        stringBuilder.append("    ; Control de Error: Division por Zero\n");

        stringBuilder.append("handlerErrorByZero: nop\n");
        stringBuilder.append("    cmp eax, 0\n");
        stringBuilder.append("    jne checkedByZero\n");

        stringBuilder.append("\n    mov eax, errorByZero\n");
        stringBuilder.append("    push eax\n");
        stringBuilder.append("    call printf\n");
        stringBuilder.append("    pop eax\n");

        stringBuilder.append("\n    mov ebx, 1\n");
        stringBuilder.append("    mov eax, 1\n");
        stringBuilder.append("    int 0x80\n");
        stringBuilder.append("    mov eax, 1\n");

        stringBuilder.append("\ncheckedByZero: nop\n");
        stringBuilder.append("    ret\n");

        stringBuilder.append("\n; ===== ===== ===== ===== =====\n");
        stringBuilder.append("; Inicio del programa\n");
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(fileName + FILE_EXTENSION)))) {
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

        Procedure procedure;

        switch (operation) {
            case "SKIP":
                //Antes del skip de la primera función hay que hacer un jump a nuestro función inicial
                if (!gotoMain) {
                    stringBuilder.append("    jmp fun#main").append("\n");
                    gotoMain = true;
                }

                stringBuilder.append(destination).append(": nop\n");
                break;
            case "PMB":

                //Ignorar PMB de main
                if (destination.equals("fun#main")) {
                    stringBuilder.append("    ; Ignorar preambulo de main").append("\n");
                    break;
                }

                // Guardar registros en la pila
                stringBuilder.append("    push ebp\n");
                stringBuilder.append("    mov ebp, esp\n");
                stringBuilder.append("    push eax\n");
                stringBuilder.append("    push ebx\n");

                procedure = procedureTable.get(destination.split("#")[1]);

                int numParams = procedure.getNumParams();
                int desplazamiento = VARIABLE_SIZE + (numParams * VARIABLE_SIZE);

                if (procedure.getSubtype() != Subtype.NONE)
                    desplazamiento += 4;

                //Guardar parámetros en la pila con el desplazamiento adecuado (4 + 4 * numParam)
                for (int i = 0; i < numParams; i++) {
                    stringBuilder.append("    mov eax, [ebp+").append(desplazamiento - (i * VARIABLE_SIZE)).append("]\n");
                    stringBuilder.append("    mov [").append(procedure.getParams().get(i).getId()).append("], eax\n");
                }
                break;

            case "PARAM":
                //Si es una variable y no es string
                if (destination.contains("$") && variableTable.get(destination).getSubtype() != Subtype.STRING)
                    stringBuilder.append("    mov eax, [").append(destination).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(destination).append("\n");
                stringBuilder.append("    push eax\n");
                break;

            case "CALL":
                procedure = procedureTable.get(operand1.split("#")[1]);

                int numArgs = procedure.getNumParams();

                if (procedure.getSubtype() != Subtype.NONE)
                    // Reservamos 4 bytes para el resultado que devolvemos
                    stringBuilder.append("    sub esp, " + VARIABLE_SIZE + "\n");

                stringBuilder.append("    call ").append(operand1).append("\n");

                if (procedure.getSubtype() != Subtype.NONE) {
                    // Recuperamos el resultado devuelto sobre el registro eax
                    stringBuilder.append("    pop eax\n");
                    // Copiamos el resultado al destino
                    stringBuilder.append("    mov [").append(destination).append("], eax\n");
                }

                // Recuperamos el espacio reservado para los parametros
                stringBuilder.append("    add esp, ").append(VARIABLE_SIZE * numArgs).append("\n");
                break;

            case "RTN":
                procedure = procedureTable.get(operand1);

                int desplazamientoRtn = VARIABLE_SIZE;
                if (procedure.getSubtype() != Subtype.NONE)
                    desplazamientoRtn += 4;

                //Si tiene retorno hay que guardarlo en la pila
                if (procedure.getSubtype() != Subtype.NONE) {
                    if (destination.contains("$"))
                        stringBuilder.append("    mov eax, [").append(destination).append("]\n");
                    else
                        stringBuilder.append("    mov eax, ").append(destination).append("\n");
                    stringBuilder.append("    mov [ebp+").append(desplazamientoRtn).append("], eax\n");
                }

                // Recuperar los registros guardados en la pila
                stringBuilder.append("    pop ebx\n");
                stringBuilder.append("    pop eax\n");
                stringBuilder.append("    pop ebp\n");

                //Return
                stringBuilder.append("    ret\n");
                break;

            case "IFGOTO":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");
                stringBuilder.append("    mov ebx, 1\n");
                stringBuilder.append("    cmp eax, ebx\n");
                stringBuilder.append("    jne ").append(destination).append("\n");
                break;

            case "GOTO":
                stringBuilder.append("    jmp ").append(destination).append("\n");
                break;

            case "COPY_LITERAL":
                //Si es un String literal se ignora, porque ya se inicializa en memoria
                if (operand1.startsWith("\"")) {
                    stringBuilder.append("    ; Copy de String literal se ignora, ya está en memoria").append("\n");
                    break;
                }

                //Comprobar boolean, si es true 1, false 0
                if (operand1.equals("true"))
                    operand1 = "1";
                else if (operand1.equals("false"))
                    operand1 = "0";

                if (operand1.equals("null"))
                    operand1 = "0";

                //Comprobar literales numéricos dentro del margen de valores máximos y mínimos permitidos
                if (Long.parseLong(operand1) > Integer.MAX_VALUE) {
                    tAC.setOperand1(String.valueOf(Integer.MAX_VALUE));
                    operand1 = String.valueOf(Integer.MAX_VALUE);
                } else if (Long.parseLong(operand1) < Integer.MIN_VALUE) {
                    tAC.setOperand1(String.valueOf(Integer.MIN_VALUE));
                    operand1 = String.valueOf(Integer.MIN_VALUE);
                }

                stringBuilder.append("    mov eax, ").append(operand1).append("\n");
                stringBuilder.append("    mov [").append(destination).append("], eax\n");
                break;

            case "COPY":
                //Tipo string en variable temporal no lleva corchetes
                if (variableTable.get(operand1).getSubtype() == Subtype.STRING && variableTable.get(operand1).getId().contains("$t#")) {
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");
                } else {
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                }
                stringBuilder.append("    mov [").append(destination).append("], eax\n");
                break;

            case "EQU":
                //Si contiene el símbolo $ es una variable, sino un literal
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    cmp eax, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    cmp eax, ").append(operand2).append("\n");

                stringBuilder.append("    sete [").append(destination).append("]\n");
                break;

            case "NOTEQU":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    cmp eax, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    cmp eax, ").append(operand2).append("\n");

                stringBuilder.append("    setne [").append(destination).append("]\n");
                break;

            case "LT":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    cmp eax, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    cmp eax, ").append(operand2).append("\n");

                stringBuilder.append("    setl [").append(destination).append("]\n");
                break;

            case "LTEQU":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    cmp eax, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    cmp eax, ").append(operand2).append("\n");

                stringBuilder.append("    setle [").append(destination).append("]\n");
                break;

            case "GT":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    cmp eax, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    cmp eax, ").append(operand2).append("\n");

                stringBuilder.append("    setg [").append(destination).append("]\n");
                break;

            case "GTEQU":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    cmp eax, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    cmp eax, ").append(operand2).append("\n");

                stringBuilder.append("    setge [").append(destination).append("]\n");
                break;

            case "NOT":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                stringBuilder.append("    test eax, eax\n");
                stringBuilder.append("    setz [").append(destination).append("]\n");
                break;

            case "OR":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    or eax, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    or eax, ").append(operand2).append("\n");

                stringBuilder.append("    setnz [").append(destination).append("]\n");
                break;

            case "AND":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    and eax, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    and eax, ").append(operand2).append("\n");

                stringBuilder.append("    setnz [").append(destination).append("]\n");
                break;

            case "PLUS":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    mov ebx, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    mov ebx, ").append(operand2).append("\n");

                stringBuilder.append("    add eax, ebx\n");

                //Comprobamos si la operacion resultante cabe en un registro de 32 bit
                stringBuilder.append("    seto bl\n");
                stringBuilder.append("    call handlerErrorByOverflow\n");

                stringBuilder.append("    mov [").append(destination).append("], eax\n");
                break;

            case "MINUS":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");


                if (operand2.contains("$"))
                    stringBuilder.append("    mov ebx, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    mov ebx, ").append(operand2).append("\n");

                stringBuilder.append("    sub eax, ebx\n");

                //Comprobamos si la operacion resultante cabe en un registro de 32 bit
                stringBuilder.append("    seto bl\n");
                stringBuilder.append("    call handlerErrorByOverflow\n");

                stringBuilder.append("    mov [").append(destination).append("], eax\n");
                break;

            case "MULTI":
                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    mov ebx, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    mov ebx, ").append(operand2).append("\n");

                stringBuilder.append("    imul ebx\n");

                //Comprobamos si la operacion resultante cabe en un registro de 32 bit
                stringBuilder.append("    setc bl\n");
                stringBuilder.append("    call handlerErrorByCarry\n");

                stringBuilder.append("    mov [").append(destination).append("], eax\n");
                break;

            case "DIV":
                //Verificamos si el denominador es un cero
                if (operand2.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand2).append("\n");

                stringBuilder.append("    call handlerErrorByZero\n");

                if (operand1.contains("$"))
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                else
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");

                stringBuilder.append("    cdq\n");

                if (operand2.contains("$"))
                    stringBuilder.append("    mov ebx, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    mov ebx, ").append(operand2).append("\n");

                stringBuilder.append("    idiv ebx\n");
                stringBuilder.append("    mov [").append(destination).append("], eax\n");
                break;

            case "GET":
                stringBuilder.append("    push ").append(destination).append("\n");
                stringBuilder.append("    push inputFormat\n");
                stringBuilder.append("    call scanf\n");
                stringBuilder.append("    add esp, 8\n");
                break;

            case "PRINT":
                //Si es una variable inicializada, pasar valor, sino referencia

                if (variableTable.get(operand1) != null && variableTable.get(operand1).getValue() != null)
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");
                else
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                stringBuilder.append("    push eax\n");
                stringBuilder.append("    call printf\n");
                stringBuilder.append("    pop eax\n");
                break;

            case "PRINTBOOL":
            case "PRINTINT":
                //Numero literal no lleva llaves
                if (operand2.contains("$"))
                    stringBuilder.append("    mov ebx, [").append(operand2).append("]\n");
                else
                    stringBuilder.append("    mov ebx, ").append(operand2).append("\n");

                stringBuilder.append("    push ebx\n");
                if (variableTable.get(operand1) != null && variableTable.get(operand1).getValue() != null)
                    stringBuilder.append("    mov eax, ").append(operand1).append("\n");
                else
                    stringBuilder.append("    mov eax, [").append(operand1).append("]\n");
                stringBuilder.append("    push eax\n");
                stringBuilder.append("    call printf\n");
                stringBuilder.append("    pop eax\n");
                stringBuilder.append("    pop ebx\n");
                break;
        }

    }
}
