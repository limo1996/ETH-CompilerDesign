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
        # Emitting write((4 + (9 * 8)))
        # Load of integer constant 8
        movl $8, %edi
        # Load of integer constant 9
        movl $9, %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        # Load of integer constant 4
        movl $4, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # --- Write of value in req %edi---
        # 2 registers needed
        subl $8, %esp
        movl %edi, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _printf
        addl $8, %esp
        # --------
        # Emitting write((4 * (9 - -(-(5)))))
        # Load of integer constant 5
        movl $5, %edi
        # Negation of %edi
        negl %edi
        # Negation of %edi
        negl %edi
        # Load of integer constant 9
        movl $9, %esi
        # %esi = %esi - %edi
        subl %edi, %esi
        # Load of integer constant 4
        movl $4, %edi
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
        # Emitting write((((((4 + (9 * 8)) + 7) - 9) + (-(8) * 7)) - 4))
        # Load of integer constant 7
        movl $7, %edi
        # Load of integer constant 8
        movl $8, %esi
        # Negation of %esi
        negl %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        # Load of integer constant 8
        movl $8, %edi
        # Load of integer constant 9
        movl $9, %edx
        # %edx = %edx * %edi
        imull %edi, %edx
        # Load of integer constant 4
        movl $4, %edi
        # %edi = %edi + %edx
        addl %edx, %edi
        # Load of integer constant 7
        movl $7, %edx
        # %edi = %edi + %edx
        addl %edx, %edi
        # Load of integer constant 9
        movl $9, %edx
        # %edi = %edi - %edx
        subl %edx, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        # Load of integer constant 4
        movl $4, %esi
        # %edi = %edi - %esi
        subl %esi, %edi
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
        # Emitting write(((((((3 * 3) - 9) + (((1 * 2) * 1) * 87)) - 93) + (34 * 89)) - 100))
        # Load of integer constant 2
        movl $2, %edi
        # Load of integer constant 1
        movl $1, %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        # Load of integer constant 1
        movl $1, %edi
        # %esi = %esi * %edi
        imull %edi, %esi
        # Load of integer constant 87
        movl $87, %edi
        # %esi = %esi * %edi
        imull %edi, %esi
        # Load of integer constant 3
        movl $3, %edi
        # Load of integer constant 3
        movl $3, %edx
        # %edx = %edx * %edi
        imull %edi, %edx
        # Load of integer constant 9
        movl $9, %edi
        # %edx = %edx - %edi
        subl %edi, %edx
        # %edx = %edx + %esi
        addl %esi, %edx
        # Load of integer constant 93
        movl $93, %esi
        # %edx = %edx - %esi
        subl %esi, %edx
        # Load of integer constant 89
        movl $89, %esi
        # Load of integer constant 34
        movl $34, %edi
        # %edi = %edi * %esi
        imull %esi, %edi
        # %edx = %edx + %edi
        addl %edi, %edx
        # Load of integer constant 100
        movl $100, %edi
        # %edx = %edx - %edi
        subl %edi, %edx
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
        # Emitting i0 = ((((((778 + (90 * 7)) - (5 * 4)) - 3) + (9 * 90)) + 3) + (9 * 8))
        # Assignment of i0 (3 registers needed)
        # Load of integer constant 4
        movl $4, %edx
        # Load of integer constant 5
        movl $5, %edi
        # %edi = %edi * %edx
        imull %edx, %edi
        # Load of integer constant 7
        movl $7, %edx
        # Load of integer constant 90
        movl $90, %esi
        # %esi = %esi * %edx
        imull %edx, %esi
        # Load of integer constant 778
        movl $778, %edx
        # %edx = %edx + %esi
        addl %esi, %edx
        # %edx = %edx - %edi
        subl %edi, %edx
        # Load of integer constant 3
        movl $3, %edi
        # %edx = %edx - %edi
        subl %edi, %edx
        # Load of integer constant 90
        movl $90, %edi
        # Load of integer constant 9
        movl $9, %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        # %edx = %edx + %esi
        addl %esi, %edx
        # Load of integer constant 3
        movl $3, %esi
        # %edx = %edx + %esi
        addl %esi, %edx
        # Load of integer constant 8
        movl $8, %esi
        # Load of integer constant 9
        movl $9, %edi
        # %edi = %edi * %esi
        imull %esi, %edi
        # %edx = %edx + %edi
        addl %edi, %edx
        movl %edx, i0
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
    # Restores base pointer
    popl %ebp
    # Return 0
    movl $0, %eax
    retl
