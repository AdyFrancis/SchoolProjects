#ifndef _MODEL_H_
#define _MODEL_H_

#include <stdio.h>

/** Maximum length of name and filename strings. */
#define NAME_LIMIT 20
/** Initial capacity allocation for the list of pointers */
#define INIT_CAP 10
/** Number of columns in pList */
#define COLS 2

/** Representation for a model, a collection of line segments. */
typedef struct {
  /** Name of the model. */
  char name[ NAME_LIMIT + 1 ];
  
  /** File name it was loaded from. */
  char fname[ NAME_LIMIT + 1 ];
  
  /** Number of points in the model.  It has half this many line segments. */
  int pCount;

  /** List of points in the model, twice as long as the number
      of segments, since each segment has two points */
  double (*pList)[ 2 ];
} Model;


/**
 * Reads in the model file and creates an instance of a Model struct
 * @param fname the name of the file to load the model with
 */
Model * loadModel (char const *fname);

/**
 * Frees a specified model
 * @param m the model to free
 */
void freeModel(Model *m);

/**
 * Frees the modelList of a scene
 * @param length the length of the model list
 * @param mList the list of Models to free
 */
void freeModelList(int length, Model ** mList);


/**
 * Applys a geometric transformation to the model
 * @param m the model to apply to the transformation to
 * @param f a pointer to the function that applies the transformation
 * @param a x coodrdinate of transformation
 * @param b y coordinate of transformation
 */
void applyToModel( Model *m, void (*f)( double pt[ 2 ], double a, double b ), double a, double b );


#endif
