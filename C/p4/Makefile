CC = gcc
CFLAGS = -g -Wall -std=c99
LDLIBS = -lm

drawing: model.o scene.o
drawing.o: model.h
drawing.o: scene.h
model.o: model.h
scene.o: scene.h

clean:
	rm -f drawing.o
	rm -f drawing
	rm -f drawing.c~
	rm -f model.o
	rm -f o.txt

