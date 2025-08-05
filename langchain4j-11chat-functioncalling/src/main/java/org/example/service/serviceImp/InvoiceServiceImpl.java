package org.example.service.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.InvoiceData;
import org.example.entity.InvoiceInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Override
    public InvoiceData createInvoice(InvoiceInfo invoiceInfo) {
        log.info("invoiceInfo: {}", invoiceInfo);
        return generateMockInvoice(invoiceInfo.getAmount());
    }

    private InvoiceData generateMockInvoice(double amount) {
        String invoiceId = UUID.randomUUID().toString(); // 模拟唯一标识
        String invoiceNumber = "FP" + System.currentTimeMillis(); // 模拟发票号码
        String status = "SUCCESS"; // 开票成功
        String issuedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // 当前时间

        return new InvoiceData(invoiceId, invoiceNumber, status, amount, issuedAt);
    }
}
