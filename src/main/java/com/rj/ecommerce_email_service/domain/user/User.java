package com.rj.ecommerce_email_service.domain.user;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private Long id;

    private String firstName;
    private String lastName;

    private Email email;

}
