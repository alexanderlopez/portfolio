var map;
function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 18.471095, lng: -69.924035 },
    zoom: 12
  });
}