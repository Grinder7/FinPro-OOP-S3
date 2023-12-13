package app.interfaces;

public interface DBMethods<T> {
    public void insert();

    public void delete();

    public void update(T newObj);
}