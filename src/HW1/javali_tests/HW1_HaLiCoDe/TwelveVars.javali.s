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
        # Emitting int i5
        # Declaration of variable i5
        i5: .long 0
        # Emitting int i6
        # Declaration of variable i6
        i6: .long 0
        # Emitting int i7
        # Declaration of variable i7
        i7: .long 0
        # Emitting int i8
        # Declaration of variable i8
        i8: .long 0
        # Emitting int i9
        # Declaration of variable i9
        i9: .long 0
        # Emitting int i10
        # Declaration of variable i10
        i10: .long 0
        # Emitting int i11
        # Declaration of variable i11
        i11: .long 0
    # Main function declaration and body
    .text
    .global _main
    _main:
    # Asm function prolog
    pushl %ebp
    movl %esp, %ebp
    .align 16
      # Emitting (...)
        # Emitting i0 = 0
        # Assignment of i0 (1 registers needed)
        # Load of integer constant 0
        movl $0, %edi
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
        # Emitting i2 = 2
        # Assignment of i2 (1 registers needed)
        # Load of integer constant 2
        movl $2, %edi
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
        # Emitting i4 = 4
        # Assignment of i4 (1 registers needed)
        # Load of integer constant 4
        movl $4, %edi
        movl %edi, i4
        # Emitting i5 = read()
        # Assignment of i5 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i5
        # Emitting i6 = 6
        # Assignment of i6 (1 registers needed)
        # Load of integer constant 6
        movl $6, %edi
        movl %edi, i6
        # Emitting i7 = read()
        # Assignment of i7 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i7
        # Emitting i8 = 8
        # Assignment of i8 (1 registers needed)
        # Load of integer constant 8
        movl $8, %edi
        movl %edi, i8
        # Emitting i9 = read()
        # Assignment of i9 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i9
        # Emitting i10 = 10
        # Assignment of i10 (1 registers needed)
        # Load of integer constant 10
        movl $10, %edi
        movl %edi, i10
        # Emitting i11 = read()
        # Assignment of i11 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i11
        # Emitting i11 = ((((i0 + i1) * (i2 + i3)) * (i4 + i5)) + (((i6 * i7) + (i8 * i9)) + (i10 * i11)))
        # Assignment of i11 (4 registers needed)
        # Load of variable i9 into register %edi
        movl i9, %edi
        # Load of variable i8 into register %esi
        movl i8, %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        # Load of variable i7 into register %edi
        movl i7, %edi
        # Load of variable i6 into register %edx
        movl i6, %edx
        # %edx = %edx * %edi
        imull %edi, %edx
        # %edx = %edx + %esi
        addl %esi, %edx
        # Load of variable i11 into register %esi
        movl i11, %esi
        # Load of variable i10 into register %edi
        movl i10, %edi
        # %edi = %edi * %esi
        imull %esi, %edi
        # %edx = %edx + %edi
        addl %edi, %edx
        # Load of variable i3 into register %edi
        movl i3, %edi
        # Load of variable i2 into register %esi
        movl i2, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of variable i1 into register %edi
        movl i1, %edi
        # Load of variable i0 into register %ecx
        movl i0, %ecx
        # %ecx = %ecx + %edi
        addl %edi, %ecx
        # %ecx = %ecx * %esi
        imull %esi, %ecx
        # Load of variable i5 into register %esi
        movl i5, %esi
        # Load of variable i4 into register %edi
        movl i4, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # %ecx = %ecx * %edi
        imull %edi, %ecx
        # %ecx = %ecx + %edx
        addl %edx, %ecx
        movl %ecx, i11
        # Emitting write(i11)
        # Load of variable i11 into register %ecx
        movl i11, %ecx
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
