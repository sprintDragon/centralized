package org.sprintdragon.centralized.manager.statistics.throughput.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by wangdi on 16-11-3.
 */
@Repository
public interface ThroughputStatRepository extends PagingAndSortingRepository<ThroughputStatDO, Long>, JpaSpecificationExecutor<ThroughputStatDO> {

    ThroughputStatDO findByUnitIdAndType(Long unitId, String type);

    List<ThroughputStatDO> findByUnitIdAndTypeAndEndTimeBetween(Long unitId, String type, Date begin, Date end);

}
