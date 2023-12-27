
function fetchData() {
    const gameIdElement = document.getElementById("selectGame");
    const gameId = gameIdElement.value;

    if (gameId === null) {
        return false;
    }

    const lastHoursElement = document.getElementById("selectHours");
    const lastHours = lastHoursElement.value;
    const lastMinutesElement = document.getElementById("selectMinutes");
    const lastMinutes = lastMinutesElement.value;

    if (lastHours === '0' && lastMinutes === '0') {
        alert("You requested the trends for the last 0 minutes which is not possible, lowest timeframe is 15 minutes.")
        return false;
    }

    let dataTable = new DataTable('#trenderTable');
    dataTable.rows().remove();
    dataTable.page.len(100);

    fetch('trender/' + gameId + '/' + lastHours + '/' + lastMinutes)
        .then(response => response.json())
        .then(data => {
            // Iterate over the keys of the map
            for (let key in data) {
                if (data.hasOwnProperty(key)) {
                    // Get the list of values for the current key
                    let values = data[key];

                    // Iterate over each entry in the list of values
                    for (let i = 0; i < values.length; i++) {
                        let entry = values[i];
                        const vOddsPercentage = Math.round((entry.voddsPercentage - 1) * 10000) / 100;
                        const vDistPercentage = Math.round((entry.vdistributionPercentage - 1) * 10000) / 100;

                        dataTable.row.add([
                            key,
                            entry.horseId,
                            '<a href=/horse.html?id=' + entry.horseId + ' target="_blank">' + entry.horseName + '</a>',
                            vDistPercentage + '%',
                            entry.distribution,
                            vOddsPercentage + '%',
                            entry.vodds,
                            entry.horseNumber
                        ]).draw(true);
                    }
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}