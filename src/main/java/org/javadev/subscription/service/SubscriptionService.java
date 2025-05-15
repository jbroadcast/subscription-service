package org.javadev.subscription.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javadev.subscription.dataaccess.repository.SubscriptionRepository;
import org.javadev.subscription.mapper.SubscriptionMapper;
import org.javadev.subscription.model.SubscriptionDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    public List<SubscriptionDTO> getTopSubscriptions() {
        Pageable pageable = PageRequest.of(0, 3);
        return subscriptionMapper.toDTOList(subscriptionRepository.findTop3ByUserCount(pageable));
    }
}