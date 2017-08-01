#include "stmt.h"
#include "expr.h"
#include <stdlib.h>
#include <string.h>

//////////////////////////////////////////////////////////////////////
// Print

// Representation for a print statement, derived from Stmt.
typedef struct {
  void (*execute)( Stmt *stmt, Context *ctxt );
  void (*destroy)( Stmt *stmt );

  /** Argument expression we're supposed to evaluate and print. */
  Expr *arg;
} PrintStmt;

// Function to execute a print statement.
static void executePrint( Stmt *stmt, Context *ctxt )
{
  // Cast the this pointer to a more specific type.
  PrintStmt *this = (PrintStmt *)stmt;

  // Evaluate our argument, print the result, then free it.
  char *result = this->arg->eval( this->arg, ctxt );
  printf( "%s", result );
  free( result );
}

//executes the an assignment statement
static void executeAssignment(Stmt *stmt, Context *ctxt)
{
    PrintStmt *this = (PrintStmt *)stmt;
    this->arg->eval(this->arg, ctxt);
}


// Function to free a print statement.
static void destroyPrint( Stmt *stmt )
{
  // Cast the this pointer to a more specific type.
  PrintStmt *this = (PrintStmt *)stmt;

  // Free our subexpression then the print statement itself.
  this->arg->destroy( this->arg );
  free( this );
}

Stmt *makePrint( Expr *arg )
{
  // Allocate space for the PrintStmt object
  PrintStmt *this = (PrintStmt *) malloc( sizeof( PrintStmt ) );

  // Remember our virutal functions.
  this->execute = executePrint;
  this->destroy = destroyPrint;

  // Remember our argument subexpression.
  this->arg = arg;

  // Return the result, as an instance of the Stmt interface.
  return (Stmt *) this;
}

Stmt *makeAssignmentStmt(Expr *arg)
{
    PrintStmt *this = (PrintStmt *) malloc(sizeof(PrintStmt));
    this->execute = executeAssignment;
    this->destroy = destroyPrint;
    
    this->arg = arg;

    return (Stmt *) this;
}
//////////////////////////////////////////////////////////////////////
// Compound

// Representation for a compound statement, derived from Stmt.
typedef struct {
  void (*execute)( Stmt *stmt, Context *ctxt );
  void (*destroy)( Stmt *stmt );

  /** List of statements in the compound. */
  Stmt **stmtList;

  /** Number of statements in the compound. */
  int len;
} CompoundStmt;


//representation for a control statement derived from Stmt
typedef struct {
  void (*execute)(Stmt *stmt, Context *ctxt);
  void (*destroy)(Stmt *stmt);

  Expr *cond;
  Stmt *body;

} IfStmt;


// Function to evaluate a compound statement.
static void executeCompound( Stmt *stmt, Context *ctxt )
{
  // Cast the this pointer to a more specific type.
  CompoundStmt *this = (CompoundStmt *)stmt;

  // Execute the sequence of statements in this compound
  for ( int i = 0; i < this->len; i++ )
    this->stmtList[ i ]->execute( this->stmtList[ i ], ctxt );
}


//Function to evaluate if statement
static void executeIf(Stmt *stmt, Context *ctxt)
{
    IfStmt *this = (IfStmt *)stmt;

    char *condition = this->cond->eval(this->cond, ctxt);

    if (strcmp(condition, "") != 0)
        this->body->execute(this->body, ctxt);

    free(condition);

}

//Function to evaluate if statement
static void executeWhile(Stmt *stmt, Context *ctxt)
{
    IfStmt *this = (IfStmt *)stmt;

    char *condition = this->cond->eval(this->cond, ctxt);
    while (strcmp(condition, "") != 0) {
        free(condition);
        this->body->execute(this->body, ctxt);
        condition = this->cond->eval(this->cond, ctxt);
    }
    free(condition);

}

// Function to free a compound statement.
static void destroyCompound( Stmt *stmt )
{
  // Cast the this pointer to a more specific type.
  CompoundStmt *this = (CompoundStmt *)stmt;

  // Free the list of statements inside this compond.
  for ( int i = 0; i < this->len; i++ )
    this->stmtList[ i ]->destroy( this->stmtList[ i ] );

  // Then, free the compound statement itself.
  free( this->stmtList );
  free( this );
}

//Function to free an if and while statement
static void destroyIf(Stmt *stmt)
{
    IfStmt *this = (IfStmt *) stmt;
    this->body->destroy(this->body);
    this->cond->destroy(this->cond);
    free(this);
}

Stmt *makeCompound( Stmt **stmtList, int len )
{
  // Allocate space for the CompoundStmt object
  CompoundStmt *this = (CompoundStmt *) malloc( sizeof( CompoundStmt ) );

  // Remember our virutal functions.
  this->execute = executeCompound;
  this->destroy = destroyCompound;

  // Remember the list of statements in the compound.
  this->stmtList = stmtList;
  this->len = len;

  // Return the result, as an instance of the Stmt interface.
  return (Stmt *) this;
}

Stmt *makeIf (Expr *cond, Stmt *body)
{
    IfStmt *this = (IfStmt*) malloc(sizeof(IfStmt));

    this->execute = executeIf;
    this->destroy = destroyIf;

    this->body = body;
    this->cond = cond;

    return (Stmt *) this;
}

Stmt *makeWhile (Expr *cond, Stmt *body)
{
    IfStmt *this = (IfStmt*) malloc(sizeof(IfStmt));

    this->execute = executeWhile;
    this->destroy = destroyIf;

    this->body = body;
    this->cond = cond;

    return (Stmt *) this;
}
