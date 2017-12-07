package com.grup.movieshelf.Realtime.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionMessage {

    // Message type constants
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

    private Integer messageType;
    private String content;
}
