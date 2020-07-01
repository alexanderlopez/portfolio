const IMAGE_ARRAY = ['/images/young_pc.jpg', '/images/pico_fogata.JPG',
    '/images/pico_17.jpg','/images/pico_07.JPG','/images/killington.jpg',
    '/images/saona.jpg'];

function getRandomImage() {
    var gallery = document.getElementById('gallery_view');
    var length = IMAGE_ARRAY.length;
    var result = Math.floor(Math.random()*length);

    gallery.src = IMAGE_ARRAY[result];
}

function clearTable() {
    document.getElementById('table_body').innerHTML = '';
}

function getComments() {
    var fetchString = '/data';
    
    var maxComments = document.getElementById('max_comments');
    console.log(maxComments.value);
    
    if (parseInt(maxComments.value) > 0) {
        fetchString = fetchString.concat('?max_comments=',maxComments.value);
    }

    clearTable();

    fetch(fetchString).then(response => response.json()).then(commentList => {

        var table = document.getElementById('table_body');

        commentList.forEach(entry => {
            var newRow = table.insertRow(0);

            var nameCell = newRow.insertCell(0);
            var commentCell = newRow.insertCell(1);

            nameCell.innerHTML = entry.name;
            commentCell.innerHTML = entry.comment;
        });
    });
}