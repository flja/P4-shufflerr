package com.company.Tokens;

public class cardValueToken extends Token
{
    public char facevalue;
    public char suit;
    public cardValueToken(int line, char afacevalue, char asuit){
        super(line);
        facevalue = afacevalue;
        suit = asuit;
    }
}
