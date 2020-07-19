package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.ParserSym;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Type;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolUnary extends SymbolBase {

    private SymbolUnary unary;
    private SymbolFactor factor;

    private Subtype subtype;

    private Variable variable;

    // [FORMA] Unary ::= NOT Unary
    public SymbolUnary(int symbol, SymbolUnary unary, int line, int column) {
        super("Unary", 0);

        this.subtype = unary.getSubtype();

        if (symbol == ParserSym.NOT && subtype != Subtype.BOOLEAN) {
                Output.writeError("Error semántico en posición " + line + ":" + column +
                        " - El operador ! (NOT) espera un tipo subyacente BOOLEAN y se ha encontrado un tipo " + subtype);
        }

        if (symbol == ParserSym.MINUS && subtype != Subtype.INT) {
            Output.writeError("Error semántico en posición " + line + ":" + column +
                    " - El operador - (MINUS) espera un tipo subyacente INT y se ha encontrado un tipo " + subtype);
        }

        this.unary = unary;

        Variable unaryVar = unary.getVariable();

        //Generar variable y meterla en la tabla de variables
        variable = new Variable(generator.generateVariable(), generator.peekFunctionLabel(), true);
        variable.setType(Type.VAR);
        variable.setSubtype(subtype);
        variableTable.put(variable.getId(), variable);

        //Añadir código de tres direcciones con la operacion
        generator.addThreeAddressCode(ParserSym.terminalNames[symbol], unaryVar.getId(), "", variable.getId());
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

        if (unary != null)
            out.print(index + "->" + unary.getIndex() + "\n");
        if (factor != null)
            out.print(index + "->" + factor.getIndex() + "\n");

        if (unary != null)
            unary.toDot(out);
        if (factor != null)
            factor.toDot(out);

    }

}
