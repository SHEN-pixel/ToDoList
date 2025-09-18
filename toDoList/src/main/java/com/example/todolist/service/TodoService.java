
package com.example.todolist.service;

import com.example.todolist.entity.Todo;
import java.util.List;

public interface TodoService {
    Todo addTodo(Todo todo);     // 添加任务
    List<Todo> getAllTodos();    // 查询所有任务
    Todo updateTodo(Long id, Todo todo); // 更新任务
    void deleteTodo(Long id);    // 删除任务
}
