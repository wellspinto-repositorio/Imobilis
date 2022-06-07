package com.wellspinto.imobilis;

import com.formdev.flatlaf.FlatDarkLaf;
import com.wellspinto.funcoes.DataBasePassWordCrypto;
import com.wellspinto.funcoes.Globais;
import java.awt.Color;
import java.awt.Insets;
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
        UIManager.put( "Component.focusWidth", 1 );
        UIManager.put( "Component.focusColor", new Color(60,98,140));
        UIManager.put( "ScrollBar.thumbArc", 999 );
        UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
        UIManager.put("PasswordField.showRevealButton", true);
        UIManager.put("TitlePane.menuBarEmbedded", Boolean.valueOf(true));
        
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("--MAKE")) {
                if (args[1].equalsIgnoreCase("--PWD")) {
                    String crypSenha = ""; String crypExpDate = "";
                    try {
                        crypSenha = DataBasePassWordCrypto.encrypt(args[2].toString(), DataBasePassWordCrypto.ALGORITMO_AES,DataBasePassWordCrypto.ALGORITMO_AES);
                        crypExpDate = DataBasePassWordCrypto.encrypt(args[3].toString(), DataBasePassWordCrypto.ALGORITMO_AES,DataBasePassWordCrypto.ALGORITMO_AES);
                    } catch (Exception e) {}
                    Globais.prefs.put("Key", crypSenha);
                    Globais.prefs.put("Licence", crypExpDate);
                } else if (args[1].equalsIgnoreCase("--REMOTE")) {
                    if (args[2].equalsIgnoreCase("ADD")) {
                        String _nn = args[3].toString();
                        String _alias = args[4].toString();
                        String _host = args[5].toString();
                        int _port = Integer.parseInt(args[6].toString());
                        String _database = args[7].toString();
                        
                        System.out.println(args[0].toString() + " " + args[1].toString() + " " + args[2].toString() + " " + _nn +
                                _alias + "," + _host + ":" + _port + "," + _database);
                        Globais.prefs.put("Remote" + _nn, _alias + "," + _host + ":" + _port + "," + _database);
                    } else if (args[2].equalsIgnoreCase("DEL")) {
                        System.out.println(args[0].toString() + " " + args[1].toString() + " " + args[2].toString() + " " + args[3].toString());
                        Globais.prefs.remove("Remote" + args[3].toString());
                    } else {
                        System.out.println("--MAKE --REMOTE commands:");
                        System.out.println("=====================================================");
                        System.out.println("ADD - Add the Remote login parameter");
                        System.out.println("DEL - Delete the Remote login parameter");
                        System.out.println("-----------------------------------------------------");
                        System.out.println("");
                        System.out.println("Command sintaxe:");
                        System.out.println("");
                        System.out.println("--MAKE --REMOTE ADD NN ALIAS HOST PORT DATABASE");
                        System.out.println("--MAKE --REMOTE DEL NN");
                        System.out.println("");
                    }
                } else if (args[1].equalsIgnoreCase("--PARAM")) {
                    if (args[2].equalsIgnoreCase("ADD")) {
                        String _type = args[3].toString();
                        String _key = args[4].toString();
                        String _value = args[5].toString();
                    } else if(args[2].equalsIgnoreCase("DEL")) {
                    
                    } else {
                        System.out.println("--MAKE --PARAM commands:");
                        System.out.println("====================================");
                        System.out.println("ADD - Add parameters for imobilis");
                        System.out.println("DEL - Delete parameters for imobilis");
                        System.out.println("------------------------------------");
                        System.out.println("");
                        System.out.println("Command Sintaxe:");
                        System.out.println("");
                        System.out.println("-- MAKE --PARAM [ADD|DEL] <<TYPE>> KEY VALUE");
                        System.out.println("");
                        System.out.println("<<TYPE>> - STRING; INTEGER; BOOLEAN; DATE");
                        System.out.println("");
                    }
                }
            } 
        }
        
        ReadSettings();

        login lg = new login();
        lg.setVisible(true);
        lg.pack();

    }
    
    private static void ReadSettings() {
        Globais.ReadSiciParameters();
        Globais.units.clear();
        String[] _hostl = Globais.remote.split(",");
        if (_hostl.length > 1) Globais.units.add(new String[] {_hostl[0], _hostl[1], _hostl[2]});
        for (int w=1;w<=99;w++) {
            String remotn = Globais.prefs.get("Remote" + new DecimalFormat("00").format(w),"");
            
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
        
        String crySenha  = Globais.prefs.get("Key", "");
        if (crySenha != "") {
            Globais.sqlPwd = DataBasePassWordCrypto.decrypt(crySenha, DataBasePassWordCrypto.ALGORITMO_AES, DataBasePassWordCrypto.ALGORITMO_AES);
        } else Globais.sqlPwd = crySenha;        
    }    
}
