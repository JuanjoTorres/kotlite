; -------------------------------------------------------------
; Using Linux System calls for 64-bit
; to run:
;   nasm -felf64 hello.asm && ld hello.o -o hello
;   ./hello
; --------------------------------------------------------------

section .data
    msg db "Hello world!",10      ; 10 is the ASCII code for a new line (LF)

section .text
    global _start

_start:
; Instruccion print con syscall
    mov rax, 1
    mov rdi, 1
    mov rsi, msg
    mov rdx, 13
    syscall

; Instruccion exit(0)
    mov rax, 60
    mov rdi, 0
    syscall