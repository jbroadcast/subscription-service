package org.javadev.subscription.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javadev.subscription.model.SubscriptionDTO;
import org.javadev.subscription.model.UserDTO;
import org.javadev.subscription.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id); // Получаем пользователя напрямую
        return ResponseEntity.ok(userDTO); // Возвращаем пользователя в ответе
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/subscriptions")
    public ResponseEntity<SubscriptionDTO> addSubscription(
            @PathVariable Long id,
            @RequestBody SubscriptionDTO subscriptionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addSubscription(id, subscriptionDTO));
    }

    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<Set<SubscriptionDTO>> getSubscriptions(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getSubscriptions(id));
    }

    @DeleteMapping("/{id}/subscriptions/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id, @PathVariable Long subscriptionId) {
        userService.deleteSubscription(id, subscriptionId);
        return ResponseEntity.noContent().build();
    }

}