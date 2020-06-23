package com.snackpub.tools;



/**
 * 字符串格式化
 */
public class StrFormatter {
    /**
     * 格式化字符串<br>
     * 此方法只是简单将占位符{}按照顺序替换为参数 <br>
     * 如果想输出{} 使用\\ 转义 { 即可，如果同时想输出 {} 之前的 \ 使用双转义符 \\\\即可 <br>
     * 例如：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》this is a for b<br>
     * 转义{}:	format("this is \\{} for {}", "a", "b") =》this is \{} for a<br>
     * 转义\:		format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param strPattern
     * @param argArray
     * @return
     */
    public static String format(final String strPattern, final Object... argArray) {
        if (SnackFunc.isBlank(strPattern) || SnackFunc.isEmpty(argArray)) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();

        /**
         *
         */
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        /**
         *
         */
        int handledPosition = 0;

        /**
         *
         */
        int delimIndex;
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = strPattern.indexOf(StringPool.EMPTY_JSON, handledPosition);
            /**
             *
             */
            if (delimIndex == -1) {
                /**
                 *
                 */
                if (handledPosition == 0) {
                    return strPattern;
                } else {
                    sbuf.append(strPattern, handledPosition, strPatternLength);
                    return sbuf.toString();
                }
            } else {
                /**
                 *
                 */
                if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == StringPool.BACK_SLASH) {
                    /**
                     *
                     */
                    if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == StringPool.BACK_SLASH) {
                        //
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(SnackFunc.toStr(argArray[argIndex]));
                        handledPosition = delimIndex + 2;
                    } else {
                        //
                        argIndex--;
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(StringPool.LEFT_BRACE);
                        handledPosition = delimIndex + 1;
                    }
                } else {//
                    sbuf.append(strPattern, handledPosition, delimIndex);
                    sbuf.append(SnackFunc.toStr(argArray[argIndex]));
                    handledPosition = delimIndex + 2;
                }
            }
        }
        // append the characters following the last {} pair.
        //
        sbuf.append(strPattern, handledPosition, strPattern.length());

        return sbuf.toString();
    }
}
