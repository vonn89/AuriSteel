/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View;


import static com.excellentsystem.AuriSteel.Function.decrypt;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.key;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class LoginController  {
    @FXML private Label versionLabel;
    @FXML private Label warning;
    @FXML private TextField username;
    @FXML private PasswordField password;
    private Main mainApp;
    private int attempt = 0;
    @FXML 
    private void handleLoginButton() throws Exception{
        if("".equals(username.getText())){
            warning.setText("username masih kosong");
        }else if(password.getText().equals("")){
            warning.setText("password masih kosong");
        }else if(attempt>=3){
            System.exit(0);
        }else{
            try{
                User user = null;
                for(User u : sistem.getListUser()){
                    if(u.getKodeUser().equals(username.getText()))
                        user = u;
                }
                if(user==null){
                    warning.setText("Username tidak ditemukan");
                    attempt = attempt +1;
                }else if(!password.getText().equals(decrypt(user.getPassword(), key))){
                    warning.setText("Password masih salah");
                    attempt = attempt +1;
                }else{
                    sistem.setUser(user);
                    mainApp.showMainScene();
                }
            }catch(Exception e){
                e.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }
    public void setMainApp(Main mainApp){
        try{
            this.mainApp = mainApp;
            versionLabel.setText("Ver. "+mainApp.version);
            warning.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
            warning.setText(ex.toString());
        }
    }
      

    
    
}
