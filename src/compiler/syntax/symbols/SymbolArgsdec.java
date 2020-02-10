/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.syntax.table.Symbol;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SymbolArgsdec extends SymbolBase {

    private SymbolLargsdec largsdec;

    // FORMA Argsdec ::= .
    public SymbolArgsdec() {
        super("Argsdec", 0);
    }

    // FORMA Argsdec ::= Largsdec
    public SymbolArgsdec(SymbolLargsdec largsdec) {
        super("Argsdec", 0);
        this.largsdec = largsdec;
    }

    public ArrayList<Symbol> getArgs() {
        return largsdec.getArgs();
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (largsdec != null) {
            out.print(index + "->" + largsdec.getIndex() + "\n");
            largsdec.toDot(out);
        }
    }

}
