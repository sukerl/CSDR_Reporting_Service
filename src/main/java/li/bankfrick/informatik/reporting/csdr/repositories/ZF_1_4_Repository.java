package li.bankfrick.informatik.reporting.csdr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import li.bankfrick.informatik.reporting.csdr.excel.entities.ZF_1_4;

public interface ZF_1_4_Repository extends JpaRepository<ZF_1_4, Integer> {
	
	List<ZF_1_4> findByAnlegerTyp(String anlegerTyp);
	
}
