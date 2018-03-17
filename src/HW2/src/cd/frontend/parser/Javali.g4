grammar Javali; // parser grammar, parses streams of tokens

@header {
	// Java header
	package cd.frontend.parser;
}

// PARSER RULES 

// types
primitiveType : 'boolean' | 'int';

type :	 		primitiveType | referenceType;
				
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
stmt : 			  assignmentStmt 
				| methodCallStmt 
				| ifStmt 
				| whileStmt 
				| returnStmt 
				| writeStmt
				;

stmtBlock : 		'{' stmt* '}';

methodCallStmt : Ident '(' actualParamList? ')' 
				 | identAccess '.' Ident '(' actualParamList? ')'
				 ';' ;

assignmentStmt : identAccess '=' (expr | newExpr | readExpr) ';';

writeStmt : 		('write' '(' expr ')' | 'writeln' '(' ')' ) ';' ;

ifStmt : 		'if' '(' expr ')' stmtBlock ('else' stmtBlock)? ;

whileStmt : 		'while' '(' expr ')' stmtBlock ;

returnStmt : 	'return' expr? ';' ;


// expressions
newExpr  : 		'new' 
				(	Ident '(' ')' 
					| Ident '[' expr ']' 
					| primitiveType '[' expr ']'
				) ;

readExpr :		'read' '(' ')';

actualParamList :expr ( ',' expr )*;

identAccess : 	Ident 
				| 'this' 
				| identAccess '.' Ident 
				| identAccess '[' expr ']' 
				| 	Ident '(' actualParamList? ')' 
				| 	identAccess '.' Ident '(' actualParamList? ')'
				;
				
expr : 			Literal 
				| identAccess 
				| '(' expr ')' 
				| ('+' | '-' | '!') expr					// Unary operators
				| '(' referenceType ')' expr				// Cast
				| expr ('*' | '/' | '%') expr			// Multiplicative operators
				| expr ('+' | '-') expr					// Additive operators
				| expr ('<' | '<=' | '>' | '>=') expr	// Comparative operators
				| expr ('==' | '!=') expr				// Equality operators
				| expr '&&' expr							// Logical and
				| expr '||' expr							// Logical or
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
