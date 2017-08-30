package com.cretin.service;

import com.cretin.annotation.Tran;
import com.cretin.dao.UserDao;
import com.cretin.domain.User;
import com.cretin.factory.BasicFactory;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserServiceImpl implements UserService {
    private UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);

    @Tran
    @Override
    public void register(User user) {
        try {
            //校验用户名是否已经存在
            if ( userDao.findUserByName(user.getUsername()) != null ) {
                //用户存在
                throw new RuntimeException("用户名已经存在");
            }

            //注册
            user.setRole("user");
            user.setState(0);
            user.setActivecode(UUID.randomUUID().toString());
            userDao.addUser(user);

            //3.发送激活邮件
            Properties prop = new Properties();
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.host", "smtp.163.com");
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.debug", "true");
            Session session = Session.getInstance(prop);

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("mxnzp_life@163.com"));
            msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(user.getEmail()));
            msg.setSubject(user.getUsername() + ",来自estore的激活邮件");
            msg.setText(user.getUsername() + ",点击如下连接激活账户,如果不能点击请复制到浏览器地址栏访问: http://localhost:8080/ActiveServlet?activecode=" + user.getActivecode());

            Transport trans = session.getTransport();
            trans.connect("mxnzp_life@163.com", "cretin273846");
            trans.sendMessage(msg, msg.getAllRecipients());

        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public User active(String activeCode) {
        User user = userDao.findUserByActivecode(activeCode);
        //激活码不存在
        if ( user == null ) {
            throw new RuntimeException("激活码不存在，请检查您的激活码！");
        }
        //用户已经激活成功 提示不要再重复激活
        if ( user.getState() != 0 ) {
            throw new RuntimeException("用户已经激活，请不要重复激活，请直接登录！");
        }
        //激活码超时
        if ( System.currentTimeMillis() - user.getUpdatetime().getTime() > 1000 * 3600 * 24 ) {
            userDao.delete(user.getId());
            throw new RuntimeException("激活码超时，此用户作废，请重新注册");
        }
        //激活用户
        userDao.updateState(user.getId(), 1);
        user.setState(1);
        return user;
    }

    @Override
    public User getUserByNameAndPsw(String username, String password) {
        return userDao.getUserByNameAndPsw(username, password);
    }
}
