package compiler.syntax.symbols;

import compiler.intermediate.ThreeAddressCode;

import java.io.PrintWriter;

public class SymbolLoopend extends SymbolBase {

    // Marcador de fin de loop WHILE
    public SymbolLoopend() {
        super("LoopEnd", 0);

        //Añadir código de tres direcciones con goto a inicio de bucle y skip de final de bucle
        generator.addThreeAddressCode(new ThreeAddressCode("GOTO", "", "", generator.popStartloopLabel()));
        generator.addThreeAddressCode(new ThreeAddressCode("SKIP", "", "", generator.popEndloopLabel()));
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
