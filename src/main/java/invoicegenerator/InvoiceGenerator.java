package invoicegenerator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class InvoiceGenerator {

    private String generateInvoiceHtml(InvoiceData data) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d. M. yyyy, HH:mm");

        String header = """
<html>
    <head>
        <style>
            body {
                font-family: sans-serif;
            }
            .invoice-datetime {
                font-style: italic;
            }
            p, table {
                margin-top: 24px;
            }
            table {
                width: 100%;
            }
            td, th {
                padding: 4px 8px;
            }
            thead th {
                border-bottom: 1px solid #CCC;
            }
            tfoot th, tfoot td {
                border-top: 1px solid #CCC;
            }
            .total {
                font-weight: bold;
            }
        </style>
    </head>
    <body>
                """;
        String footer = """
    </body>
</html>
                """;
        String body = String.format("""
        <h1>Invoice %s</h1>
        <p class="invoice-datetime">Generated on %s for Customer ID %s</p>
        <table>
            <tr>
                <td>
                    %s<br />
                    %s<br />
                    %s
                </td>
                <td>
                    %s<br />
                    %s<br />
                    %s
                </td>
            </tr>
        </table>
        <table cellspacing="0">
            <thead>
                <tr>
                    <th scope="col" align="left">Description</th>
                    <th scope="col" align="right">Price</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td align="left">
                        Charging %d kWh
                    </td>
                    <td align="right" class="total">
                        %.02f €
                    </td>
                </tr>
            </tbody>
        </table>
                """,
                data.invoiceId,
                dateTimeFormatter.format(data.invoiceDate),
                data.customerId,
                data.leftName,
                data.leftAddress1,
                data.leftAddress2,
                data.rightName,
                data.rightAddress1,
                data.rightAddress2,
                data.totalKwh,
                data.totalPrice
        );
        return header + body + footer;
    }

    public void generateInvoicePdf(InvoiceData data, File target) {
        String invoiceHtml = generateInvoiceHtml(data);
        Document document = Jsoup.parse(invoiceHtml, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        // return document;

        /*String outputHtml = "C:\\Users\\jingq\\Documents\\Testout.html";
        try (FileWriter outputWriter = new FileWriter(outputHtml)) {
            outputWriter.write(invoiceHtml);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try (OutputStream outputStream = new FileOutputStream(target)) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
