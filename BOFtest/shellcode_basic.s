.section .data

name: .string "/bin/sh"

.section .text
.global _start

_start:
pushq $0     ;
pushq name   ; 

movq $59, %rax ; 
movq %rsp, %rdi ; 
movq $0, %rsi
movq $0, %rdx ;
syscall 
