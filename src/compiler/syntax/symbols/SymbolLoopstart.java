package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolLoopstart extends SymbolBase {

    // Marcador de inicio de loop WHILE
    public SymbolLoopstart() {
        super("LoopStart", 0);

        //Generar etiquetas de inicio y fin de loop
        String startLabel = generator.generateLoopStartLabel();
        String endLabel = generator.generateLoopEndLabel();

        //Guardar etiquetas en la pila
        generator.pushStartloopLabel(startLabel);
        generator.pushEndloopLabel(endLabel);

        //Añadir código de tres direcciones con skip y la etiqueta
        generator.addThreeAddressCode("SKIP", "", "", startLabel);
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
