package com.company.CodeGenerator.TemplateCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckClass {
    List<CardClass> cards = new ArrayList<CardClass>();

    public DeckClass(String[] aCards) throws Exception {
        GenerateDeck(aCards);
    }
    public DeckClass() throws Exception {
    }
    public DeckClass(String cards) throws Exception {
        String[] a = {cards};
        GenerateDeck(a);
    }
    public int size()
    {
        return cards.size();
    }

    public int totalValue()
    {
        int i = 0;
        for (CardClass c: cards)
        {
            i += c.Value();
        }
        return i;
    }
    public CardClass get(int number)
    {
        return cards.get(number);
    }

    void GenerateDeck(String[] Cards) throws Exception
    {
        cards = new ArrayList<CardClass>();
        for (String s : Cards) {
            switch (s.toLowerCase()) {
                case "standard":
                    cards.addAll(HelpMethods.CreateSuitDeck("h"));
                    cards.addAll(HelpMethods.CreateSuitDeck("c"));
                    cards.addAll(HelpMethods.CreateSuitDeck("d"));
                    cards.addAll(HelpMethods.CreateSuitDeck("s"));
                    break;
                case "hearts":
                    cards.addAll(HelpMethods.CreateSuitDeck("h"));
                    break;
                case "clubs":
                    cards.addAll(HelpMethods.CreateSuitDeck("c"));
                    break;
                case "diamonds":
                    cards.addAll(HelpMethods.CreateSuitDeck("d"));
                    break;
                case "spades":
                    cards.addAll(HelpMethods.CreateSuitDeck("s"));
                    break;
                default:
                    cards.add(HelpMethods.CreateCard(s));
                    break;
            }
        }
    }

    public void drawfrom(DeckClass deck, int cnt)
    {
        for (int i = 0; i < cnt; i++)
        {
            if (deck.size() > 0)
            {
                CardClass card = deck.cards.get(0);
                deck.cards.remove(0);
                this.cards.add(card);
            }
        }
    }

    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    @Override
    public String toString() {
        return "DeckClass{" +
                "cards=" + cards +
                '}';
    }
}
