/**
  @file stmt.h
  @author

  Representation for statements in the language.
*/

#ifndef _STMT_H_
#define _STMT_H_

#include "expr.h"

//////////////////////////////////////////////////////////////////////
// Stmt

/** A short name to use for the statement interface. */
typedef struct StmtTag Stmt;

/** Representation for the Stat interface, a superclass for all types
    of statements.  Classes implementing this have these two fields as
    their first members.  They will set execute to point to
    appropriate functions to execute the type of statement their
    class represents, and they will set destroy to point to a function
    that frees memory for their type of statement.
*/
struct StmtTag {
  /** Pointer to a function to execute the given staement.
      @param stmt staement to be evaluated.
      @param ctxt current values of all variables.
   */
  void (*execute)( Stmt *stmt, Context *ctxt );

  /** Free memory for this statement, including any other statements and
      expressions it contains.
      @param stmt statement to free.
  */
  void (*destroy)( Stmt *stmt );
};

/** Make a statement that evaluates the given argument and prints it
    to the terminal.
    @param arg expression to evaluate and print.  The print statement
    is be responsible for freeing when it is destoryed.
    @return a new print statement.
 */
Stmt *makePrint( Expr *arg );

/** Make a compound statement, representing the sequence of statements
    @param stmtList list of statements making up this compound. The
    compound statement will be responsible for freeing this list and
    the staements it contains when it is destroyed.
    @param len number of statements in stmtList.
    @return a new statement that executes all the statements in
    stmtList, in order.
 */
Stmt *makeCompound( Stmt **stmtList, int len );

/**
 * Creates an assignment statement for variable assignment
 * @param arg the expression associated w/ the statement
 * @return the assignment statement
 */
Stmt *makeAssignmentStmt(Expr *arg);

/**
 * Creates an if statement
 * @param cond the condition expression
 * @param body the body of the if statement
 * @return the if statement
 */
Stmt *makeIf (Expr *cond, Stmt *body);

/**
 * Creates a while statement
 * @param cond the condition expression
 * @param body the body of the if statement
 * @return the while statement
 */
Stmt *makeWhile (Expr *cond, Stmt *body);


#endif
