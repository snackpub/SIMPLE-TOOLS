//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snackpub.core.tools.support;


import com.snackpub.core.tools.utils.Func;
import com.snackpub.core.tools.utils.StringPool;
import com.snackpub.core.tools.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串分割器
 */
public class StrSpliter {
    //---------------------------------------------------------------------------------------------- Split by char

    /**
     * @param str
     * @return
     * @since 3.0.8
     */
    public static List<String> splitPath(String str) {
        return splitPath(str, 0);
    }

    /**
     * @param str
     * @return
     * @since 3.0.8
     */
    public static String[] splitPathToArray(String str) {
        return toArray(splitPath(str));
    }

    /**
     * @param str
     * @param limit
     * @return
     * @since 3.0.8
     */
    public static List<String> splitPath(String str, int limit) {
        return split(str, StringPool.SLASH, limit, true, true);
    }

    /**
     * @param str
     * @param limit
     * @return
     * @since 3.0.8
     */
    public static String[] splitPathToArray(String str, int limit) {
        return toArray(splitPath(str, limit));
    }

    /**
     * @param str
     * @param separator
     * @param ignoreEmpty
     * @return
     * @since 3.2.1
     */
    public static List<String> splitTrim(String str, char separator, boolean ignoreEmpty) {
        return split(str, separator, 0, true, ignoreEmpty);
    }

