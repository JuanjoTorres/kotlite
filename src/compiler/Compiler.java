package compiler;

import compiler.lexic.Lexer;
import compiler.syntax.*;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import java.io.*;

public class Compiler {

    private final static String TOKENS_FILE = "tokens.txt";

    public static void main(String[] args) throws Exception {

        //Comprobar que se pasa el fichero de código fuente como parámetro
        if (args[0].isEmpty()) {
            System.out.println("ERROR: Introduce fichero");
            System.exit(-1);
        }

        String sourceCode = args[0];
        FileReader fileReader = new FileReader(sourceCode);
        FileWriter fileWriter = new FileWriter(TOKENS_FILE);

        int numTokens = 0;
        Lexer scanner = new Lexer(fileReader);
        Symbol symbol = scanner.next_token();

        System.out.println("FASE LEXICA iniciada.");
        System.out.println("Generando fichero de tokens...");

        while (symbol.sym != ParserSym.EOF) {

            fileWriter.write(scanner.getRow() + ":" + scanner.getCol()    // Posicion donde se ha encontrado el token
                    + " TKN_" + ParserSym.terminalNames[symbol.sym]     // Tipo de token encontrado
                    + " [" + symbol.value + "]\n");                     // Valor del token

            symbol = scanner.next_token();
            numTokens++;
        }

        fileReader.close();
        fileWriter.close();
        scanner.yyclose();

        System.out.println("Número de tokens identificados: " + numTokens);
        System.out.println("FASE LEXICA terminada.");

        fileReader = new FileReader(sourceCode);
        scanner.yyreset(fileReader);

        ComplexSymbolFactory factory = new ComplexSymbolFactory();
        Parser parser = new Parser(scanner, factory);
        parser.debug_parse();
    }
}
