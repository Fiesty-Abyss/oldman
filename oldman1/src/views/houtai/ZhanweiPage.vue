<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">{{ mokuaiming }}</p>
      <h2>{{ biaoti }}</h2>
      <p>{{ shuoming }}</p>
    </div>

    <div class="chaxunqu">
      <label>
        <span>关键词</span>
        <input placeholder="请输入姓名、编号或状态" />
      </label>
      <label>
        <span>状态</span>
        <select>
          <option>全部</option>
          <option>启用</option>
          <option>待审批</option>
          <option>已完成</option>
        </select>
      </label>
      <button type="button">查询</button>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead>
          <tr>
            <th>序号</th>
            <th>业务名称</th>
            <th>关联对象</th>
            <th>状态</th>
            <th>更新时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td colspan="6">当前为基础框架占位，后续接入真实接口数据。</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script setup>
defineProps({
  mokuaiming: {
    type: String,
    default: '业务模块'
  },
  biaoti: {
    type: String,
    required: true
  },
  shuoming: {
    type: String,
    default: '先保留查询区和表格结构，后续逐步补充增删改查与审批流。'
  }
})
</script>
