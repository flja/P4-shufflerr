package com.company;

import com.company.Tokens.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Scanner1
{
    int lineNum = 0;
    List<Token> tokens = new ArrayList<Token>();

    public Scanner1()
    {
    }

    List<Token> Lexer() throws Exception
    {
        int current;
        String line;
        Token temp;
        Token temp2;
        String word = "";
        int stringcnt = 0;
        FileReader fr = new FileReader(Paths.get(".").toAbsolutePath().normalize().toString() + "/Test.txt");
        BufferedReader br = new BufferedReader(fr);
        try
        {
            while ((line = br.readLine()) != null)
            {
                lineNum++;
                for (current = 0; current < line.length(); current++)
                {
                    temp = ScanSingleChar(line.charAt(current));
                    if (temp != null && stringcnt == 0)
                    {   //If a single char token is found
                        //word handling:
                        if (word.length() > 0)
                        { //Check if we have created a word

                                temp2 = HandleKeywords(word); //if we have crafted a word check if it's a keyword
                                if (temp2 == null)
                                { // if it's not a keyword
                                    temp2 = HandleValue(word); // then check if its a value
                                    if (temp2 == null)
                                    {  // if it's not a value
                                        temp2 = HandleId(word); // then check if it's an id
                                        if (temp2 == null)
                                        { // if it's not an id
                                            throw new Exception("Invalid" + lineNum); // then throw error
                                        }
                                    }
                                }
                                AddToken(temp2); // if no error is thrown add the token to the token list
                                word = ""; // reset word
                        }
                        if (!(temp instanceof WhitespaceToken))
                        { // if the single char found is not whitespace
                            if (current < line.length() - 1)
                            { //if we are not at the end of the file
                                temp2 = CheckCombined(line.charAt(current), line.charAt(current + 1), temp); // check for a combined token
                                current = temp == temp2 ? current : current + 1; //if a combined token is found count the current up one else just keep current
                                AddToken(temp2); //add the token to the token list
                            }
                            else
                            {
                                AddToken(temp); //if we are at end of file then just add the token.
                            }
                        }

                    }
                    else
                    {
                        if (stringcnt > 0)
                        {
                            word += line.charAt(current);
                        }
                        else
                        {
                            word += IgnoreControlChar(line.charAt(current));
                        }
                        if (IgnoreControlChar(line.charAt(current)).equals("\""))
                        {
                            if (stringcnt == 1)
                            {
                                stringcnt = 0;
                            }
                            else
                            {
                                stringcnt++;
                            }
                        }
                    }
                }
                if (!word.isEmpty())
                {
                    temp2 = HandleKeywords(word);
                    if (!(temp2 == null))
                    {
                        AddToken(temp2);
                        word = "";
                    }
                    else
                    {
                        throw new Exception("no end at line:" + lineNum);
                    }

                }
            }
        } catch (Exception e)
        {
            br.close();
            throw e;
        }
        br.close();
        return tokens;
    }

    private String IgnoreControlChar(char c)
    {
        switch (c)
        {
            case 0x9: //tap
            case 0x20: // space
                return "";
        }
        return Character.toString(c);
    }

    private Token ScanSingleChar(char c)
    {
        Token token = null;
        switch (c)
        {
            //single char tokens:
            case '{':
                token = new lBraceToken(lineNum);
                break;
            case '}':
                token = new rBraceToken(lineNum);
                break;
            case '(':
                token = new lParenToken(lineNum);
                break;
            case ')':
                token = new rParenToken(lineNum);
                break;
            case '+':
                token = new plusToken(lineNum);
                break;
            case '-':
                token = new hyphenToken(lineNum);
                break;
            case ',':
                token = new commaToken(lineNum);
                break;
            case '.':
                token = new dotToken(lineNum);
                break;
            case ';':
                token = new semicolonToken(lineNum);
                break;
            case '*':
                token = new starToken(lineNum);
                break;
            case '/':
                token = new slashToken(lineNum);
                break;
            case '?':
                token = new questionmarkToken(lineNum);
                break;
            case ':':
                token = new colonToken(lineNum);
                break;
            case ' ':
                token = new WhitespaceToken(lineNum);
                break;
            case '<':
                token = new lessThanToken(lineNum);
                break;
            case '>':
                token = new greaterThanToken(lineNum);
                break;
            case '=':
                token = new assignToken(lineNum);
                break;
            case '!':
                token = new notToken(lineNum);
                break;
        }
        return token;
    }

    private Token CheckCombined(char current, char next, Token defaultVal)
    {
        Token token = defaultVal;

        switch (current)
        {
            case '<':
                token = next == '=' ? new lessThanOrEqualToken(lineNum) : defaultVal;
                break;
            case '>':
                token = next == '=' ? new greaterThanOrEqualToken(lineNum) : defaultVal;
                break;
            case '=':
                token = next == '=' ? new equalToken(lineNum) : defaultVal;
                break;
            case '!':
                token = next == '=' ? new notEqualToken(lineNum) : defaultVal;
                break;
        }
        return token;
    }

    private Token HandleValue(String value)
    {

        try
        {
            int i = Integer.parseInt(value);
            if (i == 0)
            {
                return new zeroToken(lineNum);
            }
            else
            {
                return new nonZeroNumToken(lineNum, i);
            }
        } catch (Exception e)
        {
        }

        if (value.matches("^([aAjJqQkK]|[2-9]|10)[hHcCsSdD]$"))
        {
            return new cardValueToken(lineNum, value.toLowerCase().charAt(0), value.toLowerCase().charAt(1));
        }
        if (value.matches("^[\"][\\s\\S]*[\"]$"))
        {
            return new stringValueToken(lineNum, value);
        }
        return null;
    }

    private Token HandleKeywords(String word)
    {
        Token token = null;
        switch (word)
        {
            case "Cards":
                token = new cardsToken(lineNum);
                break;
            case "Player":
                token = new playerToken(lineNum);
                break;
            case "Table":
                token = new tableToken(lineNum);
                break;
            case "Define":
                token = new defineToken(lineNum);
                break;
            case "Setup":
                token = new setupToken(lineNum);
                break;
            case "Round":
                token = new roundToken(lineNum);
                break;
            case "Turn":
                token = new turnToken(lineNum);
                break;
            case "Endcondition":
                token = new endconditionToken(lineNum);
                break;
            case "deck":
                token = new deckToken(lineNum);
                break;
            case "number":
                token = new numberToken(lineNum);
                break;
            case "card":
                token = new cardToken(lineNum);
                break;
            case "hand":
                token = new handToken(lineNum);
                break;
            case "enum":
                token = new enumToken(lineNum);
                break;
            case "string":
                token = new stringToken(lineNum);
                break;
            case "flag":
                token = new flagToken(lineNum);
                break;
            case "standard":
                token = new standardToken(lineNum);
                break;
            case "hearts":
                token = new heartsToken(lineNum);
                break;
            case "spades":
                token = new spadesToken(lineNum);
                break;
            case "diamonds":
                token = new diamondsToken(lineNum);
                break;
            case "clubs":
                token = new clubsToken(lineNum);
                break;
            case "joker":
                token = new jokerToken(lineNum);
                break;
            case "TRUE":
            case "True":
            case "true":
                token = new trueToken(lineNum);
                break;
            case "FALSE":
            case "False":
            case "false":
                token = new falseToken(lineNum);
                break;
            case "actions":
                token = new actionsToken(lineNum);
                break;
            case "if":
                token = new ifToken(lineNum);
                break;
            case "else":
                token = new elseToken(lineNum);
                break;
            case "endif":
                token = new endifToken(lineNum);
                break;
            case "endelse":
                token = new endelseToken(lineNum);
                break;
            case "endactions":
                token = new endactionsToken(lineNum);
                break;
            case "assign":
                token = new assignSpecifierToken(lineNum);
                break;
            case "neg":
                token = new negToken(lineNum);
                break;
            case "void":
                token = new voidToken(lineNum);
                break;
            case "switch":
                token = new switchToken(lineNum);
                break;
            case "endswitch":
                token = new endSwitchToken(lineNum);
                break;
            case "func":
                token = new funcToken(lineNum);
                break;
            case "while":
                token = new whileToken(lineNum);
                break;
            case "endwhile":
                token = new endWhileToken(lineNum);
                break;
            case "action":
                token = new actionToken(lineNum);
                break;
            case "endaction":
                token = new endActionToken(lineNum);
                break;
            case "and":
                token = new andToken(lineNum);
                break;
            case "or":
                token = new orToken(lineNum);
                break;
            case "xor":
                token = new xorToken(lineNum);
                break;
            case "Functions":
                token = new functionsToken(lineNum);
                break;
            case "break":
                token = new breakToken(lineNum);
                break;
            case "case":
                token = new caseToken(lineNum);
                break;
            case "endcase":
                token = new endcaseToken(lineNum);
                break;
            case "default":
                token = new defaultToken(lineNum);
                break;
            case "enddefault":
                token = new enddefaultToken(lineNum);
                break;
            case "for":
                token = new forToken(lineNum);
                break;
            case "mod":
                token = new modToken(lineNum);
                break;
            case "return":
                token = new returnToken(lineNum);
                break;
        }
        return token;
    }

    private Token HandleId(String word)
    {
        return word.matches("^[a-zA-Z_][\\w]*$") ? new idToken(lineNum, word) : null;
    }

    private void AddToken(Token token)
    {
        tokens.add(token);
    }
}
