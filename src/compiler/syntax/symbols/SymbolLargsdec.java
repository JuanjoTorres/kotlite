package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.tables.Symbol;
import compiler.syntax.tables.Type;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SymbolLargsdec extends SymbolBase {

    private SymbolLargsdec largsdec;
    private SymbolId id;
    private SymbolBasic basic;

    private ArrayList<Symbol> args = new ArrayList<>();

    // [FORMA] Largsdec ::= Largsdec SEMICOLON Id COLON Basic
    public SymbolLargsdec(SymbolLargsdec largsdec, SymbolId id, SymbolBasic basic, int line, int column) {
        super("Largsdec", 0);

        this.largsdec = largsdec;
        this.id = id;
        this.basic = basic;

        //Crear simbolo
        Symbol symbol = new Symbol(id.getName(), Type.ARG, basic.getSubtype());

        //Añadir argumentos de lista de argumentos y el argumento actual
        args.addAll(largsdec.getArgs());
        args.add(symbol);

        //Añadir a la tabla de simbolos
        if (!symbolTable.add(symbol))
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El ID " + id.getName() +
                    " ya se encuentra en la tabla de símbolos en el ámbito actual");
        else
            //TODO Hay que meterlos en la tabla de variables ¿?
            //TODO ¿Con nueva variable? ¿Que se mete en ella?
            variableTable.put(symbol.getId(), new Variable(generator.generateVariable()));
    }

    // [FORMA] Largsdec ::= Largsdec SEMICOLON Id COLON Basic
    public SymbolLargsdec(SymbolId id, SymbolBasic basic, int line, int column) {
        super("Largsdec", 0);

        this.id = id;
        this.basic = basic;

        //Crear simbolo
        Symbol symbol = new Symbol(id.getName(), Type.ARG, basic.getSubtype());

        //Añadir el argumento actual
        args.add(new Symbol(id.getName(), Type.ARG, basic.getSubtype()));

        //Añadir a la tabla de simbolos y a la tabla de variables
        if (!symbolTable.add(symbol))
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El ID " + id.getName() +
                    " ya se encuentra en la tabla de símbolos en el ámbito actual");
        else
            //TODO Hay que meterlos en la tabla de variables ¿?
            variableTable.put(symbol.getId(), new Variable(generator.generateVariable()));
    }

    public void setArgs(ArrayList<Symbol> args) {
        this.args = args;
    }

    public ArrayList<Symbol> getArgs() {
        return args;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (largsdec != null)
            out.print(index + "->" + largsdec.getIndex() + "\n");
        out.print(index + "->" + id.getIndex() + "\n");
        out.print(index + "->" + basic.getIndex() + "\n");

        if (largsdec != null)
            largsdec.toDot(out);
        id.toDot(out);
        basic.toDot(out);
    }

}
