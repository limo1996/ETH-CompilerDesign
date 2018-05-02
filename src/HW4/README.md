# Stack Alignment - HaLiCoDe
 * C like
 * Locals below base pointer
 * Arguments are above
 * (stack decreasing)
 * first parameter always caller (object method is called on)

## Alignment
Going from high to low addresses.


Main.main
ebp
locals
temps
--------------------------------
Stack padding -> to ensure 16-byte alignment
--------------------------------
arguments
1. argument --> caller (i.e. this ref)
return address
---------------------------------------------
user caller stack frame
-------------------------------
ebp
locals
temps
temp k <- ESP
