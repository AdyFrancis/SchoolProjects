/**
 * This program creates 2D scenes given input files
 * with X and Y coordinates
 * @author Ady Francis
 */
 
#include "scene.h"
#include <math.h>

/** Constant for Pi */
#define M_PI 3.14159265358979323846264338327
/** The maximum number of chars in a command. */
#define MAX_COMMAND_LENGTH 51
/** The maximum length of a sub command */
#define MAX_SUBCOMMAND_LENGTH 10
/** Maximum length for a model name and a file name*/
#define MAX_LENGTH 20
/** The number of commands user can input */
#define COMMAND_NUM 8
/** Code for invalid input */
#define INVALID -1
/** Number to divide to get segments*/
#define SEGMENTS 4
/** X coordinate in pList */
#define X 0
/** Y coordinate in pList */
#define Y 1
/** Value to convert degrees to radians */
#define RAD_DEG 180
/** Index value for load */
#define LOAD_INDEX 0
/** Index value for load */
#define SAVE_INDEX 1
/** Index value for load */
#define DEL_INDEX 2
/** Index value for list */
#define LIST_INDEX 3
/** Index value for translate */
#define TRANS_INDEX 4
/** Index value for scale */
#define SCALE_INDEX 5
/** Index value for rotate */
#define ROT_INDEX 6
/** Index of the quit command in the cmdArr global variable */
#define QUIT_INDEX 7
/** Inputs to be matched */
#define INPUT_MATCH 3

/** Command number*/
 int cmdNum = 0;
/** Pre-defined array of possible commands */
 const char cmdArr[][MAX_COMMAND_LENGTH] = {"load", "save", "delete", "list",
                                            "translate", "scale", "rotate", "quit"};
/** Global scene variable */
Scene *scene;


/**
 * This method checks to see if the command is valdi
 * @param command the command to check
 * @return true if the command is valid, false if it isn't
 */
int checkCommand(char *command)
 {
     for (int i = 0; i < COMMAND_NUM; i++)
         if (strcmp(command, cmdArr[i]) == 0)
             return i;

     return INVALID;
 }
 
 /**
  * This method reads in the command from user input.
  * @param command the character array where the command will be stored
  * @return the index of the sub command within cmdArr. -1 if invalid command
  */
int getCommand(char *command)
{
    int length = 0;
    int c = 0;
    printf("cmd %d> ", ++cmdNum);
    while ((c = getchar()) != '\n' && c != EOF) {
        command[length] = c;
        length++;
        if (length > MAX_COMMAND_LENGTH) {
            while (c != '\n' && c != EOF)
                c = getchar();

            fprintf(stderr, "Command %d invalid\n", cmdNum);
            return INVALID;
        }
    }

    if (c == EOF)
        return QUIT_INDEX;

    char sub[MAX_SUBCOMMAND_LENGTH];
    sscanf(command, "%s", sub);
    int index = checkCommand(sub);
    if (index == INVALID)
        fprintf(stderr, "Command %d invalid\n", cmdNum);
    return index;
    
}

/**
 * Sorts an array in alphabetical order using quick-sort algorithm
 * @param length the length of the list
 * @param list the list to
 */
void sortArray(int length, Model **list)
{
    bool sorted = false;
    Model *temp = malloc(sizeof(Model));

    while (!sorted) {
        sorted = true;
        for (int i = 0; i < length; i++) {
            if ((i + 1 < length) && strcmp(scene->mList[i]->name, scene->mList[i + 1]->name) > 0) {
                sorted = false;
                memcpy(temp, scene->mList[i], sizeof(Model));
                memcpy(scene->mList[i], scene->mList[i + 1], sizeof(Model));
                memcpy(scene->mList[i + 1], temp, sizeof(Model));
            }
        }
    }
    free(temp);
}

/**
 * Adds a model to the scene
 * @param model the model to add to the scene
 */
void addToScene(Model *model)
{
    if (scene->mCount == 0) {
        scene->mCount++;
        scene->mList = (Model **)malloc(scene->mCap * sizeof(Model));
        scene->mList[0] = model;
        return;
    }
    else if (scene->mCount >= scene->mCap) {
        scene->mCap = scene->mCap * 2;
        scene->mList = (Model **)realloc(scene->mList,  scene->mCap * sizeof(Model));
    }
    scene->mList[scene->mCount++] = model;
}

