package org.lwl.demo.service.info;

import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dto.DepDto;
import org.lwl.demo.dto.DepQueryDto;
import org.lwl.demo.model.Dep;

public interface DepService {
    PageVo<Dep> getDepPage(DepQueryDto depQueryDto);

    void addDep(DepDto depDto);

    void updateDep(DepDto depDto);
}
