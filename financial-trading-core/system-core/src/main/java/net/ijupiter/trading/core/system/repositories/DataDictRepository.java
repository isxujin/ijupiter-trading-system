package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.DataDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 数据字典仓储接口
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Repository
public interface DataDictRepository extends JpaRepository<DataDict, String>, JpaSpecificationExecutor<DataDict> {

}