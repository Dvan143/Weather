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
        json = "{\"latitude\":43.875,\"longitude\":41.5625,\"generationtime_ms\":0.05805492401123047,\"utc_offset_seconds\":0,\"timezone\":\"GMT\",\"timezone_abbreviation\":\"GMT\",\"elevation\":931.0,\"daily_units\":{\"time\":\"iso8601\",\"weather_code\":\"wmo code\",\"temperature_2m_max\":\"°C\",\"temperature_2m_min\":\"°C\",\"wind_speed_10m_max\":\"km/h\"},\"daily\":{\"time\":[\"2024-11-11\",\"2024-11-12\",\"2024-11-13\"],\"weather_code\":[45,45,45],\"temperature_2m_max\":[10.9,11.4,11.9],\"temperature_2m_min\":[-3.8,-3.0,-3.6],\"wind_speed_10m_max\":[6.0,10.5,7.7]}}";
        String tempStr = json.split("weather_code\":\\[")[1].split("]")[0];
        String[] tempArrString = tempStr.split(",");
        int[] tempInt = new int[3];
        for (int i = 0; i < 3; i++) {
            tempInt[i] = Integer.parseInt(tempArrString[i]);
        }

        uri = "https://nominatim.openstreetmap.org/reverse?format=json&lat="+ lat +"&lon=" + lon;
        String json2 = restTemplate.getForObject(uri, String.class);
        String CountryName = json2.split("country\":\"")[1].split("\"")[0];
        model.addAttribute("CountryName", CountryName);
        // "weather_code":[95,95,2]
        String debug = json.split(",\"")[15];
        String temp[] = debug.split(":");
        String temp2 = temp[1];
        temp2 = temp2.replace("[","");
        temp2 = temp2.replace("]","");
        String svo2[] = temp2.split(",");
        int[] temp3 = new int[3];
        for (int i = 0; i < 3; i++) {
            temp3[i] = Integer.parseInt(svo2[i]);
        }

        model.addAttribute("dayToday", getWeatherByNum(temp3[0]));
        model.addAttribute("dayAfterDay", getWeatherByNum(temp3[1]));
        model.addAttribute("dayAfterTwoDays", getWeatherByNum(temp3[2]));
        model.addAttribute("result", json);

        return "index";
    }
    Map<Integer,String> weatherList = new HashMap<>();
    public String getWeatherByNum(int num){
        return weatherList.get(num);
    }
    public void setWeather(){
        weatherList.put(0,"Clear sky");
        weatherList.put(1,"Mainly clear");
        weatherList.put(2,"Mainly clear");
        weatherList.put(3,"Mainly clear");
        weatherList.put(45,"Fog");
        weatherList.put(48,"Fog");
        weatherList.put(51,"Drizzle");
        weatherList.put(53,"Drizzle");
        weatherList.put(55,"Drizzle");
        weatherList.put(56,"Freezing");
        weatherList.put(57,"Freezing");
        weatherList.put(61,"light rain");
        weatherList.put(63,"Moderate rain");
        weatherList.put(65,"High rain");
        weatherList.put(66,"Freezing Rain");
        weatherList.put(67,"Coldly freezing Rain");
        weatherList.put(71,"Light snow fall");
        weatherList.put(73,"Moderate snow fall");
        weatherList.put(75,"High snow fall");
        weatherList.put(77,"Snow grains");
        weatherList.put(80,"Light rain showers");
        weatherList.put(81,"Moderate rain showers");
        weatherList.put(82,"High rain showers");
        weatherList.put(85,"Snow showers");
        weatherList.put(86,"High snow showers");
        weatherList.put(95,"Light thunderstorm");
        weatherList.put(96,"Moderate thunderstorm");
        weatherList.put(99,"High thunderstorm");
    }
}
