package GUI;

import fop_assignment.FOP_Assignment;
import fop_assignment.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import javafx.application.Platform;

public class SecondPageController {

    @FXML
    private Label nameLabel;

    @FXML
    private Button addTask;

    @FXML
    private Button sortTask;

    @FXML
    private Button markTask;

    @FXML
    private Button editTask;

    @FXML
    private Button deleteTask;

    @FXML
    private Button save;

    @FXML
    private Button sendNotification;

    @FXML
    private ListView<String> viewList;



    private ObservableList<String> tasks;

    /**
     * Initialize the controller
     */
    public void initialize() {
        tasks = FXCollections.observableArrayList();
        viewList.setItems(tasks);

        // Load tasks from the backend when the application starts
        loadTasks();
    }

    /**
     * Display the name of the logged-in user
     */
    public void displayName(String username) {
        nameLabel.setText("Hi " + username);
    }

    /**
     * Load tasks from the backend and display them in the ListView
     */
    private void loadTasks() {
        tasks.clear();
        FOP_Assignment.loadTasksFromFile();
        for (Task task : FOP_Assignment.list) {
            tasks.add(task.toString());
        }
    }

    /**
     * Show a dialog for adding or editing a task
     */
    private Task showTaskDialog(Task existingTask) {
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle(existingTask == null ? "Add Task" : "Edit Task");
        dialog.setHeaderText(existingTask == null ? "Enter task details" : "Edit task details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        if (existingTask != null) titleField.setText(existingTask.getTitle());

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");
        if (existingTask != null) descriptionField.setText(existingTask.getDescription());

        DatePicker dueDatePicker = new DatePicker();
        if (existingTask != null) {
            dueDatePicker.setValue(LocalDate.parse(existingTask.getDueDate()));
        } else {
            dueDatePicker.setValue(LocalDate.now()); // Default to tomorrow
        }

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Homework", "Personal", "Work");
        categoryBox.setValue(existingTask != null ? existingTask.getCategory() : "Homework");

        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("Low", "Medium", "High");
        priorityBox.setValue(existingTask != null ? existingTask.getPriority() : "Medium");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Due Date:"), 0, 2);
        grid.add(dueDatePicker, 1, 2);
        grid.add(new Label("Category:"), 0, 3);
        grid.add(categoryBox, 1, 3);
        grid.add(new Label("Priority:"), 0, 4);
        grid.add(priorityBox, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Enforce due date restriction
        final Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        dueDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isAfter(LocalDate.now().minusDays(1))) {
                saveButton.setDisable(true);
            } else {
                saveButton.setDisable(false);
            }
        });

        dialog.setResultConverter(dialogButton -> {
        if (dialogButton == saveButtonType) {
            if (titleField.getText().trim().isEmpty()) {
                showAlert("Title cannot be empty!");
                return null;
            }
            return new Task(
                    titleField.getText(),
                    descriptionField.getText(),
                    dueDatePicker.getValue().toString(),
                    categoryBox.getValue(),
                    priorityBox.getValue(),
                    LocalDate.now().toString()
            );
        }
        return null;
    });

