
// El codi que es copiarà tal qual al document. A l'inici
package compiler.lexic;

import compiler.syntax.ParserSym;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

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
    /**
     Construcció d'un symbol sense atribut associat.
     **/
    private ComplexSymbol symbol(int type) {
        return new ComplexSymbol(ParserSym.terminalNames[type], type);
    }

    /**
     Construcció d'un symbol amb un atribut associat.
     **/
    private Symbol symbol(int type, Object value) {
        return new ComplexSymbol(ParserSym.terminalNames[type], type, value);
    }


    /**
     * Metodo que devuelve el numero de lineas que lleva leidas desde el inicio
     */
    public int getRow() { return this.yyline; }

    /**
     * Metodo que devuelve la posicion en la linea del token
     */
    public int getCol() { return this.yycolumn; }

    /**
     * Metodo para emitir errores
     */
    public void emit_error (String message) {
        System.out.println("Scanner error: " + message + " at " + (yyline + 1)
            + ":" + (yycolumn + 1) + " " + yychar);
    }

    /**
     * Metodo para emitir warnings
     */
    public void emit_warning (String message) {
        System.out.println("Scanner warning: " + message + " at " + (yyline + 1)
            + ":" + (yycolumn + 1) + " " + yychar);
    }
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
"&&" { return symbol(ParserSym.AND); }
"||" { return symbol(ParserSym.OR); }
"!"  { return symbol(ParserSym.NOT); }

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
\"(\\.|[^\"])*\" { return symbol(ParserSym.LITERAL, this.yytext()); }
\".              { emit_warning ("Uncompleted string '" + yytext() + "' -- ignored"); }

// Reglas especiales
{whites} { /* no hacer nada */ }
{eol}    { /* no hacer nada */ }

/* Error fallback */
[^] { emit_warning ("Unrecognized character '" + yytext() + "' -- ignored"); }