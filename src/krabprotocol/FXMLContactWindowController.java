package krabprotocol;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scanner.triggerServer;
import services.Currency;
import services.clientScanner;
import services.webScanner;
public class FXMLContactWindowController implements Initializable {
    
    @FXML
    private TreeView treev;
   @FXML
    private Button refreshButton;
    @FXML
    private TextField converToDollar;
    @FXML
    private Label resultConvert;
    @FXML
    private Button authButton;
    @FXML
    private TextField autKey;
    @FXML
    private Button confirmButton;
    @FXML
    private Label autLabel;
    @FXML
    private Pane confirmPanel;
    @FXML
    private Pane filesPane;
    @FXML
    private TreeView files;
    
    private Hashtable<String,ChatWindowController> dictionariChats =new Hashtable<String,ChatWindowController>(); 
    
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
    
    private final String APP_KEY = "43hf0zovvn35d4q";
    private final String APP_SECRET = "082mr748kbyx91h";
    private DbxWebAuthNoRedirect webAuth;
    private DbxRequestConfig config;
    DbxClient client;
        
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BufferedReader reader;
         DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        config = new DbxRequestConfig("Krab-Protocol", Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        try {
            File f = new File("autCode.txt");
            
            if(f.exists()){
                reader = new BufferedReader(new FileReader("autCode.txt"));
                String code = reader.readLine();
                mostrarArchivos(code);
                confirmPanel.setVisible(false);//panel
                filesPane.setVisible(true);
            }else{
                autKey.setVisible(false);
                confirmButton.setVisible(false);
                autLabel.setVisible(false);
                filesPane.setVisible(false);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        filesPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });
        filesPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    for (File file:db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        
                        filePath = filePath.replace("C:\\", "/");
                        filePath = filePath.replace('\\', '/');
                        System.out.println(filePath);
                        //File inputFile = new File("working-draft.txt");
                        FileInputStream inputStream = null;
                        try {
                            inputStream = new FileInputStream(file);
                        
                            DbxEntry.File uploadedFile = client.uploadFile(filePath, DbxWriteMode.add(), file.length(), inputStream);
                            System.out.println("Uploaded: " + uploadedFile.toString());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (DbxException ex) {
                            Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                BufferedReader reader = new BufferedReader(new FileReader("autCode.txt"));
                                String code = reader.readLine();
                                inputStream.close();
                                mostrarArchivos(code);
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        Image imageRefresh = new Image(getClass().getResourceAsStream("img/refreshIcon.png"));
        refreshButton.setGraphic(new ImageView(imageRefresh));
        Image imageDropBox = new Image(getClass().getResourceAsStream("img/dropbox_button50.png"));
        authButton.setGraphic(new ImageView(imageDropBox));
        
        ssc= singletonServerChat.getInstance();
        
        c = new Currency();
        this.resultConvert.setVisible(false);
        this.checkMembersOnline();
     
             
    }
    @FXML
    public void updateMembers(ActionEvent event) throws IOException {
        checkMembersOnline();
    }
    
    private void checkMembersOnline(){
        
        try{
            clientScanner cs = new clientScanner();
            singletonServerChat sc = singletonServerChat.getInstance();
            Hashtable h = cs.getMembersOnline( sc.getIpServer() );
            if(h!=null)
                cargar( h );
            else
               imFisrt();
            
                
        }catch(Exception ex){
            imFisrt();
        }
         
    }
    private void imFisrt(){
        webScanner ws = new webScanner();
        singletonServerChat s = singletonServerChat.getInstance();
        
        try { 
            if (ws.imFirst(s.getUserName(), InetAddress.getLocalHost().getHostAddress() )){ //Comenzar El Scanner
                  System.out.println("Now Im the firts");
                  triggerServer tS = new triggerServer();
                  tS.run();
            }else{
                System.out.println("Iam the Client because Im No the Firts");
                System.out.println( s.getIpServer() );
            }

            s.setIpServer( ws.getFisrtIp());        

            clientScanner cS = new clientScanner();
            cS.addMeatNetwork( s.getUserName() , InetAddress.getLocalHost().getHostAddress() , s.getIpServer());
        }catch(Exception ex){}      
            
    }
    
    @FXML
    public void updateMyCount(ActionEvent event) throws IOException {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEditProfile.fxml"));
           
            Parent root = (Parent) loader.load();
            loader.getController();
            Scene scene = new Scene(root);

            Stage secondStage = new Stage();
            secondStage.setTitle("Register");
            secondStage.setScene(scene);
            
            secondStage.show();
            
    }
    
    
    @FXML
    private void confirmarPermisosADropBox(){
        try {
            String code = autKey.getText();//es el textfield
            DbxAuthFinish authFinish = webAuth.finish(code);
            String accessToken = authFinish.accessToken;
            PrintWriter writer = new PrintWriter("autCode.txt", "UTF-8");
            writer.println(accessToken);
            writer.close();
            client = new DbxClient(config, accessToken);
            System.out.println("Linked account: " + client.getAccountInfo().displayName);
            
            mostrarArchivos(accessToken);
        } catch (DbxException ex) {
            Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void mostrarArchivos(String accessToken){
        try {
            client = new DbxClient(config, accessToken);
            DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
            System.out.println("Files in the root path:");
            TreeItem<String> rootfileItem = new TreeItem<>("/", rootIcon);
            rootfileItem.setExpanded(true);
            
            for(DbxEntry child : listing.children) {
                System.out.println("	" + child.name + ": " + child.toString());
                TreeItem<String> item = new TreeItem<> (child.name);
                if(child.isFolder()){
                    //item.setGraphic(rootIcon);
                    DbxEntry.WithChildren listingf = client.getMetadataWithChildren("/"+child.name);
                    for(DbxEntry folderChild : listingf.children){
                        item.setExpanded(true);
                        item.getChildren().add(new TreeItem<String>(folderChild.name));
                    }
                }else{
                    //item.setGraphic(rootIcon);
                }
                rootfileItem.getChildren().add(item);
            }
            files.setRoot(rootfileItem);//treeview
        } catch (DbxException ex) {
            Logger.getLogger(FXMLContactWindowController.class.getName()).log(Level.SEVERE, null, ex);
            confirmPanel.setVisible(true);//panel
            filesPane.setVisible(false);
        }
        
        confirmPanel.setVisible(false);//panel
        filesPane.setVisible(true);
    }
    
    @FXML
    private void seleccionarunArchivo(){
        
    }
    
    @FXML
    private void darPermisosADropbox(){

        // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
        System.out.println(authorizeUrl);
        // Abre browser para autorizar DropBox
        autKey.setVisible(true);
        confirmButton.setVisible(true);
        autLabel.setVisible(true);
        authButton.setVisible(false);
	//Abrir en WebView
       javafx.application.Platform.runLater(new Runnable(){

            @Override
            public void run() {
                Stage webStage = new Stage();
                WebView  browser = new WebView();
                WebEngine engine = browser.getEngine();
                System.out.println(authorizeUrl);
                engine.load(authorizeUrl);
        
                StackPane sp = new StackPane();
                sp.getChildren().add(browser);
		
                Scene root = new Scene(sp);
                webStage.setScene(root);
                webStage.show();
            }
           
       });
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
    
  


    final void cargar( Hashtable Users){
        Enumeration<String> elemnts = Users.keys();
        
        singletonServerChat.userOnline = Users;
        
        TreeItem<String> rootfileItem = new TreeItem<>("Members Online", rootIcon);
        rootfileItem.setExpanded(true);
        while(elemnts.hasMoreElements()){
            
            TreeItem<String> item = new TreeItem<> ( elemnts.nextElement() );
            rootfileItem.getChildren().add(item);
        }
        treev.setRoot(rootfileItem);
        
        treev.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event){
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2){
                        TreeItem<String> ev = (TreeItem) treev.getSelectionModel().getSelectedItem();
                        System.out.println("Me hiciste Click  " + ev.getValue());
                        System.out.println( "La ip es  " +singletonServerChat.userOnline.get(ev.getValue()) + " "); 
                        
                        singletonServerChat sc = singletonServerChat.getInstance();
                        FXMLContactWindowController x =sc.getFXMLContactWindowController();
                        x.makeChat( ev.getValue() , (String) singletonServerChat.userOnline.get(ev.getValue()) );
                        
                     
                    }
                }
            }
        });
        
    }
    
    public void makeChat(String userName, String ip){
        if(!singletonServerChat.dictionariChats.containsKey(userName)){
            
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
                        Parent root = (Parent) loader.load();
                        Scene scene = new Scene(root);
                        Stage secondStage = new Stage();

                        secondStage.setTitle("New chat with :"+userName);
                        secondStage.setScene(scene);

                        ChatWindowController sW =loader.getController();
                        sW.setIPReceiver(ip);
                        sW.setNameChat(userName);

                        /*
                        singletonServerChat ssh = singletonServerChat.getInstance();
                        FXMLContactWindowController x =ssh.getFXMLContactWindowController();
                        x.AddNewChat(userName, loader.getController());
                        */
                        ChatWindowController controller = loader.getController();
                        controller.putMessage("conexion with :" + ip);
                        
                        controller.setNameChat(userName);
                        controller.setIPReceiver(ip);
                        secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        
                            public void handle(WindowEvent we) {
                                singletonServerChat.dictionariChats.remove(userName);
                                
                                secondStage.close();
                                
                            }});
                        
                        singletonServerChat.dictionariChats.put(userName,controller);
                        
                        System.err.println("Usuario agregado " + userName);
                       
                        secondStage.show();

                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                    }
                });
        }else{
            System.err.println("El usuario ya existe");
        }
    }
    
    public void AddNewChat(String userName , ChatWindowController controller){
        System.out.println("Se agrego a  "+ userName);
        this.dictionariChats.put(userName, controller);
    }
    
    public ChatWindowController getChatWindowController(String userName){
        //System.err.println("buscar a "+ userName);
        return this.dictionariChats.get(userName);
    }
    
    public Hashtable getHashTable(){
        return this.dictionariChats;
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

    public void reload(){
    
    Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("alert.fxml"));
                        Parent root = (Parent) loader.load();
                        Scene scene = new Scene(root);
                        Stage secondStage = new Stage();

                        secondStage.setTitle("Alert");
                        secondStage.setScene(scene);
                        
                        secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {


                                webScanner wS = new webScanner();
                                singletonServerChat  sS = singletonServerChat.getInstance();
                                wS.removeUser( sS.getUserName() );


                                System.exit(1);


                        }});
                        
                        secondStage.show();
                        
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                    }
                });

    
    }
 public void over (ActionEvent event) throws IOException {
      
 }
   

}