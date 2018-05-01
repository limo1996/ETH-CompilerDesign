# VTables region
.data
# VTable for Object
vtable_Object:
.long 0
# VTable for Main
vtable_Main:
.long 0
.long func_Main_main
.cstring
STR_NL:
.asciz "\n"
STR_D:
.asciz "%d"
.text
.global _main
_main:
enter $8, $0
and $-16, %esp
# Pushing arguments on the stack
subl $16, %esp
movl $1, 0(%esp)
movl $4, 4(%esp)
call _calloc
movl %eax, %edi
# Poping arguments
addl $16, %esp
leal vtable_Main, %esi
movl %esi, 0(%edi)
movl %edi, 0(%esp)
addl $4, %esi
movl 0(%esi), %esi
call *%esi
movl $0, %eax
leave
ret
  # Emitting void main(...) {...}
func_Main_main:
  enter $4, $0
  movl $0, -4(%ebp)
  # Saving registers onto the stack
  pushl %esi
  pushl %edi
  pushl %ebx
    # Emitting (...)
      # Emitting a = ((((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))) + (((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))))
        # Emitting a
        leal -4(%ebp), %edi
        # Emitting ((((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))) + (((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))))
          # Emitting (((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))))
            # Emitting ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))
              # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %esi
                        # Emitting 1
                        movl $1, %edx
                      add %esi, %edx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %esi
                        # Emitting 1
                        movl $1, %ecx
                      add %esi, %ecx
                    add %edx, %ecx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %edx
                        # Emitting 1
                        movl $1, %esi
                      add %edx, %esi
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %edx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              pushl %ebx
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              popl %eax
              add %eax, %ebx
            pushl %ebx
              # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              pushl %ebx
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              popl %eax
              add %eax, %ebx
            popl %eax
            add %eax, %ebx
          pushl %ebx
            # Emitting ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))
              # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              pushl %ebx
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              popl %eax
              add %eax, %ebx
            pushl %ebx
              # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              pushl %ebx
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              popl %eax
              add %eax, %ebx
            popl %eax
            add %eax, %ebx
          popl %eax
          add %eax, %ebx
        pushl %ebx
          # Emitting (((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))))
            # Emitting ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))
              # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              pushl %ebx
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              popl %eax
              add %eax, %ebx
            pushl %ebx
              # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              pushl %ebx
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              popl %eax
              add %eax, %ebx
            popl %eax
            add %eax, %ebx
          pushl %ebx
            # Emitting ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))
              # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              pushl %ebx
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              popl %eax
              add %eax, %ebx
            pushl %ebx
              # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              pushl %ebx
                # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                pushl %ebx
                  # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  pushl %ebx
                    # Emitting ((1 + 1) + (1 + 1))
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    pushl %ebx
                      # Emitting (1 + 1)
                        # Emitting 1
                        movl $1, %ebx
                      pushl %ebx
                        # Emitting 1
                        movl $1, %ebx
                      popl %eax
                      add %eax, %ebx
                    popl %eax
                    add %eax, %ebx
                  popl %eax
                  add %eax, %ebx
                popl %eax
                add %eax, %ebx
              popl %eax
              add %eax, %ebx
            popl %eax
            add %eax, %ebx
          popl %eax
          add %eax, %ebx
        popl %eax
        add %eax, %ebx
      movl %ebx, 0(%edi)
  # Restoring registers from the stack
  popl %ebx
  popl %edi
  popl %esi
  movl $0, %eax
  leave
  ret
