package com.minmin.wenda.model;

import lombok.*;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-08-24
 * Time: 22:10
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private Date createdDate;
    private int hasRead;
    private String conversationId;

    public String getConversationId() {
        if (fromId < toId) {
            return String.format("%d_%d", fromId, toId);
        } else {
            return String.format("%d_%d", toId, fromId);
        }
    }
}
