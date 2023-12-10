package app.interfaces;

public interface DBActions<T> {
    public void insert();
    public void delete();
    public void update(T newObj);
}