package org.example.service.serviceImp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 天气获取service实现
 * @author geshishuai
 * @version 1.0
 * @date 2025/8/4 18:57
 */
@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    @Resource
    private RestTemplate restTemplate;

    @Value("${qweather.apiHost}")
    private String apiHost;
    @Value("${qweather.apiKey}")
    private String apiKey;

    @Override
    public JsonNode weather(float longitude, float latitude) throws JsonProcessingException {
        // 构建参数
        String location = String.format("%f,%f", longitude, latitude);
        // 构建url
        String url = String.format(apiHost, location);

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-QW-Api-Key", apiKey);
        headers.set("Accept-Encoding", "gzip");
        // 请求体
        HttpEntity<String> entity = new HttpEntity<>(headers);
        // 发送请求
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        log.info("jsonNode: {}", jsonNode);
        return jsonNode;

    }
}
