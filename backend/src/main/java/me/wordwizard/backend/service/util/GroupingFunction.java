package me.wordwizard.backend.service.util;

import java.util.function.Function;

public class GroupingFunction<T> implements Function<T, Integer> {
    private int chunkSize;
    private int counter;

    public GroupingFunction(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Override
    public Integer apply(T t) {
        return counter++ / chunkSize;
    }
}
