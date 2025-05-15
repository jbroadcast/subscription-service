package org.javadev.subscription.mapper;

import lombok.RequiredArgsConstructor;
import org.javadev.subscription.dataaccess.entity.Subscription;
import org.javadev.subscription.dataaccess.entity.User;
import org.javadev.subscription.dataaccess.repository.UserRepository;
import org.javadev.subscription.exception.UserNotFoundException;
import org.javadev.subscription.model.SubscriptionDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper implements Mapper<Subscription, SubscriptionDTO> {

    private final UserRepository userRepository;

    @Override
    public SubscriptionDTO toDTO(Subscription subscription) {
        if (subscription == null) {
            return null;
        }
        return SubscriptionDTO.builder()
            .id(subscription.getId())
            .serviceName(subscription.getServiceName())
            .description(subscription.getDescription())
            .userId(subscription.getUser() != null ? subscription.getUser().getId() : null)
            .build();
    }

    @Override
    public Subscription toEntity(SubscriptionDTO subscriptionDTO) {
        if (subscriptionDTO == null) {
            return null;
        }
        Subscription subscription = Subscription.builder()
            .id(subscriptionDTO.getId())
            .serviceName(subscriptionDTO.getServiceName())
            .description(subscriptionDTO.getDescription())
            .build();

        // Устанавливаем пользователя, если userId присутствует
        if (subscriptionDTO.getUserId() != null) {
            User user = userRepository.findById(subscriptionDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(subscriptionDTO.getUserId()));
            subscription.setUser(user);
        }
        return subscription;
    }

    public Set<SubscriptionDTO> toDTOSet(Set<Subscription> subscriptions) {
        return subscriptions.stream()
            .map(this::toDTO)
            .collect(Collectors.toSet());
    }

    public List<SubscriptionDTO> toDTOList(List<Subscription> subscriptions) {
        return subscriptions.stream()
            .map(this::toDTO)
            .toList();
    }

    public Set<Subscription> toEntitySet(Set<SubscriptionDTO> subscriptionDTOs) {
        return subscriptionDTOs.stream()
            .map(this::toEntity)
            .collect(Collectors.toSet());
    }
}