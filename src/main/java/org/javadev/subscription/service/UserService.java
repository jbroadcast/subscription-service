package org.javadev.subscription.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javadev.subscription.dataaccess.entity.Subscription;
import org.javadev.subscription.dataaccess.entity.User;
import org.javadev.subscription.dataaccess.repository.SubscriptionRepository;
import org.javadev.subscription.dataaccess.repository.UserRepository;
import org.javadev.subscription.exception.SubscriptionAlreadyExistsException;
import org.javadev.subscription.exception.SubscriptionException;
import org.javadev.subscription.exception.UserNotFoundException;
import org.javadev.subscription.mapper.SubscriptionMapper;
import org.javadev.subscription.mapper.UserMapper;
import org.javadev.subscription.model.SubscriptionDTO;
import org.javadev.subscription.model.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final UserMapper userMapper;

    private final SubscriptionMapper subscriptionMapper;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating user: {}", userDTO);
        User user = userMapper.toEntity(userDTO);
        User createdUser = userRepository.save(user);
        log.info("User created: {}", createdUser);
        return userMapper.toDTO(createdUser);
    }

    public UserDTO getUserById(Long id) {
        log.info("Getting user with id: {}", id);
        return userMapper.toDTO(findUserById(id));
    }

    @Transactional
    public SubscriptionDTO addSubscription(Long userId, SubscriptionDTO subscriptionDTO) {
        log.info("Adding subscription {} for userId {}", subscriptionDTO, userId);
        // Находим пользователя
        User user = findUserById(userId);
        // Проверяем, существует ли уже подписка с таким же serviceName
        Optional<Subscription> existingSubscription =
            subscriptionRepository.findByUserIdAndServiceName(userId, subscriptionDTO.getServiceName());
        if (existingSubscription.isPresent()) {
            log.warn("Subscription for service {} already exists for userId {}", subscriptionDTO.getServiceName(), userId);
            throw new SubscriptionAlreadyExistsException("Subscription for this service already exists for the user.");
        }
        // Создаем новую подписку
        Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO);
        subscription.setUser(user);
        Subscription createdSubscription = subscriptionRepository.save(subscription);
        log.info("Subscription created: {}", createdSubscription);
        return subscriptionMapper.toDTO(createdSubscription);
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        log.info("Updating user with id: {}", userId);
        User user = findUserById(userId);
        // Проверяем, были ли изменения
        if (!hasChanges(user, userDTO)) {
            log.info("No changes detected for user with id: {}", userId);
            return userMapper.toDTO(user);
        }
        // Обновляем данные пользователя
        updateUserDetails(user, userDTO);
        // Сохраняем изменения в базе данных
        return userMapper.toDTO(userRepository.save(user));
    }

    private boolean hasChanges(User user, UserDTO userDTO) {
        boolean nameChanged = userDTO.getName() != null && !userDTO.getName().isEmpty()
            && !user.getUserName().equals(userDTO.getName());
        boolean emailChanged = userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()
            && !user.getEmail().equals(userDTO.getEmail());
        return nameChanged || emailChanged;
    }

    private void updateUserDetails(User user, UserDTO userDTO) {
        if (userDTO.getName() != null && !userDTO.getName().isEmpty()) {
            user.setUserName(userDTO.getName());
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            user.setEmail(userDTO.getEmail());
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

    public Set<SubscriptionDTO> getSubscriptions(Long userId) {
        User user = findUserById(userId);
        return subscriptionMapper.toDTOSet(user.getSubscriptions());
    }

    @Transactional
    public void deleteSubscription(Long userId, Long subscriptionId) {
        log.info("Deleting subscription id: {} for user with id: {}", subscriptionId, userId);
        User user = findUserById(userId);
        // Находим подписку
        Subscription subscription = user.getSubscriptions().stream()
            .filter(sub -> sub.getId().equals(subscriptionId))
            .findFirst()
            .orElseThrow(() -> new SubscriptionException("Subscription not found"));
        // Удаляем подписку из коллекции пользователя
        user.getSubscriptions().remove(subscription);
        // Удаляем подписку из базы данных
        subscriptionRepository.delete(subscription);
        userRepository.save(user);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}