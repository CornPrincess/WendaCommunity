package com.minmin.wenda.model;

import lombok.*;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-08-12
 * Time: 21:54
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
    private int id;
    private int userId;
    private int entityId;
    private int entityType;
    private String content;
    private Date createdDate;
    private int status;
}
