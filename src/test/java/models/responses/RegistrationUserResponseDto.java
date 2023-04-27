package models.responses;

import lombok.Data;

@Data
public class RegistrationUserResponseDto {
    String error;
    String token;
    String id;
}
