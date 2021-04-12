package ui;

import database.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddForm extends JFrame
{
    private JTextField titleField;
    private JTextField manufacturerIdField;
    private JTextField costField;
    private JTextField descriptionField;
    private JTextField pathField;
    private JRadioButton yesRadio;
    private JRadioButton notRadio;
    private JLabel statusLabel;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel mainPanel;

    private MainForm mainForm;
    private Product product;
    private boolean edit;

    public void openAdd()
    {
        edit = false;
        statusLabel.setText("Добавление продукта");
        product = new Product();
        titleField.setText("");
        costField.setText("");
        descriptionField.setText("");
        pathField.setText("");
        yesRadio.setSelected(true);
        manufacturerIdField.setText("");
        okButton.setText("Добавить");
        setVisible(true);
    }

    public void openEdit(Product product)
    {
        edit = true;
        statusLabel.setText("Редактирование продукта");
        this.product = product;
        titleField.setText(product.title);
        costField.setText(String.valueOf(product.cost));
        descriptionField.setText(product.description);
        pathField.setText(product.mainImagePath);
        yesRadio.setSelected(product.isActive);
        notRadio.setSelected(!product.isActive);
        manufacturerIdField.setText(String.valueOf(product.manufacturerId));
        okButton.setText("Сохранить");
        setVisible(true);
    }

    public AddForm(MainForm mainForm)
    {
        this.mainForm = mainForm;
        setContentPane(mainPanel);

        setTitle("Салон красоты 'Шарм'");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(400, 400));
        setLocation(screenSize.width/2-200, screenSize.height/2-200);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(yesRadio);
        buttonGroup.add(notRadio);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(titleField.getText().length() > 50)
                {
                    statusLabel.setText("Длина названия не может быть больше 50 символов");
                    return;
                }
                product.title = titleField.getText();
                product.cost = Double.parseDouble(costField.getText());
                product.description = descriptionField.getText();
                product.mainImagePath = pathField.getText();
                product.isActive = yesRadio.isSelected();
                product.manufacturerId = Integer.parseInt(manufacturerIdField.getText());
                if(edit)
                    mainForm.productManager.update(product);
                else mainForm.productManager.add(product);
                mainForm.reloadTable();
                setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setVisible(false);
    }
}
