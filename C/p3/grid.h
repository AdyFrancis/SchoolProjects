#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>
/** Exit code for invalid exit */
#define INVALID_EXIT 1
/**
 * Adds a word to the grid with horizontal orientation
 * @param rpos the row position of where the word is to be added
 * @param cpos the column position of where the word is to be added
 * @param word the word that is to be added
 * @param rows the number of rows within the grid
 * @param cols the number of columns within the grid
 * @param grid the 2D array to which the word will be added to
 */
void writeHorizontal (int rpos, int cpos, char word[], int rows, int cols, char grid[rows][cols]);

/**
 * Adds a word to the grid with vertical orientation
 * @param rpos the row position of where the word is to be added
 * @param cpos the column position of where the word is to be added
 * @param word the word that is to be added
 * @param rows the number of rows within the grid
 * @param cols the number of columns within the grid
 * @param grid the 2D array to which the word will be added to
 */
void writeVertical(int rpos, int cpos, char word[], int rows, int cols, char grid[rows][cols]);

/**
 * Prints the given grid to standard output.
 * @param rows the number of rows within the grid
 * @param cols the number of columns within the grid
 * @param board the 2D array which will be printed
 */
void printGrid(int rows, int cols, char board[rows][cols]);

