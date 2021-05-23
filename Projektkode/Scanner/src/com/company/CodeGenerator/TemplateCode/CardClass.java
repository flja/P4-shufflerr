package com.company.CodeGenerator.TemplateCode;

public class CardClass {
    String Suit = "";
    String Facevalue = "";

    public CardClass(String aFacevalue, String aSuit, int aValue) {
        Suit = aSuit;
        Facevalue = aFacevalue;
    }

    int Value()
    {
        int a = 0;
        switch (Facevalue.toLowerCase())
        {
            case "2" :
                a = two.value;
                break;
            case "3" :
                a = three.value;
                break;
            case "4" :
                a = four.value;
                break;
            case "5" :
                a = five.value;
                break;
            case "6" :
                a = six.value;
                break;
            case "7" :
                a = seven.value;
                break;
            case "8" :
                a = eight.value;
                break;
            case "9" :
                a = nine.value;
                break;
            case "10" :
                a = ten.value;
                break;
            case "j" :
                a = jack.value;
                break;
            case "q" :
                a = queen.value;
                break;
            case "k" :
                a = king.value;
                break;
            case "a" :
                a = ace.value;
                break;
            case "joker" :
                break;
        }
        return a;
    }

    @Override
    public String toString() {
        return Facevalue + Suit;
    }
}
