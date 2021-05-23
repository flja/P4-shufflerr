package com.company.ContextualAnalyzer;

import com.company.AST.Node;
import com.company.AST.NonTerminalNode;
import com.company.AST.TerminalNode;
import com.company.ShufflerSymbols.PropertiesClass;
import com.company.ShufflerSymbols.ShufflerSymbols;
import com.company.Tokens.idToken;

import java.util.List;
import java.util.Stack;

public abstract class Visitor {
    Stack<ScopeTable> _stack = new Stack<ScopeTable>();
    ScopeTable _globalScope;
    Node _astTree;
    ShufflerSymbols _shufflerSymbols;

    public Visitor(ScopeTable aTable, Node aAstTree, ShufflerSymbols aShufflerSymbols) {
        _globalScope = aTable;
        _astTree = aAstTree;
        _stack.push(_globalScope);
        _shufflerSymbols = aShufflerSymbols;
    }

    public ScopeTable StartVisitor() throws Exception {
        RecursiveVisitor(_astTree);
        return _globalScope;
    }

    void RecursiveVisitor(Node node) throws Exception {
        Visit(node);
        for (Node child : node.GetChildren()) {
            RecursiveVisitor(child);
        }
    }

    void OpenScope() {
        ScopeTable table = new ScopeTable();
        table.previous = _stack.peek();
        _stack.peek().subScopes.add(table);
        _stack.push(table);
    }

    void CloseScope() {
        _stack.pop();
    }

    boolean CheckNonTerminalType(Node node, String nonTerminal) {
        if (node instanceof NonTerminalNode) {
            if (((NonTerminalNode) node).nonterminal.equals(nonTerminal)) {
                return true;
            }
        }
        return false;
    }

    void ReportError(String Message) {

    }

    public Symbol RetrieveSymbol(String name) {
        ScopeTable current = _stack.peek();
        while (current != null) {
            if (current.table.get(name) != null) {
                return current.table.get(name);
            } else {
                current = current.previous;
            }
        }
        return null;
    }

    void EnterSymbolToCurrentScope(Symbol symbol) throws Exception {
        AddSymbolToTable(symbol, _stack.peek());
    }

    public void EnterGlobalSymbol(Symbol symbol) throws Exception
    {
        AddSymbolToTable(symbol, _globalScope);
    }

    void AddSymbolToTable(Symbol symbol, ScopeTable scope) throws Exception
    {
        if (DeclaredLocally(symbol.Id()))
        {
            throw new Exception("Error at line " +   "Dublicate Definition of " + symbol.Id());

        } else
        {
            scope.table.put(symbol.Id(), symbol);
        }
        AddPropertiesToTable(symbol, scope);
    }
    Symbol EnterDclSymbolHelper(Node node, String type) throws Exception
    {
        return new Symbol((((idToken) ((TerminalNode) (node.leftMostChild.rightSib)).terminal).spelling), type);
    }

    public boolean DeclaredLocally(String name)
    {
        return _stack.peek().table.get(name) != null;
    }

    void AddPropertiesToTable(Symbol symbol, ScopeTable table) throws Exception {
        switch (symbol.Type())
        {
            case "hand" :
                AddProperties(symbol.Id(), _shufflerSymbols.TypeProperties.hand.properties, table);
                break;
            case "deck" :
                AddProperties(symbol.Id(), _shufflerSymbols.TypeProperties.deck.properties, table);
                break;
        }

    }
    void AddProperties(String id, List<PropertiesClass> properties, ScopeTable scopeTable) throws Exception {
        for (PropertiesClass p : properties) {
            String s = id + "." + p.name;
            AddSymbolToTable(new Symbol(s, p.type),scopeTable);
        }
    }

    abstract void Visit(Node node) throws Exception;

}
