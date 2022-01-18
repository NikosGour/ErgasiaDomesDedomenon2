import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main
{
    public static void main(String[] args)
    {
        OpenAddressingHashTable<String, Integer> hashTable = new OpenAddressingHashTable<>();
        
        try (Scanner scanner = new Scanner(System.in))
        {
            while (scanner.hasNext())
            {
                String line = scanner.nextLine();
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens())
                {
                    String word = st.nextToken();
                    Integer curFreq;
                    try
                    {
                        curFreq = hashTable.get(word);
                    } catch (NoSuchElementException e)
                    {
                        curFreq = null;
                    }
                    if (curFreq == null)
                    {
                        curFreq = 1;
                    } else
                    {
                        curFreq++;
                    }
                    hashTable.put(word , curFreq);
                }
            }
        }
        
        for (Dictionary.Entry<String, Integer> e : hashTable)
        {
            System.out.println("Word " + e.getKey() + " appeared " + e.getValue() + " times");
        }
        
    }
    
    
}
