global main

extern printf

section .data
    t#1      dd 0
    llueve  dd 0
    t#2      dd 0
    haceSol dd 0
    t#4      db "", 10, 0


section .bss
    DISP       resb 1000
    mensaje     resb 512

section .text
    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:  Op2:  Dest: fun_comprobarTemperatura_1
    fun_comprobarTemperatura_1: nop

        ; PASAR POR EL PARAMETRO DEST EL IDENTIFICADOR DE LA FUNCION

        ; ===== ===== ===== ===== =====
        ; Instrucción PMB
        ; Op1:  Op2:  Dest:
        mov esi, [4 + DISP] ; SIEMPRE EL MISMO CODIGO DE AQUI
        push esi
        push ebp
        mov ebp, esp
        mov [4 + ebp], ebp
        sub esp, 4          ; HASTA AQUI
        mov eax, [12 + ebp] ; PRIMER PARAMETRO
        mov [temp], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: t#4    Op2:     Dest: mensaje
        mov eax, t#4
        mov [mensaje], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: 15    Op2:     Dest: t#5
        mov eax, 15
        mov t#5, eax

        ; ===== ===== ===== ===== =====
        ; Instrucción LTEQU
        ; Op1: t#3    Op2: t#5    Dest: t#6

        ; ===== ===== ===== ===== =====
        ; Instrucción PLUS
        ; Op1: t1 Op2: t2 Dest: t3
        mov eax, [num1]
        mov ebx, [num2]
        add eax, ebx
        mov [t3], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: t3 Op2:  Dest: res
        mov eax, [t3]
        mov [res], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción RTN
        ; Op1:  Op2:  Dest: res
        mov esp, ebp
        pop ebp
        mov edi, [4 + DISP]
        pop edi
        ret

    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:  Op2:  Dest: fun_main_2
    main:

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: false Op2:  Dest: t1
        mov eax, 0
        mov [t1], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: t1 Op2:  Dest: llueve
        mov eax, [t1]
        mov [llueve], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: true Op2:  Dest: t2
        mov eax, -1
        mov [t2], eax

        ; ===== ===== ===== ===== =====
        ; Instrucción COPY
        ; Op1: t2 Op2:  Dest: haceSol
        mov eax, [t2]
        mov [haceSol], eax
        ¡














iiiiiiiiiiiiiiiiiiiiiiiiiii kkkkkkkkkkkkkkkkkkkkkkkkkk


        mov ebx, 0 ; INSTRUCCIONES PARA ACABAR PROGRAMA
        mov eax, 1
        int 0x80
