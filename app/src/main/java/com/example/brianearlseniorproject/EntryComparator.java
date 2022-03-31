package com.example.brianearlseniorproject;

import com.github.mikephil.charting.data.Entry;

import java.util.Comparator;
import java.util.Map;

public class EntryComparator implements Comparator<Entry> {
    @Override
    public int compare(Entry entry, Entry t1) {
        return Float.compare(entry.getX(), t1.getX());
    }
}
