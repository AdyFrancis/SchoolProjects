/**
 * This program encrypts
 * @file encrypt.c
 * @author Ady Francis
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "codes.h"
#include "bits.h"

/** Number of expected arguments */
#define ARG_COUNT 3
/** Index of the input file name within argument array */
#define IN_FILE 1
/** Index of the output file name within argument array */
#define OUT_FILE 2
/** Unsuccessful exit code */
#define EXIT_FAIL 1


/**
 *
 *
 */
void readFile(char *inFile, char *outFile)
{

    BitBuffer *buffer = (BitBuffer *)malloc(sizeof(BitBuffer));
    FILE *fr = fopen(inFile, "r");
    if (!fr) {
        perror(inFile);
        exit(EXIT_FAIL);
    }
 
     FILE *fw = fopen(outFile, "wb");
     if (!fw) {
        perror(outFile);
        exit(EXIT_FAIL);
    }
    char ch;
    while ((ch = fgetc(fr)) != EOF) {
        int code = symToCode(tolower(ch));
        if (code == INVALID || islower(ch)) {
            fprintf(stderr, "Invalid file\n");
            exit(EXIT_FAIL);
        }
        writeBits(code, bitsInCode(tolower(ch)), buffer, fw);
    }
    if(buffer->bcount != 0)
        fillBuffer(buffer, fw);

    free(buffer);
    fclose(fr);
    fclose(fw);
}

/**
 * Starting point of the program
 * @param argc the number of arguments provided in command line
 * @param argv array of command line arguments
 * @return exit status of the program
 */
int main(int argc, char *argv[])
{
    if (argc != ARG_COUNT) {
        fprintf(stderr, "usage: encrypt <infile> <outfile>\n");
        exit(EXIT_FAIL);
    }

    readFile(argv[IN_FILE], argv[OUT_FILE]);

    exit(EXIT_SUCCESS);
}
