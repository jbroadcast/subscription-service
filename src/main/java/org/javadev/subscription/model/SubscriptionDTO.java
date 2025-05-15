package org.javadev.subscription.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javadev.subscription.dataaccess.entity.ServiceName;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

    private Long id;

    private ServiceName serviceName;

    private String description;

    private Long userId;

}
