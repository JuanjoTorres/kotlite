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

; Sección de memoria para las variables inicializadas
section .data
    t#1 dd 5
    t#3 db "El numero es: ", 10, 0

section .text


    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#get5
fun#get5: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción RTN
    ; Op1: t#1    Op2:     Dest:
    mov esp, ebp
    pop ebp
    mov edi, [4 + DISP]
    pop edi
    ret

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#main
main:

    ; ===== ===== ===== ===== =====
    ; Instrucción CALL
    ; Op1: fun#get5    Op2:     Dest: t#2
    call t#2
    mov ebx, 8
    add ebx, esp
    mov eax, [esp+8]
    mov [t#2], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: t#2    Op2:     Dest: numero
    mov eax, [t#2]
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
