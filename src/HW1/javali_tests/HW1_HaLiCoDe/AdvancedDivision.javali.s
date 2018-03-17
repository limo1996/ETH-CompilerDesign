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
        # Emitting int i1
        # Declaration of variable i1
        i1: .long 0
        # Emitting int i2
        # Declaration of variable i2
        i2: .long 0
    # Main function declaration and body
    .text
    .global _main
    _main:
    # Asm function prolog
    pushl %ebp
    movl %esp, %ebp
    .align 16
      # Emitting (...)
        # Emitting i0 = 5
        # Assignment of i0 (1 registers needed)
        # Load of integer constant 5
        movl $5, %edi
        movl %edi, i0
        # Emitting i1 = (((55 / i0) * 9) / 7)
        # Assignment of i1 (2 registers needed)
        # Load of variable i0 into register %edi
        movl i0, %edi
        # Load of integer constant 55
        movl $55, %esi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of integer constant 9
        movl $9, %edi
        # %esi = %esi * %edi
        imull %edi, %esi
        # Load of integer constant 7
        movl $7, %edi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        movl %esi, i1
        # Emitting i2 = (97 + (5 / i0))
        # Assignment of i2 (2 registers needed)
        # Load of variable i0 into register %esi
        movl i0, %esi
        # Load of integer constant 5
        movl $5, %edi
        # %edi = %edi / %esi
        movl %edi, %eax
        cdq
        idivl %esi
        movl %eax, %edi
        # Load of integer constant 97
        movl $97, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        movl %esi, i2
        # Emitting i1 = (((((7 / 8) * i0) + 9) + 5) - ((8 * 7) / 5))
        # Assignment of i1 (3 registers needed)
        # Load of integer constant 7
        movl $7, %esi
        # Load of integer constant 8
        movl $8, %edi
        # %edi = %edi * %esi
        imull %esi, %edi
        # Load of integer constant 5
        movl $5, %esi
        # %edi = %edi / %esi
        movl %edi, %eax
        cdq
        idivl %esi
        movl %eax, %edi
        # Load of integer constant 8
        movl $8, %esi
        # Load of integer constant 7
        movl $7, %edx
        # %edx = %edx / %esi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %esi
        movl %eax, %edx
        # Load of variable i0 into register %esi
        movl i0, %esi
        # %edx = %edx * %esi
        imull %esi, %edx
        # Load of integer constant 9
        movl $9, %esi
        # %edx = %edx + %esi
        addl %esi, %edx
        # Load of integer constant 5
        movl $5, %esi
        # %edx = %edx + %esi
        addl %esi, %edx
        # %edx = %edx - %edi
        subl %edi, %edx
        movl %edx, i1
        # Emitting i2 = (67890 / 12336)
        # Assignment of i2 (2 registers needed)
        # Load of integer constant 12336
        movl $12336, %edx
        # Load of integer constant 67890
        movl $67890, %edi
        # %edi = %edi / %edx
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edi, %eax
        cdq
        # Division by %EDX, that is stored on the stack
        idivl -8(%esp)
        movl %eax, %edi
        # Restore EDX after div
        movl -8(%esp), %edx
        movl %edi, i2
        # Emitting i2 = ((((((((((((8 * 8) / 8) * 7) / 7) * 6) * 6) / 5) / 3) / 4) / 5) / 9) / 10)
        # Assignment of i2 (2 registers needed)
        # Load of integer constant 8
        movl $8, %edi
        # Load of integer constant 8
        movl $8, %edx
        # %edx = %edx * %edi
        imull %edi, %edx
        # Load of integer constant 8
        movl $8, %edi
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        # Load of integer constant 7
        movl $7, %edi
        # %edx = %edx * %edi
        imull %edi, %edx
        # Load of integer constant 7
        movl $7, %edi
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        # Load of integer constant 6
        movl $6, %edi
        # %edx = %edx * %edi
        imull %edi, %edx
        # Load of integer constant 6
        movl $6, %edi
        # %edx = %edx * %edi
        imull %edi, %edx
        # Load of integer constant 5
        movl $5, %edi
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        # Load of integer constant 3
        movl $3, %edi
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        # Load of integer constant 4
        movl $4, %edi
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        # Load of integer constant 5
        movl $5, %edi
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        # Load of integer constant 9
        movl $9, %edi
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        # Load of integer constant 10
        movl $10, %edi
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        movl %edx, i2
        # Emitting write(i0)
        # Load of variable i0 into register %edx
        movl i0, %edx
        # --- Write of value in req %edx---
        # 1 registers needed
        subl $8, %esp
        movl %edx, 4(%esp)
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
        # Emitting write(i1)
        # Load of variable i1 into register %edx
        movl i1, %edx
        # --- Write of value in req %edx---
        # 1 registers needed
        subl $8, %esp
        movl %edx, 4(%esp)
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
        # Emitting write(i2)
        # Load of variable i2 into register %edx
        movl i2, %edx
        # --- Write of value in req %edx---
        # 1 registers needed
        subl $8, %esp
        movl %edx, 4(%esp)
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
