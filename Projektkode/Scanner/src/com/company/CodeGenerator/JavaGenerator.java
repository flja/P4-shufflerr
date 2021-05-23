package com.company.CodeGenerator;

import com.company.AST.AST;
import com.company.AST.Node;
import com.company.AST.NonTerminalNode;
import com.company.AST.TerminalNode;
import com.company.CodeGenerator.TemplateCode.ActionClass;
import com.company.CodeGenerator.TemplateCode.DeckClass;
import com.company.CodeGenerator.TemplateCode.HelpMethods;
import com.company.Parser;
import com.company.ShufflerSymbols.CardsClass;
import com.company.Tokens.*;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JavaGenerator {
    String path = Paths.get(".").toAbsolutePath().normalize().toString() + "\\shufflerCode\\src\\Shuffler.java";
    String code = "";
    String functions ="";
    int playeranycnt = 0;



    public JavaGenerator() {

    }

    public void generateTemplate(Node node) throws Exception{
        code = "package shufflerCode;\n" +
                "import shufflerCode.HelpMethods;\n" +
                "import shufflerCode.ActionClass;\n" +
                "import shufflerCode.CardClass;\n" +
                "import shufflerCode.DeckClass;\n" +
                "import shufflerCode.two;\n" +
                "import shufflerCode.three;\n" +
                "import shufflerCode.four;\n" +
                "import shufflerCode.five;\n" +
                "import shufflerCode.six;\n" +
                "import shufflerCode.seven;\n" +
                "import shufflerCode.eight;\n" +
                "import shufflerCode.nine;\n" +
                "import shufflerCode.ten;\n" +
                "import shufflerCode.jack;\n" +
                "import shufflerCode.queen;\n" +
                "import shufflerCode.king;\n" +
                "import shufflerCode.joker;\n" +
                "import shufflerCode.ace;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.Arrays;\n" +
                "import java.util.List;\n" +
                "import java.util.Scanner;" +
                "public class Shuffler\n{\n" +
                "int _playerCnt = " + ((nonZeroNumToken) ((TerminalNode) node.leftMostChild.rightSib.rightSib.rightSib).terminal).value + ";\n" +
                "Cards cards;\n" +
                "List<Player> players;\n" +
                "Table table;\n" +
                "Setup setup;\n" +
                "Round round;\n" +
                "Turn turn;\n" +
                "Endcondition endcondition;\n" +
                "public static void main(String[] args) throws Exception\n" +
                "    {\n" +
                "try\n" +
                "        {\n" +
                "            new Shuffler().ShufflerRun();\n" +
                "        } catch (Exception e)\n" +
                "        {\n" +
                "            System.out.println(e.getMessage());\n" +
                "            System.out.println(\"Press any key to terminate\");\n" +
                "            System.in.read();\n" +
                "        }" +
                "    }" +
                "public Shuffler() throws Exception\n" +
                " {\n" +
                "cards = new Cards();" +
            "table = new Table();" +
                "players = GeneratePlayers(_playerCnt);" +
                "setup = new Setup();" +
                "round = new Round();" +
                "turn = new Turn();" +
                "endcondition = new Endcondition();" +
                " }\n" +
                "public int getPlayerCount()\n" +
                "{\n" +
                "return _playerCnt;" +
                "}\n" +
                "public List<Player> GeneratePlayers(int cnt) throws Exception\n" +
                "{\n" +
                "List<Player> list = new ArrayList<Player>();\n" +
                "for(int i = 0; i < cnt; i++)\n" +
                "{\n" +
                "list.add(new Player());\n" +
                "list.get(i).Number = i+1;\n" +
                "}\n" +
                "return list;\n" +
                "}\n" +
                "public void ShufflerRun() throws Exception\n{\n" +
                "setup.run();\n" +
                "while (!endcondition.end) \n" +
                "{\n" +
                "round.run();\n" +
                "}\n" +
                "System.out.println(\"Press any key to terminate program\");\n" +
                "System.in.read();\n" +
                "}\n";
    }

    public void WriteToFile() throws Exception {
        FileWriter fw = new FileWriter(path);
        fw.write(code);
        fw.flush();
        fw.close();
    }

    void Emit(String input)
    {
        code += input;
    }

    public String DeckGenerator(Node node)
    {
        String delete = "new DeckClass() = ";
        if (code.length() >= delete.length())
        {
            code = code.substring(0, code.length() - delete.length());
        }
        String s = "{";
        s = DeckGeneratorRecursion(node, s);
        s = s.substring(0,s.length() - 2);
        s = s +"}";
        return "new DeckClass(new String[]" + s +")";
    }
    public String DeckGeneratorRecursion(Node node, String s)
    {
        if (node instanceof TerminalNode)
        {
            String cards = Parser.GetName(((TerminalNode) node).terminal);
            switch(cards)
            {
                case "cardvalue":
                    cardValueToken cardvalueToken = (cardValueToken) ((TerminalNode) node).terminal;
                    s += "\"" + Character.toString(cardvalueToken.facevalue) + Character.toString(cardvalueToken.suit) + "\", ";
                    break;
                case "plus":
                    break;
                default:
                    s += "\"" + cards + "\", ";
                    break;
            }
        }
        node.visited = true;
        for (Node child: node.GetChildren()) {
            s = DeckGeneratorRecursion(child, s);
        }
        return s;
    }

    public String CardDefGenerator(Node node) throws Exception {
        String CardDefBlock =  "public class Cards\n" +
                                "{\n" +
                                "public Cards() throws Exception\n" +
                                "{\n";
        Generator gen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib));
        gen.RecursiveVisitor(gen._ast.Root);
        functions += gen.javagenerator.functions;
        CardDefBlock += gen.javagenerator.code;
        CardDefBlock += "}\n}\n";
        node.VisitSuptree();
        return CardDefBlock;
    }
    public String PlayerDefGenerator(Node node) throws Exception {
        String PlayerDefBlock =  "public class Player\n" +
                "{\n" +
                "public int Number;" +
                "public void takeTurn() throws Exception\n" +
                "{\n" +
                "turn.run(this);\n" +
                "}\n";
        Generator DclGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib.rightSib.leftMostChild.rightSib)); //PlayerDef -> CompoundStmt -> Dcls
        DclGen.RecursiveVisitor(DclGen._ast.Root);
        functions += DclGen.javagenerator.functions;
        PlayerDefBlock += DclGen.javagenerator.code;
        PlayerDefBlock += "public Player() throws Exception\n" +
                "{\n";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib.rightSib.leftMostChild.rightSib.rightSib));//PlayerDef -> CompoundStmt -> Stmts
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        PlayerDefBlock += StmtGen.javagenerator.code;
        PlayerDefBlock += "}\n}\n";
        node.VisitSuptree();

        return PlayerDefBlock;
    }

    public String HandDclGenerator(Node node) throws Exception
    {
        node.VisitSuptree();
        return "DeckClass " + ((idToken) ((TerminalNode) node.leftMostChild.rightSib).terminal).spelling + " = new DeckClass()";
    }
    public String DeckDclGenerator(Node node) throws Exception
    {
        node.VisitSuptree();
        return "DeckClass " + ((idToken) ((TerminalNode) node.leftMostChild.rightSib).terminal).spelling + " = new DeckClass()";
    }

    public String TableDefGenerator(Node node) throws Exception {
        String TableDefBlock =  "public class Table\n" +
                "{\n";
        Generator DclGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.leftMostChild.rightSib)); //TableDef -> CompoundStmt -> Dcls
        DclGen.RecursiveVisitor(DclGen._ast.Root);
        functions += DclGen.javagenerator.functions;
        TableDefBlock += DclGen.javagenerator.code;
        TableDefBlock += "public Table() throws Exception\n" +
                "{\n";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.leftMostChild.rightSib.rightSib));//TableDef -> CompoundStmt -> Stmts
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        TableDefBlock += StmtGen.javagenerator.code;
        TableDefBlock += "}\n}\n";
        node.VisitSuptree();
        return TableDefBlock;
    }

    public String SetupGenerator(Node node) throws Exception
    {
        String SetupBlock =  "public class Setup\n" +
                "{\n";
        Generator DclGen = new Generator(new AST(node.leftMostChild.rightSib.leftMostChild.rightSib)); //Setup -> CompoundStmt -> Dcls
        DclGen.RecursiveVisitor(DclGen._ast.Root);
        functions += DclGen.javagenerator.functions;
        SetupBlock += DclGen.javagenerator.code;
        SetupBlock += "public void run() throws Exception\n" +
                "{\n";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.leftMostChild.rightSib.rightSib));//Setup -> CompoundStmt -> Stmts
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        SetupBlock += StmtGen.javagenerator.code;
        SetupBlock += "}\n}\n";
        node.VisitSuptree();
        return SetupBlock;
    }
    public String RoundGenerator(Node node) throws Exception
    {
        String RoundBlock =  "public class Round\n" +
                "{\n";
        Generator DclGen = new Generator(new AST(node.leftMostChild.rightSib.leftMostChild.rightSib)); //Round -> CompoundStmt -> Dcls
        DclGen.RecursiveVisitor(DclGen._ast.Root);
        functions += DclGen.javagenerator.functions;
        RoundBlock += DclGen.javagenerator.code;
        RoundBlock += "public void run() throws Exception\n" +
                "{\n";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.leftMostChild.rightSib.rightSib));//Round -> CompoundStmt -> Stmts
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        RoundBlock += StmtGen.javagenerator.code;
        RoundBlock += "}\n}\n";
        node.VisitSuptree();
        return RoundBlock;
    }
    public String TurnGenerator(Node node) throws Exception
    {
        String TurnBlock =  "public class Turn\n" +
                "{\n";
        Generator DclGen = new Generator(new AST(node.leftMostChild.rightSib.leftMostChild.rightSib)); //Turn -> CompoundStmt -> Dcls
        DclGen.RecursiveVisitor(DclGen._ast.Root);
        functions += DclGen.javagenerator.functions;
        TurnBlock += DclGen.javagenerator.code;
        TurnBlock += "public void run(Player turntaker) throws Exception\n" +
                "{\n";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.leftMostChild.rightSib.rightSib));//Turn -> CompoundStmt -> Stmts
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        TurnBlock += StmtGen.javagenerator.code;
        TurnBlock += "}\n}\n";
        node.VisitSuptree();
        return TurnBlock;
    }
    public String EndConditionGenerator(Node node) throws Exception {
        String EndconditionBlock =  "public class Endcondition\n" +
                "{\n" +
                "Player none = new Player();\n" +
                "Player winner = none;\n" +
                "boolean end = false;\n" +
                "public Endcondition() throws Exception\n" +
                "{\n" +
                "}\n";
        Generator DclGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib.leftMostChild.rightSib)); //Endcondition -> CompoundStmt -> Dcls
        DclGen.RecursiveVisitor(DclGen._ast.Root);
        functions += DclGen.javagenerator.functions;
        EndconditionBlock += DclGen.javagenerator.code;
        EndconditionBlock += "public void check() throws Exception\n" +
                "{\n" +
                "if(";
        Generator LogicGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib));//Endcondition -> LogicalExpr
        LogicGen.RecursiveVisitor(LogicGen._ast.Root);
        functions += LogicGen.javagenerator.functions;
        EndconditionBlock += LogicGen.javagenerator.code;
        EndconditionBlock += ")\n{";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib.leftMostChild.rightSib.rightSib));//EndCondition -> CompoundStmt -> Stmts
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        EndconditionBlock += StmtGen.javagenerator.code;
        EndconditionBlock +=
                "if(winner == none)\n" +
                "{\n" +
                "}\n" +
                "else\n" +
                "{\n" +
                "System.out.println(\" The winner is player\" + (players.indexOf(winner) + 1));\n" +
                "}\n" +
                        "System.out.println(\"Press any key to terminate\");\n" +
                        "System.in.read();\n" +
                        "System.exit(0);\n" +
                "}\n}\n}\n";
        node.VisitSuptree();
        return EndconditionBlock;
    }
    public String FunctionsGenerator(Node node) throws Exception {
        Generator FunctionDefsGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib));
        FunctionDefsGen.RecursiveVisitor(FunctionDefsGen._ast.Root);
        functions += FunctionDefsGen.javagenerator.functions;
        String functionDefs = FunctionDefsGen.javagenerator.code;
        node.VisitSuptree();
        return functionDefs;
    }

    public String FunctionDefsGenerator(Node node) throws Exception
    {
        Generator TypeGen = new Generator(new AST(node.leftMostChild));
        TypeGen.RecursiveVisitor(TypeGen._ast.Root);
        functions += TypeGen.javagenerator.functions;
        String s = TypeGen.javagenerator.code;
        s += " " + ((idToken) ((TerminalNode) node.leftMostChild.rightSib).terminal).spelling;
        s += "(";
        Generator DclsGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib));
        DclsGen.RecursiveVisitor(DclsGen._ast.Root);
        functions += DclsGen.javagenerator.functions;
        String temp = DclsGen.javagenerator.code;
        if (temp.length() > 1)
        {
            temp = temp.replaceAll(";", ",");
            temp = temp.substring(0, temp.lastIndexOf(","));
        }
        s += temp;
        s += ") throws Exception";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib.rightSib));
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        s += StmtGen.javagenerator.code;
        node.VisitSuptree();
        return s;
    }

    public String SelectionStatementGenerator(Node node) throws Exception
    {
        switch (Parser.GetName(((TerminalNode) node.leftMostChild).terminal))
        {
            case "if":
                return IfStatementGenerator(node);
            case "actions":
               return ActionsStatementGenerator(node);
            case "switch":
                return SwitchStatementGenerator(node);
        }
        return null;
    }
    public String IfStatementGenerator(Node node) throws Exception
    {
        String s = "if(";
        Generator logicalGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib)); //SelectionStmt -> LogicalExpr
        logicalGen.RecursiveVisitor(logicalGen._ast.Root);
        functions += logicalGen.javagenerator.functions;
        s += logicalGen.javagenerator.code;
        s += ")";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib)); //SelectionStmt -> Stmt
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        s += StmtGen.javagenerator.code;
        s += OptElseGenerator(node.leftMostChild.rightSib.rightSib.rightSib.rightSib.rightSib);//SelectionStmt -> OptElse
        node.VisitSuptree();
        return s;
    }
    public String OptElseGenerator(Node node) throws Exception {
        String s = "";
        if (((TerminalNode) node.leftMostChild).terminal instanceof elseToken)
        {
            s = "else\n";
            Generator stmtGen = new Generator(new AST(node.leftMostChild.rightSib)); //OptElse -> Stmt
            stmtGen.RecursiveVisitor(stmtGen._ast.Root);
            functions += stmtGen.javagenerator.functions;
            s += stmtGen.javagenerator.code;
        }
        return s;
    }
    public String ActionsStatementGenerator(Node node) throws Exception
    {
        List<ActionClass> actions = new ArrayList<ActionClass>();
        actions = FindActions(node.leftMostChild.rightSib, actions);
        String s = "{\nint _ActionCnt = 1;\n";
        s += "ArrayList<Integer> _ActionMapping = new ArrayList<Integer>();\n";

        for (ActionClass item: actions) {
            s += "if(" + item.logicalExpr + ")" +
                    "\n{System.out.println(_ActionCnt" + " + \": \" + " + item.name + ");\n" +
                    "_ActionMapping.add(_ActionCnt);\n" +
                    "_ActionCnt++;\n}else{_ActionMapping.add(-100);}\n";
        }
       s += "System.out.println(\"Choose an action to perform: \");" +
        "Scanner _ActionScanner = new Scanner(System.in);\n" +
        "Integer _ActionInput = _ActionScanner.nextInt();\n" +
        "switch (_ActionMapping.indexOf(_ActionInput))\n{\n";
        for(ActionClass item : actions)
        {
            s+= "case " + actions.indexOf(item) + ":" +
                    item.body +
                    "break;\n";
        }
        s += "}\n}\n";
        node.VisitSuptree();
        return s;

    }
    public List<ActionClass> FindActions(Node node, List<ActionClass> list) throws Exception {
        if (node instanceof NonTerminalNode)
        {
            if (((NonTerminalNode) node).nonterminal.equals("LabeledStmt"))
            {
                if (((TerminalNode) node.leftMostChild).terminal instanceof actionToken)
                {
                    String name = ((stringValueToken) ((TerminalNode) node.leftMostChild.rightSib.leftMostChild).terminal).value; // LabeledStmt -> String -> stringValue
                    Generator logicGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib)); // LabeledStmt -> LogicalExpr
                    logicGen.RecursiveVisitor(logicGen._ast.Root);
                    functions += logicGen.javagenerator.functions;
                    String logicalExpr = logicGen.javagenerator.code;
                    Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib.rightSib.rightSib)); // LabeledStmt -> CompoundStmt
                    StmtGen.RecursiveVisitor(StmtGen._ast.Root);
                    functions += StmtGen.javagenerator.functions;
                    String body = StmtGen.javagenerator.code;
                    list.add(new ActionClass(name, logicalExpr, body));
                    return list;
                }
            }
        }
        for (Node child: node.GetChildren()) {
            list = FindActions(child, list);
        }
        return list;
    }
    public String SwitchStatementGenerator(Node node) throws Exception
    {
        String s = "switch(";
        Generator ExprGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib)); //SelectionStmt -> Expr
        ExprGen.RecursiveVisitor(ExprGen._ast.Root);
        functions += ExprGen.javagenerator.functions;
        s += ExprGen.javagenerator.code;
        s += ")";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib)); //SelectionStmt -> CompoundStmt
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        s += StmtGen.javagenerator.code;
        node.VisitSuptree();
        return s;
    }

    public String LoopStmtGenerator(Node node) throws Exception
    {
        switch (Parser.GetName(((TerminalNode) node.leftMostChild).terminal))
        {
            case "while" :
                return WhileLoopGenerator(node);
            default:
                return "";
        }
    }

    public String WhileLoopGenerator(Node node) throws Exception
    {
        String s = "while(";
        Generator LogicGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib));
        LogicGen.RecursiveVisitor(LogicGen._ast.Root);
        functions += LogicGen.javagenerator.functions;
        s += LogicGen.javagenerator.code;
        s+= ")";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib));
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        s+= StmtGen.javagenerator.code;
        node.VisitSuptree();
        return s;
    }

    public String LabeledStmtGenerator(Node node) throws Exception {
        switch (Parser.GetName(((TerminalNode) node.leftMostChild).terminal))
        {
            case "case" :
                return CaseGenerator(node);
            case "default":
                return DefaultGenerator(node);
            default:
                return "";
        }
    }
    public String CaseGenerator(Node node) throws Exception {

        String s = "case ";
        Generator ExprGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib)); //LabeledStmt -> Expr
        ExprGen.RecursiveVisitor(ExprGen._ast.Root);
        functions += ExprGen.javagenerator.functions;
        s += ExprGen.javagenerator.code;
        s += " : ";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib.rightSib.rightSib)); //LabeledStmt -> Stmt
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        s += StmtGen.javagenerator.code;
        node.VisitSuptree();
        return s;
    }
    public String DefaultGenerator(Node node) throws Exception {
        String s = "default : ";
        Generator StmtGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib)); //LabeledStmt -> Stmt
        StmtGen.RecursiveVisitor(StmtGen._ast.Root);
        functions += StmtGen.javagenerator.functions;
        s += StmtGen.javagenerator.code;
        node.VisitSuptree();
        return s;
    }

    public String FollowObject1Generator(Node node) throws Exception
    {
        String s = "";
        String test = node.leftMostChild.type;
        if (test != null )
        {
            if (test.equals("number"))
            {
                code += "_test_";
                if (code.contains("player._test_"))
                {
                    code = code.substring(0, code.length() - 7);
                    code += "s.";
                }
                else
                {
                    code = code.substring(0, code.length() - 6);
                }
                s = "get(";
                Generator NumberGen = new Generator(new AST(node.leftMostChild));
                NumberGen.RecursiveVisitor(NumberGen._ast.Root);
                functions += NumberGen.javagenerator.functions;
                s += NumberGen.javagenerator.code + "-1";
                s += ")";
                node.VisitSuptree();
            }
        }
        return s;
    }
    public String FunctionCall1Generator(Node node) throws Exception
    {
        String s = "";
        if (((idToken) ((TerminalNode) node.leftMostChild.leftMostChild).terminal).spelling.equals("players")) // functioncall1 -> objectspecifier -> id
        {
            s = "for(Player item : players)\n" +
                    "{\n" +
                    "item";
            Generator FollowGen = new Generator(new AST(node.leftMostChild.leftMostChild.rightSib)); // functioncall1 -> objectspecifier -> Followobject
            FollowGen.RecursiveVisitor(FollowGen._ast.Root);
            functions += FollowGen.javagenerator.functions;
            s += FollowGen.javagenerator.code;
            s += "(";
            Generator ParamGen = new Generator(new AST(node.leftMostChild.rightSib.rightSib));// functioncall1 -> Parameters
            ParamGen.RecursiveVisitor(ParamGen._ast.Root);
            functions += ParamGen.javagenerator.functions;
            s+= ParamGen.javagenerator.code;
            s+= ");\n}\n";
            node.parent.parent.parent.VisitSuptree();
        }
        return s;
    }

    public String LogicalTermGenerator(Node node) throws Exception
    {
        String s = "";
        if (node.leftMostChild.leftMostChild.leftMostChild.leftMostChild instanceof NonTerminalNode)
        {
            if (((NonTerminalNode) node.leftMostChild.leftMostChild.leftMostChild.leftMostChild.leftMostChild).nonterminal.equals("ObjectSpecifier"))
            {
                Node ObjectSpecifiernode = node.leftMostChild.leftMostChild.leftMostChild.leftMostChild.leftMostChild;
                Generator ObjectGen = new Generator(new AST(node.leftMostChild.leftMostChild.leftMostChild.leftMostChild.leftMostChild));
                ObjectGen.RecursiveVisitor(ObjectGen._ast.Root);
                functions += ObjectGen.javagenerator.functions;
                String ObjectSpecifier = ObjectGen.javagenerator.code;
                String[] ids = ObjectSpecifier.split("\\.");
                if (ids.length > 1)
                {
                    if (ids[1].equals("any"))
                    {
                        return playerAnyGenerator(node, ObjectSpecifier);
                    }
                }
                ObjectSpecifiernode.VisitSuptree();
                s = ObjectSpecifier;
            }
        }
        return s;
    }
    public String playerAnyGenerator(Node node, String line) throws Exception
    {
        line = line.substring(line.indexOf(".") + 1);
        line = line.substring(line.indexOf(".") + 1);
        Generator LogicGen = new Generator(new AST(node.leftMostChild.rightSib));
        functions += LogicGen.javagenerator.functions;
        LogicGen.RecursiveVisitor(LogicGen._ast.Root);
        line += LogicGen.javagenerator.code;
        String s = "_playeranyfunc" + playeranycnt + "()";
        functions += "public boolean " + s + "\n" +
                "{\n" +
                "for(Player p : players)\n" +
                "{\n" +
                "if (p." + line + ")\n" +
                "{\n" +
                "return true;\n" +
                "}\n" +
                "}\n" +
                "return false;\n" +
                "}\n";
        node.VisitSuptree();
        return s;
    }

    public String IdGenerator(Node node)
    {
        String spelling = String.valueOf(((idToken) ((TerminalNode) node).terminal).spelling);
        switch (spelling)
        {
            case "size" :
                return "size()";
            case "totalValue" :
                return "totalValue()";
            case "value" :
                return EvaluateValue(node);
            case "printHand" :
                spelling = "printDeck";
            case "printDeck" :
            case "printCard" :
            case "printString" :
            case "printNumber" :
            case "printFlag" :
                return "HelpMethods." + spelling;
            default:
                return spelling;
        }

    }
    public String EvaluateValue(Node node)
    {
        if (node.parent.parent.leftMostSib instanceof TerminalNode)
        {
            if (((TerminalNode) node.parent.parent.leftMostSib).terminal instanceof idToken)
            {
                switch (((idToken) ((TerminalNode) node.parent.parent.leftMostSib).terminal).spelling)
                {
                    case "ace":
                    case "two":
                    case "three":
                    case "four":
                    case "five":
                    case "six":
                    case "seven":
                    case "eight":
                    case "nine":
                    case "ten":
                    case "jack":
                    case "queen":
                    case "king":
                        return "value";
                }
            }
        }
        return "Value()";
    }

    public String FlagDclGenerator(Node node) throws Exception
    {
        String s = "boolean ";
        s += ((idToken) ((TerminalNode) node.leftMostChild.rightSib).terminal).spelling;
        s += " = false";
        node.VisitSuptree();
        return s;
    }
}
