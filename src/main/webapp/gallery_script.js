var mapElement;
var positionArray = [{lat: 43.605, lng: -72.818}, {lat: 10, lng: 10}, {lat: 10.5, lng: 10.5}, {lat: 18.988, lng: -70.928},
                     {lat: 18.124, lng: -68.656}, {lat: 18.476, lng: -69.890}];
var markerArray = []

function initMap() {
    var mapStyle = [
        {
            featureType: 'poi.business',
            stylers: [{ visibility: 'off' }]
        },
        {
            featureType: 'transit',
            elementType: 'labels.icon',
            stylers: [{ visibility: 'off' }]
        }
    ];

    var mapOptions = {
        center: new google.maps.LatLng(18.9, -70.2),
        gestureHandling: 'cooperative',
        zoom: 8,
        mapTypeId: 'terrain',
        streetViewControl: false,
        mapTypeControl: false,
        styles: mapStyle
    };

    mapElement = new google.maps.Map(document.getElementById('map'), mapOptions);

    initMarkers();
}

function initMarkers() {
    markerOptions = {
        map: mapElement,
        visible: true
    };

    positionArray.forEach((pos, index) => {
        var tempMarker = new google.maps.Marker(markerOptions);
        tempMarker.setTitle("Image " + (index + 1));
        tempMarker.setPosition(pos);
        markerArray.push(tempMarker);
    });
}

function doOverlay(id) {
    var overlay = document.getElementById(id);
    var displayMode = overlay.style.display;

    if (displayMode == 'flex') {
        overlay.style.display = 'none';
    }
    else {
        overlay.style.display = 'flex';
    }
}