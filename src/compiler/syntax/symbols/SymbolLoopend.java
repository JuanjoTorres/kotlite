package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;

import java.io.PrintWriter;

public class SymbolLoopend extends SymbolBase {

    // Marcador de fin de loop WHILE
    public SymbolLoopend() {
        super("LoopEnd", 0);

        //Añadir código de tres direcciones con goto a inicio de bucle y skip de final de bucle
        Generator.addThreeAddressCode(new ThreeAddressCode("GOTO", "", "", Generator.popStartloopLabel()));
        Generator.addThreeAddressCode(new ThreeAddressCode("SKIP", "", "", Generator.popEndloopLabel()));
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
