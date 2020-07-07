package compiler.syntax.symbols;

import compiler.codegeneration.TAC;
import compiler.syntax.tables.Subtype;

import java.io.PrintWriter;

public class SymbolId extends SymbolBase {

    private String id;

    public SymbolId(String id) {
        super(id, 0);

        this.id = id;

        //Hacer push en la pila de etiquetas de funciones
        TAC.pushFunctionLabel(id);
    }

    public String getVariable() {
        return symbolTable.getId(id).getId();
    }

    public Subtype getSubtype() {
        return symbolTable.getId(id).getSubtype();
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\" Id: " + name + "\"];\n");
    }

}
