package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;
import compiler.output.Output;
import compiler.syntax.ParserSym;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolBool extends SymbolBase {

    private SymbolBool bool;
    private SymbolJoin join;
    private SymbolRelation relation;

    private Subtype subtype;

    private Variable variable;

    // FORMA Bool ::= Bool Join Relation
    public SymbolBool(SymbolBool bool, SymbolJoin join, SymbolRelation relation, int line, int column) {
        super("Bool", 0);

        this.bool = bool;
        this.join = join;
        this.relation = relation;

        if (bool.getSubtype() != Subtype.BOOLEAN || relation.getSubtype() != Subtype.BOOLEAN)
            Output.writeError("Error semántico en posición " + line + ":" + column +
                    " - El operador binario " + ParserSym.terminalNames[join.getSymbol()] +
                    " requiere los dos operandos de tipo BOOLEAN y se ha encontrado los tipos " +
                    bool.getSubtype() + " y " + relation.getSubtype());

        //Subtype de AND o OR es BOOL
        subtype = Subtype.BOOLEAN;

        variable = new Variable(generator.generateVariable());

        Variable boolVar = bool.getVariable();
        Variable relationVar = relation.getVariable();

        //Añadir código de tres direcciones con la operacion
        generator.addThreeAddressCode(new ThreeAddressCode(ParserSym.terminalNames[join.getSymbol()], boolVar.getId(), relationVar.getId(), variable.getId()));
    }

    // FORMA Bool ::= Relation
    public SymbolBool(SymbolRelation relation) {
        super("Bool", 0);

        this.relation = relation;

        subtype = relation.getSubtype();
        variable = relation.getVariable();
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

        if (bool != null)
            out.print(index + "->" + bool.getIndex() + "\n");
        if (join != null)
            out.print(index + "->" + join.getIndex() + "\n");
        out.print(index + "->" + relation.getIndex() + "\n");

        if (bool != null)
            bool.toDot(out);
        if (join != null)
            join.toDot(out);
        relation.toDot(out);
    }

}
