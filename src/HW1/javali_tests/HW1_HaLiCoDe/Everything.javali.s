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
        # Emitting int r4
        # Declaration of variable r4
        r4: .long 0
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
        # Emitting i0 = 5
        # Assignment of i0 (1 registers needed)
        # Load of integer constant 5
        movl $5, %edi
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
        # Emitting i3 = -(7)
        # Assignment of i3 (1 registers needed)
        # Load of integer constant 7
        movl $7, %edi
        # Negation of %edi
        negl %edi
        movl %edi, i3
        # Emitting r1 = (i2 / 2)
        # Assignment of r1 (2 registers needed)
        # Load of integer constant 2
        movl $2, %edi
        # Load of variable i2 into register %esi
        movl i2, %esi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        movl %esi, r1
        # Emitting write(-((r1 / 2)))
        # Load of integer constant 2
        movl $2, %esi
        # Load of variable r1 into register %edi
        movl r1, %edi
        # %edi = %edi / %esi
        movl %edi, %eax
        cdq
        idivl %esi
        movl %eax, %edi
        # Negation of %edi
        negl %edi
        # --- Write of value in req %edi---
        # 1 registers needed
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
        # Emitting i4 = read()
        # Assignment of i4 (1 registers needed)
        # --- Read ---
        subl $8, %esp
        movl $READ_VAL, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _scanf
        movl READ_VAL, %edi
        addl $8, %esp
        # ---------
        movl %edi, i4
        # Emitting r2 = (r1 / i3)
        # Assignment of r2 (2 registers needed)
        # Load of variable i3 into register %edi
        movl i3, %edi
        # Load of variable r1 into register %esi
        movl r1, %esi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        movl %esi, r2
        # Emitting write((r2 * 12))
        # Load of integer constant 12
        movl $12, %esi
        # Load of variable r2 into register %edi
        movl r2, %edi
        # %edi = %edi * %esi
        imull %esi, %edi
        # --- Write of value in req %edi---
        # 2 registers needed
        subl $8, %esp
        movl %edi, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _printf
        addl $8, %esp
        # --------
        # Emitting write(i4)
        # Load of variable i4 into register %edi
        movl i4, %edi
        # --- Write of value in req %edi---
        # 1 registers needed
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
        # Emitting r3 = (1 - (3 / (37 / ((5 * i3) + 2))))
        # Assignment of r3 (2 registers needed)
        # Load of variable i3 into register %edi
        movl i3, %edi
        # Load of integer constant 5
        movl $5, %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        # Load of integer constant 2
        movl $2, %edi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of integer constant 37
        movl $37, %edi
        # %edi = %edi / %esi
        movl %edi, %eax
        cdq
        idivl %esi
        movl %eax, %edi
        # Load of integer constant 3
        movl $3, %esi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of integer constant 1
        movl $1, %edi
        # %edi = %edi - %esi
        subl %esi, %edi
        movl %edi, r3
        # Emitting write(r3)
        # Load of variable r3 into register %edi
        movl r3, %edi
        # --- Write of value in req %edi---
        # 1 registers needed
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
        # Emitting r4 = r3
        # Assignment of r4 (1 registers needed)
        # Load of variable r3 into register %edi
        movl r3, %edi
        movl %edi, r4
        # Emitting write(((((r4 * 10) / i4) - 2) + ((2 * 5) / -(i1))))
        # Load of integer constant 5
        movl $5, %edi
        # Load of integer constant 2
        movl $2, %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        # Load of variable i1 into register %edi
        movl i1, %edi
        # Negation of %edi
        negl %edi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of integer constant 10
        movl $10, %edi
        # Load of variable r4 into register %edx
        movl r4, %edx
        # %edx = %edx * %edi
        imull %edi, %edx
        # Load of variable i4 into register %edi
        movl i4, %edi
        # %edx = %edx / %edi
        # EDX in use, we need to store it on the stack prior to div
        movl %edx, -8(%esp)
        movl %edx, %eax
        cdq
        idivl %edi
        movl %eax, %edx
        # Load of integer constant 2
        movl $2, %edi
        # %edx = %edx - %edi
        subl %edi, %edx
        # %edx = %edx + %esi
        addl %esi, %edx
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
    # Restores base pointer
    popl %ebp
    # Return 0
    movl $0, %eax
    retl
