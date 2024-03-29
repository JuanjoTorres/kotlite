package compiler.intermediate;

public enum Operation {
    ADD, SUB, PROD, DIV, NEG,           // Operaciones aritmeticas
    COPY,                               // Operacion de asignacion
    EQU, NOTEQU, GT, LT, GTEQU, LTEQU,  // Operaciones relacionales
    AND, OR, NOT,
    SKIP, GOTO,                         // Operaciones de saltos incondicionales
    IFGOTO,                             // Operaciones de saltos condicionales
    PMB, CALL, PARAM, RTN               // Operaciones de subprogramas
}