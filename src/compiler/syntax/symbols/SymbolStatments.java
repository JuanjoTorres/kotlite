/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolStatments extends SymbolBase {

    private SymbolStatments statments;
    private SymbolStatment statment;

    // Statments ::= .
    public SymbolStatments() {
        super("Statments", 0);
    }

    // Statments ::= Statments Statment
    public SymbolStatments(SymbolStatments statments, SymbolStatment statment) {
        super("Statments", 0);

        this.statments = statments;
        this.statment = statment;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label='" + name + "'];\n");

        if (statments != null) {
            out.print(index + "->" + statments.getIndex() + "\n");
            out.print(index + "->" + statment.getIndex() + "\n");

            statments.toDot(out);
            statment.toDot(out);
        }
    }

}
