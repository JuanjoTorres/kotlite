; ------------------------------------
; Código ensamblador en NASM para Linux 32 bits (i386)
;
; Requisitos en Ubuntu 18.04:
;   sudo apt install build-essential gcc-multilib
;
; Comando para compilar:
;   nasm -f elf assembly.asm && gcc -m32 assembly_output.o -o assembly_output
;
; Comando para ejecutar:
;   ./assembly_output
;
global main

extern printf

; Sección de memoria para las variables no inicializadas
section .bss

; Sección de memoria para las variables inicializadas
section .data
    t#28 dd 1
    t#27 db "mayorque %d", 10, 0
    t#29 dd 0
    t#24 db "Entro dentro del mayorque", 10, 0
    t#23 dd 0
    t#26 dd 0
    t#25 dd 0
    t#20 dd 1
    t#22 dd 0
    t#21 dd 0
    t#17 dd 10
    t#16 db "Entro dentro del menorigualque", 10, 0
    t#19 db "menorigualque %d", 10, 0
    t#18 dd 0
    t#13 dd 0
    t#12 dd 1
    t#15 dd 0
    t#14 dd 10
    t#11 db "menorque %d", 10, 0
    t#10 dd 0
    t#45 dd 0
    num1 dd 0
    t#42 dd 0
    t#41 dd 10
    t#44 dd 1
    t#43 db "noigual %d", 10, 0
    num5 dd 0
    num4 dd 0
    num3 dd 0
    t#40 db "Entro dentro del igual", 10, 0
    num2 dd 0
    t#39 dd 0
    t#38 dd 0
    t#2 dd 0
    t#1 dd 0
    t#4 dd 10
    t#35 db "mayorigualque %d", 10, 0
    t#3 dd 10
    t#34 dd 0
    t#6 dd 10
    t#37 dd 0
    t#5 dd 0
    t#36 dd 1
    t#8 db "Entro dentro del menorque", 10, 0
    t#7 dd 0
    t#9 dd 10
    t#31 dd 0
    t#30 dd 0
    t#33 dd 0
    t#32 db "Entro dentro del mayorigualque", 10, 0

section .text


    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#main
