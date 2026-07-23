<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">家属端</p>
      <h2>健康与用药记录</h2>
      <p>查看绑定老人的健康监测、用药计划和服药执行记录。</p>
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
        <thead><tr><th>老人</th><th>护理员</th><th>时间</th><th>体温</th><th>血压</th><th>心率</th><th>血氧</th><th>血糖</th><th>备注</th></tr></thead>
        <tbody>
          <tr v-for="item in jiankangList" :key="item.id">
            <td>{{ item.laorenXingming }}</td><td>{{ item.huliRenyuanXingming || '-' }}</td><td>{{ item.jianceShijian }}</td><td>{{ item.tiwen || '-' }}</td><td>{{ item.gaoya || '-' }}/{{ item.diya || '-' }}</td><td>{{ item.xinlv || '-' }}</td><td>{{ item.xueyang || '-' }}</td><td>{{ item.xuetang || '-' }}</td><td>{{ item.qingkuang || '-' }}</td>
          </tr>
          <tr v-if="!jiankangList.length"><td colspan="9" class="kong">暂无健康监测记录</td></tr>
        </tbody>
      </table>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead><tr><th>老人</th><th>药品</th><th>规格</th><th>用法</th><th>每日次数</th><th>每次用量</th><th>用药时间</th><th>状态</th><th>医嘱</th></tr></thead>
        <tbody>
          <tr v-for="item in jihuaList" :key="item.id">
            <td>{{ item.laorenXingming }}</td><td>{{ item.yaopinMingcheng }}</td><td>{{ item.guige || '-' }}</td><td>{{ item.yongfa || '-' }}</td><td>{{ item.meiriCishu }}</td><td>{{ item.meiciYongliang || '-' }}</td><td>{{ item.yongyaoShijian || '-' }}</td><td>{{ item.zhuangtai }}</td><td>{{ item.yizhu || '-' }}</td>
          </tr>
          <tr v-if="!jihuaList.length"><td colspan="9" class="kong">暂无用药计划</td></tr>
        </tbody>
      </table>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead><tr><th>老人</th><th>护理员</th><th>药品</th><th>计划时间</th><th>实际时间</th><th>执行状态</th><th>备注</th></tr></thead>
        <tbody>
          <tr v-for="item in fuyaoList" :key="item.id">
            <td>{{ item.laorenXingming }}</td><td>{{ item.huliRenyuanXingming || '-' }}</td><td>{{ item.yaopinMingcheng }}</td><td>{{ item.jihuaShijian }}</td><td>{{ item.shijiShijian || '-' }}</td><td>{{ item.zhixingZhuangtai }}</td><td>{{ item.beizhu || '-' }}</td>
          </tr>
          <tr v-if="!fuyaoList.length"><td colspan="7" class="kong">暂无服药记录</td></tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getJiashuFuyaoJiluList, getJiashuJiankangJianceList, getJiashuLaorenXinxi, getJiashuYongyaoJihuaList } from '../../api/jiashu'

const query = reactive({ laorenId: '' })
const laorenList = ref([])
const jiankangList = ref([])
const jihuaList = ref([])
const fuyaoList = ref([])

async function loadLaoren() {
  const res = await getJiashuLaorenXinxi()
  laorenList.value = res.data || []
}

async function loadAll() {
  const params = query.laorenId ? { laorenId: query.laorenId } : {}
  const [jiankang, jihua, fuyao] = await Promise.all([getJiashuJiankangJianceList(params), getJiashuYongyaoJihuaList(params), getJiashuFuyaoJiluList(params)])
  jiankangList.value = jiankang.data || []
  jihuaList.value = jihua.data || []
  fuyaoList.value = fuyao.data || []
}

onMounted(async () => {
  await loadLaoren()
  await loadAll()
})
</script>

<style scoped>
.kong { color: #94a3b8; text-align: center; }
</style>
