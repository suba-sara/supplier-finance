package com.hcl.capstoneserver.invoice.repositories;

import com.hcl.capstoneserver.invoice.CurrencyType;
import com.hcl.capstoneserver.invoice.InvoiceStatus;
import com.hcl.capstoneserver.invoice.dto.InvoiceSearchCriteriaDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class InvoiceCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public InvoiceCriteriaRepository(
            EntityManager entityManager
    ) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    private Predicate _getPredicate(InvoiceSearchCriteriaDTO invoiceSearchCriteriaDTO, Root<Invoice> invoiceRoot) {
        List<Predicate> predicateList = new ArrayList<>();

        if (Objects.nonNull(invoiceSearchCriteriaDTO.getClientId())) {
            predicateList.add(criteriaBuilder.equal(
                    invoiceRoot.get("client").get("clientId"),
                    invoiceSearchCriteriaDTO.getClientId()
            ));
        }

        if (Objects.nonNull(invoiceSearchCriteriaDTO.getSupplierId())) {
            predicateList.add(criteriaBuilder.equal(
                    invoiceRoot.get("supplier").get("supplierId"),
                    invoiceSearchCriteriaDTO.getSupplierId()
            ));
        }

        if (Objects.nonNull(invoiceSearchCriteriaDTO.getInvoiceId())) {
            predicateList.add(criteriaBuilder.equal(
                    invoiceRoot.get("invoiceId"),
                    invoiceSearchCriteriaDTO.getInvoiceId()
            ));
        }

        if (Objects.nonNull(invoiceSearchCriteriaDTO.getInvoiceNumber())) {
            predicateList.add(criteriaBuilder.equal(
                    invoiceRoot.get("invoiceNumber"),
                    invoiceSearchCriteriaDTO.getInvoiceNumber()
            ));
        }

        if (Objects.nonNull(invoiceSearchCriteriaDTO.getDateFrom()) && Objects.nonNull(invoiceSearchCriteriaDTO.getDateTo())) {
            predicateList.add(criteriaBuilder.between(
                    invoiceRoot.get("invoiceDate"),
                    invoiceSearchCriteriaDTO.getDateFrom(),
                    invoiceSearchCriteriaDTO.getDateTo()
            ));
        } else if (Objects.nonNull(invoiceSearchCriteriaDTO.getDateFrom())) {
            predicateList.add(criteriaBuilder.equal(
                    invoiceRoot.get("invoiceDate"),
                    invoiceSearchCriteriaDTO.getDateFrom()
            ));
        }

        if (Objects.nonNull(invoiceSearchCriteriaDTO.getAgeing())) {
            predicateList.add(criteriaBuilder.equal(
                    invoiceRoot.get("invoiceDate"),
                    LocalDate.now().plusDays(-invoiceSearchCriteriaDTO.getAgeing())
            ));
        }

        if (Objects.nonNull(invoiceSearchCriteriaDTO.getStatus())) {
            for (InvoiceStatus status : invoiceSearchCriteriaDTO.getStatus()) {
                predicateList.add(criteriaBuilder.equal(invoiceRoot.get("status"), status));
            }
        }

        // hide uploaded invoices from all other users
        if (invoiceSearchCriteriaDTO.getFetchUploaded() == null) {
            predicateList.add(criteriaBuilder.notEqual(invoiceRoot.get("status"), 0));
        }


        if (Objects.nonNull(invoiceSearchCriteriaDTO.getCurrencyType())) {
            for (CurrencyType currency : invoiceSearchCriteriaDTO.getCurrencyType()) {
                predicateList.add(criteriaBuilder.equal(invoiceRoot.get("currencyType"), currency));
            }
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

    private void _setOrder(
            InvoiceSearchCriteriaDTO invoiceSearchCriteriaDTO,
            CriteriaQuery<Invoice> criteriaQuery,
            Root<Invoice> invoiceRoot
    ) {
        if (invoiceSearchCriteriaDTO.getSortDirection().isAscending()) {
            criteriaQuery.orderBy(criteriaBuilder.asc(invoiceRoot.get(invoiceSearchCriteriaDTO.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(invoiceRoot.get(invoiceSearchCriteriaDTO.getSortBy())));
        }
    }

    private Pageable _getPageable(InvoiceSearchCriteriaDTO invoiceSearchCriteriaDTO) {
        Sort sort = Sort.by(invoiceSearchCriteriaDTO.getSortDirection(), invoiceSearchCriteriaDTO.getSortBy());
        return PageRequest.of(invoiceSearchCriteriaDTO.getPageIndex(), invoiceSearchCriteriaDTO.getPageSize(), sort);
    }

    private Long _getInvoiceCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Invoice> countRoot = countQuery.from(Invoice.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    public Page<Invoice> findAllWithFilters(InvoiceSearchCriteriaDTO invoiceSearchCriteriaDTO) {
        CriteriaQuery<Invoice> criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
        Root<Invoice> invoiceRoot = criteriaQuery.from(Invoice.class);

        Predicate predicate = _getPredicate(invoiceSearchCriteriaDTO, invoiceRoot);
        criteriaQuery.where(predicate);
        _setOrder(invoiceSearchCriteriaDTO, criteriaQuery, invoiceRoot);

        TypedQuery<Invoice> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(invoiceSearchCriteriaDTO.getPageIndex() * invoiceSearchCriteriaDTO.getPageSize());
        typedQuery.setMaxResults(invoiceSearchCriteriaDTO.getPageSize());

        Pageable pageable = _getPageable(invoiceSearchCriteriaDTO);
        long invoiceCount = _getInvoiceCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, invoiceCount);
    }
}
