module com.example.player {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.player to javafx.fxml;
    exports com.example.player;
}