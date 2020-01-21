/**
Per poder compilar aquest fitxer s'ha d'haver instal·lat JFlex
 **/

/**
 * Assignatura 21742 - Compiladors I
 * Estudis: Grau en Informàtica
 * Itinerari: Computació
 * Curs: 2017-2018
 *
 * Professor: Pere Palmer
 */
// El codi que es copiarà tal qual al document. A l'inici
package compiler.lexic;

import compiler.sintax.ParserSym;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;


%%

/****
 Indicación de que tipo de analizador sintáctico se utilizara, en este caso se
 usa CUP (LALR)
 ****/
%cup
%public// Para indicar si la clase es publica
%class Scanner // Nombre de la clase
%unicode
%line
%column

%eofval{
    return symbol(ParserSym.EOF);
%eofval}

// El següent codi es copiarà també, dins de la classe. És a dir, si es posa res
// ha de ser en el format adient: mètodes, atributs, etc.
%{
/***
 Mecanismes de gestió de símbols basat en ComplexSymbol. Tot i que en
 aquest cas potser no és del tot necessari.
 ***/
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
%}



// Definiciones

digit       = [0-9]
letter      = [a-zA-Z]

white       = [ \t]+
eol         = [\r\n]+

%%


/* Regles/accions */

//Operadores
"+" { return symbol(ParserSym.PLUS); }
"-" { return symbol(ParserSym.MINUS); }
"*" { return symbol(ParserSym.MULTI); }
"/" { return symbol(ParserSym.DIV); }
"=" { return symbol(ParserSym.ASSIGN); }

//Delimitadores
"(" { return symbol(ParserSym.LPAREN); }
")" { return symbol(ParserSym.RPAREN); }
"{" { return symbol(ParserSym.LBRACKET); }
"}" { return symbol(ParserSym.RBRACKET); }
":" { return symbol(ParserSym.COLON); }
";" { return symbol(ParserSym.SEMICOLON); }
"," { return symbol(ParserSym.COMMA); }

//Operadores binarios
"&&" { return symbol(ParserSym.AND); }
"||" { return symbol(ParserSym.OR); }
"!" { return symbol(ParserSym.NOT); }

//Operadores relacionales
"==" { return symbol(ParserSym.EQUALS); }
"!=" { return symbol(ParserSym.NOTEQUALS); }
"<" { return symbol(ParserSym.LESSTHAN); }
"<=" { return symbol(ParserSym.LESSOREQUALS); }
"=>" { return symbol(ParserSym.GREATEROREQUALS); }
">" { return symbol(ParserSym.GREATERTHAN); }

//Valores booleanos
"true" { return symbol(ParserSym.TRUE); }
"false" { return symbol(ParserSym.FALSE); }

//Tipos
"Int" { return symbol(ParserSym.INT); }
"String" { return symbol(ParserSym.STRING); }
"Boolean" { return symbol(ParserSym.BOOLEAN); }
"None" { return symbol(ParserSym.NONE); }

//Bucle y condicional
"while" { return symbol(ParserSym.WHILE); }
"if" { return symbol(ParserSym.IF); }
"else" { return symbol(ParserSym.ELSE); }

//Variables y constantes
"var" { return symbol(ParserSym.VAR); }
"val" { return symbol(ParserSym.VAL); }

//Return
"return" { return symbol(ParserSym.RETURN); }

//Identificador
{letter}({letter}|{digit}|_)* { return symbol(ParserSym.ID, yytext()); }

//Integer
{digit}+ { return symbol(ParserSym.INT, yytext()); }


{white} { /* no fer res */ }
{eol} { return symbol(ParserSym.EOF); }


/* error fallback */
[^] { return symbol(ParserSym.ERROR);}