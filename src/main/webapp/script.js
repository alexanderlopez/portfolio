const IMAGE_ARRAY = ['/images/young_pc.jpg', '/images/pico_fogata.JPG',
    '/images/pico_17.jpg','/images/pico_07.JPG','/images/killington.jpg',
    '/images/saona.jpg'];

const CAPTION_ARRAY = ['Interacting with a personal computer while young.',
                       'Campfire in the middle of a valley [Valle del Tetero].',
                       'Fifteenth summit of the tallest mountain in the Dominican Republic.',
                       'First summit of the tallest mountain in the Dominican Republic.',
                       'Landscape at Killington (VT) summit.',
                       'Isla Saona (island to the south of the Dominican Republic).']

function getRandomImage() {
    var gallery = document.getElementById('gallery_view');
    var galleryText = document.getElementById('gallery_description');
    var length = IMAGE_ARRAY.length;
    var result = Math.floor(Math.random()*length);

    gallery.src = IMAGE_ARRAY[result];
    galleryText.innerText = CAPTION_ARRAY[result];
}

function clearTable() {
    document.getElementById('table_body').innerHTML = '';
}

function deleteComments() {
    fetch('/delete-data', {method: 'POST'}).then(response => {
        getComments()
    });
}

function getComments() {
    var fetchString = '/data';
    
    var maxComments = document.getElementById('max_comments');
    
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