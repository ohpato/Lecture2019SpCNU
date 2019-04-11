#include <stdio.h>
#include <unistd.h>

int main(int argc, char **argv)
{
	setreuid(0,0);
	execve("/bin/sh", NULL, NULL);
}

