  # Emitting class Main {...}
    # Emitting void main(...) {...}
    # Declaration of printf and scanf strings
    .cstring
    STR_D: .asciz "%d"
    NEW_L: .asciz "\n"
    .data
    READ_VAL: .long 0
      # Emitting (...)
        # Emitting int i0
        # Declaration of variable i0
        i0: .long 0
    # Main function declaration and body
    .text
    .global _main
    _main:
    # Asm function prolog
    pushl %ebp
    movl %esp, %ebp
    .align 16
      # Emitting (...)
        # Emitting i0 = (((((1 + 2) + (3 + 4)) + ((5 + 6) + (7 + 8))) + (((9 + 10) + (11 + 12)) + ((13 + 14) + (15 + 16)))) + ((((17 + 18) + (19 + 20)) + ((21 + 22) + (23 + 24))) + (((25 + 26) + (27 + 28)) + ((29 + 30) + (31 + 32)))))
        # Assignment of i0 (6 registers needed)
        # Load of integer constant 32
        movl $32, %edi
        # Load of integer constant 31
        movl $31, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of integer constant 30
        movl $30, %edi
        # Load of integer constant 29
        movl $29, %edx
        # %edx = %edx + %edi
        addl %edi, %edx
        # %edx = %edx + %esi
        addl %esi, %edx
        # Load of integer constant 28
        movl $28, %esi
        # Load of integer constant 27
        movl $27, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of integer constant 26
        movl $26, %esi
        # Load of integer constant 25
        movl $25, %ecx
        # %ecx = %ecx + %esi
        addl %esi, %ecx
        # %ecx = %ecx + %edi
        addl %edi, %ecx
        # %ecx = %ecx + %edx
        addl %edx, %ecx
        # Load of integer constant 24
        movl $24, %edx
        # Load of integer constant 23
        movl $23, %edi
        # %edi = %edi + %edx
        addl %edx, %edi
        # Load of integer constant 22
        movl $22, %edx
        # Load of integer constant 21
        movl $21, %esi
        # %esi = %esi + %edx
        addl %edx, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of integer constant 20
        movl $20, %edi
        # Load of integer constant 19
        movl $19, %edx
        # %edx = %edx + %edi
        addl %edi, %edx
        # Load of integer constant 18
        movl $18, %edi
        # Load of integer constant 17
        movl $17, %ebx
        # %ebx = %ebx + %edi
        addl %edi, %ebx
        # %ebx = %ebx + %edx
        addl %edx, %ebx
        # %ebx = %ebx + %esi
        addl %esi, %ebx
        # %ebx = %ebx + %ecx
        addl %ecx, %ebx
        # Load of integer constant 16
        movl $16, %ecx
        # Load of integer constant 15
        movl $15, %esi
        # %esi = %esi + %ecx
        addl %ecx, %esi
        # Load of integer constant 14
        movl $14, %ecx
        # Load of integer constant 13
        movl $13, %edx
        # %edx = %edx + %ecx
        addl %ecx, %edx
        # %edx = %edx + %esi
        addl %esi, %edx
        # Load of integer constant 12
        movl $12, %esi
        # Load of integer constant 11
        movl $11, %ecx
        # %ecx = %ecx + %esi
        addl %esi, %ecx
        # Load of integer constant 10
        movl $10, %esi
        # Load of integer constant 9
        movl $9, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # %edi = %edi + %ecx
        addl %ecx, %edi
        # %edi = %edi + %edx
        addl %edx, %edi
        # Load of integer constant 8
        movl $8, %edx
        # Load of integer constant 7
        movl $7, %ecx
        # %ecx = %ecx + %edx
        addl %edx, %ecx
        # Load of integer constant 6
        movl $6, %edx
        # Load of integer constant 5
        movl $5, %esi
        # %esi = %esi + %edx
        addl %edx, %esi
        # %esi = %esi + %ecx
        addl %ecx, %esi
        # Load of integer constant 4
        movl $4, %ecx
        # Load of integer constant 3
        movl $3, %edx
        # %edx = %edx + %ecx
        addl %ecx, %edx
        # Load of integer constant 2
        movl $2, %ecx
        # Load of integer constant 1
        movl $1, %eax
        # %eax = %eax + %ecx
        addl %ecx, %eax
        # %eax = %eax + %edx
        addl %edx, %eax
        # %eax = %eax + %esi
        addl %esi, %eax
        # %eax = %eax + %edi
        addl %edi, %eax
        # %eax = %eax + %ebx
        addl %ebx, %eax
        movl %eax, i0
        # Emitting write(i0)
        # Load of variable i0 into register %eax
        movl i0, %eax
        # --- Write of value in req %eax---
        # 1 registers needed
        subl $8, %esp
        movl %eax, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _printf
        addl $8, %esp
        # --------
        # Emitting writeln()
        # --- Write of new line ---
        subl $4, %esp
        movl $NEW_L, 0(%esp)
        calll _printf
        addl $4, %esp
        # --------
        # Emitting write((((((1 + 2) + (3 + 4)) + ((5 + 6) + (7 + 8))) + (((9 + 10) + (11 + 12)) + ((13 + 14) + (15 + 16)))) + ((((17 + 18) + (19 + 20)) + ((21 + 22) + (23 + 24))) + (((25 + 26) + (27 + 28)) + ((29 + 30) + (31 + 32))))))
        # Load of integer constant 32
        movl $32, %eax
        # Load of integer constant 31
        movl $31, %ebx
        # %ebx = %ebx + %eax
        addl %eax, %ebx
        # Load of integer constant 30
        movl $30, %eax
        # Load of integer constant 29
        movl $29, %edi
        # %edi = %edi + %eax
        addl %eax, %edi
        # %edi = %edi + %ebx
        addl %ebx, %edi
        # Load of integer constant 28
        movl $28, %ebx
        # Load of integer constant 27
        movl $27, %eax
        # %eax = %eax + %ebx
        addl %ebx, %eax
        # Load of integer constant 26
        movl $26, %ebx
        # Load of integer constant 25
        movl $25, %esi
        # %esi = %esi + %ebx
        addl %ebx, %esi
        # %esi = %esi + %eax
        addl %eax, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of integer constant 24
        movl $24, %edi
        # Load of integer constant 23
        movl $23, %eax
        # %eax = %eax + %edi
        addl %edi, %eax
        # Load of integer constant 22
        movl $22, %edi
        # Load of integer constant 21
        movl $21, %ebx
        # %ebx = %ebx + %edi
        addl %edi, %ebx
        # %ebx = %ebx + %eax
        addl %eax, %ebx
        # Load of integer constant 20
        movl $20, %eax
        # Load of integer constant 19
        movl $19, %edi
        # %edi = %edi + %eax
        addl %eax, %edi
        # Load of integer constant 18
        movl $18, %eax
        # Load of integer constant 17
        movl $17, %edx
        # %edx = %edx + %eax
        addl %eax, %edx
        # %edx = %edx + %edi
        addl %edi, %edx
        # %edx = %edx + %ebx
        addl %ebx, %edx
        # %edx = %edx + %esi
        addl %esi, %edx
        # Load of integer constant 16
        movl $16, %esi
        # Load of integer constant 15
        movl $15, %ebx
        # %ebx = %ebx + %esi
        addl %esi, %ebx
        # Load of integer constant 14
        movl $14, %esi
        # Load of integer constant 13
        movl $13, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # %edi = %edi + %ebx
        addl %ebx, %edi
        # Load of integer constant 12
        movl $12, %ebx
        # Load of integer constant 11
        movl $11, %esi
        # %esi = %esi + %ebx
        addl %ebx, %esi
        # Load of integer constant 10
        movl $10, %ebx
        # Load of integer constant 9
        movl $9, %eax
        # %eax = %eax + %ebx
        addl %ebx, %eax
        # %eax = %eax + %esi
        addl %esi, %eax
        # %eax = %eax + %edi
        addl %edi, %eax
        # Load of integer constant 8
        movl $8, %edi
        # Load of integer constant 7
        movl $7, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of integer constant 6
        movl $6, %edi
        # Load of integer constant 5
        movl $5, %ebx
        # %ebx = %ebx + %edi
        addl %edi, %ebx
        # %ebx = %ebx + %esi
        addl %esi, %ebx
        # Load of integer constant 4
        movl $4, %esi
        # Load of integer constant 3
        movl $3, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of integer constant 2
        movl $2, %esi
        # Load of integer constant 1
        movl $1, %ecx
        # %ecx = %ecx + %esi
        addl %esi, %ecx
        # %ecx = %ecx + %edi
        addl %edi, %ecx
        # %ecx = %ecx + %ebx
        addl %ebx, %ecx
        # %ecx = %ecx + %eax
        addl %eax, %ecx
        # %ecx = %ecx + %edx
        addl %edx, %ecx
        # --- Write of value in req %ecx---
        # 6 registers needed
        subl $8, %esp
        movl %ecx, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _printf
        addl $8, %esp
        # --------
        # Emitting writeln()
        # --- Write of new line ---
        subl $4, %esp
        movl $NEW_L, 0(%esp)
        calll _printf
        addl $4, %esp
        # --------
    # Restores base pointer
    popl %ebp
    # Return 0
    movl $0, %eax
    retl
