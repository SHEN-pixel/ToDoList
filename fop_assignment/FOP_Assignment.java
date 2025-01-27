package fop_assignment;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class FOP_Assignment {
    public static void main(String[] args) {
        System.out.print("Please enter your email address(xxxxxx@gmail.com): ");
        String email = sc.nextLine();
        
        loadTasksFromFile();
        while(true){
            menu();
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println();
                    newTask();
                    saveTasksToFile();
                    break;
                case 2:
                    System.out.println();
                    markTask();
                    saveTasksToFile();
                    break;
                case 3:
                    System.out.println();
                    deleteTask();
                    saveTasksToFile();
                    break;
                case 4:
                    System.out.println();
                    sortTask();
                    saveTasksToFile();
                    break;
                case 5:
                    System.out.println();
                    searchTask();
                    saveTasksToFile();
                    break;
                case 6:
                    System.out.println();
                    recurringTask();
                    saveTasksToFile();
                    break;
                case 7:
                    System.out.println();
                    viewAllTask();
                    saveTasksToFile();
                    break;
                case 8:
                    System.out.println();
                    editTask();
                    saveTasksToFile();
                    break;
                case 9:
                    System.out.println();
                    dataAnalytics();
                    saveTasksToFile();
                    break;
                case 10:
                    System.out.println();
                    System.out.println("Exiting application...");
                    emailNotification(email);
                    saveTasksToFile();
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    private static Scanner sc = new Scanner(System.in);
    public static ArrayList <Task> list = new ArrayList<>();
    
    public static void menu(){
        System.out.println("=== To-Do List Menu ===");
        System.out.println("1. Add a New Task");
        System.out.println("2. Mark Task as Complete");
        System.out.println("3. Delete a Task");
        System.out.println("4. Sort Tasks");
        System.out.println("5. Search Tasks");
        System.out.println("6. Add a Recurring Task");
        System.out.println("7. View All Tasks");
        System.out.println("8. Edit Task");
        System.out.println("9. Analytics Dashboard");
        System.out.println("10. Exit");
        System.out.print("Choose an option: ");
   }
    
    //Method to add a new task
    public static void newTask(){        
        System.out.println("=== Add a New Task ===");
        //Enter task title
        String title;
        while (true) {
            System.out.print("Enter task title: ");
            title = sc.nextLine();
            if (!title.isEmpty()) {
                break;
            } else {
                System.out.println("Error: Task title cannot be empty. Please enter a valid title.");
            }
        }
       
        //Enter tast description
        System.out.print("Enter task description: ");
        String description = sc.nextLine();
       
        //Enter duedate (must follow format)
        String duedate;
        while (true) {
            System.out.print("Enter due date (YYYY-MM-DD): ");
            duedate = sc.nextLine();

            // Check if it matches the basic date format (YYYY-MM-DD)
            if (Pattern.matches("\\d{4}-\\d{2}-\\d{2}", duedate)) {
                String[] parts = duedate.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);

                // Check that the year, month, and day are within valid ranges
                if (year == 0) 
                    System.out.println("Error: Year cannot be 0.");
                 
                else if (month < 1 || month > 12) 
                    System.out.println("Error: Month must be between 01 and 12.");
                 
                else if (day < 1 || day > getDaysInMonth(month, year)) 
                    System.out.println("Error: Invalid day for the given month.");
                 
                else {
                    // Parse the due date as a LocalDate
                    LocalDate dueDateParsed = LocalDate.parse(duedate);
                    LocalDate currentDate = LocalDate.now();

                    // Check if the due date is later than the current date
                    if (dueDateParsed.isBefore(currentDate)) 
                        System.out.println("Error: The due date must be later than today.");
                    
                    else 
                        break; // Valid date                    
                }                
            } 
            else
                System.out.println("Error: Please enter a valid date in YYYY-MM-DD format.");
            
        }
       
        //Enter task category (must match with either one category)
        String category;
        while(true){
            System.out.print("Enter task category (Homework, Personal, Work):  ");
            category = sc.nextLine();
            if(category.equalsIgnoreCase("Homework") || category.equalsIgnoreCase("Personal") || category.equalsIgnoreCase("Work")){
                break;
            }
            else{
                System.out.println("Error: Please select from the list(Homework, Personal, Work)");
            }
        }       
        //Enter priority level
        String priority;
        while(true){
            System.out.print("Priority level (Low, Medium, High):  ");
            priority = sc.nextLine();
            if(priority.equalsIgnoreCase("Low") || priority.equalsIgnoreCase("Medium") || priority.equalsIgnoreCase("High"))
                break;
            else{
                System.out.println("Error: Please select from the list(Low, Medium, High)");
            }
        }
        
        String currentDate = LocalDate.now().toString();
        //Shows task is added succesfully
        Task task = new Task(title, description, duedate, category, priority, currentDate);
        list.add(task);
        System.out.println("\nTask \"" + title + "\" added successfully!"); 
        System.out.println();
   }
    
    // Method to determine number of days in a month
    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return (isLeapYear(year)) ? 29 : 28;
            default:
                return 0;  // Invalid month, should never happen due to previous checks
        }
    }

    // Method to determine if a year is a leap year
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }
    
    //Method to view all task
    public static void viewAllTask(){
        System.out.println("=== View All Tasks ===");
        if(list.isEmpty()){
            System.out.println("No task found");
        }
        else{
            for(int i=0 ; i<list.size() ; i++){
                System.out.println((i+1) + ". " + list.get(i));
            }
        }
        System.out.println();
    }
    
    //Method to mark task as completed
    public static void markTask() {
        viewAllTask();
        System.out.println("\n=== Mark Task as Complete ===");
        System.out.print("Enter the task number you want to mark as complete: ");
        int numComplete = sc.nextInt() - 1; // Adjust for zero-based index
        if (numComplete >= 0 && numComplete < list.size()) {
            Task currentTask = list.get(numComplete);

            if (!currentTask.isCompleted()) {
                if (currentTask.getDependencies().isEmpty()) {
                    currentTask.markAsCompleted();
                    System.out.println("Task \"" + currentTask.getTitle() + "\" marked as complete!");

                    // Handle recurring tasks
                    if (currentTask.getRecurrenceInterval() != null) {
                        String nextDueDate = calculateNextDueDate(currentTask.getDueDate(), currentTask.getRecurrenceInterval());
                        Task newRecurringTask = new Task(
                            currentTask.getTitle(),
                            currentTask.getDescription(),
                            nextDueDate,
                            currentTask.getPriority(),
                            currentTask.getRecurrenceInterval(),
                            LocalDate.now().toString(),
                            true
                        );
                        list.add(newRecurringTask);
                        System.out.println("Recurring Task \"" + newRecurringTask.getTitle() + "\" added for the next occurrence on " + nextDueDate + "!");
                    }
                } else {
                    boolean allDependenciesCompleted = true;
                    for (int dependencyId : currentTask.getDependencies()) {
                        Task precedingTask = findTaskById(dependencyId);
                        if (precedingTask != null && !precedingTask.isCompleted()) {
                            allDependenciesCompleted = false;
                            System.out.println("Warning: Task \"" + currentTask.getTitle() +
                                "\" cannot be marked as complete because it depends on \"" + precedingTask.getTitle() + 
                                "\". Please complete \"" + precedingTask.getTitle() + "\" first.");
                            break;
                        }
                    }

                    if (allDependenciesCompleted) {
                        currentTask.markAsCompleted();
                        System.out.println("Task \"" + currentTask.getTitle() + "\" marked as complete!");
                    }
                }
            } else {
                System.out.println("Error: This Task is already marked as completed!");
            }
        } else {
            System.out.println("Error: Invalid task number.");
        }
        System.out.println();
    }

    
    //Method to delete a task
    public static void deleteTask(){
        viewAllTask();
        System.out.println("\n=== Delete a Task ===");
        System.out.println("Enter 0 to cancel");
        System.out.print("Enter the task number you want to delete: ");
        int numDelete = sc.nextInt() - 1;
        
        if(numDelete == -1){
            System.out.println("Operation is cancelled");
        }
        
        else if(numDelete >= 0 && numDelete < list.size()){
            System.out.println("Task " + "\"" + list.get(numDelete).getTitle() + "\" deleted successfully!");
            list.remove(numDelete);            
        }
        else{
            System.out.println("Invalid task number");
        }
        System.out.println();
    }
    
    public static void sortTask(){
        System.out.println("=== Sort Tasks ===");
        System.out.println("Sort by:");
        System.out.println("1. Due Date (Ascending)");
        System.out.println("2. Due Date (Descending)");
        System.out.println("3. Priority (High to Low)");
        System.out.println("4. Priority (Low to High)");
        System.out.print("\n> ");
        int numSort = sc.nextInt();
        
        switch(numSort){
            case 1:
                //Buble sort by due date(ascending)
                for(int pass=0 ; pass<list.size() ; pass++){
                    for(int i=0 ; i<list.size()-1 ; i++){
                        if(list.get(i).getDueDate().compareTo(list.get(i+1).getDueDate()) > 0){
                            Task temp = list.get(i);
                            list.set(i , list.get(i+1));
                            list.set(i+1 , temp);
                        }
                    }                             
                }
                System.out.println("Tasks sorted by Due Date (Ascending)!");
                System.out.println();
                break;
            
            case 2:
                //Buble sort by due date(descending)
                for(int pass=0 ; pass<list.size() ; pass++){
                    for(int i=0 ; i<list.size()-1 ; i++){
                        if(list.get(i).getDueDate().compareTo(list.get(i+1).getDueDate()) < 0){
                            Task temp = list.get(i);
                            list.set(i , list.get(i+1));
                            list.set(i+1 , temp);
                        }
                    }                             
                }
                System.out.println("Tasks sorted by Due Date (Descending)!");  
                System.out.println();
                break;
                
            case 3:
                //Bubble sort by priority (high to low)
                for(int pass=0 ; pass<list.size() ; pass++){
                    for(int i=0 ; i<list.size()-1 ; i++){
                        if(getPriorityValue(list.get(i).getPriority()) < getPriorityValue(list.get(i+1).getPriority())){
                            Task temp = list.get(i);
                            list.set(i , list.get(i+1));
                            list.set(i+1, temp);
                        }
                    }                    
                }
                System.out.println("Task sorted by Priority (High to Low) !");
                System.out.println();
                break;
                
            case 4:
                //Bubble sort by priority (low to high)
                for(int pass=0 ; pass<list.size() ; pass++){
                    for(int i=0 ; i<list.size()-1 ; i++){
                        if(getPriorityValue(list.get(i).getPriority()) > getPriorityValue(list.get(i+1).getPriority())){
                            Task temp = list.get(i);
                            list.set(i , list.get(i+1));
                            list.set(i+1, temp);
                        }
                    }                    
                }
                System.out.println("Task sorted by Priority (Low to High) !");
                System.out.println();
                break;
                
            default:
                System.out.println("Invalid option");
                System.out.println();
        }
    }
    
    //Method to return priority value
    public static int getPriorityValue(String priority){
        switch(priority.toLowerCase()){
            case "high":
                return 3;
            
            case "medium":
                return 2;
            
            case "low":
                return 1;
            
            default:
                return 0;
        }
    }
    
    //Method to search task by giving keyword
    public static void searchTask(){
        System.out.println("=== Search Task ===");
        System.out.print("Enter a keyword to search by title or description:  ");
        String keyword = sc.nextLine();
        System.out.println("\n=== Search Results ===");

        boolean found = false; // Flag to track if any tasks are found
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase()) || 
                list.get(i).getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println((i + 1) + ". " + list.get(i));
                found = true;
                System.out.println();
            }
        }
        if (!found) {
            System.out.println("No task found.");
            System.out.println();
        }
    }
    
    //Method to add recurring task
    public static void recurringTask(){
        System.out.println("*Note: Priority for recurring task is set to Medium");
        System.out.println("=== Add a Recurring Task ===");
        System.out.print("Enter task title: ");
        String title = sc.nextLine();
        System.out.print("Enter task description: ");
        String description = sc.nextLine();
        String recurrenceInterval;
        while(true){
            System.out.print("Enter recurrence interval (daily, weekly, monthly):  ");
            recurrenceInterval = sc.nextLine();
            if(recurrenceInterval.equalsIgnoreCase("daily") || recurrenceInterval.equalsIgnoreCase("weekly") || recurrenceInterval.equalsIgnoreCase("monthly")){
                break;
            }
            else
                System.out.println("Invalid Recurrence Interval. Please select from (daily, weekly, monthly)");
        }
        String currentDate = LocalDate.now().toString();
        String duedate = calculateNextDueDate(currentDate, recurrenceInterval);
        String priority = "Medium";
        boolean isRecurring = true;
        Task task = new Task(title, description, duedate, priority, recurrenceInterval, currentDate, isRecurring);
        list.add(task);
        System.out.println("\nRecurring Task \"" + title + "\" added successfully!");
        System.out.println();
        
        
    }
    // Method to calculate the next due date
    public static String calculateNextDueDate(String currentDate, String recurrenceInterval) {
        LocalDate date = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        switch (recurrenceInterval.toLowerCase()) {
            case "daily" -> date = date.plusDays(1);
            case "weekly" -> date = date.plusWeeks(1);
            case "monthly" -> date = date.plusMonths(1);
        }
        return date.toString();
    }
    
    //Method to set Task Dependency
    public static void taskDependency() {
    System.out.println("=== Set Task Dependencies ===");
    
    // First check if we have enough tasks
    if (list.size() < 2) {
        System.out.println("Error: Need at least 2 tasks to set dependencies.");
        System.out.println();
        return;
    }
    
    viewAllTask();
    System.out.println("\nEnter 0 at any time to cancel the operation.");

    Task dependentTask;
    while (true) {
        System.out.print("Enter the task number that depends on another task: ");
        int taskIndex = sc.nextInt() - 1;
        
        // Check for cancel operation
        if (taskIndex == -1) {
            System.out.println("Operation cancelled.");
            System.out.println();
            return;
        }
        
        if (taskIndex >= 0 && taskIndex < list.size()) {
            dependentTask = list.get(taskIndex);
            break;
        } else {
            System.out.println("Invalid task number. Please choose again.");
        }
    }

    // Check if there are any available tasks to depend on
    boolean availableDependencies = false;
    for (Task task : list) {
        if (task != dependentTask && !wouldCreateCycle(task, dependentTask)) {
            availableDependencies = true;
            break;
        }
    }

    if (!availableDependencies) {
        System.out.println("Error: No available tasks to set as dependency. Setting a dependency would create a cycle.");
        System.out.println();
        return;
    }

    Task precedingTask;
    while (true) {
        System.out.print("Enter the task number it depends on: ");
        int taskIndex = sc.nextInt() - 1;
        
        // Check for cancel operation
        if (taskIndex == -1) {
            System.out.println("Operation cancelled.");
            System.out.println();
            return;
        }

        if (taskIndex >= 0 && taskIndex < list.size()) {
            if (taskIndex == list.indexOf(dependentTask)) {
                System.out.println("Error: Task cannot depend on itself.");
                continue;
            }
            precedingTask = list.get(taskIndex);
            
            if (wouldCreateCycle(precedingTask, dependentTask)) {
                System.out.println("Error: This dependency would create a cycle. Please choose another task or enter 0 to cancel.");
                continue;
            }
            break;
        } else {
            System.out.println("Invalid task number. Please choose again or enter 0 to cancel.");
        }
    }

    dependentTask.addDependency(precedingTask.getTaskId());
    System.out.println("Task \"" + dependentTask.getTitle() + "\" now depends on \"" + precedingTask.getTitle() + "\".");
    System.out.println();
}

    // Method to check if adding a dependency would create a cycle
    private static boolean wouldCreateCycle(Task precedingTask, Task dependentTask) {
        // Check if the preceding task depends on the dependent task (direct cycle)
        if (precedingTask.getDependencies().contains(dependentTask.getTaskId())) {
            return true;
        }

        // Check for indirect cycles using DFS
        ArrayList<Integer> visited = new ArrayList<>();
        return hasPathTo(precedingTask, dependentTask.getTaskId(), visited);
    }

    // Helper method to check if there's a path from start task to target task ID
    private static boolean hasPathTo(Task startTask, int targetTaskId, ArrayList<Integer> visited) {
        if (visited.contains(startTask.getTaskId())) {
            return false;
        }

        visited.add(startTask.getTaskId());

        // Check all dependencies of the current task
        for (int dependencyId : startTask.getDependencies()) {
            if (dependencyId == targetTaskId) {
                return true;
            }

            Task dependencyTask = findTaskById(dependencyId);
            if (dependencyTask != null && hasPathTo(dependencyTask, targetTaskId, visited)) {
                return true;
            }
        }

        return false;
    }
    // Helper method to find a task by its ID
    private static Task findTaskById(int taskId) {
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);  // Access the task using the index
            if (task.getTaskId() == taskId) {
                return task;
            }
        }
        return null;  
    }

    
    //Method to edit task
    public static void editTask(){
        viewAllTask();
        System.out.println();
        System.out.println("=== Edit Task ===");        
        int editNum;
        while (true) {
            System.out.print("Enter the task number you want to edit: ");
            if (sc.hasNextInt()){
                editNum = sc.nextInt() - 1; // Adjust for zero-based indexing
                sc.nextLine(); // Clear buffer
                if (editNum >= 0 && editNum < list.size())
                    break;
                else
                    System.out.println("Error: Invalid task number. Please choose again.");
            }
            else{
                System.out.println("Error: Enter a valid number.");
                sc.next(); // Clear invalid input
            }
        }
        System.out.println();
        System.out.println("What would you like to edit?");
        System.out.println("1. Title");
        System.out.println("2. Description");
        System.out.println("3. Due Date");
        System.out.println("4. Category");
        System.out.println("5. Priority");
        System.out.println("6. Set Task Dependency");
        System.out.println("7. Cancel");
        System.out.println();
        System.out.print("> ");
        int edit = sc.nextInt();
        sc.nextLine();
        
        switch(edit){
            case 1:
                String newTitle;
                while (true) {
                    System.out.print("Enter task title: ");
                    newTitle = sc.nextLine();
                    if (!newTitle.isEmpty()) {
                        break;
                    } else {
                        System.out.println("Error: Task title cannot be empty. Please enter a valid title.");
                    }
                }
                System.out.println("Task \"" + list.get(editNum).getTitle() + "\" has been updated to \"" + newTitle + ".\"");
                System.out.println();
                list.get(editNum).setTitle(newTitle);
                break;
                
            case 2:
                System.out.print("Enter the new description: ");
                String newDescription = sc.nextLine();
                System.out.println("Description \"" + list.get(editNum).getDescription() + "\" has been updated to \"" + newDescription + ".\"");
                System.out.println();
                list.get(editNum).setDescription(newDescription);
                break;
                
            case 3:
                String newDueDate;
                while (true) {
                    System.out.print("Enter the new due date (YYYY-MM-DD): ");
                    newDueDate = sc.nextLine();

                    // Check if it matches the basic date format (YYYY-MM-DD)
                    if (Pattern.matches("\\d{4}-\\d{2}-\\d{2}", newDueDate)) {
                        String[] parts = newDueDate.split("-");
                        int year = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]);
                        int day = Integer.parseInt(parts[2]);

                        // Check that the year, month, and day are within valid ranges
                        if(year == 0) 
                            System.out.println("Error: Year cannot be 0.");
                        
                        else if (month < 1 || month > 12) 
                            System.out.println("Error: Month must be between 01 and 12.");
                        
                        else if (day < 1 || day > getDaysInMonth(month, year)) 
                            System.out.println("Error: Invalid day for the given month.");
                        
                        else {
                            // Parse the due date as a LocalDate
                            LocalDate dueDateParsed = LocalDate.parse(newDueDate);
                            LocalDate currentDate = LocalDate.now();

                            // Check if the due date is later than the current date
                            if (dueDateParsed.isBefore(currentDate)) 
                                System.out.println("Error: The due date must be later than today.");
                    
                            else 
                                break; // Valid date                    
                        }                
                        
                    } 
                    else {
                        System.out.println("Error: Please enter a valid date in YYYY-MM-DD format.");
                    }
                }
                System.out.println("Due Date \"" + list.get(editNum).getDueDate() + "\" has been updated to \"" + newDueDate + ".\"");
                System.out.println();
                list.get(editNum).setDueDate(newDueDate);
                break;
            
            case 4:
                String newCategory;
                while(true){
                    System.out.print("Enter the new task category (Homework, Personal, Work):  ");
                    newCategory = sc.nextLine();
                    if(newCategory.equalsIgnoreCase("Homework") || newCategory.equalsIgnoreCase("Personal") || newCategory.equalsIgnoreCase("Work") 
                       && !newCategory.equals(list.get(editNum).getCategory())){
                        break;
                    }
                    
                    else if(newCategory.equals(list.get(editNum).getCategory()))
                        System.out.println("Error: Please select a new category");
                    
                    else{
                        System.out.println("Error: Please select from the list(Homework, Personal, Work)");
                    }
                }
                System.out.println("Category \"" + list.get(editNum).getCategory() + "\" has been updated to \"" + newCategory + ".\"");
                System.out.println();
                list.get(editNum).setCategory(newCategory);
                break;
            
            case 5:
                String newPriority;
                while(true){
                    System.out.print("Enter the new priority level (Low, Medium, High):  ");
                    newPriority = sc.nextLine();
                    if(newPriority.equalsIgnoreCase("Low") || newPriority.equalsIgnoreCase("Medium") || newPriority.equalsIgnoreCase("High")
                            && !newPriority.equals(list.get(editNum).getPriority()))
                        break;
                    
                    else if(newPriority.equals(list.get(editNum).getCategory()))
                        System.out.println("Error: Please select a new priority level");
                    else{
                        System.out.println("Error: Please select from the list(Low, Medium, High)");
                    }                    
                }
                System.out.println("Priority level \"" + list.get(editNum).getPriority() + "\" has been updated to \"" + newPriority + ".\"");
                System.out.println();
                list.get(editNum).setPriority(newPriority);
                break;
                
            case 6:
                taskDependency();
                break;
            
            case 7:
                System.out.println("Back to Main Menu");
                System.out.println();
                break;
            
            default:
                System.out.println("Invalid option");
                System.out.println();
                break;
        }
    }
    
    public static void dataAnalytics(){
        loadTasksFromFile();
        int totalTasks = 0;
        int completedTasks = 0;
        int pendingTasks = 0;
        int homeworkCount = 0;
        int personalCount = 0;
        int workCount = 0;
        int recurringCount = 0;
        
        for (Task task : list) {
            totalTasks++;
            
            if (task.isCompleted()) {
                    completedTasks++;
                } else {
                    pendingTasks++;
                }
            
            if(task.getRecurrenceInterval() != null){
                recurringCount++;
            }
            
            String category = task.getCategory(); 
            if (category != null) {
                switch (category.toLowerCase()) {
                    case "homework":
                        homeworkCount++;
                        break;
                    case "personal":
                        personalCount++;
                        break;
                    case "work":
                        workCount++;
                        break;
                    default:
                        // If there's an unknown category, you can count it here or leave it out
                        break;
                }
            }
        }
    //    int totalTasks = homeworkCount + personalCount + workCount;
        double completionRate = totalTasks > 0 ? (double) completedTasks / totalTasks * 100 : 0;
        
        System.out.println("=== Analytics Dashboard ===");
        System.out.println("- Total Tasks: " + totalTasks);
        System.out.println("- Completed: " + completedTasks);
        System.out.println("- Pending: " + pendingTasks);
        System.out.printf("- Completion Rate: %.2f%%%n", completionRate);
        System.out.println("- Task Categories: Homework: " + homeworkCount + ", Personal: " + personalCount+ ", Work: " + workCount + ", Recurring: " + recurringCount);
        System.out.println();
        
    }
    
    public static void emailNotification(String email){
        for(int i=0;i<list.size();i++){
            LocalDate dueDate = LocalDate.parse(list.get(i).getDueDate());
            LocalDate reminderDate = dueDate.minusDays(1);
            LocalDate currentDate = LocalDate.now();
            String reminderTask = list.get(i).getTitle();
            String description = list.get(i).getDescription();
            boolean isCompleted = list.get(i).isCompleted();
            
            if(reminderDate.equals(currentDate) && !isCompleted){
                sendEmail(email,reminderTask, dueDate, description);
            }
        }
    }
    
    public static void sendEmail(String toEmail, String taskName, LocalDate dueDate, String description) {
        String fromEmail = "shenli263031@gmail.com"; // Your Gmail address
        String password = "sl20041010";    // Use App Password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Task Reminder: " + taskName);
            message.setText("=== Email Notification ===\n" +
                    "Sending reminder email for task \"" + taskName + "\" due in 24 hours." +

                    "\n\nTask Title: " + taskName +
                    "\nDescription: " + description +
                    "\nDue Date: " + dueDate +
                    "\n\nPlease ensure it's completed on time!");

            // Send message
            Transport.send(message);
            System.out.println("Reminder email sent successfully!");
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public static void saveTasksToFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/fop_assignment/Task.csv"))) {
        // Write header
        writer.write("TaskID,Title,Description,DueDate,IsCompleted,Category,Priority,RecurrenceInterval,CurrentDate,Dependencies");
        writer.newLine();

        // Write each task's data
        for (int i = 0; i < list.size(); i++) {
                Task task = list.get(i); // Access task directly by index
                String taskLine = task.getTaskId() + ","
                        + formatCSV(task.getTitle()) + ","
                        + formatCSV(task.getDescription()) + ","
                        + formatCSV(task.getDueDate()) + ","
                        + task.isCompleted() + ","
                        + (task.getCategory() == null ? "" : formatCSV(task.getCategory())) + ","
                        + formatCSV(task.getPriority()) + ","
                        + (task.getRecurrenceInterval() == null ? "" : formatCSV(task.getRecurrenceInterval())) + ","
                        + formatCSV(task.getCurrentDate()) + ",";

                // Add dependencies
                if (!task.getDependencies().isEmpty()) {
                    String dependencyLine = "";
                    for (int j = 0; j < task.getDependencies().size(); j++) {
                        if (j > 0) {
                            dependencyLine += "|"; // Add separator for multiple dependencies
                        }
                        dependencyLine += task.getDependencies().get(j);
                    }
                    taskLine += dependencyLine;
                }

                writer.write(taskLine);
                writer.newLine();
            }
            System.out.println("Tasks saved successfully to Task.csv");
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static String formatCSV(String value) {
        if (value == null) return ""; // Handle null values
        value = value.replace("\"", "\"\""); // Escape double quotes 
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value + "\""; // Wrap in quotes if necessary
        }
        return value;
    }

    public static void loadTasksFromFile() {
        // Clear existing list before loading
        list.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/fop_assignment/Task.csv"))) {
            // Skip header line
            String line = reader.readLine();
    
            // Reset the static nextId to ensure no ID conflicts when loading
            Task.resetNextId();
    
            while ((line = reader.readLine()) != null) {
                String[] parts = splitCSVLine(line);

                Task task = new Task(
                    unformatCSV(parts[1]),  // Title
                    unformatCSV(parts[2]),  // Description
                    unformatCSV(parts[3]),  // DueDate
                    parts[5].isEmpty() ? null : unformatCSV(parts[5]), // Category
                    unformatCSV(parts[6]),  // Priority
                    unformatCSV(parts[8])   // CurrentDate
                );

                // Handle recurring tasks
                if (!parts[7].isEmpty()) {
                    task.setRecurrenceInterval(unformatCSV(parts[7]));
                }

                // Set completion status
                if (Boolean.parseBoolean(parts[4])) {
                    task.markAsCompleted();
                }

                // Parse dependencies if any
                if (parts.length > 9 && !parts[9].isEmpty()) {
                    String[] dependencies = parts[9].split("\\|");
                    for (int i = 0; i < dependencies.length; i++) {
                        task.addDependency(Integer.parseInt(dependencies[i]));
                    }
                }

                list.add(task); // Add task to the list
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    private static String unformatCSV(String value) {
        if (value == null || value.isEmpty()) return ""; // Handle empty values
        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1); // Remove surrounding quotes
        }
        return value.replace("\"\"", "\""); // Unescape double quotes
    }
 
    private static String[] splitCSVLine(String line) {
        // Split CSV into exactly 10 parts
        String[] result = new String[10];
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        int index = 0;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes; // Toggle quotes
            } else if (c == ',' && !inQuotes) {
                result[index++] = unformatCSV(currentField.toString());
                currentField.setLength(0); // Clear the field
            } else {
                currentField.append(c);
            }
        }
        result[index] = unformatCSV(currentField.toString()); // Add last field
        return result;
    }
}

