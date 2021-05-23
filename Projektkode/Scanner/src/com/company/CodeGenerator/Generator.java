package com.company.CodeGenerator;


import com.company.AST.AST;
import com.company.AST.Node;
import com.company.AST.NonTerminalNode;
import com.company.AST.TerminalNode;
import com.company.Parser;
import com.company.Tokens.idToken;
import com.company.Tokens.nonZeroNumToken;
import com.company.Tokens.stringToken;
import com.company.Tokens.stringValueToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class Generator
{
    AST _ast;
    int _indent = 0;
    JavaGenerator javagenerator = new JavaGenerator();

    public Generator(AST aAst) {
        this._ast = aAst;
    }

    public void StartGenerator() throws Exception
    {

        _ast.ResetVisit();
        javagenerator.generateTemplate(_ast.Root.leftMostChild.rightSib.rightSib);
        RecursiveVisitor(_ast.Root);
        javagenerator.code += javagenerator.functions;
        javagenerator.code += "\n}";
        javagenerator.WriteToFile();
    }

    public void RecursiveVisitor(Node node) throws Exception
    {
        Visit(node);
        for (Node child : node.GetChildren())
        {
            if (!child.visited)
            {
            RecursiveVisitor(child);
            }
        }
    }

    void Visit(Node node) throws Exception
    {
        if (node instanceof NonTerminalNode)
        {
            switch(((NonTerminalNode) node).nonterminal)
            {
                case "Deck":
                    javagenerator.Emit(javagenerator.DeckGenerator(node));
                    break;
                case "CardsDef":
                    javagenerator.Emit(javagenerator.CardDefGenerator(node));
                    break;
                case "PlayerDef":
                    javagenerator.Emit(javagenerator.PlayerDefGenerator(node));
                    break;
                case "HandDcl":
                    javagenerator.Emit(javagenerator.HandDclGenerator(node));
                    break;
                case "DeckDcl":
                    javagenerator.Emit(javagenerator.DeckDclGenerator(node));
                    break;
                case "FlagDcl":
                    javagenerator.Emit(javagenerator.FlagDclGenerator(node));
                    break;
                case "TableDef":
                    javagenerator.Emit(javagenerator.TableDefGenerator(node));
                    break;
                case "Setup":
                    javagenerator.Emit(javagenerator.SetupGenerator(node));
                    break;
                case "Round":
                    javagenerator.Emit(javagenerator.RoundGenerator(node));
                    break;
                case "Turn":
                    javagenerator.Emit(javagenerator.TurnGenerator(node));
                    break;
                case "SelectionStmt":
                    javagenerator.Emit(javagenerator.SelectionStatementGenerator(node));
                    break;
                case "LoopStmt":
                    javagenerator.Emit(javagenerator.LoopStmtGenerator(node));
                    break;
                case "LabeledStmt":
                    javagenerator.Emit(javagenerator.LabeledStmtGenerator(node));
                    break;
                case "Endcondition":
                    javagenerator.Emit(javagenerator.EndConditionGenerator(node));
                    break;
                case "Functions":
                    javagenerator.Emit(javagenerator.FunctionsGenerator(node));
                    break;
                case "FunctionsDef":
                    javagenerator.Emit(javagenerator.FunctionDefsGenerator(node));
                    break;
                case "FollowObject1":
                    javagenerator.Emit(javagenerator.FollowObject1Generator(node));
                    break;
                case "FunctionCall1":
                    javagenerator.Emit(javagenerator.FunctionCall1Generator(node));
                    break;
                case "LogicalTerm":
                    javagenerator.Emit(javagenerator.LogicalTermGenerator(node));
                    break;
            }
        }
        else if (node instanceof TerminalNode)
        {
            switch (Parser.GetName(((TerminalNode) node).terminal))
            {
                case "actions":
                   //make
                    break;
                case "action":
                    //make
                    break;
                case "and":
                    javagenerator.Emit(" &&");
                    break;
                case "assignspecifier":
                    //ignore
                    break;
                case "assign":
                    javagenerator.Emit(" = ");
                    break;
                case "break":
                    javagenerator.Emit("break");
                    break;
                case "cards":
                    //make
                    break;
                case "card":
                    javagenerator.Emit("card ");
                    break;
                case "cardvalue":
                    //make
                    break;
                case "case":
                    javagenerator.Emit("case ");
                    break;
                case "clubs":
                    //ignore
                    break;
                case "colon":
                    javagenerator.Emit(" : ");
                    break;
                case "comma":
                    javagenerator.Emit(", ");
                    break;
                case "deck":
                    javagenerator.Emit("DeckClass ");
                    break;
                case "default":
                    javagenerator.Emit("default ");
                    break;
                case "define":
                    //ignore
                    break;
                case "diamonds":
                    //ignore
                    break;
                case "dot":
                    javagenerator.Emit(".");
                    break;
                case "else":
                    javagenerator.Emit("else ");
                    break;
                case "endactions":
                    //make
                    break;
                case "endaction":
                    //ignore
                    break;
                case "endcase":
                    //ignore
                    break;
                case "endcondition":
                    //make
                    break;
                case "enddefault":
                    //ignore
                    break;
                case "endelse":
                    //ignore
                    break;
                case "endif":
                    //ignore
                    break;
                case "endoffile":
                    //ignore
                    break;
                case "endswitch":
                    //ignore
                    break;
                case "endwhile":
                    //ignore
                    break;
                case "enum":
                    //make
                    break;
                case "equal":
                    javagenerator.Emit(" == ");
                    break;
                case "false":
                    javagenerator.Emit(" false ");
                    break;
                case "flag":
                    javagenerator.Emit(" boolean ");
                    break;
                case "for":
                    javagenerator.Emit(" for");
                    break;
                case "functions":
                    //make
                    break;
                case "func":
                    //ignore
                    break;
                case "greaterthanorequal":
                    javagenerator.Emit(" >= ");
                    break;
                case "greaterthan":
                    javagenerator.Emit(" > ");
                    break;
                case "hand":
                    javagenerator.Emit(" DeckClass ");
                    break;
                case "hearts":
                    //ignore
                    break;
                case "hyphen":
                    javagenerator.Emit(" - ");
                    break;
                case "id":
                    javagenerator.Emit(javagenerator.IdGenerator(node));
                    break;
                case "if":
                    javagenerator.Emit("if ");
                    break;
                case "joker":
                    //ignore
                    break;
                case "lbrace":
                    javagenerator.Emit("\n{\n");
                    _indent++;
                    break;
                case "lessthanorequal":
                    javagenerator.Emit(" <= ");
                    break;
                case "lessthan":
                    javagenerator.Emit(" < ");
                    break;
                case "lparen":
                    javagenerator.Emit("(");
                    break;
                case "mod":
                    javagenerator.Emit(" % ");
                    break;
                case "neg":
                    javagenerator.Emit("-");
                    break;
                case "nonzeronum":
                    javagenerator.Emit(String.valueOf(((nonZeroNumToken) ((TerminalNode) node).terminal).value));
                    break;
                case "notequal":
                    javagenerator.Emit(" != ");
                    break;
                case "not":
                    javagenerator.Emit(" ! ");
                    break;
                case "number":
                    javagenerator.Emit(" int ");
                    break;
                case "or":
                    javagenerator.Emit(" || ");
                    break;
                case "player":
                    //make
                    break;
                case "plus":
                    javagenerator.Emit(" + ");
                    break;
                case "questionmark":
                    //ignore
                    break;
                case "rbrace":
                    _indent--;
                    javagenerator.Emit("}\n");
                    break;
                case "round":
                    //make
                    break;
                case "rparen":
                    javagenerator.Emit(")");
                    break;
                case "semicolon":
                    javagenerator.Emit(" ; \n");
                    break;
                case "setup":
                    //make
                    break;
                case "slash":
                    javagenerator.Emit(" / ");
                    break;
                case "spades":
                    //ignore
                    break;
                case "standard":
                    //ignore
                    break;
                case "star":
                    javagenerator.Emit(" * ");
                    break;
                case "string":
                    javagenerator.Emit("String ");
                    break;
                case "stringvalue":
                    javagenerator.Emit(String.valueOf(((stringValueToken) ((TerminalNode) node).terminal).value));
                    break;
                case "switch":
                    javagenerator.Emit("switch");
                    break;
                case "table":
                    //make
                    break;
                case "true":
                    javagenerator.Emit("true ");
                    break;
                case "turn":
                    //make
                    break;
                case "void":
                    javagenerator.Emit("void ");
                    break;
                case "while":
                    javagenerator.Emit("while ");
                    break;
                case "xor":
                    javagenerator.Emit(" ^ ");
                    break;
                case "zero":
                    javagenerator.Emit(" 0 ");
                    break;
                case "return":
                    javagenerator.Emit(" return ");
                    break;
                default:
            }


        }
        else
        {
            throw new Exception("Fejl i generator");

        }
    }
}
