/**
 * This program takes in an input file from
 * which it reads jumbled words and creates a grid
 * based on the specifications of the input file
 * to which it adds those words resembling a crossword puzzle.
 * @Author Ady Francis
 */

#include "grid.h"

/** Number of expected arguments */
#define ARG_NUM 2
/** Command line argument array index for file name */
#define ARG_FILE 1
/** Maximum number of lines in a file */
#define FILE_LINES 10
/** Number of lines that contain the variables for the program */
#define FILE_VARS 2
/** Max length for a word */
#define MAX_LENGTH 10
/** Max amount of rows*/
#define MAX_ROWS 40
/** Max amount of columns */
#define MAX_COLS 40
/** Maximum characters in a line */
#define MAX_CHAR 20


/**
 * Checks to see if the word is within the length requirement,
 * as well as if it contains any invalid characters
 * @param word the word string to be checked
 * @param length the maximum length of words to the grid
 * @param row the row the word will be added to
 * @param col the column the word will be added to
 * @param rows the amount of rows in the grid
 * @param cols the amount of columns in the grid
 * @param orientation the orientation of the word
 * @return returns true if the word is valid
 */
bool checkWord(char word[], int row, int col, int rows, int cols, char orientation)
{
    int wordLength = (int)strlen(word);

    if (wordLength > MAX_LENGTH) {
        printf("length 2 long\n");
        return false;
    }

    if (orientation == 'V') {
        if (row + wordLength > rows) {
            return false;

        }
    }
    else if (orientation == 'H') {
        if (col + wordLength > cols) {
            return false;
        }
    }

    return true;
}

/**
 * Clears the grid, filling every entry with an empty space.
 * @param rows the amount of rows in the grid
 * @param cols the amount of columns in the grid
 * @param grid the grid that is to be clearedg
 */
void clearGrid(int rows, int cols, char grid[rows][cols])
{
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            grid[i][j] = ' ';
        }
    }

}

/**
 * Starting point of the program
 * @param argc the number of arguments provided in command line
 * @param argv array of command line arguments
 * @return exit status of the program
 */
int main(int argc, char *argv[])
{
    if (argc != ARG_NUM) {
        printf("Usage: puzzle <input-file>\n");
        exit(INVALID_EXIT);
    }

    FILE *f = fopen(argv[ARG_FILE], "r");

    if (!f) {
        fprintf(stderr, "Usage: puzzle <input-file>\n");
        exit(INVALID_EXIT);
    }

    char line[MAX_CHAR];
    int rows, cols, numWords;
    for (int i = 0; i < FILE_VARS; i++) {
        fgets(line, sizeof(line), f);
        if (i == 0)
            sscanf(line, "%d%d", &rows, &cols);
        if (i == 1)
            sscanf(line, "%d", &numWords);
    }

    if (rows > MAX_ROWS || cols > MAX_COLS) {
        fprintf(stderr, "Invalid input file\n");
        exit(INVALID_EXIT);
    }
    char grid[rows][cols];
    char orientation;
    char word[MAX_LENGTH];
    int row, col;
    int count = 0;

    clearGrid(rows, cols, grid);
    for (int i = 0; i < numWords; i++) {
        fgets(line, sizeof(line), f);
        sscanf(line, "%c%d%d%s", &orientation, &row, &col, word);

        if (feof(f) && count != numWords) {
            fprintf(stderr, "Invalid input file\n");
            exit(INVALID_EXIT);
        }

        if (!checkWord(word, row, col, rows, cols, orientation)) {
            fprintf(stderr, "Invalid input file\n");
            exit(INVALID_EXIT);
        }

        if (orientation == 'H') {
            writeHorizontal(row, col, word, rows, cols, grid);
            count++;
        }
        else if (orientation == 'V') {
            writeVertical(row, col, word, rows, cols, grid);
            count++;
        }
    }
    printGrid(rows, cols, grid);
    return EXIT_SUCCESS;
}
