package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.ParserSym;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolTerm extends SymbolBase {

    private SymbolTerm term;
    private SymbolMult mult;
    private SymbolUnary unary;

    private Variable variable;

    private Subtype subtype;

    // Term ::= Term Mult Unary
    public SymbolTerm(SymbolTerm term, SymbolMult mult, SymbolUnary unary, int line, int column) {
        super("Term", 0);

        this.term = term;
        this.mult = mult;
        this.unary = unary;

        if (term.getSubtype() != Subtype.INT || unary.getSubtype() != Subtype.INT)
            Output.writeError("Error semántico en posición " + line + ":" + column + " - La operacion " +
                    ParserSym.terminalNames[mult.getSymbol()] + " requiere operandos del tipo INT y se ha encontrado " +
                    term.getSubtype() + " y " + unary.getSubtype());

        //Resultado de multiplicacion es INT
        this.subtype = Subtype.INT;

        Variable termVar = term.getVariable();
        Variable unaryVar = unary.getVariable();

        //Generar nueva variable temporal
        variable = new Variable(generator.generateVariable());

        //Añadir código de tres direcciones con la operacion
        generator.addThreeAddressCode(ParserSym.terminalNames[mult.getSymbol()], termVar.getId(), unaryVar.getId(), variable.getId());
    }

    // Term ::= Unary
    public SymbolTerm(SymbolUnary unary) {
        super("Term", 0);

        this.unary = unary;
        this.subtype = unary.getSubtype();

        variable = unary.getVariable();
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

        if (term != null)
            out.print(index + "->" + term.getIndex() + "\n");
        if (mult != null)
            out.print(index + "->" + mult.getIndex() + "\n");
        out.print(index + "->" + unary.getIndex() + "\n");

        if (term != null)
            term.toDot(out);
        if (mult != null)
            mult.toDot(out);
        unary.toDot(out);
    }

}
