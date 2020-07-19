package compiler.syntax.symbols;

import compiler.output.Output;
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
        generator.pushIdLabel(id);
    }

    public Variable getVariable() {
        Variable variable = variableTable.get(generator.peekFunctionLabel() + "$" + id);

        if (variable == null)
            variable = variableTable.get("global$" + id);

        if (variable == null)
            Output.writeError("Error semántico - No se ha encontrado la variable " + id + " en esta ámbito");

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
