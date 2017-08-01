CC = gcc -g
CFLAGS = -Wall -std=c99

all: encrypt decrypt
encrypt: bits.o codes.o
decrypt: bits.o codes.o
encrypt.o: bits.h codes.h
decrypt.o: bits.h codes.h
bits.o: bits.h
codes.o: codes.h

clean:
	rm -f encrypt
	rm -f decrypt
	rm -f encrypt.c~
	rm -f decrypt.c~
	rm -f bits.c~
	rm -f codes.c~
	rm -f bits.h~
	rm -f codes.h~
	rm -f codes.o
	rm -f bits.o
