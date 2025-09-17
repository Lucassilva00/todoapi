package com.lucas.todoapi.DTO;


import com.lucas.todoapi.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskResponse {


    private String titulo;

    private String descricao;

    private Status status;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataTermino;

}
