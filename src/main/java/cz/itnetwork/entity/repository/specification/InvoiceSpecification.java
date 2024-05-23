package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {

    private final InvoiceFilter invoiceFilter;

    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        // List to hold individual predicates for each filter condition
        List<Predicate> predicates = new ArrayList<>();

        // Check if buyerId is specified in the filter
        if (invoiceFilter.getBuyerId() != null) {
            // Joining PersonEntity with InvoiceEntity on buyerId
            Join<PersonEntity, InvoiceEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);
            // Adding a condition for matching buyerId
            predicates.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.ID), invoiceFilter.getBuyerId()));
        }

        // Check if sellerId is specified in the filter
        if (invoiceFilter.getSellerId() != null) {
            // Joining PersonEntity with InvoiceEntity on sellerId
            Join<PersonEntity, InvoiceEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);
            // Adding a condition for matching sellerId
            predicates.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.ID), invoiceFilter.getSellerId()));
        }

        // Check if product name is specified in the filter
        if (invoiceFilter.getProduct() != null) {
            // Adding a condition for case-insensitive partial match on product name
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(InvoiceEntity_.PRODUCT)),
                    String.format("%%%s%%", invoiceFilter.getProduct().toLowerCase())));
        }

        // Check if minimum price is specified in the filter
        if (invoiceFilter.getMinPrice() != null) {
            // Adding a condition for minimum price
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), invoiceFilter.getMinPrice()));
        }

        // Check if maximum price is specified in the filter
        if (invoiceFilter.getMaxPrice() != null) {
            // Adding a condition for maximum price
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), invoiceFilter.getMaxPrice()));
        }

        // Combining all predicates with logical AND
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
