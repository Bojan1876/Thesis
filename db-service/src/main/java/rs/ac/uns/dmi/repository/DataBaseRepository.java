package rs.ac.uns.dmi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.dmi.model.DataBaseModel;

/**
 * Interface that has been expanded from JpaRepository
 * 
 * @author bojanm
 *
 */
@Repository
public interface DataBaseRepository extends JpaRepository<DataBaseModel, Long> {

}