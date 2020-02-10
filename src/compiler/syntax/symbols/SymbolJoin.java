/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.syntax.ParserSym;

import java.io.PrintWriter;

public class SymbolJoin extends SymbolBase {

    private int symbol;

    public SymbolJoin(int symbol) {
        super("Join", 0);

        this.symbol = symbol;
    }

    @Override
    public void toDot(PrintWriter out) {
        if (symbol == ParserSym.AND)
            out.print(index + " [label=\"" + name + " AND\"];\n");
        else
            out.print(index + " [label=\"" + name + " OR\"];\n");
    }

}
