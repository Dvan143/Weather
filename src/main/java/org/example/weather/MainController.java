package org.example.weather;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class MainController {
    @GetMapping
    public String index() {
        return "index";
    }
    public String index(@RequestParam String lat, @RequestParam String lon, Model model) {
        // Sending request to get Weather
        final String uri = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&daily=weather_code,temperature_2m_max,temperature_2m_min&wind_speed_unit=kn&forecast_days=3";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        model.addAttribute("result", result);
        return "index";
    }
}
