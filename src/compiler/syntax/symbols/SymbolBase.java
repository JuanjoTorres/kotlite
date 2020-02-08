/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.syntax.table.SymbolTable;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

import java.io.PrintWriter;

public abstract class SymbolBase extends ComplexSymbol {
    private static int idAutoIncrement = 0;
    static SymbolTable symbolTable = new SymbolTable();
    protected int index;

    public SymbolBase(String name, Integer valor) {
        super(name, idAutoIncrement++, valor);
        index = idAutoIncrement;
    }

    public int getIndex() {
        return index;
    }

    public abstract void toDot(PrintWriter out);
}
