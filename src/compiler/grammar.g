# http://mdaines.github.io/grammophone/

# Para comprobar la gramatica en Grammophone,
# en la producción JOIN hay que sustituir el OR por otro símbolo:

PROGRAM -> DECLS FUNCTIONS .
FUNCTIONS -> FUNCTIONS FUNCTION .
FUNCTIONS -> .
FUNCTION  -> fun id ( ARGSDEC ) : BASIC { AUP DECLS STATMENTS RTNPART ADOWN } .
FUNCTIONCALL -> id ( ARGS ) .
RTNPART -> return FACTOR .
RTNPART -> .
DECLS -> DECLS DECL .
DECLS -> .
DECL -> var id : BASIC ; .
DECL -> val id : BASIC = BOOL ; .

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