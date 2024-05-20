package org.lwl.demo.api.leave;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.LeaveStatusEnum;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.common.vo.R;
import org.lwl.demo.dto.LeaveDto;
import org.lwl.demo.dto.LeaveQueryDto;
import org.lwl.demo.model.Leave;
import org.lwl.demo.service.leave.ExamService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;
    private final CurrentUser currentUser;

    @GetMapping("/leave/exam")
    public R<PageVo<Leave>> examList(LeaveQueryDto leaveQueryDto) {
        // 审批页 获取当前领导工号 领导只能审批本部门下的请假申请
        leaveQueryDto.setLeader_id(currentUser.getUserId());
        PageVo<Leave> page =  examService.getExamPage(leaveQueryDto);
        return R.OK(page);
    }

    @PutMapping("/leave/exam/pass")
    public R<?> execPass(@RequestBody LeaveDto leaveDto) {
        leaveDto.setL_status(LeaveStatusEnum.PASS.getCode());
        leaveDto.setL_spr(currentUser.getUserName());
        leaveDto.setL_sp_date(new Date());
        examService.spLeave(leaveDto);
        return R.OK();
    }

    @PutMapping("/leave/exam/reject")
    public R<?> execReject(@RequestBody LeaveDto leaveDto) {
        leaveDto.setL_status(LeaveStatusEnum.REJECTED.getCode());
        leaveDto.setL_spr(currentUser.getUserName());
        leaveDto.setL_sp_date(new Date());
        examService.spLeave(leaveDto);
        return R.OK();
    }
}
