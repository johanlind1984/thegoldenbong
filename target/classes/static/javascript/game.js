function getGames() {
    fetch('game/getgames')
        .then(response => response.json())
        .then(data => {
            console.log('getting games')
            for (const dataKey in data) {
                var gameId = data[dataKey];
                // document.getElementById("gameLinks").innerHTML += '<a href=/index.html?gameId=' + gameId + '>' + gameId + '</a>';
                var gametag = '<option value="' + gameId + '">' + gameId + '</option>';
                document.getElementById("selectGame").innerHTML += '<option value="' + gameId + '">' + gameId + '</option>';
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}