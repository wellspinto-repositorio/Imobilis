package com.wellspinto.imobilis;

import com.formdev.flatlaf.FlatDarkLaf;
import com.wellspinto.funcoes.DataBasePassWordCrypto;
import com.wellspinto.funcoes.Globais;
import com.wellspinto.funcoes.SettingPwd;
import com.wellspinto.funcoes.Settings;
import java.awt.Color;
import java.awt.Insets;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author YOGA 510
 */
public class Imobilis {
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(new FlatDarkLaf() ); } catch (UnsupportedLookAndFeelException ex) { ex.printStackTrace(); }
        UIManager.put( "Button.arc", 999 );
        UIManager.put( "Component.arc", 999 );
        UIManager.put( "ProgressBar.arc", 999 );
        UIManager.put( "TextComponent.arc", 999 );
        UIManager.put( "Component.focusWidth", 2 );
        
        System.out.println("A cor anterior era " + UIManager.getColor("Component.focusColor") );
        UIManager.put( "Component.focusColor", new Color(60,98,140));
        
        UIManager.put( "ScrollBar.thumbArc", 999 );
        UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
        UIManager.put("PasswordField.showRevealButton", true);
        UIManager.put("TitlePane.menuBarEmbedded", Boolean.valueOf(true));
        
        ReadSettings();
        
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("--MAKE")) {
                if (args[1].equalsIgnoreCase("--PWD")) {
                    String crypSenha = ""; String crypExpDate = "";
                    try {
                        crypSenha = DataBasePassWordCrypto.encrypt(args[2].toString(), DataBasePassWordCrypto.ALGORITMO_AES,DataBasePassWordCrypto.ALGORITMO_AES);
                        crypExpDate = DataBasePassWordCrypto.encrypt(args[3].toString(), DataBasePassWordCrypto.ALGORITMO_AES,DataBasePassWordCrypto.ALGORITMO_AES);
                    } catch (Exception e) {}
                    
                    if (new File(System.getProperty("user.dir") + "/Imobilis.aut").exists()) new File(System.getProperty("user.dir") + "/Imobilis.aut").delete();
                    try {
                        FileWriter SiciConfig = new FileWriter(System.getProperty("user.dir") + "/Imobilis.aut");
                        PrintWriter SiciParam = new PrintWriter(SiciConfig);
                        SiciParam.println("DbKey=" + crypSenha);
                        SiciParam.println("Licence=" + crypExpDate);
                        SiciConfig.close();
                    } catch (IOException iox) {iox.printStackTrace();}
                    System.exit(0);
                }
            } 
        }
        
        login lg = new login();
        lg.setVisible(true);
        lg.pack();

    }
    
    private static void ReadSettings() {
        // Settings
        new Settings();

        Globais.ReadSiciParameters();
        
        Globais.units.clear();
        
        String[] _hostl = Globais.remote.split(",");
        if (_hostl.length > 1) Globais.units.add(new String[] {_hostl[0], _hostl[1], _hostl[2]});
     
        for (int w=1;w<=99;w++) {
            String remotn = System.getProperty("Remote" + new DecimalFormat("00").format(w),"");
            
            String[] _host1 = null; String alias = "", host = "", dbname = "";
            if (!"".equals(remotn)) {
                _host1 = remotn.split(",");
                if (_host1.length > 1) {
                    alias = _host1[0];
                    host  = _host1[1];
                    dbname  = _host1[2];
                }
            }
            if (!"".equals(host)) Globais.units.add(new String[] {alias, host, dbname});
        }
        
        new SettingPwd();
        String crySenha  = System.getProperty("Key", "7kf51b");
        if (crySenha != "7kf51b") {
            Globais.sqlPwd = DataBasePassWordCrypto.decrypt(crySenha, DataBasePassWordCrypto.ALGORITMO_AES, DataBasePassWordCrypto.ALGORITMO_AES);
        } else Globais.sqlPwd = crySenha;        
    }    
}
