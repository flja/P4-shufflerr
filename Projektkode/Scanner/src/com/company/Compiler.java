package com.company;

import com.company.AST.AST;
import com.company.AST.Node;
import com.company.AST.NonTerminalNode;
import com.company.AST.TerminalNode;
import com.company.CodeGenerator.Generator;
import com.company.CodeGenerator.JavaGenerator;
import com.company.ContextualAnalyzer.Analyzer;
import com.company.ContextualAnalyzer.ScopeTable;
import com.company.ShufflerSymbols.LoadShufflerSymbols;
import com.company.ShufflerSymbols.ShufflerSymbols;

public class Compiler
{
    public void Compile() throws Exception
    {
        ShufflerSymbols shufflerSymbols = new LoadShufflerSymbols().Load();
        Scanner1 scanner = new Scanner1();
        Parser parser = new Parser();
        AST ast = parser.LLparser(scanner.Lexer());
        Analyzer analyzer = new Analyzer(ast);
        ast = analyzer.RunAnalyzer();
        Node node = ast.Root;
        ScopeTable SymbolTable = analyzer._globalScope;
        PrintSymbolTable(SymbolTable);
        prettyPrintAST(node);
        ast.ResetVisit();
        JavaGenerator javaGenerator = new JavaGenerator();
        Generator codeGenerator = new Generator(ast);
        codeGenerator.StartGenerator();
    }


    public static void printNode(Node node, int indents)
    {
        String indentation = "";
        for (int i = 0; i < indents; i++)
        {
            indentation += "|   ";
        }
        if(node instanceof TerminalNode)
        {
            System.out.println(indentation  + ((TerminalNode) node).terminal.getClass().getSimpleName() + " " + node.type + " " + ((TerminalNode) node).terminal.line);
        }
        else
        {
            System.out.println(indentation + ((NonTerminalNode) node).nonterminal + " " + node.type);
        }
    }

    public static void prettyPrintAST(Node node)
    {
        int indent = 0;
        System.out.println("\n\n");
        printNode(node, indent);
        while (node != null)
        {
            if (node.leftMostChild != null && !node.leftMostChild.visited)
            {
                node = node.leftMostChild;
                indent += 1;
                printNode(node, indent);
            }
            else if (node.rightSib != null && !node.rightSib.visited)
            {
                node = node.rightSib;
                printNode(node, indent);
            }
            else if (node.parent != null)
            {
                node = node.parent;
                indent -= 1;
            }
            else
            {
                node = null;
            }
            if (node != null)
            {
                node.visited = true;
            }
        }
    }

    public static void PrintSymbolTable(ScopeTable globalScope)
    {
        int indent = 1;
        System.out.println("\n\n");
        System.out.println("SymbolTable:");
        printScopes(globalScope, indent);
    }
    static int j = 0;
    public static void printScopes(ScopeTable scopeTable, int indent)
    {
        System.out.println("Scope " + j);
        for (String item : scopeTable.table.keySet())
        {
            PrintSymbol(scopeTable.table.get(item).ToString(), indent);
        }
        j++;
        indent++;
        for (ScopeTable item : scopeTable.subScopes)
        {
            printScopes(item, indent);
        }
    }

    public static void PrintSymbol(String symbol, int indents)
    {
        String indentation = "";
        for (int i = 0; i < indents; i++)
        {
            indentation += "|   ";
        }
        System.out.println(indentation + symbol.toString());
    }
}
