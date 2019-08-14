package org.datagrouper.datagrouper.core;

import java.util.List;

/**
 * 分组接口
 */
public interface Group<K, E> {

    /**
     * 获取分组Key
     *
     * @return 分组Key
     */
    K getKey();

    /**
     * 设置分组Key
     *
     * @param key 分组Key
     */
    void setKey(K key);

    /**
     * 获取成员列表
     *
     * @return 成员列表
     */
    List<E> getMembers();

    /**
     * 设置成员列表
     *
     * @param members 成员列表
     */
    void setMembers(List<E> members);
}
