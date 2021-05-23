package com.company.Tokens;

public class nonZeroNumToken extends Token
{
    public int value;

    public nonZeroNumToken(int line, int avalue){
        super(line);
        value = avalue;
    }
}
