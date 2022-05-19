package com.wellspinto.funcoes;

import java.util.ArrayList;

public class Globais {
    /***************************************************************************
     *                                                                         *
     * Variaveis Globais do sistema                                            *
     * Criado por: Wellington de Souza Pinto                    Em: 07-05-2022 *
     * ----------------------------------------------------------------------- *
     * 
    */
    static public Db conn = null;
    static public String sqlDrive = "com.mysql.cj.jdbc.Driver";
    static public String sqlUrl = "jdbc:mysql://";
    static public String sqlAlias = "Máquina Local";
    static public String sqlHost = "127.0.0.1";
    static public int sqlPort = 3306;
    static public String sqlConn = "?createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
    static public String sqlUser = "root";
    static public String sqlPwd = "7kf51b";
    static public String sqlDbName = "sici";
    
    static public String  remote  = "";
    static public ArrayList<String[]> units = new ArrayList<>();
    
    static public void ReadSiciParameters() {
        // Parametros de conexção do banco de dados
        remote = System.getProperty("Remote", sqlAlias + "," + sqlHost + ":" + sqlPort + "," + sqlDbName);
    }
}
