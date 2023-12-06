package com.zoohee.myrestfulservice.dao;

import com.zoohee.myrestfulservice.bean.User;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "alice", new Date()));
        users.add(new User(2, "hihi", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User save(User user) {
        if(user.getId() == null) {
            user.setId(++usersCount);
        }

        if (user.getJoinDate() == null) {
            user.setJoinDate(new Date());
        }

        users.add(user);

        return user;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()) {
            User user = iterator.next();

            if(user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }
}
