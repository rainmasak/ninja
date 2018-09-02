function onLoad() {

    getFiles();

    document.getElementById("file").onchange = function(e) {
    	
        e.preventDefault();
        var file = document.querySelector('[type=file]').files[0];
        var formData = new FormData();
        
        if (!file.type.match(/image.*/)) {
            createMessage("FORBIDDEN", "Selected file is not an image!", "");
            document.getElementById("file").value = '';
            return;
        }

        if (file.size > 3000000) {
            var fileSizeStr = (file.size / 1000000) + '';
            var fileSize = fileSizeStr.substring(0, 4)
            createMessage("FORBIDDEN", "file size " + fileSize + " mb exceeds maximum allowed filesize of 3 mb!", "");
            document.getElementById("file").value = '';
            return;
        }

        if (!file) {
            alert("Please select file!")
            return;
        }
        
        formData.append('file', file);

        ajax('POST', '/photo', formData, function(response) {
            document.getElementById("file").value = '';
            if (response.status === 'OK') {
                getFiles();
            }
            if (response.status === 'OK' || response.status === 'FORBIDDEN') {
                createMessage(response.status, response.message, response.fileName);
            }
        });
    };
}

function getFiles() {

    var tableBody = document.getElementById("photoLink");
    tableBody.innerHTML = '';

    var tr = document.createElement('tr');
    var th1 = document.createElement('td');
    th1.innerHTML = "Image";
    var th2 = document.createElement('td');
    th2.innerHTML = "Preview";
    var th3 = document.createElement('td');
    th3.innerHTML = "Delete";
    tr.appendChild(th1);
    tr.appendChild(th2);
    tr.appendChild(th3);
    tr.className = "thead";
    tableBody.appendChild(tr);

    ajax('GET', '/files', '', function(data) {
        for (var file in data) {
            f = data[file]
            mydiv = document.getElementById("photoLink");
            var preview = document.createElement('img');
            preview.style = 'width: 50px'
            preview.id = 'photo_' + f;
            var tr = document.createElement('tr');
            tr.id = "tr_" + f;
            var aTag = document.createElement('a');
            var td1 = document.createElement('td');
            var td2 = document.createElement('td');
            var td3 = document.createElement('td');

            aTag.setAttribute('href', "photo.html?file=" + f);
            aTag.innerHTML = f;
            aTag.id = f
            aTag.style.padding = '10px';

            ajax('GET', '/photo?fileName=' + f, '', function(data2) {
                var pre = document.getElementById("photo_" + data2.fileName);
                pre.src = "data:image/png;base64," + data2.image;
            });

            var db = document.createElement('button');
            db.className = "delBtn"
            db.onclick = function() {
                ajax('DELETE', '/photo', f, function(data) {
                    var item2remove = document.getElementById("tr_" + data.fileName);
                    mydiv.removeChild(item2remove);
                    createMessage(data.status, data.message)
                    getFiles()
                });
            }
            db.innerHTML = 'delete';
            db.id = 'del' + f;
            db.style.padding = '2px';

            td1.appendChild(aTag);
            td2.appendChild(preview);
            td3.appendChild(db);
            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            mydiv.appendChild(tr);
        }

    });
}

function closeResponse() {
    var msg = document.getElementById('response').style.visibility = 'hidden';
    document.getElementById("overlay").style.display = "none";
    if (highlighted) {
        highlighted.style.background = 'none';
    }
}

function createMessage(status, message, fileName) {
    var msg = document.getElementById('response');
    var msgP = msg.children[1];

    document.getElementById("overlay").style.display = "block";
    switch (status) {
        case 'OK':
            msg.style.background = '#4CAF50';
            msgP.innerHTML = status + ', ' + message;
            msg.style.visibility = 'visible';
            break;
        case 'FORBIDDEN':
            msg.style.background = '#f44336';
            msgP.innerHTML = status + ', ' + message;
            msg.style.visibility = 'visible';
            if (fileName) {
                highlightName(fileName);
            }
            break;
    }
}

var highlighted;
function highlightName(fileName) {
	var counter = 0;
    highlighted = document.getElementById(fileName);
    highlighted.style.background = '#f44336';
    highlighted.style.opacy = 0.83;
}

function fetchImage() {
    var searchString = window.location.search;
    var fileName = searchString.split("=")[1];
    ajax('GET', '/photo?fileName=' + fileName, '', function(data) {
        document.getElementById("ItemPreview").src = "data:image/png;base64," + data.image;
    });
}

function ajax(method, url, data, success) {
    jQuery
        .ajax({
            url: url,
            method: method,
            dataType: 'json',
            cache: false,
            contentType: false,
            processData: false,
            type: method,
            data: data,
            success: success
        });
}