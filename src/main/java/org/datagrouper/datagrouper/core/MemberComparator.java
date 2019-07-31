package org.datagrouper.datagrouper.core;

import java.util.Comparator;
import java.util.Objects;

/**
 * 组内排序
 */
public class MemberComparator<E> {

    /** 排序方法 */
    private final Comparator<E> comparator;

    /** 排序方式 */
    private final ComparatorType type;

    /**
     * @param comparator 排序方法
     * @param type       排序方式
     */
    public MemberComparator(Comparator<E> comparator, ComparatorType type) {
        this.comparator = Objects.requireNonNull(comparator);
        this.type = Objects.requireNonNull(type);
    }

    public Comparator<E> getComparator() {
        return comparator;
    }

    public ComparatorType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MemberComparator{" +
                "comparator=" + comparator +
                ", type=" + type +
                '}';
    }
}
