package shufflerCode;
import shufflerCode.HelpMethods;
import shufflerCode.ActionClass;
import shufflerCode.CardClass;
import shufflerCode.DeckClass;
import shufflerCode.two;
import shufflerCode.three;
import shufflerCode.four;
import shufflerCode.five;
import shufflerCode.six;
import shufflerCode.seven;
import shufflerCode.eight;
import shufflerCode.nine;
import shufflerCode.ten;
import shufflerCode.jack;
import shufflerCode.queen;
import shufflerCode.king;
import shufflerCode.joker;
import shufflerCode.ace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;public class Shuffler
{
int _playerCnt = 3;
Cards cards;
List<Player> players;
Table table;
Setup setup;
Round round;
Turn turn;
Endcondition endcondition;
public static void main(String[] args) throws Exception
    {
try
        {
            new Shuffler().ShufflerRun();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Press any key to terminate");
            System.in.read();
        }    }public Shuffler() throws Exception
 {
cards = new Cards();table = new Table();players = GeneratePlayers(_playerCnt);setup = new Setup();round = new Round();turn = new Turn();endcondition = new Endcondition(); }
public int getPlayerCount()
{
return _playerCnt;}
public List<Player> GeneratePlayers(int cnt) throws Exception
{
List<Player> list = new ArrayList<Player>();
for(int i = 0; i < cnt; i++)
{
list.add(new Player());
list.get(i).Number = i+1;
}
return list;
}
public void ShufflerRun() throws Exception
{
setup.run();
while (!endcondition.end) 
{
round.run();
}
System.out.println("Press any key to terminate program");
System.in.read();
}
DeckClass gamedeck = new DeckClass(new String[]{"2h", "2c", "2d", "2s", "3h", "3c", "3d", "3s"}) ; 
public class Cards
{
public Cards() throws Exception
{
}
}
public class Player
{
public int Number;public void takeTurn() throws Exception
{
turn.run(this);
}
boolean out = false ; 
DeckClass playerHand = new DeckClass() ; 
DeckClass trickDeck = new DeckClass() ; 
public Player() throws Exception
{
out =  false  ; 
}
}
public class Table
{
public Table() throws Exception
{
}
}
public class Setup
{
 int i ; 
public void run() throws Exception
{
i = getPlayerCount() ; 
gamedeck.shuffle() ; 
for(Player item : players)
{
item.playerHand.drawfrom(gamedeck, 7);
}
while(i >  0 )
{
checkForTricks(i) ; 
i = i - 1 ; 
}
}
}
public class Round
{
public void run() throws Exception
{
endcondition.check() ; 
for(Player item : players)
{
item.takeTurn();
}
}
}
public class Turn
{
 int playernumber ; 
 int i ; 
boolean hasAce = false ; 
boolean hasTwo = false ; 
boolean hasThree = false ; 
boolean hasFour = false ; 
boolean hasFive = false ; 
boolean hasSix = false ; 
boolean hasSeven = false ; 
boolean hasEight = false ; 
boolean hasNine = false ; 
boolean hasTen = false ; 
boolean hasJack = false ; 
boolean hasQueen = false ; 
boolean hasKing = false ; 
public void run(Player turntaker) throws Exception
{
hasAce = hasCard(ace.value, turntaker.Number) ; 
hasTwo = hasCard(two.value, turntaker.Number) ; 
hasThree = hasCard(three.value, turntaker.Number) ; 
hasFour = hasCard(four.value, turntaker.Number) ; 
hasFive = hasCard(five.value, turntaker.Number) ; 
hasSix = hasCard(six.value, turntaker.Number) ; 
hasSeven = hasCard(seven.value, turntaker.Number) ; 
hasEight = hasCard(eight.value, turntaker.Number) ; 
hasNine = hasCard(nine.value, turntaker.Number) ; 
hasTen = hasCard(ten.value, turntaker.Number) ; 
hasJack = hasCard(jack.value, turntaker.Number) ; 
hasQueen = hasCard(queen.value, turntaker.Number) ; 
hasKing = hasCard(king.value, turntaker.Number) ; 
HelpMethods.printString("Player ") ; 
HelpMethods.printNumber(turntaker.Number) ; 
HelpMethods.printString(" your hand consists of: ") ; 
HelpMethods.printDeck(turntaker.playerHand) ; 
HelpMethods.printString("\nChoose a player to ask\n") ; 
{
int _ActionCnt = 1;
ArrayList<Integer> _ActionMapping = new ArrayList<Integer>();
if(turntaker.Number != 1)
{System.out.println(_ActionCnt + ": " + "Player 1");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(turntaker.Number != 2)
{System.out.println(_ActionCnt + ": " + "Player 2");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(turntaker.Number != 3)
{System.out.println(_ActionCnt + ": " + "Player 3");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
System.out.println("Choose an action to perform: ");Scanner _ActionScanner = new Scanner(System.in);
Integer _ActionInput = _ActionScanner.nextInt();
switch (_ActionMapping.indexOf(_ActionInput))
{
case 0:
{
playernumber = 1 ; 
}
break;
case 1:
{
playernumber = 2 ; 
}
break;
case 2:
{
playernumber = 3 ; 
}
break;
}
}
HelpMethods.printString("Choose a card to ask for\n") ; 
{
int _ActionCnt = 1;
ArrayList<Integer> _ActionMapping = new ArrayList<Integer>();
if(hasAce)
{System.out.println(_ActionCnt + ": " + "aces");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasTwo)
{System.out.println(_ActionCnt + ": " + "twos");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasThree)
{System.out.println(_ActionCnt + ": " + "threes");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasFour)
{System.out.println(_ActionCnt + ": " + "fours");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasFive)
{System.out.println(_ActionCnt + ": " + "fives");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasSix)
{System.out.println(_ActionCnt + ": " + "sixes");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasSeven)
{System.out.println(_ActionCnt + ": " + "sevens");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasEight)
{System.out.println(_ActionCnt + ": " + "eights");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasNine)
{System.out.println(_ActionCnt + ": " + "nines");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasTen)
{System.out.println(_ActionCnt + ": " + "tens");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasJack)
{System.out.println(_ActionCnt + ": " + "jacks");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasQueen)
{System.out.println(_ActionCnt + ": " + "queens");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
if(hasKing)
{System.out.println(_ActionCnt + ": " + "kings");
_ActionMapping.add(_ActionCnt);
_ActionCnt++;
}else{_ActionMapping.add(-100);}
System.out.println("Choose an action to perform: ");Scanner _ActionScanner = new Scanner(System.in);
Integer _ActionInput = _ActionScanner.nextInt();
switch (_ActionMapping.indexOf(_ActionInput))
{
case 0:
{
i = ace.value ; 
askForCards(turntaker.Number, playernumber, i) ; 
}
break;
case 1:
{
askForCards(turntaker.Number, playernumber, two.value) ; 
}
break;
case 2:
{
i = three.value ; 
HelpMethods.printNumber(i) ; 
HelpMethods.printNumber(three.value) ; 
askForCards(turntaker.Number, playernumber, i) ; 
}
break;
case 3:
{
askForCards(turntaker.Number, playernumber, four.value) ; 
}
break;
case 4:
{
askForCards(turntaker.Number, playernumber, five.value) ; 
}
break;
case 5:
{
askForCards(turntaker.Number, playernumber, six.value) ; 
}
break;
case 6:
{
askForCards(turntaker.Number, playernumber, seven.value) ; 
}
break;
case 7:
{
askForCards(turntaker.Number, playernumber, eight.value) ; 
}
break;
case 8:
{
askForCards(turntaker.Number, playernumber, nine.value) ; 
}
break;
case 9:
{
askForCards(turntaker.Number, playernumber, ten.value) ; 
}
break;
case 10:
{
askForCards(turntaker.Number, playernumber, jack.value) ; 
}
break;
case 11:
{
askForCards(turntaker.Number, playernumber, queen.value) ; 
}
break;
case 12:
{
askForCards(turntaker.Number, playernumber, king.value) ; 
}
break;
}
}
checkForTricks(turntaker.Number) ; 
endcondition.check() ; 
}
}
public class Endcondition
{
Player none = new Player();
Player winner = none;
boolean end = false;
public Endcondition() throws Exception
{
}
 int player1tricks ; 
 int player2tricks ; 
 int player3tricks ; 
public void check() throws Exception
{
if(gamedeck.size() ==  0  && false  == _playeranyfunc0())
{player1tricks = players.get(1-1).trickDeck.size() ; 
player2tricks = players.get(2-1).trickDeck.size() ; 
player3tricks = players.get(3-1).trickDeck.size() ; 
if(player1tricks > player2tricks &&player1tricks > player3tricks)
{
winner = players.get(1-1) ; 
}
else
if(player2tricks > player1tricks &&player2tricks > player3tricks)
{
winner = players.get(2-1) ; 
}
else
if(player3tricks > player1tricks &&player3tricks > player2tricks)
{
winner = players.get(3-1) ; 
}
else

{
winner = none ; 
}
HelpMethods.printString("Game over\n") ; 
if(winner == none)
{
}
else
{
System.out.println(" The winner is player" + (players.indexOf(winner) + 1));
}
System.out.println("Press any key to terminate");
System.in.read();
System.exit(0);
}
}
}
void  askForCards( int to , 
 int from , 
 int askValue ) throws Exception
{
DeckClass temp = new DeckClass() ; 
 int CardsRecieved =  0  ; 
 int handsize ; 
handsize = players.get(from-1).playerHand.size() ; 
while(handsize !=  0 )
{
if(players.get(from-1).playerHand.get(handsize-1).Value() == askValue)
{
players.get(to-1).playerHand.drawfrom(players.get(from-1).playerHand, 1) ; 
CardsRecieved = CardsRecieved + 1 ; 
}
else

{
temp.drawfrom(players.get(from-1).playerHand, 1) ; 
}
handsize = players.get(from-1).playerHand.size() ; 
}
if(CardsRecieved ==  0 )
{
HelpMethods.printString("\nGo Fish!!\n") ; 
players.get(to-1).playerHand.drawfrom(gamedeck, 1) ; 
handsize = players.get(to-1).playerHand.size() ; 
HelpMethods.printString("You fished a ") ; 
HelpMethods.printCard(players.get(to-1).playerHand.get(handsize-1)) ; 
HelpMethods.printString("\n") ; 
}
else

{
HelpMethods.printString("\nPlayer ") ; 
HelpMethods.printNumber(to) ; 
HelpMethods.printString(" recieved ") ; 
HelpMethods.printNumber(CardsRecieved) ; 
HelpMethods.printString(" Cards ") ; 
HelpMethods.printString("from player ") ; 
HelpMethods.printNumber(from) ; 
HelpMethods.printString("\n") ; 
}
players.get(from-1).playerHand.drawfrom(temp, temp.size()) ; 
}
 boolean  hasCard( int cardValue , 
 int playerNum ) throws Exception
{
 int i = 1 ; 
while(i < players.get(playerNum-1).playerHand.size())
{
if(players.get(playerNum-1).playerHand.get(i-1).Value() == cardValue)
{
 return true  ; 
}
i = i + 1 ; 
}
 return  false  ; 
}
void  checkForTricks( int playerNumber ) throws Exception
{
DeckClass temp = new DeckClass() ; 
 int i ; 
i = players.get(playerNumber-1).playerHand.size() ; 
HelpMethods.printString("Checking tricks for player ") ; 
HelpMethods.printNumber(playerNumber) ; 
HelpMethods.printString("\n") ; 
while(i >  0 )
{
 int cardValue ; 
 int j ; 
 int amount =  0  ; 
j = players.get(playerNumber-1).playerHand.size() ; 
cardValue = players.get(playerNumber-1).playerHand.get(i-1).Value() ; 
while(j >  0 )
{
if(players.get(playerNumber-1).playerHand.get(j-1).Value() == cardValue)
{
amount = amount + 1 ; 
}
j = j - 1 ; 
}
if(amount >= 4)
{
j = players.get(playerNumber-1).playerHand.size() ; 
while(j >  0 )
{
if(players.get(playerNumber-1).playerHand.get(j-1).Value() == cardValue)
{
players.get(playerNumber-1).trickDeck.drawfrom(players.get(playerNumber-1).playerHand, 1) ; 
}
else

{
temp.drawfrom(players.get(playerNumber-1).playerHand, 1) ; 
}
j = j - 1 ; 
}
players.get(playerNumber-1).playerHand.drawfrom(temp, temp.size()) ; 
i = i - 3 ; 
HelpMethods.printString("Congrats new trick set of: ") ; 
HelpMethods.printNumber(cardValue) ; 
HelpMethods.printString("\n") ; 
}
i = i - 1 ; 
}
}
public boolean _playeranyfunc0()
{
for(Player p : players)
{
if (p.playerHand.size() >  0 )
{
return true;
}
}
return false;
}

}