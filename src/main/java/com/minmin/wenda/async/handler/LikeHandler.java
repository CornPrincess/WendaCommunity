package com.minmin.wenda.async.handler;

import com.minmin.wenda.async.EventModel;
import com.minmin.wenda.async.EventType;
import com.minmin.wenda.model.Message;
import com.minmin.wenda.model.User;
import com.minmin.wenda.service.MessageService;
import com.minmin.wenda.service.UserService;
import com.minmin.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-09-04
 * Time: 22:24
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("User " +  user.getName() + " like your comment, http://127.0.0.1:8080/question/" + model.getExts().get("questionId") );
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Collections.singletonList(EventType.LIKE);
    }
}
