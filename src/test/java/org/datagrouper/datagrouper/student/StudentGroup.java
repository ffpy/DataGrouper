package org.datagrouper.datagrouper.student;

import lombok.ToString;
import org.datagrouper.datagrouper.core.Group;

import java.util.List;

@ToString
public class StudentGroup implements Group<String, Student> {

    private String key;
    private List<Student> members;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String name) {
        this.key = name;
    }

    @Override
    public List<Student> getMembers() {
        return members;
    }

    @Override
    public void setMembers(List<Student> members) {
        this.members = members;
    }
}
