/*
 * Compile with -lseccomp 
 */
#include <unistd.h>
#include <seccomp.h>
#include <linux/seccomp.h>

int main(void) {
	scmp_filter_ctx ctx;
	ctx = seccomp_init(SCMP_ACT_ALLOW);
	seccomp_rule_add(ctx, SCMP_ACT_KILL, SCMP_SYS(execve), 0);
	seccomp_load(ctx);
	char *pname = "/bin/sh";
	char *pargv[] = {"/bin/sh", NULL};
	char *penvp[] = {NULL};
	write(1, "spawning a shell: \n", 19);
	/* syscall 59: execve */
	syscall(59, pname, pargv, penvp);
	return 0;
}
