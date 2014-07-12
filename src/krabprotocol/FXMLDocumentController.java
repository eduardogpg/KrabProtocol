package krabprotocol;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLDocumentController {
    
    
    private KrabProtocol application;
   
    public void setApp(KrabProtocol application){
        this.application = application;
    }
    
    @FXML
    private void signIn(ActionEvent event) throws IOException {
        
        //application.openContactWindow(event);
        
        //Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLContacWindow.fxml"));
        
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLContacWindow.fxml"));
        Parent root = (Parent) loader.load();
        //loader.getController();
        Scene scene = new Scene(root);
            
        Stage secondStage = new Stage();
        secondStage.setTitle("Nueva Ventana");
        secondStage.setScene(scene);
        ((Node)(event.getSource())).getScene().getWindow().hide();
        secondStage.show();
        //FXMLContactWindowController controller = loader.getController();
        //controller.cargar();
        
       
    }
    
    
}
