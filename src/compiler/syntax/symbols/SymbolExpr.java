package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.ParserSym;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Type;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolExpr extends SymbolBase {

    private SymbolExpr expr;
    private SymbolAdd add;
    private SymbolTerm term;

    private Subtype subtype;
    private Variable variable;

    // [FORMA] Expr ::= Expr Add Term
    public SymbolExpr(SymbolExpr expr, SymbolAdd add, SymbolTerm term, int line, int column) {
        super("Expr", 0);

        this.expr = expr;
        this.add = add;
        this.term = term;

        if (expr.getSubtype() != Subtype.INT || term.getSubtype() != Subtype.INT)
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El operador " + ParserSym.terminalNames[add.getSymbol()] +
                    " requiere operandos del tipo INT, y se ha encontrado " + expr.getSubtype() + " y " + term.getSubtype());

        //Resultado de + o - es INT
        subtype = Subtype.INT;

        Variable exprVar = expr.getVariable();
        Variable termVar = term.getVariable();

        //Generar variable y meterla en la tabla de variables
        variable = new Variable(generator.generateVariable(), generator.peekFunctionLabel(), true);
        variable.setType(Type.VAR);
        variable.setSubtype(subtype);
        variableTable.put(variable.getId(), variable);

        //Añadir código de tres direcciones con la operacion
        generator.addThreeAddressCode(ParserSym.terminalNames[add.getSymbol()], exprVar.getId(), termVar.getId(), variable.getId());

    }

    // [FORMA] Expr ::= Term
    public SymbolExpr(SymbolTerm term) {
        super("Expr", 0);

        this.term = term;
        this.subtype = term.getSubtype();

        //Generar nueva variable temporal
        variable = term.getVariable();
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

        if (expr != null)
            out.print(index + "->" + expr.getIndex() + "\n");
        if (add != null)
            out.print(index + "->" + add.getIndex() + "\n");
        if (term != null)
            out.print(index + "->" + term.getIndex() + "\n");

        if (expr != null)
            expr.toDot(out);
        if (add != null)
            add.toDot(out);
        if (term != null)
            term.toDot(out);
    }

}
