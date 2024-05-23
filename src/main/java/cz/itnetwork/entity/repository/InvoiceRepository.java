package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long>, JpaSpecificationExecutor<InvoiceEntity> {

    /**
     * Finds a list of invoices based on the seller's identification number.
     *
     * @param identificationNumber the seller's identification number
     * @return a list of {@link InvoiceEntity} where the seller's identification number matches the given number
     */
    List<InvoiceEntity> findBySeller_IdentificationNumber(String identificationNumber);

    /**
     * Finds a list of invoices based on the buyer's identification number.
     *
     * @param identificationNumber the buyer's identification number
     * @return a list of {@link InvoiceEntity} where the buyer's identification number matches the given number
     */
    List<InvoiceEntity> findByBuyer_IdentificationNumber(String identificationNumber);

    /**
     * Gets the total count of invoices.
     *
     * @return the total number of invoices as an integer
     */
    @Query(value = "SELECT COUNT(*) AS invoiceCount FROM invoice", nativeQuery = true)
    int getInvoiceCount();

    /**
     * Gets the sum of all invoice prices.
     *
     * @return the sum of all invoice prices as a {@link Long}
     */
    @Query(value = "SELECT SUM(price) AS allTimeSum FROM invoice", nativeQuery = true)
    Long getAllTimeSum();

    /**
     * Gets the sum of invoice prices for the current year.
     *
     * @return the sum of invoice prices for the current year as a {@link Long}
     */
    @Query(value = "SELECT SUM(price) AS currentYearSum FROM invoice WHERE YEAR(issued) = YEAR(CURDATE())", nativeQuery = true)
    Long getCurrentYearSum();
}
