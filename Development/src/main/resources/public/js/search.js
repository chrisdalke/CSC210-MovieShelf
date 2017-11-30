$(function(){

    $( "#search-form" ).submit(function( event ) {
        event.preventDefault();
        runSearch();
    });

    $("#searchString").keyup(function(){
        runSearch();
    })

});

var isSearching = false;

function runSearch(){
    var searchStringValue = $("#searchString").val()
    if (searchStringValue.length > 0){
        $("#search-pane").show();
        if (!isSearching) {
            isSearching = true;
            doSearch(searchStringValue, function (data) {
                buildResults(data);
                isSearching = false;
            }, function(){
                //Search failed, don't parse the results.
                isSearching = false
            });
        }
    } else {
        $("#search-pane").hide();
    }
}

function buildResults(searchObject){
    console.log(searchObject);
    getLibrary(function(library){
        $("#search-results").html("");
        var j = 0;
        for (var i = 0; i < searchObject.length; i++){
            if (i>10)
                break;
             var title=searchObject[i].titleId;
             var entry = $("<div class=\"uk-grid-match uk-grid-collapse \" uk-grid></div><br>");
             var titleCard=$("<div class=\"uk-card uk-width-expand@m\"></div>");
             var cardText=$("<a class=\"uk-position-center\" href=\"/titles/"+title+"\">"+searchObject[i].titleName+" ("+searchObject[i].year+")</a>");
             var buttonCard=$("<div class=\"uk-card uk-width-auto@m\"></div>");
             buttonCard.attr('id',title);

             if(!containsTitle(title)){
                buttonCard.addClass("addButton");
                buttonCard.append($("<span class=\"uk-position-center signs\" uk-icon=\"icon: plus\">"));
                buttonCard.click(function(){
                    addFavorite(this.id,function(){
                        runSearch();
                        refreshShelf();
                        
                    });
                    console.log("added");
                });

             }else{
                buttonCard.addClass("removeButton");
                buttonCard.append($("<span class=\"uk-position-center signs\" uk-icon=\"icon: minus\">"));
                buttonCard.click(function(){
                    removeFavorite(this.id,function(){
                        runSearch();
                        refreshShelf();

                    });
                    console.log("removed");
                });
            }
            titleCard.append(cardText);
            entry.append(titleCard);
            entry.append(buttonCard);
            $("#search-results").append(entry);
        }

        function containsTitle(titleId){
            for (var i = 0; i < library.length; i++) {
                if(titleId===library[i].titleId){
                    return true;
                }
            }
            return false;
        }
    });
}
