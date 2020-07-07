package compiler.codegeneration;

import compiler.output.Output;

import java.util.Stack;

public class TAC {

    private static final Stack<String> FUNCTION_STACK = new Stack<>();

    //TODO Añadir propiedades op, var1, var y dest
    public TAC() {

    }

    /**
     * Escribir código de tres direcciones
     *
     * @param op
     * @param b
     * @param c
     * @param a
     */
    public static void genera(String op, String b, String c, String a) {
        Output.writeThreeAddressCode(op + " | " + b + " | " + c + " | " + a);
    }

    public static String popFunctionLabel() {
        return FUNCTION_STACK.pop();
    }

    public static void pushFunctionLabel(String functionLabel) {
        FUNCTION_STACK.push(functionLabel);
    }
}