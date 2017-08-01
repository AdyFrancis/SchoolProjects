#include "expr.h"
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <stdbool.h>

//////////////////////////////////////////////////////////////////////
// Context

/** Representation for a variable anme and its value. */
typedef struct {
  char name[ MAX_IDENT_LEN + 1 ];
  char *val;
} VarRec;

/** Hidden implementation of the context.  Really just a wrapper
    around a resizable array of VarRec structs. */
struct ContextTag {
  // List of all varname/value pairs.
  VarRec *vList;

  // Number of name/value pairs.
  int vCount;

  // Capacity of the name/value list.
  int vCap;
};

Context *makeContext()
{
  Context *ctxt = (Context *)malloc(sizeof(Context));
  ctxt->vCount = 0;
  ctxt->vCap = INIT_CAP;
  ctxt->vList = (VarRec *)malloc(sizeof(VarRec) * ctxt->vCap);
  return ctxt;
}

char const *getVariable( Context *ctxt, char const *name )
{
    for (int i = 0; i < ctxt->vCount; i++) {
        if (strcmp(ctxt->vList[i].name, name) == 0)
            return ctxt->vList[i].val;
    }

    return "";
}

void setVariable( Context *ctxt, char const *name, char *value )
{

    bool set = false;
    for (int i = 0; i < ctxt->vCount; i++) {
        if (strcmp(ctxt->vList[i].name, name) == 0) {
            free(ctxt->vList[i].val);
            ctxt->vList[i].val = (char *) malloc(strlen(value) + 1);
            strcpy(ctxt->vList[i].val, value);
            set = true;
            break;
        }
    }

    if (!set) {
        if (ctxt->vCount + 1 == ctxt->vCap) {
            ctxt->vCap *= 2;
            ctxt->vList = (VarRec *)realloc(ctxt->vList, sizeof(VarRec) * ctxt->vCap);
        }

        strcpy(ctxt->vList[ctxt->vCount].name, name);
        ctxt->vList[ctxt->vCount].val = (char *) malloc(strlen(value) + 1);
        strcpy(ctxt->vList[ctxt->vCount].val, value);
        
        ctxt->vCount++;
    }
}

void freeContext( Context *ctxt )
{
    for (int i = 0; i < ctxt->vCount; i++) {
        free(ctxt->vList[i].val);
    }
    free(ctxt->vList);
    free(ctxt);

}

//////////////////////////////////////////////////////////////////////
// Literal

// Representation for a Literal expression, derived from Expr.
typedef struct {
  char *(*eval)( Expr *oper, Context *ctxt );
  void (*destroy)( Expr *oper );

  /** Literal value of this expression. */
  char *val;
} LiteralExpr;


typedef struct {

    char *(*eval)(Expr *assign, Context *ctxt);
    void (*destroy)(Expr *assign);

    char *name;
} AssignExpr;

// Function to evaluate a literal expression.
static char *evalLiteral( Expr *expr, Context *ctxt )
{
  // Cast the this pointer to a more specific type.
  LiteralExpr *this = (LiteralExpr *)expr;

  // Make and return a copy of the value we contain.
  char *result = (char *) malloc( strlen( this->val ) + 1 );
  strcpy( result, this->val );
  return result;
}

static char *evalVarAssign(Expr* expr, Context *ctxt)
{
    AssignExpr *this = (AssignExpr *)expr;

    char *result = (char *)malloc(strlen(getVariable(ctxt, this->name)) + 1);
    strcpy (result, getVariable(ctxt, this->name));
    return result;
}

// Function to free a literal expression.
static void destroyLiteral( Expr *expr )
{
  // Cast the this pointer to a more specific type.
  LiteralExpr *this = (LiteralExpr *)expr;

  // Free the value we contain and the literal object itself.
  free( this->val );
  free( this );
}


//Function to destroy a assignment expression
static void destroyAssign(Expr *expr)
{
    AssignExpr *this = (AssignExpr *) expr;

    free(this->name);
    free(this);

}

Expr *makeLiteral( char *val )
{
  // Allocate space for the LiteralExpr object
  LiteralExpr *this = (LiteralExpr *) malloc( sizeof( LiteralExpr ) );

  // Remember our virutal functions.
  this->eval = evalLiteral;
  this->destroy = destroyLiteral;

  // Remember the literal string we contain.
  this->val = val;

  // Return the result, as an instance of the base.
  return (Expr *) this;
}


