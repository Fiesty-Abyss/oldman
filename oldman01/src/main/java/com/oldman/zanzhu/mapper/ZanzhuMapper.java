package com.oldman.zanzhu.mapper;

import java.util.List;
import java.util.Map;

public interface ZanzhuMapper {

    List<Map<String, Object>> gongkaiList();

    List<Map<String, Object>> yongtuList();

    List<Map<String, Object>> wuziList();
}


