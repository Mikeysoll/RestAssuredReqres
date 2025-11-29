package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {
    private String email;
    private String password;
}
