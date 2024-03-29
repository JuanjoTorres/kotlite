package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.ParserSym;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Type;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolFactor extends SymbolBase {

    private SymbolBool bool;
    private SymbolId id;

    private Subtype subtype;

    private Variable variable;

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

        //Generar variable
        variable = new Variable(generator.generateVariable(), generator.peekFunctionLabel(), true);

        switch (symbol) {
            case ParserSym.TRUE:
                subtype = Subtype.BOOLEAN;

                //Dar valor a la variable
                variable.setValue("true");

                //Añadir código de tres direcciones con la operacion
                generator.addThreeAddressCode("COPY_LITERAL", "true", "", variable.getId());
                break;
            case ParserSym.FALSE:
                subtype = Subtype.BOOLEAN;

                //Dar valor a la variable
                variable.setValue("false");

                //Añadir código de tres direcciones con la operacion
                generator.addThreeAddressCode("COPY_LITERAL", "false", "", variable.getId());
                break;
            case ParserSym.NONE:
                subtype = Subtype.NONE;

                //Dar valor a la variable
                variable.setValue("null");

                //Añadir código de tres direcciones con la operacion
                generator.addThreeAddressCode("COPY_LITERAL", "null", "", variable.getId());
                break;
        }

        //Poner tipo y tipo subyacente
        variable.setType(Type.VAR);
        variable.setSubtype(subtype);

        //Meter en tabla de variables
        variableTable.put(variable.getId(), variable);
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

        //Si es un entero comprobar que no se salga del rango
        if (subtype == Subtype.INT) {
            if (Long.parseLong(literal) > Integer.MAX_VALUE) {
                Output.writeInfo(" * WARNING: El número " + literal + " es mayor que el valor máximo de un Int de 32 bits." +
                        " Se ha reducido su valor a " + Integer.MAX_VALUE);
                literal = String.valueOf(Integer.MAX_VALUE);
            } else if (Long.parseLong(literal) < Integer.MIN_VALUE) {
                Output.writeInfo(" * WARNING: El número " + literal + " es menor que el valor mínimo de un Int de 32 bits." +
                        " Se ha aumentado su valor a " + Integer.MIN_VALUE);
                literal = String.valueOf(Integer.MIN_VALUE);
            }
        }

        //Generar variable
        variable = new Variable(generator.generateVariable(), generator.peekFunctionLabel(), true);
        variable.setType(Type.VAR);
        variable.setSubtype(subtype);
        variableTable.put(variable.getId(), variable);
        variable.setValue(literal);

        //Añadir código de tres direcciones con la operacion
        generator.addThreeAddressCode("COPY_LITERAL", literal, "", variable.getId());
    }

    public Variable getVariable() {
        return variable;
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + " " + subtype + "\"];\n");

        if (bool != null)
            out.print(index + "->" + bool.getIndex() + "\n");
        if (id != null)
            out.print(index + "->" + id.getIndex() + "\n");

        if (bool != null)
            bool.toDot(out);
        if (id != null)
            id.toDot(out);
    }

}
