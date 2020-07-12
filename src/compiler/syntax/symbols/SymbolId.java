package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.syntax.tables.Procedure;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolId extends SymbolBase {

    private String id;

    public SymbolId(String id) {
        super(id, 0);

        this.id = id;

        //Hacer push en la pila de etiquetas de funciones
        Generator.pushFunctionLabel(id);

        System.out.println("Push id: " + id);
    }

    public Variable getVariable() {
        return variableTable.get(id);
    }

    public Procedure getProcedure() {
        System.out.println("Get procedure:" + id);
        return procedureTable.get(id);
    }

    public Subtype getSubtype() {
        return symbolTable.getId(id).getSubtype();
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\" Id: " + name + "\"];\n");
    }

}
