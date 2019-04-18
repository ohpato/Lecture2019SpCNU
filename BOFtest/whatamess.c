#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>

#define MAX_BUF	512

enum _CATEGORIES {
  CAT_PASSWD = 1,
  CAT_SHADOW,
  CAT_FSTAB,
  CAT_SUDOERS
};

int analyze_sudoers(char *fname)
{
	char buf[MAX_BUF];
	int fd;

	if (buf != NULL)
		printf("Buffer ready: CHECKED: %p\n", buf);

	if (0 < (fd = open(fname, O_RDONLY))) {
		read(fd, buf, 1024);

		printf("Analyzing the sudoers file...\n");
		// do actual things
		close(fd);
	} else {
		printf("file open failed.\n");
	}

	return 0;
}

int main(int argc, char *argv[])
{
	char buf[MAX_BUF];
	int cat;
	
	printf("Welcome! This is a system configuration analyzer.\n");

	if (argc < 3) {
		printf("Usage: %s <category> <conf_file_name>\n", argv[0]);
		printf(" - <category> := 1|2|3|4 \n");
		printf("\t\t 1 for the passwd file, 2 for the shadow file,\n");
		printf("\t\t 3 for the fstab, and 4 for the sudoers\n");
		printf(" - <conf_file_name> := file_name\n");
		printf("\t\t file_name can be /etc/passwd, /etc/shadow,\n");
		printf("\t\t /etc/fstab /etc/sudoers, etc. The name depends on distros\n");
	} else {
		cat = atoi(argv[1]);
		switch(cat) {
		case CAT_PASSWD:
			printf("Analyzing the password file\n");
			// TODO: analyze the password file
			break;
		CAT_SHADOW:
			printf("Analyzing the shadow file\n");
			// TODO: analyze the shadow file
			break;
		case CAT_FSTAB:
			printf("Analyzing the fatab file\n");
			// TODO: analyze the fstab file
			break;
		case CAT_SUDOERS:
			printf("Analyzing the sudoers file\n");
			analyze_sudoers(argv[2]);
			break;
		default:
			printf("Error: wrong category");
			return -1;
		}
	}
	//analyze_something_else();
	return 0;
}
