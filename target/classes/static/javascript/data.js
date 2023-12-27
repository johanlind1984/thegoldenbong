var trender;
async function downladData() {
    const gameIdElement = document.getElementById("selectGame");
    const gameId = gameIdElement.value;
    console.log('fetching data for gameID' + gameId)

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

    let fetchedTrender;

    await fetch('trender/' + gameId + '/' + lastHours + '/' + lastMinutes)
        .then(response => response.json())
        .then(data => {
            trender = data;
        });
}

async function getTrender() {
    return await downladData();
}