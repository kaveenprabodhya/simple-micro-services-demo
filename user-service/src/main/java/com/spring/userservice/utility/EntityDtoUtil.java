package com.spring.userservice.utility;

import com.spring.userservice.dto.TransactionRequestDTO;
import com.spring.userservice.dto.TransactionResponseDTO;
import com.spring.userservice.dto.TransactionStatus;
import com.spring.userservice.dto.UserDTO;
import com.spring.userservice.entity.User;
import com.spring.userservice.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDtoUtil {
    public static UserDTO toUserDto(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public static User toUserEntity(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    public static UserTransaction toTransactionEntity(TransactionRequestDTO requestDTO){
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserId(requestDTO.getUserId());
        userTransaction.setAmount(requestDTO.getAmount());
        userTransaction.setTransactionDate(LocalDateTime.now());
        return userTransaction;
    }

    public static TransactionResponseDTO toTransactionDTO(TransactionRequestDTO requestDTO, TransactionStatus status){
        TransactionResponseDTO responseDTO = new TransactionResponseDTO();
        responseDTO.setAmount(requestDTO.getAmount());
        responseDTO.setUserId(requestDTO.getUserId());
        responseDTO.setStatus(status);
        return responseDTO;
    }
}
