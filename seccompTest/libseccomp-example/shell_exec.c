#include <unistd.h>

int main(void) {
	char *pname = "/bin/sh";
	char *pargv[] = {"/bin/sh", NULL};
	char *penvp[] = {NULL};
	write(1, "spawning a shell: \n", 19);
	/* syscall 59: execve */
	syscall(59, pname, pargv, penvp);
	return 0;
}
