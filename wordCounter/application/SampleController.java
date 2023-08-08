package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class SampleController {
	@FXML
	private TextArea textArea;
	@FXML
	private Text ans ;
	@FXML
	private Button button;
	
	private String[] formArray(String sentence) {
		return sentence.split(" ");
		
	}
	
   private int count(String[] array) {
	return array.length;
   }
	
  @FXML 
  public void getCount(ActionEvent event)throws IOException {
	  int c = count(formArray(textArea.getText()));
	  System.out.println(c);
	  ans.setText(""+c);
	
  }
}
