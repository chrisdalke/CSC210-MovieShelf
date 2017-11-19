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
                parseSearchResults(data);
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

function parseSearchResults(searchObject){
    console.log("SEARCH RESULTS:");
    console.log(searchObject);
    $("#search-results").html("");
    for (var i = 0; i < searchObject.length; i++) {
        if(i>10){
            break;
        }
        var entry = $("<div class=\"uk-grid-match uk-grid-collapse \" uk-grid></div><br>");
        var titleCard=$("<div class=\"uk-card uk-width-expand@m\"></div>");
        var buttonCard;
        var cardText=$("<a class=\"uk-position-center\" href=\"/titles/"+searchObject[i].titleId+"\">"+searchObject[i].titleName+" ("+searchObject[i].year+")</a>");
        var titleObject=Object.freeze(searchObject[i]); 

        if(!containsTitle(searchObject[i].titleId)){
            buttonCard =$("<div class=\"uk-card addButton uk-width-auto@m\"><span class=\"uk-position-center signs\" uk-icon=\"icon: plus\"></span></div>");
            buttonCard.click(function(){
                addFavorite(titleObject.titleId);
                console.log("added");
            });
        }else{
          buttonCard =$("<div class=\"uk-card removeButton uk-width-auto@m\"><span class=\"uk-position-center signs\" uk-icon=\"icon: minus\"></span></div>");
            buttonCard.click(function(){
                removeFavorite(titleObject.titleId);
                console.log("removed");
            });  
        }
        
        titleCard.append(cardText);
        entry.append(titleCard);
        entry.append(buttonCard);
        $("#search-results").append(entry);
    }
}

function containsTitle(titleId){
    //Use getLibrary in api.js
    return Math.random() >= 0.5;
}