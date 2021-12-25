import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenAddressingHashTableTest
{

    @Test
    void testBasicFunctionality()
    {
        int size = 16;
        OpenAddressingHashTable<Integer, Integer> table = new OpenAddressingHashTable<>(size);
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        
        for (int i = 0; i < size; i++)
        {
            table.put(i, i + 1);
            assertEquals(i + 1, table.size());
        }
        
        for (int i = 0; i < size; i++)
        {
            assertEquals(i + 1, table.get(i));
        }
        assertFalse(table.isEmpty());
        
        for (int i = 0; i < size; i++)
        {
            Integer x = table.remove(i);
            assertEquals(i + 1, x);
            assertEquals(size - i - 1, table.size());
        }
        assertTrue(table.isEmpty());
    }
    
    @Test
    void test()
    {
        int size = 15;
        OpenAddressingHashTable<String, Integer> table = new OpenAddressingHashTable<>(size);
        
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        
        assertFalse(OpenAddressingHashTable.isPower(size));
        assertTrue(OpenAddressingHashTable.isPower(table.getLength()));
        
        size = table.getLength();
        
        assertNotEquals("Aa", "BB");
        assertEquals("Aa".hashCode(), "BB".hashCode());
        
        table.put("Aa", 1);
        table.put("BB", 2);
        
        assertEquals(table.hash("Aa"), table.hash("BB"));
        assertEquals(2, table.size());
    
        int indexAa = table.getIndex("Aa") , indexBB = table.getIndex("BB");

        assertEquals((indexAa + 1) % table.getLength(), indexBB);
        
    }
}