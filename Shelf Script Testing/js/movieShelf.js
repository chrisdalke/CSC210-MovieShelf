var script = document.createElement('script');
script.src = 'http://code.jquery.com/jquery-1.11.0.min.js';
script.type = 'text/javascript';

/*
function addToGrid() {
    var grid=$("#grid");
    console.log(grid);
    var outerDiv=$("<div><div>");
    var card=$("<div></div>");
    card.addClass("uk-card uk-card-secondary uk-height-large uk-card-body card");
    outerDiv.append(card);
    grid.append(outerDiv);
}
*/

function addToGrid(grid){
    var outerDiv=$("<div></div>");
    var card=$("<div></div>");
    card.addClass("uk-card uk-card-secondary uk-height-large uk-card-body card");
    outerDiv.append(card);
    grid.append(outerDiv);
}

function addNewGrid(master){
    var newGrid=$("<div uk-grid></div>");
    newGrid.attr("data-value","1");
    newGrid.addClass("uk-grid-match uk-child-width-1-4 uk-grid-small uk-text-center uk-padding-large grid");
    addToGrid(newGrid);
    master.append(newGrid);
}

function addGrid(){
    var currentGrid=$(".grid:last");
    var value = parseInt(currentGrid.attr("data-value"));
    if(value<4){
        addToGrid(currentGrid);
        currentGrid.attr("data-value",String(value+1));
    }else{
        var masterGrid=$("#gridElement");
        addNewGrid(masterGrid);
    }
}