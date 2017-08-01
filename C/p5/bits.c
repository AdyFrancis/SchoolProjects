/**
 * This component is responsible for writing out bits
 * @author Ady Francis
 * @file bits.c
 *
 *
 */

#include "bits.h"
/** Unsuccessful exit code */
#define EXIT_FAIL 1
/** Code for end of file */
#define FILE_END -1
/** Code for invalid file */
#define INVALID_FILE -2



void writeBits( int code, int nbits, BitBuffer *buffer, FILE *fp )
{
    if (buffer->bcount == 0 && nbits <= BITS_PER_BYTE) {

        int bitsToWrite = nbits;
        int mask = 0x1 << (bitsToWrite - 1);
        int bit = code & mask;

         while (bitsToWrite > 0) {

            buffer->bits |= bit;
            mask = mask >> 1;
            bit = code & mask;
            buffer->bcount++;
            bitsToWrite--;
            if (buffer->bcount == BITS_PER_BYTE)
                flushBits(buffer, fp);
        }

        int emptyBits = BITS_PER_BYTE - buffer->bcount;
        buffer->bits = buffer->bits << emptyBits;
    }
    else {
        int emptyBits = BITS_PER_BYTE - buffer->bcount;
        int bitsToWrite = nbits;

        if (bitsToWrite == emptyBits) {
            buffer->bits |= code;
            buffer->bcount += emptyBits;
            flushBits(buffer, fp);
        }
        else if (bitsToWrite < emptyBits) {
            buffer->bits |= (code << (emptyBits - bitsToWrite));
            buffer->bcount += bitsToWrite;
        }
        else if (bitsToWrite > emptyBits) {

            int part =  bitsToWrite - emptyBits;

            buffer->bits |= (code >> part);


            flushBits(buffer, fp);
            bitsToWrite = part;
            int newCode = code;

            if (part > BITS_PER_BYTE){
                int mask = 0x7FF;
                for (int i = 0; i < nbits - bitsToWrite; i++) {
                    newCode = newCode & (mask >> i);
                }
                mask = 0x1;
                int partCode = newCode >> (nbits - emptyBits - BITS_PER_BYTE);

                for (int j = 0; j < BITS_PER_BYTE; j++) {
                    buffer->bits |= (partCode & mask);
                    mask = mask << 1;
                    bitsToWrite--;
                }
                flushBits(buffer, fp);

                mask = 0x1;
                for (int k = 0; k < bitsToWrite; k++) {
                    buffer->bits |= (code & mask);
                    mask = mask << 1;
                    buffer->bcount++;
                }
                if (buffer->bcount == BITS_PER_BYTE)
                    flushBits(buffer, fp);
                else 
                    buffer->bits = buffer->bits << (BITS_PER_BYTE - buffer->bcount);

            
            }
            else {
                int mask = 0x1;

                for (int i = 0; i < bitsToWrite; i++) {
                    buffer->bits |= (code & mask);
                    mask = mask << 1;
                    buffer->bcount++;
                    if (buffer->bcount == BITS_PER_BYTE && code == 0xb6c) {
                        flushBits(buffer, fp);
                    }
                }                
                buffer->bits = buffer->bits << (BITS_PER_BYTE - buffer->bcount);
            }
        }
    }
}

/**
 * Helper method for readBits. 
 * @param byte to find the shift for
 * @return the shift amount
 */   
int findShift (int byte)
{
    int maskOne = 0x1;
    int maskTwo = 0x2;
    int shift = 0;

     for (int i = 0; i < BITS_PER_BYTE; i++) {
        int one = byte & maskOne;
        int two = byte & maskTwo;
        if (one == 0 && two == 0)
            return shift;
        maskOne = maskOne << 1;
        maskTwo = maskTwo << 1;
        shift++;
    }
    return 0;
}
/** Reads and returns the next valid code from the given file. Each valid code 
    starts with a 1 and ends with two consecutive 0s (00).
    if no bits or only 0s have been read when the end of file is reached,
    -1 is returned. 
    If the first bit read is a 1 and the end of file is reached before 
    two consecutive 0s (00) are read, -2 is returned.
    If the first bit read is a 0 and a 1 is read before the end of the file
    is reached, -2 is returned.
    The given buffer may contain some bits left over from the last read, and if this
    the read has any left-over bits, it should leave them in that buffer.
    @param buffer pointer to storage for left-over from one read call to the
    next.
    @param fp file bits are being read from, opened for reading in binary.
    @return value of the valid code read in, -1 if we reach the
    end-of-file under valid conditions, and -2 if the file is invalid.
*/
int readBits (BitBuffer *buffer, FILE *fp )
{
    if (feof(fp))
        return FILE_END;

    int byte = 0;
    fread(&byte, 1, 1, fp);
    buffer->bits = byte;
    int shift = 0;
    //there are unwritten low order bits
    if (buffer->bcount > 0){
        shift = findShift(byte);
        int newCode = 0;
        int mask = 0x1;
        for (int i = 0; i < buffer->bcount; i++) {
            newCode |= (buffer->bits & mask);
            mask = mask << 1;
        }
        buffer->bits = newCode;

        if (shift == 0){
            //todo
        }
        else {
            buffer->bits = buffer->bits << (BITS_PER_BYTE - shift);
            mask = 0x80;
            int highOrder = 0;
            for (int i = 0; i < BITS_PER_BYTE - shift; i++) {
                highOrder |= byte & mask;
                mask = mask >> 1;
            }
            highOrder = highOrder >> (BITS_PER_BYTE - shift);
        }
    }
    else {
        shift = findShift(byte);
        if (shift >= BITS_PER_BYTE / 2)
            return INVALID_FILE;
        
        if (shift == 0) {
            fread(&byte, 1, 1, fp);
        }
        else if (shift != 0 && buffer->bcount == 0) {
            int x = buffer->bits >> shift;
            buffer->bcount = shift;
            return x;
        }
    }
}

void fillBuffer(BitBuffer *buffer, FILE *fp)
{
    flushBits(buffer, fp);
}

void flushBits(BitBuffer *buffer, FILE *fp)
{
    fwrite(&(buffer->bits), 1, 1, fp);
    buffer->bcount = 0;
    buffer->bits = 0;
}
