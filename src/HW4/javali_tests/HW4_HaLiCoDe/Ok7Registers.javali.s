  # Emitting class Main {...}
    # Emitting void main(...) {...}
    .section .data
STR_NL:
    .string "\n"
STR_D:
    .string "%d"
    .section .data
var_a:
    .int 0
    .section .text
    .globl main
main:
    enter $8, $0
    and $-16, %esp
      # Emitting (...)
        # Emitting a = ((((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))) + (((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))))
          # Emitting ((((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))) + (((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))))
            # Emitting (((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))) + ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))))
              # Emitting ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))
                # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %esi
                        add %edi, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %edx
                        add %edi, %edx
                      add %esi, %edx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %edi
                        add %esi, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ecx
                        add %esi, %ecx
                      add %edi, %ecx
                    add %edx, %ecx
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %edi
                        add %edx, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %esi
                        add %edx, %esi
                      add %edi, %esi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %edx
                        add %edi, %edx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %ebx
                        add %edi, %ebx
                      add %edx, %ebx
                    add %esi, %ebx
                  add %ecx, %ebx
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %esi
                        add %ecx, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edx
                        add %ecx, %edx
                      add %esi, %edx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ecx
                        add %esi, %ecx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %edi
                        add %esi, %edi
                      add %ecx, %edi
                    add %edx, %edi
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ecx
                        add %edx, %ecx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %esi
                        add %edx, %esi
                      add %ecx, %esi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edx
                        add %ecx, %edx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %eax
                        add %ecx, %eax
                      add %edx, %eax
                    add %esi, %eax
                  add %edi, %eax
                add %ebx, %eax
              pushl %eax
                # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %ebx
                        add %eax, %ebx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edi
                        add %eax, %edi
                      add %ebx, %edi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %eax
                        add %ebx, %eax
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %esi
                        add %ebx, %esi
                      add %eax, %esi
                    add %edi, %esi
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %eax
                        add %edi, %eax
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %ebx
                        add %edi, %ebx
                      add %eax, %ebx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edi
                        add %eax, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edx
                        add %eax, %edx
                      add %edi, %edx
                    add %ebx, %edx
                  add %esi, %edx
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ebx
                        add %esi, %ebx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %edi
                        add %esi, %edi
                      add %ebx, %edi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %esi
                        add %ebx, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %eax
                        add %ebx, %eax
                      add %esi, %eax
                    add %edi, %eax
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %esi
                        add %edi, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %ebx
                        add %edi, %ebx
                      add %esi, %ebx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %edi
                        add %esi, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ecx
                        add %esi, %ecx
                      add %edi, %ecx
                    add %ebx, %ecx
                  add %eax, %ecx
                add %edx, %ecx
              popl %edx
              add %edx, %ecx
            pushl %ecx
              # Emitting ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))
                # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edx
                        add %ecx, %edx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %eax
                        add %ecx, %eax
                      add %edx, %eax
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ecx
                        add %edx, %ecx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ebx
                        add %edx, %ebx
                      add %ecx, %ebx
                    add %eax, %ebx
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %ecx
                        add %eax, %ecx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edx
                        add %eax, %edx
                      add %ecx, %edx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %eax
                        add %ecx, %eax
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edi
                        add %ecx, %edi
                      add %eax, %edi
                    add %edx, %edi
                  add %ebx, %edi
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %edx
                        add %ebx, %edx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %eax
                        add %ebx, %eax
                      add %edx, %eax
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ebx
                        add %edx, %ebx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ecx
                        add %edx, %ecx
                      add %ebx, %ecx
                    add %eax, %ecx
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %ebx
                        add %eax, %ebx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edx
                        add %eax, %edx
                      add %ebx, %edx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %eax
                        add %ebx, %eax
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %esi
                        add %ebx, %esi
                      add %eax, %esi
                    add %edx, %esi
                  add %ecx, %esi
                add %edi, %esi
              pushl %esi
                # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %edi
                        add %esi, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ecx
                        add %esi, %ecx
                      add %edi, %ecx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %esi
                        add %edi, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %edx
                        add %edi, %edx
                      add %esi, %edx
                    add %ecx, %edx
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %esi
                        add %ecx, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edi
                        add %ecx, %edi
                      add %esi, %edi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ecx
                        add %esi, %ecx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %eax
                        add %esi, %eax
                      add %ecx, %eax
                    add %edi, %eax
                  add %edx, %eax
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %edi
                        add %edx, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ecx
                        add %edx, %ecx
                      add %edi, %ecx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %edx
                        add %edi, %edx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %esi
                        add %edi, %esi
                      add %edx, %esi
                    add %ecx, %esi
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edx
                        add %ecx, %edx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edi
                        add %ecx, %edi
                      add %edx, %edi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ecx
                        add %edx, %ecx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ebx
                        add %edx, %ebx
                      add %ecx, %ebx
                    add %edi, %ebx
                  add %esi, %ebx
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
                          # Emitting 1
                          movl $1, %eax
                        add %ebx, %eax
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %esi
                        add %ebx, %esi
                      add %eax, %esi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %ebx
                        add %eax, %ebx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edi
                        add %eax, %edi
                      add %ebx, %edi
                    add %esi, %edi
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ebx
                        add %esi, %ebx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %eax
                        add %esi, %eax
                      add %ebx, %eax
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %esi
                        add %ebx, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %ecx
                        add %ebx, %ecx
                      add %esi, %ecx
                    add %eax, %ecx
                  add %edi, %ecx
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %eax
                        add %edi, %eax
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %esi
                        add %edi, %esi
                      add %eax, %esi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edi
                        add %eax, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %ebx
                        add %eax, %ebx
                      add %edi, %ebx
                    add %esi, %ebx
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %edi
                        add %esi, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %eax
                        add %esi, %eax
                      add %edi, %eax
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %esi
                        add %edi, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %edx
                        add %edi, %edx
                      add %esi, %edx
                    add %eax, %edx
                  add %ebx, %edx
                add %ecx, %edx
              pushl %edx
                # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ecx
                        add %edx, %ecx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ebx
                        add %edx, %ebx
                      add %ecx, %ebx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edx
                        add %ecx, %edx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %eax
                        add %ecx, %eax
                      add %edx, %eax
                    add %ebx, %eax
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %edx
                        add %ebx, %edx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %ecx
                        add %ebx, %ecx
                      add %edx, %ecx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ebx
                        add %edx, %ebx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %esi
                        add %edx, %esi
                      add %ebx, %esi
                    add %ecx, %esi
                  add %eax, %esi
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %ecx
                        add %eax, %ecx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %ebx
                        add %eax, %ebx
                      add %ecx, %ebx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %eax
                        add %ecx, %eax
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edx
                        add %ecx, %edx
                      add %eax, %edx
                    add %ebx, %edx
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %eax
                        add %ebx, %eax
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %ecx
                        add %ebx, %ecx
                      add %eax, %ecx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %ebx
                        add %eax, %ebx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edi
                        add %eax, %edi
                      add %ebx, %edi
                    add %ecx, %edi
                  add %edx, %edi
                add %esi, %edi
              popl %esi
              add %esi, %edi
            pushl %edi
              # Emitting ((((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))) + (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))))
                # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %esi
                        add %edi, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %edx
                        add %edi, %edx
                      add %esi, %edx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %edi
                        add %esi, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ecx
                        add %esi, %ecx
                      add %edi, %ecx
                    add %edx, %ecx
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %edi
                        add %edx, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %esi
                        add %edx, %esi
                      add %edi, %esi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %edx
                        add %edi, %edx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %ebx
                        add %edi, %ebx
                      add %edx, %ebx
                    add %esi, %ebx
                  add %ecx, %ebx
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %esi
                        add %ecx, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edx
                        add %ecx, %edx
                      add %esi, %edx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ecx
                        add %esi, %ecx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %edi
                        add %esi, %edi
                      add %ecx, %edi
                    add %edx, %edi
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %ecx
                        add %edx, %ecx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edx
                          # Emitting 1
                          movl $1, %esi
                        add %edx, %esi
                      add %ecx, %esi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %edx
                        add %ecx, %edx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ecx
                          # Emitting 1
                          movl $1, %eax
                        add %ecx, %eax
                      add %edx, %eax
                    add %esi, %eax
                  add %edi, %eax
                add %ebx, %eax
              pushl %eax
                # Emitting (((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))) + ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))))
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %ebx
                        add %eax, %ebx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edi
                        add %eax, %edi
                      add %ebx, %edi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %eax
                        add %ebx, %eax
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %esi
                        add %ebx, %esi
                      add %eax, %esi
                    add %edi, %esi
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %eax
                        add %edi, %eax
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %ebx
                        add %edi, %ebx
                      add %eax, %ebx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edi
                        add %eax, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %eax
                          # Emitting 1
                          movl $1, %edx
                        add %eax, %edx
                      add %edi, %edx
                    add %ebx, %edx
                  add %esi, %edx
                  # Emitting ((((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))) + (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1))))
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ebx
                        add %esi, %ebx
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %edi
                        add %esi, %edi
                      add %ebx, %edi
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %esi
                        add %ebx, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %ebx
                          # Emitting 1
                          movl $1, %eax
                        add %ebx, %eax
                      add %esi, %eax
                    add %edi, %eax
                    # Emitting (((1 + 1) + (1 + 1)) + ((1 + 1) + (1 + 1)))
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %esi
                        add %edi, %esi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %edi
                          # Emitting 1
                          movl $1, %ebx
                        add %edi, %ebx
                      add %esi, %ebx
                      # Emitting ((1 + 1) + (1 + 1))
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %edi
                        add %esi, %edi
                        # Emitting (1 + 1)
                          # Emitting 1
                          movl $1, %esi
                          # Emitting 1
                          movl $1, %ecx
                        add %esi, %ecx
                      add %edi, %ecx
                    add %ebx, %ecx
                  add %eax, %ecx
                add %edx, %ecx
              popl %edx
              add %edx, %ecx
            popl %edx
            add %edx, %ecx
          popl %edx
          add %edx, %ecx
        movl %ecx, var_a
    movl $0, %eax
    leave
    ret
