package compiler;


import compiler.lexic.Lexer;
import compiler.syntax.*;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import java.io.*;

public class Compiler {

    private final static String DIR = "tokens.txt";

    public static void main(String[] args) throws Exception {

        //Comprobar que se pasa el fichero de código fuente como parámetro
        if (args[0].isEmpty()) {
            System.out.println("ERROR: Introduce fichero");
            System.exit(-1);
        }

        String fileName = args[0];
        FileReader fileReader = new FileReader(fileName);
        FileWriter fileWriter = new FileWriter(DIR);

        int numTokens = 0;
        Lexer scan = new Lexer(fileReader);
        Symbol symbol = scan.next_token();

        System.out.println("FASE LEXICA iniciada.");
        System.out.println("Generando fichero de tokens...");

        while (symbol.sym != ParserSym.EOF) {

            fileWriter.write(scan.getRow() + ":" + scan.getCol()    // Posicion donde se ha encontrado el token
                    + " TKN_" + ParserSym.terminalNames[symbol.sym]     // Tipo de token encontrado
                    + " [" + symbol.value + "]\n");                     // Valor del token

            symbol = scan.next_token();
            numTokens++;
        }

        fileReader.close();
        fileWriter.close();
        scan.yyclose();

        System.out.println("Número de tokens identificados: " + numTokens);
        System.out.println("FASE LEXICA terminada.");

        fileReader = new FileReader(args[0]);
        scan.yyreset(fileReader);

        ComplexSymbolFactory factory = new ComplexSymbolFactory();
        Parser parser = new Parser(scan, factory);
        parser.debug_parse();
    }
}
