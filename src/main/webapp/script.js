const IMAGE_ARRAY = ['/images/young_pc.jpg', '/images/pico_fogata.JPG',
    '/images/pico_17.jpg','/images/pico_07.JPG','/images/killington.jpg',
    '/images/saona.jpg'];

function getRandomImage() {
    var gallery = document.getElementById('gallery_view');
    var length = IMAGE_ARRAY.length;
    var result = Math.floor(Math.random()*length);

    gallery.src = IMAGE_ARRAY[result];
}

function getComments() {
    fetch('/data').then(response => response.json()).then(commentList => {

        var table = document.getElementById('comment_table');

        commentList.forEach(entry => {
            var newRow = table.insertRow(1);

            var nameCell = newRow.insertCell(0);
            var commentCell = newRow.insertCell(1);

            nameCell.innerHTML = entry.name;
            commentCell.innerHTML = entry.comment;
        });
    });
}