package com.application.passbook.merchants.constant;

/**
 * 优惠券颜色
 */
public enum TemplateColor {

    RED(1, "红色"),
    GREEN(2, "绿色"),
    BLUE(3, "蓝色");

    // 颜色代码
    private Integer code;

    // 颜色表述
    private String color;

    TemplateColor(Integer code, String color){
        this.code = code;
        this.color = color;
    }

    public Integer getCode() {
        return code;
    }

    public String getColor() {
        return color;
    }
}
