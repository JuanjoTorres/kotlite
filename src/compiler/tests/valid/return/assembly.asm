; ----------------------------------------------------
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
; ----------------------------------------------------
global main

extern printf

; Sección de memoria para las variables no inicializadas
section .bss

; Sección de memoria para las variables inicializadas
section .data
    t#2 dd 0
    t#1 dd 5
    numero dd 0
    t#3 db "El numero es: %d", 10, 0

section .text

main:

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#get
    jmp fun#main
fun#get: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción PMB
    ; Op1:     Op2:     Dest: fun#get

    ; ===== ===== ===== ===== =====
    ; Instrucción RTN
    ; Op1: get    Op2:     Dest: t#1
    mov eax, 222222
    mov [esp+4], eax
    ret 0

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#main
fun#main: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción PMB
    ; Op1:     Op2:     Dest: fun#main

    ; ===== ===== ===== ===== =====
    ; Instrucción CALL
    ; Op1: fun#get    Op2:     Dest: t#2
    sub esp, 4
    call fun#get
    pop eax
    mov [t#2], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#2    Op2:     Dest: numero
    mov eax, t#2
    mov [numero], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINTINT
    ; Op1: t#3    Op2: numero    Dest:
    mov ebx, [numero]
    push ebx
    mov eax, t#3
    push eax
    call printf
    pop eax
    pop ebx

    ; ===== ===== ===== ===== =====
    ; exit(0)
    mov ebx, 0
    mov eax, 1
    int 0x80
