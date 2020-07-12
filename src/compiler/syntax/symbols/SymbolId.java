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
    }

    public Variable getVariable() {
        System.out.println("Obtener variable de id: " + id);
        Variable var = variableTable.get(id);
        System.out.println("Sacando variable: " + id);
        System.out.println(" --> Variable: " + var.toString());
        return var;
    }

    public Procedure getProcedure() {
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
