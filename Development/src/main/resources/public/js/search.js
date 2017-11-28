/**
 * Created by chrisdalke on 11/14/17.
 */
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
    getLibrary(function(library){
        $("#search-results").html("");
        for (var i = 0; i < searchObject.length; i++){
            if (i>10)
                break;
             var entry = $("<div class=\"uk-grid-match uk-grid-collapse \" uk-grid></div><br>");
             var titleCard=$("<div class=\"uk-card uk-width-expand@m\"></div>");
             var buttonCard;
             var cardText=$("<a class=\"uk-position-center\" href=\"/titles/"+searchObject[i].titleId+"\">"+searchObject[i].titleName+" ("+searchObject[i].year+")</a>");
             //var titleObject=Object.freeze(searchObject[i]);

             function add(titleId){
                addFavorite(titleId,function(){
                        refreshShelf();
                    });
                console.log("added");
             }

             function remove(titleId){
                removeFavorite(titleId,function(){
                    refreshShelf();
                    });
                console.log("removed");
             }

             if(!containsTitle(searchObject[i].titleId)){
                buttonCard =$("<div class=\"uk-card addButton uk-width-auto@m\"><span class=\"uk-position-center signs\" uk-icon=\"icon: plus\"></span></div>");
                buttonCard.click(add(searchObject[i].titleId));
                /*
                buttonCard.click(function(){
                    addFavorite(titleObject[i].titleId,function(){
                        refreshShelf();
                    });
                console.log("added");
                });
                */
             }else{
                buttonCard =$("<div class=\"uk-card removeButton uk-width-auto@m\"><span class=\"uk-position-center signs\" uk-icon=\"icon: minus\"></span></div>");
                buttonCard.click(remove(searchObject[i].titleId));
                /*
                buttonCard.click(function(){
                    removeFavorite(titleObject[i].titleId,function(){
                    refreshShelf();
                    });
                console.log("removed");
                }); 
                */
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