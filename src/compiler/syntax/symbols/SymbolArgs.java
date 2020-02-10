/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolArgs extends SymbolBase {

    private SymbolLargs largs;

    // FORMA Args ::= .
    public SymbolArgs() {
        super("Args", 0);
    }

    // FORMA Args ::= Largs
    public SymbolArgs(SymbolLargs largs) {
        super("Args", 0);
        this.largs = largs;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (largs != null) {
            out.print(index + "->" + largs.getIndex() + "\n");
            largs.toDot(out);
        }
    }

}
