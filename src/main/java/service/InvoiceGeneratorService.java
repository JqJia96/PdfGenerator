package service;

import invoicegenerator.InvoiceData;
import invoicegenerator.InvoiceGenerator;
import invoicegenerator.PriceCalculator;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

public class InvoiceGeneratorService extends BaseService {

    private final String id;

    private InvoiceGenerator generator = new InvoiceGenerator();

    public InvoiceGeneratorService(String inDestination, String outDestination, String brokerUrl) {
        super(inDestination, outDestination, brokerUrl);

        this.id = UUID.randomUUID().toString();

        System.out.println("Invoice Generator Worker (" + this.id + ") started...");
    }

    @Override
    protected String executeInternal(String input) {

        String[] splitted = input.split(":");
        if (splitted.length != 3) {
            throw new IllegalArgumentException("input mismatch");
        }

        String invoiceId = splitted[0];
        String absolutePath = System.getProperty("user.home") + File.separator + "Invoice_" + invoiceId + ".pdf";

        File targetFile = new File(absolutePath);
        long totalKwh = Long.parseLong(splitted[1], 10);
        String customerId = splitted[2];

        InvoiceData data = new InvoiceData();

        data.invoiceId = invoiceId;
        data.invoiceDate = LocalDateTime.now();
        data.customerId = customerId;

        data.leftName = "Max Mustermann";
        data.leftAddress1 = "Musterstraße 1";
        data.leftAddress2 = "1010 Wien";

        data.rightName = "ElectroCharge";
        data.rightAddress1 = "Firmenstraße 1";
        data.rightAddress2 = "1010 Wien";

        data.totalKwh = totalKwh;
        data.totalPrice = PriceCalculator.getPriceForKwh(totalKwh);

        System.out.println("Writing to " + absolutePath);
        this.generator.generateInvoicePdf(data, targetFile);

        return null;

    }



}

