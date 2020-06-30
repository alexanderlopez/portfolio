const IMAGE_ARRAY = ['/images/young_pc.jpg', '/images/pico_fogata.JPG',
    '/images/pico_17.jpg','/images/pico_07.JPG','/images/killington.jpg',
    '/images/saona.jpg'];

function getRandomImage() {
    var gallery = document.getElementById('gallery_view');
    var length = IMAGE_ARRAY.length;
    var result = Math.floor(Math.random()*length);

    gallery.src = IMAGE_ARRAY[result];
}

function getData() {
    fetch('/data').then(response => response.json()).then(respObj => {
        var comTable = document.getElementById('com_table');
        var newRow = comTable.insertRow(-1);

        var nameCell = newRow.insertCell(0);
        var commentCell = newRow.insertCell(1);

        nameCell.innerHTML = respObj.name;
        commentCell.innerHTML = respObj.comment;
    });
}