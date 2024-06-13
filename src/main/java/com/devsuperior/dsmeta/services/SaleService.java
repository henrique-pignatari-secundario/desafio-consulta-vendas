package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.controllers.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
		LocalDate maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		if(!maxDateStr.isBlank()){
			maxDate = LocalDate.parse(maxDateStr, dtf);
		}

		LocalDate minDate = maxDate.minusYears(1);
		if(!minDateStr.isBlank()){
			minDate = LocalDate.parse(minDateStr, dtf);
		}

		if(minDate.isAfter(maxDate)) throw new IllegalArgumentException("A data minima deve ser menor do que a maxima");

		return repository.saleSummaryFrom(minDate, maxDate);
	}
}
