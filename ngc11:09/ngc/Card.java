public class Card 
{
    private String type, value;
    private String[] cardType = {"Clubs", "Spades", "Diamonds", "Hearts"};
    private String[] cardValue = {"Ace", "King", "Queen", "Jack", "10",
                                   "9", "8", "7", "6", "5", "4", "3", "2"};

    public Card(int types, int values)
    {
        type = cardType[types]; 
        value = cardValue[values];
    }

    public String toString()
    {
        return type + " " + value;
    }
    public String getType(){
        return type;
    }
    public String getValue(){
        return value;
    }

}