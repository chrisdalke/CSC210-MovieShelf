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
        $("#search-results").append("<b>"+searchObject[i].titleName+" ("+searchObject[i].year+") </b><br>");
    }
}