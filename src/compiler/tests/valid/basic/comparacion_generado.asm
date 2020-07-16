global main

extern printf

; Sección de memoria para las variables no inicializadas
section .bss
    mensaje resb 512

; Sección de memoria para las variables inicializadas
section .data
    llueve  dd 0
    haceSol dd 0
    t#1 dd 0
    t#2 dd 0
    t#3 dd 0
    t#4 dd 0
    t#5 db 'Soy verdadero', 10, 0
    t#6 db 'Soy falso', 10, 0
    t#7 db 'Antes de meter en el bucle', 10, 0

section .text
    main:

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: false Op2:  Dest: t1
        mov eax, 0
        mov [t#3], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: t1 Op2:  Dest: llueve
        mov eax, [t#3]
        mov [llueve], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: true Op2:  Dest: t2
        mov eax, 1
        mov [t#4], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: t2 Op2:  Dest: haceSol
        mov eax, [t#4]
        mov [haceSol], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: t#5    Op2:     Dest: mensaje
        mov eax, t#7
        mov [mensaje], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción EQU
        ; Op1: t#3    Op2: t#4    Dest: t#5
        mov eax, [haceSol]
        cmp eax, [llueve]
        setne [t#5]

        ; ===== ===== ===== ===== =====
        ; Instrucción IFGOTO
        ; Op1: t#5    Op2:     Dest: cond_false_1
        mov eax, [t#5]
        mov ebx, 1
        cmp eax, ebx
        jne cond_false_1

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: t#5    Op2:     Dest: mensaje
        mov eax, t#5
        mov [mensaje], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción PRINT
        ; Op1: comentario Op2: mensaje Dest:
        mov eax, mensaje
        push eax
        call printf
        add esp, 8

        ; ===== ===== ===== ===== =====
        ; Instrucción GOTO
        ; Op1:     Op2:     Dest: cond_true_1
        jmp cond_true_1

        ; ===== ===== ===== ===== =====
        ; Instrucción SKIP
        ; Op1:     Op2:     Dest: cond_false_1
        cond_false_1: nop

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: t#6    Op2:     Dest: mensaje
        mov eax, t#6
        mov [mensaje], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción PRINT
        ; Op1: comentario Op2: mensaje Dest:
        mov eax, mensaje
        push eax
        call printf
        add esp, 8

        ; ===== ===== ===== ===== =====
        ; Instrucción SKIP
        ; Op1:     Op2:     Dest: cond_true_1
        cond_true_1: nop

        ; ===== ===== ===== ===== =====
        ; Instrucción PRINT
        ; Op1: comentario Op2: mensaje Dest:
        mov eax, mensaje
        push eax
        call printf
        add esp, 8

        mov ebx, 0 ; INSTRUCCIONES PARA ACABAR PROGRAMA
        mov eax, 1
        int 0x80




