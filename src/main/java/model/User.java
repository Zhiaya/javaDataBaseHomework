package model;

import lombok.*;
import lombok.experimental.Accessors;
import repository.UserSystemRepository;

import java.util.Scanner;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User checkUser(Scanner input) {
        while (true) {
            System.out.println("Input your information below : ");
            System.out.println("Enter username: ");
            String username = input.nextLine();
            System.out.println("Enter password: ");
            String password = input.nextLine();
            UserSystemRepository userRepository = new UserSystemRepository();
            if (userRepository.loginUser(username, password)) {
                return new User(username, password);
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        }
    }
}
