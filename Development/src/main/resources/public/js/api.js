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

function addFriend(username, success) {
    doAjax(
        "/api/friends",
        "POST",
        {
            username: username
        },
        function(result) {
            success(result, username);
        }
    );

}

function deleteFriend(username, success) {
    doAjax(
        "/api/friends/" + username,
        "DELETE",
        {},
        function () {
            success(username);
        }
    );
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

//------------------------------------------------
// Sessions API
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
// Metadata API
//------------------------------------------------
function addMetadata(titleId,success){
    doAjax("/api/meta/"+titleId+"/"+img_url+"/"+description, "POST", {}, success, null);
}

function getImage(titleId,success){
    doAjax("/api/meta/"+title,"GET",{ },success,null);
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

$.fn.extend({
    animateCss: function (animationName, callback) {
        var animationEnd = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
        this.addClass('animated ' + animationName).one(animationEnd, function() {
            $(this).removeClass('animated ' + animationName);
            if (callback) {
                callback();
            }
        });
        return this;
    }
});

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////