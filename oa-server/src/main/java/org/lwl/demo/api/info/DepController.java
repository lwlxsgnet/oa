package org.lwl.demo.api.info;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.common.vo.R;
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

    @DeleteMapping("/info/dep/{id}")
    public R<?> execDel(@PathVariable Integer id) {
        depService.deleteDep(id);
        return R.OK();
    }

    @DeleteMapping("/info/dep")
    public R<?> execDel(@RequestBody Integer[] ids) {
        depService.deleteDep(ids);
        return R.OK();
    }

    @PutMapping("/info/dep/{id}/{status}")
    public R<?> changeStatus(@PathVariable Integer id, @PathVariable Integer status) {
        depService.changeStatus(id, status);
        return R.OK();
    }
}