    /**
     * @param str
     * @param separator
     * @param isTrim
     * @param ignoreEmpty
     * @return
     * @since 3.0.8
     */
    public static List<String> split(String str, char separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, 0, isTrim, ignoreEmpty);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param ignoreEmpty
     * @return
     * @since 3.0.8
     */
    public static List<String> splitTrim(String str, char separator, int limit, boolean ignoreEmpty) {
        return split(str, separator, limit, true, ignoreEmpty, false);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param isTrim
     * @param ignoreEmpty
     * @return
     * @since 3.0.8
     */
    public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, false);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param isTrim
     * @param ignoreEmpty
     * @return
     * @since 3.2.1
     */
    public static List<String> splitIgnoreCase(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, true);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param isTrim
     * @param ignoreEmpty
     * @param ignoreCase
     * @return
     * @since 3.2.1
     */
    public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase) {
        if (StringUtil.isEmpty(str)) {
            return new ArrayList<String>(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
        }

        final ArrayList<String> list = new ArrayList<>(limit > 0 ? limit : 16);
        int len = str.length();
        int start = 0;
        for (int i = 0; i < len; i++) {
            if (Func.equals(separator, str.charAt(i))) {
                addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
                start = i + 1;

                //
                if (limit > 0 && list.size() > limit - 2) {
                    break;
                }
            }
        }
        return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param isTrim
     * @param ignoreEmpty
     * @return
     * @since 3.0.8
     */
    public static String[] splitToArray(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return toArray(split(str, separator, limit, isTrim, ignoreEmpty));
    }

    //---------------------------------------------------------------------------------------------- Split by String

    /**
     * @param str
     * @param separator
     * @param isTrim
     * @param ignoreEmpty
     * @return
     * @since 3.0.8
     */
    public static List<String> split(String str, String separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, -1, isTrim, ignoreEmpty, false);
    }

    /**
     * @param str
     * @param separator
     * @param ignoreEmpty
     * @return
     * @since 3.2.1
     */
    public static List<String> splitTrim(String str, String separator, boolean ignoreEmpty) {
        return split(str, separator, true, ignoreEmpty);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param isTrim
     * @param ignoreEmpty
     * @return
     * @since 3.0.8
     */
    public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, false);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param ignoreEmpty
     * @return
     * @since 3.2.1
     */
    public static List<String> splitTrim(String str, String separator, int limit, boolean ignoreEmpty) {
        return split(str, separator, limit, true, ignoreEmpty);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param isTrim
     * @param ignoreEmpty
     * @return
     * @since 3.2.1
     */
    public static List<String> splitIgnoreCase(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, limit, isTrim, ignoreEmpty, true);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param ignoreEmpty
     * @return
     * @since 3.2.1
     */
    public static List<String> splitTrimIgnoreCase(String str, String separator, int limit, boolean ignoreEmpty) {
        return split(str, separator, limit, true, ignoreEmpty, true);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param isTrim
     * @param ignoreEmpty
     * @param ignoreCase
     * @return
     * @since 3.2.1
     */
    public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty, boolean ignoreCase) {
        if (StringUtil.isEmpty(str)) {
            return new ArrayList<String>(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
        }

        if (StringUtil.isEmpty(separator)) {
            return split(str, limit);
        } else if (separator.length() == 1) {
            return split(str, separator.charAt(0), limit, isTrim, ignoreEmpty, ignoreCase);
        }

        final ArrayList<String> list = new ArrayList<>();
        int len = str.length();
        int separatorLen = separator.length();
        int start = 0;
        int i = 0;
        while (i < len) {
            i = StringUtil.indexOf(str, separator, start, ignoreCase);
            if (i > -1) {
                addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
                start = i + separatorLen;

                //
                if (limit > 0 && list.size() > limit - 2) {
                    break;
                }
            } else {
                break;
            }
        }
        return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
    }

    /**
     * @param str
     * @param separator
     * @param limit
     * @param isTrim
     * @param ignoreEmpty
     * @return
     * @since 3.0.8
     */
    public static String[] splitToArray(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return toArray(split(str, separator, limit, isTrim, ignoreEmpty));
    }

    //---------------------------------------------------------------------------------------------- Split by Whitespace

    /**
     * <br>
     *
     * @param str
     * @param limit
     * @return
     * @since 3.0.8
     */
    public static List<String> split(String str, int limit) {
        if (StringUtil.isEmpty(str)) {
            return new ArrayList<String>(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList<String>(1), str, true, true);
        }

        final ArrayList<String> list = new ArrayList<>();
        int len = str.length();
        int start = 0;
        for (int i = 0; i < len; i++) {
            if (Func.isEmpty(str.charAt(i))) {
                addToList(list, str.substring(start, i), true, true);
                start = i + 1;
                if (limit > 0 && list.size() > limit - 2) {
                    break;
                }
            }
        }
        return addToList(list, str.substring(start, len), true, true);
    }

    /**
     * @param str
     * @param limit
     * @return
     * @since 3.0.8
     */
    public static String[] splitToArray(String str, int limit) {
        return toArray(split(str, limit));
    }

    //---------------------------------------------------------------------------------------------- Split by regex

    /**
     * @param str
     * @param separatorPattern {@link Pattern}
     * @param limit
     * @param isTrim
     * @param ignoreEmpty
     * @return
     * @since 3.0.8
     */
    public static List<String> split(String str, Pattern separatorPattern, int limit, boolean isTrim, boolean ignoreEmpty) {
        if (StringUtil.isEmpty(str)) {
            return new ArrayList<String>(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
        }

        if (null == separatorPattern) {
            return split(str, limit);
        }

        final Matcher matcher = separatorPattern.matcher(str);
        final ArrayList<String> list = new ArrayList<>();
        int len = str.length();
        int start = 0;
        while (matcher.find()) {
            addToList(list, str.substring(start, matcher.start()), isTrim, ignoreEmpty);
            start = matcher.end();

            if (limit > 0 && list.size() > limit - 2) {
                break;
            }
        }
        return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
    }

    /**
     * @param str
     * @param separatorPattern {@link Pattern}
     * @param limit
     * @param isTrim
     * @param ignoreEmpty
     * @return
     * @since 3.0.8
     */
    public static String[] splitToArray(String str, Pattern separatorPattern, int limit, boolean isTrim, boolean ignoreEmpty) {
        return toArray(split(str, separatorPattern, limit, isTrim, ignoreEmpty));
    }

    //---------------------------------------------------------------------------------------------- Split by length

    /**
     * @param str
     * @param len
     * @return
     */
    public static String[] splitByLength(String str, int len) {
        int partCount = str.length() / len;
        int lastPartCount = str.length() % len;
        int fixPart = 0;
        if (lastPartCount != 0) {
            fixPart = 1;
        }

        final String[] strs = new String[partCount + fixPart];
        for (int i = 0; i < partCount + fixPart; i++) {
            if (i == partCount + fixPart - 1 && lastPartCount != 0) {
                strs[i] = str.substring(i * len, i * len + lastPartCount);
            } else {
                strs[i] = str.substring(i * len, i * len + len);
            }
        }
        return strs;
    }

    //---------------------------------------------------------------------------------------------------------- Private method start

    /**
     * @param list
     * @param part
     * @param isTrim
     * @param ignoreEmpty
     * @return
     */
    private static List<String> addToList(List<String> list, String part, boolean isTrim, boolean ignoreEmpty) {
        part = part.toString();
        if (isTrim) {
            part = part.trim();
        }
        if (false == ignoreEmpty || false == part.isEmpty()) {
            list.add(part);
        }
        return list;
    }

    /**
     * List
     *
     * @param list List
     * @return Array
     */
    private static String[] toArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }
    //---------------------------------------------------------------------------------------------------------- Private method end
}
