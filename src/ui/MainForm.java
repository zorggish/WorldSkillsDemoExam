package ui;

import com.mysql.cj.jdbc.MysqlDataSource;
import database.Product;
import database.ProductManager;
import util.DataModel;

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class MainForm extends JFrame
{
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton idSortButton;
    private JButton costSortButton;
    private JButton titleSortButton;
    private JComboBox comboBox;
    private JLabel countLabel;

    public MysqlDataSource dataSource = new MysqlDataSource();
    public DataModel model = new DataModel();
    public ProductManager productManager;
    public AddForm addForm = new AddForm(this);
    public boolean idSorted, titleSorted, costSorted;

    public void reloadTable()
    {
        model.values = productManager.getAll();
        model.fireTableDataChanged();
        countLabel.setText("  " + model.values.size() + " / " + model.values.size() + "  ");
        idSorted = true;
        titleSorted = false;
        costSorted = false;
    }

    public MainForm()
    {
        setContentPane(mainPanel);

        setTitle("Салон красоты 'Шарм'");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(600, 600));
        setLocation(screenSize.width/2-300, screenSize.height/2-300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dataSource.setServerName("127.0.0.1");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("demoexam");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        productManager = new ProductManager(dataSource);
        table.setModel(model);
        reloadTable();

        comboBox.addItem("Все");
        Set<String> manufacturers = new HashSet<String>();
        for(Product product : model.values)
            manufacturers.add(product.manufacturer);
        for(String manufacturer : manufacturers)
            comboBox.addItem(manufacturer);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addForm.openAdd();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1)
                    addForm.openEdit(model.values.get(table.getSelectedRow()));
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1)
                    productManager.delete(model.values.get(table.getSelectedRow()));
                reloadTable();
            }
        });

        idSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(model.values, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        if(idSorted)
                            return Integer.compare(o2.id, o1.id);
                        else return Integer.compare(o1.id, o2.id);
                    }
                });
                idSorted = !idSorted;
                titleSorted = false;
                costSorted = false;
                model.fireTableDataChanged();
            }
        });

        titleSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(model.values, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        if(titleSorted)
                            return String.CASE_INSENSITIVE_ORDER.compare(o2.title, o1.title);
                        else return String.CASE_INSENSITIVE_ORDER.compare(o1.title, o2.title);
                    }
                });
                idSorted = false;
                titleSorted = !titleSorted;
                costSorted = false;
                model.fireTableDataChanged();
            }
        });

        costSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(model.values, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        if(costSorted)
                            return Double.compare(o2.cost, o1.cost);
                        else return Double.compare(o1.cost, o2.cost);
                    }
                });
                idSorted = false;
                titleSorted = false;
                costSorted = !costSorted;
                model.fireTableDataChanged();
            }
        });

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    reloadTable();
                    if(comboBox.getSelectedIndex() != 0)
                    {
                        int totalSize = model.values.size();
                        System.out.println(comboBox.getSelectedItem().toString());
                        model.values.removeIf(product -> !product.manufacturer.equals(comboBox.getSelectedItem().toString()));
                        model.fireTableDataChanged();
                        countLabel.setText("  " + model.values.size() + " / " + totalSize + "  ");
                    }
                }
            }
        });

        setVisible(true);
    }
}
