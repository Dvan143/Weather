package org.example.weather;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
    @GetMapping("/getWeather")
    public String index(@RequestParam String lat, @RequestParam String lon, Model model) {

        // Sending request to get Weather
        String uri = "https://api.open-meteo.com/v1/forecast?latitude=" + lat +"&longitude=" + lon + "&daily=weather_code,temperature_2m_max,temperature_2m_min,wind_speed_10m_max&forecast_days=3";
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(uri, String.class);
        setWeather();
        String tempStr = json.split("weather_code\":\\[")[1].split("]")[0];
        String[] tempArrString = tempStr.split(",");

        uri = "https://nominatim.openstreetmap.org/reverse?format=json&lat="+ lat +"&lon=" + lon;
        String json2 = restTemplate.getForObject(uri, String.class);
        String countryName = json2.split("country\":\"")[1].split("\"")[0];
        String regionName = json2.split("state\":\"")[1].split("\"")[0];
        String cityName = json2.split("town\":\"")[1].split("\"")[0];
        model.addAttribute("countryName", countryName);
        model.addAttribute("regionName", regionName);
        model.addAttribute("cityName", cityName);
        // "weather_code":[95,95,2]

        model.addAttribute("dayToday", getWeatherByNum(tempArrString[0]));
        model.addAttribute("dayAfterDay", getWeatherByNum(tempArrString[1]));
        model.addAttribute("dayAfterTwoDays", getWeatherByNum(tempArrString[2]));

        return "index";
    }
    Map<String,String> weatherList = new HashMap<>();
    public String getWeatherByNum(String num){
        return weatherList.get(num);
    }
    public void setWeather(){
        weatherList.put("0","Clear sky");
        weatherList.put("1","Mainly clear");
        weatherList.put("2","Mainly clear");
        weatherList.put("3","Mainly clear");
        weatherList.put("45","Fog");
        weatherList.put("48","Fog");
        weatherList.put("51","Drizzle");
        weatherList.put("53","Drizzle");
        weatherList.put("55","Drizzle");
        weatherList.put("56","Freezing");
        weatherList.put("57","Freezing");
        weatherList.put("61","light rain");
        weatherList.put("63","Moderate rain");
        weatherList.put("65","High rain");
        weatherList.put("66","Freezing Rain");
        weatherList.put("67","Coldly freezing Rain");
        weatherList.put("71","Light snow fall");
        weatherList.put("73","Moderate snow fall");
        weatherList.put("75","High snow fall");
        weatherList.put("77","Snow grains");
        weatherList.put("80","Light rain showers");
        weatherList.put("81","Moderate rain showers");
        weatherList.put("82","High rain showers");
        weatherList.put("85","Snow showers");
        weatherList.put("86","High snow showers");
        weatherList.put("95","Light thunderstorm");
        weatherList.put("96","Moderate thunderstorm");
        weatherList.put("99","High thunderstorm");
    }
}
