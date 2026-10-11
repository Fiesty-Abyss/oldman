package com.oldman.denglu.dto;

import java.util.List;

public record DengluXiangying(
        String token,
        Long yonghuId,
        String dengluZhanghao,
        String xingming,
        String touxiang,
        List<String> jueseBianmaList,
        List<String> jueseMingchengList
) {
}


