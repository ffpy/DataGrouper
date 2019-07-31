## 介绍
DataGrouper是一个数据分组器，可以把List按照指定的规则来进行分组。<br>
内置了拼音分组器，可以很方便地把列表像微信联系人列表那样按照拼音进行分组，使用pinyin4j来获取拼音。

## 开始
#### Student.java
```
@Data
@AllArgsConstructor
public class Student {

    private String name;
}
```

#### StudentGroup.java
```
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
```

#### StudentGrouper.java
```
public class StudentGrouper extends PinyinGrouper<Student, StudentGroup> {

    public StudentGrouper() {
        super("管理员", "其他");
    }

    @Override
    protected String key(Student student) {
        return student.getName();
    }

    @Override
    protected boolean like(Student student) {
        // 管理员位于收藏分组
        return student.getName().contains("admin");
    }

    @Override
    public StudentGroup newGroup() {
        return new StudentGroup();
    }

    @Override
    public Map<String, MemberComparator<Student>> memberComparator() {
        // 顶置超级管理员
        Map<String, MemberComparator<Student>> map = new HashMap<>(2);
        map.put(GROUP_ID_LIKE, new MemberComparator<>((s1, s2) -> {
            boolean s1IsSuper = s1.getName().startsWith("super");
            boolean s2IsSuper = s2.getName().startsWith("super");
            return Boolean.compare(s2IsSuper, s1IsSuper);
        }, ComparatorType.BEFORE));
        return map;
    }
}
```

#### main
```
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

List<StudentGroup> groups = new DataGrouper<>(students, new StudentGrouper()).groups();
groups.forEach(System.out::println);
```

#### output
```
StudentGroup(name=管理员, members=[Student(name=super_admin), Student(name=1admin), Student(name=2admin), Student(name=admin)])
StudentGroup(name=A, members=[Student(name=啊1), Student(name=aaa), Student(name=abc)])
StudentGroup(name=B, members=[Student(name=bbb)])
StudentGroup(name=其他, members=[Student(name=123)])
```
