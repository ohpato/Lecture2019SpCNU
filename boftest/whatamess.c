#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>

#define MAX_BUF	256

int analyze_sudoers(char *fname)
{
  char buf[MAX_BUF];
  int fd;

  if (0 < (fd = open(fname, O_RDONLY))) {
    read(fd, buf, 1024);

    printf("Analyze the configuration file...\n");
    // do something

    close( fd);
  } else {
    printf("file open failed.\n");
  }

  return 0;
}

int main(int argc, char *argv[])
{
  char buf[4096];
  printf("Welcome! This is a system config analyzer\n");

  if (argc < 2) {
    printf("Usage: %s <conf_file_name>\n", argv[0]);
  } else {
    analyze_sudoers(argv[1]);
  }

  //analyze_something_else();
  return 0;
}
