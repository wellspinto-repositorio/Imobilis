package com.wellspinto.imobilis;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatTextField;
import com.formdev.flatlaf.icons.FlatSearchWithHistoryIcon;
import com.wellspinto.funcoes.Globais;
import com.wellspinto.funcoes.PrinterLister;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.print.PrintService;
import javax.swing.Box;
import javax.swing.Icon;

/**
 *
 * @author YOGA 510
 */
public class principal extends javax.swing.JFrame {
    private int searchBarWidth = 300;
    private int searchBarHeigth = 30;
    private Color searchBarFocusColor = new Color(60,98,140); //60,98,140
    /**
     * Creates new form principal
     */
    public principal() {
        initComponents();
        
        JToggleButton themeSet = new JToggleButton((Icon)new FlatSVGIcon("com/wellspinto/icons/github.svg"));
        //themeSet.setRolloverIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/laserPrinterHover.svg"));
        //themeSet.setSelectedIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/laserPrinterOn.svg"));
        themeSet.setToolTipText("Tema Claro ou Escuro.");
        themeSet.addActionListener((e) -> {
            EventQueue.invokeLater(() -> {
                try {
                    String themeFlat = null;
                    if (themeSet.isSelected()) {
                        themeFlat = FlatLightLaf.class.getName();
                    } else {
                        themeFlat = FlatDarkLaf.class.getName();
                    }
                    UIManager.setLookAndFeel(themeFlat);
                    FlatLaf.updateUI();
                } catch (Exception ex) {}
            });

        });
        
        JToggleButton laserPrinter = new JToggleButton((Icon)new FlatSVGIcon("com/wellspinto/icons/laserPrinterOff.svg"));
        laserPrinter.setRolloverIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/laserPrinterHover.svg"));
        laserPrinter.setSelectedIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/laserPrinterOn.svg"));
        laserPrinter.setToolTipText("Impressora Laser esta Desativada.");
        laserPrinter.addActionListener((e) -> {
            if (laserPrinter.isSelected()) {
                laserPrinter.setToolTipText("Impressora Laser esta Ativa.");
            } else {
                laserPrinter.setToolTipText("Impressora Laser esta Desativada.");
            }
        });
        
        JToggleButton thermalPrinter = new JToggleButton((Icon)new FlatSVGIcon("com/wellspinto/icons/thermalPrinterOff.svg"));
        thermalPrinter.setRolloverIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/thermalPrinterHover.svg"));
        thermalPrinter.setSelectedIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/thermalPrinterOn.svg"));
        thermalPrinter.setToolTipText("Impressora Laser esta Desativada.");
        thermalPrinter.addActionListener((e) -> {
            if (thermalPrinter.isSelected()) {
                thermalPrinter.setToolTipText("Impressora Thermica esta Ativa.");
            } else {
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
                //popupMenu.add("(empty)");
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
        emailButton.setIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/users.svg"));
        emailButton.setButtonType(FlatButton.ButtonType.toolBarButton);
        emailButton.setFocusable(false);
        emailButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Hello User! How are you?", "User", 1));

        FlatTextField searchBar = new FlatTextField();
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
          JPopupMenu popupMenu = new JPopupMenu();
          popupMenu.add("(empty)");
          popupMenu.show(searchHistoryButton, 0, searchHistoryButton.getHeight());
        });
        searchBar.putClientProperty("JTextField.leadingComponent", searchHistoryButton);
        
        JToggleButton matchCaseButton = new JToggleButton((Icon)new FlatSVGIcon("com/wellspinto/icons/matchCase.svg"));
        matchCaseButton.setRolloverIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/matchCaseHovered.svg"));
        matchCaseButton.setSelectedIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/matchCaseSelected.svg"));
        matchCaseButton.setToolTipText("Caso Sensitivo");
        JToggleButton wordsButton = new JToggleButton((Icon)new FlatSVGIcon("com/wellspinto/icons/words.svg"));
        wordsButton.setRolloverIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/wordsHovered.svg"));
        wordsButton.setSelectedIcon((Icon)new FlatSVGIcon("com/wellspinto/icons/wordsSelected.svg"));
        wordsButton.setToolTipText("Frase Inteira");
    
        searchBar.setPlaceholderText("Busca Globalizada");
        
        JToolBar searchToolbar = new JToolBar();
        searchToolbar.addSeparator();
        searchToolbar.add(matchCaseButton);
        searchToolbar.add(wordsButton);

        searchBar.putClientProperty("JTextField.trailingComponent", searchToolbar);
        searchBar.putClientProperty("JTextField.showClearButton", Boolean.valueOf(true));
        searchBar.putClientProperty("JTextField.showClearButton", Boolean.valueOf(true));
    
        menuBar.add(Box.createGlue());
        menuBar.add((Component)printerToolbar);
        menuBar.add((Component)searchBar);
        menuBar.add((Component)emailButton);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        openMenuItem = new JMenuItem();
        saveMenuItem = new JMenuItem();
        saveAsMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        editMenu = new JMenu();
        cutMenuItem = new JMenuItem();
        copyMenuItem = new JMenuItem();
        pasteMenuItem = new JMenuItem();
        deleteMenuItem = new JMenuItem();
        helpMenu = new JMenu();
        contentMenuItem = new JMenuItem();
        aboutMenuItem = new JMenuItem();
        desktopPane = new JDesktopPane();
        jToolBar2 = new JToolBar();
        jLabel1 = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Imobilis");
        setIconImage(new ImageIcon(getClass().getResource("/com/wellspinto/figuras/logo.png")).getImage());
        Container contentPane = getContentPane();

        //======== menuBar ========
        {

            //======== fileMenu ========
            {
                fileMenu.setMnemonic('f');
                fileMenu.setText("File");

                //---- openMenuItem ----
                openMenuItem.setMnemonic('o');
                openMenuItem.setText("Open");
                fileMenu.add(openMenuItem);

                //---- saveMenuItem ----
                saveMenuItem.setMnemonic('s');
                saveMenuItem.setText("Save");
                fileMenu.add(saveMenuItem);

                //---- saveAsMenuItem ----
                saveAsMenuItem.setMnemonic('a');
                saveAsMenuItem.setText("Save As ...");
                saveAsMenuItem.setDisplayedMnemonicIndex(5);
                fileMenu.add(saveAsMenuItem);

                //---- exitMenuItem ----
                exitMenuItem.setMnemonic('x');
                exitMenuItem.setText("Exit");
                exitMenuItem.addActionListener(e -> exitMenuItemActionPerformed(e));
                fileMenu.add(exitMenuItem);
            }
            menuBar.add(fileMenu);

            //======== editMenu ========
            {
                editMenu.setMnemonic('e');
                editMenu.setText("Edit");

                //---- cutMenuItem ----
                cutMenuItem.setMnemonic('t');
                cutMenuItem.setText("Cut");
                editMenu.add(cutMenuItem);

                //---- copyMenuItem ----
                copyMenuItem.setMnemonic('y');
                copyMenuItem.setText("Copy");
                editMenu.add(copyMenuItem);

                //---- pasteMenuItem ----
                pasteMenuItem.setMnemonic('p');
                pasteMenuItem.setText("Paste");
                editMenu.add(pasteMenuItem);

                //---- deleteMenuItem ----
                deleteMenuItem.setMnemonic('d');
                deleteMenuItem.setText("Delete");
                editMenu.add(deleteMenuItem);
            }
            menuBar.add(editMenu);

            //======== helpMenu ========
            {
                helpMenu.setMnemonic('h');
                helpMenu.setText("Help");

                //---- contentMenuItem ----
                contentMenuItem.setMnemonic('c');
                contentMenuItem.setText("Contents");
                helpMenu.add(contentMenuItem);

                //---- aboutMenuItem ----
                aboutMenuItem.setMnemonic('a');
                aboutMenuItem.setText("About");
                helpMenu.add(aboutMenuItem);
            }
            menuBar.add(helpMenu);
        }
        setJMenuBar(menuBar);

        //======== desktopPane ========
        {
            desktopPane.setLayout(null);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < desktopPane.getComponentCount(); i++) {
                    Rectangle bounds = desktopPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = desktopPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                desktopPane.setMinimumSize(preferredSize);
                desktopPane.setPreferredSize(preferredSize);
            }
        }

        //======== jToolBar2 ========
        {
            jToolBar2.setRollover(true);
            jToolBar2.setMargin(new Insets(5, 5, 5, 5));

            //---- jLabel1 ----
            jLabel1.setIcon(new ImageIcon("C:\\Users\\YOGA 510\\Pictures\\Sici\\lockkey.png"));
            jLabel1.setBorder(new EtchedBorder());
            jLabel1.setPreferredSize(new Dimension(300, 404));
            jLabel1.setMaximumSize(new Dimension(300, 404));
            jLabel1.setMinimumSize(new Dimension(300, 404));
            jToolBar2.add(jLabel1);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(jToolBar2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(desktopPane, GroupLayout.PREFERRED_SIZE, 491, GroupLayout.PREFERRED_SIZE)))
                    .addGap(321, 321, 321))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jToolBar2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(desktopPane, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
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
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu editMenu;
    private JMenuItem cutMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem pasteMenuItem;
    private JMenuItem deleteMenuItem;
    private JMenu helpMenu;
    private JMenuItem contentMenuItem;
    private JMenuItem aboutMenuItem;
    private JDesktopPane desktopPane;
    private JToolBar jToolBar2;
    private JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
