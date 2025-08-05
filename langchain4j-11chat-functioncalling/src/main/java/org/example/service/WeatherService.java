package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.entity.CityInfo;
import org.example.entity.WeatherNow;
import org.example.entity.WeatherResponse;

import java.util.List;

public interface WeatherService {

    /**
     * description  根据经纬度查询当前天气
     * date         2025/8/4 18:48
     * @param       longitude 经度
     * @param       latitude  纬度
     * @return      java.lang.String
     */
    WeatherNow weather(float longitude, float latitude);

    /**
     * description  根据经纬度查询城市信息
     * date         2025/8/4 18:48
     * @param       longitude 经度
     * @param       latitude  纬度
     * @return      java.lang.String
     */
    List<CityInfo> cityLookup(float longitude, float latitude);
}
