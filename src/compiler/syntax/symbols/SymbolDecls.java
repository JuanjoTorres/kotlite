/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolDecls extends SymbolBase {

    private SymbolDecls decls;
    private SymbolDecl decl;

    // FORMA Decls ::= .
    public SymbolDecls() {
        super("Decls", 0);
    }

    // FORMA Decls ::= Decls Decl
    public SymbolDecls(SymbolDecls decls, SymbolDecl decl) {
        super("Decls", 0);
        this.decls = decls;
        this.decl = decl;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label='" + name + "'];\n");

        if (decls != null) {
            out.print(index + "->" + decls.getIndex() + "\n");
            out.print(index + "->" + decl.getIndex() + "\n");
        }
    }

}
