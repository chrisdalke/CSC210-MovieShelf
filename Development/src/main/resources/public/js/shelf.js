$(function(){

    refreshShelf();
});



function refreshShelf(){
    doAjax("/api/shelf","GET",{},function(data){
        var shelfDivString = "";
        for (var i = 0; i < data.length; i +=3){
            //Add a shelf row
            shelfDivString += "<div class=\"shelf\"><div class=\"shelf-grid uk-grid-collapse uk-child-width-expand@s\" uk-grid>";
            if (i + 0 < data.length){
                shelfDivString += "<div class=\"shelf-item-holder\"><div class=\"shelf-item-box\"><a href=\"/titles/"+data[i+0].titleId+"\"><div class=\"movie\">";
                shelfDivString += data[i+0].titleName;
                shelfDivString += " ("+data[i+0].year+")";
                shelfDivString += "</div></a></div></div>";
            } else {
                shelfDivString += "<div class=\"shelf-item-holder\"></div>";
            }
            if (i + 1 < data.length){
                shelfDivString += "<div class=\"shelf-item-holder\"><div class=\"shelf-item-box\"><a href=\"/titles/"+data[i+1].titleId+"\"><div class=\"movie\">";
                shelfDivString += data[i+1].titleName;
                shelfDivString += " ("+data[i+1].year+")";
                shelfDivString += "</div></a></div></div>";

            } else {
                shelfDivString += "<div class=\"shelf-item-holder\"></div>";
            }
            if (i + 2 < data.length){
                shelfDivString += "<div class=\"shelf-item-holder\"><div class=\"shelf-item-box\"><a href=\"/titles/"+data[i+2].titleId+"\"><div class=\"movie\">";
                shelfDivString += data[i+2].titleName;
                shelfDivString += " ("+data[i+2].year+")";
                shelfDivString += "</div></a></div></div>";
            } else {
                shelfDivString += "<div class=\"shelf-item-holder\"></div>";
            }
            shelfDivString += "</div></div>";
        }

        $("#main-with-sidebar").html(shelfDivString);


    },null);
}