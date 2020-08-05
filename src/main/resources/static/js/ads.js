const URLS = {
    jobs: '/api/jobs',
    vehicles: '/api/vehicles',
    realEstates: '/api/real-estates',
};

/*const localhost = "http://localhost:8080/";

let oldURL = document.referrer;
if (oldURL === `${localhost}` + "jobs/add"){
    document.getElementById("jobs").checked = true;
} else if (oldURL === `${localhost}` + "real-estates/add"){
    document.getElementById("realEstates").checked = true;
} else if (oldURL === `${localhost}` + "vehicles/add"){
    document.getElementById("vehicles").checked = true;
}*/

const toJob = ({addedOn, expireOn, title, id}) => `
    <tr>
    <td>${addedOn}</td>
    <td>${expireOn}</td>
    <td>${title}</td>
    <td><a href="/jobs/details/${id}"
class="btn btn-success font-weight-bold text-white">Виж</a></td>
</tr>`

const toVehicle = ({addedOn, expireOn, image, title, id}) => `
    <tr>
    <td>${addedOn}</td>
    <td>${expireOn}</td>
    <td>
    <a class="image-link" href=${image}>
    <img class="img-fluid" src=${image} width="40" height="40"
alt="Vehicle">
    </a>
    </td>
    <td>${title}</td>
    <td><a href="/vehicles/details/${id}"
class="btn btn-success font-weight-bold text-white">Виж</a></td>
</tr>`

const toRealEstate = ({addedOn, expireOn, imageUrl, title, id}) => `
    <tr>
    <td>${addedOn}</td>
    <td>${expireOn}</td>
    <td>
    <a class="image-link" href=${imageUrl}>
    <img class="img-fluid" src=${imageUrl} width="40" height="40"
alt="Vehicle">
    </a>
    </td>
    <td>${title}</td>
    <td><a href="/real-estates/details/${id}"
class="btn btn-success font-weight-bold text-white">Виж</a></td>
</tr>`

$('#jobs').click(() => {
fetch(URLS.jobs)
    .then(res => res.json())
    .then(work => {
        let result = '';
        work.forEach(job => {
            const jobString = toJob(job)
            result += jobString;
        });
        document.getElementById('ads')
            .innerHTML = result;
       document.querySelector('#table_id > thead:nth-child(1) > tr:nth-child(1) > th:nth-child(3)').style.display = 'none';

    });})

$('#vehicles').click(() => {
    fetch(URLS.vehicles)
        .then(res => res.json())
        .then(vehicles => {
            let result = '';
            vehicles.forEach(vehicle => {
                const jobString = toVehicle(vehicle)
                result += jobString;
            });
            document.getElementById('ads')
                .innerHTML = result;
            document.querySelector('#table_id > thead:nth-child(1) > tr:nth-child(1) > th:nth-child(3)').style.display = 'block';
        });})


$('#realEstates').click(() => {
    fetch(URLS.realEstates)
        .then(res => res.json())
        .then(realEstates => {
            let result = '';
            realEstates.forEach(realEstate => {
                const jobString = toRealEstate(realEstate)
                result += jobString;
            });
            document.getElementById('ads')
                .innerHTML = result;
            document.querySelector('#table_id > thead:nth-child(1) > tr:nth-child(1) > th:nth-child(3)').style.display = 'block';

        });})



