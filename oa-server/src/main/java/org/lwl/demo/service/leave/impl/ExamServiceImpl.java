package org.lwl.demo.service.leave.impl;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.dao.ExamDao;
import org.lwl.demo.service.leave.ExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamDao examDao;
}
