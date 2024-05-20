package org.lwl.demo.api.leave;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.LeaveStatusEnum;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.common.vo.R;
import org.lwl.demo.dto.LeaveDto;
import org.lwl.demo.dto.LeaveQueryDto;
import org.lwl.demo.model.Leave;
import org.lwl.demo.service.leave.ApprevService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class ApprevController {

    private final ApprevService apprevService;

    // 登录者只能给自己请假 需要获取当前用户
    private final CurrentUser currentUser;

    @GetMapping("/leave/apprev")
    public R<PageVo<Leave>> apprevList(LeaveQueryDto leaveQueryDto) {
        leaveQueryDto.setE_id(currentUser.getUserId());
        PageVo<Leave> page = apprevService.getApprevPage(leaveQueryDto);
        return R.OK(page);
    }

    @PostMapping("/leave/apprev")
    public R<?> execAdd(@RequestBody LeaveDto leaveDto) {
        leaveDto.setE_id(currentUser.getUserId());
        leaveDto.setL_submit_date(new Date());
        leaveDto.setL_status(LeaveStatusEnum.NOT_SUBMIT.getCode());
        apprevService.addLeave(leaveDto);
        return R.OK();
    }

    @PutMapping("/leave/apprev")
    public R<?> execUpd(@RequestBody LeaveDto leaveDto) {
        //leaveDto.setE_id(currentUser.getUserId());    修改已知道了id
        leaveDto.setL_submit_date(new Date());
        //leaveDto.setL_status(LeaveStatusEnum.NOT_SUBMIT.getCode());   能修改的，一定是 已提交 状态
        apprevService.updateLeave(leaveDto);
        return R.OK();
    }

    @DeleteMapping("/leave/apprev/{id}")
    public R<?> execDel(@PathVariable Long id) {
        apprevService.deleteLeave(id);
        return R.OK();
    }

    @DeleteMapping("/leave/apprev")
    public R<?> execDel(@RequestBody Long... ids) {
        apprevService.deleteLeave(ids);
        return R.OK();
    }

    @PutMapping("/leave/apprev/submit/{id}")
    public R<?> execSubmit(@PathVariable Long id) {
        apprevService.submitLeave(id);
        return R.OK();
    }

    @PutMapping("/leave/apprev/back/{id}")
    public R<?> execBack(@PathVariable Long id) {
        apprevService.backLeave(id);
        return R.OK();
    }
}
