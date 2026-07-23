package com.neu.common.auth;

import com.neu.xitong.entity.XitongJuese;
import com.neu.xitong.entity.XitongYonghu;
import com.neu.xitong.mapper.XitongYonghuMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class DangqianYonghuResolver {

    private final XitongYonghuMapper xitongYonghuMapper;

    public DangqianYonghuResolver(XitongYonghuMapper xitongYonghuMapper) {
        this.xitongYonghuMapper = xitongYonghuMapper;
    }

    public DangqianYonghu resolve(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Long yonghuId = parseYonghuId(token);
        if (yonghuId == null) {
            throw new IllegalArgumentException("请先登录后再访问");
        }

        XitongYonghu yonghu = xitongYonghuMapper.findById(yonghuId);
        if (yonghu == null) {
            throw new IllegalArgumentException("登录用户不存在");
        }

        List<String> jueseBianmaList = yonghu.getJueseList() == null
                ? List.of()
                : yonghu.getJueseList().stream()
                .map(XitongJuese::getJueseBianma)
                .filter(StringUtils::hasText)
                .toList();

        return new DangqianYonghu(yonghuId, jueseBianmaList);
    }

    private Long parseYonghuId(String token) {
        if (!StringUtils.hasText(token) || !token.startsWith("oldman-")) {
            return null;
        }

        String[] parts = token.split("-");
        if (parts.length < 3) {
            return null;
        }

        try {
            return Long.valueOf(parts[1]);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}


