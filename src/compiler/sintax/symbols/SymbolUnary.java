/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.sintax.symbols;

import java.io.PrintWriter;

public class SymbolUnary extends SymbolBase {

    private SymbolUnary unary;
    private SymbolFactor factor;

    // [FORMA] Unary ::= NOT Unary
    public SymbolUnary(SymbolUnary unary) {

        super("Unary", 0);
        this.unary = unary;
    }

    // [FORMA] Unary ::= Factor
    public SymbolUnary(SymbolFactor factor) {

        super("Unary", 0);
        this.factor = factor;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
