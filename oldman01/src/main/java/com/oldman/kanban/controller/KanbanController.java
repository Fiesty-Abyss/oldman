package com.oldman.kanban.controller;

import com.oldman.common.ApiResponse;
import com.oldman.kanban.biz.KanbanBiz;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/kanban")
public class KanbanController {

    private final KanbanBiz kanbanBiz;

    public KanbanController(KanbanBiz kanbanBiz) {
        this.kanbanBiz = kanbanBiz;
    }

    @GetMapping("/tongji")
    public ApiResponse<Map<String, Integer>> tongji() {
        return ApiResponse.ok(kanbanBiz.tongji());
    }
}



