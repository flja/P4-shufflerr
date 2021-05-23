package com.company.CodeGenerator;

import com.company.AST.AST;
import com.company.AST.Node;
import com.company.AST.NonTerminalNode;
import com.company.AST.TerminalNode;
import com.company.Parser;
import com.company.Tokens.idToken;
import com.company.Tokens.nonZeroNumToken;
import com.company.Tokens.stringValueToken;

import java.io.FileWriter;
import java.nio.file.Paths;

public class ValidationCodeGenerator {
    AST _ast;
    String code = "";
    int _indent = 0;

    public ValidationCodeGenerator(AST aAst) {
        this._ast = aAst;
    }

    public void StartGenerator(Node node) throws Exception
    {
        _ast.ResetVisit();
        RecursiveVisitor(_ast.Root);
    }

    void RecursiveVisitor(Node node) throws Exception
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
                case "":
                    break;
            }
        }
        else if (node instanceof TerminalNode)
        {
            switch (Parser.GetName(((TerminalNode) node).terminal))
            {
                case "actions":
                    Emit("actions ");
                    break;
                case "action":
                    Emit("action ");
                    break;
                case "and":
                    Emit("and ");
                    break;
                case "assignspecifier":
                    Emit("assign ");
                    break;
                case "assign":
                    Emit("= ");
                    break;
                case "break":
                    Emit("break");
                    break;
                case "cards":
                    Emit("cards ");
                    break;
                case "card":
                    Emit("card ");
                    break;
                case "cardvalue":
                    Emit("cardvalue ");
                    break;
                case "case":
                    Emit("case ");
                    break;
                case "clubs":
                    Emit("clubs ");
                    break;
                case "colon":
                    Emit(": ");
                    break;
                case "comma":
                    Emit(", ");
                    break;
                case "deck":
                    Emit("deck ");
                    break;
                case "default":
                    Emit("default ");
                    break;
                case "define":
                    Emit("Define ");
                    break;
                case "diamonds":
                    Emit("diamonds ");
                    break;
                case "dot":
                    Emit("."); //Mellemrum?
                    break;
                case "else":
                    Emit("else ");
                    break;
                case "endactions":
                    Emit("endactions ");
                    break;
                case "endaction":
                    Emit("endaction ");
                    break;
                case "endcase":
                    Emit("endcase ");
                    break;
                case "endcondition":
                    Emit("endcondition ");
                    break;
                case "enddefault":
                    Emit("enddefault ");
                    break;
                case "endelse":
                    Emit("endelse ");
                    break;
                case "endif":
                    Emit("endif ");
                    break;
                case "endoffile":
                    Emit("endoffile ");
                    break;
                case "endswitch":
                    Emit("endswitch ");
                    break;
                case "endwhile":
                    Emit("endwhile ");
                    break;
                case "enum":
                    Emit("enum ");
                    break;
                case "equal":
                    Emit("== ");
                    break;
                case "false":
                    Emit("false ");
                    break;
                case "flag":
                    Emit("flag ");
                    break;
                case "for":
                    Emit("for ");
                    break;
                case "functions":
                    Emit("Functions ");
                    break;
                case "func":
                    Emit("func ");
                    break;
                case "greaterthanorequal":
                    Emit(">= ");
                    break;
                case "greaterthan":
                    Emit("> ");
                    break;
                case "hand":
                    Emit("hand ");
                    break;
                case "hearts":
                    Emit("hearts ");
                    break;
                case "hyphen":
                    Emit("- ");
                    break;
                case "id":
                    Emit(String.valueOf(((idToken) ((TerminalNode) node).terminal).spelling));
                    break;
                case "if":
                    Emit("if ");
                    break;
                case "joker":
                    Emit("joker ");
                    break;
                case "lbrace":
                    Emit("\n{\n");
                    _indent++;
                    break;
                case "lessthanorequal":
                    Emit("<= ");
                    break;
                case "lessthan":
                    Emit("< ");
                    break;
                case "lparen":
                    Emit("(");
                    break;
                case "mod":
                    Emit("mod ");
                    break;
                case "neg":
                    Emit("neg ");
                    break;
                case "nonzeronum":
                    Emit(String.valueOf(((nonZeroNumToken) ((TerminalNode) node).terminal).value));
                    break;
                case "notequal":
                    Emit("!= ");
                    break;
                case "not":
                    Emit("! ");
                    break;
                case "number":
                    Emit("number ");
                    break;
                case "or":
                    Emit("or ");
                    break;
                case "player":
                    Emit("player ");
                    break;
                case "plus":
                    Emit("+ ");
                    break;
                case "questionmark":
                    Emit("? ");
                    break;
                case "rbrace":
                    _indent--;
                    Emit("}\n");
                    break;
                case "round":
                    Emit("Round ");
                    break;
                case "rparen":
                    Emit(")");
                    break;
                case "semicolon":
                    Emit("; \n");
                    break;
                case "setup":
                    Emit("Setup ");
                    break;
                case "slash":
                    Emit("/ ");
                    break;
                case "spades":
                    Emit("spades ");
                    break;
                case "standard":
                    Emit("standard");
                    break;
                case "star":
                    Emit("* ");
                    break;
                case "string":
                    Emit("string ");
                    break;
                case "stringvalue":
                    Emit(String.valueOf(((stringValueToken) ((TerminalNode) node).terminal).value));
                    break;
                case "switch":
                    Emit("switch ");
                    break;
                case "table":
                    Emit("Table ");
                    break;
                case "true":
                    Emit("true ");
                    break;
                case "turn":
                    Emit("Turn ");
                    break;
                case "void":
                    Emit("void ");
                    break;
                case "while":
                    Emit("while ");
                    break;
                case "whitespace":
                    Emit("");
                    break;
                case "xor":
                    Emit("xor ");
                    break;
                case "zero":
                    Emit("0 ");
                    break;
                default:
            }


        }
        else
        {
            throw new Exception("Fejl i generator");

        }
    }

    void Emit(String input)
    {
        String s = "";
        if (code.length() != 0)
        {
            if(code.charAt(code.length() - 1) == '\n' || input.charAt(0) == '\n')
            {
                for (int i = 0; i < _indent; i++)
                {
                    s += "   ";
                }
                input = input.charAt(0) == '\n' ? input.replaceFirst("\n", "\n" + s) : s + input;
            }
        }
        code += input;
    }

    public void WriteToFile() throws Exception {
        String path = Paths.get(".").toAbsolutePath().normalize().toString() + "/ValidationCode.txt";
        FileWriter fw = new FileWriter(path);
        fw.write(code);
        fw.flush();
        fw.close();
    }
}
