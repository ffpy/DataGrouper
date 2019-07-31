package org.datagrouper.datagrouper.core;

import org.datagrouper.datagrouper.utils.HashMapUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 数据分组器
 *
 * @param <E> 元素类型
 * @param <G> 分组类型
 */
public class DataGrouper<E, G extends Group<E>> {

    /** 数据集 */
    private List<E> data;

    /** 分组id -> 分组 */
    private Map<String, GroupItem<E>> groupMap;

    /** 分组id -> 组内排序 */
    private Map<String, MemberComparator<E>> memberComparatorMap;

    /** 默认的组内排序 */
    private Comparator<E> defaultComparator;

    /** 分组器 */
    private Grouper<E, G> grouper;

    /** 分组id -> 分组Key */
    private Map<String, GroupKey> groupKeyMap;

    /** 分组id -> 分组位置 */
    private Map<String, Integer> groupPositionMap;

    /** 是否显示空分组 */
    private boolean isShowEmptyGroup;

    /**
     * @param data    数据集
     * @param grouper 分组器
     */
    public DataGrouper(List<E> data, Grouper<E, G> grouper) {
        this.data = data;
        this.grouper = Objects.requireNonNull(grouper);
    }

    /**
     * 是否显示空分组
     *
     * @param b true为是，false为否
     * @return this
     */
    public DataGrouper<E, G> showEmptyGroup(boolean b) {
        this.isShowEmptyGroup = b;
        return this;
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
        {
            GroupKeys groupKeys = grouper.groupKeys();
            groupKeyMap = groupKeys.getGroupKeyMap();
            groupPositionMap = groupKeys.getGroupPositionMap();
            groupMap = HashMapUtils.newHashMap(groupKeys.size());
        }

        // 分组
        for (E item : data) {
            if (item != null) {
                GroupItem<E> group = getGroupItem(grouper.alloter(item));
                if (group != null) {
                    group.getMembers().add(item);
                }
            }
        }

        // 组内排序
        for (GroupItem<E> group : groupMap.values()) {
            group.getMembers().sort(getComparator(group));
        }

        // 创建空分组
        if (isShowEmptyGroup) {
            for (GroupKey groupKey : groupKeyMap.values()) {
                if (!groupMap.containsKey(groupKey.getId())) {
                    getGroupItem(groupKey.getId());
                }
            }
        }

        // 构造分组列表并执行组间排序
        return groupMap.values()
                .stream()
                .sorted(Comparator.comparingInt(this::getGroupPosition))
                .map(groupItem -> {
                    G group = grouper.newGroup();
                    group.setName(groupItem.getGroupKey().getName());
                    group.setMembers(groupItem.getMembers());
                    return group;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取分组对应的组内排序方法
     *
     * @param group 分组
     * @return 组内排序方法
     */
    private Comparator<E> getComparator(GroupItem<E> group) {
        if (memberComparatorMap != null) {
            MemberComparator<E> memberComparator = memberComparatorMap.get(group.getGroupKey().getId());
            if (memberComparator != null) {
                return memberComparator.getType()
                        .apply(defaultComparator, memberComparator.getComparator());
            }
        }
        return defaultComparator;
    }

    /**
     * 根据分组id获取对应的分组
     *
     * @param groupId 分组id
     * @return 分组
     */
    private GroupItem<E> getGroupItem(String groupId) {
        GroupItem<E> group = groupMap.get(groupId);
        if (group == null) {
            GroupKey groupKey = groupKeyMap.get(groupId);
            if (groupKey == null) {
                return null;
            }

            group = new GroupItem<>(groupKey, new LinkedList<>());
            groupMap.put(groupId, group);
        }
        return group;
    }

    /**
     * 获取分组对应的位置
     *
     * @param group 分组
     * @return 位置
     */
    private int getGroupPosition(GroupItem<E> group) {
        return groupPositionMap.get(group.getGroupKey().getId());
    }

    /**
     * 分组
     *
     * @param <E> 成员类型
     */
    private static class GroupItem<E> {

        /** 分组Key */
        private final GroupKey groupKey;

        /** 成员列表 */
        private final List<E> members;

        public GroupItem(GroupKey groupKey, List<E> members) {
            this.groupKey = groupKey;
            this.members = members;
        }

        public List<E> getMembers() {
            return members;
        }

        public GroupKey getGroupKey() {
            return groupKey;
        }
    }
}
