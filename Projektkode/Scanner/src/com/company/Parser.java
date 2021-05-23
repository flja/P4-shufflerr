package com.company;
import com.company.AST.AST;
import com.company.AST.Node;
import com.company.AST.NonTerminalNode;
import com.company.AST.TerminalNode;
import com.company.Tokens.Token;
import com.company.Tokens.idToken;

import java.util.*;

public class Parser
{
    public AST LLparser(List<Token> ts) throws Exception
    {

        int tsIndex = 0;
        Node rootNode;
        Node currentNode;
        List<String> terminals = Arrays.asList(Data.terminals);
        List<String> nonTerminals = Arrays.asList(Data.nonterminals);
        boolean accepted;
        Stack<String> stack = new Stack<String>();
        stack.clear();
        stack.push("Prog");
        rootNode = new NonTerminalNode(stack.peek());
        currentNode = rootNode;
        accepted = false;
        while (!accepted)
        {
            System.out.println("stack: " + stack.peek() + " ts: " + GetName(ts.get(tsIndex)));
            System.out.println("Line: " + ts.get(tsIndex).line);
            if (isTerminal(stack.peek()))
            {
                System.out.println("is terminal");
                if(match(ts.get(tsIndex),stack.peek()))
                {
                    System.out.println("matches");
                    if (currentNode instanceof NonTerminalNode)
                    {
                        System.out.println(((NonTerminalNode) currentNode).nonterminal);
                    }
                    ((TerminalNode)currentNode).terminal = ts.get(tsIndex);
                    tsIndex++;
                }
                stack.pop();
                currentNode.visited = true;
                currentNode = setCurrentNode(currentNode);
                if (stack.peek().equals("$"))
                {
                    accepted = true;
                }
            }
            else
            {
                System.out.println("is nonterminal");
                int p = Data.generateTable().get(nonTerminals.indexOf(stack.peek())).get(terminals.indexOf(GetName(ts.get(tsIndex))));
                System.out.println(nonTerminals.indexOf(stack.peek()) + " " + terminals.indexOf(GetName(ts.get(tsIndex))));
                if (p == 0)
                {
                    throw new Exception("Error at line " + ts.get(tsIndex).line + ": Unexpected token\n");
                }
                System.out.println("Production: " + p);
                List<String> A = Data.getProduction(p);
                Node child = null;
                stack.pop();
                for (int i = A.size() - 1; i >= 0; i--)
                {
                    stack.push(A.get(i));
                }
                for (int i = 0; i < A.size(); i++)
                {
                    if (child == null)
                    {
                        if (isTerminal(A.get(i)))
                        {
                            child = new TerminalNode(null);
                        }
                        else
                        {
                            child = new NonTerminalNode(A.get(i));
                        }
                    }
                    else
                    {
                        if (isTerminal(A.get(i)))
                        {
                            child.MakeSiblings(new TerminalNode(null));
                        }
                        else
                        {
                            child.MakeSiblings(new NonTerminalNode(A.get(i)));
                        }
                    }
                }
                if (A.size() != 0)
                {
                    currentNode.AdoptChildren(child);
                }
                currentNode.visited = true;
                currentNode = setCurrentNode(currentNode);
            }
            System.out.println("\n\n");
        }
        return new AST(rootNode);

    }

    boolean isTerminal(String a)
    {
        return Character.isLowerCase(a.charAt(0));
    }

    boolean match(Token a, String b) throws Exception
    {
        if (GetName(a).equals(b.toLowerCase()))
        {
            return true;
        }
        else
        {
            String message = "Expected " + b + " Token at line " + a.line + " But found a " + GetName(a) + " token";
            if (a instanceof idToken){
                message += " " + ((idToken) a).spelling;
            }
            throw new Exception(message);
        }
    }

    public static String GetName(Token t)
    {
        return t.getClass().getSimpleName().replaceAll("Token","").toLowerCase();
    }

    Node setCurrentNode(Node current)
    {
        while (current.visited)
        {
            if (current.leftMostChild != null && !current.leftMostChild.visited)
            {
                return current.leftMostChild;
            }
            else if (current.rightSib != null && !current.rightSib.visited)
            {
                return current.rightSib;
            }
            else if (current.parent != null)
            {
                current = current.parent;
            }
        }
        //System.out.println("No more");
        return null;
    }
}
