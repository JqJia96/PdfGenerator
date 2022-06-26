import service.InvoiceGeneratorService;

public class Main {

    private final static String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) {
        InvoiceGeneratorService invoiceGeneratorService = new InvoiceGeneratorService("PDF READY", "PDF GENERATED", BROKER_URL);

        invoiceGeneratorService.run();
    }

}
