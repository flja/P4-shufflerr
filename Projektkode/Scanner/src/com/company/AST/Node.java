package com.company.AST;

import com.company.Tokens.Token;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    public Node parent = null;
    public Node leftMostChild = null;
    public Node rightSib = null;
    public Node leftMostSib = this;
    public boolean visited = false;
    public String type = null;

    public Node MakeSiblings(Node node)
    {
        Node xsibs = this;
        while (xsibs.rightSib != null)
        {
            xsibs = xsibs.rightSib;
        }
        Node ysibs = node.leftMostSib;
        xsibs.rightSib = ysibs;
        ysibs.leftMostSib = xsibs.leftMostSib;
        ysibs.parent = xsibs.parent;
        while (ysibs.rightSib != null)
        {
            ysibs = ysibs.rightSib;
            ysibs.leftMostSib = xsibs.leftMostSib;
            ysibs.parent = xsibs.parent;
        }
        return ysibs;
    }

    public void AdoptChildren(Node node)
    {
        if (this.leftMostChild != null)
        {
            this.leftMostChild.MakeSiblings(node);
        }
        else
        {
            Node ysibs = node.leftMostSib;
            this.leftMostChild = ysibs;
            while (ysibs != null)
            {
                ysibs.parent = this;
                ysibs = ysibs.rightSib;
            }
        }
    }

    public List<Node> GetChildren()
    {
        List<Node> children = new ArrayList<Node>();
        Node child = this.leftMostChild;
        if (child != null)
        {
            children.add(child);
            while(child.rightSib != null)
            {
                child = child.rightSib;
                children.add(child);
            }
        }
        return children;
    }
    public void VisitSuptree()
    {
        RecursiveVisitSubtree(this);
    }
    public void RecursiveVisitSubtree(Node node)
    {
        node.visited = true;
        for (Node child : node.GetChildren())
        {
            RecursiveVisitSubtree(child);
        }
    }

}
