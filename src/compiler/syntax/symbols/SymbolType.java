/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.syntax.ParserSym;
import compiler.syntax.table.Type;

import java.io.PrintWriter;

public class SymbolType extends SymbolBase {

    private Type type;

    public SymbolType(int type) {

        super("Type", 0);

        if (type == ParserSym.VAR)
            this.type = Type.VAR;
        else if (type == ParserSym.VAL)
            this.type = Type.CONST;

    }

    public Type getType() {
        return type;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
