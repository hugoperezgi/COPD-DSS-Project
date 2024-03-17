package gui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorPopupController {
	@FXML
	Label errorDisplayText;

	public void displayErrorText(String text) {
		errorDisplayText.setText(text);
	}
}


