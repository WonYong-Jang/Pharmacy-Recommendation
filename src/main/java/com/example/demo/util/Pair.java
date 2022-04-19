package com.example.demo.util;

import lombok.Getter;

@Getter
public class Pair<T, S> {
    T first;
    S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }
}
