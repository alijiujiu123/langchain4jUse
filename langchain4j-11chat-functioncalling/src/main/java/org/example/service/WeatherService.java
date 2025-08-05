package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface WeatherService {

    /**
     * description  根据经纬度查询当前天气
     * @param
     * @return      java.lang.String
     */
    /**
     * description  根据经纬度查询当前天气
     * date         2025/8/4 18:48
     * @param       longitude 经度
     * @param       latitude  纬度
     * @return      java.lang.String
     */
    JsonNode weather(float longitude, float latitude) throws JsonProcessingException;
}
