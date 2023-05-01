package com.long_map;

public class LongHashNode<V> {

    private long key;
    private V value;

    private LongHashNode<V> next;

    public LongHashNode(long key, V value) {
        this.key = key;
        this.value = value;
    }

    public long getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public LongHashNode<V> getNext() {
        return next;
    }

    public void setNext(LongHashNode<V> next) {
        this.next = next;
    }
}
