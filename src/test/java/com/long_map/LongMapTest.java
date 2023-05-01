package com.long_map;

import org.junit.Assert;
import org.junit.Test;

public class LongMapTest {

    @Test
    public void testPut() {
        LongMap<Integer> longMap = new LongMapImpl<>();
        Integer val0 = 0;
        Integer val1 = 1;

        longMap.put(0L, val0);
        longMap.put(1L, val1);
        longMap.put(2L, null);

        Assert.assertEquals(val0, longMap.get(0L));
        Assert.assertEquals(val1, longMap.get(1L));
        Assert.assertNull(longMap.get(2L));
    }

    @Test
    public void testPutReplace() {
        LongMap<Integer> longMap = new LongMapImpl<>();
        Integer val0 = 0;
        Integer val1 = 1;

        longMap.put(0L, val0);
        Integer replacedValue = longMap.put(0L, val1);

        Assert.assertFalse(longMap.containsValue(val0));
        Assert.assertEquals(val0, replacedValue);
    }


    @Test
    public void testGet() {
        LongMap<Integer> longMap = new LongMapImpl<>();
        Integer val = 1;
        longMap.put(0L, val);

        Integer actual0 = longMap.get(0L);
        Integer actual1 = longMap.get(1L);

        Assert.assertEquals(val, actual0);
        Assert.assertNull(actual1);
    }

    @Test
    public void testRemove() {
        LongMap<Integer> longMap = new LongMapImpl<>();
        Integer val = 1;
        longMap.put(0L, val);

        Integer actual1 = longMap.remove(0L);
        Assert.assertEquals(val, actual1);
    }

    @Test
    public void testRemoveNotExisting() {
        LongMap<Integer> longMap = new LongMapImpl<>();

        Integer actual = longMap.remove(0L);

        Assert.assertNull(actual);
    }

    @Test
    public void testSize() {
        LongMap<Integer> longMap = new LongMapImpl<>();

        Assert.assertEquals(0, longMap.size());

        Integer value0 = 1;
        longMap.put(0L, value0);
        Integer value1 = 1;
        longMap.put(1L, value1);
        Integer value2 = 1;
        longMap.put(2L, value2);

        Assert.assertEquals(3, longMap.size());
    }

    @Test
    public void testIsEmpty() {
        LongMap<Integer> longMap = new LongMapImpl<>();

        Assert.assertTrue(longMap.isEmpty());

        Integer value = 1;
        longMap.put(0L, value);

        Assert.assertFalse(longMap.isEmpty());
    }

    @Test
    public void testGetKeys() {
        LongMap<Integer> longMap = new LongMapImpl<>();
        long[] keysEmpty = longMap.keys();

        long[] keysEmptyExpected = new long[0];
        Assert.assertArrayEquals(keysEmptyExpected, keysEmpty);

        Integer value = 0;
        longMap.put(0L, value);
        long[] keysNonEmpty = longMap.keys();

        long[] keysNonEmptyExpected = {0L};
        Assert.assertArrayEquals(keysNonEmptyExpected, keysNonEmpty);
    }

    @Test
    public void testGetValues() {
        LongMap<Integer> longMap = new LongMapImpl<>(Integer.class);
        Integer[] valuesEmpty = longMap.values();

        Assert.assertEquals(0, valuesEmpty.length);

        Integer value = 1;
        longMap.put(0L, value);
        Integer[] valuesNotEmpty = longMap.values();

        Integer[] realValues = {value};
        Assert.assertArrayEquals(realValues, valuesNotEmpty);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetValuesIllegalState() {
        LongMap<Integer> longMap = new LongMapImpl<>();
        longMap.values();
    }


    @Test
    public void testContainsKey() {
        LongMap<Integer> longMap = new LongMapImpl<>();

        Assert.assertFalse(longMap.containsKey(1L));

        Integer value = 1;
        longMap.put(1L, value);

        Assert.assertTrue(longMap.containsKey(1L));
    }

    @Test
    public void testContainsValue() {
        LongMap<Integer> longMap = new LongMapImpl<>();
        Integer value = 1;

        Assert.assertFalse(longMap.containsValue(value));

        longMap.put(1L, value);

        Assert.assertTrue(longMap.containsValue(value));
    }

    @Test
    public void testClear() {
        LongMap<Integer> longMap = new LongMapImpl<>();
        Integer value = 1;
        longMap.put(1L, value);

        longMap.clear();

        Assert.assertTrue(longMap.isEmpty());
    }
}
