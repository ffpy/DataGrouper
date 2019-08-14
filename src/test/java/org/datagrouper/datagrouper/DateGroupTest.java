package org.datagrouper.datagrouper;

import org.datagrouper.datagrouper.core.DataGrouper;
import org.datagrouper.datagrouper.date.DateGroup;
import org.datagrouper.datagrouper.date.DateGrouper;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class DateGroupTest {

    @Test
    public void test() {
        List<LocalDateTime> list = new LinkedList<>();
        list.add(LocalDateTime.parse("2018-01-01T10:00:00"));
        list.add(LocalDateTime.parse("2018-01-01T11:00:00"));
        list.add(LocalDateTime.parse("2018-01-02T01:00:00"));
        list.add(LocalDateTime.parse("2018-01-02T12:00:00"));

        List<DateGroup> groups = new DataGrouper<>(list, new DateGrouper()).groups();
        groups.forEach(System.out::println);
    }
}
