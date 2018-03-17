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
        # Emitting r1 = (i0 + i1)
        # Assignment of r1 (2 registers needed)
        # Load of variable i1 into register %edi
        movl i1, %edi
        # Load of variable i0 into register %esi
        movl i0, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        movl %esi, r1
        # Emitting write(r1)
        # Load of variable r1 into register %esi
        movl r1, %esi
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
        # Emitting write((r1 - 3))
        # Load of integer constant 3
        movl $3, %esi
        # Load of variable r1 into register %edi
        movl r1, %edi
        # %edi = %edi - %esi
        subl %esi, %edi
        # --- Write of value in req %edi---
        # 2 registers needed
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
