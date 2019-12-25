package org.zdxue.zk.console.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.zdxue.zk.console.model.User;
import org.zdxue.zk.console.model.UserRole;
import org.zdxue.zk.console.service.LoginService;
import org.zdxue.zk.console.springboot.autoconfigure.ZKConsoleProperties;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author xuezhongde
 */
@Service
public class LoginServiceImpl implements LoginService, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Resource
    private ZKConsoleProperties zkConsoleProperties;

    FileBasedUserInfoStore userInfoStore;

    @Override
    public User login(String username, String password) {
        return userInfoStore.getUser(username, password);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        userInfoStore = new FileBasedUserInfoStore(zkConsoleProperties);
    }

    private static class FileBasedUserInfoStore {
        private static final String FILE_NAME = "users.properties";
        private static final Map<String, User> USER_MAP = new ConcurrentHashMap<>();
        private static final ScheduledExecutorService watcher = Executors.newScheduledThreadPool(1);

        private String filePath;

        FileBasedUserInfoStore(ZKConsoleProperties zkConsoleProperties) {
            filePath = zkConsoleProperties.getDataPath() + File.separator + FILE_NAME;

            if (new File(filePath).exists()) {
                load();
                watch();
            } else {
                InputStream inputStream = getClass().getResourceAsStream("/" + FILE_NAME);
                if (inputStream == null) {
                    logger.error("Can not found the file {} in Spring Boot jar", FILE_NAME);
                    System.out.println(String.format("Can not found the file %s in Spring Boot jar", FILE_NAME));
                    System.exit(-1);
                }
                load(inputStream);
            }
        }

        void watch() {
            watcher.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    load();
                }
            }, 1, 5, TimeUnit.SECONDS);
        }

        void load() {
            load(null);
        }

        void load(InputStream inputStream) {
            Properties props = new Properties();
            try {
                if (inputStream == null) {
                    props.load(new FileReader(filePath));
                } else {
                    props.load(inputStream);
                }
            } catch (IOException e) {
                logger.error("load error: " + filePath, e);
                throw new RuntimeException(e);
            }

            String[] info;
            UserRole role;
            Map<String, User> users = new HashMap<>();
            for (String propName : props.stringPropertyNames()) {
                String propValue = props.getProperty(propName);
                info = propValue.split(",", 2);
                if (info.length <= 0) {
                    continue;
                } else if (info.length == 1) {
                    role = UserRole.ORDINARY;
                } else {
                    role = UserRole.of(Integer.parseInt(info[1]));
                }

                User user = new User(propName, info[0], role);
                users.put(propName, user);
            }

            USER_MAP.clear();
            USER_MAP.putAll(users);
        }

        User getUser(String username, String password) {
            User user = USER_MAP.get(username);
            if (user != null && user.getPassword().equals(password)) {
                return user.cloneUser();
            }

            return null;
        }
    }

}
