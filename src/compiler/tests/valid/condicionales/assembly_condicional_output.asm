; ------------------------------------
; Código ensamblador en NASM para Linux 32 bits (i386)
;
; Requisitos en Ubuntu 18.04:
;   sudo apt install build-essential gcc-multilib
;
; Comando para compilar:
;   nasm -felf64 assembly_output.asm && gcc -m32 assembly_output.o -o assembly_output
;
; Comando para ejecutar:
;   ./assembly_output
;
global main

extern printf

; Sección de memoria para las variables no inicializadas
section .bss
    mensaje resb 512

; Sección de memoria para las variables inicializadas
section .data
    t#2 dd 0
    temp dd 0
    t#1 dd 0
    t#4 db "", 10, 0
    haceSol dd 0
    t#6 dd 0
    t#5 dd 0
    t#8 db "Hace calor", 10, 0
    t#7 db "Hace frío", 10, 0
    llueve dd 0
    t#9 dd 0
    t#10 dd 0

section .text

main:

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: false    Op2:     Dest: t#1

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#1    Op2:     Dest: llueve

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: true    Op2:     Dest: t#2

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#2    Op2:     Dest: haceSol

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#comprobarTemperatura
    fun#comprobarTemperatura: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: ""    Op2:     Dest: t#4

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#4    Op2:     Dest: mensaje

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: 15    Op2:     Dest: t#5

    ; ===== ===== ===== ===== =====
    ; Instrucción LTEQU
    ; Op1: t#3    Op2: t#5    Dest: t#6

    ; ===== ===== ===== ===== =====
    ; Instrucción IFGOTO
    ; Op1: t#6    Op2:     Dest: cond_false_1

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: "Hace frío"    Op2:     Dest: t#7

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#7    Op2:     Dest: mensaje

    ; ===== ===== ===== ===== =====
    ; Instrucción GOTO
    ; Op1:     Op2:     Dest: cond_true_1

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_false_1
    cond_false_1: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: "Hace calor"    Op2:     Dest: t#8

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#8    Op2:     Dest: mensaje

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: cond_true_1
    cond_true_1: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINT
    ; Op1: mensaje    Op2:     Dest: 
    mov eax, [mensaje]
    push eax
    call printf
    pop eax

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#main

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: 40    Op2:     Dest: t#9

    ; ===== ===== ===== ===== =====
    ; Instrucción PARAM
    ; Op1:     Op2:     Dest: t#9

    ; ===== ===== ===== ===== =====
    ; Instrucción CALL
    ; Op1: fun#comprobarTemperatura    Op2:     Dest: t#10

    ; ===== ===== ===== ===== =====
    ; exit(0)
    mov ebx, 0
    mov eax, 1
    int 0x80
