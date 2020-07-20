package compiler.intermediate;

import java.util.ArrayList;
import java.util.Stack;

public class Generator {

    private static int variableNumber = 0;
    private static int loopStartNumber = 0;
    private static int loopEndNumber = 0;
    private static int condTrueNumber = 0;
    private static int condFalseNumber = 0;

    private Stack<String> idLabelStack = new Stack<>();
    private Stack<String> functionLabelStack = new Stack<>();

    private Stack<String> condFalseStack = new Stack<>();
    private Stack<String> condTrueStack = new Stack<>();

    private Stack<String> endloopLabelStack = new Stack<>();
    private Stack<String> startloopLabelStack = new Stack<>();

    private static ArrayList<ThreeAddressCode> threeAddressCodes;

    public void addThreeAddressCode(String operation, String op1, String op2, String destination) {
        threeAddressCodes.add(new ThreeAddressCode(operation, op1, op2, destination));
    }

    public static ArrayList<ThreeAddressCode> getThreeAddressCodes() {
        return threeAddressCodes;
    }

    public String peekFunctionLabel() {
        if (functionLabelStack.isEmpty())
            return null;
        return functionLabelStack.peek();
    }

    public String popFunctionLabel() {
        return functionLabelStack.pop();
    }

    public void pushFunctionLabel(String label) {
        functionLabelStack.push(label);
    }

    public String popIdLabel() {
        return idLabelStack.pop();
    }

    public void pushIdLabel(String label) {
        idLabelStack.push(label);
    }

    public String popCondTrueLabel() {
        if (condTrueStack.empty())
            return null;
        return condTrueStack.pop();
    }

    public void pushCondTrueLabel(String label) {
        condTrueStack.push(label);
    }

    public String popCondFalseLabel() {
        if (condFalseStack.empty())
            return null;
        return condFalseStack.pop();
    }

    public void pushCondFalseLabel(String label) {
        condFalseStack.push(label);
    }

    public String popEndloopLabel() {
        if (endloopLabelStack.empty())
            return null;
        return endloopLabelStack.pop();
    }

    public void pushEndloopLabel(String label) {
        endloopLabelStack.push(label);
    }

    public String popStartloopLabel() {
        if (startloopLabelStack.empty())
            return null;
        return startloopLabelStack.pop();
    }

    public void pushStartloopLabel(String label) {
        startloopLabelStack.push(label);
    }

    public String generateLoopStartLabel() {
        return "loop_start_" + ++loopStartNumber;
    }

    public String generateLoopEndLabel() {
        return "loop_end_" + ++loopEndNumber;
    }

    public String generateCondTrueLabel() {
        return "cond_true_" + ++condTrueNumber;
    }

    public String generateCondFalseLabel() {
        return "cond_false_" + ++condFalseNumber;
    }

    public String generateVariable() {
        return "t#" + ++variableNumber;
    }

    public static void initThreeAddressCode() {
        threeAddressCodes = new ArrayList<>();
    }
}