Expr *makeAssignment (char *name)
{
    AssignExpr *this = (AssignExpr *) malloc (sizeof(AssignExpr));

    this->eval = evalVarAssign;
    this->destroy = destroyAssign;

    this->name = name;

    return (Expr *) this;
}

// For double values, this should be the longest representation that could
// get printed with %f, a large positive exponent and some fractional digits.
#define MAX_NUMBER 400

//////////////////////////////////////////////////////////////////////
// Sum expressions

/** Representation for a sum expression.  This struct could probably
    be used to represent lots of different binary expressions. */
typedef struct {
  char *(*eval)( Expr *oper, Context *ctxt );
  void (*destroy)( Expr *oper );

  // Two sub-expressions.
  Expr *leftExpr, *rightExpr;
} SumExpr;

typedef struct {
  void (*eval)(Expr *oper, Context *ctxt);
  void (*destroy)(Expr *oper);
  
  Expr *leftExpr, *rightExpr;
} VarExpr;

/**
 * Destroy function for binary expressions
 * @param expr the expression to destroy
 */
static void destroySum( Expr *expr )
{
  SumExpr *this = (SumExpr *)expr;

  // Free our operand subexpressions.
  this->leftExpr->destroy( this->leftExpr );
  this->rightExpr->destroy( this->rightExpr );

  // Then the struct itself.
  free( this );
}


/**
 * Eval function for a sum expression
 * @param expr the expression to evaluate
 * @param ctxt the list of all variables and their values
 * @return the result of the sum expression
 */
static char *evalSum( Expr *expr, Context *ctxt )
{
  // Get a pointer to the more specific type this function works with.
  SumExpr *this = (SumExpr *)expr;

  // Evaluate our two operands
  char *left = this->leftExpr->eval( this->leftExpr, ctxt );
  char *right = this->rightExpr->eval( this->rightExpr, ctxt );

  // Parse the left and right operands as doubles.  Set them
  // to zero if they don't parse correctly.
  double a, b;
  if ( sscanf( left, "%lf", &a ) != 1 )
    a = 0.0;

  if ( sscanf( right, "%lf", &b ) != 1 )
    b = 0.0;

  // We're done with the values returned by our two subexpressions,
  // We just needed to get them as doubles
  free( left );
  free( right );

  // Compute the result, store it in a dynamically allocated string
  // and return it to the caller.
  char *result = (char *)malloc( MAX_NUMBER + 1 );
  sprintf( result, "%f", a + b );
  return result;
}

/**
 * Eval function for a difference  expression
 * @param expr the expression to evaluate
 * @param ctxt the list of all variables and their values
 * @return the result of the difference expression
 */
static char *evalDiff(Expr *expr, Context *ctxt)
{
    SumExpr *this = (SumExpr *)expr;

    char *left = this->leftExpr->eval(this->leftExpr, ctxt);
    char *right = this->rightExpr->eval(this->rightExpr, ctxt);

    double a, b;

    if (sscanf(left, "%lf", &a) != 1)
        a = 0.0;

    if (sscanf(right, "%lf", &b) != 1)
        b = 0.0;

    free(left);
    free(right);

    char *result = (char *)malloc(MAX_NUMBER + 1);
    sprintf(result, "%f", a - b);
    return result;

}

/**
 * Eval function for a product expression
 * @param expr the expression to evaluate
 * @param ctxt the list of all variables and their values
 * @return the result of the product expression
 */
static char *evalProd(Expr *expr, Context *ctxt)
{
    SumExpr *this = (SumExpr *)expr;

    char *left = this->leftExpr->eval(this->leftExpr, ctxt);
    char *right = this->rightExpr->eval(this->rightExpr, ctxt);

    double a, b;

    if (sscanf(left, "%lf", &a) != 1)
        a = 0.0;
    if (sscanf(right, "%lf", &b) != 1)
        b = 0.0;

    free(left);
    free(right);

    char *result = (char *)malloc(MAX_NUMBER + 1);
    sprintf(result, "%f", a * b);
    return result;
}

/**
 * Eval function for a quotient expression
 * @param expr the expression to evaluate
 * @param ctxt the list of all variables and their values
 * @return the result of the quotient expression
 */
