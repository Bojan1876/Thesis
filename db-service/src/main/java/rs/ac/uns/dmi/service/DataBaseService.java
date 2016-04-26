package rs.ac.uns.dmi.service;

import java.util.List;

import rs.ac.uns.dmi.model.DataBaseModel;

/**
 * Interface service that allows retrieval by Id-in, finding and keeping all the
 * databasemodel
 * 
 * @author bojanm
 *
 */
public interface DataBaseService {

	/**
	 * Returns {@link DataBaseModel}
	 * 
	 * @param id
	 * @return
	 */
	DataBaseModel findOne(Long id);

	List<DataBaseModel> findAll();

	DataBaseModel save(DataBaseModel dataBaseModel);

	void delete(Long id);

	void update(DataBaseModel dataBaseModel);
}
