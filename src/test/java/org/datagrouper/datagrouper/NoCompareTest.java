package org.datagrouper.datagrouper;

import org.datagrouper.datagrouper.core.DataGrouper;
import org.datagrouper.datagrouper.name.Name;
import org.datagrouper.datagrouper.name.NameGrouper;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class NoCompareTest {

    @Test
    public void test() {
        List<Name> names = Arrays.asList(
                new Name("bcd"), new Name("b2"), new Name("b1"), new Name("aaa"), new Name("abc")
        );
        new DataGrouper<>(names, new NameGrouper()).groups()
                .forEach(System.out::println);
    }

}
