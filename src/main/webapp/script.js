const IMAGE_ARRAY = ['/images/young_pc.jpg', '/images/pico_fogata.JPG',
    '/images/pico_17.jpg','/images/pico_07.JPG','/images/killington.jpg',
    '/images/saona.jpg'];

function getRandomImage() {
    var gallery = document.getElementById('gallery_view');
    var length = IMAGE_ARRAY.length;
    var result = Math.floor(Math.random()*length);

    gallery.src = IMAGE_ARRAY[result];
}