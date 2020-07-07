package compiler.syntax.symbols;

import compiler.codegeneration.TAC;
import compiler.output.Output;
import compiler.syntax.ParserSym;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolFactor extends SymbolBase {

    private SymbolBool bool;
    private SymbolId id;

    private Subtype subtype;

    private String variable;

    // [FORMA] Factor ::= LPAREN Bool RPAREN
    public SymbolFactor(SymbolBool bool) {
        super("Factor", 0);

        this.bool = bool;
        subtype = bool.getSubtype();

        //Obtener variable
        variable = bool.getVariable();
    }

    // [FORMA] Factor ::= Id
    public SymbolFactor(SymbolId id, int line, int column) {
        super("Factor", 0);

        this.id = id;

        //Comprobar si existe el id
        if (symbolTable.getId(id.getName()) == null) {
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El id " + id.getName() + " no se encuentra en la tabla de símbolos");
            return;
        }

        this.subtype = id.getSubtype();

        //Obtener variable
        variable = id.getVariable();
    }

    public SymbolFactor(int symbol) {
        super("Factor", 0);

        //Generar nueva variable temporal
        variable = Variable.nextVariable();

        switch (symbol) {
            case ParserSym.TRUE:
                subtype = Subtype.BOOLEAN;

                TAC.genera("ASSIG", "true", "", variable);
                break;
            case ParserSym.FALSE:
                subtype = Subtype.BOOLEAN;

                TAC.genera("ASSIG", "false", "", variable);
                break;
            case ParserSym.NONE:
                subtype = Subtype.NONE;

                TAC.genera("ASSIG", "null", "", variable);
                break;
        }
    }

    public SymbolFactor(int symbol, String literal) {
        super("Factor", 0);

        switch (symbol) {
            case ParserSym.LITERAL:
                subtype = Subtype.STRING;
                break;
            case ParserSym.NUM:
                subtype = Subtype.INT;
                break;
        }

        //Generar nueva variable temporal
        variable = Variable.nextVariable();

        TAC.genera("ASSIG", literal, "", variable);
    }

    public String getVariable() {
        return variable;
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + " " + subtype + "\"];\n");

        if (bool != null) {
            out.print(index + "->" + bool.getIndex() + "\n");
            bool.toDot(out);
        } else if (id != null) {
            out.print(index + "->" + id.getIndex() + "\n");
            id.toDot(out);
        }
    }

}
