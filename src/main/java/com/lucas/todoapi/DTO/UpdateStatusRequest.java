package com.lucas.todoapi.DTO;

import com.lucas.todoapi.model.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long userId;

    @NotNull(message = "O status é obrigatório")
    private Status status;
}
