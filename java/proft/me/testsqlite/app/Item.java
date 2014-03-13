package proft.me.testsqlite.app;

public class Item {
    int id;
    String title;

    public Item() {}

    public Item(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Item(String title) {
        this.title = title;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
