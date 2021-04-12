package util;

import database.Product;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class DataModel extends AbstractTableModel
{
    public Vector<Product> values;
    private String[] columnNames = {"ID", "Название", "Цена", "Описание", "Изображение", "Активен?", "Производитель"};

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return values.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        try {
            if(columnIndex == 6)
                ++columnIndex;
            return Product.class.getDeclaredFields()[columnIndex].get(values.elementAt(rowIndex));
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
