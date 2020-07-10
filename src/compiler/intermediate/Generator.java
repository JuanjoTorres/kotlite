package compiler.intermediate;

import java.util.ArrayList;
import java.util.Stack;

public class Generator {

    private static int returnLabel = 0;
    private static int variableNumber = 0;

    private static Stack<String> functionStack = new Stack<>();

    private static ArrayList<ThreeAddressCode> threeAddressCodes = new ArrayList<>();

    public static void addThreeAddressCode(ThreeAddressCode threeAddressCode) {
        threeAddressCodes.add(threeAddressCode);
    }

    public static ArrayList<ThreeAddressCode> getThreeAddressCodes() {
        return threeAddressCodes;
    }

    public static String popFunctionLabel() {
        return functionStack.pop();
    }

    public static void pushFunctionLabel(String functionLabel) {
        functionStack.push(functionLabel);
    }

    public static String generateReturnLabel() {
        return "rnt_" + ++variableNumber;
    }

    public static String generateVariable() {
        return "t" + ++variableNumber;
    }
}