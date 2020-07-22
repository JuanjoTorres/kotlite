package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Symbol;
import compiler.syntax.tables.Type;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SymbolFunctioncall extends SymbolBase {

    private SymbolId id;
    private SymbolArgs args;

    private Variable variable;

    private Subtype subtype;

    // [FORMA] Functioncall ::= Id LPAREN Args RPAREN
    public SymbolFunctioncall(SymbolId id, SymbolArgs args, int line, int column) {
        super("Functioncall", 0);

        this.id = id;
        this.args = args;

        //Comprobar si existe la función
        if (symbolTable.getId(id.getName()) == null) {
            Output.writeError("Error semántico en posición " + line + ":" + column + " - La función " + id.getName() + " no se encuentra en la tabla de símbolos");
            return;
        }

        subtype = id.getSubtype();

        ArrayList<Symbol> functionArgs = symbolTable.getId(id.getName()).getArgs();

        //Si la funcion se llama sin argumentos y si que tiene argumentos, el numero de argumentos es erróneo
        if (args == null && functionArgs.size() > 0) {
            Output.writeError("Error semántico en posición " + line + ":" + column + " - La función " + id.getName() + " espera " + functionArgs.size() +
                    " argumento(s) y se han recibido 0 argumento(s)");
            return;
        } else if (args != null && functionArgs.size() == 0) {
            Output.writeError("Error semántico en posición " + line + ":" + column + " - La función " + id.getName() +
                    " espera 0 argumentos y se han recibido " + args.getArgs().size() + " argumento(s)");
            return;
        }

        if (args != null) {
            //Comprobar el número de argumentos
            if (args.getArgs().size() != functionArgs.size()) {
                Output.writeError("Error semántico en posición " + line + ":" + column + " - La función " + id.getName() + " espera " + functionArgs.size() +
                        " argumento(s) y se han recibido " + args.getArgs().size() + " argumento(s)");
                return;
            }

            //Comprobar el tipo subyacente de los argumentos
            for (int i = 0; i < args.getArgs().size(); i++) {
                if (args.getArgs().get(i).getSubtype() != functionArgs.get(i).getSubtype())
                    Output.writeError("Error semántico en posición " + line + ":" + column + " - El argumento en posición " + (i + 1) +
                            " de la función " + id.getName() + "() es del tipo " + functionArgs.get(i).getSubtype() +
                            " y se ha recibido un parámetro del tipo " + args.getArgs().get(i).getSubtype());
            }

            //Crear variable por cada parametro
            for (int i = 0; i < args.getArgs().size(); i++) {
                Variable variable = new Variable(args.getArgs().get(i).getId(), false);
                variable.setType(Type.ARG);
                variable.setSubtype(args.getArgs().get(i).getSubtype());
                variable.setParentFunction(generator.peekFunctionLabel());
                variableTable.put(variable.getId(), variable);

                //Generar código de tres direcciones
                generator.addThreeAddressCode("PARAM", "", "", variable.getId());
            }
        }

        if (subtype != Subtype.NONE) {

            //Generar variable y meterla en la tabla de variables
            variable = new Variable(generator.generateVariable(), generator.peekFunctionLabel(), true);
            variable.setType(Type.VAR);
            variable.setSubtype(subtype);
            variableTable.put(variable.getId(), variable);

            // Llamada a función guardando el valor de retorno en variable temporal
            generator.addThreeAddressCode("CALL", id.getProcedure().getStartLabel(), "", variable.getId());
        } else {
            // Llamada a función guardando el valor de retorno en variable temporal
            generator.addThreeAddressCode("CALL", id.getProcedure().getStartLabel(), "", "");
        }
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

        if (id != null)
            out.print(index + "->" + id.getIndex() + "\n");
        if (args != null)
            out.print(index + "->" + args.getIndex() + "\n");

        if (id != null)
            id.toDot(out);
        if (args != null)
            args.toDot(out);
    }

}
