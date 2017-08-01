/**
 * Header file for the codes.c component.
 * @author Ady Francis
 */


#ifndef _CODES_H_
#define _CODES_H_

#include <stdio.h>
#include <ctype.h>
/** Index for the space character */
#define SPACE_INDEX 26

/** Index for the new line character */
#define NL_INDEX 27

/** decimal value of a on ASCII table */
#define A_VALUE 97

/** Invalid search value */
#define INVALID -1

/** Length of the code array */
#define CODE_ARR_LENGTH 28

typedef struct {

    char c;
    int code, bits;

} Code;

/**
 * Given the ASCII code for a character, this method returns the code used to represent it.
 * @param ch the character to convert
 * @return the hexadecimal code for the character. -1 if there is no code for the given character.
 */
int symToCode(unsigned char ch);

/**
 * Given the ASCII code for a character, this function returns the # of bits used to represent it.
 * @param ch the ASCII character to find
 * @return the number of bits used to represent the hexadecimal code for the character
 */
int bitsInCode(unsigned char ch);

/**
 * Given a hexadecimal code, this function returns the ASCII character it represents.
 * @param code the hexadecimal code used to represent a character
 * @return the ASCII character the code represents
 */
int codeToSym(int code);



#endif
