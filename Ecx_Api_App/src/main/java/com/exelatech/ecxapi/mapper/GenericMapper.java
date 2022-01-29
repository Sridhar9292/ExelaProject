package com.exelatech.ecxapi.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface GenericMapper<T, PK extends Serializable> {
    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> getAll();

    List<T> getAll(@Param("filter") List<String> filter);

    /**
     * Gets all records without duplicates.
     * <p>Note that if you use this method, it is imperative that your model
     * classes correctly implement the hashcode/equals methods</p>
     * @return List of populated objects
     */
    List<T> getAllDistinct();

    /**
     * Gets all records that match a search term. "*" will get them all.
     * @param searchTerm the term to search for
     * @return the matching records
     * @throws SearchException
     */
    List<T> search(@Param("searchTerm") String searchTerm) throws SearchException;

    List<T> search(@Param("searchTerm") String searchTerm, @Param("filter") List<String> filter) throws SearchException;
    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    T get(PK id, @Param("filter") List<String> filter);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    T exists(PK id);

    /**
     * Generic method to insert an object
     * @param object the object to insert
     * @return number of records inserted
     */
    int insert(T object);

    /**
     * Generic method to update an object
     * @param object the object to save
     * @return the persisted object
     */
    int update(T object);

    /**
     * Generic method to delete an object
     * @param object the object to remove
     */
    int remove(T object);

    /**
     * Generic method to delete an object
     * @param id the identifier (primary key) of the object to remove
     */
    int remove(PK id);

    /**
     * Find a list of records by using a named query
     * @param queryName query name of the named query
     * @param queryParams a map of the query names and the values
     * @return a list of the records found
     */
    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

    /**
     * Generic method to regenerate full text index of the persistent class T
     */
    void reindex();

    /**
     * Generic method to regenerate full text index of all indexed classes
     * @param async true to perform the reindexing asynchronously
     */
    void reindexAll(boolean async);
}
