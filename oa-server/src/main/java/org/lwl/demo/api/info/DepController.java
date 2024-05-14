package org.lwl.demo.api.info;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.common.vo.R;
import org.lwl.demo.dao.DepDao;
import org.lwl.demo.dto.DepDto;
import org.lwl.demo.dto.DepQueryDto;
import org.lwl.demo.model.Dep;
import org.lwl.demo.service.info.DepService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DepController {
    private final DepService depService;

    @GetMapping("/info/dep")
    public R<PageVo<Dep>> depList(DepQueryDto depQueryDto) {
        PageVo<Dep> page = depService.getDepPage(depQueryDto);
        return R.OK(page);
    }

    @PostMapping("/info/dep")
    public R<?> execAdd(@RequestBody DepDto depDto) {
        depService.addDep(depDto);
        return R.OK();
    }

    @PutMapping("/info/dep")
    public R<?> execUpd(@RequestBody DepDto depDto) {
        depService.updateDep(depDto);
        return R.OK();
    }
}
