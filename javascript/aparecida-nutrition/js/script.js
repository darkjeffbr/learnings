function calcImc(weight, height){
    return (weight / Math.pow(height, 2)).toFixed(2);
}

function getPatientFromTr(patientTr){
    var name = patientTr.querySelector(".info-nome");
    var weight = patientTr.querySelector(".info-peso");
    var height = patientTr.querySelector(".info-altura");
    var fat = patientTr.querySelector(".info-gordura");
    var imc = patientTr.querySelector(".info-imc");

    return {
        "name": name.textContent,
        "weight": weight.textContent,
        "height": height.textContent,
        "fat": fat.textContent,
        "imc": imc.textContent
    }
}

function patientToTr(patient){
    var nameTd = document.createElement("td");
    nameTd.classList.add("info-nome");
    nameTd.textContent = patient.name;

    var weightTd = document.createElement("td");
    weightTd.classList.add("info-peso");
    weightTd.textContent = patient.weight;

    var heightTd = document.createElement("td");
    heightTd.classList.add("info-altura");
    heightTd.textContent = patient.height;

    var fatTd = document.createElement("td");
    fatTd.classList.add("info-gordura");
    fatTd.textContent = patient.fat;

    var imcTd = document.createElement("td");
    imcTd.classList.add("info-imc");
    imcTd.textContent = patient.imc;

    var tr = document.createElement("tr");
    tr.appendChild(nameTd);
    tr.appendChild(weightTd);
    tr.appendChild(heightTd);
    tr.appendChild(fatTd);
    tr.appendChild(imcTd);

    tr.classList.add("paciente");

    return tr;
}

function isPatientValid(patient) {
    if(patient.weight <= 0 || patient.weight > 1000){
        console.log(patient.name + " has invalid weight ("+patient.weight+")");
        return false;
    }
    if(patient.height <= 0 || patient.height >= 3){
        console.log(patient.name + " has invalid height ("+patient.height+")");
        return false;
    }

    return true;
}

function fillImcInTable (){

    var patients = document.querySelectorAll(".paciente");

    for(var i = 0; i<patients.length; i++){

        var patientTr = patients[i];
        var patient = getPatientFromTr(patientTr);
        
        if( ! isPatientValid(patient)){
            patientTr.classList.add("invalid-data");
            continue;
        }

        var imc = calcImc(patient.weight, patient.height);
        patientTr.querySelector(".info-imc").textContent = imc;
    
    }

}

fillImcInTable();