package compiler;

import compiler.lexic.Lexer;
import compiler.output.Output;
import compiler.syntax.*;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import java.io.*;

public class Compiler {

    public static void main(String[] args) throws Exception {

        //Comprobar que se pasa el fichero de código fuente como parámetro
        if (args[0].isEmpty()) {
            System.err.println("Error: No se ha introducido fichero de código fuente como argumento");
            System.exit(-1);
        }

        String sourceCode = args[0];
        FileReader fileReader = new FileReader(sourceCode);

        int numTokens = 0;
        Lexer scanner = new Lexer(fileReader);
        scanner.yyreset(fileReader);
        Symbol symbol = scanner.next_token();

        Output.writeInfo("ANÁLISIS LÉXICO iniciado.");

        while (symbol.sym != ParserSym.EOF) {
            Output.writeToken(scanner.getRow() + ":" + scanner.getCol() + " TKN_" + ParserSym.terminalNames[symbol.sym] + " [" + symbol.value + "]");
            symbol = scanner.next_token();
            numTokens++;
        }

        Output.writeInfo("Número de tokens identificados: " + numTokens);
        Output.writeInfo("ANÁLISIS LÉXICO terminado.");

        scanner.yyclose();
        fileReader = new FileReader(sourceCode);
        scanner.yyreset(fileReader);

        Output.writeInfo("ANÁLISIS SINTÁCTICO iniciado.");

        Parser parser = new Parser(scanner, new ComplexSymbolFactory());
        parser.debug_parse();

        Output.writeInfo("ANÁLISIS SINTÁCTICO terminado.");
    }
}
