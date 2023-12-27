function loadchart () {
    const gameIdElement = document.getElementById("selectGame");
    const gameId = gameIdElement.value;

    if (gameId === null) {
        return false;
    }

    const lastHoursElement = document.getElementById("selectHours");
    const lastHours = lastHoursElement.value;
    const lastMinutesElement = document.getElementById("selectMinutes");
    const lastMinutes = lastMinutesElement.value;

    fetch('trender/' + gameId + '/' + lastHours + '/' + lastMinutes)
        .then(response => response.json())
        .then(data => {
            let graphContainer = document.getElementById("graphContainer");
            let charts = [];
            let graphtags = '';
            graphContainer.innerHTML = graphtags;

            // add canvas and divs for all races
            for (let key in data) {
                graphtags = '<div class="graph">';
                graphtags += '<canvas id=myChart' + key + '></canvas>';
                graphtags += '</div>';
                graphContainer.innerHTML += graphtags;
            }

            console.log('divs done')

            // add all the graphs with data
            for (let key in data) {
                const ctx = document.getElementById("myChart" + key).getContext('2d');
                let distributions = [];
                let labels = [];

                if (data.hasOwnProperty(key)) {
                    // Get the list of values for the current key
                    let values = data[key];

                    // Iterate over each entry in the list of values
                    for (let i = 0; i < values.length; i++) {
                        let entry = values[i];
                        labels.push(entry.horseName + ', vD: ' + entry.distribution);
                        const percent = Math.round((entry.vdistributionPercentage - 1) * 10000) / 100;
                        distributions.push(percent);
                    }
                }

                const chartData = {
                    labels: labels,
                    datasets: [{
                        label: "Race:" + key,
                        data: distributions,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                            'rgba(255, 205, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(201, 203, 207, 0.2)'
                        ],
                        borderColor: [
                            'rgb(255, 99, 132)',
                            'rgb(255, 159, 64)',
                            'rgb(255, 205, 86)',
                            'rgb(75, 192, 192)',
                            'rgb(54, 162, 235)',
                            'rgb(153, 102, 255)',
                            'rgb(201, 203, 207)'
                        ],
                        borderWidth: 1
                    }]
                };

                const config = {
                    type: 'bar',
                    data: chartData,
                    options: {
                        scales: {
                            y: {
                                beginAtZero: false
                            }
                        }
                    },
                };

                charts.push(new Chart (ctx, config));
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
};

function loadChartHorses() {
    const gameIdElement = document.getElementById("selectGame");
    const gameId = gameIdElement.value;

    if (gameId === null) {
        return false;
    }

    const lastHoursElement = document.getElementById("selectHours");
    const lastHours = lastHoursElement.value;
    const lastMinutesElement = document.getElementById("selectMinutes");
    const lastMinutes = lastMinutesElement.value;

    fetch('trender/horses/' + gameId + '/' + lastHours + '/' + lastMinutes)
        .then(response => response.json())
        .then(data => {
            const graphContainer = document.getElementById("graphHorseContainer");
            let charts = [];
            let graphtags = '';
            graphContainer.innerHTML = graphtags;

            // add canvas and divs for all races
            for (let key in data) {
                graphtags = '<div class="graph">';
                graphtags += '<h1>' + key +  '</h1>';
                graphtags += '<canvas id=horse' + key + '></canvas>';
                graphtags += '</div>';
                graphContainer.innerHTML += graphtags;
            }

            console.log('divs horses done')

            // add all the graphs with data
            for (let key in data) {

                const ctx = document.getElementById("horse" + key).getContext('2d');
                let distributions = [];
                let labels = [];
                let labelName = '';

                if (data.hasOwnProperty(key)) {
                    // Get the list of values for the current key
                    let values = data[key];

                    // Iterate over each entry in the list of values
                    for (let i = 0; i < values.length; i++) {
                        let entry = values[i];
                        labelName = entry.horseName;
                        labels.push(entry.timeStamp);
                        const percent = entry.distribution;
                        distributions.push(percent);
                    }
                }

                const chartData = {
                    labels: labels,
                    datasets: [{
                        label: labelName,
                        data: distributions,
                        fill: false,
                        borderColor: 'rgb(75, 192, 192)',
                        tension: 0.1
                    }]
                };

                const config = {
                    type: 'line',
                    data: chartData,
                };

                charts.push(new Chart (ctx, config));
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
};

function loadChartHorse() {
    const gameIdElement = document.getElementById("selectGame");
    const gameId = gameIdElement.value;

    if (gameId === null) {
        return false;
    }

    const lastHoursElement = document.getElementById("selectHours");
    const lastHours = lastHoursElement.value;
    const lastMinutesElement = document.getElementById("selectMinutes");
    const lastMinutes = lastMinutesElement.value;

    fetch('trender/horses/' + gameId + '/' + lastHours + '/' + lastMinutes)
        .then(response => response.json())
        .then(data => {
            const graphContainer = document.getElementById("graphHorseContainer");
            let charts = [];
            let graphtags = '';
            graphContainer.innerHTML = graphtags;

            // add canvas and divs for all races
            for (let key in data) {
                graphtags = '<div class="graph">';
                graphtags += '<h1>' + key + '</h1>';
                graphtags += '<canvas id=horse' + key + '></canvas>';
                graphtags += '</div>';
                graphContainer.innerHTML += graphtags;
            }

            console.log('divs horses done')

            // add all the graphs with data
            for (let key in data) {

                const ctx = document.getElementById("horse" + key).getContext('2d');
                let distributions = [];
                let labels = [];
                let labelName = '';

                if (data.hasOwnProperty(key)) {
                    // Get the list of values for the current key
                    let values = data[key];

                    // Iterate over each entry in the list of values
                    for (let i = 0; i < values.length; i++) {
                        let entry = values[i];
                        labelName = entry.horseName;
                        labels.push(entry.timeStamp);
                        const percent = entry.distribution;
                        distributions.push(percent);
                    }
                }

                const chartData = {
                    labels: labels,
                    datasets: [{
                        label: labelName,
                        data: distributions,
                        fill: false,
                        borderColor: 'rgb(75, 192, 192)',
                        tension: 0.1
                    }]
                };

                const config = {
                    type: 'line',
                    data: chartData,
                };

                charts.push(new Chart (ctx, config));
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
};