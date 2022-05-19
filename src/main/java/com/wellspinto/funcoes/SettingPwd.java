package com.wellspinto.funcoes;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;
import javax.swing.JOptionPane;

public class SettingPwd {
    private static final String dbPassWordSettingPath = System.getProperty("user.dir") + "/Imobilis.aut";
    
    public SettingPwd() {
        boolean exists = (new File(dbPassWordSettingPath)).exists();
        if (!exists) {
            JOptionPane.showMessageDialog(null, "Programa sem autorização!!!\n\nEntre em contato com a WellSoft Tel.: (21)97665-9897", "Erro de Licença", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        Properties p;
        FileInputStream propFile;
        try {
            propFile = new FileInputStream(dbPassWordSettingPath);
            p = new Properties(System.getProperties()){
                @Override
                public synchronized Enumeration<Object> keys() {
                    return Collections.enumeration(new TreeSet<Object>(super.keySet()));
                }
            };
            p.load(propFile);

            System.setProperties(p);
        } catch (Exception ex) {ex.printStackTrace();}                
    }
}
