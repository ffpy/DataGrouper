package org.datagrouper.datagrouper;

import org.datagrouper.datagrouper.core.Groups;
import org.datagrouper.datagrouper.date.DateGroup;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class GroupsTest {

    @Test
    public void test() {
        List<LocalDateTime> list = new LinkedList<>();
        list.add(LocalDateTime.parse("2018-01-01T10:00:00"));
        list.add(LocalDateTime.parse("2018-01-01T11:00:00"));
        list.add(LocalDateTime.parse("2018-01-02T01:00:00"));
        list.add(LocalDateTime.parse("2018-01-02T12:00:00"));

        Groups.of(list, LocalDate.class, DateGroup.class)
                .newGroup(time -> new DateGroup())
                .alloc(LocalDateTime::toLocalDate)
                .group()
                .forEach(System.out::println);
    }
}
