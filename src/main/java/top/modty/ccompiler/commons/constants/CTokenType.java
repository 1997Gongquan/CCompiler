package top.modty.ccompiler.commons.constants;

public enum CTokenType {
	
	//非终结符
	PROGRAM, EXT_DEF_LIST, EXT_DEF, OPT_SPECIFIERS, EXT_DECL_LIST,
	EXT_DECL, VAR_DECL, SPECIFIERS,
	TYPE_OR_CLASS, TYPE_NT,

	/*struct定义*/
	STRUCT_SPECIFIER,OPT_TAG,DEF_LIST,TAG,DEF,DECL_LIST,DECL,

	FUNCT_DECL, VAR_LIST, PARAM_DECLARATION,

	//枚举
	ENUM_SPECIFIER, OPT_ENUM_LIST, ENUMERATOR_LIST, ENUMERATOR, ENUM_NT,

	CONST_EXPR, ARGS,

	COMPOUND_STMT, LOCAL_DEFS, STMT_LIST, INITIALIZER, EXPR, NO_COMMA_EXPR,BINARY, UNARY, STATEMENT,

	TEST, OPT_EXPR, END_OPT_EXPR, TARGET, ELSE_STATEMENT, IF_STATEMENT, IF_ELSE_STATEMENT,

	TYPE_SPECIFIER, NEW_NAME, NAME_NT,
		
    //终结符
	NAME/*标识符*/, TYPE/*char、float、double、int、long、short、void*/, STRUCT,CLASS/*auto、static、register*/, LP/*(*/, RP/*)*/, LB/*[*/, RB/*]*/,PLUS/*+*/,LC/*{*/,RC/*}*/, NUMBER, STRING/*""*/, QUEST/*?*/, COLON/*:*/,
	//
	RELOP/*==、<=、<、>*/, ANDAND, OR/*|*/, AND/*&*/, EQUOP, SHIFTOP/*>>、<<*/, DIVOP/*/、%*/, XOR/*^*/, MINUS/*-*/, INCOP/*++*/, DECOP/*--*/, STRUCTOP/*->、.*/,
	
	RETURN/*return*/, IF/*if*/, ELSE/*else*/, SWITCH/*switch*/, CASE/*case*/, DEFAULT/*default*/, BREAK/*break*/, WHILE/*while*/, FOR/*for*/, DO/*do*/, CONTINUE/*continue*/, GOTO/*goto*/,
	
	//枚举定义
	ENUM/*enum*/,
	COMMA/*,*/, SEMI/*;*/, WHITE_SPACE/*\n、\t、 */, EQUAL/*=*/,TTYPE, STAR/***/, UNKNOWN_TOKEN/*未识别类型*/;
	
	
	
	public static final int FIRST_TERMINAL_INDEX = NAME.ordinal();
	public static  final int LAST_TERMINAL_INDEX = UNKNOWN_TOKEN.ordinal();
	
	public static final int FIRST_NON_TERMINAL_INDEX = PROGRAM.ordinal();
	public static final int LAST_NON_TERMINAL_INDEX = NAME_NT.ordinal();
	
	public static String getSymbolStr(int val) {
		return CTokenType.values()[val].toString();
	}
	
	public static boolean isTerminal(int val) {
		if (FIRST_TERMINAL_INDEX <= val && val <= LAST_TERMINAL_INDEX) {
			return true;
		}
		return false;
	}
	
}
