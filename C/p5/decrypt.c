/**
 * This program decrypts
 * @file decrypt.c
 * @author Ady Francis
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "bits.h"

/** Number of expected arguments */
#define ARG_COUNT 3
/** Index of the input file name within argument array */
#define IN_FILE 1
/** Index of the output file name within argument array */
#define OUT_FILE 2
/** Unsuccessful exit code */
#define EXIT_FAIL 1
/** Code for end of file */
#define FILE_END -1
/** Code for invalid file */
#define INVALID_FILE -2



void readFile(char *inFile, char *outFile)
{
    BitBuffer *buffer = (BitBuffer *)malloc(sizeof(BitBuffer));
    buffer->bcount = 0;
    FILE *fr = fopen(inFile, "rb");
    if (!fr) {
        perror(inFile);
        exit(EXIT_FAIL);
    }
    
     FILE *fw = fopen(outFile, "w");
     if (!fw) {
        perror(outFile);
        exit(EXIT_FAIL);
    }
    int code = 0;
    while (code != FILE_END) {
        code = readBits(buffer, fr);
        if (code == INVALID_FILE) {
            fprintf(stderr, "Invalid file\n");
            exit(EXIT_FAIL);
        }
    }

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
        fprintf(stderr, "usage: decrypt <infile> <outfile>\n");
        exit(EXIT_FAIL);
    }

    readFile(argv[IN_FILE], argv[OUT_FILE]);
}
