; -------------------------------------------------------------
; Using Linux System calls for 64-bit
; to run:
;   nasm -felf64 hello.asm && ld hello.o -o hello
;   ./hello
; --------------------------------------------------------------

global main

extern printf

section .data
    t1 db "Hello world!",10,0      ; 10 is the ASCII code for a new line (LF)

section .bss
    t2 resb 512       ; Reservar 512 bytes (¿bits?) de memoria

section .text

main:
    ; ===== ===== ===== ===== =====
    ; Instrucción CALL
    ; Op1: fun_print_1 Op2:  Dest: t2
    mov eax, t1
    mov [t2], eax
    mov eax, [t2]
    push eax
    call printf
    pop eax

    ; Exit
	mov	eax,0		;  normal, no error, return value
	ret			    ; return