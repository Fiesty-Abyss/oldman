package com.oldman.kanban.biz.impl;


import com.oldman.kanban.biz.KanbanBiz;
import com.oldman.kanban.mapper.KanbanMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class KanbanBizImpl implements KanbanBiz {

    private final KanbanMapper kanbanMapper;

    public KanbanBizImpl(KanbanMapper kanbanMapper) {
        this.kanbanMapper = kanbanMapper;
    }

    @Override
    public Map<String, Integer> tongji() {
        Map<String, Integer> data = new LinkedHashMap<>();
        data.put("laorenZongshu", kanbanMapper.countLaoren());
        data.put("zaizhuLaoren", kanbanMapper.countZaizhuLaoren());
        data.put("kongxianChuangwei", kanbanMapper.countKongxianChuangwei());
        data.put("huliRenyuan", kanbanMapper.countHuliRenyuan());
        data.put("daishenpiWaichu", kanbanMapper.countDaishenpiWaichu());
        data.put("daishenpiTuizhu", kanbanMapper.countDaishenpiTuizhu());
        return data;
    }
}


