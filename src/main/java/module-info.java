module com.rds.stm {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.rds.stm to javafx.fxml;
	exports com.rds.stm;
}