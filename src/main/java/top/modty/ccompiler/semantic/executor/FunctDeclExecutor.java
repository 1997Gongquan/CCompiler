package top.modty.ccompiler.semantic.executor;

import top.modty.ccompiler.commons.constants.Directive;
import top.modty.ccompiler.commons.constants.ICodeKey;
import top.modty.ccompiler.grammar.CGrammarInitializer;
import top.modty.ccompiler.semantic.*;
import top.modty.ccompiler.semantic.code.ProgramGenerator;
import top.modty.ccompiler.semantic.inter.ICodeNode;
import top.modty.ccompiler.semantic.inter.IValueSetter;

import java.util.ArrayList;
import java.util.Collections;
/**
 * @author 点木
 * @date 2020-05-05 16:05
 * @mes 实现由参数传递的函数调用
 */
public class FunctDeclExecutor extends BaseExecutor {
    private ArrayList<Object> argsList = null;
    private ICodeNode currentNode;
    ProgramGenerator generator = ProgramGenerator.getInstance();
    
	@Override
	public Object Execute(ICodeNode root) {
		int production = (Integer)root.getAttribute(ICodeKey.PRODUCTION);
		Symbol symbol = null;
		currentNode = root;
		
		switch (production) {
		case CGrammarInitializer.NewName_LP_RP_TO_FunctDecl:
			root.reverseChildren();
			ICodeNode n = root.getChildren().get(0);
			String name = (String)n.getAttribute(ICodeKey.TEXT);
			symbol = (Symbol)root.getAttribute(ICodeKey.SYMBOL);
			generator.setCurrentFuncName(name);
			if (name != null && name.equals("main") != true) {
				String declaration = name+ emitArgs(symbol);
				generator.emitDirective(Directive.METHOD_PUBBLIC_STATIC, declaration);
				generator.setNameAndDeclaration(name, declaration);
			}
			copyChild(root, root.getChildren().get(0));
			break;
			
		case  CGrammarInitializer.NewName_LP_VarList_RP_TO_FunctDecl:
			n = root.getChildren().get(0);
			name = (String)n.getAttribute(ICodeKey.TEXT);
			symbol = (Symbol)root.getAttribute(ICodeKey.SYMBOL);
			generator.setCurrentFuncName(name);
			if (name != null && name.equals("main") != true) {
				String declaration = name + emitArgs(symbol);
				generator.emitDirective(Directive.METHOD_PUBBLIC_STATIC, declaration);
				generator.setNameAndDeclaration(name, declaration);
			}
			
			//获得参数列表
			Symbol args = symbol.getArgList();
			initArgumentList(args);
			
			
			if (args == null || argsList == null || argsList.isEmpty()) {
				//如果参数为空，那就是解析错误
				System.err.println("Execute function with arg list but arg list is null");
				System.exit(1);
			}
		
 			break;
		}
		return root;
	}
	
	private void initArgumentList(Symbol args) {
		if (args == null) {
			return;
		}
		
		generator.initFuncArguments(true);
		argsList = FunctionArgumentList.getFunctionArgumentList().getFuncArgList(true);
		Symbol eachSym = args;
		int count = 0;
		while (eachSym != null && eachSym.getDeclarator(Declarator.POINTER) != null) {
			IValueSetter setter = (IValueSetter)eachSym;
			try {
				/*
				 * 将每个输入参数设置为对应值并加入符号表
				 */
				setter.setValue(argsList.get(count));
				count++;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			eachSym = eachSym.getNextSymbol();
		}
		generator.initFuncArguments(false);
	}
	
	
	private String emitArgs(Symbol funSymbol) {
		Symbol s = funSymbol.getArgList();
		ArrayList<Symbol> params = new ArrayList<Symbol>();
		while (s != null) {
			params.add(s);
			s = s.getNextSymbol();
		}
		Collections.reverse(params);
		String args = "(";
		for (int i = 0; i < params.size(); i++) {
			Symbol symbol = params.get(i);
			String arg = "";
			if (symbol.getDeclarator(Declarator.ARRAY) != null || symbol.getDeclarator(Declarator.POINTER) != null) {
				arg += "[";
			}
			
			if (symbol.hasType(Specifier.INT)) {
				arg += "I";
			}
			
			args += arg;
		}
		
		if (funSymbol.hasType(Specifier.INT)) {
			args += ")I";
		} else {
		    args += ")V";
		}
		
		return args;
	}

	
}
