const API_URL = '/api/todos'  // 对应后端接口

// 获取并渲染任务
async function fetchTodos() {
    const res = await fetch(API_URL)
    const todos = await res.json()
    const ul = document.getElementById('todoList')
    ul.innerHTML = ''

    todos.forEach(todo => {
        const li = document.createElement('li')

        // checkbox
        const checkbox = document.createElement('input')
        checkbox.type = 'checkbox'
        checkbox.checked = todo.completed
        checkbox.addEventListener('change', () => toggleTodo(todo, checkbox.checked))

        // 任务文字
        const span = document.createElement('span')
        span.textContent = todo.task
        if (todo.completed) span.classList.add('done')

        // 删除按钮
        const delBtn = document.createElement('button')
        delBtn.textContent = '删除'
        delBtn.addEventListener('click', () => deleteTodo(todo.id))

        li.appendChild(checkbox)
        li.appendChild(span)
        li.appendChild(delBtn)
        ul.appendChild(li)
    })
}

// 添加任务
async function addTask() {
    const input = document.getElementById('newTask')
    const task = input.value.trim()
    if (!task) return
    await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ task, completed: false })
    })
    input.value = ''
    fetchTodos()
}

document.getElementById('addBtn').addEventListener('click', addTask)
document.getElementById('newTask').addEventListener('keypress', e => {
    if (e.key === 'Enter') addTask()
})

// 切换完成状态（使用后端 PUT 更新）
async function toggleTodo(todo, completed) {
    await fetch(`${API_URL}/${todo.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id: todo.id, task: todo.task, completed })
    })
    fetchTodos()
}

// 删除任务
async function deleteTodo(id) {
    await fetch(`${API_URL}/${id}`, { method: 'DELETE' })
    fetchTodos()
}

// 页面加载
window.onload = fetchTodos
