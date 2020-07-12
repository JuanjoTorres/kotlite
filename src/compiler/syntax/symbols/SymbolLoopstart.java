package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;

import java.io.PrintWriter;

public class SymbolLoopstart extends SymbolBase {

    // Marcador de inicio de loop WHILE
    public SymbolLoopstart() {
        super("LoopStart", 0);

        //Generar etiquetas de inicio y fin de loop
        String startLabel = Generator.generateLoopStartLabel();
        String endLabel = Generator.generateLoopEndLabel();

        //Guardar etiquetas en la pila
        Generator.pushStartloopLabel(startLabel);
        Generator.pushEndloopLabel(endLabel);

        //Añadir código de tres direcciones con skip y la etiqueta
        Generator.addThreeAddressCode(new ThreeAddressCode("SKIP", "", "", startLabel));
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
