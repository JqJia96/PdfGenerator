package invoicegenerator;

import java.time.LocalDateTime;

public class InvoiceData {

    public String invoiceId;
    public String customerId;
    public LocalDateTime invoiceDate;

    public String leftName;
    public String leftAddress1;
    public String leftAddress2;

    public String rightName;
    public String rightAddress1;
    public String rightAddress2;

    public long totalKwh;
    public double totalPrice;

}
