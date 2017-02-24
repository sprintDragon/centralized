package org.sprintdragon.centralized.manager.statistics.throughput.repository;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangdi on 16-11-3.
 */
@Data
@ToString
@Entity
@Table(name = "centralized_throughput_stat")
public class ThroughputStatDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;
    @Column(name = "UNIT_ID")
    private Long unitId;
    @Column(name = "START_TIME")
    private Date startTime;
    @Column(name = "END_TIME")
    private Date endTime;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "NUMBER")
    private Long number;
    @Column(name = "SIZE")
    private Long size;
    @Column(name = "CREATED")
    private Date create;
    @Column(name = "MODIFIED")
    private Date modified;

}
