package com.fish.service.impl;

import com.fish.entity.Log;
import com.fish.repository.LogRepository;
import com.fish.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fish
 * <p>
 * 2018年4月18日
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public Integer saveLog(Log log) {
        return logRepository.save(log).getId();
    }
}
