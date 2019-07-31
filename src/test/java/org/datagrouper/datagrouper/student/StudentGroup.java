package org.datagrouper.datagrouper.student;

import lombok.ToString;
import org.datagrouper.datagrouper.core.Group;

import java.util.List;

@ToString
public class StudentGroup implements Group<Student> {

    private String name;

    private List<Student> members;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
