# Para comprobar la gramatica en Grammophone:
# http://mdaines.github.io/grammophone/

PROGRAM -> DECLS FUNCTIONS .
FUNCTIONS -> FUNCTIONS FUNCTION .
FUNCTIONS -> .
FUNCTION  -> fun id AUP ( ARGSDEC ) : BASIC { DECLS STATMENTS RTNPART ADOWN } .
FUNCTIONCALL -> id ( ARGS ) .
RTNPART -> return FACTOR .
RTNPART -> .
DECLS -> DECLS DECL .
DECLS -> .
DECL -> TYPE id : BASIC ; .
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
STATMENT -> id = BOOL ; .
STATMENT -> if ( BOOL ) { AUP DECLS STATMENTS ADOWN } ELSEPART .
STATMENT -> while ( BOOL ) { AUP DECLS STATMENTS ADOWN } .
# Llamada a funcion recogiendo el retorno
STATMENT -> id = FUNCTIONCALL ; .
# Llamada a funcion sin recoger el retorno
STATMENT -> FUNCTIONCALL ; .

ELSEPART -> else { AUP DECLS STATMENTS ADOWN } .
ELSEPART -> .
BOOL -> BOOL JOIN RELATION .
BOOL -> RELATION .
RELATION -> EXPR OPREL EXPR .
RELATION -> EXPR .
EXPR -> EXPR ADD TERM .
EXPR -> TERM .
TERM -> TERM MULT UNARY .
TERM -> UNARY .
UNARY -> ! UNARY .
UNARY -> FACTOR .
ADD	-> + .
ADD -> - .
MULT -> * .
MULT -> / .
JOIN -> or .
JOIN -> and .
OPREL -> == .
OPREL -> != .
OPREL -> < .
OPREL -> <= .
OPREL -> > .
OPREL -> >= .
TYPE -> var .
TYPE -> val .
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
ID -> id .

AUP -> .
ADOWN -> .