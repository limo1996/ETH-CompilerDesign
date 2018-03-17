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
        # Emitting i0 = (+(2) + +(3))
        # Assignment of i0 (2 registers needed)
        # Load of integer constant 3
        movl $3, %edi
        # Load of integer constant 2
        movl $2, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        movl %esi, i0
        # Emitting i1 = (0 - +(i1))
        # Assignment of i1 (2 registers needed)
        # Load of variable i1 into register %esi
        movl i1, %esi
        # Load of integer constant 0
        movl $0, %edi
        # %edi = %edi - %esi
        subl %esi, %edi
        movl %edi, i1
        # Emitting i2 = (+(i0) * +(i1))
        # Assignment of i2 (2 registers needed)
        # Load of variable i1 into register %edi
        movl i1, %edi
        # Load of variable i0 into register %esi
        movl i0, %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        movl %esi, i2
        # Emitting i1 = (+(i0) * 1)
        # Assignment of i1 (2 registers needed)
        # Load of integer constant 1
        movl $1, %esi
        # Load of variable i0 into register %edi
        movl i0, %edi
        # %edi = %edi * %esi
        imull %esi, %edi
        movl %edi, i1
        # Emitting i0 = (+(0) + +(+(i0)))
        # Assignment of i0 (2 registers needed)
        # Load of variable i0 into register %edi
        movl i0, %edi
        # Load of integer constant 0
        movl $0, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        movl %esi, i0
        # Emitting i1 = +(+(i0))
        # Assignment of i1 (1 registers needed)
        # Load of variable i0 into register %esi
        movl i0, %esi
        movl %esi, i1
        # Emitting write(i0)
        # Load of variable i0 into register %esi
        movl i0, %esi
        # --- Write of value in req %esi---
        # 1 registers needed
        subl $8, %esp
        movl %esi, 4(%esp)
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
        # Load of variable i1 into register %esi
        movl i1, %esi
        # --- Write of value in req %esi---
        # 1 registers needed
        subl $8, %esp
        movl %esi, 4(%esp)
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
        # Load of variable i2 into register %esi
        movl i2, %esi
        # --- Write of value in req %esi---
        # 1 registers needed
        subl $8, %esp
        movl %esi, 4(%esp)
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
        # Emitting i0 = (-(6) - 1)
        # Assignment of i0 (2 registers needed)
        # Load of integer constant 1
        movl $1, %esi
        # Load of integer constant 6
        movl $6, %edi
        # Negation of %edi
        negl %edi
        # %edi = %edi - %esi
        subl %esi, %edi
        movl %edi, i0
        # Emitting i2 = (i0 * -(i1))
        # Assignment of i2 (2 registers needed)
        # Load of variable i1 into register %edi
        movl i1, %edi
        # Negation of %edi
        negl %edi
        # Load of variable i0 into register %esi
        movl i0, %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        movl %esi, i2
        # Emitting i2 = -(-(1))
        # Assignment of i2 (1 registers needed)
        # Load of integer constant 1
        movl $1, %esi
        # Negation of %esi
        negl %esi
        # Negation of %esi
        negl %esi
        movl %esi, i2
        # Emitting write(i0)
        # Load of variable i0 into register %esi
        movl i0, %esi
        # --- Write of value in req %esi---
        # 1 registers needed
        subl $8, %esp
        movl %esi, 4(%esp)
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
        # Load of variable i1 into register %esi
        movl i1, %esi
        # --- Write of value in req %esi---
        # 1 registers needed
        subl $8, %esp
        movl %esi, 4(%esp)
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
        # Load of variable i2 into register %esi
        movl i2, %esi
        # --- Write of value in req %esi---
        # 1 registers needed
        subl $8, %esp
        movl %esi, 4(%esp)
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
        # Emitting i0 = -(+(3))
        # Assignment of i0 (1 registers needed)
        # Load of integer constant 3
        movl $3, %esi
        # Negation of %esi
        negl %esi
        movl %esi, i0
        # Emitting i1 = +(-(+(-(-(4)))))
        # Assignment of i1 (1 registers needed)
        # Load of integer constant 4
        movl $4, %esi
        # Negation of %esi
        negl %esi
        # Negation of %esi
        negl %esi
        # Negation of %esi
        negl %esi
        movl %esi, i1
        # Emitting i0 = (-(i1) - -(+(i1)))
        # Assignment of i0 (2 registers needed)
        # Load of variable i1 into register %esi
        movl i1, %esi
        # Negation of %esi
        negl %esi
        # Load of variable i1 into register %edi
        movl i1, %edi
        # Negation of %edi
        negl %edi
        # %edi = %edi - %esi
        subl %esi, %edi
        movl %edi, i0
        # Emitting write(i0)
        # Load of variable i0 into register %edi
        movl i0, %edi
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
        # Emitting write(i1)
        # Load of variable i1 into register %edi
        movl i1, %edi
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
        # Emitting write(i2)
        # Load of variable i2 into register %edi
        movl i2, %edi
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
    # Restores base pointer
    popl %ebp
    # Return 0
    movl $0, %eax
    retl
