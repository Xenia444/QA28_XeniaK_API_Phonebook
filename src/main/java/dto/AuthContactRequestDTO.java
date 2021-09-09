package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString

public class AuthContactRequestDTO {

    String address;
    String description;
    String email;
    Integer id;
    String lastName;
    String name;
    String phone;
}
