package util;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class CustomModel<T> extends AbstractTableModel
{
    public Class<T> cls;
    public Vector<T> values;

    public CustomModel(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public int getRowCount() {
        return values.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int column) {
        return cls.getDeclaredFields()[column].getName();
    }

    //@Override
    //public Class<?> getColumnClass(int columnIndex) {
    //    return super.getColumnClass(columnIndex);
    //}

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            return cls.getDeclaredFields()[columnIndex].get(values.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
