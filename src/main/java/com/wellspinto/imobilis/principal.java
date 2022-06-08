package com.wellspinto.imobilis;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import javax.swing.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatTextField;
import com.formdev.flatlaf.icons.FlatSearchWithHistoryIcon;
import com.wellspinto.funcoes.Globais;
import com.wellspinto.funcoes.PrinterLister;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.print.PrintService;
import javax.swing.Box;
import javax.swing.Icon;
import net.miginfocom.swing.*;

/**
 *
 * @author YOGA 510
 */
public class principal extends javax.swing.JFrame {
    private int searchBarWidth = 300;
    private int searchBarHeigth = 30;
    private Color searchBarFocusColor = new Color(60,98,140);
    private JScrollPane searcBox = new JScrollPane();
    private boolean searchBoxCreated = false;
    private JPopupMenu popupMenu = new JPopupMenu();
    private ButtonGroup bg = new ButtonGroup();
    private FlatTextField searchBar = new FlatTextField();
    
    /**
     * Creates new form principal
     */
    public principal() {
        initComponents();

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
        
        FlatSVGIcon laserIconPrinter = new FlatSVGIcon("icons/laserPrinterOff.svg");
        JToggleButton laserPrinter = new JToggleButton((Icon)laserIconPrinter);
        laserPrinter.setToolTipText("Impressora Laser esta Desativada.");
        laserPrinter.addActionListener((e) -> {
            if (laserPrinter.isSelected()) {
                laserPrinter.setToolTipText("Impressora Laser esta Ativa.");
            } else {
                laserPrinter.setToolTipText("Impressora Laser esta Desativada.");
            }
        });
        
        FlatSVGIcon thermalIconPrinter = new FlatSVGIcon("icons/thermalPrinterDisabled.svg",16,16);
        JToggleButton thermalPrinter = new JToggleButton((Icon)thermalIconPrinter);
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
        emailButton.setIcon((Icon)new FlatSVGIcon("icons/users.svg"));
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
                searcBox.setVisible(searchBar.getText().trim().length() > 3);
                repaint();

            }

            @Override
            public void keyReleased(KeyEvent e) {
                searchBox();
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
        internalFrame2 = new JInternalFrame();
        internalFrame1 = new JInternalFrame();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Imobilis");
        setIconImage(new ImageIcon(getClass().getResource("/figuras/logo.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

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
            desktopPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            desktopPane.setLayout(new GridBagLayout());
            ((GridBagLayout)desktopPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)desktopPane.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)desktopPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)desktopPane.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //======== internalFrame2 ========
            {
                internalFrame2.setVisible(true);
                Container internalFrame2ContentPane = internalFrame2.getContentPane();
                internalFrame2ContentPane.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]"));
            }
            desktopPane.add(internalFrame2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //======== internalFrame1 ========
            {
                internalFrame1.setVisible(true);
                Container internalFrame1ContentPane = internalFrame1.getContentPane();
                internalFrame1ContentPane.setLayout(new GridBagLayout());
                ((GridBagLayout)internalFrame1ContentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)internalFrame1ContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)internalFrame1ContentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)internalFrame1ContentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
            }
            desktopPane.add(internalFrame1, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        contentPane.add(desktopPane);
        desktopPane.setBounds(0, -30, 635, 470);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
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
    private JInternalFrame internalFrame2;
    private JInternalFrame internalFrame1;
    // End of variables declaration//GEN-END:variables

}
