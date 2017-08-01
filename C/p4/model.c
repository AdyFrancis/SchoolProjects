/**
 * This component of the program
 * loads and manipulates the models.
 */

#include "model.h"
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

/**
 * Frees the dynamically allocated model
 * @param the model to free.
 */
void freeModel(Model *m)
{
    free(m->pList);
    free(m);
}

/**
 * Frees the modelList of a scene
 * @param length the length of the model list
 * @param mList the list of Models to free
 */
void freeModelList(int length, Model ** mList)
{
    for (int i = 0; i < length; i++) {
        freeModel(mList[i]);
    }
}

void applyToModel(Model *m, void (*f)(double pt[COLS], double a, double b), double a, double b)
{
    for (int i = 0; i < m->pCount / COLS; i++)
        f(m->pList[i], a, b);
}

/**
 * Loads a model with a list of points given a file name
 * @param the name of the file
 * @return instance of model with the points loaded in
 */
Model * loadModel (char const *fname)
{
    FILE *f = fopen(fname, "r");
    if (!f)
        return NULL;

    Model *model = (Model *)malloc(sizeof(Model));
    strcpy(model->fname, fname);

    int pCap = INIT_CAP;
    model->pCount = 0;
    model->pList = (double (*)[COLS]) malloc(pCap * sizeof(*model->pList));
    double p = 0;
    int row = 0;
    int col = 0;

    while (fscanf(f, "%lf", &p) == 1) {
        model->pList[row][col] = p;
        model->pCount++;

        if (col == 1) {
            col = 0;
            row++;
        }
        else col++;
        
        if (model->pCount >= pCap) {
            pCap = model->pCount * COLS;
            model->pList = (double (*)[COLS])realloc(model->pList, pCap * sizeof(*model->pList));
        }
    }

    fclose(f);
    return model;
}
