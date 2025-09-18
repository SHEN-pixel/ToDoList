# 📝 ToDoList 项目 (Spring Boot + MySQL + 原生前端)

## 📖 项目简介 (Introduction)
这是一个简单的 **全栈 ToDoList 应用**，后端使用 **Spring Boot + JPA + MySQL**，前端使用 **HTML + CSS + JavaScript (Fetch API)**。  
This is a simple **full-stack ToDoList application** with **Spring Boot + JPA + MySQL** as the backend, and **HTML + CSS + JavaScript (Fetch API)** as the frontend.

功能 (Features):
- ✅ 添加任务 Add new task  
- ✅ 查看任务列表 View task list  
- ✅ 标记任务完成/未完成 Toggle task status  
- ✅ 删除任务 Delete task  

---

## 📂 项目结构 (Project Structure)

toDoList/
├─ src/
│ ├─ main/
│ │ ├─ java/com/example/todolist/
│ │ │ ├─ controller/ # 控制层 (Controller layer)
│ │ │ │ └─ TodoController.java
│ │ │ ├─ entity/ # 实体层 (Entity layer)
│ │ │ │ └─ Todo.java
│ │ │ ├─ repository/ # 数据访问层 (Repository layer)
│ │ │ │ └─ TodoRepository.java
│ │ │ ├─ service/ # 服务层 (Service layer)
│ │ │ │ └─ Impl/TodoService.java
│ │ │ └─ ToDoListApplication.java # 启动类 (Main Application)
│ │ ├─ resources/
│ │ │ ├─ static/ # 前端静态资源 (Frontend static files)
│ │ │ │ ├─ index.html
│ │ │ │ ├─ style.css
│ │ │ │ └─ app.js
│ │ │ ├─ templates/ # (暂未使用，可放Thymeleaf模板)
│ │ │ └─ application.yml # 配置文件 (Configuration)
└─ pom.xml # Maven 配置






