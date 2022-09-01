package com.example.arcrca;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public final MessageRepository messageRepository;

    public UserService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void deleteUsers(List<Long> ids) {
        ids.forEach(id -> {
            Optional<CustomUser> user = userRepository.findById(id);
            user.ifPresent(u -> {
                if ( ! AppConfig.ADMIN.equals(u.getLogin())) {
                    userRepository.deleteById(u.getId());
                }
            });
        });
    }

    @Transactional
    public boolean addUser(String login, String passHash,
                           UserRole role, String email,
                           String phone,
                           String address) {
        if (userRepository.existsByLogin(login))
            return false;

        CustomUser user = new CustomUser(login, passHash, role, email, phone, address);
        user.asymParty.generateKeys();
        user.asymParty.saveKeys(" Public.key"," Private.key", passHash, user);
        userRepository.save(user);

        return true;
    }

    @Transactional
    public void updateUser(String login, String email, String phone, String address) {
        CustomUser user = userRepository.findByLogin(login);
        if (user == null)
            return;

        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);

        userRepository.save(user);
    }

    @Transactional
    public void sendMessage(String login,Long id, String message){
        CustomUser user1 = userRepository.findByLogin(login);
        Optional<CustomUser> user2 = userRepository.findById(id);
        user2.ifPresent(u -> {
            u.getAsymParty().loadKeys(" Public.key"," Private.key", u.getPassword(), u);
            CustomMessage customMessage = new CustomMessage(u, user1.asymParty.sendMessage(u,message));
            messageRepository.save(customMessage);
        });
    }

    @Transactional
    public String encryptMessage(String login,Long id){
        CustomUser user = userRepository.findByLogin(login);
        Optional<CustomMessage> message = messageRepository.findById(id);
        user.getAsymParty().loadKeys(" Public.key"," Private.key", user.getPassword(), user);
        return user.asymParty.receiveMessage(message.get().getMessage());
    }


    @Transactional(readOnly = true)
    public List<CustomMessage> findAllById(Long id) {

        return messageRepository.findAllByLogin(id);
    }

}
