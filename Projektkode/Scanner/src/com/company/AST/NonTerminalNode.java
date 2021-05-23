package com.company.AST;

import com.company.Tokens.Token;

public class NonTerminalNode extends Node
{
    public String nonterminal;

    public NonTerminalNode(String aNonterminal)
    {
        this.nonterminal = aNonterminal;
    }
}
