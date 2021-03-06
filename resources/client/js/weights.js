function pageLoad() {

    document.getElementById("weightButton").addEventListener("click", logWeight);

    google.load("visualization", "1", {packages: ["corechart"]});
    google.setOnLoadCallback(function () {
        drawChart()
    });

    let dates = [];
    let weights = [];

    fetch("/weight/track", {method: 'post'}
    ).then(response => response.json()
    ).then(results => {

        for (let result of results) {
            dates.push(result.DateRecorded);
            weights.push(result.CurrentWeight);
        }
    });

    function drawChart() {
        let data = new google.visualization.DataTable();
        data.addColumn('string', 'DateRecorded');
        data.addColumn('number', 'CurrentWeight');

        for (let i = 0; i < weights.length; i++) {
            data.addRow([dates[i], weights[i]]);
        }


        var options = {
            title: 'Progress Tracker',
            legend: {position: 'bottom'}
        };

        let chart = new google.visualization.LineChart(document.getElementById('chart'));

        chart.draw(data, options);
    }
}

function logWeight(event) {
    event.preventDefault();

    const form = document.getElementById("weightForm");
    const formData = new FormData(form);

    fetch("/weight/log-new", {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            window.location.href = "weights.html";

        }
    });
}