grammar Javali; // parser grammar, parses streams of tokens

@header {
	// Java header
	package cd.frontend.parser;
}

// PARSER RULES 

// types
primitiveType : 'boolean' | 'int'										#primType							
				;
type :	 		primitiveType | referenceType							
				;
referenceType : Ident | arrayType;

arrayType :	 	Ident '[' ']'  
				|  primitiveType '[' ']';

// program structure
unit : 			classDecl + EOF ;

classDecl : 		'class' Ident ('extends' Ident)? '{' memberList '}';

memberList : 	(varDecl | methodDecl)*;

varDecl : 		type Ident (',' Ident )* ';' ;

methodDecl : 	(type | 'void') Ident '(' formalParamList? ')' 
				'{' varDecl* stmt* '}' ;

formalParamList : type Ident (',' type Ident)* ;

// statements
stmt : 			  assignmentStmt 										#stmtAssign
				| methodCallStmt 										#stmtMetCall
				| ifStmt 												#stmtIf
				| whileStmt 												#stmtWhile
				| returnStmt 											#stmtReturn
				| writeStmt												#stmtWrite
				;

stmtBlock : 		'{' stmt* '}';

methodCallStmt : Ident '(' actualParamList? ')'	';'						#methCall
				 | identAccess '.' Ident '(' actualParamList? ')'	 ';'		#methIaCall
				 ;

assignmentStmt :   identAccess '=' expr  ';'								
				 | identAccess '=' newExpr ';'							
				 | identAccess '=' readExpr ';'							
				 ;

writeStmt : 		  'write' '(' expr ')' ';'								#write
				| 'writeln' '(' ')' ';' 									#writeln
				;

ifStmt : 		'if' '(' expr ')' stmtBlock ('else' stmtBlock)? ;

whileStmt : 		'while' '(' expr ')' stmtBlock ;

returnStmt : 	'return' expr? ';' ;


// expressions
newExpr  : 		'new' Ident '(' ')' 										#newObj
				| 'new' Ident '[' expr ']' 								#newIArray
				| 'new' primitiveType '[' expr ']'						#newPArray
				;

readExpr :		'read' '(' ')'											#read
				;

actualParamList :expr ( ',' expr )*;

identAccess : 	Ident 													#iaIdent
				| 'this' 												#iaThis
				| identAccess '.' Ident 									#iaIaIdent
				| identAccess '[' expr ']' 								#iaArrayAccess
				| 	Ident '(' actualParamList? ')' 						#iaMethodCall
				| 	identAccess '.' Ident '(' actualParamList? ')'		#iaIaMethodCall
				;
				
expr : 			Literal 													#LIT
				| identAccess 											#TERM
				| '(' expr ')'											#BRACKETS
				| ('+' | '-' | '!') expr									#UNARY
				| '(' referenceType ')' expr								#CAST
				| expr ('*' | '/' | '%') expr							#MULTI
				| expr ('+' | '-') expr									#ADDI
				| expr ('<' | '<=' | '>' | '>=') expr					#COMP
				| expr ('==' | '!=') expr								#EQI
				| expr '&&' expr											#AND
				| expr '||' expr											#OR
				;
			
// LEXER RULES

fragment
Letter : 		'A'..'Z' |	'a'..'z';

fragment
Digit : 			'0'..'9';

fragment
HexDigit : 		Digit |  'a'..'f'  |  'A'..'F';

fragment
Decimal  :		'0' | '1'..'9' Digit*;

fragment
Hex :			('0x' | '0X') HexDigit+;

fragment
Integer :		Hex | Decimal;

fragment
Boolean : 		'false' | 'true';

Ident : 			Letter  (Letter | Digit )*;

Literal :		'null' | Boolean | Integer;
	
// comments and white space does not produce tokens:
COMMENT :		'/*' .*? '*/' -> skip;					// Multi-line comment 

LINE_COMMENT :	'//' ~('\n'|'\r')* -> skip;				// Line comment

WS : 			(' '|'\r'|'\t'|'\n') -> skip;			// White space


// handle characters which failed to match any other token
//ErrorCharacter : . ;
