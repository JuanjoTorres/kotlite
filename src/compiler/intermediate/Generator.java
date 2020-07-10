package compiler.intermediate;

import java.util.ArrayList;
import java.util.Stack;

public class Generator {

    private static int variableNumber = 0;
    private static int loopStartNumber = 0;
    private static int loopEndNumber = 0;
    private static int condFalseNumber = 0;

    private static Stack<String> functionLabelStack = new Stack<>();

    private static Stack<String> condFalseStack = new Stack<>();
    private static Stack<String> endloopLabelStack = new Stack<>();
    private static Stack<String> startloopLabelStack = new Stack<>();

    private static String lastBoolVar;

    private static ArrayList<ThreeAddressCode> threeAddressCodes = new ArrayList<>();

    public static void addThreeAddressCode(ThreeAddressCode threeAddressCode) {
        threeAddressCodes.add(threeAddressCode);
    }

    public static ArrayList<ThreeAddressCode> getThreeAddressCodes() {
        return threeAddressCodes;
    }

    public static String popFunctionLabel() {
        return functionLabelStack.pop();
    }

    public static void pushFunctionLabel(String label) {
        functionLabelStack.push(label);
    }

    public static String popFalseLabel() {
        return condFalseStack.pop();
    }

    public static void pushFalseLabel(String label) {
        condFalseStack.push(label);
    }

    public static String popEndloopLabel() {
        return endloopLabelStack.pop();
    }

    public static void pushEndloopLabel(String label) {
        endloopLabelStack.push(label);
    }

    public static String popStartloopLabel() {
        return startloopLabelStack.pop();
    }

    public static void pushStartloopLabel(String label) {
        startloopLabelStack.push(label);
    }

    public static String generateLoopStartLabel() {
        return "loop_start_" + ++loopStartNumber;
    }

    public static String generateLoopEndLabel() {
        return "loop_end_" + ++loopEndNumber;
    }

    public static String generateCondFalseLabel() {
        return "cond_false_" + ++condFalseNumber;
    }

    public static String generateVariable() {
        return "t" + ++variableNumber;
    }

    public static String getLastBoolVar() {
        System.out.println("GET LAST BOOL: " + lastBoolVar);
        return lastBoolVar;
    }

    public static void setLastBoolVar(String lastBoolVar) {
        System.out.println("SET LAST BOOL: " + lastBoolVar);
        Generator.lastBoolVar = lastBoolVar;
    }
}