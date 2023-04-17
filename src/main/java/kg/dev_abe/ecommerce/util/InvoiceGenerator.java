package kg.dev_abe.ecommerce.util;


import com.lowagie.text.DocumentException;
import kg.dev_abe.ecommerce.models.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class InvoiceGenerator {
    private static final String ORDER_TEMPLATE = "order-template";
    private SpringTemplateEngine templateEngine;

    public byte[] generateInvoice(Order order) {
        String html = parseThymeleafTemplate(order);
        return generatePdfFromHtml(html);
    }

    public String parseThymeleafTemplate(Order order) {

        Context context = new Context();
        context.setVariable("order", order);
        return templateEngine.process(ORDER_TEMPLATE, context);
    }

    public byte[] generatePdfFromHtml(String html) {
        byte[] pdfBytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            pdfBytes = outputStream.toByteArray();
        } catch (DocumentException | IOException e) {
            log.error("Error during invoice generation");
        }
        return pdfBytes;

    }

}
