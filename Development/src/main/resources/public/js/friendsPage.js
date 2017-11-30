$(function () {

    $(".confirm-friend-request-link").click(function () {
        // slice the username out of the element ID and pass it in
        var username = this.id.slice(23);
        addFriend(username, function (result) {
            removeIncomingFriendRequest(username);
            addFriendToList(username);
        });
    });

    $(".deny-friend-request-link").click(function () {
        // slice the username out of the element ID and pass it in
        deleteFriend(this.id.slice(20), function (username) {
            removeIncomingFriendRequest(username);
        });
    });

    // use of .on() rather than .click() for the following methods was intentional
    // so that newly generated elements are included with the click functionality
    // (new elements are added when user clicks confirm button on a friend request)

    $("#outgoing-friend-request-table").on("click", ".cancel-outgoing-friend-request-link", function () {
        // slice the username out of the element ID and pass it in
        deleteFriend(this.id.slice(22), function (username) {
            removeOutgoingFriendRequest(username);
        });
    });

    $("#friends-list-table").on("click", ".remove-friend-link", function () {
        deleteFriend(this.id.slice(14), function (username) {
            removeFriend(username);
        });
    });

    $("#add-friend-form").submit(function (event) {
        event.preventDefault();
        var username = $("#add-friend-input-field").val();
        addFriend(username, function (result) {
            if(result.status != 1) {
                addToOutgoingFriendRequests(username);
            }
        })
    });
});

function removeIncomingFriendRequest(username) {
    $("#incoming-friend-request-table-entry-" + username).remove();
}

function removeOutgoingFriendRequest(username) {
    $("#outgoing-friend-request-table-entry-" + username).remove();
}

function removeFriend(username) {
    $("#friends-list-table-entry-" + username).remove();
}

function addToOutgoingFriendRequests(username) {
    var table = $("#outgoing-friend-request-table");
    var htmlString =
        "<tr>" +
            "<td id='outgoing-friend-request-table-entry-" + username +"'>" +
                "<span>" + username + " </span>" +
                "<a class='cancel-outgoing-friend-request-link' id='friend-request-cancel-" + username +"'>(Cancel)</a>" +
            "</td>" +
        "</tr>";
    table.append(htmlString);
}

function addFriendToList(username) {
    var table = $("#friends-list-table");
    var htmlString =
        "<tr>" +
            "<td id='friends-list-table-entry-" + username + "'>" +
                "<span>" + username + " </span>" +
                "<a class='remove-friend-link' id='friend-remove-" + username + "'>(Remove)</a>" +
            "</td>" +
        "</tr>";
    table.append(htmlString);
}