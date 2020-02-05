/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.syntax.ParserSym;
import compiler.syntax.table.Subtype;

import java.io.PrintWriter;

public class SymbolBasic extends SymbolBase {

    private Subtype tipo;

    public SymbolBasic(int symbol) {

        super("Basic", 0);

        if (symbol == ParserSym.INT)
            tipo = Subtype.INT;
        else if (symbol == ParserSym.STRING)
            tipo = Subtype.STRING;
        else if (symbol == ParserSym.BOOLEAN)
            tipo = Subtype.BOOLEAN;
        else if (symbol == ParserSym.NONE)
            tipo = Subtype.NULL;
    }

    public Subtype getSubtype() {
        return tipo;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
