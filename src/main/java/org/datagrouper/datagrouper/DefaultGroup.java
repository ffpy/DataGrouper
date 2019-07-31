package org.datagrouper.datagrouper;

import org.datagrouper.datagrouper.core.Group;

import java.util.List;

/**
 * 默认分组
 *
 * @param <E> 元素类型
 */
public class DefaultGroup<E> implements Group<E> {
    /** 分组名 */
    private String name;

    /** 成员列表 */
    private List<E> members;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<E> getMembers() {
        return members;
    }

    @Override
    public void setMembers(List<E> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "DefaultGroup{" +
                "name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
}
