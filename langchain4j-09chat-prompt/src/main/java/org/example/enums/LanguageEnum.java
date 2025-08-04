package org.example.enums;

public enum LanguageEnum {
    CHINESE(1, "中文"),
    ENGLISH(2, "英文"),
    JAPANESE(3, "日语"),
    KOREAN(4, "韩语"),
    FRENCH(5, "法语"),
    GERMAN(6, "德语"),
    SPANISH(7, "西班牙语"),
    ITALIAN(8, "意大利语"),
    RUSSIAN(9, "俄语"),
    PORTUGUESE(10, "葡萄牙语"),
    ARABIC(11, "阿拉伯语"),
    ;
    private Integer code;
    private String value;
    private LanguageEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * description  根据code获取语言，默认返回中文
     * date         2025/8/4 09:37
     * @param       code
     * @return      java.lang.String
     */
    public static String getValueByCode(Integer code) {
        for (LanguageEnum value : LanguageEnum.values()) {
            if (value.code.equals(code)) {
                return value.value;
            }
        }
        return LanguageEnum.CHINESE.value;
    }

    public Integer getCode() {
        return code;
    }
    public String getValue() {
        return value;
    }
}
