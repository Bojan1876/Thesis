package rs.ac.uns.dmi.service;

import java.util.List;

import rs.ac.uns.dmi.dto.VirtualMachineDTO;

public interface VMServiceClient {

	/**
	 * Create virtual machine provided data by calling vm-service.
	 */
	VirtualMachineDTO createVM(VirtualMachineDTO virtualMachineDTO);

	/**
	 * Save virtual machine with provided data in db-service
	 */
	void saveVM(VirtualMachineDTO virtualMachineDTO);

	/**
	 * Returns List of all {@link VirtualMachineDTO}s from db-service.
	 */
	List<VirtualMachineDTO> reviewVM();

	/**
	 * Deletes virtual machine for provided id in db-service.
	 */
	void deleteVM(Long id);

	/**
	 * Update virtual machine with provided data in db-service.
	 */
	void updateVM(VirtualMachineDTO virtualMachineDTO);

	/**
	 * Get one Virtual Machine by id in db-service.
	 */
	VirtualMachineDTO getVM(Long id);

}
