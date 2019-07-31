package org.datagrouper.datagrouper.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具类
 */
public class PinyinUtils {

    /** 默认带声调输出格式 */
    private static final HanyuPinyinOutputFormat FORMAT_DEFAULT;

    /** 不带声调输出格式 */
    private static final HanyuPinyinOutputFormat FORMAT_WITHOUT_TONE;

    static {
        FORMAT_DEFAULT = new HanyuPinyinOutputFormat();

        FORMAT_WITHOUT_TONE = new HanyuPinyinOutputFormat();
        // 不显示声调
        FORMAT_WITHOUT_TONE.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    /**
     * 将字符串中的汉语转为拼音，非汉语则不变
     *
     * @param str     输入字符串
     * @param hasTone 是否带声调
     * @return 转换后的字符串
     */
    public static String toPinyin(String str, boolean hasTone) {
        if (str == null) {
            return null;
        }

        try {
            return PinyinHelper.toHanYuPinyinString(str,
                    hasTone ? FORMAT_DEFAULT : FORMAT_WITHOUT_TONE, "", true);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        return "";
    }
}
