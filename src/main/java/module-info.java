module com.example.lab4javaright {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab4javaright to javafx.fxml;
    exports com.example.lab4javaright;
}