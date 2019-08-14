package org.datagrouper.datagrouper;

import org.datagrouper.datagrouper.core.DataGrouper;
import org.datagrouper.datagrouper.student.Student;
import org.datagrouper.datagrouper.student.StudentGroup;
import org.datagrouper.datagrouper.student.StudentGrouper;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class StudentGroupTest {

    @Test
    public void test() {
        List<StudentGroup> groups = new DataGrouper<>(getStudents(), new StudentGrouper()).groups();
        groups.forEach(System.out::println);
    }

    private List<Student> getStudents() {
        List<Student> students = new LinkedList<>();
        students.add(new Student("abc"));
        students.add(new Student("123"));
        students.add(new Student("啊1"));
        students.add(new Student("aaa"));
        students.add(new Student("bbb"));
        students.add(new Student("1admin"));
        students.add(new Student("admin"));
        students.add(new Student("super_admin"));
        students.add(new Student("2admin"));
        return students;
    }
}
