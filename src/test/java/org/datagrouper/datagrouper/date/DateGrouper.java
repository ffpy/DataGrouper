package org.datagrouper.datagrouper.date;

import org.datagrouper.datagrouper.core.Grouper;
import org.datagrouper.datagrouper.core.MemberComparator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

public class DateGrouper implements Grouper<LocalDate, LocalDateTime, DateGroup> {
    @Override
    public DateGroup newGroup() {
        return new DateGroup();
    }

    @Override
    public LocalDate alloc(LocalDateTime time) {
        return time.toLocalDate();
    }

    @Override
    public Comparator<LocalDateTime> defaultComparator() {
        return Comparator.comparing(Function.identity());
    }

    @Override
    public Map<LocalDate, MemberComparator<LocalDateTime>> memberComparator() {
        return null;
    }

    @Override
    public Comparator<DateGroup> groupComparator() {
        return Comparator.comparing(DateGroup::getKey);
    }
}
