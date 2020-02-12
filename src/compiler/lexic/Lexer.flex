
// El codi que es copiarà tal qual al document. A l'inici
package compiler.lexic;

import compiler.output.Output;
import compiler.syntax.ParserSym;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;

%%

/******************************************************************************
 Indicación de que tipo de analizador sintáctico se utilizara, en este caso se
 usa CUP (arbol sintactico LALR)
 *****************************************************************************/

%cup
%public        // Para indicar si la clase es publica
%class Lexer   // Nombre de la clase
%unicode
%line
%column
%char

/* Definiciones */
digit  = [0-9]
letter = [a-zA-Z]
whites = [' ' | '\t']+
eol    = ['\r' | '\n' | '\r\n']
strings = \"(\\.|[^\"])*\"
input = [^\r\n]

// El següent codi es copiarà també, dins de la classe. És a dir, si es posa res
// ha de ser en el format adient: mètodes, atributs, etc.
%{

    private ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();

    /**
     Construcció d'un symbol sense atribut associat.
     **/
    private Symbol symbol(int type) {
        Location left = new Location(yyline+1, yycolumn+1-yylength());
        Location right = new Location(yyline+1, yycolumn+1);
        return symbolFactory.newSymbol(ParserSym.terminalNames[type], type, left, right);
    }

    /**
     Construcció d'un symbol amb un atribut associat.
     public ComplexSymbol(String name, int id, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right, Object value)
     **/
    private Symbol symbol(int type, Object value) {
        Location left = new Location(yyline+1, yycolumn+1-yylength());
        Location right = new Location(yyline+1, yycolumn+1);
        return symbolFactory.newSymbol(ParserSym.terminalNames[type], type, left, right, value);
    }

    /**
     * Metodo que devuelve el numero de lineas que lleva leidas desde el inicio
     */
    public int getRow() { return this.yyline + 1; }

    /**
     * Metodo que devuelve la posicion en la linea del token
     */
    public int getCol() { return this.yycolumn + 1; }
%}

%eofval{
    return symbol(ParserSym.EOF);
%eofval}

%%

/* Reglas y acciones */

//Comentario de una linea
"//" {input}*{eol}? { /* Ignorar comentarios */ }

// Operadores
"+" { return symbol(ParserSym.PLUS, this.yytext()); }
"-" { return symbol(ParserSym.MINUS, this.yytext()); }
"*" { return symbol(ParserSym.MULTI, this.yytext()); }
"/" { return symbol(ParserSym.DIV, this.yytext()); }
"=" { return symbol(ParserSym.ASSIGN, this.yytext()); }

// Delimitadores
"(" { return symbol(ParserSym.LPAREN); }
")" { return symbol(ParserSym.RPAREN); }
"{" { return symbol(ParserSym.LBRACKET); }
"}" { return symbol(ParserSym.RBRACKET); }
":" { return symbol(ParserSym.COLON); }
";" { return symbol(ParserSym.SEMICOLON); }
"," { return symbol(ParserSym.COMMA); }

// Operadores binarios
"and" { return symbol(ParserSym.AND); }
"or"  { return symbol(ParserSym.OR); }
"!"   { return symbol(ParserSym.NOT); }

// Operadores relacionales
"==" { return symbol(ParserSym.EQU); }
"!=" { return symbol(ParserSym.NOTEQU); }
"<"  { return symbol(ParserSym.LESS); }
"<=" { return symbol(ParserSym.LESSEQU);}
"=>" { return symbol(ParserSym.GREATEREQU); }
">"  { return symbol(ParserSym.GREATER); }

// Valores booleanos
"true"  { return symbol(ParserSym.TRUE); }
"false" { return symbol(ParserSym.FALSE); }

// Tipos
"Int"     { return symbol(ParserSym.INT); }
"String"  { return symbol(ParserSym.STRING); }
"Boolean" { return symbol(ParserSym.BOOLEAN); }
"None"    { return symbol(ParserSym.NONE); }

// Bucle y condicional
"while" { return symbol(ParserSym.WHILE); }
"if"    { return symbol(ParserSym.IF); }
"else"  { return symbol(ParserSym.ELSE); }

// Variables y constantes
"var" { return symbol(ParserSym.VAR); }
"val" { return symbol(ParserSym.VAL); }

// Funciones y metodos
"fun"    { return symbol(ParserSym.FUN); }
"return" { return symbol(ParserSym.RETURN); }

// Identificador
{letter}({letter}|{digit}|_)* { return symbol(ParserSym.ID, this.yytext()); }

// Integer
{digit}+ { return symbol(ParserSym.NUM, this.yytext()); }

// Strings
{strings} { return symbol(ParserSym.LITERAL, this.yytext()); }

// Reglas especiales
{whites} { /* no hacer nada */ }
{eol}    { /* no hacer nada */ }

/* Error fallback */
[^] { Output.writeError("Lexic error: Unexpected character in input: '" + yytext() + "' at line " + (yyline+1) + " column " + (yycolumn+1)); }