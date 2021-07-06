const firePixelsArray = [];
const fireWidth = 200;
const fireHeight = 200;

const fireColorsPalette = [
    {
        "r":7,
        "g":7,
        "b":7
    },
    {
        "r":31,
        "g":7,
        "b":7
    },
    {
        "r":47,
        "g":15,
        "b":7
    },
    {
        "r":71,
        "g":15,
        "b":7
    },
    {
        "r":87,
        "g":23,
        "b":7
    },
    {
        "r":103,
        "g":31,
        "b":7
    },
    {
        "r":119,
        "g":31,
        "b":7
    },
    {
        "r":143,
        "g":39,
        "b":7
    },
    {
        "r":159,
        "g":47,
        "b":7
    },
    {
        "r":175,
        "g":63,
        "b":7
    },
    {
        "r":191,
        "g":71,
        "b":7
    },
    {
        "r":199,
        "g":71,
        "b":7
    },
    {
        "r":223,
        "g":79,
        "b":7
    },
    {
        "r":223,
        "g":87,
        "b":7
    },
    {
        "r":223,
        "g":87,
        "b":7
    },
    {
        "r":215,
        "g":95,
        "b":7
    },
    {
        "r":215,
        "g":95,
        "b":7
    },
    {
        "r":215,
        "g":103,
        "b":15
    },
    {
        "r":207,
        "g":111,
        "b":15
    },
    {
        "r":207,
        "g":119,
        "b":15
    },
    {
        "r":207,
        "g":127,
        "b":15
    },
    {
        "r":207,
        "g":135,
        "b":23
    },
    {
        "r":199,
        "g":135,
        "b":23
    },
    {
        "r":199,
        "g":143,
        "b":23
    },
    {
        "r":199,
        "g":151,
        "b":31
    },
    {
        "r":191,
        "g":159,
        "b":31
    },
    {
        "r":191,
        "g":159,
        "b":31
    },
    {
        "r":191,
        "g":167,
        "b":39
    },
    {
        "r":191,
        "g":167,
        "b":39
    },
    {
        "r":191,
        "g":175,
        "b":47
    },
    {
        "r":183,
        "g":175,
        "b":47
    },
    {
        "r":183,
        "g":183,
        "b":47
    },
    {
        "r":183,
        "g":183,
        "b":55
    },
    {
        "r":207,
        "g":207,
        "b":111
    },
    {
        "r":223,
        "g":223,
        "b":159
    },
    {
        "r":239,
        "g":239,
        "b":199
    },
    {
        "r":255,
        "g":255,
        "b":255
    }
];


function start() {
    createFireDataStructure();
    createFireSource();
    renderFire();
    setInterval(calculateFirePropagation, 50);
}

function createFireDataStructure() {
    const canvas = document.querySelector('#fireCanvas');
    const table = document.createElement('table');

    for(let column = 0; column <fireWidth; column++){
        const tr = document.createElement('tr');
        for(let row = 0; row < fireHeight; row++){
            const pixelIndex = column + (fireWidth * row);
            const td = document.createElement('td');
            td.classList.add('pixel');
            td.dataset.intensity = 0;
            tr.appendChild(td);
            firePixelsArray.push(td);
        }
        table.appendChild(tr);
    }
    canvas.appendChild(table);
}

function calculateFirePropagation() {
    for(let column = 0; column <fireWidth; column++){
        for(let row = 0; row < fireHeight; row++){
            const pixelIndex = column + (fireWidth * row);
            updateFireIntensityPerPixel(pixelIndex);
        }
    }
    renderFire();
}

function updateFireIntensityPerPixel(currentPixelIndex){
    const belowPixelIndex = currentPixelIndex + fireWidth;
    if(belowPixelIndex >= fireWidth * fireHeight){
        return;
    }

    const direction = document.querySelector('input[name="wind-direction"]:checked').value;
    const fireLiveness = document.querySelector('input[type="range"]').value;


    let decay = Math.floor(Math.random() * fireLiveness);
    const belowPixelFireIntensity = firePixelsArray[belowPixelIndex].dataset.intensity;

    let newFireIntensity = belowPixelFireIntensity - decay;
    if(newFireIntensity<0){
        newFireIntensity = 0;
    }

    
    if(direction == 'l'){
        decay = decay * -1;
    }

    firePixelsArray[currentPixelIndex+decay].dataset.intensity = newFireIntensity;
}

function renderFire() {
    //let html = '<table cellpadding="0" cellspacing="0">';
    for(let row = 0; row < fireHeight; row++){
        //html += '<tr>';

        for(let column = 0; column <fireWidth; column++){
            const pixelIndex = column + (fireWidth * row);
            const fireIntensity = firePixelsArray[pixelIndex].dataset.intensity;
            firePixelsArray[pixelIndex].style = `background-color: rgb(${ getColorForIntensity(fireIntensity) })`;

            //html += `<td class="pixel" style="background-color: rgb(${ getColorForIntensity(fireIntensity) })">`;
            // html += fireIntensity;
            //html += '</td>';
        }

        //html += '</tr>';
    }
    //html += '</table>';

    //document.querySelector('#fireCanvas').innerHTML = html;
}

function getColorForIntensity(fireIntensity){
    const color = fireColorsPalette[fireIntensity];
    return `${color.r},${color.g},${color.b}`;
}

function createFireSource() {
    for(let column = 0; column < fireWidth; column++) {
        const overflowFixelIndex = fireWidth * fireHeight;
        const pixelIndex = (overflowFixelIndex-fireWidth) + column;
        firePixelsArray[pixelIndex].dataset.intensity = 36;
    }
}

start();