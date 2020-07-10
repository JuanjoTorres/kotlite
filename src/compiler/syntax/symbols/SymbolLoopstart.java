package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;

import java.io.PrintWriter;

public class SymbolLoopstart extends SymbolBase {

    // FORMA Cond ::= .
    public SymbolLoopstart() {
        super("LoopStart", 0);

        //Generar etiqueta de inicio de loop
        String startLabel = Generator.generateLoopStartLabel();

        //Guardar etiqueta en la pila
        Generator.pushStartloopLabel(startLabel);

        //Añadir código de tres direcciones con skip y la etiqueta
        Generator.addThreeAddressCode(new ThreeAddressCode("SKIP", "", "", startLabel));
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
