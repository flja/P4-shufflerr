package com.company.AST;

import com.company.Tokens.Token;

public class TerminalNode extends Node
{
    public Token terminal;

    public TerminalNode(Token aTerminal)
    {
        this.terminal = aTerminal;
    }
}
