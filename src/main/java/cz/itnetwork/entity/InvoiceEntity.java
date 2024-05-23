package cz.itnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "invoice")
@Getter
@Setter
public class InvoiceEntity {

    /**
     * The unique identifier for the invoice.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The invoice number associated with this invoice.
     */
    @Column(nullable = false)
    private String invoiceNumber;

    /**
     * The date when the invoice was issued.
     */
    @Column(nullable = false)
    private LocalDate issued;

    /**
     * The due date for payment of the invoice.
     */
    @Column(nullable = false)
    private LocalDate dueDate;

    /**
     * The name or description of the product being invoiced.
     */
    @Column(nullable = false)
    private String product;

    /**
     * The price of the product/service on the invoice.
     */
    @Column(nullable = false)
    private long price;

    /**
     * The value-added tax (VAT) applied to the invoice.
     */
    @Column(nullable = false)
    private int vat;

    /**
     * Any additional notes or comments related to the invoice.
     */
    private String note;

    /**
     * The buyer associated with this invoice.
     * Many invoices can be associated with a single buyer.
     */
    @ManyToOne
    private PersonEntity buyer;

    /**
     * The seller associated with this invoice.
     * Many invoices can be associated with a single seller.
     */
    @ManyToOne
    private PersonEntity seller;
}
