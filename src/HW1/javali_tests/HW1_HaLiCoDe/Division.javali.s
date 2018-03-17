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
    # Main function declaration and body
    .text
    .global _main
    _main:
    # Asm function prolog
    pushl %ebp
    movl %esp, %ebp
    .align 16
      # Emitting (...)
        # Emitting i0 = 1
        # Assignment of i0 (1 registers needed)
        # Load of integer constant 1
        movl $1, %edi
        movl %edi, i0
        # Emitting i1 = 2
        # Assignment of i1 (1 registers needed)
        # Load of integer constant 2
        movl $2, %edi
        movl %edi, i1
        # Emitting i2 = 3
        # Assignment of i2 (1 registers needed)
        # Load of integer constant 3
        movl $3, %edi
        movl %edi, i2
        # Emitting i3 = (i2 / i1)
        # Assignment of i3 (2 registers needed)
        # Load of variable i1 into register %edi
        movl i1, %edi
        # Load of variable i2 into register %esi
        movl i2, %esi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        movl %esi, i3
        # Emitting write(i3)
        # Load of variable i3 into register %esi
        movl i3, %esi
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
        # Emitting i3 = (i2 / i0)
        # Assignment of i3 (2 registers needed)
        # Load of variable i0 into register %esi
        movl i0, %esi
        # Load of variable i2 into register %edi
        movl i2, %edi
        # %edi = %edi / %esi
        movl %edi, %eax
        cdq
        idivl %esi
        movl %eax, %edi
        movl %edi, i3
        # Emitting write(i3)
        # Load of variable i3 into register %edi
        movl i3, %edi
        # --- Write of value in req %edi---
        # 1 registers needed
        subl $8, %esp
        movl %edi, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _printf
        addl $8, %esp
        # --------
        # Emitting i3 = (i1 / i0)
        # Assignment of i3 (2 registers needed)
        # Load of variable i0 into register %edi
        movl i0, %edi
        # Load of variable i1 into register %esi
        movl i1, %esi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        movl %esi, i3
        # Emitting write(i3)
        # Load of variable i3 into register %esi
        movl i3, %esi
        # --- Write of value in req %esi---
        # 1 registers needed
        subl $8, %esp
        movl %esi, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _printf
        addl $8, %esp
        # --------
        # Emitting i3 = (i0 / i3)
        # Assignment of i3 (2 registers needed)
        # Load of variable i3 into register %esi
        movl i3, %esi
        # Load of variable i0 into register %edi
        movl i0, %edi
        # %edi = %edi / %esi
        movl %edi, %eax
        cdq
        idivl %esi
        movl %eax, %edi
        movl %edi, i3
        # Emitting write(i3)
        # Load of variable i3 into register %edi
        movl i3, %edi
        # --- Write of value in req %edi---
        # 1 registers needed
        subl $8, %esp
        movl %edi, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _printf
        addl $8, %esp
        # --------
    # Restores base pointer
    popl %ebp
    # Return 0
    movl $0, %eax
    retl
