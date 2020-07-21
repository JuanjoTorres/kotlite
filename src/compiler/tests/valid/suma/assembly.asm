; ----------------------------------------------------
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
; ----------------------------------------------------
global main

extern printf

; Sección de memoria para las variables no inicializadas
section .bss

; Sección de memoria para las variables inicializadas
section .data
    t#2 dd 0
    t#1 dd 0
    t#4 dd 0
    t#3 dd 0
    t#5 db "La suma es %d", 10, 0
    num1 dd 0
    global$global dd 0
    num2 dd 0

section .text

main:

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#sumar
    jmp fun#main
fun#sumar: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción PMB
    ; Op1:     Op2:     Dest: fun#sumar
    push ebp
    mov ebp, esp
    push eax
    push ebx
    mov eax, [ebp+16]
    mov [num1], eax
    mov eax, [ebp+12]
    mov [num2], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: num1    Op2: num2    Dest: t#1
    mov eax, [num1]
    mov ebx, [num2]
    add eax, ebx
    mov [t#1], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción RTN
    ; Op1: sumar    Op2:     Dest: t#1
    mov eax, [t#1]
    mov [ebp+8], eax
    pop ebx
    pop eax
    pop ebp
    ret

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#main
fun#main: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción PMB
    ; Op1:     Op2:     Dest: fun#main
    ; Ignorar preambulo de main

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 3    Op2:     Dest: t#2
    mov eax, 3
    mov [t#2], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: -5    Op2:     Dest: t#3
    mov eax, -5
    mov [t#3], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PARAM
    ; Op1:     Op2:     Dest: t#2
    mov eax, [t#2]
    push eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PARAM
    ; Op1:     Op2:     Dest: t#3
    mov eax, [t#3]
    push eax

    ; ===== ===== ===== ===== =====
    ; Instrucción CALL
    ; Op1: fun#sumar    Op2:     Dest: t#4
    sub esp, 4
    call fun#sumar
    pop eax
    mov [t#4], eax
    add esp, 8

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#4    Op2:     Dest: global
    mov eax, [t#4]
    mov [global$global], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: "La suma es %d"    Op2:     Dest: t#5
    ; Copy de String literal se ignora, ya está en memoria

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINTINT
    ; Op1: t#5    Op2: global    Dest: 
    mov ebx, [global$global]
    push ebx
    mov eax, t#5
    push eax
    call printf
    pop eax
    pop ebx

    ; ===== ===== ===== ===== =====
    ; exit(0)
    mov ebx, 0
    mov eax, 1
    int 0x80
