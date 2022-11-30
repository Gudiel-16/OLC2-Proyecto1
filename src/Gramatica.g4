grammar Gramatica;

options { caseInsensitive = true; }

parse:
    linstrucciones EOF;

linstrucciones:
    instruccion instruccion*
    ;

instruccion:
    progmain
    | declarationVar
    | asignationVar
    | printt
    | decArrExtenso
    | decArrCorto
    | asigArrList
    | asigArrPosEspecifico
    | declarationArrayDinamico
    | asignationArrayDinamico
    | desAsignationArrayDinamico
    | sentenciaIf
    | doEstructura
    | doWhileEstrctura
    | controlExit
    | controlCycle
    | etiquetaDo
    | etiquetaDoWhile
    | subroutine
    | call
    | funcion;

progmain:
    PROGRAM ID implicit linstrucciones END PROGRAM ID;

implicit: 'implicit' 'none';

declarationVar:
    type '::' declarationVar2 declarationVar3
    | type '::' declarationVar2;

declarationVar2:
    ID ('=' expr)? ;

declarationVar3:
    ',' declarationVar2 declarationVar3
    | ',' declarationVar2;

asignationVar:
    ID '=' expr;

printt:
    PRINT '*,' expr printt2
    | PRINT '*,' expr;

printt2: printt3 printt2
    | printt3;

printt3: ',' expr;

lexpr: expr (',' expr)*;

decArrExtenso: type ',' DIMENSION '(' expr declarationArray2? ')' '::' ID ;

decArrCorto: type '::' ID '(' expr declarationArray2? ')';

declarationArray2 : ',' expr ;

asigArrList: ID '=' '(/' lexpr '/)' ;

asigArrPosEspecifico: ID '[' lexpr ']' '=' expr;

asignationArray2: ',' expr asignationArray2
                | ',' expr;

declarationArrayDinamico:
                type ',' 'allocatable' '::' ID '(' ':' declarationArrayDinamico2? ')';

declarationArrayDinamico2: ',' ':' ;

asignationArrayDinamico: 'allocate' '(' ID '(' expr asignationArrayDinamico2? ')' ')';

desAsignationArrayDinamico: 'deallocate' '(' ID ')' ;

asignationArrayDinamico2: ',' expr;

sentenciaIf: 'if' lcondicionesIf ('else' 'if' lcondicionesIf)* ('else' elseBloque)? 'end' 'if';

lcondicionesIf
 : '(' expr ')' 'then' linstrucciones ;

elseBloque
 : linstrucciones ;

doEstructura: 'do' ID '=' expr ',' expr ',' expr linstrucciones 'end' 'do' ;

doWhileEstrctura: 'do' 'while' '(' expr ')' linstrucciones 'end' 'do';

controlExit: 'exit';

controlCycle: 'cycle';

etiquetaDo: ID ':' 'do' ID '=' expr ',' expr ',' expr linstrucciones 'end' 'do' ID ;

etiquetaDoWhile: ID ':' 'do' 'while' '(' expr ')' linstrucciones 'end' 'do' ID;

subroutine: 'subroutine' id1=ID '(' lexpr? ')' implicit ldeclP? linstrucciones 'end' 'subroutine' id2=ID;

ldeclP: declParametros+ ;

declParametros: type ',' 'intent' '(' 'in' ')' '::' valorParametros ;

valorParametros: ID
                | ID '(' expr (',' expr)? ')' ;

call: 'call' ID '(' lexpr? ')' ;

funcion: 'function' id1=ID '(' lexpr? ')' 'result' '(' idr=ID ')' implicit ldeclP? linstrucciones 'end' 'function' id2=ID;

expr:
    left=expr op='**'<assoc=right> right=expr                                               #powExpr
    | op='-' expr                                                                           #unaryMinusExpr
    | op='.not.' expr                                                                       #notExpr
    | left=expr op=('*'|'/') right=expr                                                     #opExpr
    | left=expr op=('+'|'-') right=expr                                                     #opExpr
    | left=expr op=(MAYOR | MENOR | MAYORQUE | MENORQUE) right=expr                         #relationalExpr
    | left=expr op=(IGUALIGUAL| DIFIGUAL) right=expr                                        #equalityExpr
    | left=expr op='.and.' right=expr                                                       #andExpr
    | left=expr op='.or.' right=expr                                                        #orExpr
    | '(' left=expr op=',' right=expr ')'                                                   #complexExpr
    | '(' expr ')'                                                                          #parentExpr
    | ID '[' lexpr ']'                                                                      #arrayAccesoExpr
    | ID '(' lexpr? ')'                                                                      #funcExpr
    | chtr= (CHTR1|CHTR2|STRING1)                                                           #charExpr
    | sizearr = SIZEARR '(' ID ')'                                                          #sizeArr
    | logicl= LOGICL                                                                        #logicalExpr
    | idd = ID                                                                              #idAtom
    | integ=INT                                                                             #integExpr
    | real=REAL                                                                             #realExpr
    ;

type:
    'integer'
    | 'real'
    | 'complex'
    | 'character'
    | 'logical';

PROGRAM: 'program';
END: 'end';
PRINT: 'print';
DIMENSION: 'dimension';
SIZEARR: 'size';

MENORQUE: ('<=' | '.le.');
MAYORQUE: ('>='|'.ge.');
MENOR: ('<' | '.lt.');
MAYOR: ('>'|'.gt.');
IGUALIGUAL: ('=='|'.eq.');
DIFIGUAL: ('/='|'.ne.');

CHTR1: '"'(~["\r\n] | '""')'"';
CHTR2: '\''(~['\r\n] | '""')+'\'';
LOGICL: ('.true.'|'.false.');
ID: [a-zA-Z][a-zA-Z0-9_]*;
INT: [0-9]+;
REAL: [0-9]+[.][0-9]+;
//COMPLEX:
STRING1: '"' (~["\r\n] | '""')* '"' ;

COMMENT : '!' ~[\r\n]* -> skip ;



WS: [ \n\t\r]+ -> skip ; //Son los que se van a ignorar
