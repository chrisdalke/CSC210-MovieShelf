///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Realtime;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////
// Sessions Socket Manager
// Handles realtime bidirectional communication for sessions.
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.Realtime.Entity.SessionMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SessionSocketController {


    // Controller URL formats;
    // READS from /session/client/{sessionId}
    // WRITES to /session/server/{sessionId}

    /*

    public static final int MESSAGE_CONFIRM = -2;
    public static final int MESSAGE_ERROR = -1;
    public static final int MESSAGE_JOIN = 0;
    public static final int MESSAGE_LEAVE = 1;
    public static final int MESSAGE_ADD_MOVIE = 2;
    public static final int MESSAGE_REMOVE_MOVIE = 3;
    public static final int MESSAGE_READY = 4;
    public static final int MESSAGE_READY_CANCEL = 5;
    public static final int MESSAGE_FLAIR = 6;

    public static final int MESSAGE_TEST = 999;
     */

    @MessageMapping("/session/{sessionId}")
    @SendTo("/topic/session/{sessionId}")
    public SessionMessage sessionMessageManager(@DestinationVariable String sessionId,SessionMessage clientMessage){

        switch (clientMessage.getMessageType()){
            case SessionMessage.MESSAGE_JOIN: {
                return new SessionMessage(SessionMessage.MESSAGE_CONFIRM,"Confirmed.");
            }
            case SessionMessage.MESSAGE_LEAVE: {
                return new SessionMessage(SessionMessage.MESSAGE_CONFIRM,"Confirmed.");
            }
            case SessionMessage.MESSAGE_ADD_MOVIE: {
                return new SessionMessage(SessionMessage.MESSAGE_CONFIRM,"Confirmed.");
            }
            case SessionMessage.MESSAGE_REMOVE_MOVIE: {
                return new SessionMessage(SessionMessage.MESSAGE_CONFIRM,"Confirmed.");
            }
            case SessionMessage.MESSAGE_READY: {
                return new SessionMessage(SessionMessage.MESSAGE_CONFIRM,"Confirmed.");
            }
            case SessionMessage.MESSAGE_READY_CANCEL: {
                return new SessionMessage(SessionMessage.MESSAGE_CONFIRM,"Confirmed.");
            }
            case SessionMessage.MESSAGE_FLAIR: {
                return new SessionMessage(SessionMessage.MESSAGE_CONFIRM,"Confirmed.");
            }
            case SessionMessage.MESSAGE_TEST: {
                return new SessionMessage(SessionMessage.MESSAGE_CONFIRM,"TEST SUCCESS MESSAGE.");
            }
            default: {
                //Unknown message.
                return new SessionMessage(SessionMessage.MESSAGE_ERROR,"Unknown message sent by client.");
            }
        }


    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////