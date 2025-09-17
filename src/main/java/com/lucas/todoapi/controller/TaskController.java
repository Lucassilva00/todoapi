package com.lucas.todoapi.controller;

import com.lucas.todoapi.DTO.TaskRequest;
import com.lucas.todoapi.DTO.TaskResponse;
import com.lucas.todoapi.DTO.UpdateStatusRequest;
import com.lucas.todoapi.model.Status;
import com.lucas.todoapi.model.Task;
import com.lucas.todoapi.model.User;
import com.lucas.todoapi.repository.TaskRepository;
import com.lucas.todoapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskRequest taskRequest){

        User user = userRepository.findById(taskRequest.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuário não encontrado!"));

        if (taskRepository.existsByTituloAndUser(taskRequest.getTitulo(), user)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você já possui uma tarefa com este nome.");
        }

        Task task = new Task();

        task.setTitulo(taskRequest.getTitulo());
        task.setDescricao(taskRequest.getDescricao());
        task.setStatus(Status.NAO_INICIADA);
        task.setUser(user);


        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Tarefa "+task.getTitulo()+ " criada com sucesso! ");
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> listTasks(@RequestParam Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));

        List<Task> tasks = taskRepository.findByUserId(userId);

        if (tasks.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<TaskResponse> responseList = tasks.stream()
                .map(task -> {
                TaskResponse tr = new TaskResponse();
                tr.setTitulo(task.getTitulo());
                tr.setDescricao(task.getDescricao());
                tr.setStatus(task.getStatus());
                tr.setDataCriacao(task.getDataCriacao());
                tr.setDataTermino(task.getDataTermino());
                return tr;
            }).toList();
        return ResponseEntity.ok(responseList);

    }

    @PatchMapping("{taskId}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long taskId,
                                             @Valid @RequestBody UpdateStatusRequest updateStatusRequest){
        //1. Buscar task no banco
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada."));

        //2. Validar que a task pertence aquele usuário
        if(!task.getUser().getId().equals(updateStatusRequest.getUserId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para alterar esta tarefa.");
        }

        //3. Atualizar status
        task.setStatus(updateStatusRequest.getStatus());

        //4. Status concluido, criar timestamp
        if (updateStatusRequest.getStatus() == Status.CONCLUIDA){
            task.setDataTermino(LocalDateTime.now());
        }else task.setDataTermino(null);

        //5. Salvar atualização
        taskRepository.save(task);

        //6. Retornar sucesso na atualização do status da task
        return ResponseEntity.ok("Status atualizado para "+task.getStatus()+", com sucesso!");
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId,
                                             @RequestParam Long userId){

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada."));

        if (!task.getUser().getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para excluir esta tarefa.");
        }

        taskRepository.delete(task);

        return ResponseEntity.ok("Tarefa excluída com sucesso!");
    }

}
