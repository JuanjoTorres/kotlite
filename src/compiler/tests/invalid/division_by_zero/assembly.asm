; ----------------------------------------------------
; Código ensamblador en NASM para Linux 32 bits (i386)
;
; Requisitos en Ubuntu 18.04 y 20.04:
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
    main$t#3 dd 0
    main$t#2 dd 0
    main$t#5 db "HOlaaa", 10, 0
    main$t#4 db "La division es %d", 10, 0
    global$globalC dd 0
    main$t#1 dd 0
    errorByZero db "KOTLITE - ERROR DE EJECUCION: Operacion de Division por Zero", 10, 0
section .text


handlerErrorByZero: nop
    cmp eax, 0
    jne checked

    mov eax, errorByZero
    push eax
    call printf
    pop eax

    mov ebx, 1
    mov eax, 1
    int 0x80

    checked: nop
    ret

main:

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:     Op2:     Dest: fun#main
    jmp fun#main
fun#main: nop

    ; ===== ===== ===== ===== =====
    ; Instrucción PMB
    ; Op1:     Op2:     Dest: fun#main
    ; Ignorar preambulo de main

       pop ebx
    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 4    Op2:     Dest: main$t#1
    mov eax, 4
    mov [main$t#1], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 0    Op2:     Dest: main$t#2
    mov eax, 0
    mov [main$t#2], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción DIV
    ; Op1: main$t#1    Op2: main$t#2    Dest: main$t#3

    mov eax, [main$t#2]
    call handlerErrorByZero

    mov eax, [main$t#1]
    cdq
    mov ebx, [main$t#2]
    idiv ebx
    mov [main$t#3], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: main$t#3    Op2:     Dest: global$globalC
    mov eax, [main$t#3]
    mov [global$globalC], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: "La division es %d"    Op2:     Dest: main$t#4
    ; Copy de String literal se ignora, ya está en memoria

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINTINT
    ; Op1: main$t#4    Op2: global$globalC    Dest: 
    mov ebx, [global$globalC]
    push ebx
    mov eax, main$t#4
    push eax
    call printf
    pop eax
    pop ebx

    ; ===== ===== ===== ===== =====
    ; exit(0)
    mov ebx, 0
    mov eax, 1
    int 0x80
