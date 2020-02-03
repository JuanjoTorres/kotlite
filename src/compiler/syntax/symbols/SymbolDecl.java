/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolDecl extends SymbolBase {

    private SymbolType type;
    private SymbolId id;
    private SymbolBasic basic;
    private SymbolFactor factor;

    // FORMA Decl ::= Type Id COLON Basic SEMICOLON
    public SymbolDecl(SymbolType type, SymbolId id, SymbolBasic basic) {

        super("Decl", 0);
        this.type = type;
        this.id = id;
        this.basic = basic;
    }

    // FORMA Decl ::= Type Id COLON Basic ASSIGN Factor SEMICOLON
    public SymbolDecl(SymbolType type, SymbolId id, SymbolBasic basic, SymbolFactor factor) {

        super("Decl", 0);
        this.type = type;
        this.id = id;
        this.basic = basic;
        this.factor = factor;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
