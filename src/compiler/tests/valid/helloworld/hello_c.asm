; -------------------------------------------------------------
; Using Linux System calls for 64-bit
; to run:
;   nasm -felf64 hello.asm && ld hello.o -o hello
;   ./hello
; --------------------------------------------------------------

global main

extern printf

section .data
    msg db "Hello world!",10,0      ; 10 is the ASCII code for a new line (LF)

section .bss
    temp resb 512       ; Reservar 512 bytes (¿bits?) de memoria

section .text

main:
; Instruccion print con fprint
    mov eax, msg
    mov [temp], eax
    mov eax, [temp]
    push eax
    call printf
    pop eax

; Exit
	mov	eax,0		;  normal, no error, return value
	ret			; return