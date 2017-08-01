/**
    @file codes.c
    @author Ady Francis

    Component helps map between symbols and codes used to represent them
*/
#include "codes.h"

Code codeArr[] = {
{ 'a', .code = 0x2c, .bits = 6 }, 
{ 'b', .code = 0x354, .bits = 10 }, 
{ 'c', .code = 0x6b4, .bits = 11 },
{ 'd', .code = 0xd4, .bits = 8 },
{ 'e', .code = 0x4, .bits = 3 },
{ 'f', .code = 0x2b4, .bits = 10 },
{ 'g', .code = 0x1b4, .bits = 9 }, 
{ 'h', .code = 0x154, .bits = 9 },
{ 'i', .code = 0x14, .bits = 5 },
{ 'j', .code = 0xb6c, .bits = 12 },
{ 'k', .code = 0x1ac, .bits = 9 },
{ 'l', .code = 0x2d4, .bits = 10 },
{ 'm', .code = 0x6c, .bits = 7 },
{ 'n', .code = 0x34, .bits = 6 },
{ 'o', .code = 0x36c, .bits = 10 },
{ 'p', .code = 0x5B4, .bits = 11 },
{ 'q', .code = 0xdac, .bits = 12 },
{ 'r', .code = 0xb4, .bits = 8 },
{ 's', .code = 0x54, .bits = 7 },
{ 't', .code = 0xc, .bits = 4 },
{ 'u', .code = 0xac, .bits = 8 },
{ 'v', .code = 0x2ac, .bits = 10 },
{ 'w', .code = 0x16c, .bits = 9 },
{ 'x', .code = 0x6ac, .bits = 11 },
{ 'y', .code = 0xd6c, .bits = 12 },
{ 'z', .code = 0x6d4, .bits = 11 },
{ ' ', .code = 0x5ac, .bits = 11 },
{ '\n', .code = 0x56c, .bits = 11 },
};

//97 - 122 -> 0 - 25
int symToCode(unsigned char ch)
{
    if (ch == ' ')
        return codeArr[SPACE_INDEX].code;
    else if (ch == '\n')
        return codeArr[NL_INDEX].code;
    
    int val = ch - A_VALUE;

    if (val < 0 || val > 25)
        return INVALID;

    return codeArr[val].code;
}

int codeToSym(int code)
{
    for (int i = 0; i < CODE_ARR_LENGTH; i++)
        if (codeArr[i].code == code)
            return codeArr[i].c;
    
    return INVALID;
}

int bitsInCode(unsigned char ch)
{
    if (ch == ' ')
        return codeArr[SPACE_INDEX].bits;
    else if (ch == '\n')
        return codeArr[NL_INDEX].bits;

    int val = ch - A_VALUE;

    if (val < 0 || val > 25)
        return INVALID;

    return codeArr[val].bits;
}
