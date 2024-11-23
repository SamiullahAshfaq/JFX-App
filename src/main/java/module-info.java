module example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens example.demo1 to javafx.fxml;
    exports example.demo1;
}