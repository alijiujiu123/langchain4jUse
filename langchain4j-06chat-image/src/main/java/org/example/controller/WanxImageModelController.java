package org.example.controller;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.utils.JsonUtils;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/wanx")
public class WanxImageModelController {

    @Value("${spring.ai.alibaba.apiKey}")
    private String alibabaApiKey;

    @Autowired
    private WanxImageModel wanxImageModel;

    @GetMapping("/simpleCreate")
    public String simpleCreate() {
        Response<Image> imageResponse = wanxImageModel.generate("明成祖朱棣现代照片");
        long l = System.currentTimeMillis();
        log.info("simpleCreate开始生成图片");
        URI url = imageResponse.content().url();
        log.info("图片地址：{}, 耗时: {}", url, System.currentTimeMillis() - l);
        return url.toString();
    }

    @GetMapping("/completedCreate")
    public String completedCreate() {
        String prompt = "近景镜头，18岁的中国女孩，现代服饰，圆脸，侧对镜头，" +
                "干净简洁服装，商业摄影，室外，电影级光照，半身特写，精致的淡妆，锐利的边缘。";
        ImageSynthesisParam imageSynthesisParam = ImageSynthesisParam.builder()
                .apiKey(alibabaApiKey)
                .model(ImageSynthesis.Models.WANX_V1)
                .prompt(prompt)
                .style("<watercolor>")
                .n(1)
                .size("1024*1024")
                .build();
        ImageSynthesis imageSynthesis = new ImageSynthesis();
        ImageSynthesisResult result = null;
        long l = System.currentTimeMillis();
        log.info("开始生成图片");
        try {
            result = imageSynthesis.call(imageSynthesisParam);
        }catch (Exception e) {
            log.error("error:{}", e.getMessage());
        }
        log.info("生成图片耗时:{}", System.currentTimeMillis() - l);
        String json = JsonUtils.toJson(result);
        log.info("result:{}", json);
        return json;
    }

}
