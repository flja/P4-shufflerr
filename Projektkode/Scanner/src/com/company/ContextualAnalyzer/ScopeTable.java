package com.company.ContextualAnalyzer;

import java.util.*;

public class ScopeTable
{
    public boolean Visited = false;
    ScopeTable previous = null;
    public HashMap<String, Symbol> table = new HashMap<String,Symbol>();
    public List<ScopeTable> subScopes = new ArrayList<ScopeTable>();
}
