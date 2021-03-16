package ui;

import com.sun.tools.javac.Main;
import database.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddForm extends JFrame
{
    private JTextField titleField;
    private JTextField costField;
    private JTextField descriptionField;
    private JTextField pathField;
    private JTextField manufacturerIDField;
    private JRadioButton yesRadioButton;
    private JRadioButton notRadioButton;
    private JButton addButton;
    private JLabel statusLabel;
    private JPanel mainPanel;
    private JLabel typeField;
    private MainForm mainForm;
    private boolean isEdit;
    private Product product;

    public void open()
    {
        product = new Product();
        statusLabel.setText("");
        titleField.setText("");
        costField.setText("");
        descriptionField.setText("");
        pathField.setText("");
        manufacturerIDField.setText("");
        isEdit = false;
        addButton.setText("Добавить");
        typeField.setText("Добавление продукта");
        setVisible(true);
    }

    public void edit(Product product)
    {
        this.product = new Product();
        this.product.ID = product.ID;
        statusLabel.setText("");
        titleField.setText(product.title);
        costField.setText(Double.toString(product.cost));
        descriptionField.setText(product.description);
        pathField.setText(product.mainImagePath);
        manufacturerIDField.setText(Integer.toString(product.manufacturerID));
        if(product.isActive)
            yesRadioButton.setSelected(true);
        else notRadioButton.setSelected(true);
        isEdit = true;
        addButton.setText("Сохранить");
        typeField.setText("Редактирование продукта");
        setVisible(true);
    }

    public AddForm(MainForm mainForm)
    {
        this.mainForm = mainForm;
        setContentPane(mainPanel);

        setTitle("Добавление продукта");
        setMinimumSize(new Dimension(500, 500));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-250, Toolkit.getDefaultToolkit().getScreenSize().height/2-250);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(yesRadioButton);
        buttonGroup.add(notRadioButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(titleField.getText().length() > 50)
                {
                    statusLabel.setText("Ошибка: слишком длинное название");
                    return;
                }
                else product.setTitle(titleField.getText());
                try {
                    product.setCost(Double.parseDouble(costField.getText()));
                    product.setManufacturerID(Integer.parseInt(manufacturerIDField.getText()));
                } catch (Exception exception) {
                    statusLabel.setText("Ошибка формата");
                    return;
                }
                product.setDescription(descriptionField.getText());
                product.setActive(yesRadioButton.isSelected());
                product.setMainImagePath(pathField.getText());
                try {
                    if(isEdit)
                        mainForm.productManager.update(product);
                    else mainForm.productManager.add(product);
                    setVisible(false);
                    mainForm.reloadTable();

                } catch (SQLException throwables) {
                    statusLabel.setText("Ошибка подключения");
                }
            }
        });

        setVisible(false);
    }
}
