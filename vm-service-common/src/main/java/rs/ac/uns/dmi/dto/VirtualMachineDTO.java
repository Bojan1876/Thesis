package rs.ac.uns.dmi.dto;

import java.io.Serializable;

/**
 * Create Virtual Machine with id, name, ram, ipAddress and status
 * 
 * @author Bojan
 *
 */

public class VirtualMachineDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -313383326552036714L;
	private Long id;
	private String name;
	private Long ram;
	private String ipAddress;
	private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getRam() {
		return ram;
	}

	public void setRam(Long ram) {
		this.ram = ram;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
