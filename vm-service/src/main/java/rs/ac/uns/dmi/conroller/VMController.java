package rs.ac.uns.dmi.conroller;

import java.util.Random;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.dmi.dto.Status;
import rs.ac.uns.dmi.dto.VirtualMachineDTO;

@RestController
public class VMController {

	/**
	 * Create random IpAddress and also create random Virtual Machine status
	 * (ON/OFF)
	 * 
	 * @param virtualMchineDTO
	 * @return
	 */

	@RequestMapping(value = "/virtualmachnine", method = RequestMethod.POST)
	public VirtualMachineDTO vmservice(@RequestBody VirtualMachineDTO virtualMchineDTO) {
		virtualMchineDTO.setIpAddress(new Random().nextInt(255) + "." + new Random().nextInt(255) + "."
				+ new Random().nextInt(255) + "." + new Random().nextInt(255));
		virtualMchineDTO.setStatus(Status.generatedRandom());
		return virtualMchineDTO;
	}

}
