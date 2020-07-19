package compiler.output;

import compiler.intermediate.ThreeAddressCode;
import compiler.syntax.tables.Procedure;
import compiler.syntax.tables.Symbol;
import compiler.syntax.tables.Type;
import compiler.syntax.tables.Variable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Output {

    private final static String INFO_FILE = "info.txt";
    private final static String TOKENS_FILE = "tokens.txt";
    private final static String ERRORS_FILE = "errors.txt";
    private final static String SYMBOLS_FILE = "symbols_table.html";
    private final static String VARIABLE_FILE = "variable_table.html";
    private final static String PROCEDURE_FILE = "procedure_table.html";
    private final static String THREE_ADDRESS_CODE = "three_address_code.txt";

    /**
     * Crear fichero html y inicializar tabla de simbolos
     */
    public static void initSymbolTable() {

        try {

            FileWriter fileWriter = new FileWriter(SYMBOLS_FILE);
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            buffer.write("<!DOCTYPE html><html><head>");
            buffer.write("<title>Tabla de símbolos</title>");
            buffer.write("<meta charset=\"UTF-8\">");
            buffer.write("<style>table {width:100%;padding:0 100px;border-collapse:collapse;}table,th,td{border:1px solid #000}tr:nth-child(odd){background: #ddd}th, td {padding: 10px}</style>");
            buffer.write("</head><body>");
            buffer.write("<h1>Práctica Compiladores - UIB</h1>");
            buffer.write("<h2>Tabla de simbolos</h2>");
            buffer.write("<table><tr><th>Context</th><th>ID</th><th>Type</th><th>SubType</th><th>Args</th></tr>");

            buffer.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + SYMBOLS_FILE + ": " + e.getMessage());
        }

    }

    /**
     * Añadir simbolo al fichero de tabla de símbolos
     */
    public static void writeSymbol(Symbol symbol, Stack<HashMap> hashMapStack) {

        try {
            FileWriter fileWriter = new FileWriter(SYMBOLS_FILE, true);
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
            System.err.println("[IOException] Write Error in " + SYMBOLS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Cerrar fichero html de table símbolos
     */
    public static void closeSymbolTable() {
        try {
            FileWriter fileWriter = new FileWriter(SYMBOLS_FILE, true);
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            buffer.write("</table></body></html>");
            buffer.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + SYMBOLS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Crear fichero html y inicializar tabla de procedimientos
     */
    public static void initProcedureTable() {
        try {
            FileWriter fileWriter = new FileWriter(PROCEDURE_FILE);
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            buffer.write("<!DOCTYPE html><html><head>");
            buffer.write("<title>Tabla de procedimientos</title>");
            buffer.write("<meta charset=\"UTF-8\">");
            buffer.write("<style>table {width:100%;padding:0 100px;border-collapse:collapse;}table,th,td{border:1px solid #000}tr:nth-child(odd){background: #ddd}th, td {padding: 10px}</style>");
            buffer.write("</head><body>");
            buffer.write("<h1>Práctica Compiladores - UIB</h1>");
            buffer.write("<h2>Tabla de procedimientos</h2>");
            buffer.write("<table><tr><th>NP</th><th>ID</th><th>Label</th><th>Deep</th><th>Params</th><th>Size</th><th>Return</th></tr>");

            buffer.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + PROCEDURE_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Añadir simbolo al fichero de tabla de procedimientos
     */
    public static void writeProcedure(String id, Procedure procedure) {

        try {
            FileWriter fileWriter = new FileWriter(PROCEDURE_FILE, true);
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            //Añadir entrada a la tabla html
            buffer.write("<tr><td>NP : " + procedure.getNumProcedure()
                    + "<td>ID : " + id
                    + "</td><td>Label: " + procedure.getStartLabel()
                    + "</td><td>Deep: " + procedure.getDeep()
                    + "</td><td>Params: " + procedure.getNumParams()
                    + "</td><td>Size: " + (procedure.getNumParams() * 4)
                    + "</td><td>Return: " + procedure.getSubtype() + "</td></tr>");

            buffer.close();

        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + PROCEDURE_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Cerrar fichero html de tabla de procedimientos
     */
    public static void closeProcedureTable() {
        try {
            FileWriter fileWriter = new FileWriter(PROCEDURE_FILE, true);
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            buffer.write("</table></body></html>");
            buffer.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + PROCEDURE_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Crear fichero html y inicializar tabla de variables
     */
    public static void initVariableTable() {

        try {
            FileWriter fileWriter = new FileWriter(VARIABLE_FILE);
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            buffer.write("<!DOCTYPE html><html><head>");
            buffer.write("<title>Tabla de variables</title>");
            buffer.write("<meta charset=\"UTF-8\">");
            buffer.write("<style>table {width:100%;padding:0 100px;border-collapse:collapse;}table,th,td{border:1px solid #000}tr:nth-child(odd){background: #ddd}th, td {padding: 10px}</style>");
            buffer.write("</head><body>");
            buffer.write("<h1>Práctica Compiladores - UIB</h1>");
            buffer.write("<h2>Tabla de variables</h2>");
            buffer.write("<table><tr><th>NV</th><th>ID</th><th>Deep</th><th>Size</th><th>Parent</th><th>Type</th><th>SubType</th><th>Value</th></tr>");

            buffer.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + VARIABLE_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Añadir simbolo al fichero de tabla de símbolos
     */
    public static void writeVariable(String id, Variable variable) {

        String color;

        if (variable.getId().contains("#"))
            color = "#D2B4DE";
        else if (variable.getType() == Type.ARG)
            color = "#FAD7A0";
        else if (variable.getType() == Type.CONST)
            color = "#F1948A";
        else if (variable.getDeep() == 0)
            color = "#82E0AA";
        else
            color = "#A9CCE3";

        try {
            FileWriter fileWriter = new FileWriter(VARIABLE_FILE, true);
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            //Añadir entrada a la tabla html
            buffer.write("<tr style='background-color:" + color + "'><td>NV : " + variable.getNumVariable() + "</td><td>Id : " + id + "</td><td>Deep : " + variable.getDeep() + "</td><td>Size : " + variable.getSize() + "</td><td>Parent : " + variable.getParentFunction() + "</td><td>Type : " + variable.getType() + "</td><td>Subtype : " + variable.getSubtype() + "</td><td>Value : " + variable.getValue() + "</td></tr>");


            buffer.close();

        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + VARIABLE_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Cerrar fichero html de tabla de variables
     */
    public static void closeVariableTable() {
        try {
            FileWriter fileWriter = new FileWriter(VARIABLE_FILE, true);
            BufferedWriter buffer = new BufferedWriter(fileWriter);

            buffer.write("</table></body></html>");
            buffer.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + VARIABLE_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Vaciar fichero de errores
     *
     * @return result
     */
    public static void truncateErrors() {
        try {
            FileWriter fileWriter = new FileWriter(ERRORS_FILE);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + ERRORS_FILE + ": " + e.getMessage());
        }
    }

    public static void writeError(String error) {
        try {
            FileWriter fileWriter = new FileWriter(ERRORS_FILE, true);
            fileWriter.write(error + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + ERRORS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Vaciar fichero de tokens
     *
     * @return result
     */
    public static void truncateTokens() {
        try {
            FileWriter fileWriter = new FileWriter(TOKENS_FILE);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + TOKENS_FILE + ": " + e.getMessage());
        }
    }

    public static void writeToken(String token) {
        try {
            FileWriter fileWriter = new FileWriter(TOKENS_FILE, true);
            fileWriter.write(token + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + TOKENS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Vaciar fichero de info
     *
     * @return result
     */
    public static void truncateInfo() {
        try {
            FileWriter fileWriter = new FileWriter(INFO_FILE);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + INFO_FILE + ": " + e.getMessage());
        }
    }

    public static void writeInfo(String info) {

        System.out.println(info);

        try {
            FileWriter fileWriter = new FileWriter(INFO_FILE, true);
            fileWriter.write(info + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + INFO_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Vaciar fichero de código de tres direcciones
     *
     * @return result
     */
    public static void truncateThreeAddressCode() {
        try {
            FileWriter fileWriter = new FileWriter(THREE_ADDRESS_CODE);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + THREE_ADDRESS_CODE + ": " + e.getMessage());
        }
    }

    public static void writeThreeAddressCodes(ArrayList<ThreeAddressCode> threeAddressCodes) {

        try {
            FileWriter fileWriter = new FileWriter(THREE_ADDRESS_CODE, true);

            for (ThreeAddressCode threeAddressCode : threeAddressCodes) {
                fileWriter.write(threeAddressCode.toString() + "\n");
            }

            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + THREE_ADDRESS_CODE + ": " + e.getMessage());
        }
    }

    public static void writeThreeAddressCode(String info) {

        System.out.println(info);

        try {
            FileWriter fileWriter = new FileWriter(THREE_ADDRESS_CODE, true);
            fileWriter.write(info + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("[IOException] Write Error in " + THREE_ADDRESS_CODE + ": " + e.getMessage());
        }
    }
}
