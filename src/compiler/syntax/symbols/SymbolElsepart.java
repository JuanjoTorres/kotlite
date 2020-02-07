/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolElsepart extends SymbolBase {

    private SymbolDecls decls;
    private SymbolStatments statments;

    // [FORMA] Elsepart ::= .
    public SymbolElsepart() { super("Elsepart", 0); }

    // [FORMA] Elsepart ::= ELSE LBRACKET Decls Statments RBRACKET
    public SymbolElsepart(SymbolDecls decls, SymbolStatments statments) {
        super("Elsepart", 0);
        this.decls = decls;
        this.statments = statments;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}