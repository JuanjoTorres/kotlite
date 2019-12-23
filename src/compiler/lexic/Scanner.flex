/**
  Per poder compilar aquest fitxer s'ha d'haver instal·lat JFlex
 **/

// Cabecera del documento
package jlex_cup_example.compiler_components.jflex;

import java.io.*;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import jlex_cup_example.compiler_components.cup.ParserSym;

%%

/****
 Indicación de que tipo de analizador sintáctico se utilizara, en este caso se
 usa CUP (LALR)
 ****/
%cup
%public      // Para indicar si la clase es publica
%class Lexer // Nombre de la clase
%unicode

%line
%column

%eofval{
  return symbol(ParserSym.EOF); // CREAR UN SIMBOLO END_OF_FILE
%eofval}

alpha = [a-zA-Z_]
digit = [0-9]
alnum = {alpha}|{digit}
print = [ -~]

ID        = {alpha}+{alnum}*
INT_LIT   = [+-]?{digit}*
FLOAT_LIT = {digit}*.{digit}+
CHAR_LIT  = (\'{print}\')|(\'\[nftrbv]\')
STRING    = \"{print}*\"
%%
"//".*              { printf("Eat up comment at line %d\n", lineno); }

"/*"                { printf("Eat up comment from line %d ", lineno);
                      BEGIN(ML_COMMENT); }
<ML_COMMENT>"*/"    { printf("to line %d\n", lineno); BEGIN(INITIAL); }
<ML_COMMENT>[^*\n]+
<ML_COMMENT>"*"
<ML_COMMENT>"\n"    { lineno += 1; }

"Char"              { ret_print("KEYWORD_CHAR");   }
"Int"               { ret_print("KEYWORD_INT");    }
"Float"             { ret_print("KEYWORD_FLOAT");  }
"String"            { ret_print("KEYWORD_STRING"); }
"Boolean"           { ret_print("KEYWORD_BOOL");   }
"None"              { ret_print("KEYWORD_VOID");   }
"var"               { ret_print("KEYWORD_VAR");    }
"val"               { ret_print("KEYWORD_CONST");  }

"if"                { ret_print("KEYWORD_IF");       }
"else"              { ret_print("KEYWORD_ELSE");     }
"while"             { ret_print("KEYWORD_WHILE");    }
"fun"               { ret_print("KEYWORD_FUNCTION"); }
"return"            { ret_print("KEYWORD_RETURN");   }
"True"              { ret_print("KEYWORD_TRUE");     }
"False"             { ret_print("KEYWORD_FALSE");    }

"+"|"-"             { ret_print("ADD_OP"); }
"*"                 { ret_print("MUL_OP"); }
"/"                 { ret_print("DIV_OP"); }
"||"                { ret_print("OR_OP");  }
"&&"                { ret_print("AND_OP"); }
"!"                 { ret_print("NOT_OP"); }
"=="|"!="           { ret_print("EQU_OP"); }
">"|"<"|">="|"<="   { ret_print("REL_OP"); }

"("                 { ret_print("LPAREN"); }
")"                 { ret_print("RPAREN"); }
"{"                 { ret_print("LBRACE"); }
"}"                 { ret_print("RBRACE"); }
";"                 { ret_print("SEMI");   }
","                 { ret_print("COMMA");  }
"="                 { ret_print("ASSIGN"); }
":"                 { ret_print("COLON");  }

{ID}                { ret_print("ID");           }
{INT_LIT}           { ret_print("VALUE_INT");    }
{FLOAT_LIT}         { ret_print("VALUE_FLOAT");  }
{CHAR_LIT}          { ret_print("VALUE_CHAR");   }
{STRING}            { ret_print("VALUE_STRING"); }

"\n"              { lineno +=1; }
[ \t\r\f]+        /* eat up whitespace */

.                 { yyerror("Unrecognized character"); }


void ret_print(char *token_type) {
    printf("yytext: %s\ttoken: %s\tlineno: %d\n", yytext, token_type, lineno);
}

void yyerror(char *message) {
    printf("Error: \"%s\" in line %d. Token = %s\n", message, lineno, yytext);
    exit(1);
}

int main(int argc, char *argv[]) {

    yyin = fopen(argv[1], "r");
    yylex();
    fclose(yyin);

    return 0;
}
