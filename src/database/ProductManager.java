package database;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ProductManager
{
    MysqlDataSource source;

    public ProductManager(MysqlDataSource source)
    {
        this.source = source;
    }

    public void add(Product product)
    {
        try
        {
            try(Connection connection = source.getConnection())
            {
                String sql = "INSERT INTO Product (Title, Cost, Description, MainImagePath, IsActive, ManufacturerID) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, product.title);
                preparedStatement.setDouble(2, product.cost);
                preparedStatement.setString(3, product.description);
                preparedStatement.setString(4, product.mainImagePath);
                preparedStatement.setBoolean(5, product.isActive);
                preparedStatement.setInt(6, product.manufacturerId);
                System.out.println(preparedStatement.toString());
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL connection error!");
        }
    }

    public void update(Product product)
    {
        try
        {
            try(Connection connection = source.getConnection())
            {
                String sql = "UPDATE Product SET Title=?, Cost=?, Description=?, MainImagePath=?, IsActive=?, ManufacturerID=? WHERE ID=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, product.title);
                preparedStatement.setDouble(2, product.cost);
                preparedStatement.setString(3, product.description);
                preparedStatement.setString(4, product.mainImagePath);
                preparedStatement.setBoolean(5, product.isActive);
                preparedStatement.setInt(6, product.manufacturerId);
                preparedStatement.setInt(7, product.id);
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL connection error!");
        }
    }

    public void delete(Product product)
    {
        try
        {
            try(Connection connection = source.getConnection())
            {
                String sql = "DELETE FROM Product WHERE Id=?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, product.id);
                System.out.println(preparedStatement.toString());
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL connection error!");
        }
    }

    public Vector<Product> getAll()
    {
        try
        {
            try(Connection connection = source.getConnection())
            {
                String sql = "select * from product, manufacturer WHERE Product.ManufacturerID = Manufacturer.ID;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet result = preparedStatement.executeQuery();
                Vector<Product> products = new Vector<Product>();
                while(result.next())
                {
                    Product product = new Product();
                    product.id = result.getInt(1);
                    product.title = result.getString(2);
                    product.cost = result.getInt(3);
                    product.description = result.getString(4);
                    product.mainImagePath = result.getString(5);
                    product.isActive = result.getBoolean(6);
                    product.manufacturerId = result.getInt(7);
                    product.manufacturer = result.getString(9);
                    products.add(product);
                }
                return products;
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL connection error!");
        }
        return null;
    }
}
