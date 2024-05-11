package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long>, JpaSpecificationExecutor<InvoiceEntity> {

    List<InvoiceEntity> findBySeller_IdentificationNumber(String identificationNumber);
    List<InvoiceEntity> findByBuyer_IdentificationNumber(String identificationNumber);
    @Query(value = "SELECT COUNT(*) AS invoiceCount FROM invoice", nativeQuery = true)
    int getInvoiceCount();

    @Query(value = "SELECT SUM(price) AS allTimeSum FROM invoice", nativeQuery = true)
    Long getAllTimeSum();

    @Query(value = "SELECT SUM(price) AS currentYearSum FROM invoice WHERE YEAR(issued) = YEAR(CURDATE())", nativeQuery = true)
    Long getCurrentYearSum();

}
