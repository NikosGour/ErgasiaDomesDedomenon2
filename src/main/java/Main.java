public class Main
{
    public static void main(String[] args)
    {
        OpenAddressingHashTable<String, Integer> hashTable = new OpenAddressingHashTable<>(8);
    
        hashTable.put("a", 1);
        hashTable.put("b", 2);
        hashTable.put("c", 3);
        hashTable.put("d", 4);
        hashTable.put("e", 5);
        for (var entry : hashTable)
        {
            System.out.println(entry);
        }
    }
    
}
