/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.sintax.symbols;

import java.io.PrintWriter;

public class SymbolProgram extends SymbolBase {

    private SymbolDecls decls;
    private SymbolFunctions functions;

    // [FORMA] Program ::= Decls Functions
    public SymbolProgram(SymbolDecls decls, SymbolFunctions functions) {

        super("Program", 0);
        this.decls = decls;
        this.functions = functions;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
