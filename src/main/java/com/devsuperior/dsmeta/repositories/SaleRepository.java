package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(seller.name, SUM(sale.amount)) " +
            "FROM Sale sale " +
            "JOIN sale.seller seller " +
            "WHERE sale.date >= :minDate " +
            "AND sale.date <= :maxDate " +
            "GROUP BY seller.name")
    List<SaleSummaryDTO> saleSummaryFrom(LocalDate minDate, LocalDate maxDate);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(sale.id, sale.date, sale.amount, seller.name) " +
            "FROM Sale sale " +
            "JOIN sale.seller seller " +
            "WHERE sale.date >= :minDate " +
            "AND sale.date <= :maxDate " +
            "AND UPPER(seller.name) LIKE UPPER(CONCAT('%', :sellerName,'%'))")
    Page<SaleReportDTO> saleReportFrom(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable);
}
