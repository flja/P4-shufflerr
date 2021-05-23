package com.company.CodeGenerator.TemplateCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpMethods {
    public static CardClass CreateCard(String card) throws Exception
    {
        List<String> list = new ArrayList<String>();
        int value = 0;
        if(card.toLowerCase().equals("joker"))
        {
            return new CardClass("joker", "", 10);
        }
        else if (card.length() == 2)
        {
            list.add(Character.toString(card.charAt(0)));
            list.add(Character.toString(card.charAt(1)));
        }
        else if(card.length() == 3)
        {
            String s = card.substring(0,2);
            list.add(s);
            list.add(Character.toString(card.charAt(2)));
        }
        else
        {
            throw new Exception("Error in create card");
        }
        switch (list.get(0))
        {
            case "A":
            case "a":
                value = 14;
                break;
            case "K":
            case "k":
                value = 13;
                break;
            case "Q":
            case "q":
                value = 12;
                break;
            case "J":
            case "j":
                value = 11;
                break;
            default:
                value = Integer.parseInt(list.get(0));
                break;
        }
        return new CardClass(list.get(0).toString(), list.get(1).toString(), value);
    }

    public static List<CardClass> CreateSuitDeck(String suit) throws Exception
    {
        List<CardClass> list = new ArrayList<CardClass>();
        String[] facevalue = {"a","2","3","4","5","6","7","8","9","10","j","q","k"};
        for (String s : facevalue) {
            list.add(CreateCard(s + suit));
        }
        return list;
    }
    public static void printDeck(DeckClass deck)
    {
        for (CardClass card : deck.cards)
        {
            printCard(card);
            System.out.print(" ");
        }
    }
    public static void printCard(CardClass card)
    {
        System.out.print(card);
    }
    public static void printString(String string)
    {
        System.out.print(string);
    }
    public static void printNumber(int i)
    {
        System.out.print(i);
    }
    public static void printFlag(Boolean bool)
    {
        System.out.print(bool);
    }
}
