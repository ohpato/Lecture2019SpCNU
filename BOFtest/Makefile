# To disable such stack protections as ASLR, NX(no execute bits),
# and stack canaries, the following options are needed:
# 	-fno-stack-protector 
# 	-z execstack 
# Also, do the below to disable ASLR
#       $ sudo sh -c "echo 0 > /proc/sys/kernel/randomize_va_space"
#
whatamess:	whatamess.c
	gcc -O0 -o whatamess whatamess.c -fno-stack-protector -z execstack
