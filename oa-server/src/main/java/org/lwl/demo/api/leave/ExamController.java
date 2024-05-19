package org.lwl.demo.api.leave;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.service.leave.ExamService;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;

@RestController
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;
    private final CurrentUser currentUser;
}
