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
        String position = "".concat(countryName).concat(" ").concat(regionName).concat(" ").concat(cityName);
        model.addAttribute("position", position);

        // "weather_code":[95,95,2]
        model.addAttribute("imageName", getNameOfWeatherByNum(tempArrString[0]));

        model.addAttribute("dayToday", getWeatherByNum(tempArrString[0]));
        model.addAttribute("dayAfterDay", getWeatherByNum(tempArrString[1]));
        model.addAttribute("dayAfterTwoDays", getWeatherByNum(tempArrString[2]));

        // repair later
        model.addAttribute("TemperatureToday", -10 + "°");

        return "index";
    }
    Map<String, String[]> weatherList = new HashMap<>();
    public String getWeatherByNum(String num){
        return weatherList.get(num)[0];
    }
    public String getNameOfWeatherByNum(String num){
        return weatherList.get(num)[1];
    }
    public void setWeather(){
        weatherList.put("0", new String[]{"Clear sky", "Sky"});
        weatherList.put("1", new String[]{"Mainly clear", "Sky"});
        weatherList.put("2", new String[]{"Mainly clear", "Sky"});
        weatherList.put("3", new String[]{"Mainly clear", "Sky"});
        weatherList.put("45", new String[]{"Foggy", "Clouds"});
        weatherList.put("48", new String[]{"Foggy", "Clouds"});
        weatherList.put("51", new String[]{"Drizzle", "Clouds"});
        weatherList.put("53", new String[]{"Drizzle", "Clouds"});
        weatherList.put("55", new String[]{"Drizzle", "Clouds"});
        weatherList.put("56", new String[]{"Freezing", "Snowflake"});
        weatherList.put("57", new String[]{"Freezing", "Snowflake"});
        weatherList.put("61", new String[]{"Light rain","Rain"});
        weatherList.put("63", new String[]{"Moderate rain","Rain"});
        weatherList.put("65", new String[]{"High rain","Rain"});
        weatherList.put("66", new String[]{"Freezing Rain","Rain"});
        weatherList.put("67", new String[]{"Coldly freezing Rain","Rain"});
        weatherList.put("80", new String[]{"Light rain showers","Rain"});
        weatherList.put("81", new String[]{"Moderate rain showers","Rain"});
        weatherList.put("82", new String[]{"High rain showers","Rain"});
        weatherList.put("95", new String[]{"Light thunderstorm","Thunderstorm"});
        weatherList.put("96", new String[]{"Moderate thunderstorm","Thunderstorm"});
        weatherList.put("99", new String[]{"High thunderstorm","Thunderstorm"});
        weatherList.put("71", new String[]{"Light snow fall","Snow"});
        weatherList.put("73", new String[]{"Moderate snow fall","Snow"});
        weatherList.put("75", new String[]{"High snow fall","Snow"});
        weatherList.put("77", new String[]{"Snow grains","Snow"});
        weatherList.put("85", new String[]{"Snow showers","Snow"});
        weatherList.put("86", new String[]{"High snow showers","Snow"});
    }
}
