var lat = 0
var lon = 0
navigator.geolocation.getCurrentPosition(function (position) {
    lat = position.coords.latitude;
    lon = position.coords.longitude;
    if(window.location.href == "") window.location.href = "?latitude=" + lat  + "longitude=" + lon;
});