module blockchain_hashing {
    requires javafx.controls;
    requires javafx.fxml;

    opens blockchain_hashing to javafx.fxml;
    exports blockchain_hashing;
}
