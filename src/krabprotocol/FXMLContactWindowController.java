package krabprotocol;

import chat.triggerChat;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import services.Currency;

public class FXMLContactWindowController implements Initializable {

    
    @FXML
    private TreeView treev;
    
    @FXML
    private TextField converToDollar;
    
    @FXML
    private Label resultConvert;
 
    
    private Currency c;
    
    private double pesos;
    
    private TreeItem<String> rootItem;
    
    private final Node rootIcon = new ImageView(
        new Image(getClass().getResourceAsStream("img/MSN-icon.png"))
    );
    private final Node contactIcon = new ImageView(
        new Image(getClass().getResourceAsStream("img/MSN-icon.png"))
    );
    
    //lista de ejemplo borrar luego
        List<Contacto> contacto = Arrays.asList(
            new Contacto("Shago", "Amigos"), new Contacto("Pepe", "Amigos"),
            new Contacto("Juan", "Amigos"), new Contacto("Sergio", "Amigos"),
            new Contacto("Filomeno", "Amigos"), new Contacto("Vaquita Alpura", "Compañeros de Trabajo"),
            new Contacto("Chica QR en pompas","Compañeros de Trabajo"));
    
        
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        c = new Currency();
        this.resultConvert.setVisible(false);
        cargar();
   
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
    


    public void cargar(){
        //crea la raiz del arbol
        rootItem = new TreeItem<>("Contacts", rootIcon);
        rootItem.setExpanded(true);
        
        //llena el arbol XD borrar luego quiza
        for (Contacto cont : contacto) {
            TreeItem<String> item = new TreeItem<> (cont.getUsername());
            boolean found = false;
            for(TreeItem<String> depNode:rootItem.getChildren()){
                if(depNode.getValue().contentEquals(cont.getGrupo())){
                    depNode.getChildren().add(item);
                    found = true;
                    break;
                }
            }
            if(!found){
                TreeItem<String> depNode = new TreeItem<>(cont.getGrupo());
                rootItem.getChildren().add(depNode);
                depNode.getChildren().add(item);
            }
        }
        //obtiene el TreeView del FXML
        //TreeView<String> tree = (TreeView) getScene.lookup("#treev");
        //treev = new TreeView();
        treev.setRoot(rootItem);//configura la raiz
        
        // lo hace editable e implementa cellfactory
        treev.setEditable(true);
        treev.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>(){
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new TextFieldTreeCellImpl();
            }
        });
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
        
        //@Override
        public void startEdit(){
            super.startEdit();
            
            System.out.println("XD Me haces cosquillas");
            if(textfield == null){
                createTextField();
            }
            setText(null);
            setGraphic(textfield);
            textfield.selectAll();
        }
        
        @Override
        public void cancelEdit(){
            super.cancelEdit();
            setText((String)getItem());
            setGraphic(getTreeItem().getGraphic());
        }
        
        @Override
        public void updateItem(String item, boolean empty){
            super.updateItem(item, empty);
            
            if(empty){
                setText(null);
                setGraphic(null);
            } else {
                if(isEditing()){
                    
                    //
                    if(textfield !=null){
                        textfield.setText(getString());
                    }
                    setText(null);
                    setGraphic(textfield);
                    //
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    if(getTreeItem().isLeaf()){
                        setContextMenu(popupMenu);
                    }
                }
            }
        }
        ///
        private void createTextField(){
            textfield = new TextField(getString());
            textfield.setOnKeyReleased(new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent k){
                    if(k.getCode() == KeyCode.ENTER){
                        commitEdit(textfield.getText());
                    }else if(k.getCode() == KeyCode.ESCAPE){
                        cancelEdit();
                    }
                }
            });
        }//
        
        private String getString(){
            return getItem() == null? "": getItem().toString();
        }

    }
}