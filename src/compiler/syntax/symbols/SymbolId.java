package compiler.syntax.symbols;

import compiler.syntax.tables.Procedure;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolId extends SymbolBase {

    private String id;

    public SymbolId(String id) {
        super(id, 0);

        this.id = id;

        System.out.println("ID: " + id);

        //Hacer push en la pila de etiquetas de funciones
        generator.pushIdLabel(id);
    }

    public Variable getVariable() {
        Variable variable = variableTable.get(generator.peekFunctionLabel() + "$" + id);


        System.out.println("Buscando " + generator.peekFunctionLabel() + "$" + id);

        if (variable == null) {
            System.out.println("El id buscado es NULO");
            System.out.println("Buscando " + "global$" + id);
            variable = variableTable.get("global$" + id);
        }

        if (variable == null) {
            System.out.println("El id global buscado es NULO");
        }

        return variable;
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
