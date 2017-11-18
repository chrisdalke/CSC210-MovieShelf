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
        var titleCard=$("<div class=\"uk-card uk-width-expand@m\">"+searchObject[i].titleName+" ("+searchObject[i].year+")</div>");
        var buttonCard =$("<div class=\"uk-card addButton uk-width-auto@m\"><a icon: \"plus\"></a></div>");
        var titleObject=Object.freeze(searchObject[i]);
        buttonCard.click(function(){
            addFavorite(titleObject.titleId);
            console.log("added");
        });
        entry.append(titleCard);
        entry.append(buttonCard);
        $("#search-results").append(entry);
        /*
        var entry = $("<div class=\"uk-card \"></div><br>");
        var body= $("<div class=\"uk-card-body \">"+searchObject[i].titleName+" ("+searchObject[i].year+")</div>")
        var right=$("<div class=\"uk-card-media-right\"></div>");
        var button=$("<a class=\"uk-button uk-button-default\" href=\"/titles/"+searchObject[i].titleId+"\"> View</a>");
        right.append(button);
        entry.append(body);
        entry.append(right);
        $("#search-results").append(entry);
        */
    }
}