# To make an attack easy, we use the following options:
# 	-fno-stack-protector: disable the stack smashing protector
#		(https://wiki.osdev.org/Stack_Smashing_Protector) 
# 	-z execstack: make stack executable (the stack of ELF binaries or shared libraries) 
# Also, do the below to disable ASLR
#       $ sudo sh -c "echo 0 > /proc/sys/kernel/randomize_va_space"
#
TARGET = whatamess
$(TARGET):	$(TARGET).c
	gcc -O0 -o $(TARGET) $(TARGET).c -fno-stack-protector -z execstack
clean: 
	rm -f $(TARGET) *~
