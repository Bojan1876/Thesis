package rs.ac.uns.dmi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.dmi.converter.DataBaseModelConverter;
import rs.ac.uns.dmi.converter.VirtualMachineDTOConverter;
import rs.ac.uns.dmi.dto.VirtualMachineDTO;
import rs.ac.uns.dmi.service.DataBaseService;

@RestController
@RequestMapping("/database")
public class DataBaseController {

	@Autowired
	DataBaseService databaseservice;

	@Autowired
	DataBaseModelConverter dataBaseModelConverter;

	@Autowired
	VirtualMachineDTOConverter virtualMachineDTOConverter;

	/**
	 * Maps all models from the model database
	 * 
	 * @return all found database service
	 */
	// ovde vracam podatke iz baze
	// stream je isto kolekcija sa DataBase modelom koja pruza dodatne
	// funkcinalnosti
	// za razliku od liste. Koristi se u Java8
	// Stream- Pozivam funkciju map koja svaki element kolekcije (dbModel)
	// konvertuje u virtualMachineDTOConverter
	// nakon mapiranja elemente stream-a sakupljamo u listu
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<VirtualMachineDTO> getAll() {
		return databaseservice.findAll().stream().map(dbModel -> virtualMachineDTOConverter.convert(dbModel))
				.collect(Collectors.toList());
	}

	/**
	 * Maps all models with id
	 * 
	 * @param id
	 * @return model with this id
	 */

	// ovde uzimam podatke sa url-a, iz {id}
	// poziv funckije findOne vraca databasemodel i onda
	// VirtualMachineDTOConverter
	// konvertuje DataBaseModel u VirtualMachineDTO
	@RequestMapping("{id}")
	public VirtualMachineDTO getDataBaseModel(@PathVariable("id") Long id) {
		return virtualMachineDTOConverter.convert(databaseservice.findOne(id));
	}

	/**
	 * Used to set parameters on the localhost from the DataBaseTransfer Object
	 * and stored
	 * 
	 * @param dataBaseTransferObject
	 * @return
	 */

	@RequestMapping(method = RequestMethod.POST)
	public VirtualMachineDTO createModel(@RequestBody VirtualMachineDTO virtualMachineDTO) {
		return virtualMachineDTOConverter
				.convert(databaseservice.save(dataBaseModelConverter.convert(virtualMachineDTO)));
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		databaseservice.delete(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public void update(@RequestBody VirtualMachineDTO virtualMachineDTO) {
		databaseservice.update(dataBaseModelConverter.convert(virtualMachineDTO));
	}
}
