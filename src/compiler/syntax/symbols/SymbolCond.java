package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;
import compiler.output.Output;
import compiler.syntax.ParserSym;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolCond extends SymbolBase {

    private SymbolBool bool;

    private Subtype subtype;
    private Variable variable;

    // FORMA Cond ::= Bool .
    public SymbolCond(SymbolBool bool) {
        super("Cond", 0);

        this.bool = bool;

        //Subtype de COND es el del  BOOL
        subtype = bool.getSubtype();

        //Obtener variable de bool
        variable = bool.getVariable();

        //Obtener etiqueta de condición falsa
        String falseLabel = Generator.popCondFalseLabel();

        //Si no hay etiqueta de condiciona falsa es que estamos en un bucle WHILE
        if (falseLabel == null) {
            falseLabel = Generator.popEndloopLabel();
            //Volver a meter etiqueta en la pila para generar el SKIP del WHILE
            Generator.pushEndloopLabel(falseLabel);
        } else {
            //Volver a meter etiqueta en la pila para generar el SKIP del ENDIF
            Generator.pushCondFalseLabel(falseLabel);
        }

        //Salto condicional a la etiqueta de false o endloop
        Generator.addThreeAddressCode(new ThreeAddressCode("IFGOTO", variable.getId(), "", falseLabel));
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

        if (bool != null)
            bool.toDot(out);
    }
}
