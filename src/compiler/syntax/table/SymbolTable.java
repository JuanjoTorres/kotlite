package compiler.syntax.table;

import compiler.KotliteException;

import java.io.*;
import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

    public BufferedWriter buffer;

    Stack<HashMap> hashMapStack = new Stack<>();

    public SymbolTable() {

        reset();

        try {

            FileOutputStream file = new FileOutputStream("symbols_table.html");
            OutputStreamWriter stream = new OutputStreamWriter(file, "UTF-8");
            buffer = new BufferedWriter(stream);

            //TODO Crear fichero html y inicializar tabla

            buffer.write("<!DOCTYPE html><html><head>");
            buffer.write("<title>Tabla de símbolos</title>");
            buffer.write("<meta charset=\"UTF-8\">");
            buffer.write("<style>table {width:100%;padding:0 100px;border-collapse:collapse;}table,th,td{border:1px solid #000}tr:nth-child(odd){background: #ddd}th, td {padding: 10px}</style>");
            buffer.write("</head><body>");
            buffer.write("<h1>Práctica Compiladores 1 - UIB</h1>");
            buffer.write("<h2>Tabla de simbolos</h2>");
            buffer.write("<table><tr><th>Context</th><th>ID</th><th>Type</th><th>SubType</th><th>Args</th></tr>");

            // HACER VOLCADO DE MEMORIA QUE PIDE EL PROFE, FORMATO PDF
        } catch (IOException e) {

            System.err.println("[SYNTAX ERROR] Write Error in Symbol Table: " + e.getMessage());
        }


        //Añadir funciont print() a la tabla de simbolos
        try {
            add(new Symbol("print", Type.PROC, Subtype.NULL));
        } catch (KotliteException.DuplicatedIdentifierException e) {
            e.printStackTrace();
        }

    }

    public void reset() {
        //Vaciar pila e inicializar nivel 0
        hashMapStack.empty();
        hashMapStack.push(new HashMap());
    }

    public void startBlock() {
        //Aumentar nivel insertando un nuevo hashmap en la pila
        hashMapStack.push(new HashMap());
    }

    public void endBlock() {
        //Reducir nivel eliminando un hashmap de la pila
        hashMapStack.pop();
    }

    public void add(Symbol symbol) throws KotliteException.DuplicatedIdentifierException {

        System.out.println("Insertando ID:" + symbol.getId());

        //Comprobar si ya existe el identificador
        if (hashMapStack.peek().containsKey(symbol.getId()))
            throw new KotliteException.DuplicatedIdentifierException("Duplicated ID");

        //Insertar identificador en la tabla de símbolos
        hashMapStack.peek().put(symbol.getId(), symbol);

        //Añadir entrada a la tabla html
        try {
            if (symbol.getType() == Type.PROC) {
                buffer.write("<tr><td>Nivel : " + hashMapStack.size() + "</td><td>Id : " + symbol.getId() + "</td><td>Type : " + symbol.getType() + "</td><td>Subtype : " + symbol.getSubtype() + "</td><td>");

                if (symbol.getArgs() != null) {
                    buffer.write("<table>");
                    for (int i = 0; i < symbol.getArgs().size(); i++) {
                        buffer.write("<tr><td>Id : " + symbol.getArgs().get(i).getId() + "</td><td>Type : " + symbol.getArgs().get(i).getType() + "</td><td>Subtype : " + symbol.getArgs().get(i).getSubtype() + "</td></tr>");
                    }
                    buffer.write("</table>");
                }

                buffer.write("</td></tr>");

            } else {
                buffer.write("<tr><td>Nivel : " + hashMapStack.size() + "</td><td>Id : " + symbol.getId() + "</td><td>Type : " + symbol.getType() + "</td><td>Subtype : " + symbol.getSubtype() + "</td><td style='text-align:center'>-</td></tr>");
            }
        } catch (IOException e) {
            System.err.println("[SYNTAX ERROR] Write Error in Symbol Table: " + e.getMessage());
        }

        //TODO FALTA AÑADIR EL ATRIBUTO NP
    }

    public Symbol getId(String id) throws KotliteException.IdentifierNotExistException {

        System.out.println("Buscando id: " + id);

        //Recorrer la tabla de símbolos desde el nivel superior hacia el inferior
        for (int i = hashMapStack.size() - 1; i >= 0; i--) {

            //TODO Buscar en array list de argumentos

            //Comprobar si existe el identificador en el nivel actual
            if (hashMapStack.get(i).containsKey(id))
                //Devolver identificador
                return (Symbol) hashMapStack.get(i).get(id);

        }

        //No existe el identificador, lanzar excepción
        throw new KotliteException.IdentifierNotExistException("No existe el ID");
    }
}
