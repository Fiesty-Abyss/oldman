package com.oldman.zanzhu.biz;

import java.util.List;
import java.util.Map;

public interface ZanzhuBiz {

    List<Map<String, Object>> gongkaiList();

    List<Map<String, Object>> yongtuList();

    List<Map<String, Object>> wuziList();
}


