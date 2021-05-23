package com.company.ContextualAnalyzer;

import com.company.AST.Node;
import com.company.AST.NonTerminalNode;
import com.company.AST.TerminalNode;
import com.company.Compiler;
import com.company.Main;
import com.company.Parser;
import com.company.ShufflerSymbols.PropertiesClass;
import com.company.ShufflerSymbols.ShufflerSymbols;
import com.company.Tokens.*;

public class VisitorVarDCLAndCheck extends Visitor
{


    public VisitorVarDCLAndCheck(ScopeTable aTable, Node aAstTree, ShufflerSymbols aShufflerSymbols) throws Exception {
        super(aTable, aAstTree, aShufflerSymbols);
    }

    @Override
    public ScopeTable StartVisitor() throws Exception {
        Compiler.PrintSymbolTable(_globalScope);
        RecursiveVisitor(_astTree);
        return _globalScope;
    }

    @Override
    void Visit(Node node) throws Exception
    {
        if (node instanceof TerminalNode)
        {
            switch (Parser.GetName(((TerminalNode) node).terminal))
            {
                case "lbrace" :
                    OpenScope();
                    AddScopeSymbols(node);
                    break;
                case "rbrace" :
                    CloseScope();
                    break;
                case "id":
                    if (!node.visited)
                    {
                        if (RetrieveSymbol(((idToken) ((TerminalNode) node).terminal).spelling) == null)
                        {
                            throw new Exception("Undeclared symbol: " + ((idToken) ((TerminalNode) node).terminal).spelling);
                        }
                    }
                    break;
                default :
            }
        }
        else if(node instanceof NonTerminalNode)
        {
            switch (((NonTerminalNode) node).nonterminal)
            {
                case "DeckDcl" :
                    EnterSymbolToCurrentScope(EnterDclSymbolHelper(node, "deck"));
                    break;
                case "NumberDcl" :
                    EnterSymbolToCurrentScope(EnterDclSymbolHelper(node, "number"));
                    break;
                case "CardDcl" :
                    EnterSymbolToCurrentScope(EnterDclSymbolHelper(node, "card"));
                    break;
                case "HandDcl" :
                    EnterSymbolToCurrentScope(EnterDclSymbolHelper(node, "hand"));
                    break;
                case "EnumDcl" :
                    EnterSymbolToCurrentScope(EnterDclSymbolHelper(node, "enum"));
                    break;
                case "StringDcl" :
                    EnterSymbolToCurrentScope(EnterDclSymbolHelper(node, "string"));
                    break;
                case "FlagDcl" :
                    EnterSymbolToCurrentScope(EnterDclSymbolHelper(node, "flag"));
                    break;
                case "ObjectSpecifier" :
                    String s = HandleObjectSpecifier(node);

                    if (RetrieveSymbol(s) == null)
                    {
                        throw new Exception("Undeclared symbol at line " + ((TerminalNode) node.leftMostChild).terminal.line + ": " + s );
                    }
                    break;
            }
        }
    }
    void AddScopeSymbols(Node node) throws Exception
    {
        switch (((NonTerminalNode) node.parent.parent).nonterminal)
        {
            case "Turn":
                AddTurnSymbols();
                break;
            case "Endcondition":
                AddEndconditionSymbols();
                break;
        }
    }
    void AddEndconditionSymbols() throws Exception
    {
        for (PropertiesClass item: _shufflerSymbols.DefaultEndconditionSymbols.symbols ) {
            AddSymbolToTable(new Symbol(item.name, item.type), _stack.peek());
        }
    }

    void AddTurnSymbols() throws Exception
    {
        String keyCheck = "player.numberValue";
        for (String key:_globalScope.table.keySet()) {
            if(key.contains(keyCheck))
            {
                String id = "turntaker" + key.substring(keyCheck.length());
                _stack.peek().table.put(id,new Symbol(id,_globalScope.table.get(key).Type()));
            }
        }
        for (PropertiesClass item: _shufflerSymbols.DefaultTurnSymbols.symbols ) {
            AddSymbolToTable(new Symbol(item.name, item.type),_stack.peek());
        }

    }
    @Override
    Symbol EnterDclSymbolHelper(Node node, String type) throws Exception
    {
        node.visited = true;
        return super.EnterDclSymbolHelper(node, type);
    }

    String HandleObjectSpecifier(Node node)
    {
        String id = "";
        Node next = null;
        if (node instanceof NonTerminalNode && node.leftMostChild != null)
        {
            switch (((NonTerminalNode) node).nonterminal)
            {
                case "ObjectSpecifier":
                case "FollowObject":
                    next = node.leftMostChild.rightSib;
                    break;
                case "FollowObject1":
                    next = node.rightSib;
                    break;
                default:
                    next = null;
            }
        }
        if (node.leftMostChild instanceof TerminalNode)
        {
            switch (Parser.GetName(((TerminalNode) node.leftMostChild).terminal))
            {
                case "dot":
                    id += ".";
                    break;
                case "id":
                    Symbol symbol = RetrieveSymbol(((idToken) ((TerminalNode) node.leftMostChild).terminal).spelling);
                    if (symbol != null && !((NonTerminalNode) node).nonterminal.equals("ObjectSpecifier"))
                    {
                        if (symbol._type.equals("number"))
                        {
                            id += "numberValue";
                            node.leftMostChild.type = "number";
                        }
                        else
                        {
                            id += ((idToken) ((TerminalNode) node.leftMostChild).terminal).spelling;
                        }
                    }
                    else
                    {
                        id += ((idToken) ((TerminalNode) node.leftMostChild).terminal).spelling;
                    }
                    node.leftMostChild.visited = true;
                    break;
            }
        }
        else  if (node.leftMostChild instanceof NonTerminalNode)
        {
            switch (((NonTerminalNode) node.leftMostChild).nonterminal)
            {
                case "String":
                    id += "stringValue";
                    break;
                case "Number":
                        id += "numberValue";
                        node.leftMostChild.type = "number";
                    break;
                case "Card":
                    id += "cardValue";
                    break;
            }
        }
        if (next != null)
        {
            id += HandleObjectSpecifier(next);
        }
        return id;
    }
}
