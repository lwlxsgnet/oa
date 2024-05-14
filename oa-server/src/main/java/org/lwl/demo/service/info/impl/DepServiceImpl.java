package org.lwl.demo.service.info.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.InfoStatusEnum;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.common.page.QueryAction;
import org.lwl.demo.dao.DepDao;
import org.lwl.demo.dto.DepDto;
import org.lwl.demo.dto.DepQueryDto;
import org.lwl.demo.model.Dep;
import org.lwl.demo.service.info.DepService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DepServiceImpl implements DepService {

    private final DepDao depDao;
    @Override
    public PageVo<Dep> getDepPage(DepQueryDto depQueryDto) {

//        QueryAction<Dep> action = new QueryAction<Dep>() {
//            @Override
//            public List<Dep> executeQuery() throws Exception {
//                return depDao.findDepList(depQueryDto);
//            }
//        };

//        QueryAction<Dep> action = ()->depDao.findDepList(depQueryDto);

        return PageVo.getPageVo(depQueryDto, ()->depDao.findDepList(depQueryDto));//第二个参数可用action替代
    }

    private int id;//最大编号

    @PostConstruct//表示该方法在创建对象后执行，且仅一次执行 初始化方法
    public void init() {
        id = depDao.findMaxId();
    }
    //线程安全 synchronized
    private synchronized int newId() {
        return ++id;
    }

    @Override
    public void addDep(DepDto depDto) {
        depDto.setD_id(newId());
        depDto.setD_status(InfoStatusEnum.undetermined.getCode());
        depDao.insertDep(depDto);
    }

    @Override
    public void updateDep(DepDto depDto) {
        depDao.updateDep(depDto);
    }
}
