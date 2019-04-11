# in order to disable such stack protections as ASLR, NX(no execute bits),
# and stack canaries, the following options are needed:
# 	-fno-stack-protector 
# 	-z execstack 
# -m32 is to compile it as a 32-bit binary
whatamess:	whatamess.c
	gcc -O0 -o whatamess whatamess.c -mpreferred-stack-boundary=4 -fno-stack-protector -z execstack
