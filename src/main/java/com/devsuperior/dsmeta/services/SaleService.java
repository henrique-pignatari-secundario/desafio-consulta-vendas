package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleSummaryDTO> saleSummaryFrom(String minDateStr, String maxDateStr){
		LocalDate maxDate = getMaxDate(maxDateStr);
		LocalDate minDate = getMinDate(minDateStr, maxDate);

		return repository.saleSummaryFrom(minDate, maxDate);
	}

	public Page<SaleReportDTO> saleReportFrom(String minDateStr, String maxDateStr, String sellerName, Pageable pageable){
		LocalDate maxDate = getMaxDate(maxDateStr);
		LocalDate minDate = getMinDate(minDateStr, maxDate);

		return repository.saleReportFrom(minDate, maxDate, sellerName, pageable);
	}

	private LocalDate getMaxDate(String maxDateStr){
		LocalDate maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		if(!maxDateStr.isBlank()){
			maxDate = LocalDate.parse(maxDateStr, dtf);
		}
		return maxDate;
	}

	private LocalDate getMinDate(String minDateStr, LocalDate referenceDate){
		LocalDate minDate = referenceDate.minusYears(1);
		if(!minDateStr.isBlank()){
			minDate = LocalDate.parse(minDateStr, dtf);
		}
		return minDate;
	}
}
