package org.example.modelTools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.service.WeatherService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 天气工具
 * ai工具高阶实现
 * @version 1.0
 * @date 2025/8/4 19:02
 */
@Slf4j
@Component
public class WeatherToolHandler {

    @Resource
    private WeatherService weatherService;

    @Tool("根据经纬度查询当前天气")
    public JsonNode weather(@P("经度") float longitude, @P("纬度") float latitude) {
        log.info("天气工具查询，经度: {}, 纬度: {}", longitude, latitude);
        JsonNode weather = null;
        try {
            weather = weatherService.weather(longitude, latitude);
        } catch (JsonProcessingException e) {
            log.error("查询天气异常", e);
        }
        return weather;
    }

    @Tool("查询当前位置信息,获取经纬度")
    public Map<String, Float> getLocation() {
        float longitude = 114.17432827209761f;
        float latitude = 22.316554772157385f;
        log.info("位置工具查询，经度: {}, 纬度: {}", longitude, latitude);
        return Map.of("longitude", longitude, "latitude", latitude);
    }
}
