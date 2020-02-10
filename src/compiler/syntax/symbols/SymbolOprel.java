/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolOprel extends SymbolBase {

    private int relationType;

    public SymbolOprel(int relationType) {
        super("Oprel", 0);

        this.relationType = relationType;
    }

    public int getRelationType() {
        return relationType;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");
    }

}
