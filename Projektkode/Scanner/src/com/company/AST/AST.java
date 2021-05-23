package com.company.AST;

public class AST
{
    public Node Root;
    public AST(Node aRoot)
    {
        Root = aRoot;
    }

    public void ResetVisit()
    {
        ResetVisits(this.Root);
    }

    private void ResetVisits(Node node)
    {
        node.visited = false;
        for (Node child : node.GetChildren() )
        {
            ResetVisits(child);
        }
    }
}
