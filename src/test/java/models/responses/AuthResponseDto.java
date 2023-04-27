package models.responses;

import lombok.Data;

@Data
public class AuthResponseDto {
    String error;
    String token;
}