static char *evalQuot(Expr *expr, Context *ctxt)
{
    SumExpr *this = (SumExpr *)expr;

    char *left = this->leftExpr->eval(this->leftExpr, ctxt);
    char *right = this->rightExpr->eval(this->rightExpr, ctxt);

    double a, b;

    if (sscanf(left, "%lf", &a) != 1)
        a = 0.0;
    if (sscanf(right, "%lf", &b) != 1)
        b = 0.0;

    free(left);
    free(right);

    char *result = (char *)malloc(MAX_NUMBER + 1);
    sprintf(result, "%f", a / b);
    return result;
}

//evaluates the < than statement
static char *evalLessThan(Expr *expr, Context *ctxt)
{
    SumExpr *this = (SumExpr *) expr;
    
    
    char *left = this->leftExpr->eval(this->leftExpr, ctxt);
    char *right = this->rightExpr->eval(this->rightExpr, ctxt);

    double a, b;

    if (sscanf(left, "%lf", &a) != 1)
        a = 0.0;
    if (sscanf(right, "%lf", &b) != 1)
        b = 0.0;

    free(left);
    free(right);

    char *result = (char *)malloc(MAX_NUMBER + 1);
    if (a < b)
        sprintf(result, "%c", 't');
    else sprintf(result, "%s", "");

    return result;

}

//evaluates the || statement
static char *evalOr (Expr *expr, Context *ctxt)
{
    SumExpr *this = (SumExpr *) expr;
    
    
    char *left = this->leftExpr->eval(this->leftExpr, ctxt);
    char *right = this->rightExpr->eval(this->rightExpr, ctxt);


    char *result = (char *)malloc(MAX_NUMBER + 1);
    if (strcmp(left, "t") == 0 || strcmp(right, "t") == 0)
        sprintf(result, "%c", 't');
    else sprintf(result, "%s", "");

    free(left);
    free(right);

    return result;

}

//evaluates a && statement
static char *evalAnd (Expr *expr, Context *ctxt)
{
    SumExpr *this = (SumExpr *) expr;
    
    
    char *left = this->leftExpr->eval(this->leftExpr, ctxt);
    char *right = this->rightExpr->eval(this->rightExpr, ctxt);


    char *result = (char *)malloc(MAX_NUMBER + 1);
    if (strcmp(left, "t") == 0 && strcmp(right, "t") == 0)
        sprintf(result, "%c", 't');
    else sprintf(result, "%s", "");

    free(left);
    free(right);

    return result;

}

//evaluates an == statement
static char *evalEquals(Expr *expr, Context *ctxt)
{
    SumExpr *this = (SumExpr *) expr;
    
    
    char *left = this->leftExpr->eval(this->leftExpr, ctxt);
    char *right = this->rightExpr->eval(this->rightExpr, ctxt);

    char *result = (char *)malloc(MAX_NUMBER + 1);
    if (strcmp(left, right) == 0)
        sprintf(result, "%c", 't');
    else sprintf(result, "%s", "");

    free(left);
    free(right);

    return result;

}

//sets the value of a variable
static void evalAssignment(Expr *expr, Context *ctxt)
{
    VarExpr *this = (VarExpr *)expr;

    char *name = this->leftExpr->eval(this->leftExpr, ctxt);
    char *value = this->rightExpr->eval(this->rightExpr, ctxt);
    //printf("NAME: %s\nVALUE: %s\n", name, value);
    setVariable(ctxt, name, value);
    free(name);
    free(value);
    //printf("%s\n", getVariable(ctxt, name));
}

Expr *makeSum( Expr *leftExpr, Expr *rightExpr )
{
  // Make an instance of SumExpr
  SumExpr *this = (SumExpr *) malloc( sizeof( SumExpr ) );
  this->destroy = destroySum;
  this->eval = evalSum;

  // Remember the two sub-expressions.
  this->leftExpr = leftExpr;
  this->rightExpr = rightExpr;

  // Return the instance as if it's an Expr (which it sort of is)
  return (Expr *) this;
}

/**
 *  Evaluates a binary difference expression
 *  @param leftExpr left-hand expression to subtract.
 *  @param rightExpr right-hand expression to subtract.
 *  @return pointer to a new, dynamically allocated subclass of Expr.
 */
