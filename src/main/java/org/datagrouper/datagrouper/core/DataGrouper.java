package org.datagrouper.datagrouper.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 数据分组器
 *
 * @param <E> 元素类型
 * @param <G> 分组类型
 */
public class DataGrouper<K, E, G extends Group<K, E>> {

    /** 数据集 */
    private List<E> data;

    /** 分组Key -> 分组 */
    private Map<K, G> groupMap;

    /** 分组Key -> 组内排序 */
    private Map<K, MemberComparator<E>> memberComparatorMap;

    /** 默认的组内排序 */
    private Comparator<E> defaultComparator;

    /** 分组器 */
    private Grouper<K, E, G> grouper;

    /**
     * @param data    数据集
     * @param grouper 分组器
     */
    public DataGrouper(List<E> data, Grouper<K, E, G> grouper) {
        this.data = data;
        this.grouper = Objects.requireNonNull(grouper);
    }

    /**
     * 执行分组
     *
     * @return 分组列表
     */
    public List<G> groups() {
        // 空数据集直接返回空列表
        if (data == null || data.isEmpty()) {
            return Collections.emptyList();
        }

        // 初始化数据
        memberComparatorMap = grouper.memberComparator();
        defaultComparator = grouper.defaultComparator();
        groupMap = defaultComparator == null ? new LinkedHashMap<>() : new HashMap<>();

        // 分组
        for (E item : data) {
            if (item != null) {
                K key = grouper.alloc(item);
                if (key != null) {
                    G group = groupMap.get(key);
                    if (group == null) {
                        group = grouper.newGroup(item);
                        group.setKey(key);
                        group.setMembers(new LinkedList<>());
                        groupMap.put(key, group);
                    }
                    group.getMembers().add(item);
                }
            }
        }

        // 组内排序
        for (G group : groupMap.values()) {
            Comparator<E> comparator = getComparator(group);
            if (comparator != null) {
                group.getMembers().sort(comparator);
            }
        }

        // 构造分组列表并执行组间排序
        Stream<G> stream = groupMap.values().stream();
        Comparator<G> groupComparator = grouper.groupComparator();
        if (groupComparator != null) {
            stream = stream.sorted(groupComparator);
        }
        return stream.collect(Collectors.toList());
    }

    /**
     * 获取分组对应的组内排序方法
     *
     * @param group 分组
     * @return 组内排序方法
     */
    private Comparator<E> getComparator(G group) {
        if (memberComparatorMap != null) {
            MemberComparator<E> memberComparator = memberComparatorMap.get(group.getKey());
            if (memberComparator != null) {
                return memberComparator.getType()
                        .apply(defaultComparator, memberComparator.getComparator());
            }
        }
        return defaultComparator;
    }
}
