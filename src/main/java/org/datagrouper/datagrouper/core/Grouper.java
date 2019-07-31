package org.datagrouper.datagrouper.core;

import java.util.Comparator;
import java.util.Map;

/**
 * 分组器，在这里提供分组的细节信息
 */
public interface Grouper<E, G extends Group<E>> {

    /**
     * 用于创建Group，return new CustomGroup();
     *
     * @return 新创建的Group
     */
    G newGroup();

    /**
     * 给元素分配分组
     *
     * @param e 元素
     * @return 对应的分组Key
     */
    String alloter(E e);

    /**
     * 返回分组Key集合
     *
     * @return 分组Key集合
     */
    GroupKeys groupKeys();

    /**
     * 返回默认的组内排序方法
     *
     * @return 默认的组内排序方法
     */
    Comparator<E> defaultComparator();

    /**
     * 返回指定分组对应的组内排序方法的Map
     *
     * @return 排序方法Map
     */
    Map<String, MemberComparator<E>> memberComparator();
}
