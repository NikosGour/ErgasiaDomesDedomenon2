public class Main
{
    public static void main(String[] args)
    {
        OpenAddressingHashTable<String, Integer> hashTable = new OpenAddressingHashTable<>(17);
    
    
        for (var entry : hashTable)
        {
            System.out.println(entry);
        }
    }
    
}
