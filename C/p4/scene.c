/**
 * This component handles Scene manipulation
 */

#include "scene.h"

/**
 * Frees a scene and the models in it
 * @param s the scene to free
 */
void freeScene (Scene *s)
{
   freeModelList(s->mCount, s->mList);
}

/**
 * Checks the scene to see if it has a model with the specified name
 * @param s the scene to check
 * @param name the name of the model to check for
 * @return true if the model is found
 */
bool checkScene(Scene *s, const char * name)
{
    for (int i = 0; i < s->mCount; i++)
        if (strcmp(name, s->mList[i]->name) == 0)
            return true;

    return false;
}

/**
 * Applies to a transformation to a model in the scene given the name of the model
 * @param s the scene to apply the transformation to
 * @param name the name of the model to apply the transformation to
 * @param f the transformation function to apply
 * @param a the x value of transformation
 * @param b the y value of transformation
 * @returns true if the model was found
 */
bool applyToScene(Scene *s, char const *name, void (*f)(double pt[2], double a, double b),
                                                                      double a, double b)
{
    for (int i = 0; i < s->mCount; i++)
        if (strcmp(s->mList[i]->name, name) == 0) {
            applyToModel(s->mList[i], f, a, b);
            return true;
        }

    return false;
}
