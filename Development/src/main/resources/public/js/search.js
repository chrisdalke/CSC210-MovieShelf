$(function(){

    $( "#search-form" ).submit(function( event ) {
        event.preventDefault();
        runSearch();
    });

    $("#searchString").keyup(function(){
        runSearch();
    })

});

function runSearch(){
    var searchStringValue = $("#searchString").val();
    if (searchStringValue.length > 0){
        $("#search-pane").show();
        doSearch(searchStringValue, function (data) {
            if($("#searchString").val() == searchStringValue) {
                buildResults(data);
            }
        }, function () {
        });
    } else {
        $("#search-pane").hide();
    }
}

function buildResults(searchObject){
    console.log(searchObject);
    $("#search-results").html("");
    if(searchObject.users.length == 0 && searchObject.titles.length == 0) {
      $("#search-results").append("<i>No Results<\i>");
    }
    // append users
    if(searchObject.users.length > 0) {
        $("#search-results").append("Users");
    }
    for(var i = 0; i < searchObject.users.length; i++) {
        if (i>4)
            break;
        var user = searchObject.users[i];
        var userEntry = $("<div class=\"uk-grid-match uk-grid-collapse \" uk-grid></div><br>");
        var userCard=$("<div class=\"uk-card uk-width-expand@m\"></div>");
        var userCardText=$("<a class=\"uk-position-center\" href=\"/user/profile/"+user.username+"\">"+user.username+"</a>");
        var userButtonCard=$("<div class=\"uk-card uk-width-auto@m\"></div>");
        userButtonCard.attr('id',user.username);

        if(!user.added){
            userButtonCard.addClass("addButton");
            userButtonCard.append($("<span class=\"uk-position-center signs\" uk-icon=\"icon: plus\">"));
            userButtonCard.click(function(){
                addFriend(this.id,function(){
                    runSearch();
                });
                console.log("added");
            });

        } else {
            userButtonCard.addClass("removeButton");
            userButtonCard.append($("<span class=\"uk-position-center signs\" uk-icon=\"icon: minus\">"));
            userButtonCard.click(function(){
                deleteFriend(this.id,function(){
                    runSearch();
                });
                console.log("removed");
            });
        }
        userCard.append(userCardText);
        userEntry.append(userCard);
        userEntry.append(userButtonCard);
        $("#search-results").append(userEntry);
    }
    // append titles
    if(searchObject.titles.length > 0) {
        if(searchObject.users.length > 0) {
            $("#search-results").append("<hr>");
        }
        $("#search-results").append("Titles");
    }
    for (var j = 0; j < searchObject.titles.length; j++){
        if (j>10)
            break;
        var title = searchObject.titles[j].title;
        var entry = $("<div class=\"uk-grid-match uk-grid-collapse \" uk-grid></div><br>");
        var titleCard=$("<div class=\"uk-card uk-width-expand@m\"></div>");
        var cardText=$("<a class=\"uk-position-center\" href=\"/titles/"+title.titleId+"\">"+title.titleName+" ("+title.year+")</a>");
        var buttonCard=$("<div class=\"uk-card uk-width-auto@m\"></div>");
        buttonCard.attr('id',title.titleId);

        if(!searchObject.titles[j].containedInShelf | isSession){
            buttonCard.addClass("addButton");
            buttonCard.append($("<span class=\"uk-position-center signs\" uk-icon=\"icon: plus\">"));
            setupButtonAddClickListener(buttonCard,title.titleId)
        } else {
            buttonCard.addClass("removeButton");
            buttonCard.append($("<span class=\"uk-position-center signs\" uk-icon=\"icon: minus\">"));
            setupButtonRemoveClickListener(buttonCard,title.titleId)
        }
        titleCard.append(cardText);
        entry.append(titleCard);
        entry.append(buttonCard);
        $("#search-results").append(entry);
    }
}

function setupButtonAddClickListener(button,titleId){
    button.click(function(){
        if (isSession){
            sessionAddTitle(titleId);
        } else {
            addFavorite(titleId, function () {
                runSearch();
                refreshShelf();

            });
        }
        console.log("added");
    });
}

function setupButtonRemoveClickListener(button,titleId){
    button.click(function(){
        removeFavorite(titleId,function(){
            runSearch();
            refreshShelf();

        });
        console.log("removed");
    });
}

//SHHHHHHH
function plug(contents){
  var lastShelf=$(".flag:last");
  switch (contents) {
    case 3:
    case 0:
      break;
    case 1:
    lastShelf.append("<div class=\"shelf-item-holder\"></div>");
    lastShelf.append("<div class=\"shelf-item-holder\"></div>");
      break;
    case 2:
    lastShelf.append("<div class=\"shelf-item-holder\"></div>");
        break;
    default:
    break;
  }
}
