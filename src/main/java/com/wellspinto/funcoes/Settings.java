package com.wellspinto.funcoes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

public class Settings {
    static private String GlobalPropert = System.getProperty("user.dir") + "/Imobilis.conf";
    static private String mFile = "";
    private Properties p;
    FileInputStream propFile;
    
    public Settings() {
        String osname = System.getProperty("os.name").toUpperCase().trim();
        String localPropert = "";
        if (osname.equalsIgnoreCase("LINUX") || osname.equalsIgnoreCase("MAC")) {
            localPropert = System.getProperty("user.home") + "/Imobilis.conf";
        } else {
            localPropert = "C://Imobilis.conf";
        }
    
        boolean exists = (new File(localPropert)).exists();
        
        if (exists) {
            mFile = localPropert;
        } else {
            mFile = GlobalPropert;
        }
        
        try {
            propFile = new FileInputStream(mFile);
            p = new Properties(System.getProperties()){
                @Override
                public synchronized Enumeration<Object> keys() {
                    return Collections.enumeration(new TreeSet<Object>(super.keySet()));
                }
            };
            p.load(propFile);

            System.setProperties(p);
        } catch (Exception ex) {ex.printStackTrace();}
        
        //System.out.println(localPropert);
    }
    
    public void Save(String propriedade, String Valor) {
        try {
            System.setProperty(propriedade, Valor);
            p.setProperty(propriedade, Valor);
            FileOutputStream outFile = new FileOutputStream(mFile);
            
            p.store(outFile,"Imobilis.propriedades");
            outFile.close();
        } catch (Exception ex) {ex.printStackTrace();}
    }    
}
