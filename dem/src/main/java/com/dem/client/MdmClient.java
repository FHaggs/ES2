package com.dem.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Map;

@FeignClient(name = "mdmClient", url = "${mdm.api.url}")
public interface MdmClient {
    @PostMapping("/api/countries")
    Map<String, Object> createCountry(@RequestBody Map<String, Object> country);

    @PostMapping("/api/countries/batch")
    List<Map<String, Object>> createCountries(@RequestBody List<Map<String, Object>> countries);
} 