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
    t#2 dd 0
    t#1 db "Estoy en la subrutina", 10, 0

section .text

main:

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#escribir
    jmp fun#main
fun#escribir: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINT
    ; Op1: t#1    Op2:     Dest: 
    mov eax, t#1
    push eax
    call printf
    pop eax

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#main
fun#main: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción CALL
    ; Op1: fun#escribir    Op2:     Dest: t#2
    call t#2
    mov ebx, 8
    add ebx, esp
    mov eax, [esp+8]
    mov [t#2], eax

    ; ===== ===== ===== ===== =====
    ; exit(0)
    mov ebx, 0
    mov eax, 1
    int 0x80
