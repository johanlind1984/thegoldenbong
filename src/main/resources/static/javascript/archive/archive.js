function reloadArchivedData() {
    const gameIdElement = document.getElementById("selectGameType");
    const gameType = gameIdElement.value;

    const vDistLowElement = document.getElementById("lowvdist");
    const vdistLow = vDistLowElement.value;

    const vDistHighElement = document.getElementById("highvdist");
    const vdistHigh = vDistHighElement.value;

    if (gameType === null) {
        return false;
    }

    fetch('trender/archivestatistics/' + gameType + '/' + vdistLow + '/' + vdistHigh)  // Replace with your API endpoint
        .then(response => response.json())  // Parse the JSON response
        .then(data => {
            updateDivWithStrings(data);
        })
        .catch(error => {
            console.error('Error fetching data: ', error);
        });


    let dataTable = new DataTable('#archiveTable');
    dataTable.rows().remove();
    dataTable.page.len(10000);

    fetch('trender/archive/' + gameType + '/' + vdistLow + '/' + vdistHigh)
        .then(response => response.json())
        .then(data => {
                    // Iterate over each entry in the list of values
                    for (let i = 0; i < data.length; i++) {
                        let entry = data[i];

                        const vDist60 = entry.vdistribution0 - entry.vdistribution60;
                        const vDist30 = entry.vdistribution0 - entry.vdistribution30;
                        const vDist15 = entry.vdistribution0 - entry.vdistribution15;
                        let vDist60percent = 0;
                        let vDist30percent = 0;
                        let vDist15percent = 0;
                        const vDist0 = entry.vdistribution0;

                        if (vDist0 === 0) {
                            // do nothing
                        } else {
                            vDist60percent = Math.round((vDist60 / vDist0) * 100);
                            vDist30percent = Math.round((vDist30 / vDist0) * 100);
                            vDist15percent = Math.round((vDist15 / vDist0) * 100);
                        }

                        const vOdds60 = entry.vodds0 - entry.vodds60;
                        const vOdds30 = entry.vodds0 - entry.vodds30;
                        const vOdds15 = entry.vodds0 - entry.vodds15;
                        let vOdds60percent = 0;
                        let vOdds30percent = 0;
                        let vOdds15percent = 0;
                        const vOdds0 = entry.vodds0;

                        if (vOdds0 === 0) {
                            // do nothing
                        } else {
                            vOdds60percent = Math.round((vOdds60 / vOdds0) * 100);
                            vOdds30percent = Math.round((vOdds30 / vOdds0) * 100);
                            vOdds15percent = Math.round((vOdds15 / vOdds0) * 100);
                        }

                        dataTable.row.add([
                            entry.raceId,
                            entry.placement,
                            entry.horseNumber,
                            entry.horseName,
                            vDist60,
                            vDist60percent,
                            vDist30,
                            vDist30percent,
                            vDist15,
                            vDist15percent,
                            vDist0,
                            vOdds60,
                            vOdds60percent,
                            vOdds30,
                            vOdds30percent,
                            vOdds15,
                            vOdds15percent,
                            vOdds0
                        ]).draw(true);
                    }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function updateDivWithStrings(strings) {
    const div = document.getElementById("statistics");

    // Clear existing content in the div
    div.innerHTML = '';

    // Iterate over each string in the list and append it to the div
    strings.forEach(str => {
        // Create a text node or a new div for each string
        const textNode = document.createTextNode(str);
        const paragraph = document.createElement('p');
        paragraph.appendChild(textNode);

        // Append the paragraph to the main div
        div.appendChild(paragraph);
    });
}
