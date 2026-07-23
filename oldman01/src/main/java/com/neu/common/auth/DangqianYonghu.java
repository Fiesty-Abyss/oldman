package com.neu.common.auth;

import java.util.List;

public record DangqianYonghu(Long yonghuId, List<String> jueseBianmaList) {

    public boolean hasJuese(String jueseBianma) {
        return jueseBianmaList != null && jueseBianmaList.contains(jueseBianma);
    }

    public String firstJuese() {
        if (jueseBianmaList == null || jueseBianmaList.isEmpty()) {
            return "";
        }
        return jueseBianmaList.get(0);
    }
}


