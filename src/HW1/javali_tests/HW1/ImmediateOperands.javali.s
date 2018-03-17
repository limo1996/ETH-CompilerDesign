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
        # Emitting i0 = 0
        # Assignment of i0 (1 registers needed)
        # Load of integer constant 0
        movl $0, %edi
        movl %edi, i0
        # Emitting i0 = (5 + i0)
        # Assignment of i0 (2 registers needed)
        # Load of variable i0 into register %edi
        movl i0, %edi
        # Load of integer constant 5
        movl $5, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        movl %esi, i0
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
        # Emitting i0 = (i0 + 5)
        # Assignment of i0 (2 registers needed)
        # Load of integer constant 5
        movl $5, %esi
        # Load of variable i0 into register %edi
        movl i0, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
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
        # Emitting i0 = ((i0 + 5) + 3)
        # Assignment of i0 (2 registers needed)
        # Load of integer constant 5
        movl $5, %edi
        # Load of variable i0 into register %esi
        movl i0, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of integer constant 3
        movl $3, %edi
        # %esi = %esi + %edi
        addl %edi, %esi
        movl %esi, i0
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
    # Restores base pointer
    popl %ebp
    # Return 0
    movl $0, %eax
    retl
