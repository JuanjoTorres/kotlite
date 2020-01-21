/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.sintax.symbols;

import java.io.PrintWriter;

public class SymbolFunctions extends SymbolBase {

    public SymbolFunctions() {
        super("Functions", 0);
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
