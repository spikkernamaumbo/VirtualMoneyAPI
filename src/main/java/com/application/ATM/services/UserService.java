package com.application.ATM.services;


import com.application.ATM.User.userApiutils.APIResponses;
import com.application.ATM.models.User;
import com.application.ATM.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public  UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void RegisterUser(User user){
        Optional<User> userRepositoryByPhoneNumber = userRepository.findByPhoneNumber(user.getPhoneNumber());

        if (userRepositoryByPhoneNumber.isPresent()){
            throw  new IllegalStateException(" user already available");
        }

        userRepository.save(user);
    }

    protected void DeleteUser(User user) {
        userRepository.findByPhoneNumber(user.getPhoneNumber()).
                orElseThrow(() -> new EntityNotFoundException("NO user with the phone number " + user.getPhoneNumber()));
        userRepository.delete(user);
    }
    protected User getOneUser(int id ){
        return userRepository.findById(id).orElse(null);
    }

//    get current balance of a user specified
    public ResponseEntity<Object> getCurrentBalance(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
//            no relations
            return APIResponses.generateResponse(null, HttpStatus.OK,userOptional.map(User::getFirstName));
        }
        return null;
    }

//    adding money to walet
//    public ResponseEntity<Object> addMoneyToWallet(String accountNumber){
//        Optional<User> userOptional = userRepository.findByAccountNumber(accountNumber);
//        if(!userOptional.isPresent()){
//            return APIResponses.generateResponse("no user",HttpStatus.NOT_FOUND,null);
//        }
//        double moneyAdded = 198+345;
//        return APIResponses.generateResponse("money added successfuly",HttpStatus.OK,moneyAdded);
//
//    }
}
