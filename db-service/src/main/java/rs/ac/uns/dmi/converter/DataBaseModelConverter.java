package rs.ac.uns.dmi.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import rs.ac.uns.dmi.dto.VirtualMachineDTO;
import rs.ac.uns.dmi.model.DataBaseModel;

/**
 * Convert {@link VirtualMachineDTO} to {@link DataBaseModel}
 * 
 * @author bojanm
 *
 */

@Component
public class DataBaseModelConverter implements Converter<VirtualMachineDTO, DataBaseModel> {

	@Override
	public DataBaseModel convert(VirtualMachineDTO source) {
		DataBaseModel dataBaseModel = new DataBaseModel();
		dataBaseModel.setId(source.getId());
		dataBaseModel.setName(source.getName());
		dataBaseModel.setRam(source.getRam());
		dataBaseModel.setIpAddress(source.getIpAddress());
		dataBaseModel.setStatus(source.getStatus());
		return dataBaseModel;
	}

}
