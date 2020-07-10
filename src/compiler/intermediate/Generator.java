package compiler.intermediate;

import java.util.ArrayList;
import java.util.Stack;

public class Generator {

    private static int variableNumber = 0;

    private static Stack<String> functionLabelStack = new Stack<>();

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

    public static void pushFunctionLabel(String functionLabel) {
        functionLabelStack.push(functionLabel);
    }

    public static String generateVariable() {
        return "t" + ++variableNumber;
    }
}