/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.sintax.symbols;

import java.io.PrintWriter;

public class SymbolFunctioncall extends SymbolBase {

    private SymbolId id;
    private SymbolArgs args;

    // [FORMA] Functioncall ::= Id LPAREN Args RPAREN
    public SymbolFunctioncall(SymbolId id, SymbolArgs args) {

        super("Functioncall", 0);
        this.id = id;
        this.args = args;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
