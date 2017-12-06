$(function(){
    $("#session-code-form").submit(function(e){
        var sessionCode = $("#session-code-value").val();
        joinSession(sessionCode);

        e.preventDefault();
    });

    $("#session-code-close").click(function(){
        resetSessionModal();
    })
});

function clearSessionCode() {
    $("#session-code-value").val("");
}

function joinSession(sessionCode) {
    $("#session-code-label").text("Joining Session...");
    $('#session-code-label').animateCss('zoomIn');
    $('#session-code-close').hide();

    doAjax(
        "/api/public/session/"+sessionCode+"/join_check",
        "GET",
        function(successMessage){
            resetSessionModal();
        },
        function(failureMessage){
            setTimeout(function(){
                resetSessionModal();
                $("#session-code-label").text(failureMessage.message);
            }, 1000);
        });
}

function resetSessionModal(){
    $("#session-code-label").text("Enter Session Code:");
    $('#session-code-label').animateCss('zoomIn');
    clearSessionCode();
    $('#session-code-close').show();
}

