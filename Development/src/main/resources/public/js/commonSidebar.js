$(function(){
    $("#create-session-form").submit(function(){
        return false;
    });
    $("#create-session-submit").click(function(){
        var sessionName = $("#sessionNameField").val();
        // On create session submit, call ajax request and then redirect to the session.

        UIkit.modal($("#modal-session-join")).show()

        doAjaxJson("/api/session","POST",{"sessionName":sessionName},function(sessionResults){
            window.location = "/sessions/"+sessionResults.sessionCode;
        })

        return false;
    })
});