///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////
// JS API
// Contains function for interfacing on the clientside
// with the movieshelf REST API.
/////////////////////////////////////////////////////////////

var loggedInCredentials = "";

//------------------------------------------------
// User API
//------------------------------------------------

function getUserFromSessionCode(sessionCode, callback){

}

//------------------------------------------------
// Friend API
//------------------------------------------------

function addFriend(friend){
    doAjax("/api/friends","POST",{"friend": friend});
}

function removeFriend(friendship){
    doAjax("/api/shelf/"+friendship,"DELETE",{});
}

function getFriends(){

}
//------------------------------------------------
// Search API
//------------------------------------------------

function doSearch(searchText, success, failure){
    doAjax(
        "/api/search",
        "GET",
        {
            searchString: searchText
        },
        function(result){
            success(result);
        },
        function(){
            failure();
        });
}


function getLibrary(success){
    doAjax(
        "/api/shelf",
        "GET",
        {
        },
        function(result){
            success(result);
        },
        function(){});
}

//------------------------------------------------
// Session API
//------------------------------------------------


//------------------------------------------------
// Shelf API
//------------------------------------------------

function addFavorite(title,success){
    doAjax("/api/shelf/"+title,"POST",{ },success,null);
}

function removeFavorite(title,success){
    doAjax("/api/shelf/"+title,"DELETE",{},success,null);

}

function getFavorites(){

}

//------------------------------------------------
// Helper Methods
//------------------------------------------------

function doAjax(url, method, data, success, failure){
    $.ajax({
        url: url,
        method: method,
        data: data,
        beforeSend: function (xhr){
            xhr.setRequestHeader("Content-Type","application/json");
            xhr.setRequestHeader("Accept","application/json");
        },
        success: function(result){
            if (success) {
                success(result);
            }
        },
        error: function(result){
            console.log(result);
            if (failure){
                failure(result);
            }
        }
    });
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////