package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Иван", "Иванов", (byte) 5);
        userService.saveUser("Степан", "Степанов", (byte) 10);
        userService.saveUser("Николай", "Николаев", (byte) 15);
        userService.saveUser("Андрей", "Андреев", (byte) 20);

//        userService.removeUserById(3);
//
//        List<User> list = userService.getAllUsers();
//        for(User user: list) {
//            System.out.println(user.getName());
//        }
//        userService.cleanUsersTable();
//        userService.cleanUsersTable();
        userService.dropUsersTable();
        userService.dropUsersTable();

    }
}
