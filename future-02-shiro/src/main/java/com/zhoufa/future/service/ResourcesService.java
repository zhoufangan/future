package com.zhoufa.future.service;

import com.zhoufa.future.entity.Resources;

import java.util.List;

/**
 * @author zhoufangan. Created in 2019/4/18 17:36.
 * @version V1.0
 */
public interface ResourcesService {
    List<Resources> listAll();

    List<Resources> listByUserId(Long userId);
}
