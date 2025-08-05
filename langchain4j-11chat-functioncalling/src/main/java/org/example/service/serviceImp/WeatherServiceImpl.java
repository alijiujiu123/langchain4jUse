package org.example.service.serviceImp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.CityInfo;
import org.example.entity.WeatherNow;
import org.example.entity.WeatherResponse;
import org.example.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

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
    @Value("${qweather.url.weatherNow}")
    private String weatherNow;
    @Value("${qweather.url.cityLoopup}")
    private String cityLoopup;

    @Override
    public WeatherNow weather(float longitude, float latitude) {
        log.info("查询天气，经度: {}, 纬度: {}", longitude, latitude);
        WeatherResponse weatherResponse = null;
        try {
            // 构建参数
            String location = String.format("%f,%f", longitude, latitude);
            // 构建url
            String weatherNowUrl = String.format(weatherNow, location);

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-QW-Api-Key", apiKey);
            headers.set("Accept-Encoding", "gzip");
            // 请求体
            HttpEntity<String> entity = new HttpEntity<>(headers);
            // 发送请求
//            ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, WeatherResponse.class);
//            responseBody = response.getBody();
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    apiHost+weatherNowUrl,
                    HttpMethod.GET,
                    entity,
                    byte[].class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                byte[] responseBody = response.getBody();
                String json;
                if (isGzipped(response.getHeaders())) {
                    json = decompressGzip(responseBody);
                } else {
                    json = new String(responseBody, StandardCharsets.UTF_8);
                }

                // ✅ 将 JSON 字符串反序列化为 Java 对象
                weatherResponse = new com.fasterxml.jackson.databind.ObjectMapper()
                        .readValue(json, WeatherResponse.class);

                log.info("responseBody: {}", response);
            }
        }catch (Exception e) {
            log.error("请求天气接口异常", e);
        }
        return weatherResponse.getNow();

    }

    @Override
    public List<CityInfo> cityLookup(float longitude, float latitude) {
        log.info("查询城市信息，经度: {}, 纬度: {}", longitude, latitude);
        WeatherResponse weatherResponse = null;
        try {
            // 构建参数
            String location = String.format("%f,%f", longitude, latitude);
            // 构建url
            String cityLookupUrl = String.format(cityLoopup, location);

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-QW-Api-Key", apiKey);
            headers.set("Accept-Encoding", "gzip");
            // 请求体
            HttpEntity<String> entity = new HttpEntity<>(headers);
            // 发送请求
//            ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, WeatherResponse.class);
//            responseBody = response.getBody();
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    apiHost+cityLookupUrl,
                    HttpMethod.GET,
                    entity,
                    byte[].class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                byte[] responseBody = response.getBody();
                String json;
                if (isGzipped(response.getHeaders())) {
                    json = decompressGzip(responseBody);
                } else {
                    json = new String(responseBody, StandardCharsets.UTF_8);
                }

                // ✅ 将 JSON 字符串反序列化为 Java 对象
                weatherResponse = new com.fasterxml.jackson.databind.ObjectMapper()
                        .readValue(json, WeatherResponse.class);

                log.info("responseBody: {}", response);
            }
        }catch (Exception e) {
            log.error("请求城市信息接口异常", e);
        }
        return weatherResponse.getLocation();
    }

    private static boolean isGzipped(HttpHeaders headers) {
        return headers.getFirst("Content-Encoding") != null &&
                headers.getFirst("Content-Encoding").toLowerCase().contains("gzip");
    }

    private static String decompressGzip(byte[] compressed) throws Exception {
        try (
                GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressed));
                InputStreamReader reader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);
                BufferedReader in = new BufferedReader(reader)
        ) {
            StringBuilder outStr = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                outStr.append(line);
            }
            return outStr.toString();
        }
    }


//    public static void main(String[] args) {
//        WeatherServiceImpl weatherService = new WeatherServiceImpl();
//        weatherService.restTemplate = new RestTemplate();
//        weatherService.apiHost = "https://ng6hewtg6j.re.qweatherapi.com";
//        weatherService.apiKey = "bec9a61822b14c8fadce8529552f75b0";
//        weatherService.weatherNow = "/v7/weather/now?location=%s";
//        weatherService.cityLoopup = "/geo/v2/city/lookup?location=%s";
//        WeatherNow weather = weatherService.weather(114.17433f, 22.316555f);
//        System.out.println(weather);
//        List<CityInfo> cityInfo = weatherService.cityLookup(114.17433f, 22.316555f);
//        System.out.println(cityInfo);
//    }
}
