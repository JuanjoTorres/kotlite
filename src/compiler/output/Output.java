package compiler.output;

import compiler.syntax.table.Symbol;
import compiler.syntax.table.Type;

import java.io.*;
import java.util.HashMap;
import java.util.Stack;

public class Output {

    /**
     * Crear fichero html y inicializar tabla de simbolos
     */
    public static void initSymbolTable() {

        try {

            FileWriter fileWriter = new FileWriter("symbols_table.html");
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            buffer.write("<!DOCTYPE html><html><head>");
            buffer.write("<title>Tabla de símbolos</title>");
            buffer.write("<meta charset=\"UTF-8\">");
            buffer.write("<style>table {width:100%;padding:0 100px;border-collapse:collapse;}table,th,td{border:1px solid #000}tr:nth-child(odd){background: #ddd}th, td {padding: 10px}</style>");
            buffer.write("</head><body>");
            buffer.write("<h1>Práctica Compiladores 1 - UIB</h1>");
            buffer.write("<h2>Tabla de simbolos</h2>");
            buffer.write("<table><tr><th>Context</th><th>ID</th><th>Type</th><th>SubType</th><th>Args</th></tr>");

            buffer.close();

            // HACER VOLCADO DE MEMORIA QUE PIDE EL PROFE, FORMATO PDF
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in symbols_table.html: " + e.getMessage());
        }

    }

    /**
     * Añadir simbolo al fichero de tabla de símbolos
     */
    public static void writeTable(Symbol symbol, Stack<HashMap> hashMapStack) {

        try {
            FileWriter fileWriter = new FileWriter("symbols_table.html", true);
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            //Añadir entrada a la tabla html
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

            buffer.close();

        } catch (IOException e) {
            System.err.println("[IOException] Write Error in symbols_table.html: " + e.getMessage());
        }
    }

    /**
     * Cerrar fichero html
     */
    public static void closeSymbolTable() {
        try {
            FileWriter fileWriter = new FileWriter("symbols_table.html", true);
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            buffer.write("</table></body></html>");
            buffer.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in symbols_table.html: " + e.getMessage());
        }
    }

    /**
     * Vaciar fichero de errores
     *
     * @return result
     */
    public static void truncateErrors() {
        try {
            FileWriter fileWriter = new FileWriter("errors.txt");
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in errors.txt: " + e.getMessage());
        }
    }

    public static void writeError(String error) {
        try {
            FileWriter fileWriter = new FileWriter("errors.txt", true);
            fileWriter.write(error + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in errors.txt: " + e.getMessage());
        }
    }
}
