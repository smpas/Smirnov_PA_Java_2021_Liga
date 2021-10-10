package com.example.socialnetwork;

import com.example.socialnetwork.entity.*;
import com.example.socialnetwork.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.List;

@SpringBootApplication
public class SocialNetworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(SchoolRepository schoolRepository, ClientRepository clientRepository,
                                  PostRepository postRepository, FriendRepository friendRepository,
                                  DialogRepository dialogRepository, MessageRepository messageRepository) {
        return (args) -> {
            School sc45 = new School("45", "koroleva");
            School sc59 = new School("59", "parashutnaya");

            Client client1 = new Client("pavel", "smirnov", "smpas", "m", sc45);
            Client client2 = new Client("alexey", "petrov", "smalex", "m", sc59);

            schoolRepository.save(sc45);
            schoolRepository.save(sc59);

            clientRepository.save(client1);
            clientRepository.save(client2);

            Post post = new Post("hello", "hello everyone!");
            post.setClient(client2);
            post.setDate(Timestamp.valueOf("2021-08-11 17:05:33"));
            postRepository.save(post);
            schoolRepository.delete(sc45);

            Friend friendship = new Friend(client1, client2);
            Friend friendship1 = new Friend(client2, client1);
            friendRepository.save(friendship);
            friendRepository.save(friendship1);
//            friendRepository.deleteFriendByFirstClient(client1);

            Dialog dialog = new Dialog("pasha and lesha");
            dialog.setClients(List.of(client1, client2));
            dialogRepository.save(dialog);

            Message message = new Message(client1, dialog, Timestamp.valueOf("2021-09-11 19:03:10"), "hi");
            Message message1 = new Message(client2, dialog, Timestamp.valueOf("2021-09-11 19:03:10"), "hello");
            Message message2 = new Message(client1, dialog, Timestamp.valueOf("2021-09-11 19:03:10"), "how are you");
            messageRepository.save(message);
            messageRepository.save(message1);
            messageRepository.save(message2);

        };
    }
}
