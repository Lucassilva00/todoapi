# Todo API 📝

## Descrição
API RESTful simples para gerenciamento de tarefas (ToDo). Permite criar usuários, registrar tarefas, listar, atualizar status e excluir tarefas. Feita com **Spring Boot**, **JPA/Hibernate** e **H2/MySQL**.

Funcionalidades:
- Cadastro de usuários com validação de e-mail e senha forte
- Login de usuários
- Criação de tarefas (título obrigatório, descrição opcional)
- Listagem de tarefas por usuário
- Atualização do status da tarefa
- Exclusão de tarefas

## Tecnologias
- Java 17
- Spring Boot
- Spring Data JPA
- Bean Validation
- Spring Security (sem JWT)
- Banco de dados: H2 ou MySQL

## Endpoints principais
- **POST /users/register** – Cadastrar usuário
- **POST /auth/login** – Login do usuário
- **POST /tasks** – Criar tarefa
- **GET /tasks?userId={id}** – Listar tarefas do usuário
- **PATCH /tasks/{taskId}/status** – Atualizar status da tarefa
- **DELETE /tasks/{taskId}?userId={id}** – Excluir tarefa

## Como rodar
1. Clonar o repositório:
```bash
git clone https://github.com/seu-usuario/todo-api.git
```
2. Configurar o banco de dados (H2 ou MySQL)
3. Rodar o projeto na IDE ou via terminal:
```bash
mvn spring-boot:run
```
4. Testar os endpoints com Postman ou Insomnia

## Observações
- Senhas são criptografadas com BCrypt
- Status das tarefas é um enum (NAO_INICIADA, EM_ANDAMENTO, CONCLUIDA)
- Validações aplicadas: e-mail válido, senha forte (letra maiúscula, letra minúscula, número, caractere especial),
título de tarefa único por usuário