main:

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#1    Op2:     Dest: num1
    mov eax, [t#1]
    mov [num1], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#2    Op2:     Dest: num2
    mov eax, [t#2]
    mov [num2], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#3    Op2:     Dest: num3
    mov eax, [t#3]
    mov [num3], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#4    Op2:     Dest: num4
    mov eax, [t#4]
    mov [num4], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#5    Op2:     Dest: num5
    mov eax, [t#5]
    mov [num5], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción LT
    ; Op1: num1    Op2: t#6    Dest: t#7
    mov eax, [num1]
    cmp eax, [t#6]
    setl [t#7]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#7    Op2:     Dest: cond_false_1
    mov eax, [t#7]
    mov ebx, 1
    cmp eax, ebx
    jne cond_false_1

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINT
    ; Op1: t#8    Op2:     Dest: 
    mov eax, t#8
    push eax
    call printf
    pop eax

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: cond_true_1
    jmp cond_true_1

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_false_1
cond_false_1: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_true_1
cond_true_1: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_start_1
loop_start_1: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción LT
    ; Op1: num1    Op2: t#9    Dest: t#10
    mov eax, [num1]
    cmp eax, [t#9]
    setl [t#10]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#10    Op2:     Dest: loop_end_1
    mov eax, [t#10]
    mov ebx, 1
    cmp eax, ebx
    jne loop_end_1

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINTINT
    ; Op1: t#11    Op2: num1    Dest: 
    mov ebx, [num1]
    push ebx
    mov eax, t#11
    push eax
    call printf
    pop eax
    pop ebx

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: num1    Op2: t#12    Dest: t#13
    mov eax, [num1]
    mov ebx, [t#12]
    add eax, ebx
    mov [t#13], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#13    Op2:     Dest: num1
    mov eax, [t#13]
    mov [num1], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: loop_start_1
    jmp loop_start_1

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_end_1
loop_end_1: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción LTEQU
    ; Op1: num2    Op2: t#14    Dest: t#15
    mov eax, [num2]
    cmp eax, [t#14]
    setle [t#15]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#15    Op2:     Dest: cond_false_2
    mov eax, [t#15]
    mov ebx, 1
    cmp eax, ebx
    jne cond_false_2

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINT
    ; Op1: t#16    Op2:     Dest: 
    mov eax, t#16
    push eax
    call printf
    pop eax

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: cond_true_2
    jmp cond_true_2

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_false_2
cond_false_2: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_true_2
cond_true_2: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_start_2
loop_start_2: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción LTEQU
    ; Op1: num2    Op2: t#17    Dest: t#18
    mov eax, [num2]
    cmp eax, [t#17]
    setle [t#18]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#18    Op2:     Dest: loop_end_2
    mov eax, [t#18]
    mov ebx, 1
    cmp eax, ebx
    jne loop_end_2

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINTINT
    ; Op1: t#19    Op2: num2    Dest: 
    mov ebx, [num2]
    push ebx
    mov eax, t#19
    push eax
    call printf
    pop eax
    pop ebx

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: num2    Op2: t#20    Dest: t#21
    mov eax, [num2]
    mov ebx, [t#20]
    add eax, ebx
    mov [t#21], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#21    Op2:     Dest: num2
    mov eax, [t#21]
    mov [num2], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: loop_start_2
    jmp loop_start_2

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_end_2
loop_end_2: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción GT
    ; Op1: num3    Op2: t#22    Dest: t#23
    mov eax, [num3]
    cmp eax, [t#22]
    setg [t#23]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#23    Op2:     Dest: cond_false_3
    mov eax, [t#23]
    mov ebx, 1
    cmp eax, ebx
    jne cond_false_3

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINT
    ; Op1: t#24    Op2:     Dest: 
    mov eax, t#24
    push eax
    call printf
    pop eax

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: cond_true_3
    jmp cond_true_3

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_false_3
cond_false_3: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_true_3
cond_true_3: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_start_3
loop_start_3: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción GT
    ; Op1: num3    Op2: t#25    Dest: t#26
    mov eax, [num3]
    cmp eax, [t#25]
    setg [t#26]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#26    Op2:     Dest: loop_end_3
    mov eax, [t#26]
    mov ebx, 1
    cmp eax, ebx
    jne loop_end_3

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINTINT
    ; Op1: t#27    Op2: num3    Dest: 
    mov ebx, [num3]
    push ebx
    mov eax, t#27
    push eax
    call printf
    pop eax
    pop ebx

    ; ===== ===== ===== ===== =====
    ; Instrucción MINUS
    ; Op1: num3    Op2: t#28    Dest: t#29
    mov eax, [num3]
    mov ebx, [t#28]
    sub eax, ebx
    mov [t#29], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#29    Op2:     Dest: num3
    mov eax, [t#29]
    mov [num3], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: loop_start_3
    jmp loop_start_3

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_end_3
loop_end_3: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción GTEQU
    ; Op1: num4    Op2: t#30    Dest: t#31
    mov eax, [num4]
    cmp eax, [t#30]
    setge [t#31]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#31    Op2:     Dest: cond_false_4
    mov eax, [t#31]
    mov ebx, 1
    cmp eax, ebx
    jne cond_false_4

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINT
    ; Op1: t#32    Op2:     Dest: 
    mov eax, t#32
    push eax
    call printf
    pop eax

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: cond_true_4
    jmp cond_true_4

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_false_4
cond_false_4: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_true_4
cond_true_4: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_start_4
loop_start_4: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción GTEQU
    ; Op1: num4    Op2: t#33    Dest: t#34
    mov eax, [num4]
    cmp eax, [t#33]
    setge [t#34]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#34    Op2:     Dest: loop_end_4
    mov eax, [t#34]
    mov ebx, 1
    cmp eax, ebx
    jne loop_end_4

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINTINT
    ; Op1: t#35    Op2: num4    Dest: 
    mov ebx, [num4]
    push ebx
    mov eax, t#35
    push eax
    call printf
    pop eax
    pop ebx

    ; ===== ===== ===== ===== =====
    ; Instrucción MINUS
    ; Op1: num4    Op2: t#36    Dest: t#37
    mov eax, [num4]
    mov ebx, [t#36]
    sub eax, ebx
    mov [t#37], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#37    Op2:     Dest: num4
    mov eax, [t#37]
    mov [num4], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: loop_start_4
    jmp loop_start_4

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_end_4
loop_end_4: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción EQU
    ; Op1: num5    Op2: t#38    Dest: t#39
    mov eax, [num5]
    cmp eax, [t#38]
    sete [t#39]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#39    Op2:     Dest: cond_false_5
    mov eax, [t#39]
    mov ebx, 1
    cmp eax, ebx
    jne cond_false_5

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINT
    ; Op1: t#40    Op2:     Dest: 
    mov eax, t#40
    push eax
    call printf
    pop eax

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: cond_true_5
    jmp cond_true_5

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_false_5
cond_false_5: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_true_5
cond_true_5: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_start_5
loop_start_5: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción NOTEQU
    ; Op1: num5    Op2: t#41    Dest: t#42
    mov eax, [num5]
    cmp eax, [t#41]
    setne [t#42]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#42    Op2:     Dest: loop_end_5
    mov eax, [t#42]
    mov ebx, 1
    cmp eax, ebx
    jne loop_end_5

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINTINT
    ; Op1: t#43    Op2: num5    Dest: 
    mov ebx, [num5]
    push ebx
    mov eax, t#43
    push eax
    call printf
    pop eax
    pop ebx

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: num5    Op2: t#44    Dest: t#45
    mov eax, [num5]
    mov ebx, [t#44]
    add eax, ebx
    mov [t#45], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#45    Op2:     Dest: num5
    mov eax, [t#45]
    mov [num5], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: loop_start_5
    jmp loop_start_5

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_end_5
loop_end_5: nop

    ; ===== ===== ===== ===== =====
    ; exit(0)
    mov ebx, 0
    mov eax, 1
    int 0x80
