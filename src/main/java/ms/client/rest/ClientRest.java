package ms.client.rest;

import java.util.List;

import ms.client.dto.ClientDetailDto;
import ms.client.dto.KpiClientDto;
import ms.client.exception.InternalException;
import ms.client.model.Client;
import ms.client.service.IClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ClientRest {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IClientService clientService;

	/**
	 * Service returns client kpi based in ages (average and standard deviation)
	 * 
	 * @return
	 */
	@GetMapping("/kpiclientes")
	public ResponseEntity getKpiClient() {
		try {
			logger.info("[GET] START /kpiclientes");
			KpiClientDto response = clientService.getKpi();
			return ResponseEntity.ok(response);
		} catch (InternalException internalException) {
			logger.error("[GET] /kpiclientes [ERROR] ",
					internalException.getMessage());
			return new ResponseEntity(internalException.getStatus());

		} catch (Exception e) {
			logger.error("[GET] /kpiclientes [ERROR]", e.getMessage());
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info("[GET] END /kpiclientes");
		}
	}

	/**
	 * Service returns client list
	 * 
	 * @return
	 */
	@GetMapping("/listclientes")
	public ResponseEntity listClients() {
		try {
			logger.info("[GET] START /listclientes");
			List<ClientDetailDto> response = clientService.getList();
			return ResponseEntity.ok(response);
		} catch (InternalException internalException) {
			logger.error("[GET] START /listclientes [ERROR] ",
					internalException.getMessage());
			return new ResponseEntity(internalException.getStatus());

		} catch (Exception e) {
			logger.error("[GET] /listclientes [ERROR] ", e.getMessage());
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info("[GET] END /listclientes");
		}
	}

	/**
	 * service create a client, validating it already exists
	 * 
	 * @param client
	 * @return
	 */
	@PostMapping("/creacliente")
	public ResponseEntity saveClient(@RequestBody Client client) {
		try {
			logger.info("[POST] START /creacliente", client.toString());
			clientService.save(client);
			return new ResponseEntity(HttpStatus.OK);
		} catch (InternalException internalException) {
			logger.error("[POST] /creacliente [ERROR] ",
					internalException.getMessage());
			return new ResponseEntity(internalException.getStatus());

		} catch (Exception e) {
			logger.error("[POST] /creacliente [ERROR] ", e.getMessage());
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info("[END] END /creacliente", client.toString());
		}
	}
}
