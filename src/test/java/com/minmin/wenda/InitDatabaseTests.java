package com.minmin.wenda;

import com.minmin.wenda.dao.LoginTicketDAO;
import com.minmin.wenda.dao.QuestionDAO;
import com.minmin.wenda.dao.UserDAO;
import com.minmin.wenda.model.LoginTicket;
import com.minmin.wenda.model.Question;
import com.minmin.wenda.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
	@Autowired
	UserDAO userDAO;

	@Autowired
	QuestionDAO questionDAO;

	@Autowired
	LoginTicketDAO loginTicketDAO;

	@Before
	public void init_user() {
		Random random = new Random();
		for(int i = 0; i < 11; i++) {
			User user = new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("USER%d", i));
			user.setPassword("");
			user.setSalt("");
			userDAO.addUser(user);
		}
	}

	@Test
	public void initDatabase() {
		Random random = new Random();

		for(int i = 0; i < 11; i++) {
			User user = new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("USER%d", i));
			user.setPassword("");
			user.setSalt("");
			userDAO.addUser(user);


			user.setPassword("loveminmin");
			userDAO.updatePassword(user);

			Question question = new Question();
			question.setCommentCount(i);
			Date date = new Date();
			date.setTime(date.getTime() + 1000*3600*i);
			question.setCreatedDate(date);
			question.setUserId(i+1);
			question.setTitle(String.format("TITLE{%d}", i));
			question.setContent(String.format("love minmin, hahahahh Content %d", i));

			questionDAO.addQuestion(question);
		}

		Assert.assertEquals("loveminmin", userDAO.selectById(1).getPassword());
		userDAO.deleteById(1);

		Assert.assertNull(userDAO.selectById(1));

		System.out.print(questionDAO.selectLatestQuestions(0, 0, 10));

		// test the LoginticketDAO
		LoginTicket loginTicket = new LoginTicket();
		loginTicket.setUserId(1);
		loginTicket.setStatus(1);
		loginTicket.setTicket("123");
		loginTicket.setExpired(new Date());
		loginTicket.setId(1);
		loginTicketDAO.addTicket(loginTicket);
	}

	@Test
	public void test_insert_user() {
		Random random = new Random();
		User user = new User();
		user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
		user.setName(String.format("USER%d", 12));
		user.setPassword("");
		user.setSalt("");
		userDAO.addUser(user);
	}

	@Test
	public void test_update_user() {
		User user = userDAO.selectById(1);
		System.out.println(user);
		user.setPassword("loveminmin");
		userDAO.updatePassword(user);
	}
}
