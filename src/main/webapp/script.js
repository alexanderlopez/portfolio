const IMAGE_ARRAY = ['/images/young_pc.jpg', '/images/pico_fogata.JPG',
    '/images/pico_17.jpg','/images/pico_07.JPG','/images/killington.jpg',
    '/images/saona.jpg'];

function getRandomImage() {
    var gallery = document.getElementById('gallery_view');
    var length = IMAGE_ARRAY.length;
    var result = Math.floor(Math.random()*length);

    gallery.src = IMAGE_ARRAY[result];

    console.log(result, IMAGE_ARRAY[result]);
}

function addServletText() {
    fetch('/data').then((response) => response.text()).then(response_text => {
        document.getElementById('fetch_test').innerHTML = response_text;
        console.log(response_text);
    });
    console.log('Done');
}