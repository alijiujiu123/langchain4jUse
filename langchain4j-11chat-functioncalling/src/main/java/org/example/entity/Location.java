package org.example.entity;

import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Description("经纬度信息")
public class Location {
    @Description("经度")
    private float longitude;
    @Description("纬度")
    private float latitude;

}
