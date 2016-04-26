package rs.ac.uns.dmi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.dmi.model.DataBaseModel;
import rs.ac.uns.dmi.repository.DataBaseRepository;

/**
 * Implemented DataBase Service
 * 
 * @author bojanm
 *
 */
@Service
public class DataBaseServiceImpl implements DataBaseService { // ovaj metod
																// implementira
																// DataBaseService
	@Autowired
	DataBaseRepository dataBaseRepository;

	public DataBaseModel findOne(Long id) {
		return dataBaseRepository.findOne(id);
	}

	public List<DataBaseModel> findAll() {
		return dataBaseRepository.findAll();
	}

	public DataBaseModel save(DataBaseModel dataBaseModel) {
		return dataBaseRepository.save(dataBaseModel);
	}

	@Override
	public void delete(Long id) {
		dataBaseRepository.delete(id);
	}

	public void update(DataBaseModel dataBaseModel) {
		dataBaseRepository.save(dataBaseModel);
	}

}
