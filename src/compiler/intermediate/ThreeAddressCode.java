package compiler.intermediate;

public class ThreeAddressCode {

    private String operation;
    private String operand1;
    private String operand2;
    private String destination;

    public ThreeAddressCode(String operation, String operand1, String operand2, String destination) {
        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.destination = destination;
    }

    public String getOperation() {
        return operation;
    }

    public String getOperand1() {
        return operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return operation + " | " + operand1 + " | " + operand2 + " | " + destination;
    }

}