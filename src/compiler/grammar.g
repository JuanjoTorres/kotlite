# http://mdaines.github.io/grammophone/

# Para comprobar la gramatica en Grammophone,
# en la producción JOIN hay que sustituir el OR por otro símbolo:

PROGRAM -> DECLS FUNCTIONS .
FUNCTIONS -> FUNCTIONS FUNCTION .
FUNCTIONS -> .
FUNCTION  -> fun id AUP ( ARGSDEC ) : BASIC { DECLS STATMENTS RTNPART ADOWN } .
#TODO Comprobar argumentos igual a declarados
FUNCTIONCALL -> id ( ARGS ) .
#TODO Comprobar tipo de return con tipo de function
RTNPART -> return FACTOR .
RTNPART -> .
DECLS -> DECLS DECL .
DECLS -> .
DECL -> TYPE id : BASIC ; .
#TODO Comprobar tipo de variable/constante y valor
DECL -> TYPE id : BASIC = BOOL ; .

TYPE -> var .
TYPE -> val .

# Lista de argumentos con tipo para declarar una función
ARGSDEC -> LARGSDEC .
ARGSDEC -> .
LARGSDEC -> LARGSDEC ; id : BASIC .
LARGSDEC -> id : BASIC .

# Lista de argumentos con ids o valores para llamar a una función
ARGS -> LARGS .
ARGS -> .
LARGS -> LARGS , FACTOR .
LARGS -> FACTOR .

STATMENTS -> STATMENTS STATMENT .
STATMENTS -> .
#TODO Comprobar tipos de variable y expresion
STATMENT -> id = BOOL ; .
#TODO Comprobar expresion booleana
STATMENT -> if ( BOOL ) { AUP DECLS STATMENTS ADOWN } ELSEPART .
#TODO Comprobar expresion booleana
STATMENT -> while ( BOOL ) { AUP DECLS STATMENTS ADOWN } .
# Llamada a funcion recogiendo el retorno
#TODO Comprobar tipos de variable y retorno de funcion
STATMENT -> id = FUNCTIONCALL ; .
# Llamada a funcion sin recoger el retorno
STATMENT -> FUNCTIONCALL ; .

ELSEPART -> else { AUP DECLS STATMENTS ADOWN } .
ELSEPART -> .
#TODO Comprobacion de tipos
BOOL -> BOOL JOIN RELATION .
BOOL -> RELATION .
#TODO Comprobacion de tipos comparables
RELATION -> EXPR OPREL EXPR .
RELATION -> EXPR .
#TODO Comprobacion de tipos
EXPR -> EXPR ADD TERM .
EXPR -> TERM .
#TODO Comprobacion de tipo INT
TERM -> TERM MULT UNARY .
TERM -> UNARY .
#TODO Comprobar tipo booleano
UNARY -> ! UNARY .
UNARY -> FACTOR .
ADD	-> + .
ADD -> - .
MULT -> * .
MULT -> / .
# Sustituir [ JOIN -> || ] por [ JOIN -> $$ ]
JOIN -> $$ .
JOIN -> && .
OPREL -> == .
OPREL -> != .
OPREL -> < .
OPREL -> <= .
OPREL -> > .
OPREL -> >= .
BASIC -> Int .
BASIC -> String .
BASIC -> Boolean .
BASIC -> None .
FACTOR -> ( BOOL ) .
FACTOR -> id .
FACTOR -> literal .
FACTOR -> num .
FACTOR -> true .
FACTOR -> false .

AUP -> .
ADOWN -> .