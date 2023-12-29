function reloadArchivedData() {
    const gameIdElement = document.getElementById("selectGameType");
    const gameType = gameIdElement.value;

    if (gameType === null) {
        return false;
    }

    let dataTable = new DataTable('#archiveTable');
    dataTable.rows().remove();
    dataTable.page.len(100);

    fetch('trender/archive/' + gameType)
        .then(response => response.json())
        .then(data => {
            // Iterate over the keys of the map
                    // Iterate over each entry in the list of values
                    for (let i = 0; i < data.length; i++) {
                        let entry = data[i];

                        const vDist60 = entry.vdistribution0 - entry.vdistribution60;
                        const vDist30 = entry.vdistribution0 - entry.vdistribution30;
                        const vDist15 = entry.vdistribution0 - entry.vdistribution15;
                        const vDist0 = entry.vdistribution0;

                        const vOdds60 = entry.vodds0 - entry.vodds60;
                        const vOdds30 = entry.vodds0 - entry.vodds30;
                        const vOdds15 = entry.vodds0 - entry.vodds15;
                        const vOdds0 = entry.vodds0;

                        dataTable.row.add([
                            entry.raceId,
                            entry.placement,
                            entry.horseNumber,
                            entry.horseName,
                            vDist60,
                            vDist30,
                            vDist15,
                            vDist0,
                            vOdds60,
                            vOdds30,
                            vOdds15,
                            vOdds0
                        ]).draw(true);
                    }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}