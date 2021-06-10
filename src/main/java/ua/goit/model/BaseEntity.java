package ua.goit.model;

public interface BaseEntity <ID> {
    ID getId();
    void setId(ID id);
}
