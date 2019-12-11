
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Main extends JFrame {
    private static JTextField inputField;
    private static JButton fetchButton, saveAsTextButton, saveAsJSONButton;
    private static JLabel inputLabel;
    private static JTextArea resultsArea;
    private static JDialog textDialog;
    private static JDialog jsonDialog;
    private static JDialog aboutItemsDialog;
    public Main() {

        initUI();
    }

    private void initUI() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        createMenuBar();
        createSaveAsTextDialog();
        createSaveAsJSONFile();
        createAboutItemsDialog();

        inputLabel = new JLabel("Enter URL: ");
        inputField = new JTextField(35);

        fetchButton = new JButton("Fetch");
        fetchButton.setEnabled(false);

        saveAsTextButton = new JButton("Save to Text");
        saveAsTextButton.setEnabled(false);

        saveAsJSONButton = new JButton("Save to JSON");
        saveAsJSONButton.setEnabled(false);

        resultsArea = new JTextArea(25, 50);
        resultsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane sp = new JScrollPane(resultsArea);

        inputField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                enableFetchButton();
            }

            public void removeUpdate(DocumentEvent e) {
                enableFetchButton();
            }
            public void insertUpdate(DocumentEvent e) {
                enableFetchButton();
            }
        });

        resultsArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableSaveButtons();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableSaveButtons();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableSaveButtons();
            }
        });

        fetchButton.addActionListener(e -> {
            resultsArea.setText("");
            try {
                ArrayList<DataObject> arrayList = Scraper.scrapePage(inputField.getText());
                resultsArea.setText(new DataObject(Writer.writeToScreen(arrayList)).getCode());
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        });

        saveAsTextButton.addActionListener(e -> {
            textDialog.setVisible(true);
        });

        saveAsJSONButton.addActionListener(e -> {
            jsonDialog.setVisible(true);
        });

        JPanel top = new JPanel();
        top.setLayout(new FlowLayout());
        JPanel middle = new JPanel();
        middle.setLayout(new FlowLayout());
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());

        top.add(inputLabel);
        top.add(inputField);
        top.add(fetchButton);

        middle.add(sp);

        bottom.add(saveAsTextButton);
        bottom.add(saveAsJSONButton);

        getContentPane().add(top);
        getContentPane().add(middle);
        getContentPane().add(bottom);

        pack();
        setTitle("Web Scraper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void enableFetchButton() {
        if(inputField.getText().length() > 0) {
            fetchButton.setEnabled(true);
        }
        else {
            fetchButton.setEnabled(false);
        }
    }

    private void enableSaveButtons() {
        if (resultsArea.getText().length() > 0) {
            saveAsTextButton.setEnabled(true);
            saveAsJSONButton.setEnabled(true);
        } else {
            saveAsTextButton.setEnabled(false);
            saveAsJSONButton.setEnabled(false);
        }
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.setMnemonic(KeyEvent.VK_E);
        exitButton.setToolTipText("Exit application");
        exitButton.addActionListener((event) -> System.exit(0));

        JMenuItem aboutItemsButton = new JMenuItem("About Items");
        aboutItemsButton.setMnemonic(KeyEvent.VK_E);
        aboutItemsButton.setToolTipText("Info about the displayed Items");
        aboutItemsButton.addActionListener((event) -> {
            aboutItemsDialog.setVisible(true);
        });

        fileMenu.add(exitButton);
        helpMenu.add(aboutItemsButton);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void createSaveAsJSONFile() {
        jsonDialog = new JDialog(getOwner(), "Save As JSON File");

        JLabel l = new JLabel("Enter filename (*.json): ");
        JTextField textField = new JTextField(10);

        // Save as JSON button
        JButton saveButton = new JButton("Save as JSON");
        saveButton.addActionListener(e -> {
            Writer.writeToJson(textField.getText(), resultsArea.getText());
            jsonDialog.setVisible(false);
        });

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            jsonDialog.setVisible(false);
        });

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(l);
        p.add(textField);
        p.add(cancelButton);
        p.add(saveButton);

        jsonDialog.add(p);
        jsonDialog.setSize(200, 200);
        jsonDialog.setVisible(false);
    }

    private void createSaveAsTextDialog() {
        textDialog = new JDialog(getOwner(), "Save As Text File");

        JLabel l = new JLabel("Enter filename (*.txt): ");
        JTextField textField = new JTextField(10);

        // Save as text button
        JButton saveButton = new JButton("Save as Text");
        saveButton.addActionListener(e -> {
            Writer.writeToText(textField.getText(), resultsArea.getText());
            textDialog.setVisible(false);
        });

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            textDialog.setVisible(false);
        });

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(l);
        p.add(textField);
        p.add(cancelButton);
        p.add(saveButton);

        textDialog.add(p);
        textDialog.setSize(200, 200);
        textDialog.setVisible(false);
    }
    private void createAboutItemsDialog() {
        aboutItemsDialog = new JDialog(getOwner(), "About Items");

        JTextArea l = new JTextArea("The items being displayed on the screen \n"
                +"are the html and js of the webpage entered \n" +
                "in the url text field");
        l.setEditable(false);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            aboutItemsDialog.setVisible(false);
        });

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(l);
        p.add(closeButton);

        aboutItemsDialog.add(p);
        aboutItemsDialog.setSize(300, 150);
        aboutItemsDialog.setVisible(false);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            Main ex = new Main();
            ex.setVisible(true);
        });
    }
}