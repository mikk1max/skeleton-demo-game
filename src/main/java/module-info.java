module com.example.demogame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demogame to javafx.fxml;
    exports com.example.demogame;
}