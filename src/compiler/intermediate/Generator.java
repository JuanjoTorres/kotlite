package compiler.intermediate;

import java.util.ArrayList;
import java.util.Stack;

public class Generator {

    private static final Stack<String> FUNCTION_STACK = new Stack<>();

    private static ArrayList<ThreeAddressCode> threeAddressCodes = new ArrayList<>();

    public static void addThreeAddressCode(ThreeAddressCode threeAddressCode) {
        threeAddressCodes.add(threeAddressCode);
    }

    public static ArrayList<ThreeAddressCode> getThreeAddressCodes() {
        return threeAddressCodes;
    }

    public static String popFunctionLabel() {
        return FUNCTION_STACK.pop();
    }

    public static void pushFunctionLabel(String functionLabel) {
        FUNCTION_STACK.push(functionLabel);
    }
}