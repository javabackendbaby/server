package com.project.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Номер телефона", example = "+79999999999")
    @Size(min = 7, max = 12, message = "Номер телефона должен содержать от 7 до 12 символов")
    @NotBlank(message = "Номер телефона не может быть пустыми")
    private String phoneNumber;

    @Schema(description = "Имя пользователя", example = "peter")
    @Size(min = 1, max = 30, message = "Имя пользователя должно содержать от 1 до 30 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}