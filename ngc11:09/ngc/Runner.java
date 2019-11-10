import java.util.Scanner;
import java.util.ArrayList;
public class Runner 
{
    public static void main(String[] args)
    {
        Deck deck = new Deck();
        
        //System.out.println(deck.toString());
        deck.scramble();
       
        System.out.println("Challenge 1: # of aces among the first 5 cards");
        countAces(deck);
        System.out.println();

        System.out.println("Challenge 2: # of face cards not among the first 13 in deck");
        countNonFace(deck);
        System.out.println();

        System.out.println("Challenge 3: # aces first 5 - # kings last 5");
        countAceKing(deck);
        System.out.println();

        System.out.println("Challenge 4: # cards before ace1");
        beforeAces(deck);
        System.out.println();

        System.out.println("Challenge 5: # cards between first and last ace");
        betweenAces(deck);
        System.out.println();

        System.out.println("Challenge 6: # face cards before the first ace");
        faceAce(deck);
        System.out.println();
    }
    private static void countAces(Deck deck) //runs 10,000 times, and records the # of appearances of aces among the first 5 cards
    {
        ArrayList<Integer> occ = new ArrayList<Integer>();
        for(int i = 0; i<5; i++)
        {
            int num = 0;
            occ.add(num);
        }

        for(int i = 0; i<10000;i++)
        {
            int count = 0;
            deck.scramble();
            for(int j = 0; j<5;j++)
            {
                if(deck.get(j).getValue() == "Ace")
                {
                    count++;
                }
            }
            for(int j = 0; j<5; j++)
            {
                if(j == count)
                {
                    int curVal = occ.get(j);
                    occ.set(j,curVal+1);
                }
            }
        }
        double eV = 0;
        for(int i = 0; i<5; i++)
        {
            System.out.println(i+": " + (double)occ.get(i)/100 + "%");
            eV += (i*occ.get(i));
        }
        eV = eV/10000; //Need to div by # simulations to get proper result
        System.out.println("Expected Value: " + eV);
    }
    private static void countNonFace(Deck deck) //runs 10,000 times, and records the # of face cards that do not appear among the first 13 cards
    {
        ArrayList<Integer> occ = new ArrayList<Integer>();
        for(int i = 0; i<13;i++)
        {
            int num = 0;
            occ.add(num);
        }

        for(int i = 0; i<10000;i++)
        {
            int count = 12; //starts with all outside then takes one away for each it notices inside
            deck.scramble();
            for(int j = 0; j<14;j++)
            {
                if(deck.get(j).getValue() == "King" || deck.get(j).getValue() == "Queen" || deck.get(j).getValue() == "Jack")
                {
                    count--;
                }
            }

            for(int j = 0; j<13; j++)
            {
                if(j == count)
                {
                    int curVal = occ.get(j);
                    occ.set(j, curVal+1);
                }
            }
        }
        double eV = 0;
        for(int i = 0; i<occ.size();i++)
        {
            System.out.println(i + ": " + (double)occ.get(i)/100 + "%");
            eV += (i * occ.get(i));
        }
        eV = eV/10000;
        System.out.println("Expected Value: " + eV);
    }
    private static void countAceKing(Deck deck) //runs 10,000 times, records result of # aces first five - # kings last 5
    {
        int nFour, nThree, nTwo, nOne, zero, one, two, three, four;
        nFour = nThree = nTwo = nOne = zero = one = two = three = four = 0;
        ArrayList<Integer> occ = new ArrayList<Integer>();
        for(int i = 0; i<9;i++)
        {
            int num = 0;
            occ.add(num);
        }
        for(int i = 0; i<10000;i++)
        {
            int count = 0;
            deck.scramble();
            for(int j = 0; j<5;j++)
            {
                if(deck.get(j).getValue() == "Ace")
                {
                    count++;
                }
            }
            for(int j = 51; j > 46; j--)
            {
                if(deck.get(j).getValue() == "King")
                {
                    count--;
                }
            }
            for(int j = 0; j<9;j++)
            {
                if(count == j-4)
                {
                    int curVal = occ.get(j);
                    occ.set(j,curVal+1);
                }
            }
        }
        double eV = 0;
        for(int i = 0; i<9;i++)
        {
            eV += (i-4)* occ.get(i);
            System.out.println(i-4+": " + (double)occ.get(i)/100 + "%");
        }
        eV =eV/10000;
        System.out.println("Expected Value: " + eV);
    }
    private static void beforeAces(Deck deck) //runs 10,000 times, records the number of cards before the first ace
    {
        ArrayList<Integer> occ = new ArrayList<Integer>();
        for(int i = 0; i<52; i++)
        {
            int num = 0;
            occ.add(num);
        }
        for(int i = 0; i<10000;i++)
        {
            deck.scramble();
            int count = 0;
            for(int j = 0; j<52;j++)
            {
                if(deck.get(j).getValue() == "Ace")
                {
                    int curVal = occ.get(j);
                    occ.set(j, curVal+1);
                    break;
                } 
            }
        }
        double eV = 0.0;
        for(int i = 0; i<52;i++)
        {
            System.out.println(i +": " + (double) occ.get(i)/100 + "%");
            eV += i * occ.get(i);
        }
        eV = eV/10000;
        System.out.println("Expected Value: " + eV);
    }
    private static void betweenAces(Deck deck) //runs 10,000 times, records the # of cards between first and last ace
    {
        ArrayList<Integer> occ = new ArrayList<Integer>();
        for(int i = 0; i<52; i++)
        {
            int num = 0;
            occ.add(num);
        }
        for(int i = 0; i<10000;i++)
        {
            deck.scramble();
            int count, aceCount;
            int spot1,spot2, spot3, spot4;
            count= aceCount = 0;
            spot1 = spot2 = spot3 = spot4 = -1;
            for(int j = 0; j<52;j++)
            {
                if(deck.get(j).getValue() == "Ace")
                {
                    if(spot1 == -1)
                    {
                        spot1 = j;
                    }
                    else if(spot2 == -1)
                    {
                        spot2 = j;
                    }
                    else if(spot3 == -1)
                    {
                        spot3 = j;
                    }
                    else if(spot3 != -1)
                    {
                        spot4 = j;
                        int gap = spot4 - spot1;
                        int curVal = occ.get(gap);
                        occ.set(gap, curVal+1);
                        break;
                    }
                } 
            }
        }
        double eV = 0;
        for(int i = 0; i<52;i++)
        {
            System.out.println(i +": " + (double) occ.get(i)/100 + "%");
            eV +=  i*occ.get(i);
        }
        eV = eV/10000;
        System.out.println("Expected Value: " + eV);
    }
    private static void faceAce(Deck deck) //runs 10,000 times, records the # of face cards before the first ace
    {
         ArrayList<Integer> occ = new ArrayList<Integer>();
         for(int i = 0; i<13;i++)
         {
            int num = 0;
            occ.add(num);
         }
         for(int i = 0; i<10000;i++)
         {
            deck.scramble();
            int cards = 0;
            for(int j = 0; j<52;j++)
            {
                if(deck.get(j).getValue() == "Ace")
                {
                    break;
                }
                if(deck.get(j).getValue() == "King" || deck.get(j).getValue() == "Queen" || deck.get(j).getValue() == "Jack")
                {
                    cards++;
                }
            }
            int curVal = occ.get(cards);
            occ.set(cards,curVal+1);
         }
         double eV = 0;
         for(int i = 0; i<occ.size();i++)
         {
            System.out.println(i + ": " + (double)occ.get(i)/100 + "%");
            eV += i * occ.get(i);
         }
         eV = eV/10000;
         System.out.println("Expected Value: " + eV);
    }
}