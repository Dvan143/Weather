var lat = 0
var lon = 0
    navigator.geolocation.getCurrentPosition(function (position) {
        lat = position.coords.latitude;
        lon = position.coords.longitude;
        if(window.location.href === "http://localhost:8080/") window.location.href = "/getWeather?lat=" + lat  + "&lon=" + lon;
    })