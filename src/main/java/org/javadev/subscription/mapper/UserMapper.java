package org.javadev.subscription.mapper;

import org.javadev.subscription.dataaccess.entity.Subscription;
import org.javadev.subscription.dataaccess.entity.User;
import org.javadev.subscription.model.SubscriptionDTO;
import org.javadev.subscription.model.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .name(user.getUserName())
            .email(user.getEmail())
            .subscriptions(user.getSubscriptions() != null ? user.getSubscriptions().stream()
                .map(subscription -> SubscriptionDTO.builder()
                    .id(subscription.getId())
                    .serviceName(subscription.getServiceName())
                    .description(subscription.getDescription())
                    .userId(subscription.getUser().getId())
                    .build())
                .collect(Collectors.toSet()) : null)
            .build();
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = User.builder()
            .id(userDTO.getId())
            .userName(userDTO.getName())
            .email(userDTO.getEmail())
            .build();

        if (userDTO.getSubscriptions() != null) {
            Set<Subscription> subscriptions = userDTO.getSubscriptions().stream()
                .map(subscriptionDTO -> Subscription.builder()
                    .id(subscriptionDTO.getId())
                    .serviceName(subscriptionDTO.getServiceName())
                    .description(subscriptionDTO.getDescription())
                    .user(user) // Устанавливаем связь с пользователем
                    .build())
                .collect(Collectors.toSet());
            user.setSubscriptions(subscriptions);
        }
        return user;
    }
}