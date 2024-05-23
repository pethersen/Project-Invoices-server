package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    /**
     * The unique identifier for the invoice.
     * Mapped to the JSON property "_id".
     */
    @JsonProperty("_id")
    private Long id;

    /**
     * The invoice number.
     */
    private String invoiceNumber;

    /**
     * The date when the invoice was issued.
     * Formatted as "yyyy-MM-dd".
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issued;

    /**
     * The due date for the invoice.
     * Formatted as "yyyy-MM-dd".
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    /**
     * The product or service described in the invoice.
     */
    private String product;

    /**
     * The price of the product or service.
     */
    private long price;

    /**
     * The VAT (Value Added Tax) percentage applied to the invoice.
     */
    private int vat;

    /**
     * Additional notes related to the invoice.
     */
    private String note;

    /**
     * The buyer's information.
     */
    private PersonDTO buyer;

    /**
     * The seller's information.
     */
    private PersonDTO seller;

}
