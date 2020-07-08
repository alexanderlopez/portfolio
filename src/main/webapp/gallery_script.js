var map;
function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 18.471095, lng: -69.924035 },
        zoom: 12
    });
}

function doOverlay(id) {
    var overlay = document.getElementById(id);
    var displayMode = overlay.style.display;

    if (displayMode == "flex") {
        overlay.style.display = "none";
    }
    else {
        overlay.style.display = "flex";
    }
}