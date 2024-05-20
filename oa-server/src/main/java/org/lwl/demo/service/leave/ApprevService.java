package org.lwl.demo.service.leave;

import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dto.LeaveDto;
import org.lwl.demo.dto.LeaveQueryDto;
import org.lwl.demo.model.Leave;

public interface ApprevService {

    PageVo<Leave> getApprevPage(LeaveQueryDto leaveQueryDto);

    void addLeave(LeaveDto leaveDto);

    void updateLeave(LeaveDto leaveDto);

    void deleteLeave(Long... ids);

    void submitLeave(Long id);

    void backLeave(Long id);
}
