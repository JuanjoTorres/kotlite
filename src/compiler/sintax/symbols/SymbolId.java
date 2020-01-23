/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.sintax.symbols;

import java.io.PrintWriter;

public class SymbolId extends SymbolBase {

    // FALTA ACABAR
    public SymbolId(String id, int idleft) {
        super(id, 0);
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
