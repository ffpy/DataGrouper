package org.datagrouper.datagrouper.core;

import java.util.Objects;

/**
 * 分组Key
 */
public class GroupKey {

    /** 分组id，用于分配分组 */
    private final String id;

    /** 分组名，会放到分组的分组名那里 */
    private final String name;

    /**
     * @param id 分组id，分组id与分组名同名
     */
    public GroupKey(String id) {
        this(id, id);
    }

    /**
     * @param id   分组id
     * @param name 分组名
     */
    public GroupKey(String id, String name) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GroupKey{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
