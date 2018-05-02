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
  enter $8, $0
  movl $0, -4(%ebp)
  movl $0, -8(%ebp)
  # Saving registers onto the stack
  pushl %esi
  pushl %edi
  pushl %ebx
    # Emitting (...)
      # Emitting i = 0
        # Emitting i
        leal -4(%ebp), %edi
        # Emitting 0
        movl $0, %esi
      movl %esi, 0(%edi)
      # Emitting sum = 0
        # Emitting sum
        leal -8(%ebp), %esi
        # Emitting 0
        movl $0, %edi
      movl %edi, 0(%esi)
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
                movl -4(%ebp), %esi
              cmpl $0, %edi
              jne label2
              pushl $7
              call _exit
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
                # Emitting sum
                leal -8(%ebp), %esi
                # Emitting (sum + i)
                  # Emitting i
                  movl -4(%ebp), %edi
                  # Emitting sum
                  movl -8(%ebp), %edx
                add %edi, %edx
              movl %edx, 0(%esi)
          jmp label6
label5:
            # Emitting (...)
              # Emitting sum = ((sum + 10) - i)
                # Emitting sum
                leal -8(%ebp), %edx
                # Emitting ((sum + 10) - i)
                  # Emitting (sum + 10)
                    # Emitting 10
                    movl $10, %esi
                    # Emitting sum
                    movl -8(%ebp), %edi
                  add %esi, %edi
                  # Emitting i
                  movl -4(%ebp), %esi
                sub %esi, %edi
              movl %edi, 0(%edx)
label6:
          # Emitting i = (i + 1)
            # Emitting i
            leal -4(%ebp), %edi
            # Emitting (i + 1)
              # Emitting 1
              movl $1, %edx
              # Emitting i
              movl -4(%ebp), %esi
            add %edx, %esi
          movl %esi, 0(%edi)
label0:
        # Emitting (i <= 10)
          # Emitting 10
          movl $10, %esi
          # Emitting i
          movl -4(%ebp), %edi
        cmpl %esi, %edi
        jle label7
        movl $0, %edi
        jmp label8
label7:
        movl $1, %edi
label8:
      cmpl $0, %edi
      jne label1
      # Emitting write(sum)
        # Emitting sum
        movl -8(%ebp), %edi
      # Pushing arguments on the stack
      subl $20, %esp
      movl $STR_D, 0(%esp)
      movl %edi, 4(%esp)
      call _printf
      # Poping arguments
      addl $20, %esp
  # Restoring registers from the stack
  popl %ebx
  popl %edi
  popl %esi
  movl $0, %eax
  leave
  ret
