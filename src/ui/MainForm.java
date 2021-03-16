package ui;

import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;
import database.MysqlDatabase;
import database.Product;
import database.ProductManager;
import util.CustomModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;

public class MainForm extends JFrame
{
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;
    private JButton idSortButton;
    private JButton titleSortButton;
    private JButton costSortButton;
    private JButton editButton;
    public MysqlDatabase database = new MysqlDatabase("127.0.0.1", 3306, "demoexam", "root", "root");
    private CustomModel<Product> model = new CustomModel<Product>(Product.class);
    public ProductManager productManager = new ProductManager(database);
    private AddForm addForm = new AddForm(this);
    private boolean idSorted, titleSorted, costSorted;

    public void reloadTable() {
        try {
            model.values = productManager.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        table.setModel(model);
        idSorted = true;
        model.fireTableDataChanged();
    }

    public MainForm()
    {
        setContentPane(mainPanel);

        setTitle("Салон красоты 'Шарм'");
        setMinimumSize(new Dimension(750, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-325, Toolkit.getDefaultToolkit().getScreenSize().height/2-250);

        reloadTable();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addForm.open();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1)
                {
                    addForm.edit(model.values.get(table.getSelectedRow()));
                }
            }
        });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(table.getSelectedRow() != -1 && e.getKeyCode() == KeyEvent.VK_DELETE)
                {
                    Product product = new Product();
                    product.setID(model.values.get(table.getSelectedRow()).ID);
                    productManager.delete(product);
                    model.values.remove(table.getSelectedRow());
                    model.fireTableDataChanged();
                }
            }
        });

        idSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!idSorted)
                    Collections.sort(model.values, (o1, o2) -> Integer.compare(o1.ID, o2.ID));
                else Collections.sort(model.values, (o1, o2) -> Integer.compare(o2.ID, o1.ID));
                idSorted = !idSorted;
                titleSorted = false;
                costSorted = false;
                model.fireTableDataChanged();
            }
        });

        titleSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!titleSorted)
                    Collections.sort(model.values, (o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.title, o2.title));
                else Collections.sort(model.values, (o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o2.title, o1.title));
                titleSorted = !titleSorted;
                idSorted = false;
                costSorted = false;
                model.fireTableDataChanged();
            }
        });

        costSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!costSorted)
                    Collections.sort(model.values, (o1, o2) -> Double.compare(o1.cost, o2.cost));
                else Collections.sort(model.values, (o1, o2) -> Double.compare(o2.cost, o1.cost));
                costSorted = !costSorted;
                idSorted = false;
                titleSorted = false;
                model.fireTableDataChanged();
            }
        });

        setVisible(true);
    }
}
