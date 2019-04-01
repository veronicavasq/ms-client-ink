package ms.client.repository;

import java.util.Date;
import java.util.List;

import ms.client.dto.ClientDetailDto;
import ms.client.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
	
	@Query(value="SELECT count(u.id) FROM client as u WHERE u.firstName= :name and u.lastName = :lastName and client.birthDate=:birthDate", nativeQuery = true)
	Integer validateUserByNameAndBirthDate(@Param("name") String name, @Param("lastName") String lastName, @Param("birthDate")  Date birthDate);

	@Query(value = "SELECT ROUND(AVG(ABS(IFNULL(TIMESTAMPDIFF(YEAR,CURDATE(),birthDate),0)))) as ageAverage "
			+ " , STDDEV(birthDate) as standarDeviation "
			+ " FROM client "
			, nativeQuery = true)
	Object[] calculateKpiClient();
	
	@Query(value = "SELECT "
			+ " client.firstName as firstName, client.lastName as lastName, CONVERT(client.birthDate , DATE) as  birthDate, "
			+ " CONVERT (ABS(TIMESTAMPDIFF(YEAR,CURDATE(),birthDate)), UNSIGNED) as age,"
			+ " CONVERT(DATE_ADD(birthDate, INTERVAL (70+TIMESTAMPDIFF(YEAR,CURDATE(),birthDate))*365 DAY), DATE) as deathDate "
			+ " FROM client ", nativeQuery = true)
	List<ClientDetailDto> listAll();

}