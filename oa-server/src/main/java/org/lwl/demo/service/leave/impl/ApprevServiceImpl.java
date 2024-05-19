package org.lwl.demo.service.leave.impl;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.LeaveStatusEnum;
import org.lwl.demo.common.ex.BusinessException;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dao.ApprevDao;
import org.lwl.demo.dto.LeaveDto;
import org.lwl.demo.dto.LeaveQueryDto;
import org.lwl.demo.model.Leave;
import org.lwl.demo.service.leave.ApprevService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApprevServiceImpl implements ApprevService {

    private final ApprevDao apprevDao;

    @Override
    public PageVo<Leave> getApprevPage(LeaveQueryDto leaveQueryDto) {
        return PageVo.getPageVo(leaveQueryDto, ()->apprevDao.findApprevList(leaveQueryDto));
    }

    @Override
    public void addLeave(LeaveDto leaveDto) {
        apprevDao.insertLeave(leaveDto);
    }

    @Override
    public void updateLeave(LeaveDto leaveDto) {
        apprevDao.updateLeave(leaveDto);
    }

    @Override
    public void deleteLeave(Long... ids) {
        if (ids == null || ids.length == 0) throw new BusinessException("请至少选择一条数据!");

        //已经提交的请假申请，不能删除
        boolean exists = apprevDao.findExistsSubmited(ids);
        if (exists) throw new BusinessException("至少一条请假申请单已被提交,禁止删除!");

        apprevDao.deleteLeave(ids);
    }

    @Override
    public void submitLeave(Long id) {
        apprevDao.updateLeaveStatus(id, LeaveStatusEnum.SUBMITED.getCode());
    }
}
