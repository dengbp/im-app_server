package com.yr.net.app.tools;

import com.yr.net.app.jpa.UserNameEntry;
import com.yr.net.app.jpa.UserNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderedIdUserNameGenerator implements UserNameGenerator {
    @Autowired
    private UserNameRepository userIdRepository;

    @Override
    public String getUserName(String phone) {
        UserNameEntry entry = new UserNameEntry();
        userIdRepository.save(entry);
        return entry.getId() + "";
    }
}
