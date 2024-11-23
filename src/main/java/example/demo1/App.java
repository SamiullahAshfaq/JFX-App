package example.demo1;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class App extends Application {

    private String submittedName;
    private String submittedFatherName;
    private String submittedPhone;
    private String submittedCity;
    private String submittedImagePath;

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        return label;
    }

    private Text createStyledText(String text) {
        Text styledText = new Text(text);
        styledText.setStyle("-fx-font-size: 16px; -fx-fill: white;");
        return styledText;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Extended Form");

        // Create the main layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: #284b63;"); // Dark blue background

        // Add font
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Regular.ttf"), 16);

        Text scenetitle = new Text("Welcome to JFX App");
        scenetitle.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: white;");
        grid.add(scenetitle, 0, 0, 2, 1);

        // Name field
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        grid.add(nameLabel, 0, 1);

        TextField nameField = new TextField();
        grid.add(nameField, 1, 1);

        // Father Name field
        Label fatherNameLabel = new Label("Father Name:");
        fatherNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        grid.add(fatherNameLabel, 0, 2);

        TextField fatherNameField = new TextField();
        grid.add(fatherNameField, 1, 2);

        // Phone Number field
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        grid.add(phoneLabel, 0, 3);

        TextField phoneField = new TextField();
        grid.add(phoneField, 1, 3);

        // City Address field
        Label cityLabel = new Label("City Address:");
        cityLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        grid.add(cityLabel, 0, 4);

        TextField cityField = new TextField();
        grid.add(cityField, 1, 4);

        // Image Upload Button
        Label imageLabel = new Label("Upload Image:");
        imageLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        grid.add(imageLabel, 0, 5);

        Button uploadButton = new Button("Browse");
        uploadButton.setStyle("-fx-background-color: #ffa500; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        final Text imagePath = new Text();
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                imagePath.setText(selectedFile.getAbsolutePath());
            }
        });

        HBox uploadBox = new HBox(10);
        uploadBox.getChildren().addAll(uploadButton, imagePath);
        grid.add(uploadBox, 1, 5);

        // Password field
        Label pw = new Label("Password:");
        pw.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        grid.add(pw, 0, 6);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 6);

        // Submit button with loading effect
        Button btn = new Button("Submit");
        btn.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        final ProgressIndicator loadingSpinner = new ProgressIndicator();
        loadingSpinner.setVisible(false);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btn, loadingSpinner);
        grid.add(hbBtn, 1, 8);

        // View Info button
        Button viewBtn = new Button("View Info");
        viewBtn.setStyle("-fx-background-color: #32cd32; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        viewBtn.setDisable(true);
        HBox hbViewBtn = new HBox(10);
        hbViewBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbViewBtn.getChildren().add(viewBtn);
        grid.add(hbViewBtn, 1, 9);

        final Text actiontarget = new Text();
        actiontarget.setStyle("-fx-font-size: 12px; -fx-font-style: italic;");
        grid.add(actiontarget, 1, 10);

        // Handle Submit Button
        btn.setOnAction(e -> {
            StringBuilder errors = new StringBuilder();

            // Validation
            if (nameField.getText().isEmpty()) errors.append("Name is required.\n");
            if (fatherNameField.getText().isEmpty()) errors.append("Father Name is required.\n");
            if (phoneField.getText().isEmpty() || !phoneField.getText().matches("\\d{11}"))
                errors.append("Valid Phone Number (11 digits) is required.\n");
            if (cityField.getText().isEmpty()) errors.append("City Address is required.\n");
            if (pwBox.getText().isEmpty()) errors.append("Password is required.\n");
            if (imagePath.getText().isEmpty()) errors.append("Image upload is required.\n");

            if (errors.length() > 0) {
                // Display errors in an alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Errors");
                alert.setHeaderText("Please correct the following errors:");
                alert.setContentText(errors.toString());
                alert.showAndWait();

                viewBtn.setDisable(true); // Disable view button on errors
                loadingSpinner.setVisible(false); // Hide loading spinner if error occurs
            } else {
                // Show loading spinner
                loadingSpinner.setVisible(true);

                // Simulate loading time
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> {
                    // Save submitted data
                    submittedName = nameField.getText();
                    submittedFatherName = fatherNameField.getText();
                    submittedPhone = phoneField.getText();
                    submittedCity = cityField.getText();
                    submittedImagePath = imagePath.getText();

                    actiontarget.setFill(Color.WHITESMOKE);
                    actiontarget.setText("Form submitted successfully!");
                    viewBtn.setDisable(false); // Enable view button
                    loadingSpinner.setVisible(false); // Hide loading spinner
                });
                pause.play();
            }
        });


        // Handle View Info Button
        viewBtn.setOnAction(e -> {
            // Create a new VBox for the submitted info page
            VBox infoView = new VBox(20);
            infoView.setAlignment(Pos.CENTER);
            infoView.setPadding(new Insets(30));
            infoView.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #bdc3c7);"); // Gradient background

            // Title
            Text title = new Text("Submitted Information");
            title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");
            infoView.getChildren().add(title);

            // Name
            HBox nameBox = new HBox(10);
            nameBox.setAlignment(Pos.CENTER_LEFT);
            nameBox.getChildren().addAll(
                    createStyledLabel("Name:"),
                    createStyledText(submittedName)
            );
            infoView.getChildren().add(nameBox);

            // Father Name
            HBox fatherNameBox = new HBox(10);
            fatherNameBox.setAlignment(Pos.CENTER_LEFT);
            fatherNameBox.getChildren().addAll(
                    createStyledLabel("Father Name:"),
                    createStyledText(submittedFatherName)
            );
            infoView.getChildren().add(fatherNameBox);

            // Phone Number
            HBox phoneBox = new HBox(10);
            phoneBox.setAlignment(Pos.CENTER_LEFT);
            phoneBox.getChildren().addAll(
                    createStyledLabel("Phone Number:"),
                    createStyledText(submittedPhone)
            );
            infoView.getChildren().add(phoneBox);

            // City Address
            HBox cityBox = new HBox(10);
            cityBox.setAlignment(Pos.CENTER_LEFT);
            cityBox.getChildren().addAll(
                    createStyledLabel("City Address:"),
                    createStyledText(submittedCity)
            );
            infoView.getChildren().add(cityBox);

            // Uploaded Image
            if (submittedImagePath != null) {
                HBox imageBox = new HBox(10);
                imageBox.setAlignment(Pos.CENTER_LEFT);
                imageBox.getChildren().add(createStyledLabel("Uploaded Image:"));

                ImageView imageView = new ImageView(new Image("file:" + submittedImagePath));
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0, 0); -fx-border-color: white; -fx-border-radius: 50; -fx-border-width: 3;");
                imageBox.getChildren().add(imageView);

                infoView.getChildren().add(imageBox);
            }

            // Back Button
            Button backButton = new Button("Back");
            backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
            backButton.setOnAction(event -> {
                // Return to the form view
                primaryStage.getScene().setRoot(grid);
            });
            infoView.getChildren().add(backButton);

            // Set the new scene content
            primaryStage.getScene().setRoot(infoView);
        });


        // Set the scene and show the window
        Scene scene = new Scene(grid, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
