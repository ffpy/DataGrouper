package org.datagrouper.datagrouper.name;

import lombok.Data;
import org.datagrouper.datagrouper.core.Group;

import java.util.List;

@Data
public class NameGroup implements Group<String, Name> {
    private String key;
    private List<Name> members;
}
