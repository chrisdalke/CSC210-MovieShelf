$(function(){
    refreshShelf();
});

function refreshShelf(){
    doAjax("/api/shelf","GET",{},function(data){
        var shelfDivString = "";
        for (var i = 0; i < data.length; i +=3){
            //Add a shelf row
            shelfDivString += "<div class=\"shelf\"><div class=\"shelf-grid uk-grid-collapse uk-child-width-expand@s\" uk-grid>";
            if (i < data.length){
                shelfDivString += "<div class=\"shelf-item-holder\"><div class=\"shelf-item-box\"><a href=\"/titles/"+data[i].title.titleId+"\"><div class=\"movie\">";
                shelfDivString += "<img src=\"";
                shelfDivString += data[i].imgURL;
                shelfDivString += "\" width=\"100%\" height=\"100%\">";
                shelfDivString += "</div></a></div></div>";
            } else {
                shelfDivString += "<div class=\"shelf-item-holder\"></div>";
            }
            if (i + 1 < data.length){
                shelfDivString += "<div class=\"shelf-item-holder\"><div class=\"shelf-item-box\"><a href=\"/titles/"+data[i+1].title.titleId+"\"><div class=\"movie\">";
                shelfDivString += "<img src=\"";
                shelfDivString += data[i+1].imgURL;
                shelfDivString += "\" width=\"100%\" height=\"100%\">";
                shelfDivString += "</div></a></div></div>";

            } else {
                shelfDivString += "<div class=\"shelf-item-holder\"></div>";
            }
            if (i + 2 < data.length){
                shelfDivString += "<div class=\"shelf-item-holder\"><div class=\"shelf-item-box\"><a href=\"/titles/"+data[i+2].title.titleId+"\"><div class=\"movie\">";
                shelfDivString += "<img class=\"coverArt\" src=\"";
                shelfDivString += data[i+2].imgURL;
                shelfDivString += "\" width=\"100%\" height=\"100%\">";
                shelfDivString += "</div></a></div></div>";
            } else {
                shelfDivString += "<div class=\"shelf-item-holder\"></div>";
            }
            shelfDivString += "</div></div>";
        }
        $("#main-with-sidebar").html(shelfDivString);
    },null);
}

function addImageTag(div,img){
  div += "<img src=\"";
  div += img;
  div += "\" width=\"100%\" height=\"100%\">";
}
/*
input = "/api/meta/"+data[i].titleId;
doAjax(input, "GET", {}, function(img_src) {
    shelfDivString += "<img src=\"";
    shelfDivString += img_src;
    shelfDivString += "\" width=\"100%\" height=\"100%\">";
}, null);
*/
