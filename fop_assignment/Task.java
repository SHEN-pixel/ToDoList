package fop_assignment;


import java.util.ArrayList;

public class Task{
    private String title;
    private String description;
    private String duedate;
    private boolean isCompleted;
    private String category;
    private String priority;
    private String recurrenceInterval;
    private String currentDate;
    private ArrayList<Integer> dependencies;
    private static int nextId = 1;  // Static variable to generate unique IDs
    private final int taskId;  // Unique identifier for each task
    private boolean isRecurring;
    
    //Constructor with all features of a task (without recurrence)
    public Task(String title, String description, String duedate, String category, String priority, String currentDate){
        this.title = title;
        this.description = description;
        this.duedate = duedate;
        this.isCompleted = false;
        this.category = category;
        this.priority = priority;
        this.recurrenceInterval = null;
        this.currentDate = currentDate;
        this.dependencies = new ArrayList<>();
        this.taskId = nextId++;  // Assign and increment unique ID
        this.title = title;
        this.isRecurring = false;
    }
    
    //Constructor for recurring task
    public Task(String title, String description, String duedate, String priority, String recurrenceInterval, String currentDate, boolean isRecurring){
        this.title = title;
        this.description = description;
        this.duedate = duedate;
        this.isCompleted = false;
        this.category = null;
        this.priority = priority;
        this.recurrenceInterval = recurrenceInterval;
        this.currentDate = currentDate;
        this.dependencies = new ArrayList<>();
        this.taskId = nextId++;  // Assign and increment unique ID
        this.title = title;
        this.isRecurring = true;
    }
    
    //Setter and Getter
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDueDate(){
        return duedate;
    }
    
    public void setDueDate(String duedate){
        this.duedate = duedate;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }
    
    public String getRecurrenceInterval(){
        return recurrenceInterval;
    }
    
    public void setRecurrenceInterval(String recurrenceInterval){
        this.recurrenceInterval = recurrenceInterval;
    }
    
    public String getCurrentDate(){
        return currentDate;
    }
    
    public void setCurrentDate(String currentDate){
        this.currentDate = currentDate;
    }
    
    public ArrayList<Integer> getDependencies() {
        return dependencies;
    }

    public void addDependency(int taskId) {
        dependencies.add(taskId);
    }
    
    // Add getter for taskId
    public int getTaskId() {
        return taskId;
    }
    
    public static void resetNextId() {
    nextId = 1;
    }
    
    @Override
    public String toString(){
        if(recurrenceInterval==null){
            return "[" + (isCompleted ? "Completed" : "Incomplete") + "] " + title + ": " + description + " - Due: " + duedate + " - Category: " + category + " - Priority: " + priority;
        }
        else 
            return "[" + (isCompleted ? "Completed" : "Incomplete") + "] " + title + ": " + description + " - Due: " + duedate + " - Recurs : " + recurrenceInterval;
    }        
}

