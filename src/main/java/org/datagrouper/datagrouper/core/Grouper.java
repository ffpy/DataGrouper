package org.datagrouper.datagrouper.core;

import com.sun.istack.internal.Nullable;

import java.util.Comparator;
import java.util.Map;

/**
 * 分组器，在这里提供分组的细节信息
 */
public interface Grouper<K, E, G extends Group<K, E>> {

    /**
     * 用于创建Group，return new CustomGroup();
     *
     * @return 新创建的Group
     */
    G newGroup();

    /**
     * 给元素分配分组，返回null不进行分组
     *
     * @param e 元素
     * @return 对应的分组Key
     */
    @Nullable
    K alloc(E e);

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
    @Nullable
    Map<K, MemberComparator<E>> memberComparator();

    /**
     * 返回分组排序方法
     *
     * @return 分组排序方法
     */
    Comparator<G> groupComparator();
}
