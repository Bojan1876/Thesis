package rs.ac.uns.dmi.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.ac.uns.dmi.dto.VirtualMachineDTO;
import rs.ac.uns.dmi.service.VMServiceClient;

/**
 * Client implementation for executing REST calls to external micro-servicess.
 * 
 * @author Bojan
 *
 */
@Service("vmService")
public class VMServiceClientImpl implements VMServiceClient {

	private RestTemplate resttemplate = new RestTemplate();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VirtualMachineDTO createVM(VirtualMachineDTO virtualMachineDTO) {
		HttpEntity<VirtualMachineDTO> request = new HttpEntity<>(virtualMachineDTO);
		return resttemplate.postForObject("http://localhost:80/test-vm/virtualmachnine", request,
				VirtualMachineDTO.class);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveVM(VirtualMachineDTO virtualMachineDTO) {
		HttpEntity<VirtualMachineDTO> request = new HttpEntity<>(virtualMachineDTO);
		resttemplate.postForObject("http://localhost:85/test-db/database", request, VirtualMachineDTO.class);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<VirtualMachineDTO> reviewVM() {
		// inner class- new ParameterizedTypeReference, koristi taj json sto
		// dobijes da pretvoris u VirtualMachineDTO. OVo je radjeno zato sto je
		// pre ovog stanja bilo da je dovuceni json convertovao u listu, ali je
		// falilo tipiziranje, podaci od tipa su bili izgubljeni (type erasure),
		// tako da je lista mapa, umesto liste VirtualMAchineDTO
		// uvodjenjem typereference-a tip liste se sacuvava, tako da ne bude
		// lista mapa, vec ono sto nama treba, lista VirtualMachineDTO
		// tako da kada radim stream u kontroleru nece bacati excaption
		return resttemplate.exchange("http://localhost:85/test-db/database/all", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<VirtualMachineDTO>>() {
				}).getBody();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteVM(Long id) {
		resttemplate.delete("http://localhost:85/test-db/database/" + id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateVM(VirtualMachineDTO virtualMachineDTO) {
		HttpEntity<VirtualMachineDTO> request = new HttpEntity<>(virtualMachineDTO);
		resttemplate.put("http://localhost:85/test-db/database/" + virtualMachineDTO.getId(), request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VirtualMachineDTO getVM(Long id) {
		return resttemplate.getForObject("http://localhost:85/test-db/database/" + id, VirtualMachineDTO.class);
	}
}
