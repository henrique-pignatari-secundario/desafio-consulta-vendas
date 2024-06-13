package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.controllers.SaleSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT new com.devsuperior.dsmeta.controllers.SaleSummaryDTO(seller.name, SUM(sale.amount)) " +
            "FROM Sale sale " +
            "JOIN sale.seller seller " +
            "WHERE sale.date >= :minDate " +
            "AND sale.date < :maxDate " +
            "GROUP BY seller.name")
    List<SaleSummaryDTO> saleSummaryFrom(LocalDate minDate, LocalDate maxDate);
}
