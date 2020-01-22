/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.sintax.symbols;

import java.io.PrintWriter;

public class SymbolDecl extends SymbolBase {

    private SymbolType type;
    private SymbolId id;
    private SymbolBasic basic;

    // FORMA Decl ::= Type Id COLON Basic SEMICOLON
    public SymbolDecl(SymbolType type, SymbolId id, SymbolBasic basic) {

        super("Decl", 0);
        this.type = type;
        this.id = id;
        this.basic = basic;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
