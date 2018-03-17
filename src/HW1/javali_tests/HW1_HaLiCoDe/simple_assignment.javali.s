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
        # Emitting i0 = ((((i0 + 5) + 10) - 13) + 2)
        # Assignment of i0 (2 registers needed)
        # Load of integer constant 5
        movl $5, %edi
        # Load of variable i0 into register %esi
        movl i0, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of integer constant 10
        movl $10, %edi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Load of integer constant 13
        movl $13, %edi
        # %esi = %esi - %edi
        subl %edi, %esi
        # Load of integer constant 2
        movl $2, %edi
        # %esi = %esi + %edi
        addl %edi, %esi
        movl %esi, i0
        # Emitting i1 = (i0 + 5)
        # Assignment of i1 (2 registers needed)
        # Load of integer constant 5
        movl $5, %esi
        # Load of variable i0 into register %edi
        movl i0, %edi
        # %edi = %edi + %esi
        addl %esi, %edi
        movl %edi, i1
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
    # Restores base pointer
    popl %ebp
    # Return 0
    movl $0, %eax
    retl
