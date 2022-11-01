package repository;

public interface ICrudRepository<ID, E>
{
    void add(E e); // add an element from type E
    void delete(ID id); // delete an element by its id
    void update(ID id, E e); // change the element with the given id to the given element
    E findId(ID id); // finds the element with the given id
    //return all
}