Expr *makeDifference(Expr *leftExpr, Expr *rightExpr)
{
    SumExpr *this = (SumExpr *)malloc(sizeof(SumExpr));
    this->destroy = destroySum;
    this->eval = evalDiff;

    this->leftExpr = leftExpr;
    this->rightExpr = rightExpr;

    return (Expr *) this;

}

/**
 *  Evaluates a binary product expression
 *  @param leftExpr left-hand expression to multiply.
 *  @param rightExpr right-hand expression to multiply.
 *  @return pointer to a new, dynamically allocated subclass of Expr.
 */
Expr *makeProduct(Expr *leftExpr, Expr *rightExpr)
{
    SumExpr *this = (SumExpr *)malloc(sizeof(SumExpr));
    this->destroy = destroySum;
    this->eval = evalProd;

    this->leftExpr = leftExpr;
    this->rightExpr = rightExpr;

    return (Expr *) this;
}

/**
 *  Evaluates a binary quotient expression
 *  @param leftExpr left-hand expression to divide.
 *  @param rightExpr right-hand expression to divide.
 *  @return pointer to a new, dynamically allocated subclass of Expr.
 */
Expr *makeQuotient(Expr *leftExpr, Expr *rightExpr)
{
    SumExpr *this = (SumExpr *)malloc(sizeof(SumExpr));
    this->destroy = destroySum;
    this->eval = evalQuot;

    this->leftExpr = leftExpr;
    this->rightExpr = rightExpr;

    return (Expr *) this;
}

/**
 *  Creates a less than "<" binary operation
 *  @param leftExpr left-hand expression.
 *  @param rightExpr right-hand expression.
 *  @return pointer to a new, dynamically allocated subclass of Expr.
 */
Expr *makeLessThan(Expr *leftExpr, Expr *rightExpr)
{
    SumExpr *this = (SumExpr *)malloc(sizeof(SumExpr));
    this->destroy = destroySum;
    this->eval = evalLessThan;

    this->leftExpr = leftExpr;
    this->rightExpr = rightExpr;

    return (Expr *) this;
}

/**
 *  Creates an equals to "==" binary operation
 *  @param leftExpr left-hand expression.
 *  @param rightExpr right-hand expression.
 *  @return pointer to a new, dynamically allocated subclass of Expr.
 */
Expr *makeEquals(Expr *leftExpr, Expr *rightExpr)
{
    SumExpr *this = (SumExpr *)malloc(sizeof(SumExpr));
    this->destroy = destroySum;
    this->eval = evalEquals;

    this->leftExpr = leftExpr;
    this->rightExpr = rightExpr;

    return (Expr *) this;
}

/**
 *  Creates an or clause
 *  @param leftExpr left-hand expression.
 *  @param rightExpr right-hand expression.
 *  @return pointer to a new, dynamically allocated subclass of Expr.
 */
Expr *makeOr(Expr *leftExpr, Expr *rightExpr)
{
    SumExpr *this = (SumExpr *)malloc(sizeof(SumExpr));
    this->destroy = destroySum;
    this->eval = evalOr;

    this->leftExpr = leftExpr;
    this->rightExpr = rightExpr;

    return (Expr *) this;
}


/**
 *  Creates an and clause
 *  @param leftExpr left-hand expression.
 *  @param rightExpr right-hand expression.
 *  @return pointer to a new, dynamically allocated subclass of Expr.
 */
Expr *makeAnd(Expr *leftExpr, Expr *rightExpr)
{
    SumExpr *this = (SumExpr *)malloc(sizeof(SumExpr));
    this->destroy = destroySum;
    this->eval = evalAnd;

    this->leftExpr = leftExpr;
    this->rightExpr = rightExpr;

    return (Expr *) this;
}


/**
 * Creates a variable assignment expression
 * @param leftExpr the name of the variable
 * @param rightExpr the value of the variable
 * @return pointer to a new, dynamically allocated subclass of Expr.
 */
Expr *makeAssignmentExpr(Expr *leftExpr, Expr *rightExpr)
{
    VarExpr *this =  (VarExpr *)malloc(sizeof(VarExpr));
    this->destroy = destroySum;
    this->eval = evalAssignment;
    
    this->leftExpr = leftExpr;
    this->rightExpr = rightExpr;

    return (Expr *) this;
}
