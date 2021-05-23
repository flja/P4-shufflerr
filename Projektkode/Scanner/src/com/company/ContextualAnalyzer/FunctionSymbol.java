package com.company.ContextualAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class FunctionSymbol extends Symbol
{
    String _returnType;
    List<String> _parameters = new ArrayList<String>();
    public FunctionSymbol(String aId, String aType, String aReturnType, List<String> aParameters)
    {
        super(aId, aType);
        String s = "";
        for (String item: aParameters)
        {
            s += item;
            s += "; ";
        }
        if (s.lastIndexOf(";") != -1)
        {
            s = s.substring(0,s.lastIndexOf(" "));
        }
        _type = aReturnType + " func(" + s +")";
        _returnType = aReturnType;
        _parameters = aParameters;
    }
}