        return dialog.showAndWait().orElse(null);
    }

    @FXML
    private void handleAddTask() {
        Task newTask = showTaskDialog(null); // Pass `null` to create a new task
        if (newTask != null) {
            FOP_Assignment.list.add(newTask);
            FOP_Assignment.saveTasksToFile();
            loadTasks();
        }
    }

    @FXML
    private void handleEditTask() {
        int selectedIndex = viewList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = FOP_Assignment.list.get(selectedIndex);
            Task editedTask = showTaskDialog(selectedTask);
            if (editedTask != null) {
                selectedTask.setTitle(editedTask.getTitle());
                selectedTask.setDescription(editedTask.getDescription());
                selectedTask.setDueDate(editedTask.getDueDate());
                selectedTask.setCategory(editedTask.getCategory());
                selectedTask.setPriority(editedTask.getPriority());
                FOP_Assignment.saveTasksToFile();
                loadTasks();
            }
        } else {
            showAlert("Please select a task to edit.");
        }
    }

    @FXML
    private void handleSortTask() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Due Date (Ascending)",
                "Due Date (Ascending)", "Due Date (Descending)", "Priority (High to Low)", "Priority (Low to High)");
        dialog.setTitle("Sort Tasks");
        dialog.setHeaderText("Choose a sorting option:");

        dialog.showAndWait().ifPresent(choice -> {
            switch (choice) {
                case "Due Date (Ascending)" -> {
                    for (int pass = 0; pass < FOP_Assignment.list.size(); pass++) {
                        for (int i = 0; i < FOP_Assignment.list.size() - 1; i++) {
                            if (FOP_Assignment.list.get(i).getDueDate().compareTo(FOP_Assignment.list.get(i + 1).getDueDate()) > 0) {
                                Task temp = FOP_Assignment.list.get(i);
                                FOP_Assignment.list.set(i, FOP_Assignment.list.get(i + 1));
                                FOP_Assignment.list.set(i + 1, temp);
                            }
                        }
                    }
                }
                case "Due Date (Descending)" -> {
                    for (int pass = 0; pass < FOP_Assignment.list.size(); pass++) {
                        for (int i = 0; i < FOP_Assignment.list.size() - 1; i++) {
                            if (FOP_Assignment.list.get(i).getDueDate().compareTo(FOP_Assignment.list.get(i + 1).getDueDate()) < 0) {
                                Task temp = FOP_Assignment.list.get(i);
                                FOP_Assignment.list.set(i, FOP_Assignment.list.get(i + 1));
                                FOP_Assignment.list.set(i + 1, temp);
                            }
                        }
                    }
                }
                case "Priority (High to Low)" -> {
                    for (int pass = 0; pass < FOP_Assignment.list.size(); pass++) {
                        for (int i = 0; i < FOP_Assignment.list.size() - 1; i++) {
                            if (FOP_Assignment.getPriorityValue(FOP_Assignment.list.get(i).getPriority()) <
                                    FOP_Assignment.getPriorityValue(FOP_Assignment.list.get(i + 1).getPriority())) {
                                Task temp = FOP_Assignment.list.get(i);
                                FOP_Assignment.list.set(i, FOP_Assignment.list.get(i + 1));
                                FOP_Assignment.list.set(i + 1, temp);
                            }
                        }
                    }
                }
                case "Priority (Low to High)" -> {
                    for (int pass = 0; pass < FOP_Assignment.list.size(); pass++) {
                        for (int i = 0; i < FOP_Assignment.list.size() - 1; i++) {
                            if (FOP_Assignment.getPriorityValue(FOP_Assignment.list.get(i).getPriority()) >
                                    FOP_Assignment.getPriorityValue(FOP_Assignment.list.get(i + 1).getPriority())) {
                                Task temp = FOP_Assignment.list.get(i);
                                FOP_Assignment.list.set(i, FOP_Assignment.list.get(i + 1));
                                FOP_Assignment.list.set(i + 1, temp);
                            }
                        }
                    }
                }
                default -> showAlert("Invalid choice!");
            }
            FOP_Assignment.saveTasksToFile(); // Save the sorted tasks
            loadTasks(); // Reload tasks to update the ListView
        });
    }




    @FXML
    private void handleMarkTask() {
        int selectedIndex = viewList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = FOP_Assignment.list.get(selectedIndex);
            selectedTask.markAsCompleted();
            FOP_Assignment.saveTasksToFile();
            loadTasks();
        } else {
            showAlert("Please select a task to mark as complete.");
        }
    }

    @FXML
    private void handleDeleteTask() {
        int selectedIndex = viewList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            FOP_Assignment.list.remove(selectedIndex);
            FOP_Assignment.saveTasksToFile();
            loadTasks();
        } else {
            showAlert("Please select a task to delete.");
        }
    }

    @FXML
    private void handleSave() {
        FOP_Assignment.saveTasksToFile();
        showAlert("Tasks saved successfully.");
        // Exit the application after the alert is closed
        Platform.exit();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
