package li.bankfrick.informatik.reporting.csdr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import li.bankfrick.informatik.reporting.csdr.entities.db.mapping.TxTp_Mapping;

public interface TxTp_Mapping_Repository extends JpaRepository<TxTp_Mapping, Integer> {
	

}
