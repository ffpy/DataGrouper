package org.datagrouper.datagrouper.core;

import java.util.Comparator;
import java.util.Objects;

/**
 * 排序方式
 */
public enum ComparatorType {
    /** 先执行自定义排序，再执行默认排序 */
    BEFORE(new Action() {
        @Override
        public <E> Comparator<E> apply(Comparator<E> defaultComparator, Comparator<E> memberComparator) {
            return (o1, o2) -> {
                int result = memberComparator.compare(o1, o2);
                if (result != 0) {
                    return result;
                }
                return defaultComparator.compare(o1, o2);
            };
        }
    }),

    /** 先执行默认排序，再执行自定义排序 */
    AFTER(new Action() {
        @Override
        public <E> Comparator<E> apply(Comparator<E> defaultComparator, Comparator<E> memberComparator) {
            return (o1, o2) -> {
                int result = defaultComparator.compare(o1, o2);
                if (result != 0) {
                    return result;
                }
                return memberComparator.compare(o1, o2);
            };
        }
    }),

    /** 只执行自定义排序 */
    ONLY(new Action() {
        @Override
        public <E> Comparator<E> apply(Comparator<E> defaultComparator, Comparator<E> memberComparator) {
            return memberComparator;
        }
    }),

    ;

    /** 动作实现 */
    private final Action action;

    ComparatorType(Action action) {
        this.action = Objects.requireNonNull(action);
    }

    public <E> Comparator<E> apply(Comparator<E> defaultComparator, Comparator<E> memberComparator) {
        return this.action.apply(defaultComparator, memberComparator);
    }

    @FunctionalInterface
    private interface Action {

        <E> Comparator<E> apply(Comparator<E> defaultComparator, Comparator<E> memberComparator);
    }
}
