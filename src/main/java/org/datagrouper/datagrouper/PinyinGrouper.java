package org.datagrouper.datagrouper;

import org.apache.commons.lang3.StringUtils;
import org.datagrouper.datagrouper.core.Group;
import org.datagrouper.datagrouper.core.Grouper;
import org.datagrouper.datagrouper.core.MemberComparator;
import org.datagrouper.datagrouper.utils.HashMapUtils;
import org.datagrouper.datagrouper.utils.PinyinUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 拼音分组器，按照A-Z来分组，中文会转换为拼音来分组，
 * 开头不是字母的会放到其他分组(#)。
 * 可以设置收藏分组(*)。
 *
 * @param <E> 元素类型
 * @param <G> 分组类型
 */
public abstract class PinyinGrouper<E, G extends Group<String, E>> implements Grouper<String, E, G> {

    /** 收藏分组Key */
    public static final String GROUP_KEY_LIKE = "*";

    /** 其他分组Key */
    public static final String GROUP_KEY_OTHER = "#";

    /** 拼音缓存（str -> 拼音str） */
    private final Map<String, String> pinyinMap;

    /** 收藏分组名 */
    private final String likeGroupName;

    /** 其他分组名 */
    private final String otherGroupName;

    public PinyinGrouper() {
        this(0);
    }

    public PinyinGrouper(int dataSize) {
        this(GROUP_KEY_LIKE, GROUP_KEY_OTHER, dataSize);
    }

    public PinyinGrouper(String likeGroupName, String otherGroupName) {
        this(likeGroupName, otherGroupName, 0);
    }

    /**
     * @param likeGroupName  收藏分组名
     * @param otherGroupName 其他分组名
     * @param dataSize       预计数据集大小，用于防止HashMap扩容
     */
    public PinyinGrouper(String likeGroupName, String otherGroupName, int dataSize) {
        this.likeGroupName = Objects.requireNonNull(likeGroupName);
        this.otherGroupName = Objects.requireNonNull(otherGroupName);
        this.pinyinMap = dataSize > 0 ? HashMapUtils.newHashMap(dataSize) : new HashMap<>();
    }

    /**
     * 获取元素的分组key
     * <p>
     * 比如People类按照name字段来分组，则可以实现为 return e.getName();
     *
     * @param e 元素
     * @return 元素的分组key
     */
    protected abstract String key(E e);

    /**
     * 判断元素是不是要放到收藏分组
     *
     * @param e 元素
     * @return true为放到收藏分组，false反之
     */
    protected boolean like(E e) {
        return false;
    }

    @Override
    public String alloter(E e) {
        String key = key(e);
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (like(e)) {
            return GROUP_KEY_LIKE;
        }

        String pinyin = getPinyin(key);

        char firstChar = pinyin.charAt(0);
        if (Character.isLetter(firstChar)) {
            return String.valueOf(firstChar);
        }

        return GROUP_KEY_OTHER;
    }

    @Override
    public Comparator<E> defaultComparator() {
        return Comparator.comparing(o -> getPinyin(key(o)));
    }

    @Override
    public Map<String, MemberComparator<E>> memberComparator() {
        return null;
    }

    @Override
    public Comparator<G> groupComparator() {
        return Comparator.comparingInt((G g) -> getGroupType(g.getKey())).thenComparing(Group::getKey);
    }

    private static int getGroupType(String key) {
        if (GROUP_KEY_LIKE.equals(key)) {
            return 2;
        } else if (GROUP_KEY_OTHER.equals(key)) {
            return 0;
        }
        return 1;
    }

    /**
     * 获取字符串的拼音字符串
     *
     * @param str 字符串
     * @return 对应的拼音字符串
     */
    private String getPinyin(String str) {
        String pinyin = pinyinMap.get(str);
        if (pinyin == null) {
            pinyin = StringUtils.upperCase(PinyinUtils.toPinyin(str, true));
            pinyinMap.put(str, pinyin);
        }
        return pinyin;
    }
}
