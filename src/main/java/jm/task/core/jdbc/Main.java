package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        service.dropUsersTable();
        service.createUsersTable();
        service.saveUser("Oleg", "Kopysov", (byte) 22);
        service.saveUser("Nikita", "Maslenikov", (byte) 22);
        service.saveUser("Steve", "Jobs", (byte) 55);
        service.saveUser("Mark", "Wolberg", (byte) 54);
        List<User> allUsers = service.getAllUsers();
        allUsers.forEach(user -> System.out.println(user.toString()));
        service.cleanUsersTable();
    }
}
