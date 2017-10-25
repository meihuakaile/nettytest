package pojo;

import lombok.Data;

/**
 * User
 *
 * @author chenliclchen
 * @date 17-10-25 下午6:33
 */
@Data
public class User {
    private String name;
    private int age;
    public User(String name, int age){
        this.name = name;
        this.age = age;
    }
}
