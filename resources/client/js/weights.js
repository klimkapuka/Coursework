function pageLoad() {

    /*fetch("/weight/track", {method: 'post'}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            var result = JSON.parse(responseData);
            drawChart(result);
        }
    });

    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart(result) {

        var data = new google.visualization.DataTable();
            data.addColumn('number', 'CurrentWeight');
            data.addColumn('string', 'DateRecorded');

        for (var i = 0; i < result.length; i++) {
            data.addRow([result[i].CurrentWeight, result[i].DateRecorded]);
        }

        var options = {
            title: 'Progress Tracker',
            legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart'));

        chart.draw(data, options);

    }*/

    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Date', 'Weight'],
            ['10/09/2019',  95],
            ['12/09/2019',  92.5],
            ['17/09/2019',  90],
            ['21/09/2019',  85.4]
        ]);

        var options = {
            title: 'Progress Tracker',
            legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart'));

        chart.draw(data, options);
    }

}