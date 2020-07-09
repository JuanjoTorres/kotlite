package compiler.codegeneration;

public enum Operation {
    ADD, SUB, PROD, DIV, NEG,           // Operaciones aritmeticas
    COPY,                               // Operacion de asignacion
    EQU, NOTEQU, GT, LT, GTEQU, LTQUE,  // Operaciones relacionales
    AND, OR, NOT,
    SKIP, GOTO,                         // Operaciones de saltos incondicionales
    IFEQ, IFNE, IFGT, IFLT, IFGE, IFLE, // Operaciones de saltos condicionales
    PMB, CALL, PARAM, RTN               // Operaciones de subprogramas
}