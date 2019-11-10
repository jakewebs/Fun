import java.util.Random;
import java.util.ArrayList;

public class Deck 
{
    private ArrayList<Card> cards;

    public Deck()
    {
        cards = new ArrayList<Card>();

        for(int i =0; i<4; i++)
        {
            for(int j=0; j<13;j++)
            {
                cards.add(new Card(i,j));
            }
        }  
    }

    public String toString()
    {
        String str = "";
        for(int i = 0; i<52;i++)
        {
            str += cards.get(i).toString() + "\n";
        }
        return str;
    }

    //Mix the cards in a random order
    public void scramble()
    {
        for(int i = 0; i<52; i++)
        {
            int num = (int)(Math.random() * 51 + 1);
            Card c = cards.get(num);
            Card d = cards.get(i);
            Card temp = c;
            cards.set(num,d);
            cards.set(i,temp);
        }

    }
    public Card get(int num)
    {
        return cards.get(num);
    }
}
