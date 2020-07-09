package compiler.syntax.symbols;

import compiler.syntax.tables.ProcedureTable;
import compiler.syntax.tables.SymbolTable;
import compiler.syntax.tables.VariableTable;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

import java.io.PrintWriter;

public abstract class SymbolBase extends ComplexSymbol {

    private static int idAutoIncrement = 0;

    public static SymbolTable symbolTable = new SymbolTable();
    public static VariableTable variableTable = new VariableTable();
    public static ProcedureTable procedureTable = new ProcedureTable();

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
