package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.ParserSym;
import compiler.syntax.table.Subtype;

import java.io.PrintWriter;

public class SymbolFactor extends SymbolBase {

    private SymbolBool bool;
    private SymbolId id;

    private Subtype subtype;

    // [FORMA] Factor ::= LPAREN Bool RPAREN
    public SymbolFactor(SymbolBool bool) {
        super("Factor", 0);

        this.bool = bool;
        subtype = bool.getSubtype();
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
    }

    public SymbolFactor(int symbol) {
        super("Factor", 0);
        switch (symbol) {
            case ParserSym.LITERAL:
                subtype = Subtype.STRING;
                break;
            case ParserSym.NUM:
                subtype = Subtype.INT;
                break;
            case ParserSym.TRUE:
            case ParserSym.FALSE:
                subtype = Subtype.BOOLEAN;
                break;
            case ParserSym.NONE:
                subtype = Subtype.NONE;
                break;
        }
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
