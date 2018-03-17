  # Emitting class Main {...}
    # Emitting void main(...) {...}
    # Declaration of printf and scanf strings
    .cstring
    STR_D: .asciz "%d"
    NEW_L: .asciz "\n"
    .data
    READ_VAL: .long 0
      # Emitting (...)
        # Emitting int a
        # Declaration of variable a
        a: .long 0
    # Main function declaration and body
    .text
    .global _main
    _main:
    # Asm function prolog
    pushl %ebp
    movl %esp, %ebp
    .align 16
      # Emitting (...)
        # Emitting a = 1073741824
        # Assignment of a (1 registers needed)
        # Load of integer constant 1073741824
        movl $1073741824, %edi
        movl %edi, a
        # Emitting write(((((((((a / 2) / 4) / 8) / 16) / 32) / 64) / 128) / 256))
        # Load of integer constant 2
        movl $2, %edi
        # Load of variable a into register %esi
        movl a, %esi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of integer constant 4
        movl $4, %edi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of integer constant 8
        movl $8, %edi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of integer constant 16
        movl $16, %edi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of integer constant 32
        movl $32, %edi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of integer constant 64
        movl $64, %edi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of integer constant 128
        movl $128, %edi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # Load of integer constant 256
        movl $256, %edi
        # %esi = %esi / %edi
        movl %esi, %eax
        cdq
        idivl %edi
        movl %eax, %esi
        # --- Write of value in req %esi---
        # 2 registers needed
        subl $8, %esp
        movl %esi, 4(%esp)
        movl $STR_D, 0(%esp)
        calll _printf
        addl $8, %esp
        # --------
    # Restores base pointer
    popl %ebp
    # Return 0
    movl $0, %eax
    retl
