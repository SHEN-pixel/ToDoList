// src/main/java/com/example/todolist/service/impl/TodoServiceImpl.java
package com.example.todolist.service.Impl;

import com.example.todolist.entity.Todo;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // 标记为 Service 层组件
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;

    // 构造函数注入 Repository
    public TodoServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    @Override
    public Todo addTodo(Todo todo) {
        return repository.save(todo);
    }

    @Override
    public Todo updateTodo(Long id, Todo todo) {
        todo.setId(id);
        return repository.save(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        repository.deleteById(id);
    }
}
