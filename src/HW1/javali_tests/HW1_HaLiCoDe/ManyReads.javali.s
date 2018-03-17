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
        # Emitting int i3
        # Declaration of variable i3
        i3: .long 0
        # Emitting int i4
        # Declaration of variable i4
        i4: .long 0
    # Main function declaration and body
    .text
    .global _main
    _main:
    # Asm function prolog
    pushl %ebp
    movl %esp, %ebp
    .align 16
      # Emitting (...)
        # Emitting i0 = read()
        # Assignment of i0 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i0
        # Emitting i1 = read()
        # Assignment of i1 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i1
        # Emitting i2 = read()
        # Assignment of i2 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i2
        # Emitting i3 = read()
        # Assignment of i3 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i3
        # Emitting write(((i0 / i1) * (i2 / i3)))
        # Load of variable i3 into register %edi
        movl i3, %edi
        # Load of variable i2 into register %esi
        movl i2, %esi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of variable i1 into register %edi
        movl i1, %edi
        # Load of variable i0 into register %edx
        movl i0, %edx
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        # %edx = %edx * %esi
        imull %esi, %edx
        # --- Write of value in req %edx---
        # 3 registers needed
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
        # Emitting i0 = read()
        # Assignment of i0 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edx
        addl $8, %esp
        # ---------
        movl %edx, i0
        # Emitting i1 = read()
        # Assignment of i1 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edx
        addl $8, %esp
        # ---------
        movl %edx, i1
        # Emitting i2 = read()
        # Assignment of i2 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edx
        addl $8, %esp
        # ---------
        movl %edx, i2
        # Emitting i3 = read()
        # Assignment of i3 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edx
        addl $8, %esp
        # ---------
        movl %edx, i3
        # Emitting write(((i0 / i1) * (i2 / i3)))
        # Load of variable i3 into register %edx
        movl i3, %edx
        # Load of variable i2 into register %esi
        movl i2, %esi
        # %esi = %esi / %edx
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %esi, %eax
        cdq
        # Division by %EDX, that is stored on the stack
        idivl -8(%esp)
        movl %eax, %esi
        # Restore EDX after div
        movl -8(%esp), %edx
        # Load of variable i1 into register %edx
        movl i1, %edx
        # Load of variable i0 into register %edi
        movl i0, %edi
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
        # %edi = %edi * %esi
        imull %esi, %edi
        # --- Write of value in req %edi---
        # 3 registers needed
        subl $8, %esp
        movl %edi, 4(%esp)
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
        # Emitting i0 = read()
        # Assignment of i0 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i0
        # Emitting i1 = read()
        # Assignment of i1 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i1
        # Emitting i2 = read()
        # Assignment of i2 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i2
        # Emitting i3 = read()
        # Assignment of i3 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i3
        # Emitting write((((i0 / i1) * (i2 / i3)) * ((((i0 + i1) * (i2 + (-(i3) * +(i2)))) / ((i0 + i3) + -(i2))) / (((((((i0 - i1) - i2) - i3) - i0) - i1) - i2) - i3))))
        # Load of variable i2 into register %edi
        movl i2, %edi
        # Load of variable i3 into register %esi
        movl i3, %esi
        # Negation of %esi
        negl %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        # Load of variable i2 into register %edi
        movl i2, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i1 into register %esi
        movl i1, %esi
        # Load of variable i0 into register %edx
        movl i0, %edx
        # %edx = %edx + %esi
        addl %esi, %edx
        # %edx = %edx * %edi
        imull %edi, %edx
        # Load of variable i3 into register %edi
        movl i3, %edi
        # Load of variable i0 into register %esi
        movl i0, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of variable i2 into register %edi
        movl i2, %edi
        # Negation of %edi
        negl %edi
        # %esi = %esi + %edi
        addl %edi, %esi
        # %edx = %edx / %esi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %esi
        movl %eax, %edx
        # Load of variable i1 into register %esi
        movl i1, %esi
        # Load of variable i0 into register %edi
        movl i0, %edi
        # %edi = %edi - %esi
        subl %esi, %edi
        # Load of variable i2 into register %esi
        movl i2, %esi
        # %edi = %edi - %esi
        subl %esi, %edi
        # Load of variable i3 into register %esi
        movl i3, %esi
        # %edi = %edi - %esi
        subl %esi, %edi
        # Load of variable i0 into register %esi
        movl i0, %esi
        # %edi = %edi - %esi
        subl %esi, %edi
        # Load of variable i1 into register %esi
        movl i1, %esi
        # %edi = %edi - %esi
        subl %esi, %edi
        # Load of variable i2 into register %esi
        movl i2, %esi
        # %edi = %edi - %esi
        subl %esi, %edi
        # Load of variable i3 into register %esi
        movl i3, %esi
        # %edi = %edi - %esi
        subl %esi, %edi
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        # Load of variable i3 into register %edi
        movl i3, %edi
        # Load of variable i2 into register %esi
        movl i2, %esi
        # %esi = %esi / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Restore EDX after div
        movl -8(%esp), %edx
        # Load of variable i1 into register %edi
        movl i1, %edi
        # Load of variable i0 into register %ecx
        movl i0, %ecx
        # %ecx = %ecx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %ecx, %eax
        cdq
        idivl %edi
        movl %eax, %ecx
        # Restore EDX after div
        movl -8(%esp), %edx
        # %ecx = %ecx * %esi
        imull %esi, %ecx
        # %ecx = %ecx * %edx
        imull %edx, %ecx
        # --- Write of value in req %ecx---
        # 4 registers needed
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
        # Emitting i4 = 0
        # Assignment of i4 (1 registers needed)
        # Load of integer constant 0
        movl $0, %ecx
        movl %ecx, i4
        # Emitting write(i4)
        # Load of variable i4 into register %ecx
        movl i4, %ecx
        # --- Write of value in req %ecx---
        # 1 registers needed
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
