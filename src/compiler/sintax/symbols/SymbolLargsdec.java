/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.sintax.symbols;

import java.io.PrintWriter;

public class SymbolLargsdec extends SymbolBase {

    private SymbolLargsdec largsdec;
    private SymbolId id;
    private SymbolBasic basic;

    // [FORMA] Largsdec ::= Largsdec SEMICOLON Id COLON Basic
    public SymbolLargsdec(SymbolLargsdec largsdec, SymbolId id, SymbolBasic basic) {

        super("Largsdec", 0);
        this.largsdec = largsdec;
        this.id = id;
        this.basic = basic;
    }

    // [FORMA] Largsdec ::= Largsdec SEMICOLON Id COLON Basic
    public SymbolLargsdec(SymbolId id, SymbolBasic basic) {

        super("Largsdec",0);
        this.id = id;
        this.basic = basic;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
