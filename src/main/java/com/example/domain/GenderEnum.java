package com.example.domain;

/**
 * Created by fjc on 2017-06-29.
 */
public enum GenderEnum {

    MAN("0", "男"), WOMAN("1", "女"), UNDEFINED("2", "未知");

    private String key;
    private String value;

    GenderEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public static String getValue(String key) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getKey().equals(key)) {
                return genderEnum.value;
            }
        }
        return null;
    }
}
