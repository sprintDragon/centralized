package org.sprintdragon.centralized.shared.arbitrate.event;

import java.util.List;

/**
 * Created by wangdi on 17-2-22.
 */
public interface OperateEvent<T> extends ArbitrateEvent {

    void upsert(T t) throws Exception;

    List<T> list() throws Exception;

}
