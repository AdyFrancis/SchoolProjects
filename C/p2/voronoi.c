/**
    @file voronoi.c
    @author Ady Francis (afranci)
    Program that draws a voronoi diagram
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>

/** Height of the ppm image*/
#define IMG_HEIGHT 100

/** Width of the ppm image */
#define IMG_WIDTH 100

/** Max RGB intensity value */
#define RGB_MAX 255

/** Exit code for invalid input */
#define INVALID_INPUT_EXIT 100

/** Number of inputs to match for each scanf */
#define INPUT_MATCH 2

/** X and Y coordinate for the first input point */
double redX, redY;

/** X and Y coordinate for the second input point */
double greenX, greenY;

/** X and Y coordinate for the third input point */
double blueX, blueY;

/**
    Prints the default header for the PPM output file
*/
void printHeader() {
    printf("P3\n%d %d\n%d\n", IMG_HEIGHT, IMG_WIDTH, RGB_MAX);
}


/**
 * Given a double, returns the square of that double.
 * @param x the double that is to be squared;
 * @return the square of the parameter.
 */
double square(double x) {

    return x * x;
}


/**
    Given the location of a pixel and one of the input points
    this function returns true if the given pixel is the cloest to the
    given point.
    @param col column location of the pixel
    @param row row location of the pixel
    @param x x-coordinate of the input point
    @param y y-coordinate of the input point
*/
bool nearestPoint (int col, int row, double x, double y) {
    if (round(x) == row && round(y) == col)
        return true;

    return false;
}


/**
    Given the location of a pixel, this function
    will print out the right color for the pixel.
    @param col column location of the pixel
    @param row row location of the pixel
*/
void chooseColor(int col, int row) {
    double redDistance = sqrt(square(row - redX) + square(col - redY));
    double greenDistance = sqrt(square(row - greenX) + square(col - greenY));
    double blueDistance = sqrt(square(row - blueX) + square(col - blueY));
    
    if (redDistance < greenDistance && redDistance < blueDistance)
        printf("%d   0   0 ", RGB_MAX);
    else if (greenDistance < redDistance && greenDistance < blueDistance)
        printf("  0 %d   0 ", RGB_MAX);
    else
        printf("  0   0 %d ", RGB_MAX);
}

/**
    Starting point for the program
    @return Exit status for the program
*/
int main () {

    if (scanf("%lf%lf", &redX, &redY) != INPUT_MATCH) {
        printf("Invalid input\n");
        exit(INVALID_INPUT_EXIT);
    }
    if (scanf("%lf%lf", &greenX, &greenY) != INPUT_MATCH) {
        printf("Invalid input\n");
        exit(INVALID_INPUT_EXIT);
    }
    if (scanf("%lf%lf", &blueX, &blueY) != INPUT_MATCH) {
        printf("Invalid input\n");
        exit(INVALID_INPUT_EXIT);
    }

    printHeader();
    
    for (int i = 0; i < IMG_HEIGHT; i++) {
        for (int j = 0; j < IMG_WIDTH; j++) {
            if (nearestPoint(i, j, redX, redY) || nearestPoint(i, j, greenX, greenY)
            || nearestPoint(i, j, blueX, blueY)) {
                printf("%d %d %d ", RGB_MAX, RGB_MAX, RGB_MAX);
            }
            else {
                chooseColor(i, j);
            }
        }
        printf("\n");
    }

    return EXIT_SUCCESS;
}
