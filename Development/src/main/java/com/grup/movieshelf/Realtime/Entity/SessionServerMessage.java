package com.grup.movieshelf.Realtime.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionServerMessage {

    // Message type constants
    public static final int MESSAGE_ERROR = -1;
    public static final int MESSAGE_REFRESH_USERS = 1;
    public static final int MESSAGE_TRIGGER_LOAD = 2;
    public static final int MESSAGE_TRIGGER_RESULTS = 3;
    public static final int MESSAGE_TRIGGER_READY = 4;
    public static final int MESSAGE_TRIGGER_UNREADY = 5;

    private Integer messageType;
    private String content;
}
