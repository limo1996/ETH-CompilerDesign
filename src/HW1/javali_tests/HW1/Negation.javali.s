  # Emitting class Main {...}
    # Emitting void main(...) {...}
    # Declaration of printf and scanf strings
    .cstring
    STR_D: .asciz "%d"
    NEW_L: .asciz "\n"
    .data
    READ_VAL: .long 0
      # Emitting (...)
        # Emitting int A
        # Declaration of variable A
        A: .long 0
        # Emitting int B
        # Declaration of variable B
        B: .long 0
        # Emitting int a
        # Declaration of variable a
        a: .long 0
        # Emitting int b
        # Declaration of variable b
        b: .long 0
        # Emitting int c
        # Declaration of variable c
        c: .long 0
        # Emitting int d
        # Declaration of variable d
        d: .long 0
    # Main function declaration and body
    .text
    .global _main
    _main:
    # Asm function prolog
    pushl %ebp
    movl %esp, %ebp
    .align 16
      # Emitting (...)
        # Emitting A = 1
        # Assignment of A (1 registers needed)
        # Load of integer constant 1
        movl $1, %edi
        movl %edi, A
        # Emitting B = 1
        # Assignment of B (1 registers needed)
        # Load of integer constant 1
        movl $1, %edi
        movl %edi, B
        # Emitting a = (A * -(B))
        # Assignment of a (2 registers needed)
        # Load of variable B into register %edi
        movl B, %edi
        # Negation of %edi
        negl %edi
        # Load of variable A into register %esi
        movl A, %esi
        # %esi = %esi * %edi
        imull %edi, %esi
        movl %esi, a
        # Emitting b = (-(A) * B)
        # Assignment of b (2 registers needed)
        # Load of variable B into register %esi
        movl B, %esi
        # Load of variable A into register %edi
        movl A, %edi
        # Negation of %edi
        negl %edi
        # %edi = %edi * %esi
        imull %esi, %edi
        movl %edi, b
        # Emitting c = -((A + B))
        # Assignment of c (1 registers needed)
        # Load of variable B into register %edi
        movl B, %edi
        # Load of variable A into register %esi
        movl A, %esi
        # %esi = %esi + %edi
        addl %edi, %esi
        # Negation of %esi
        negl %esi
        movl %esi, c
        # Emitting d = -((A * B))
        # Assignment of d (1 registers needed)
        # Load of variable B into register %esi
        movl B, %esi
        # Load of variable A into register %edi
        movl A, %edi
        # %edi = %edi * %esi
        imull %esi, %edi
        # Negation of %edi
        negl %edi
        movl %edi, d
        # Emitting write(a)
        # Load of variable a into register %edi
        movl a, %edi
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
        # Emitting write(b)
        # Load of variable b into register %edi
        movl b, %edi
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
        # Emitting write(c)
        # Load of variable c into register %edi
        movl c, %edi
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
        # Emitting write(d)
        # Load of variable d into register %edi
        movl d, %edi
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
