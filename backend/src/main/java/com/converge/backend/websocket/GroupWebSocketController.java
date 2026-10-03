package com.converge.backend.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GroupWebSocketController {

    @MessageMapping("/group/{groupCode}")
    @SendTo("/topic/group/{groupCode}")
    public String groupMessage(
            @DestinationVariable String groupCode,
            String message
    ) {
        return message;
    }
}