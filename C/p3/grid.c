/**
 * This program handles writing to the grid
 * and printing the grid for puzzle.c
 * @Author Ady Francis
 */

#include "grid.h"

/**
 * Adds a word to the grid with horizontal orientation
 * @param rpos the row position of where the word is to be added
 * @param cpos the column position of where the word is to be added
 * @param word the word that is to be added
 * @param rows the number of rows within the grid
 * @param cols the number of columns within the grid
 * @param grid the 2D array to which the word will be added to
 */
void writeHorizontal (int rpos, int cpos, char word[], int rows, int cols, char grid[rows][cols]) {

    int length = (int)strlen(word);

    for (int i = 0; i < length; i++) {
        if (isspace(grid[rpos][i+cpos])) {
            grid[rpos][i + cpos] = word[i];
        }
        else if (isalpha(grid[rpos][i + cpos])) {
            if (word[i] != grid[rpos][i + cpos]) {
                fprintf(stderr, "Invalid input file\n");
                exit(INVALID_EXIT);
            }
            else {
                grid[rpos][i + cpos] = word[i];
            }
        }
    }

}

/**
 * Adds a word to the grid with vertical orientation
 * @param rpos the row position of where the word is to be added
 * @param cpos the column position of where the word is to be added
 * @param word the word that is to be added
 * @param rows the number of rows within the grid
 * @param cols the number of columns within the grid
 * @param grid the 2D array to which the word will be added to
 */
void writeVertical(int rpos, int cpos, char word[], int rows, int cols, char grid[rows][cols]){

    int length = (int)strlen(word);

    for (int i = 0; i < length; i++) {
        if (isspace(grid[i + rpos][cpos])) {
            grid[i + rpos][cpos] = word[i];
        }
        else if (isalpha(grid[i + rpos][cpos])) {
            if (word[i] != grid[i + rpos][cpos]) {
                fprintf(stderr, "Invalid input file\n");
                exit(INVALID_EXIT);
            }
            else {
                grid[i + rpos][cpos] = word[i];
            }
        }
    }
}

/**
 * Prints the given grid to standard output.
 * @param rows the number of rows within the grid
 * @param cols the number of columns within the grid
 * @param board the 2D array which will be printed
 */
void printGrid(int rows, int cols, char board[rows][cols])
{
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            printf("%c", board[i][j]);
            if (j != cols - 1) {
                printf(" ");
            }
        }
        printf("\n");
    }
}
