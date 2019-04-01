package ms.client.service;

import java.util.List;

import ms.client.dto.ClientDetailDto;
import ms.client.dto.KpiClientDto;
import ms.client.exception.InternalException;
import ms.client.model.Client;
import ms.client.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService implements IClientService {

	private static final String TIME_OUT_MESSAGE = "Time Out Database Connection";
	@Autowired
	private ClientRepository clientRepository;

	@Override
	@Transactional(readOnly = true)
	public void save(Client client) throws InternalException {
		try {
			validateClientInfo(client);
			clientRepository.save(client);
		} catch (DataAccessException e) {
			throw new InternalException(HttpStatus.REQUEST_TIMEOUT,
					TIME_OUT_MESSAGE);
		}

	}

	/**
	 * Validate if client info is correct and if client no already exists
	 * 
	 * @param client
	 * @throws InternalException
	 */
	void validateClientInfo(Client client) throws InternalException {
		if (client.getFirstName() == null || client.getFirstName().isEmpty()
				|| client.getLastName() == null
				|| client.getLastName().isEmpty()
				|| client.getBirthDate() == null)

			throw new InternalException(HttpStatus.BAD_REQUEST,
					"Incorrect Parameters");
		Integer count = clientRepository.validateUserByNameAndBirthDate(
				client.getFirstName(), client.getLastName(),
				client.getBirthDate());
		if (count>0)
			throw new InternalException(HttpStatus.CONFLICT,
					"Client already exists");
	}

	@Override
	public KpiClientDto getKpi() throws InternalException {
		try {
			Object[] list = clientRepository.calculateKpiClient();
			KpiClientDto kpiClientDto = new KpiClientDto();
			if (list != null && list[0] != null) {
				Object[] object=(Object[]) list[0];
				if (object[0] != null)
					kpiClientDto.setAgeAverage(object[0].toString());
				if (object[1] != null)
					kpiClientDto.setStandarDeviation(object[1].toString());
			}
			return kpiClientDto;
		} catch (DataAccessException e) {
			throw new InternalException(HttpStatus.REQUEST_TIMEOUT,
					TIME_OUT_MESSAGE);
		}
	}

	@Override
	public List<ClientDetailDto> getList() throws InternalException {
		try {
			return clientRepository.listAll();
		} catch (DataAccessException e) {
			throw new InternalException(HttpStatus.REQUEST_TIMEOUT,
					TIME_OUT_MESSAGE);
		}
	}

}
