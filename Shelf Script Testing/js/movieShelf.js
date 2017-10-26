var script = document.createElement('script');
script.src = 'http://code.jquery.com/jquery-1.11.0.min.js';
script.type = 'text/javascript';

function addToGrid() {
    var grid=$("#grid");
    console.log(grid);
    var outerDiv=$("<div><div>");
    var card=$("<div></div>");
    card.addClass("uk-card uk-card-secondary uk-height-large uk-card-body card");
    outerDiv.append(card);
    grid.append(outerDiv);
}

function addGrid(){
    var currentGrid=$("#gridElement").last
    console.log(currentGrid.attr("#value"))
}