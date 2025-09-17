package com.lucas.todoapi.runner;

import com.lucas.todoapi.model.Task;
import com.lucas.todoapi.model.User;
import com.lucas.todoapi.repository.TaskRepository;
import com.lucas.todoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestDataRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // Checa se o usuário já existe
        User user;
        if (userRepository.existsByEmail("lucas@email.com")) {
            user = userRepository.findByEmail("lucas@email.com");
        } else {
            user = new User();
            user.setNome("Lucas");
            user.setEmail("lucas@email.com");
            user.setSenha("SenhaForte123!");
            userRepository.save(user);
        }

        // Checa se a tarefa já existe para esse usuário
        String tituloTarefa = "Estudar Spring Boot";
        boolean taskExists = taskRepository.existsByTituloAndUser(tituloTarefa, user);
        if (!taskExists) {
            Task task = new Task();
            task.setTitulo(tituloTarefa);
            task.setDescricao("Aprender JPA e criar API");
            task.setUser(user);
            taskRepository.save(task);
        }

        System.out.println("✅ Usuário e tarefa garantidos no banco!");
    }
}