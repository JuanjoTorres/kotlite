package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolCondelse extends SymbolBase {

    // Marcador de else en medio de un condicional IF/ELSEIF
    public SymbolCondelse() {
        super("CondElse", 0);

        //Obtener etiqueta true para hacer el GOTO
        String trueLabel = generator.popCondTrueLabel();

        //Añadir código de tres direcciones con goto a final de condicional para saltar el elsepart
        generator.addThreeAddressCode("GOTO", "", "", trueLabel);

        //Volver a guardar la etiqueta true para hacer el SKIP en Condend
        generator.pushCondTrueLabel(trueLabel);

        //Añadir código de tres direcciones con etiqueta a elsepart
        generator.addThreeAddressCode("SKIP", "", "", generator.popCondFalseLabel());
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
