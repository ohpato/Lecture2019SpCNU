# in order to disable such stack protections as ASLR, NX(no execute bits),
# and stack canaries, the following options are needed:
# 	-fno-stack-protector 
# 	-z execstack 
# -m32 is to compile it as a 32-bit binary
# also, do this before exploit it
#     $ sudo sh -c "echo 0 > /proc/sys/kernel/randomize_va_space"
whatamess:	whatamess.c
	gcc -O0 -o whatamess whatamess.c -fno-stack-protector -z execstack
