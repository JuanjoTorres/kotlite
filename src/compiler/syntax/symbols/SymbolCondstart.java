package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;

import java.io.PrintWriter;

public class SymbolCondstart extends SymbolBase {

    // Marcador de inicio de condicional IF
    public SymbolCondstart() {
        super("CondStart", 0);

        //Generar etiqueta de condición true y false
        String trueLabel = Generator.generateCondTrueLabel();
        String falseLabel = Generator.generateCondFalseLabel();

        //Guardar etiquetas en pilas
        Generator.pushCondTrueLabel(trueLabel);
        Generator.pushCondFalseLabel(falseLabel);
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
