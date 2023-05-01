package com.long_map;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LongMapImpl<V> implements LongMap<V> {

    private static final float LOAD_FACTOR = 0.75f;
    private static final int INITIAL_NUM_OF_BUCKETS = 10;
    private static final int SIZE_MULTIPLIER = 2;

    private ArrayList<LongHashNode<V>> bucketArray;
    private int numBuckets;
    private int size;

    private List<Long> keys = new ArrayList<>();
    private List<V> values = new ArrayList<>();

    private Class<V> genericType = null;

    public LongMapImpl() {
        bucketArray = new ArrayList<>();
        numBuckets = INITIAL_NUM_OF_BUCKETS;
        size = 0;
        for (int i = 0; i < numBuckets; i++) {
            bucketArray.add(null);
        }
    }

    public LongMapImpl(Class<V> aClass) {
        this();
        genericType = aClass;
    }

    public V put(long key, V value) {
        final int bucketIndex = getBucketIndex(key);
        LongHashNode<V> head = bucketArray.get(bucketIndex);

        while (head != null) {
            if (head.getKey() == key) {
                V oldValue = head.getValue();
                head.setValue(value);
                values.remove(oldValue);
                values.add(value);
                return oldValue;
            }
            head = head.getNext();
        }

        size++;
        values.add(value);
        keys.add(key);
        head = bucketArray.get(bucketIndex);
        final LongHashNode<V> newNode = new LongHashNode<>(key, value);
        newNode.setNext(head);
        bucketArray.set(bucketIndex, newNode);

        if ((float) size / numBuckets >= LOAD_FACTOR) {
            final ArrayList<LongHashNode<V>> temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = SIZE_MULTIPLIER * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++) {
                bucketArray.add(null);
            }
            for (LongHashNode<V> headNode : temp) {
                while (headNode != null) {
                    put(headNode.getKey(), headNode.getValue());
                    headNode = headNode.getNext();
                }
            }
        }

        return null;
    }


    public V get(long key) {
        int bucketIndex = getBucketIndex(key);
        LongHashNode<V> head = bucketArray.get(bucketIndex);

        while (head != null) {
            if (head.getKey() == key) {
                return head.getValue();
            }
            head = head.getNext();
        }

        return null;
    }


    public V remove(long key) {
        final int bucketIndex = getBucketIndex(key);
        LongHashNode<V> head = bucketArray.get(bucketIndex);

        LongHashNode<V> prev = null;
        while (head != null) {
            if (head.getKey() == key) {
                break;
            }
            prev = head;
            head = head.getNext();
        }

        if (head == null) {
            return null;
        }

        if (prev != null) {
            prev.setNext(head.getNext());
        } else {
            bucketArray.set(bucketIndex, head.getNext());
        }

        keys.remove(key);
        values.remove(head.getValue());
        size--;
        return head.getValue();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(long key) {
        final int bucketIndex = getBucketIndex(key);
        LongHashNode<V> head = bucketArray.get(bucketIndex);
        while (head != null) {
            if (head.getKey() == key) {
                return true;
            }
            head = head.getNext();
        }
        return false;
    }

    public boolean containsValue(V value) {
        for (LongHashNode<V> headNode : bucketArray) {
            while (headNode != null) {
                if (Objects.equals(headNode.getValue(), value)) {
                    return true;
                }
                headNode = headNode.getNext();
            }
        }
        return false;
    }

    public long[] keys() {
        return Arrays.stream(keys.toArray(new Long[0]))
                .mapToLong(Long::longValue)
                .toArray();
    }

    public V[] values() {
        if (genericType == null) {
            throw new IllegalStateException("Generic type argument not passed");
        }
        V[] tmp = (V[]) Array.newInstance(genericType, values.size());
        return values.toArray(tmp);
    }

    public long size() {
        return size;
    }

    public void clear() {
        size = 0;
        numBuckets = INITIAL_NUM_OF_BUCKETS;
        keys = new ArrayList<>();
        values = new ArrayList<>();
        bucketArray.clear();
    }

    private int getBucketIndex(long key) {
        int hashCode = Long.hashCode(key);
        return hashCode % numBuckets;
    }
}
