<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">家属端</p>
      <h2>费用账单</h2>
      <p>查看绑定老人的费用账单和线下缴费记录。</p>
    </div>

    <div class="chaxunqu">
      <label>
        <span>老人</span>
        <select v-model="query.laorenId">
          <option value="">全部</option>
          <option v-for="item in laorenList" :key="item.laorenId" :value="item.laorenId">{{ item.laorenXingming }}</option>
        </select>
      </label>
      <button type="button" @click="loadAll">查询</button>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead><tr><th>老人</th><th>月份</th><th>床位费</th><th>护理费</th><th>药品费</th><th>其他</th><th>应收金额</th><th>已缴金额</th><th>未缴金额</th><th>状态</th><th>生成时间</th></tr></thead>
        <tbody>
          <tr v-for="item in zhangdanList" :key="item.id">
            <td>{{ item.laorenXingming }}</td><td>{{ item.zhangdanYuefen }}</td><td>{{ item.chuangweiFei }}</td><td>{{ item.huliFei }}</td><td>{{ item.yaopinFei }}</td><td>{{ item.qitaFei }}</td><td>{{ item.zongjine }}</td><td>{{ item.yijiaoJine }}</td><td>{{ item.weiJiaoJine }}</td><td>{{ item.zhuangtai }}</td><td>{{ item.shengchengShijian }}</td>
          </tr>
          <tr v-if="!zhangdanList.length"><td colspan="11" class="kong">暂无费用账单</td></tr>
        </tbody>
      </table>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead><tr><th>老人</th><th>账单月份</th><th>缴费金额</th><th>缴费方式</th><th>缴费时间</th><th>备注</th></tr></thead>
        <tbody>
          <tr v-for="item in jiaofeiList" :key="item.id">
            <td>{{ item.laorenXingming }}</td><td>{{ item.zhangdanYuefen }}</td><td>{{ item.jiaofeiJine }}</td><td>{{ item.jiaofeiFangshi || '-' }}</td><td>{{ item.jiaofeiShijian }}</td>
            <td>{{ item.beizhu || '-' }}</td>
          </tr>
          <tr v-if="!jiaofeiList.length"><td colspan="6" class="kong">暂无缴费记录</td></tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getJiashuFeiyongZhangdanList, getJiashuJiaofeiJiluList, getJiashuLaorenXinxi } from '../../api/jiashu'

const query = reactive({ laorenId: '' })
const laorenList = ref([])
const zhangdanList = ref([])
const jiaofeiList = ref([])

async function loadLaoren() {
  const res = await getJiashuLaorenXinxi()
  laorenList.value = res.data || []
}

async function loadAll() {
  const params = query.laorenId ? { laorenId: query.laorenId } : {}
  const [zhangdan, jiaofei] = await Promise.all([getJiashuFeiyongZhangdanList(params), getJiashuJiaofeiJiluList(params)])
  zhangdanList.value = zhangdan.data || []
  jiaofeiList.value = jiaofei.data || []
}

onMounted(async () => {
  await loadLaoren()
  await loadAll()
})
</script>

<style scoped>
.kong { color: #94a3b8; text-align: center; }
</style>
