package com.company.ContextualAnalyzer;

import com.company.AST.Node;
import com.company.AST.NonTerminalNode;
import com.company.AST.TerminalNode;
import com.company.Parser;
import com.company.ShufflerSymbols.ShufflerSymbols;
import com.company.Tokens.idToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VisitorFunctionDCLs extends Visitor
{
    int state = 0;
    String ReturnType;

    public VisitorFunctionDCLs(ScopeTable aTable, Node aAstTree, ShufflerSymbols aShufflerSymbols) {
        super(aTable, aAstTree, aShufflerSymbols);
    }


    @Override
    public void Visit(Node node) throws Exception
    {

        if (CheckNonTerminalType(node, "FunctionsDef"))
        {
            try
            {
                String returnType = Parser.GetName(((TerminalNode) node.leftMostChild.leftMostChild).terminal);
                String id = ((idToken) ((TerminalNode) node.leftMostChild.rightSib).terminal).spelling;
                List<String> parameters = GetParameterTypes(node.leftMostChild.rightSib.rightSib.rightSib);
                Collections.reverse(parameters);

                Symbol symbol = new FunctionSymbol(id, returnType + "func", returnType, parameters);
                EnterSymbolToCurrentScope(symbol);

            } catch (Exception e)
            {
                throw new Exception(e.getMessage());
            }
        }
    }

    List<String> GetParameterTypes(Node node)
    {
        List<String> list = new ArrayList<String>();
        node = node.leftMostChild;
        if (node != null) {
            if (((NonTerminalNode) node).nonterminal.equals("Dcl")) {
                switch (((NonTerminalNode) node.leftMostChild).nonterminal) {
                    case "DeckDcl":
                        list.add("deck");
                        break;
                    case "NumberDcl":
                        list.add("number");
                        break;
                    case "CardDcl":
                        list.add("card");
                        break;
                    case "HandDcl":
                        list.add("hand");
                        break;
                    case "EnumDcl":
                        list.add("enum");
                        break;
                    case "StringDcl":
                        list.add("string");
                        break;
                    case "FlagDcl":
                        list.add("flag");
                        break;
                }
                list.addAll(GetParameterTypes(node.rightSib.rightSib));
            }
        }
        return list;
    }
}