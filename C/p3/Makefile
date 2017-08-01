CC = gcc
CFLAGS = -Wall -std=c99

all: jumble puzzle

jumble: jumble.o

puzzle: grid.o
puzzle.o: grid.h
grid.o: grid.h

clean:
	rm -f jumble.o
	rm -f grid.o
	rm -f puzzle.o
	rm -f jumble
	rm -f puzzle
	rm -f grid.c~
	rm -f puzzle.c~
	rm -f jumble.c~
	rm -f grid.h~
