package rs.ac.uns.dmi.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import rs.ac.uns.dmi.dto.VirtualMachineDTO;
import rs.ac.uns.dmi.model.DataBaseModel;

/**
 * Convert {@link DataBaseModel} to {@link VirtualMachineDTO}}
 * 
 * @author Bojan
 *
 */

@Component
public class VirtualMachineDTOConverter implements Converter<DataBaseModel, VirtualMachineDTO> {

	@Override
	public VirtualMachineDTO convert(DataBaseModel source) {
		VirtualMachineDTO virtualMachnieDTO = new VirtualMachineDTO();
		virtualMachnieDTO.setId(source.getId());
		virtualMachnieDTO.setName(source.getName());
		virtualMachnieDTO.setRam(source.getRam());
		virtualMachnieDTO.setIpAddress(source.getIpAddress());
		virtualMachnieDTO.setStatus(source.getStatus());
		return virtualMachnieDTO;
	}

}
