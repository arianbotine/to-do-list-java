package com.java.todolist.controller;

import com.java.todolist.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void deveCriarTodoComSucesso() {
        Todo todo = Todo.builder()
                .nome("Casa")
                .descricao("Limpar chão da cozinha")
                .prioridade(1)
                .realizado(false)
                .build();

        webTestClient
                .post()
                .uri("/todos")
                .bodyValue(todo)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].nome").isEqualTo(todo.getNome())
                .jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
                .jsonPath("$[0].prioridade").isEqualTo(todo.getPrioridade())
                .jsonPath("$[0].realizado").isEqualTo(todo.isRealizado());

    }

    @Test
    public void naoDeveCriarTodo() {
        Todo todo = Todo.builder()
                .nome("")
                .descricao("")
                .prioridade(1)
                .build();

        webTestClient
                .post()
                .uri("/todos")
                .bodyValue(todo)
                .exchange()
                .expectStatus().isBadRequest();

    }
}