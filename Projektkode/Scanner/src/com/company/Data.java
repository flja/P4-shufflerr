package com.company;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.nio.file.Paths;
import java.nio.file.Path;

public class Data
{
    static String[] nonterminals = "Prog;DeckDcls;CardsDef;PlayerDef;TableDef;Setup;Round;Turn;Endcondition;Functions;Dcls;Dcl;DeckDcl;NumberDcl;OptNumberAssign;CardDcl;HandDcl;EnumDcl;StringDcl;OptStringAssign;FlagDcl;OptFlagAssign;Deck;Add;Cards;Card;Number;Sign;String;Enum;Enum2;Enum3;Flag;Parameters;Parameters1;Parameters2;Val;Stmts;Stmt;Assignments;Assignment;ObjectSpecifier;FollowObject;FollowObject1;CompoundStmt;SelectionStmt;LabeledStmt;OptElse;LoopStmt;Exprs;Expr;AddExpr;AddExpr1;Term;Term1;Factor;LogicalExpr;LogicalExpr1;LogicalTerm;LogicalTerm1;LogicalOperator;FunctionCall;FunctionCall1;FunctionsDefs;FunctionsDef;Type".split(";");
    static String[] terminals = "assign;semicolon;define;cards;lbrace;rbrace;player;lparen;nonzeronum;rparen;table;setup;round;turn;endcondition;functions;deck;id;number;card;hand;enum;string;flag;standard;plus;hearts;spades;diamonds;clubs;cardvalue;joker;zero;neg;stringvalue;comma;TRUE;FALSE;break;return;assignspecifier;dot;if;actions;endactions;switch;endswitch;case;colon;endcase;default;enddefault;action;endaction;else;endif;while;endwhile;for;hyphen;star;slash;mod;questionmark;or;and;xor;lessthan;greaterthan;equal;notequal;lessthanorequal;greaterthanorequal;func;void".toLowerCase().split(";");

    static List<List<Integer>> generateTable() throws Exception
    {
        List<List<Integer>> table = new ArrayList<List<Integer>>();

        int NonTerminalIndex = -1;
        File file = new File(getPath("parsetable.csv"));
        Scanner fr = new Scanner(file);
        fr.nextLine();
        while(fr.hasNextLine())
        {
            NonTerminalIndex++;
            table.add(new ArrayList<Integer>());
            String[] linearr = fr.nextLine().split(";");
            for (int i = 1; i < linearr.length; i++)
            {
                table.get(NonTerminalIndex).add(Integer.parseInt(linearr[i]));
            }
        }
        fr.close();
        return table;
    }
    static List<String> getProduction(int p) throws Exception
    {
        File file = new File(getPath("BNF.txt"));
        Scanner fr = new Scanner(file);
        for (int i = 1; i < p; i++)
        {
            fr.nextLine();
        }
        String line = fr.nextLine();
        if (line.split("⟶").length < 2)
        {
            return new ArrayList<String>();
        }
        return Arrays.asList(line.split("⟶")[1].split(" "));
    }

    static String getPath(String filename)
    {
        return FileSystems.getDefault().getPath(filename).toAbsolutePath().normalize().toString();
    }
}
