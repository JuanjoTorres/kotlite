/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.syntax.table.Symbol;
import compiler.syntax.table.Type;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SymbolLargsdec extends SymbolBase {

    private SymbolLargsdec largsdec;
    private SymbolId id;
    private SymbolBasic basic;

    private ArrayList<Symbol> args = new ArrayList<>();

    // [FORMA] Largsdec ::= Largsdec SEMICOLON Id COLON Basic
    public SymbolLargsdec(SymbolLargsdec largsdec, SymbolId id, SymbolBasic basic) {
        super("Largsdec", 0);

        this.largsdec = largsdec;
        this.id = id;
        this.basic = basic;

        //Añadir argumentos de lista de argumentos y el argumento actual
        args.addAll(largsdec.getArgs());
        args.add(new Symbol(id.getName(), Type.ARG, basic.getSubtype()));
    }

    // [FORMA] Largsdec ::= Largsdec SEMICOLON Id COLON Basic
    public SymbolLargsdec(SymbolId id, SymbolBasic basic) {
        super("Largsdec", 0);

        this.id = id;
        this.basic = basic;

        //Añadir el argumento actual
        args.add(new Symbol(id.getName(), Type.ARG, basic.getSubtype()));
    }

    public void setArgs(ArrayList<Symbol> args) {
        this.args = args;
    }

    public ArrayList<Symbol> getArgs() {
        return args;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label='" + name + "'];\n");

        if (largsdec != null) {
            out.print(index + "->" + largsdec.getIndex() + "\n");
            out.print(index + "->" + id.getIndex() + "\n");
            out.print(index + "->" + basic.getIndex() + "\n");

            largsdec.toDot(out);
            id.toDot(out);
            basic.toDot(out);
        } else {
            out.print(index + "->" + id.getIndex() + "\n");
            out.print(index + "->" + basic.getIndex() + "\n");

            id.toDot(out);
            basic.toDot(out);
        }
    }

}
