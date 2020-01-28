package compiler;


import compiler.lexic.Lexer;
import compiler.syntax.*;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import java.io.*;

public class Compiler {

    public static void main(String[] args) throws Exception {

        final String DIR = "tokens.txt";
        final int EOF = ParserSym.EOF;

        FileWriter fw = new FileWriter(DIR);
        FileReader fr = null;
        String fileName;
        Symbol s;
        int numTokens = 0;

        if(args[0].isEmpty())
            System.out.println("ERROR: Introduce fichero");
        else {
            fileName = args[0];
            fr = new FileReader(fileName);
        }

        System.out.println("FASE LEXICA iniciada.");
        Lexer scan = new Lexer(fr);
        s = scan.next_token();
        System.out.println("Generando fichero de tokens...");

        while (s.sym != EOF) {

            fw.write(scan.getRow() + ":" + scan.getCol()            // Posicion donde se ha encontrado el token
                    + " TKN_" + ParserSym.terminalNames[s.sym]      // Tipo de token encontrado
                    + " [" + s.value + "]\n");                      // Valor del token
            s = scan.next_token();
            numTokens++;
        }

        fw.close();
        fr.close();
        scan.yyclose();

        System.out.println("Numero de tokens identificados: " + numTokens);
        System.out.println("FASE LEXICA terminada.");

        fr = new FileReader(args[0]);
        scan.yyreset(fr);

        ComplexSymbolFactory factory = new ComplexSymbolFactory();
        Parser parser = new Parser(scan, factory);
        parser.debug_parse();

    }
}
