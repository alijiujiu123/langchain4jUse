package org.example.entity;
import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Description("城市位置信息")
public class CityInfo {

    @Description("地区名称，例如：东城")
    private String name;

    @Description("地区ID，例如：101011600")
    private String id;

    @Description("纬度")
    private String lat;

    @Description("经度")
    private String lon;

    @Description("所属市级行政区，例如：北京")
    private String adm2;

    @Description("所属省级行政区，例如：北京市")
    private String adm1;

    @Description("所属国家，例如：中国")
    private String country;

    @Description("时区名称，例如：Asia/Shanghai")
    private String tz;

    @Description("UTC时差，例如：+08:00")
    private String utcOffset;

    @Description("是否夏令时，0为否")
    private String isDst;

    @Description("地点类型，例如：city")
    private String type;

    @Description("排序等级，例如：35")
    private String rank;

    @Description("天气详情链接")
    private String fxLink;
}
