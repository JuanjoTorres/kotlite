/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolFunctions extends SymbolBase {

    private SymbolFunctions functions;
    private SymbolFunction function;

    // [FORMA] Functions ::= .
    public SymbolFunctions() {
        super("Functions", 0);
    }

    // [FORMA] Functions ::= Functions Function
    public SymbolFunctions(SymbolFunctions functions, SymbolFunction function) {
        super("Functions", 0);

        this.functions = functions;
        this.function = function;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label='" + name + "'];\n");

        if (functions != null) {
            out.print(index + "->" + functions.getIndex() + "\n");
            out.print(index + "->" + function.getIndex() + "\n");

            functions.toDot(out);
            function.toDot(out);
        }
    }

}
