package compiler.intermediate;

public enum Operation {
    ADD, SUBS, PROD, DIV,
    ASSIG,
    EQU, NOTEQU, GT, LT, GTEQU, LTQUE,
    NEG, AND, OR, NOT,
    SKIP, GOTO, IFGOTO,
    END, CALL, RETURN
}