/**
 * Parses the load command
 * @param command the load command to parse
 */
void parseLoadCmd (char *command)
{
    char modelName[MAX_COMMAND_LENGTH];
    char fileName[MAX_COMMAND_LENGTH];

    if (sscanf(command, "%*s%s%s", modelName, fileName) != 2) {
        fprintf(stderr, "Command %d invalid\n", cmdNum);
    }
    else if (strlen(modelName) > MAX_LENGTH || strlen(fileName)  > MAX_LENGTH
                                            || checkScene(scene, modelName)) {
        fprintf(stderr, "Command %d invalid\n", cmdNum);
    }
    else {
        Model *model = loadModel(fileName);
        if (model == NULL)
            fprintf(stderr, "Can't open file: %s\n", fileName);
        else {
            strcpy(model->name, modelName);
            addToScene(model);
        }
    }

}


/**
 * Parses the save command
 * @param command the save command to parse
 */
void parseSaveCmd (char *command)
{
    char fileName[MAX_COMMAND_LENGTH];
    int modelsInScene = scene->mCount;
    sortArray(modelsInScene, scene->mList);
    
    if (sscanf(command, "%*s%s", fileName) != 1 || strlen(fileName) > MAX_LENGTH) {
        fprintf(stderr, "Command %d invalid\n", cmdNum);
    }
    else {
        FILE *f = fopen(fileName, "w");
        if (!f)
            fprintf(stderr, "Can't open file: %s\n", fileName);

        int modelsInScene = scene->mCount;

        for (int i = 0; i < modelsInScene; i++) {
            int segmentsInModel = (scene->mList[i]->pCount) / 2;
            for (int j = 0; j < segmentsInModel; j++) {
                fprintf(f, "%.3lf %.3lf\n", scene->mList[i]->pList[j][0],
                                            scene->mList[i]->pList[j][1]);
                if (j > 0 && j % 2 == 1)
                    fprintf(f, "\n");
            }
        }
        fclose(f);
    }
}

/**
 * Parses the list command.
 */
void parseListCmd()
{
    int modelsInScene = scene->mCount;
    sortArray(modelsInScene, scene->mList);
    if (scene->mCount == 0)
        return;
    else if (scene->mCount == 1)
        printf("%s %s (%d)\n", scene->mList[0]->name, scene->mList[0]->fname,
                scene->mList[0]->pCount / SEGMENTS);
    else {
        sortArray(modelsInScene, scene->mList);
        for (int i = 0; i < modelsInScene; i++) {
            printf("%s %s (%d)\n", scene->mList[i]->name, scene->mList[i]->fname,
            scene->mList[i]->pCount / SEGMENTS);
        }
    }
}

/**
 * Parses the delete command
 * @param the command to parse
 */
void parseDeleteCmd(char *command)
{
    char name[MAX_COMMAND_LENGTH];
    bool found = false;
    if (sscanf(command, "%*s%s", name) != 1 || strlen(name) > MAX_LENGTH)
         fprintf(stderr, "Command %d invalid\n", cmdNum);
    else {

        int modelsInScene = scene->mCount;
        
        Model **tempList =(Model **)malloc(scene->mCap * sizeof(Model));
        for (int i = 0; i < modelsInScene; i++) {
            if (strcmp(scene->mList[i]->name, name) == 0) {
                freeModel(scene->mList[i]);
                found = true;

                if (i == 0) {
                    for (int q = 0; q < modelsInScene; q++)
                        if (q + 1 < modelsInScene)
                            tempList[q] = scene->mList[q + 1];
                }
                else if (i == modelsInScene - 1) {
                    for (int q = 0; q < modelsInScene - 1; q++)
                        if (q + 1 < modelsInScene - 1)
                            tempList[q] = scene->mList[q + 1];
                }
                else {
                    for (int j = 0; j < i; j++)
                        tempList[j] = scene->mList[j];
                    if (i + 1 < modelsInScene)
                        tempList[i] = scene->mList[i + 1];
                    if (i + 2 < modelsInScene)
                        for (int k = i + 2; k < modelsInScene; k++)
                            tempList[k] = scene->mList[k];
                }
                break;
            }
        }
        if (!found)
            fprintf(stderr, "Command %d invalid\n", cmdNum);
        else {
            scene->mCount--;
            memcpy(scene->mList, tempList, scene->mCap * sizeof(Model));
        }
        free(tempList);
    }

}

