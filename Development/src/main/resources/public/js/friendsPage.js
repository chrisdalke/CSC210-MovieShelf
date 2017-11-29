/**
 * Created by chrisdalke on 11/15/17.
 */

$( "#remove" ).on ("click", function(event) {
    //found this function at - https://stackoverflow.com/questions/487056/retrieve-button-value-with-jquery
    var getButtonValue = function(event) {
        var label = event.text();
        event.text('');
        var buttonValue = event.val();
        event.text(label);
        return buttonValue;
    }
    $.get( "http://localhost:8080/api/friends", function( getButtonValue ) {
      // how to run removeFriend?
    });
});