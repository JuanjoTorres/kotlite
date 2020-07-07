package compiler.syntax.symbols;

import compiler.codegeneration.ThreeAddressCode;
import compiler.output.Output;
import compiler.syntax.ParserSym;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolExpr extends SymbolBase {

    private SymbolExpr expr;
    private SymbolAdd add;
    private SymbolTerm term;

    private Subtype subtype;
    private String variable;

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

        //TODO de donde se sacan las variables?
        String exprVar = expr.getVariable();
        String termVar = term.getVariable();

        //Generar nueva variable temporal
        variable = Variable.nextVariable();

        //TODO se pasa el id, el valor o que?
        ThreeAddressCode.genera(ParserSym.terminalNames[add.getSymbol()], exprVar, termVar, variable);

    }

    // [FORMA] Expr ::= Term
    public SymbolExpr(SymbolTerm term) {
        super("Expr", 0);

        this.term = term;
        this.subtype = term.getSubtype();

        //Generar nueva variable temporal
        variable = term.getVariable();
    }

    public String getVariable() {
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
        out.print(index + "->" + term.getIndex() + "\n");

        if (expr != null)
            expr.toDot(out);
        if (add != null)
            add.toDot(out);
        term.toDot(out);
    }

}
