package org.datagrouper.datagrouper.student;

import org.datagrouper.datagrouper.PinyinGrouper;
import org.datagrouper.datagrouper.core.ComparatorType;
import org.datagrouper.datagrouper.core.MemberComparator;

import java.util.HashMap;
import java.util.Map;

public class StudentGrouper extends PinyinGrouper<Student, StudentGroup> {

    public StudentGrouper() {
        super("管理员", "其他");
    }

    @Override
    protected String key(Student student) {
        return student.getName();
    }

    @Override
    protected boolean like(Student student) {
        // 管理员位于收藏分组
        return student.getName().contains("admin");
    }

    @Override
    public StudentGroup newGroup() {
        return new StudentGroup();
    }

    @Override
    public Map<String, MemberComparator<Student>> memberComparator() {
        // 顶置超级管理员
        Map<String, MemberComparator<Student>> map = new HashMap<>(2);
        map.put(GROUP_ID_LIKE, new MemberComparator<>((s1, s2) -> {
            boolean s1IsSuper = s1.getName().startsWith("super");
            boolean s2IsSuper = s2.getName().startsWith("super");
            return Boolean.compare(s2IsSuper, s1IsSuper);
        }, ComparatorType.BEFORE));
        return map;
    }
}
