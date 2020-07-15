global main

extern printf

section .data
    globalA dd 0
    globalB dd 0
    globalC dd 0
    num1    dd 0
    num2    dd 0
    res     dd 0
    t1      dd 0
    t2      dd 0
    t3      dd 0
    t4      dd 0
    t5      dd 0
    t6      dd 0
    t7      db 'La suma es %d', 10, 0

section .bss
    DISP       resb 1000
    comentario resb 512

section .text
    ; ===== ===== ===== ===== =====
    ; Instrucción SKIP
    ; Op1:  Op2:  Dest: fun_sumar_1
    fun_sumar_1: nop

        ; PASA POR EL PARAMETRO DEST EL IDENTIFICADOR DE LA FUNCION

        ; ===== ===== ===== ===== =====
        ; Instrucción PMB
        ; Op1:  Op2:  Dest:
        mov esi, [4 + DISP]
        push esi
        push ebp
        mov ebp, esp
        mov [4 + ebp], ebp
        sub esp, 4
        mov eax, [12 + ebp]
        mov [num1], eax
        mov eax, [16 + ebp]
        mov [num2], eax

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
          ; Op1: 1 Op2:  Dest: t4
          mov eax, 1
          mov [t4], eax

          ; ===== ===== ===== ===== =====
          ; Instrucción COPY
          ; Op1: t4 Op2:  Dest: globalA
          mov eax, [t4]
          mov [globalA], eax

          ; ===== ===== ===== ===== =====
          ; Instrucción COPY
          ; Op1: 2 Op2:  Dest: t5
          mov eax, 2
          mov [t5], eax

          ; ===== ===== ===== ===== =====
          ; Instrucción COPY
          ; Op1: t5 Op2:  Dest: globalB
          mov eax, [t5]
          mov [globalB], eax

          ; LOS PARAMETROS SE INTRODUCEN EN ORDEN INVERSO (PRIMERO B Y LUEGO A)

          ; ===== ===== ===== ===== =====
          ; Instrucción PARAM
          ; Op1:  Op2:  Dest: globalB
          mov eax, [globalB]
          push eax

          ; ===== ===== ===== ===== =====
          ; Instrucción PARAM
          ; Op1:  Op2:  Dest: globalA
          mov eax, [globalA]
          push eax

          ; LAS VARIABLES DEL CODIGO DEL CALL TAMBIEN SE DECLARAN ARRIBA

          ; ===== ===== ===== ===== =====
          ; Instrucción CALL
          ; Op1: fun_sumar_1 Op2:  Dest: t6
          call fun_sumar_1
          mov ebx, 8    ; 4 bytes por parametro (4 + 4 = 8) o lo que ocupen las variables
          add ebx, esp
          mov eax, [res]
          mov [t6], eax

          ; ===== ===== ===== ===== =====
          ; Instrucción COPY
          ; Op1: t6 Op2:  Dest: globalC
          mov eax, [t6]
          mov [globalC], eax

          ; ===== ===== ===== ===== =====
          ; Instrucción COPY
          ; Op1: t7 Op2:  Dest: comentario
          mov eax, t7
          mov [comentario], eax

          ; ===== ===== ===== ===== =====
          ; Instrucción PRINTINT
          ; Op1: comentario Op2: globalC Dest:
          sub esp, 4
          mov ebx, [globalC]
          push ebx
          mov eax, [comentario]
          push eax
          call printf
          add esp, 8

	      mov ebx, 0 ; INSTRUCCIONES PARA ACABAR PROGRAMA
	      mov eax, 1
	      int 0x80
