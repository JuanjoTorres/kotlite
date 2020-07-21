; ----------------------------------------------------
; Código ensamblador en NASM para Linux 32 bits (i386)
;
; Requisitos en Ubuntu 18.04 y 20.04:
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
    main$t#18 db "La multiplicacion es %d", 10, 0
    main$t#17 dd 0
    main$t#3 dd 0
    main$t#10 dd 0
    main$t#2 dd 0
    main$t#5 dd 0
    main$t#12 dd 0
    main$t#4 dd 0
    main$t#11 dd 0
    main$t#7 dd 0
    main$t#14 dd 0
    global$globalC dd 0
    main$t#6 dd 0
    main$t#13 dd 0
    main$t#9 dd 0
    main$t#16 dd 0
    main$t#8 dd 0
    main$t#15 dd 0
    main$t#1 dd 0
    errorByOverflow db "KOTLITE - ERROR DE EJECUCION: Operacion con Overflow", 10, 0

section .text

handlerErrorByOverflow: nop
    cmp bl, 0
    je checked

    mov eax, errorByOverflow
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

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 9999999    Op2:     Dest: main$t#1
    mov eax, 9999999
    mov [main$t#1], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 2    Op2:     Dest: main$t#2
    mov eax, 2
    mov [main$t#2], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 4    Op2:     Dest: main$t#3
    mov eax, 4
    mov [main$t#3], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: main$t#2    Op2: main$t#3    Dest: main$t#4
    mov eax, [main$t#2]
    mov ebx, [main$t#3]
    add eax, ebx
    mov [main$t#4], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 3    Op2:     Dest: main$t#5
    mov eax, 3
    mov [main$t#5], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: main$t#4    Op2: main$t#5    Dest: main$t#6
    mov eax, [main$t#4]
    mov ebx, [main$t#5]
    add eax, ebx
    mov [main$t#6], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 5    Op2:     Dest: main$t#7
    mov eax, 5
    mov [main$t#7], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: main$t#6    Op2: main$t#7    Dest: main$t#8
    mov eax, [main$t#6]
    mov ebx, [main$t#7]
    add eax, ebx
    mov [main$t#8], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 6666    Op2:     Dest: main$t#9
    mov eax, 6666
    mov [main$t#9], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 3    Op2:     Dest: main$t#10
    mov eax, 3
    mov [main$t#10], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: main$t#9    Op2: main$t#10    Dest: main$t#11
    mov eax, [main$t#9]
    mov ebx, [main$t#10]
    add eax, ebx
    mov [main$t#11], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: 3    Op2:     Dest: main$t#12
    mov eax, 3
    mov [main$t#12], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: main$t#11    Op2: main$t#12    Dest: main$t#13
    mov eax, [main$t#11]
    mov ebx, [main$t#12]
    add eax, ebx
    mov [main$t#13], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: main$t#8    Op2: main$t#13    Dest: main$t#14
    mov eax, [main$t#8]
    mov ebx, [main$t#13]
    add eax, ebx
    mov [main$t#14], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: -5    Op2:     Dest: main$t#15
    mov eax, -5
    mov [main$t#15], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción PLUS
    ; Op1: main$t#14    Op2: main$t#15    Dest: main$t#16
    mov eax, [main$t#14]
    mov ebx, [main$t#15]
    add eax, ebx
    mov [main$t#16], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción MULTI
    ; Op1: main$t#1    Op2: main$t#16    Dest: main$t#17
    mov eax, [main$t#1]
    mov ebx, [main$t#16]
    imul ebx

    ; GESTION OVERFLOW
    setc bl
    call handlerErrorByOverflow

    mov [main$t#17], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY
    ; Op1: main$t#17    Op2:     Dest: global$globalC
    mov eax, [main$t#17]
    mov [global$globalC], eax

    ; ===== ===== ===== ===== =====
    ; Instrucción COPY_LITERAL
    ; Op1: "La multiplicacion es %d"    Op2:     Dest: main$t#18
    ; Copy de String literal se ignora, ya está en memoria

    ; ===== ===== ===== ===== =====
    ; Instrucción PRINTINT
    ; Op1: main$t#18    Op2: global$globalC    Dest: 
    mov ebx, [global$globalC]
    push ebx
    mov eax, main$t#18
    push eax
    call printf
    pop eax
    pop ebx

    ; ===== ===== ===== ===== =====
    ; exit(0)
    mov ebx, 0
    mov eax, 1
    int 0x80
