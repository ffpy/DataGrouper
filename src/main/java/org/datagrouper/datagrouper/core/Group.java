package org.datagrouper.datagrouper.core;

import java.util.List;

/**
 * 分组接口
 */
public interface Group<E> {

    /**
     * 获取分组名
     *
     * @return 分组名
     */
    String getName();

    /**
     * 设置分组名
     *
     * @param name 分组名
     */
    void setName(String name);

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
