package org.example.service.serviceImp;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import org.example.entity.InvoiceData;
import org.example.entity.InvoiceInfo;

public interface InvoiceService {
    /**
     * description  开发票
     * date         2025/8/5 09:30
     * @param       companyName 公司名称
     * @param       dutyNumber  税号序列
     * @param       amount      开票金额
     * @return      java.lang.String
     */
    InvoiceData createInvoice(InvoiceInfo invoiceInfo);

    public static ToolSpecification toolSpecification() {
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("发票开具助手")
                .description("根据用户提交的开票信息，开具发票")
                .parameters(JsonObjectSchema.builder()
                        .addStringProperty("companyName", "公司名称")
                        .addStringProperty("dutyNumber", "税号序列")
                        .addStringProperty("amount", "开票金额，保留两位有效数字")
                        .build())
                .build();
        return toolSpecification;
    }
}
