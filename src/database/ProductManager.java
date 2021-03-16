package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ProductManager
{
    MysqlDatabase db;

    public ProductManager(MysqlDatabase database)
    {
        db = database;
    }

    public int add(Product product) throws SQLException
    {
        try(Connection c = db.getConnection())
        {
            String sql = "INSERT INTO Product (Title, Cost, Description, MainImagePath, IsActive, ManufacturerID) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, product.title);
            ps.setDouble(2, product.cost);
            ps.setString(3, product.description);
            ps.setString(4, product.mainImagePath);
            ps.setInt(5, (product.isActive ? 1 : 0));
            ps.setInt(6, product.manufacturerID);
            System.out.println(ps.toString());
            return ps.executeUpdate();
        }
    }

    public int update(Product product) throws SQLException {
        try(Connection c = db.getConnection())
        {
            String sql = "UPDATE Product SET Title=?, Cost=?, Description=?, MainImagePath=?, IsActive=?, ManufacturerID=? WHERE ID=?;";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, product.title);
            ps.setDouble(2, product.cost);
            ps.setString(3, product.description);
            ps.setString(4, product.mainImagePath);
            ps.setInt(5, (product.isActive ? 1 : 0));
            ps.setInt(6, product.manufacturerID);
            ps.setInt(7, product.ID);
            return ps.executeUpdate();
        }
    }

    public Vector<Product> getAll() throws SQLException
    {
        try(Connection c = db.getConnection())
        {
            String sql = "SELECT * FROM Product;";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            Vector<Product> products = new Vector<Product>();
            while(result.next())
                products.add(new Product(result.getInt(1),
                                         result.getString(2),
                                         result.getDouble(3),
                                         result.getString(4),
                                         result.getString(5),
                                         result.getInt(6) == 1,
                                         result.getInt(7)
                ));
            return products;
        }
    }

    public int delete(Product p)
    {
        try(Connection c = db.getConnection())
        {
            String sql = "DELETE FROM Product WHERE ID=?;";
            PreparedStatement preparedStatement = c.prepareStatement(sql);
            preparedStatement.setInt(1, p.ID);
            return preparedStatement.executeUpdate();
        }
        catch (Exception e)
        { }
        return 0;
    }
}
