package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;

import java.io.PrintWriter;

public class SymbolCondelse extends SymbolBase {

    // Marcador de else en medio de un condicional IF/ELSEIF
    public SymbolCondelse() {
        super("CondElse", 0);

        //Obtener etiqueta true para hacer el GOTO
        String trueLabel = Generator.popCondTrueLabel();

        //Añadir código de tres direcciones con goto a final de condicional para saltar el elsepart
        Generator.addThreeAddressCode(new ThreeAddressCode("GOTO", "", "", trueLabel));

        //Volver a guardar la etiqueta true para hacer el SKIP en Condend
        Generator.pushCondTrueLabel(trueLabel);

        //Añadir código de tres direcciones con etiqueta a elsepart
        Generator.addThreeAddressCode(new ThreeAddressCode("SKIP", "", "", Generator.popCondFalseLabel()));
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
