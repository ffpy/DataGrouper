package org.datagrouper.datagrouper.name;

import org.datagrouper.datagrouper.core.Grouper;
import org.datagrouper.datagrouper.core.MemberComparator;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Map;

public class NameGrouper implements Grouper<String, Name, NameGroup> {
    @Override
    public NameGroup newGroup(Name name) {
        return new NameGroup();
    }

    @Nullable
    @Override
    public String alloc(Name name) {
        return String.valueOf(name.getName().charAt(0)).toUpperCase();
    }

    @Nullable
    @Override
    public Comparator<Name> defaultComparator() {
        return null;
    }

    @Nullable
    @Override
    public Map<String, MemberComparator<Name>> memberComparator() {
        return null;
    }

    @Nullable
    @Override
    public Comparator<NameGroup> groupComparator() {
        return null;
    }
}
