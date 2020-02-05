/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import com.sun.codemodel.internal.JForEach;
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
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
