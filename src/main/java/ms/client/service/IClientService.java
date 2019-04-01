package ms.client.service;

import java.util.List;

import ms.client.dto.ClientDetailDto;
import ms.client.dto.KpiClientDto;
import ms.client.exception.InternalException;
import ms.client.model.Client;

public interface IClientService {

	/**
	 * Save a client
	 * @param client
	 * @throws InternalException
	 */
	void save(Client client) throws InternalException;

	/**
	 * Return kpi of clients based in ages
	 * @return
	 * @throws InternalException
	 */
	KpiClientDto getKpi() throws InternalException;

	/**
	 * Return client list
	 * @return
	 * @throws InternalException
	 */
	List<ClientDetailDto> getList() throws InternalException;

}
