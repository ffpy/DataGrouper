package org.datagrouper.datagrouper.date;

import lombok.ToString;
import org.datagrouper.datagrouper.core.Group;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ToString
public class DateGroup implements Group<LocalDate, LocalDateTime> {
    private LocalDate key;
    private List<LocalDateTime> members;

    @Override
    public LocalDate getKey() {
        return key;
    }

    @Override
    public void setKey(LocalDate key) {
        this.key = key;
    }

    @Override
    public List<LocalDateTime> getMembers() {
        return members;
    }

    @Override
    public void setMembers(List<LocalDateTime> members) {
        this.members = members;
    }
}
