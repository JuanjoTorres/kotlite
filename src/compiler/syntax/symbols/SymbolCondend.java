package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;

import java.io.PrintWriter;

public class SymbolCondend extends SymbolBase {

    // Marcador de fin de condicional IF / ELSEIF
    public SymbolCondend() {
        super("CondEnd", 0);

        //Añadir código de tres direcciones con etiqueta true
        Generator.addThreeAddressCode(new ThreeAddressCode("SKIP", "", "", Generator.popCondTrueLabel()));
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
