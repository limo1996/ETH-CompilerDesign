  # Emitting class Main {...}
    # Emitting void main(...) {...}
    # Declaration of printf and scanf strings
    .cstring
    STR_D: .asciz "%d"
    NEW_L: .asciz "\n"
    .data
    READ_VAL: .long 0
      # Emitting (...)
        # Emitting int r1
        # Declaration of variable r1
        r1: .long 0
        # Emitting int r2
        # Declaration of variable r2
        r2: .long 0
        # Emitting int r3
        # Declaration of variable r3
        r3: .long 0
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
        # Emitting i1 = 1
        # Assignment of i1 (1 registers needed)
        # Load of integer constant 1
        movl $1, %edi
        movl %edi, i1
        # Emitting i2 = 2
        # Assignment of i2 (1 registers needed)
        # Load of integer constant 2
        movl $2, %edi
        movl %edi, i2
        # Emitting i3 = 3
        # Assignment of i3 (1 registers needed)
        # Load of integer constant 3
        movl $3, %edi
        movl %edi, i3
        # Emitting i4 = 4
        # Assignment of i4 (1 registers needed)
        # Load of integer constant 4
        movl $4, %edi
        movl %edi, i4
        # Emitting i5 = 5
        # Assignment of i5 (1 registers needed)
        # Load of integer constant 5
        movl $5, %edi
        movl %edi, i5
        # Emitting i6 = 6
        # Assignment of i6 (1 registers needed)
        # Load of integer constant 6
        movl $6, %edi
        movl %edi, i6
        # Emitting i7 = 7
        # Assignment of i7 (1 registers needed)
        # Load of integer constant 7
        movl $7, %edi
        movl %edi, i7
        # Emitting r1 = (i0 + (i1 + (i2 + (i3 + (i4 + (i5 + (i6 + i7)))))))
        # Assignment of r1 (2 registers needed)
        # Load of variable i7 into register %edi
        movl i7, %edi
        # Load of variable i6 into register %esi
        movl i6, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of variable i5 into register %edi
        movl i5, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i4 into register %esi
        movl i4, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of variable i3 into register %edi
        movl i3, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i2 into register %esi
        movl i2, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of variable i1 into register %edi
        movl i1, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i0 into register %esi
        movl i0, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        movl %esi, r1
        # Emitting r2 = (((((((i0 + i1) + i2) + i3) + i4) + i5) + i6) + i7)
        # Assignment of r2 (2 registers needed)
        # Load of variable i1 into register %esi
        movl i1, %esi
        # Load of variable i0 into register %edi
        movl i0, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i2 into register %esi
        movl i2, %esi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i3 into register %esi
        movl i3, %esi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i4 into register %esi
        movl i4, %esi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i5 into register %esi
        movl i5, %esi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i6 into register %esi
        movl i6, %esi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i7 into register %esi
        movl i7, %esi
        # %edi = %edi + %esi
        addl %esi, %edi
        movl %edi, r2
        # Emitting r3 = (((i0 + i1) + (i2 + i3)) + ((i4 + i5) + (i6 + i7)))
        # Assignment of r3 (4 registers needed)
        # Load of variable i7 into register %edi
        movl i7, %edi
        # Load of variable i6 into register %esi
        movl i6, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of variable i5 into register %edi
        movl i5, %edi
        # Load of variable i4 into register %edx
        movl i4, %edx
        # %edx = %edx + %edi
        addl %edi, %edx
        # %edx = %edx + %esi
        addl %esi, %edx
        # Load of variable i3 into register %esi
        movl i3, %esi
        # Load of variable i2 into register %edi
        movl i2, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of variable i1 into register %esi
        movl i1, %esi
        # Load of variable i0 into register %ecx
        movl i0, %ecx
        # %ecx = %ecx + %esi
        addl %esi, %ecx
        # %ecx = %ecx + %edi
        addl %edi, %ecx
        # %ecx = %ecx + %edx
        addl %edx, %ecx
        movl %ecx, r3
        # Emitting write(r1)
        # Load of variable r1 into register %ecx
        movl r1, %ecx
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
        # Emitting write(r2)
        # Load of variable r2 into register %ecx
        movl r2, %ecx
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
        # Emitting write(r3)
        # Load of variable r3 into register %ecx
        movl r3, %ecx
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
