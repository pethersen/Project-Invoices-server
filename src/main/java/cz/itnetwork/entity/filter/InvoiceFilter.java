package cz.itnetwork.entity.filter;

import lombok.Data;

@Data
public class InvoiceFilter {
    /**
     * The ID of the buyer for filtering invoices.
     */
    private Long buyerId;

    /**
     * The ID of the seller for filtering invoices.
     */
    private Long sellerId;

    /**
     * The product name for filtering invoices.
     */
    private String product;

    /**
     * The minimum price for filtering invoices.
     */
    private Long minPrice;

    /**
     * The maximum price for filtering invoices.
     */
    private Long maxPrice;

    /**
     * The maximum number of results to return. Defaults to 10 if not specified.
     */
    private Integer limit = 10;

}