/**
 * Translates a set of coordinates
 * @param pt an array of coordinates to translate
 * @param a the x value of translation
 * @param b the y value of translation
 */
void translate(double pt[COLS], double a, double b)
{
    pt[X] += a;
    pt[Y] += b;
}


/**
 * Scales a set of coordinates
 * @param pt an array of coordinates to scale
 * @param a the x value of scaling
 * @param b the y value of scaling
 */
void scale(double pt[COLS], double a, double b)
{
    pt[X] = pt[X] * a;
    pt[Y] = pt[Y] * b;
}


/**
 * Rotates a set of coordinates
 * @param pt an array of coordinates to rotate
 * @param a the x value of rotation
 * @param b the y value of rotation
 */
void rotate(double pt[COLS], double a, double b)
{
    double x = pt[X];
    double y = pt[Y];
    double rad = a * (M_PI / RAD_DEG);
    pt[X] = (x * cos(rad)) - (y * sin(rad));
    pt[Y] = (x * sin(rad)) + (y * cos(rad));
}

/**
 * Parses the translate command
 * @param command the command to parse
 */
void parseTransCmd(char *command)
{
    char name[MAX_COMMAND_LENGTH];
    double x;
    double y;

    if (sscanf(command, "%*s%s%lf%lf", name, &x, &y) != INPUT_MATCH || strlen(name) > MAX_LENGTH
        || !applyToScene(scene, name, translate, x, y)) {
        
        fprintf(stderr, "Command %d invalid\n", cmdNum);
    }
}

/**
 * Parses the scale command
 * @param command the command to parse
 */
void parseScaleCmd(char *command)
{
    char name[MAX_COMMAND_LENGTH];
    double x;

    if (sscanf(command, "%*s%s%lf", name, &x) != 2 || strlen(name) > MAX_LENGTH
        || !applyToScene(scene, name, scale, x, x)) {
        
        fprintf(stderr, "Command %d invalid\n", cmdNum);
    }
}

/**
 * Parses the rotate command
 * @param command the command to parse
 */
void parseRotateCmd(char *command)
{
    char name[MAX_COMMAND_LENGTH];
    double x;

     if (sscanf(command, "%*s%s%lf", name, &x) != 2 || strlen(name) > MAX_LENGTH
        || !applyToScene(scene, name, rotate, x, x)) {

        fprintf(stderr, "Command %d invalid\n", cmdNum);
    }
}

/**
 * Initializes a scene struct and all its fields
 */
Scene *makeScene()
{
    scene = (Scene *)malloc(sizeof(Scene));
    scene->mCap = INIT_CAP;
    scene->mCount = 0;
    return scene;
}

 /**
 * Starting point of the program
 * @return exit status of the program
 */
int main()
{
    char * command = (char *)malloc(MAX_COMMAND_LENGTH);
    int index = 0;
    scene = makeScene();

    while ((index = getCommand(command)) != QUIT_INDEX){
        if (index == LOAD_INDEX)
            parseLoadCmd(command);
        else if (index == SAVE_INDEX)
            parseSaveCmd(command);
        else if (index == DEL_INDEX)
            parseDeleteCmd(command);
        else if (index == LIST_INDEX)
            parseListCmd();
        else if (index == TRANS_INDEX)
            parseTransCmd(command);
        else if (index == SCALE_INDEX)
            parseScaleCmd(command);
        else if (index == ROT_INDEX)
            parseRotateCmd(command);

        command = (char *)calloc(MAX_COMMAND_LENGTH, sizeof(char));
    }
    free(command);
    freeScene(scene);
}
