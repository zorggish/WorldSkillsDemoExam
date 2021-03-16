package database;

public class Product
{
    public int ID;
    public String title;
    public double cost;
    public String description;
    public String mainImagePath;
    public boolean isActive;
    public int manufacturerID;

    public Product()
    {
        this.ID = -1;
    }

    public Product(int ID, String title, double cost, String description, String mainImagePath, boolean isActive, int manufacturerID) {
        this.ID = ID;
        this.title = title;
        this.cost = cost;
        this.description = description;
        this.mainImagePath = mainImagePath;
        this.isActive = isActive;
        this.manufacturerID = manufacturerID;
    }

    public Product(String title, double cost, String description, String mainImagePath, boolean isActive, int manufacturerID) {
        this(-1, title, cost, description, mainImagePath, isActive, manufacturerID);
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", mainImagePath='" + mainImagePath + '\'' +
                ", isActive=" + isActive +
                ", manufacturerID=" + manufacturerID +
                '}';
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMainImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }
}
