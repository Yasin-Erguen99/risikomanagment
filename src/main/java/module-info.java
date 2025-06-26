module risikomanagment {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    opens risikomanagment.gui to javafx.fxml ,javafx.graphics, javafx.controls;
}
