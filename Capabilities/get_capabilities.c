/*
 * To compile it, install the "libcap_dev" package first.
 * Then, compile this file with "-lcap".
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/capability.h>

int main(int argc, char **argv)
{
	pid_t pid;
	cap_t pcaps;
	char *caps_text = NULL;

	if (argc < 2) {
		printf("Usage: %s PID\n", argv[0]);
		exit(EXIT_FAILURE);
	}

	pid = atoi(argv[1]);
	pcaps = cap_get_pid(pid);
	if (!pcaps) {
		printf("Error: cap_get_pid(). Check PID\n");
		exit(EXIT_FAILURE);
	}

	/* capset status will be returned as a list of cap_t, 
	   so the list should be converted to be readable strings */
	caps_text = cap_to_text(pcaps, NULL);
	if (!caps_text) {
		printf("Error: caps_to_text()\n");
		exit(EXIT_FAILURE);
	}

	printf("The process has the following capabilities\n %s\n", caps_text);
	cap_free(caps_text); /* do not forget to call cap_free */

	exit(EXIT_SUCCESS);
}
