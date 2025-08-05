package org.example.entity;

import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Description("当前天气信息")
public class WeatherNow {
    @Description("观测时间，格式为 ISO 8601，例如：2025-08-04T17:34+08:00")
    private String obsTime;

    @Description("当前温度（℃）")
    private String temp;

    @Description("体感温度（℃）")
    private String feelsLike;

    @Description("天气图标代码")
    private String icon;

    @Description("天气状况文本，例如：多云")
    private String text;

    @Description("风向360角度")
    private String wind360;

    @Description("风向文本，例如：西南风")
    private String windDir;

    @Description("风力等级")
    private String windScale;

    @Description("风速，单位：公里/小时")
    private String windSpeed;

    @Description("相对湿度（%）")
    private String humidity;

    @Description("当前降水量（毫米）")
    private String precip;

    @Description("大气压强（百帕）")
    private String pressure;

    @Description("能见度（公里）")
    private String vis;

    @Description("云量（%）")
    private String cloud;

    @Description("露点温度（℃）")
    private String dew;
}
