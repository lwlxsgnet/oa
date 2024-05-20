package org.lwl.demo.service.leave.impl;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dao.ExamDao;
import org.lwl.demo.dto.LeaveDto;
import org.lwl.demo.dto.LeaveQueryDto;
import org.lwl.demo.model.Leave;
import org.lwl.demo.service.leave.ExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamDao examDao;

    @Override
    public PageVo<Leave> getExamPage(LeaveQueryDto leaveQueryDto) {
        return PageVo.getPageVo(leaveQueryDto, ()->examDao.findExamList(leaveQueryDto));
    }

    @Override
    public void spLeave(LeaveDto leaveDto) {
        examDao.updateLeave(leaveDto);
    }
}
