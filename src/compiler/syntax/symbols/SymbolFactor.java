/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolFactor extends SymbolBase {

    private SymbolBool bool;
    private SymbolId id;
    private int SYMBOL;

    // [FORMA] Factor ::= LPAREN Bool RPAREN
    public SymbolFactor(SymbolBool bool) {

        super("Break", 0);
        this.bool = bool;
    }

    // [FORMA] Factor ::= Id
    public SymbolFactor(int symbol, SymbolId id) {

        super("Break", 0);
        this.SYMBOL = symbol;
        this.id = id;
    }

    public SymbolFactor(int symbol) {
        super("Break", 0);
        this.SYMBOL = symbol;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
