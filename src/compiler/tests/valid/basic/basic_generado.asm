global main
extern printf, gets
section .data
    globalA dd 0
    globalB dd 0
    globalC dd 0
    num1    dd 0
    num2    dd 0
    res     dd 0
    t1      dd 0
    t2      dd 0
    t3      dd 0
    t4      dd 0
    t5      dd 'La suma es %d', 10, 0
section .bbs
    DISP       resb 1000
    comentario resb 512
section .text
    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:  Op2:  Dest: fun_sumar_4
    fun_sumar_4: np
        ; ===== ===== ===== ===== =====
        ; Instrucción PMB
        ; Op1:  Op2:  Dest:
        mov esi, [4 + DISP]
        push esi
        push ebp
        mov ebp, esp
        mov [4 + ebp], ebp
        sub esp, 4
        mov eax, [12 + ebp]
        mov [num1], eax
        mov eax, [16 + ebp]
        mov [num2], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción PLUS
        ; Op1: t1 Op2: t2 Dest: t3
        mov eax, [num1]
        mov ebx, [num2]
        add eax, ebx
        mov [t3], eax