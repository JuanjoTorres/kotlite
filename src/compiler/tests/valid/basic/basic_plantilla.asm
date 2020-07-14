global main
extern _printf, _gets
section .bss
DISP resb 1000
section .text
; ===== ===== ===== ===== =====
; Instrucción SKIP
; Op1:  Op2:  Dest: fun_sumar_1


; ===== ===== ===== ===== =====
; Instrucción PLUS
; Op1: t1 Op2: t2 Dest: t3


; ===== ===== ===== ===== =====
; Instrucción COPY
; Op1: t3 Op2:  Dest: res


; ===== ===== ===== ===== =====
; Instrucción RTN
; Op1:  Op2:  Dest: res


; ===== ===== ===== ===== =====
; Instrucción SKIP
; Op1:  Op2:  Dest: fun_main_2


; ===== ===== ===== ===== =====
; Instrucción COPY
; Op1: 1 Op2:  Dest: t4


; ===== ===== ===== ===== =====
; Instrucción COPY
; Op1: t4 Op2:  Dest: globalA


; ===== ===== ===== ===== =====
; Instrucción COPY
; Op1: 2 Op2:  Dest: t5


; ===== ===== ===== ===== =====
; Instrucción COPY
; Op1: t5 Op2:  Dest: globalB


; ===== ===== ===== ===== =====
; Instrucción PARAM
; Op1:  Op2:  Dest: globalA


; ===== ===== ===== ===== =====
; Instrucción PARAM
; Op1:  Op2:  Dest: globalB


; ===== ===== ===== ===== =====
; Instrucción CALL
; Op1: fun_sumar_1 Op2:  Dest: t6


; ===== ===== ===== ===== =====
; Instrucción COPY
; Op1: t6 Op2:  Dest: globalC


; ===== ===== ===== ===== =====
; Instrucción COPY
; Op1: "La suma es %d" Op2:  Dest: t7


; ===== ===== ===== ===== =====
; Instrucción COPY
; Op1: t7 Op2:  Dest: comentario


; ===== ===== ===== ===== =====
; Instrucción PRINTINT
; Op1: comentario Op2: globalC Dest: 


