package us.lsi.dp1.newcorporder.match.chat;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class Message {

    private String sender;
    private Instant at;
    private String message;

}
