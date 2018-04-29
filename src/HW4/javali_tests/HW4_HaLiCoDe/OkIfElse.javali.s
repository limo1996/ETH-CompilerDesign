  # Emitting class Main {...}
    # Emitting void main(...) {...}
    .section .data
STR_NL:
    .string "\n"
STR_D:
    .string "%d"
    .section .data
var_i:
    .int 0
var_sum:
    .int 0
    .section .text
    .globl main
main:
    enter $8, $0
    and $-16, %esp
      # Emitting (...)
        # Emitting i = 0
          # Emitting 0
          movl $0, %edi
        movl %edi, var_i
        # Emitting sum = 0
          # Emitting 0
          movl $0, %edi
        movl %edi, var_sum
        # Emitting while ((i <= 10)) {...}
        jmp label0
label1:
          # Emitting (...)
            # Emitting if (((i % 2) == 0)) {...} else {...}
              # Emitting ((i % 2) == 0)
                # Emitting (i % 2)
                  # Emitting 2
                  movl $2, %edi
                  # Emitting i
                  movl var_i, %esi
                cmpl $0, %edi
                jne label2
                pushl $7
                call exit
label2:
                pushl %edi
                pushl %esi
                popl %eax
                popl %ebx
                cltd
                idivl %ebx
                movl %edx, %esi
                # Emitting 0
                movl $0, %edi
              cmpl %edi, %esi
              je label3
              movl $0, %esi
              jmp label4
label3:
              movl $1, %esi
label4:
            cmpl $0, %esi
            je label5
              # Emitting (...)
                # Emitting sum = (sum + i)
                  # Emitting (sum + i)
                    # Emitting i
                    movl var_i, %edi
                    # Emitting sum
                    movl var_sum, %edx
                  add %edi, %edx
                movl %edx, var_sum
            jmp label6
label5:
              # Emitting (...)
                # Emitting sum = ((sum + 10) - i)
                  # Emitting ((sum + 10) - i)
                    # Emitting (sum + 10)
                      # Emitting 10
                      movl $10, %edx
                      # Emitting sum
                      movl var_sum, %edi
                    add %edx, %edi
                    # Emitting i
                    movl var_i, %edx
                  sub %edx, %edi
                movl %edi, var_sum
label6:
            # Emitting i = (i + 1)
              # Emitting (i + 1)
                # Emitting 1
                movl $1, %edi
                # Emitting i
                movl var_i, %edx
              add %edi, %edx
            movl %edx, var_i
label0:
          # Emitting (i <= 10)
            # Emitting 10
            movl $10, %edx
            # Emitting i
            movl var_i, %edi
          cmpl %edx, %edi
          jbe label7
          movl $0, %edi
          jmp label8
label7:
          movl $1, %edi
label8:
        cmpl $0, %edi
        jne label1
        # Emitting write(sum)
          # Emitting sum
          movl var_sum, %edx
        sub $16, %esp
        movl %edx, 4(%esp)
        movl $STR_D, 0(%esp)
        call printf
        add $16, %esp
    movl $0, %eax
    leave
    ret
