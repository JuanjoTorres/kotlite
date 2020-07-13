package compiler.syntax.symbols;

import compiler.intermediate.ThreeAddressCode;
import compiler.output.Output;
import compiler.syntax.ParserSym;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolRelation extends SymbolBase {

    private SymbolExpr expr1;
    private SymbolOprel oprel;
    private SymbolExpr expr2;

    private Subtype subtype;

    private Variable variable;

    // [FORMA] Relation ::= Expr Oprel Expr
    public SymbolRelation(SymbolExpr expr1, SymbolOprel oprel, SymbolExpr expr2, int line, int column) {
        super("Relation", 0);

        this.expr1 = expr1;
        this.oprel = oprel;
        this.expr2 = expr2;

        if (expr1.getSubtype() != expr2.getSubtype())
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El operador relacional " + ParserSym.terminalNames[oprel.getRelationType()] +
                    " requiere operandos dos operandos del mismo tipo, y se ha encontrado " + expr1.getSubtype() + " y " + expr2.getSubtype());

        switch (oprel.getRelationType()) {
            case ParserSym.LESS:
            case ParserSym.LESSEQU:
            case ParserSym.GREATEREQU:
            case ParserSym.GREATER:
                if (expr1.getSubtype() != Subtype.INT || expr2.getSubtype() != Subtype.INT)
                    Output.writeError("Error semántico en posición " + line + ":" + column + " - El operador relacional " + ParserSym.terminalNames[oprel.getRelationType()] +
                            " requiere operandos del tipo INT, y se ha encontrado " + expr1.getSubtype() + " y " + expr2.getSubtype());
        }

        this.subtype = Subtype.BOOLEAN;

        Variable expr1Var = expr1.getVariable();
        Variable expr2Var = expr2.getVariable();

        //Generar nueva variable temporal
        variable = new Variable(generator.generateVariable());

        //Añadir código de tres direcciones con la operacion
        generator.addThreeAddressCode(new ThreeAddressCode(ParserSym.terminalNames[oprel.getRelationType()], expr1Var.getId(), expr2Var.getId(), variable.getId()));
    }

    // [FORMA] Relation ::= Expr
    public SymbolRelation(SymbolExpr expr1) {
        super("Relation", 0);

        this.expr1 = expr1;
        subtype = expr1.getSubtype();

        variable = expr1.getVariable();
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

        out.print(index + "->" + expr1.getIndex() + "\n");
        if (oprel != null)
            out.print(index + "->" + oprel.getIndex() + "\n");
        if (expr2 != null)
            out.print(index + "->" + expr2.getIndex() + "\n");

        expr1.toDot(out);
        if (oprel != null)
            oprel.toDot(out);
        if (expr2 != null)
            expr2.toDot(out);
    }

}
