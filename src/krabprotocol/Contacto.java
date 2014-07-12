package krabprotocol;

public class Contacto {
    
    private String username;
    private String status;
    private String grupo;
    
    
    public Contacto(String username){
        this.username = username;
    }

    public Contacto(String username, String grupo){
        this.username = username;
        this.grupo = grupo;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getGrupo(){
        return this.grupo;
    }
}
