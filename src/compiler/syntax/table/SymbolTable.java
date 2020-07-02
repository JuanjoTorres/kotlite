package compiler.syntax.table;

import compiler.output.Output;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

    private Stack<HashMap> hashMapStack = new Stack<>();

    public SymbolTable() {

        //Iniciar tabla de símbolos
        init();
    }

    /**
     * Vaciar la tabla de símbolo
     */
    public void init() {

        //Vaciar pila e inicializar nivel 0
        hashMapStack = new Stack<>();
        hashMapStack.push(new HashMap());

        //Vaciar contenido de fichero de info
        Output.truncateInfo();

        //Vaciar contenido de fichero de errores
        Output.truncateErrors();

        //Vaciar contenido de fichero de tokens
        Output.truncateTokens();

        //Inicializar fichero de tabla de simbolos
        Output.initSymbolTable();

        //Inicializar fichero de tabla de variable
        Output.initVariableTable();

        //Añadir función print() a la tabla de simbolos
        Symbol print = new Symbol("print", Type.PROC, Subtype.NONE);
        print.getArgs().add(new Symbol("text", Type.ARG, Subtype.STRING));
        add(print);
    }

    public void startBlock() {
        //Aumentar nivel insertando un nuevo hashmap en la pila
        hashMapStack.push(new HashMap());
    }

    public void endBlock() {
        //Reducir nivel eliminando un hashmap de la pila
        hashMapStack.pop();
    }

    public boolean add(Symbol symbol) {
        //Comprobar que no exista el identificador en el ámbito actual de la tabla de simbolos
        if (!hashMapStack.peek().containsKey(symbol.getId())) {
            //Insertar identificador en la tabla de símbolos
            hashMapStack.peek().put(symbol.getId(), symbol);

            //Añadir symbol al fichero de la tabla de símbolos
            Output.writeSymbol(symbol, hashMapStack);
        } else {
            return false;
        }

        return true;
    }

    public Symbol getId(String id) {

        //Recorrer la tabla de símbolos desde el nivel superior hacia el inferior
        for (int i = hashMapStack.size() - 1; i >= 0; i--) {

            //Comprobar si existe el identificador en el nivel actual
            if (hashMapStack.get(i).containsKey(id))
                //Devolver identificador
                return (Symbol) hashMapStack.get(i).get(id);

        }

        return null;
    }
}
