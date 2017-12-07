var isConnected = false;
var stompClient = null;
var sessionId = null;
var isReady = 0;
var userId = -1;
var sessionUserList = [];

$(function(){
    // On page load, grab the session id
    sessionId = parseInt($("#session-id").text());
    isReady = parseInt($("#session-is-ready").text());
    userId = parseInt($("#session-user-id").text());
    if (isReady == 0){
        sessionSetMyUnready(true);
    } else {
        sessionSetMyReady(true);
    }

    //Build static user list
    $(".session-user").each(function(item){
        var tempId = $(this).attr('id');
        if (tempId) {
            var userIdTemp = parseInt(tempId.replace("session-user-", ""))
            sessionUserList.push(userIdTemp);
            $("#session-user-"+userIdTemp).animateCss("bounceIn");
        }
    });

    // Join the session socket
    socketConnect();

    $("#button-session-finish").click(function(e){
        sessionToggleReady();
        return false;
    });
});

function socketConnect(){
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        isConnected = true;
        sessionConnect();
        stompClient.subscribe('/topic/session/'+sessionId, function (message) {
            socketReceive(JSON.parse(message.body));
        });
    });
}

function socketSend(message){
    if (isConnected) {
        stompClient.send("/app/session/" + sessionId, {}, JSON.stringify({messageType: message.messageType, content: message.content, userId: userId}));
        console.log("Sent message!");
    }
}

function sessionUpdateUserDisplay(){

}

function socketReceive(message){
    console.log("Received message:");
    console.log(message);
    switch (message.messageType){
        case 1: {
            // Refresh the users' data
            refreshUsers();
            break;
        }
        case 2: {
            // Trigger the loading page
            sessionShowLoad();
            break;
        }
        case 3: {
            // Trigger the final session results page
            location.reload();
            break;
        }
        case 4: {
            // A user set their ready state
            // Display (if the user is not us)
            var tempUserId = parseInt(message.content);
            if (tempUserId != userId) {
                sessionSetReady(tempUserId);
            }
            break;
        }
        case 5: {
            // A user set their unready state
            // Display (if the user is not us)
            var tempUserId = parseInt(message.content);
            if (tempUserId != userId) {
                sessionSetUnready(tempUserId);
            }
            break;
        }
    }
}

function refreshUsers(){
    doAjax("/api/public/session/"+sessionId+"/users_other",
        "GET",{},function(userDataList){
        var htmlString = "";
        $("#session-users").html(htmlString);
        console.log(userDataList);
        for (var i = 0; i < userDataList.length; i++){
            htmlString = "";
            var tempUser = userDataList[i].user;
            var tempUserSession = userDataList[i].userSession;
            var tempUserSuggestionList = userDataList[i].userSuggestionList;

            htmlString += `
            <div class="session-user" id="session-user-`+tempUser.userId+`">
                <div uk-grid>
                    <div class="uk-width-auto session-user-holder uk-flex uk-flex-column uk-flex-center">`+tempUser.username+`</div>
                    <div class="noborder uk-width-auto uk-flex uk-flex-column uk-flex-center"><div class="session-arrow-left"></div></div>
                    <div class="uk-width-expand session-movie-holder">Movies Chosen</div>
                    <div class="uk-width-auto session-user-confirm-holder uk-flex uk-flex-column uk-flex-center">
                        <div id="session-ready-`+tempUser.userId+`" class="session-unfinished-icon session-confirm-icon uk-flex uk-align-center uk-flex-column uk-flex-center">
                            <span uk-icon="icon: more" class="session-user-confirm-icon"></span>
                        </div>
                    </div>
                </div>
            </div>
            `;
            if (tempUser.userId != userId) {
                $("#session-users").append(htmlString);
                if (tempUserSession.isReady) {
                    sessionSetReady(tempUser.userId, true);
                } else {
                    sessionSetUnready(tempUser.userId, true);
                }
                if (!sessionUserList.includes(tempUser.userId)){
                    sessionUserList.push(tempUser.userId);
                    $("#session-user-"+tempUser.userId).animateCss("bounceIn");
                }
            }
        }
    });
}

function sessionAddTitle(titleId){

}

function sessionRemoveTitle(titleId){

}

function sessionShowLoad(){
    UIkit.modal($("#modal-session-load")).show()
}

function sessionDisconnect(){
    socketSend({messageType: 1, content: "Disconnected."});
}

function sessionConnect(){
    socketSend({messageType: 0, content: "Connected"});
}

function sessionToggleReady(){
    if (isReady == 0){
        isReady = 1;
        sessionSetMyReady();
    } else {
        sessionSetMyUnready();
        isReady = 0;
    }
}

function sessionSetMyReady(skipAnimation){
    // Update local display
    sessionSetReady(userId,skipAnimation);
    // Send socket message saying we are updating
    $("#button-session-finish").off();
    if (skipAnimation){
        $("#button-session-finish").remove();
    } else {
        $("#button-session-finish").animateCss("bounceOut",function(){
            $("#button-session-finish").remove();
        });
        socketSend({messageType: 4, content: "Ready."});
    }

}

function sessionSetMyUnready(skipAnimation){
    // Update local display
    sessionSetUnready(userId,skipAnimation);
    // send socket message saying we are unready
    $("#button-session-finish").removeClass("button-session-finished").addClass("button-session-unfinished").text("Finish!");
    if (skipAnimation){
    } else {
        socketSend({messageType: 5, content: "Not Ready."});
    }
}

function sessionSetReady(userToSet,skipAnimation){
    if (skipAnimation){
        $("#session-ready-" + userToSet).removeClass("session-unfinished-icon").addClass("session-finished-icon").html("<span uk-icon=\"icon: check\" class=\"session-user-confirm-icon\"></span>");
    } else {
        $("#session-ready-" + userToSet).animateCss("bounceOut", function () {
            $("#session-ready-" + userToSet).animateCss("bounceIn");
            $("#session-ready-" + userToSet).removeClass("session-unfinished-icon").addClass("session-finished-icon").html("<span uk-icon=\"icon: check\" class=\"session-user-confirm-icon\"></span>");
        });
    }
}

function sessionSetUnready(userToSet,skipAnimation){
    if (skipAnimation){
        $("#session-ready-"+userToSet).removeClass("session-finished-icon").addClass("session-unfinished-icon").html("<span uk-icon=\"icon: more\" class=\"session-user-confirm-icon\"></span>");
    } else {
        $("#session-ready-"+userToSet).animateCss("bounceOut",function(){
            $("#session-ready-"+userToSet).animateCss("bounceIn");
            $("#session-ready-"+userToSet).removeClass("session-finished-icon").addClass("session-unfinished-icon").html("<span uk-icon=\"icon: more\" class=\"session-user-confirm-icon\"></span>");
        });
    }
}

function checkObjectChanged(objectOriginal, objectNew){
    return Object.toJSON(objectOriginal) == Object.toJSON(objectNew);
}