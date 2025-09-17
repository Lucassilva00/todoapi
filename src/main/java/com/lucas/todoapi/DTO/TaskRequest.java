package com.lucas.todoapi.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

    @NotBlank(message = "O título é obrigatório!")
    private String titulo;

    private String descricao;

    @NotNull(message = "O ID do usuário é obrigatório!")
    private Long userId;
}
