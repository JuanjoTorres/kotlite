package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;
import compiler.output.Output;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolUnary extends SymbolBase {

    private SymbolUnary unary;
    private SymbolFactor factor;

    private Subtype subtype;

    private Variable variable;

    // [FORMA] Unary ::= NOT Unary
    public SymbolUnary(SymbolUnary unary, int line, int column) {
        super("Unary", 0);

        this.subtype = unary.getSubtype();
        if (subtype != Subtype.BOOLEAN)
            Output.writeError("Error semántico en posición " + line + ":" + column +
                    " - El operador ! (NOT) espera un tipo subyacente BOOLEAN y se ha encontrado un tipo " + subtype);

        this.unary = unary;

        Variable unaryVar = unary.getVariable();

        //Generar nueva variable temporal
        variable = new Variable();

        //Añadir código de tres direcciones con la operacion
        Generator.addThreeAddressCode(new ThreeAddressCode("NOT", unaryVar.getId(), "", variable.getId()));
    }

    // [FORMA] Unary ::= Factor
    public SymbolUnary(SymbolFactor factor) {
        super("Unary", 0);

        this.factor = factor;
        this.subtype = factor.getSubtype();

        //Generar nueva variable temporal
        variable = factor.getVariable();
    }

    public Variable getVariable() {
        return variable;
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (unary != null) {
            out.print(index + "->" + unary.getIndex() + "\n");
            unary.toDot(out);
        } else if (factor != null) {
            out.print(index + "->" + factor.getIndex() + "\n");
            factor.toDot(out);
        }
    }

}
