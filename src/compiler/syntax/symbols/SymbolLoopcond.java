package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;

import java.io.PrintWriter;

public class SymbolLoopcond extends SymbolBase {

    // FORMA Cond ::= .
    public SymbolLoopcond() {
        super("LoopEnd", 0);

        String endloopLabel = Generator.generateLoopEndLabel();

        Generator.addThreeAddressCode(new ThreeAddressCode("IFGOTO", Generator.getLastBoolVar(), "", endloopLabel));

        Generator.pushEndloopLabel(endloopLabel);
    }

    @Override
    public void toDot(PrintWriter out) {
    }
}
