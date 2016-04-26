package rs.ac.uns.dmi.cotroller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import rs.ac.uns.dmi.dto.Status;
import rs.ac.uns.dmi.dto.VirtualMachineDTO;
import rs.ac.uns.dmi.service.VMServiceClient;

@Controller
public class ActivitiController {
	// runtime mi sluzi za povezivanje sa activiti dijagramom, za startovanje
	// activiti procesa
	@Autowired
	private RuntimeService runtimeService; // ovo mi je za proces bazu u
											// aktivitiju

	@Autowired
	private TaskService taskService; // za task bazu u aktivituju

	@Autowired
	private VMServiceClient vmServiceClient; // za pozive ka drugim
												// mikroservisima

	// Sluzi mi kada biram akciju
	@RequestMapping(value = "/startmanu", method = RequestMethod.POST)
	public String vmservice(@RequestParam String processKey, @RequestParam String action,
			VirtualMachineDTO virtualMachineDTO, Model model) {
		System.out.println("In start menu");

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("CreateVM")
				.variableValueLike("processKey", processKey).singleResult();
		runtimeService.setVariable(processInstance.getId(), "action", action);

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
		taskService.complete(task.getId());

		Task nextTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active()
				.singleResult();
		model.addAttribute("processKey", processKey);
		if ("CreateVM".equals(nextTask.getName())) {
			return "createVirtualMachine";
		} else if ("DeleteVM".equals(nextTask.getName())) {
			model.addAttribute("virtualMachines", vmServiceClient.reviewVM());
			return "deleteVirtualMachine";
		} else if ("UpdateVM".equals(nextTask.getName())) {
			model.addAttribute("virtualMachines", vmServiceClient.reviewVM());
			return "updateVirtualMachine";
		}
		return "";
	}

	// serviraj mi start menu stranicu i zapocni proces. Ovo se prvo desava
	@RequestMapping(value = "/startmanu", method = RequestMethod.GET)
	public String getStartManu(Model model) {
		Map<String, Object> props = new HashMap<>();
		String processKey = UUID.randomUUID().toString();
		props.put("processKey", processKey);

		runtimeService.startProcessInstanceByKey("CreateVM", props);
		model.addAttribute("processKey", processKey);

		return "startManu";
	}

	@RequestMapping(value = "/createVirtualMachine", method = RequestMethod.POST)
	public String createVM(@RequestParam String processKey, VirtualMachineDTO virtualMachineDTO) {
		System.out.println("In create menu");

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("CreateVM")
				.variableValueLike("processKey", processKey).singleResult();
		runtimeService.setVariable(processInstance.getId(), "virtualMachineDTO",
				vmServiceClient.createVM(virtualMachineDTO));

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
		taskService.complete(task.getId());
		return "createSuccess";
	}

	@RequestMapping(value = "/deleteVirtualMachine", method = RequestMethod.POST)
	public String deleteVM(@RequestParam String processKey, @RequestParam Long id) {
		System.out.println("In delete menu");
		vmServiceClient.deleteVM(id);

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("CreateVM")
				.variableValueLike("processKey", processKey).singleResult();

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
		taskService.complete(task.getId());
		return "deleteSuccess";
	}

	// kada kliknem na emial, ova stranica se prikaze
	@RequestMapping(value = "/reviewVirtualMachine", method = RequestMethod.GET)
	public String reviewVM(@RequestParam String processKey, Model model) {

		// processKey ulazni parametar za pretrazivanje activiti procesa.
		// ProcessInstance predstavlja podatke definisane u activiti dijagramu i
		// njegovo stanje
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("CreateVM")
				.variableValueLike("processKey", processKey).singleResult();

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();

		if (task != null) {
			model.addAttribute("virtualMachines", vmServiceClient.reviewVM());
			taskService.complete(task.getId());
			return "reviewVirtualMachine";
		}
		return "errorPage";
	}

	@RequestMapping(value = "/updateVirtualMachine", method = RequestMethod.POST)
	public String updateVM(@RequestParam String processKey, @RequestParam Long id, Model model) {
		System.out.println("In update menu");
		// vmServiceClient.updateVM(virtualMachineDTO); precice u change
		// ovo povezuje html stranicu i podatke iz modela
		model.addAttribute("virtualMachineDTO", vmServiceClient.getVM(id));
		model.addAttribute("processKey", processKey);
		model.addAttribute("allStatuses", Status.VALUES);
		// pretrazujem aktiviti bazu po ovom processKeyu
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("CreateVM")
				.variableValueLike("processKey", processKey).singleResult();

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
		taskService.complete(task.getId());
		return "changeVirtualMachine";
	}

	@RequestMapping(value = "/changeVirtualMachine", method = RequestMethod.POST)
	public String changeVM(@RequestParam String processKey, VirtualMachineDTO virtualMachineDTO) {
		System.out.println("In change menu");

		vmServiceClient.updateVM(virtualMachineDTO);
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("CreateVM")
				.variableValueLike("processKey", processKey).singleResult();

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
		taskService.complete(task.getId());
		return "updateSuccess";
	}

	// Kreiraj sve ON i OFF Virtual Machine

	@RequestMapping(value = "/reviewVirtualMachine", method = RequestMethod.POST)
	public String reviewStatus(Model model, @RequestParam String action) {
		System.out.println("In review menu");
		// valueOf- pravi enum od stringa
		//filtriram u slucaju da je ALL, ako nije ALL onda je Status jednako necemu, ustvari poredim akciju koja je stigla sa forme i poredim vrednost Statusa u samoj virtuelnoj masini koja se trenutno filtrira
		model.addAttribute("virtualMachines",
				vmServiceClient.reviewVM().stream()
						.filter(vm -> "ALL".equals(action) ? true : Status.valueOf(action).equals(vm.getStatus()))
						.collect(Collectors.toList()));

		return "reviewVirtualMachine";
	}

}
