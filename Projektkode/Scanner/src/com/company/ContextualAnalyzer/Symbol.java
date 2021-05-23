package com.company.ContextualAnalyzer;

public class Symbol
{
    String _id;
    String _type;

    public String Id()
    {
        return _id;
    }
    public String Type()
    {
        return _type;
    }

    public Symbol(String aId, String aType)
    {
        this._id = aId;
        this._type = aType;
    }

    public String ToString()
    {
        return _id + ", " + _type;
    }
}
