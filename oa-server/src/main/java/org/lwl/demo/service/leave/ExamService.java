package org.lwl.demo.service.leave;

import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dto.LeaveDto;
import org.lwl.demo.dto.LeaveQueryDto;
import org.lwl.demo.model.Leave;

public interface ExamService {
    PageVo<Leave> getExamPage(LeaveQueryDto leaveQueryDto);

    void spLeave(LeaveDto leaveDto);
}
