package compiler.codegeneration;

import compiler.output.Output;

public class ThreeAddressCode {

    private StringBuilder assignBuffer;

    public ThreeAddressCode() {
        assignBuffer = new StringBuilder();
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

    /**
     * Escribir operación
     *
     * @param operation
     * @param b
     * @param c
     * @param a
     */
    public void addAssignOperation(Operation operation, String b, String c, String a) {
        assignBuffer.append(operation).append(" | ").append(b).append(" | ").append(c).append(" | ").append(a).append(" | \n");
    }

    /**
     * Bajar nivel de ámbito
     *
     * @param operation
     */
    public void writeAssignOperations(Operation operation) {
        Output.writeThreeAddressCode(assignBuffer.toString());
        assignBuffer.setLength(0);
    }
}