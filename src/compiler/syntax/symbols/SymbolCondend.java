package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolCondend extends SymbolBase {

    // Marcador de fin de condicional IF / ELSEIF
    public SymbolCondend() {
        super("CondEnd", 0);

        //Añadir código de tres direcciones con etiqueta true
        generator.addThreeAddressCode("SKIP", "", "", generator.popCondTrueLabel());
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
