; ------------------------------------
; Código ensamblador en NASM para Linux 32 bits (i386)
;
; Requisitos en Ubuntu 18.04:
;   sudo apt install build-essential gcc-multilib
;
; Comando para compilar:
;   nasm -f elf assembly_output.asm && gcc -m32 assembly_output.o -o assembly_output
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
    t#2 dd 25
    t#1 dd 0
    t#4 dd 0
    t#3 dd 10
    t#6 dd 1
    t#5 db "Iteracion %d", 10, 0
    num1 dd 0
    t#7 dd 0
    num2 dd 0

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
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: loop_start_1
loop_start_1: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción LTEQU
    ; Op1: num1    Op2: t#3    Dest: t#4
    mov eax, [num1]
    cmp eax, [t#3]
    setle [t#4]

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#4    Op2:     Dest: loop_end_1
    mov eax, [t#4]
    mov ebx, 1
    cmp eax, ebx
    jne loop_end_1

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINTINT
    ; Op1: t#5    Op2: num1    Dest: 
    mov ebx, [num1]
    push ebx
    mov eax, t#5
    push eax
    call printf
    pop eax
    pop ebx

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: num1    Op2: t#6    Dest: t#7
    mov eax, [num1]
    mov ebx, [t#6]
    add eax, ebx
    mov [t#7], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#7    Op2:     Dest: num1
    mov eax, [t#7]
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
    ; exit(0)
    mov ebx, 0
    mov eax, 1
    int 0x80
