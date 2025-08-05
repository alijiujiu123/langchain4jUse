package org.example.modelTools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.CityInfo;
import org.example.entity.Location;
import org.example.entity.WeatherNow;
import org.example.entity.WeatherResponse;
import org.example.service.WeatherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    // 缓存经纬度信息
    private final Map<Long,  Location> locationMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // 缓存经纬度信息
        // 用户1: 大雨
        locationMap.put(1L, new Location(114.17432827209761f, 22.316554772157385f));
        // 用户2: 晴天
        locationMap.put(2L, new Location(116.41f, 39.92f));
    }

    @Tool("根据经纬度查询当前天气")
    public WeatherNow weather(Location location) {
        log.info("根据经纬度查询当前天气Tool, location: {}", location);
        return weatherService.weather(location.getLongitude(), location.getLatitude());
    }

    @Tool("根据用户id,获取经纬度")
    public Location getLocation(@ToolMemoryId Long memoryId) {
        log.info("查询经纬度信息, memoryId:{}", memoryId);
        // 查询经纬度信息, 默认晴天位置
        Location orDefault = locationMap.getOrDefault(memoryId, new Location(116.41f, 39.92f));
        return orDefault;
    }

    @Tool("根据经纬度进行城市搜索")
    public List<CityInfo> cityLookup(Location location) {
        log.info("根据经纬度进行城市搜索, location: {}", location);
        return weatherService.cityLookup(location.getLongitude(), location.getLatitude());
    }
}
