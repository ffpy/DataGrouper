package org.datagrouper.datagrouper.core;

import org.datagrouper.datagrouper.utils.HashMapUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 分组Key集合
 */
public class GroupKeys {

    /** 分组id -> 分组 */
    private final Map<String, GroupKey> groupKeyMap;

    /** 分组id -> position */
    private final Map<String, Integer> groupPositionMap;

    /** 位置上边界 */
    private int positionTop = 1;

    /** 位置下边界 */
    private int positionBottom = 1;

    public GroupKeys() {
        this.groupKeyMap = new HashMap<>();
        this.groupPositionMap = new HashMap<>();
    }

    /**
     * @param size 预计分组个数，用于防止HashMap扩容
     */
    public GroupKeys(int size) {
        this.groupKeyMap = HashMapUtils.newHashMap(size);
        this.groupPositionMap = HashMapUtils.newHashMap(size);
    }

    /**
     * 在头部添加分组，分组ID与分组名相同
     *
     * @param id 分组ID
     * @return this
     */
    public GroupKeys addFirst(String id) {
        return add(--positionTop, id);
    }

    /**
     * 在头部添加分组
     *
     * @param id   分组ID
     * @param name 分组名
     * @return this
     */
    public GroupKeys addFirst(String id, String name) {
        return add(--positionTop, id, name);
    }

    /**
     * 在头部添加分组
     *
     * @param groupKey 分组Key
     * @return this
     */
    public GroupKeys addFirst(GroupKey groupKey) {
        return add(--positionTop, groupKey);
    }

    /**
     * 在尾部添加分组，分组ID与分组名相同
     *
     * @param id 分组ID
     * @return this
     */
    public GroupKeys addLast(String id) {
        return add(positionBottom++, id);
    }

    /**
     * 在尾部添加分组
     *
     * @param id   分组ID
     * @param name 分组名
     * @return this
     */
    public GroupKeys addLast(String id, String name) {
        return add(positionBottom++, id, name);
    }

    /**
     * 在尾部添加分组
     *
     * @param groupKey 分组Key
     * @return this
     */
    public GroupKeys addLast(GroupKey groupKey) {
        return add(positionBottom++, groupKey);
    }

    /**
     * 在指定位置添加分组，分组ID与分组名相同
     *
     * @param position 位置
     * @param id       分组ID
     * @return this
     */
    public GroupKeys add(int position, String id) {
        return add(position, new GroupKey(id));
    }

    /**
     * 在指定位置添加分组
     *
     * @param position 位置
     * @param id       分组ID
     * @param name     分组名
     * @return this
     */
    public GroupKeys add(int position, String id, String name) {
        return add(position, new GroupKey(id, name));
    }

    /**
     * 在指定位置添加分组
     *
     * @param position 位置
     * @param groupKey 分组Key
     * @return this
     */
    public GroupKeys add(int position, GroupKey groupKey) {
        if (groupKeyMap.containsKey(groupKey.getId())) {
            throw new RuntimeException("groupKey is already exists");
        }

        groupPositionMap.put(groupKey.getId(), position);
        groupKeyMap.put(groupKey.getId(), groupKey);

        return this;
    }

    /**
     * 获取分组Key的Map
     *
     * @return groupKeyMap，不可修改
     */
    public Map<String, GroupKey> getGroupKeyMap() {
        return Collections.unmodifiableMap(groupKeyMap);
    }

    /**
     * 获取分组的Position的Map
     *
     * @return groupPositionMap，不可修改
     */
    public Map<String, Integer> getGroupPositionMap() {
        return Collections.unmodifiableMap(groupPositionMap);
    }

    /**
     * 获取分组个数
     *
     * @return 分组个数
     */
    public int size() {
        return groupKeyMap.size();
    }

    @Override
    public String toString() {
        return "GroupKeys{" +
                "groupKeyMap=" + groupKeyMap +
                ", groupPositionMap=" + groupPositionMap +
                ", positionTop=" + positionTop +
                ", positionBottom=" + positionBottom +
                '}';
    }
}
