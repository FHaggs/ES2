package com.dem.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Map;

@FeignClient(name = "mdmClient", url = "${mdm.api.url}")
public interface MdmClient {
    @GetMapping("/api/countries")
    Map<String, Object> getCountry(@PathVariable Long id);

    @PostMapping("/api/countries")
    Map<String, Object> createCountry(@RequestBody Map<String, Object> country);

    @PostMapping("/api/countries/upsert/batch")
    List<Map<String, Object>> createOrUpdateCountries(@RequestBody List<Map<String, Object>> countries);
    
} 