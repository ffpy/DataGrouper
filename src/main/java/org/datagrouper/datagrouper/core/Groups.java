package org.datagrouper.datagrouper.core;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class Groups<K, E, G extends Group<K, E>> {
    private List<E> data;
    private Function<E, G> newGroup;
    private Function<E, K> alloc;
    private Supplier<Comparator<E>> defaultComparator = () -> null;
    private Supplier<Map<K, MemberComparator<E>>> memberComparator = () -> null;
    private Supplier<Comparator<G>> groupComparator = () -> null;

    public static <K, E, G extends Group<K, E>> Groups<K, E, G> of(List<E> data, Class<K> keyType, Class<G> groupType) {
        return new Groups<K, E, G>(data);
    }

    private Groups(List<E> data) {
        this.data = data;
    }

    public Groups<K, E, G> newGroup(Function<E, G> newGroup) {
        this.newGroup = Objects.requireNonNull(newGroup);
        return this;
    }

    public Groups<K, E, G> alloc(Function<E, K> alloc) {
        this.alloc = Objects.requireNonNull(alloc);
        return this;
    }

    public Groups<K, E, G> defaultComparator(Supplier<Comparator<E>> defaultComparator) {
        this.defaultComparator = Objects.requireNonNull(defaultComparator);
        return this;
    }

    public Groups<K, E, G> memberComparator(Supplier<Map<K, MemberComparator<E>>> memberComparator) {
        this.memberComparator = Objects.requireNonNull(memberComparator);
        return this;
    }

    public Groups<K, E, G> groupComparator(Supplier<Comparator<G>> groupComparator) {
        this.groupComparator = Objects.requireNonNull(groupComparator);
        return this;
    }

    public List<G> group() {
        Objects.requireNonNull(newGroup);
        Objects.requireNonNull(alloc);
        return new DataGrouper<>(data, new Grouper<K, E, G>() {

            @Override
            public G newGroup(E e) {
                return newGroup.apply(e);
            }

            @Nullable
            @Override
            public K alloc(E e) {
                return alloc.apply(e);
            }

            @Nullable
            @Override
            public Comparator<E> defaultComparator() {
                return defaultComparator.get();
            }

            @Nullable
            @Override
            public Map<K, MemberComparator<E>> memberComparator() {
                return memberComparator.get();
            }

            @Nullable
            @Override
            public Comparator<G> groupComparator() {
                return groupComparator.get();
            }
        }).groups();
    }
}
