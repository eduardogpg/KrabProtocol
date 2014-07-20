package krabprotocol;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.Currency;

import services.clientScanner;

public class FXMLContactWindowController implements Initializable {
    
    private Hashtable userOnline = new Hashtable();
   
    @FXML
    private TreeView treev;
   
    
    @FXML
    private TextField converToDollar;
    
    @FXML
    private Label resultConvert;
 
    
    private singletonServerChat ssc;
            
    private Currency c;
    
    private double pesos;
    
    private TreeItem<String> rootItem;
    
    private final Node rootIcon = new ImageView(
        new Image(getClass().getResourceAsStream("img/MSN-icon.png"))
    );
    private final Node contactIcon = new ImageView(
        new Image(getClass().getResourceAsStream("img/MSN-icon.png"))
    );
    
  
        
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ssc= singletonServerChat.getInstance();
        
        c = new Currency();
        this.resultConvert.setVisible(false);
        
        try{
            clientScanner cs = new clientScanner();
            singletonServerChat sc = singletonServerChat.getInstance();
            cargar( cs.getMembersOnline( sc.getIpServer() ));
        }catch(Exception ex){
        
        }
        
                                
        /*
        new Thread(new Runnable() {
             public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        while(singletonServerChat.flagTree){
                            try {
                                Thread.sleep(5000);
                                clientScanner cs = new clientScanner();
                                
                                singletonServerChat sc = singletonServerChat.getInstance();
                                cargar( cs.getMembersOnline( sc.getIpServer() ));
                                
                            } catch (InterruptedException ex) {
                                Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                    }
                });
            }
            
        }).start();
        
        */
        
           
    }
    
    @FXML
    public void convertDollar(ActionEvent event) throws IOException {
        
        try{
            Double pesos = Double.parseDouble( this.converToDollar.getText());
            pesos = c.conversionRate(net.webservicex.Currency.USD, net.webservicex.Currency.MXN) * pesos;
            this.resultConvert.setVisible(true);
            this.resultConvert.setText( ""+pesos );
        }catch(Exception ex){
            this.resultConvert.setText( "only number, Try again" );
        }  
    }
    
    @FXML
    public void newChat(ActionEvent event) throws IOException {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
            
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage secondStage = new Stage();
            secondStage.setTitle("Chat with : ");
            secondStage.setScene(scene);
            
            ChatWindowController c = loader.getController();
            c.setIPReceiver("localhost");
            
            secondStage.show();
    }
    


    final void cargar( Hashtable Users){
        Enumeration<String> elemnts = Users.keys();
        
        TreeItem<String> rootfileItem = new TreeItem<>("Members Online", rootIcon);
        rootfileItem.setExpanded(true);
        while(elemnts.hasMoreElements()){
            
            TreeItem<String> item = new TreeItem<> ( elemnts.nextElement() );
            rootfileItem.getChildren().add(item);
        }
        treev.setRoot(rootfileItem);
        
    }
    
    private final class TextFieldTreeCellImpl extends TreeCell<String> {
        private TextField textfield;
        private ContextMenu popupMenu = new ContextMenu();
        
        public TextFieldTreeCellImpl(){
            MenuItem addMenuItem = new MenuItem("Delete Contact");
            popupMenu.getItems().add(addMenuItem);
            
            addMenuItem.setOnAction(new EventHandler() {
                @Override
                public void handle(Event t) {
                    
                    TreeItem selTI= getTreeItem();
                    selTI.getParent().getChildren().remove(selTI);
                }
            });
        }
        
        
    }
    
   
  
  
   
}