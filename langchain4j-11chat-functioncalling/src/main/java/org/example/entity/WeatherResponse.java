package org.example.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 实时天气响应实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    /**
     * 状态码
     */
    private String code;

    /**
     * 数据更新时间，格式为 ISO 8601，例如：2025-08-04T18:51+08:00
     */
    private String updateTime;

    /**
     * 天气详情链接
     */
    private String fxLink;

    /**
     * 当前天气信息
     */
    private WeatherNow now;

    /**
     * 地理信息
     */
    private List<CityInfo> location;

    /**
     * 数据来源信息
     */
    private Refer refer;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Refer {
        /**
         * 数据来源列表
         */
        private List<String> sources;

        /**
         * 数据许可信息
         */
        private List<String> license;
    }
}
