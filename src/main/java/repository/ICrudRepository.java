package repository;

import java.util.List;

public interface ICrudRepository<ID, E>
{
    /**
     * adds an element from type E to the repository
     * @param e the element that should be added
     * @throws //TODO sau deloc
     */
    void add(E e); // add an element from type E

    /**
     * removed an element from the repository
     * @param id the id of the element to be removed
     * @throws //TODO de sus
     */
    void delete(ID id); // delete an element by its id

    /**
     * updates an existing element of the repository to another one given as a parameter
     * @param id the id of the element that should be changed
     * @param e the element that should replace the old existing element
     * @throws //TODO de sus
     */
    void update(ID id, E e); // change the element with the given id to the given element

    /**
     * finds an element from the repository by the id
     * @param id the id of the element that should be found
     * @return the element, if it is found. null otherwise
     */
    E findId(ID id); // finds the element with the given id

    /**
     * returns the whole repository as a list
     * @return a list of all elements in the repository
     */
    List<E> findAll(); // returns entire list

}
