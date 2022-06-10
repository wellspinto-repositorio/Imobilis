package com.wellspinto.imobilis;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatTextField;
import com.formdev.flatlaf.icons.FlatSearchWithHistoryIcon;
import com.wellspinto.funcoes.BackGroundDeskTopPane;
import com.wellspinto.funcoes.Globais;
import com.wellspinto.funcoes.PrinterLister;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.print.PrintService;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;

/**
 *
 * @author YOGA 510
 */
public class MenuPrincipal extends javax.swing.JFrame {
    private int searchBarWidth = 300;
    private int searchBarHeigth = 30;
    private Color searchBarFocusColor = new Color(60,98,140);
    private JScrollPane searcBox = new JScrollPane();
    private boolean searchBoxCreated = false;
    private JPopupMenu popupMenu = new JPopupMenu();
    private ButtonGroup bg = new ButtonGroup();
    private FlatTextField searchBar = new FlatTextField();

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        initComponents();

        setTitle(".:: Imobilis ::.");
        setIconImage(new ImageIcon(getClass().getResource("/figuras/logo.png")).getImage());
        InitSettings();
        
        userName.setText(Globais.userName);
        hostName.setText(Globais.sqlAlias);
    }

    private void InitSettings() {
        // Search Menu Options
        {
            JCheckBoxMenuItem propCheckBoxMenuItem = new JCheckBoxMenuItem();
            propCheckBoxMenuItem.setText("Proprietários");
            propCheckBoxMenuItem.setMnemonic('P');
            propCheckBoxMenuItem.setSelected(true);
            bg.add(propCheckBoxMenuItem);

            JCheckBoxMenuItem imovCheckBoxMenuItem = new JCheckBoxMenuItem();
            imovCheckBoxMenuItem.setText("Imóveis");
            imovCheckBoxMenuItem.setMnemonic('I');
            imovCheckBoxMenuItem.setSelected(false);
            bg.add(imovCheckBoxMenuItem);

            JCheckBoxMenuItem locaCheckBoxMenuItem = new JCheckBoxMenuItem();
            locaCheckBoxMenuItem.setText("Locatários");
            locaCheckBoxMenuItem.setMnemonic('L');
            locaCheckBoxMenuItem.setSelected(false);
            bg.add(locaCheckBoxMenuItem);

            JCheckBoxMenuItem fiadCheckBoxMenuItem = new JCheckBoxMenuItem();
            fiadCheckBoxMenuItem.setText("Fiadores");
            fiadCheckBoxMenuItem.setMnemonic('F');
            fiadCheckBoxMenuItem.setSelected(false);
            bg.add(fiadCheckBoxMenuItem);

            JCheckBoxMenuItem moviCheckBoxMenuItem = new JCheckBoxMenuItem();
            moviCheckBoxMenuItem.setText("Movimentos");
            moviCheckBoxMenuItem.setMnemonic('I');
            moviCheckBoxMenuItem.setSelected(false);
            bg.add(moviCheckBoxMenuItem);

            popupMenu.add(propCheckBoxMenuItem);
            popupMenu.add(imovCheckBoxMenuItem);
            popupMenu.add(locaCheckBoxMenuItem);
            popupMenu.add(fiadCheckBoxMenuItem);
            popupMenu.addSeparator();
            popupMenu.add(moviCheckBoxMenuItem);
        }        
                
        FlatSVGIcon themeIcon = new FlatSVGIcon("icons/theme.svg",16,16);
        themeIcon.setColorFilter( new FlatSVGIcon.ColorFilter( color -> Color.WHITE ) );
        JToggleButton themeSet = new JToggleButton((Icon)themeIcon);
        themeSet.setToolTipText("Tema Claro ou Escuro.");
        themeSet.addActionListener((e) -> {
            EventQueue.invokeLater(() -> {
                try {
                    String themeFlat = null;
                    if (themeSet.isSelected()) {
                        themeIcon.setColorFilter( new FlatSVGIcon.ColorFilter( color -> Color.BLACK ) );
                        themeFlat = FlatLightLaf.class.getName();
                    } else {
                        themeFlat = FlatDarkLaf.class.getName();
                        themeIcon.setColorFilter( new FlatSVGIcon.ColorFilter( color -> Color.WHITE ) );
                    }
                    UIManager.setLookAndFeel(themeFlat);
                    FlatLaf.updateUI();
                } catch (Exception ex) {}
            });

        });
        
        FlatSVGIcon laserIconPrinter = new FlatSVGIcon("icons/laser.svg",16,16);
        laserIconPrinter.setColorFilter( new FlatSVGIcon.ColorFilter( color -> Color.red ) );
        JToggleButton laserPrinter = new JToggleButton((Icon)laserIconPrinter);                        
        laserPrinter.setToolTipText("Impressora Laser esta Desativada.");
        laserPrinter.addActionListener((e) -> {
            if (laserPrinter.isSelected()) {
                laserIconPrinter.setColorFilter( new FlatSVGIcon.ColorFilter( color -> new Color(50,205,50) ) );
                laserPrinter.setToolTipText("Impressora Laser esta Ativa."); 
            } else {
                laserIconPrinter.setColorFilter( new FlatSVGIcon.ColorFilter( color -> Color.red ) );
                laserPrinter.setToolTipText("Impressora Laser esta Desativada.");
            }
        });
        
        FlatSVGIcon thermalIconPrinter = new FlatSVGIcon("icons/thermal.svg",16,16);
        thermalIconPrinter.setColorFilter( new FlatSVGIcon.ColorFilter( color -> Color.red ) );
        JToggleButton thermalPrinter = new JToggleButton((Icon)thermalIconPrinter);
        thermalPrinter.setToolTipText("Impressora Thermica esta Desativada.");
        thermalPrinter.addActionListener((e) -> {
            if (thermalPrinter.isSelected()) {
                thermalIconPrinter.setColorFilter( new FlatSVGIcon.ColorFilter( color -> new Color(50,205,50) ) );
                thermalPrinter.setToolTipText("Impressora Thermica esta Ativa.");
            } else {
                thermalIconPrinter.setColorFilter( new FlatSVGIcon.ColorFilter( color -> Color.red ) );
                thermalPrinter.setToolTipText("Impressora Thermica esta Desativada.");
            }
        });
        thermalPrinter.addMouseListener(new MouseAdapter() { 
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK && e.getClickCount() == 1) {
                JPopupMenu popupMenu = new JPopupMenu();
                
                PrinterLister pLister = new PrinterLister();
                for(PrintService ps : pLister.getPrinters()){
                    popupMenu.add(ps.getName());
		}
                popupMenu.show(thermalPrinter, 0, thermalPrinter.getHeight());
                }
            }
        });
                
        JToolBar printerToolbar = new JToolBar();
        printerToolbar.add(themeSet);
        printerToolbar.addSeparator();
        printerToolbar.add(laserPrinter);
        printerToolbar.add(thermalPrinter);
        printerToolbar.addSeparator();
        
        FlatButton emailButton = new FlatButton();
        emailButton.setIcon((Icon)new FlatSVGIcon("icons/carta.svg",16,16));
        emailButton.setButtonType(FlatButton.ButtonType.toolBarButton);
        emailButton.setFocusable(false);
        emailButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Hello User! How are you?", "User", 1));

        // seachBar Initializacion
        searchBar.setPreferredSize(new Dimension(searchBarWidth, searchBarHeigth));
        searchBar.setMaximumSize(new Dimension(searchBarWidth, searchBarHeigth));
        searchBar.setMinimumSize(new Dimension(searchBarWidth, searchBarHeigth));
        searchBar.setSize(searchBarWidth, searchBarHeigth);
        searchBar.setFocusable(true);
        searchBar.putClientProperty("Component.focusWidth", 0);
        searchBar.putClientProperty("Component.focusColor", searchBarFocusColor);
        
        JButton searchHistoryButton = new JButton((Icon)new FlatSearchWithHistoryIcon(true));
        searchHistoryButton.setToolTipText("Search History");
        searchHistoryButton.addActionListener(e -> {
          popupMenu.show(searchHistoryButton, 0, searchHistoryButton.getHeight());
        });
        searchBar.putClientProperty("JTextField.leadingComponent", searchHistoryButton);
        
        JToggleButton matchCaseButton = new JToggleButton((Icon)new FlatSVGIcon("icons/matchCase.svg"));
        matchCaseButton.setRolloverIcon((Icon)new FlatSVGIcon("icons/matchCaseHovered.svg"));
        matchCaseButton.setSelectedIcon((Icon)new FlatSVGIcon("icons/matchCaseSelected.svg"));
        matchCaseButton.setToolTipText("Caso Sensitivo");
        JToggleButton wordsButton = new JToggleButton((Icon)new FlatSVGIcon("icons/words.svg"));
        wordsButton.setRolloverIcon((Icon)new FlatSVGIcon("icons/wordsHovered.svg"));
        wordsButton.setSelectedIcon((Icon)new FlatSVGIcon("icons/wordsSelected.svg"));
        wordsButton.setToolTipText("Frase Inteira");
    
        searchBar.setPlaceholderText("Busca Globalizada");
        
        JToolBar searchToolbar = new JToolBar();
        searchToolbar.addSeparator();
        searchToolbar.add(matchCaseButton);
        searchToolbar.add(wordsButton);

        searchBar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (searchBar.getText().trim().length() >= 3) {                    
                    searchBox();
                    repaint();
                }
                searcBox.setVisible(searchBar.getText().trim().length() >= 3);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        
        searchBar.putClientProperty("JTextField.trailingComponent", searchToolbar);
        searchBar.putClientProperty("JTextField.showClearButton", Boolean.valueOf(true));
        searchBar.putClientProperty("JTextField.showClearButton", Boolean.valueOf(true));
    
        menuBar.add(Box.createGlue());
        menuBar.add((Component)printerToolbar);
        menuBar.add((Component)searchBar);
        menuBar.add((Component)emailButton);
    }
    
    private void searchBox() {
        if (!searchBoxCreated) {
            final int searchBoxWidth = 600;
            final int searchBoxHeight = 300;
            //JScrollPane searcBox = new JScrollPane();
            Dimension searchBoxDimension = new Dimension(searchBoxWidth, searchBoxHeight);
            searcBox.setSize(searchBoxDimension);
            searcBox.setPreferredSize(searchBoxDimension);
            searcBox.setMaximumSize(searchBoxDimension);
            searcBox.setMinimumSize(searchBoxDimension);

            int posEnd = searchBar.getX();
            int posStart = posEnd + (32) + searchBarWidth  - searchBoxWidth;
            searcBox.setLocation(posStart , -2);

            JTable jtbl = new JTable();
            jtbl.setSize(searchBoxDimension);
            jtbl.setPreferredSize(searchBoxDimension);
            jtbl.setMaximumSize(searchBoxDimension);
            jtbl.setMinimumSize(searchBoxDimension);
            jtbl.setVisible(true);

            searcBox.add(jtbl);

            add(searcBox,0);
            searcBox.setVisible(false);
            searchBoxCreated = true;
        }            
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WorkArea = new BackGroundDeskTopPane(Globais.backGround);
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        userName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        hostName = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Imobilis");
        setMaximumSize(new java.awt.Dimension(1366, 768));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Usuário Logado:");

        jLabel3.setText("Local na Rede:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hostName, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(userName)
                    .addComponent(jLabel3)
                    .addComponent(hostName))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Contents");
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(WorkArea)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(WorkArea, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        this.dispose();
        
        Globais.conn = null;
        
        login lg = new login();
        lg.setVisible(true);
        lg.pack();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane WorkArea;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel hostName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables

}
/*
        WorkArea = new JDesktopPane() {
            Image newimage = null;
            {
                ImageIcon icon = null;
                if (Globais.backGround.isEmpty()) {
                    URL url = getClass().getResource("/figuras/Imobilis-wallpaper.jpg");
                    icon = new ImageIcon(url);
                } else {
                    String url = Globais.backGround;
                    icon = new ImageIcon(url);
                }

                //Dimension dDesktop = this.getSize();
                int width = 1366; //(int)dDesktop.getWidth();
                int height = 768; //(int)dDesktop.getHeight();

                Image image = icon.getImage();
                newimage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(newimage, 0, 0, this);
            }
        };
*/