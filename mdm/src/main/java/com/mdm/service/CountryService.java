package com.mdm.service;

import com.mdm.dto.CountryDTO;
import com.mdm.mapper.CountryMapper;
import com.mdm.model.Country;
import com.mdm.repository.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {
     @Autowired
    private CountryRepository countryRepository;

    public List<CountryDTO> findAll(String name, String code) {
        List<Country> countries = countryRepository.findAll();
        return countries.stream()
                .filter(c -> (name == null || c.getName().equalsIgnoreCase(name)))
                .filter(c -> (code == null || c.getCode().equalsIgnoreCase(code)))
                .map(CountryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CountryDTO findById(Long id) {
        return countryRepository.findById(id).map(CountryMapper::toDTO).orElse(null);
    }

    @Transactional
    public CountryDTO create(CountryDTO dto) {
        if (countryRepository.findByCode(dto.getCode()).isPresent()) {
            throw new IllegalArgumentException("Country code already exists");
        }
        if (countryRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Country name already exists");
        }
        Country entity = CountryMapper.toEntity(dto);
        entity.setId(null);
        return CountryMapper.toDTO(countryRepository.save(entity));
    }

    public CountryDTO upsert(CountryDTO dto) {
        Optional<Country> existingOpt = countryRepository.findByName(dto.getName());

        Country country = existingOpt.orElseGet(Country::new);

        country.setName(dto.getName());
        country.setCode(dto.getCode());
        country.setRegion(dto.getRegion());
        country.setSubregion(dto.getSubregion());
        country.setCapital(dto.getCapital());
        country.setPopulation(dto.getPopulation());

        Country saved = countryRepository.save(country);
        return CountryMapper.toDTO(saved);
    }
    
    @Transactional
    public CountryDTO update(Long id, CountryDTO dto) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));
        if (!country.getCode().equals(dto.getCode()) && countryRepository.findByCode(dto.getCode()).isPresent()) {
            throw new IllegalArgumentException("Country code already exists");
        }
        if (!country.getName().equals(dto.getName()) && countryRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Country name already exists");
        }
        country.setName(dto.getName());
        country.setCode(dto.getCode());
        country.setRegion(dto.getRegion());
        country.setSubregion(dto.getSubregion());
        country.setCapital(dto.getCapital());
        country.setPopulation(dto.getPopulation());
        return CountryMapper.toDTO(countryRepository.save(country));
    }

    @Transactional
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }
} 