/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolLargs extends SymbolBase {

    private SymbolLargs largs;
    private SymbolFactor factor;

    // [FORMA] Largs ::= Largs COMMA Factor
    public SymbolLargs(SymbolLargs largs, SymbolFactor factor) {

        super("Largs", 0);
        this.largs = largs;
        this.factor = factor;
    }

    // [FORMA] Largs ::= Factor
    public SymbolLargs(SymbolFactor factor) {
        super("Largs",0);
        this.factor = factor;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
