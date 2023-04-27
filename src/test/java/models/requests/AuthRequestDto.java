package models.requests;

import lombok.Data;

@Data
public class AuthRequestDto {
    String email, password;
}
