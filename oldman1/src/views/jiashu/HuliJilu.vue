<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">家属端</p>
      <h2>床位与护理记录</h2>
      <p>查看绑定老人的当前床位、护理级别和护理员护理记录。</p>
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
        <thead><tr><th>老人</th><th>楼栋</th><th>楼层</th><th>房间</th><th>房型</th><th>床位</th><th>床位状态</th><th>护理级别</th><th>设置日期</th></tr></thead>
        <tbody>
          <tr v-for="item in chuangweiList" :key="`${item.laorenId}-${item.chuangweihao}`">
            <td>{{ item.laorenXingming }}</td><td>{{ item.loudong || '-' }}</td><td>{{ item.louceng || '-' }}</td><td>{{ item.fangjianhao || '-' }}</td><td>{{ item.fangjianLeixing || '-' }}</td><td>{{ item.chuangweihao || '-' }}</td><td>{{ item.chuangweiZhuangtai || '-' }}</td><td>{{ item.jibieMingcheng || '-' }}</td><td>{{ item.shezhiRiqi || '-' }}</td>
          </tr>
          <tr v-if="!chuangweiList.length"><td colspan="9" class="kong">暂无床位护理信息</td></tr>
        </tbody>
      </table>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead><tr><th>老人</th><th>护理员</th><th>护理项目</th><th>时间</th><th>数量</th><th>内容</th><th>反馈</th><th>状态</th></tr></thead>
        <tbody>
          <tr v-for="item in huliList" :key="item.id">
            <td>{{ item.laorenXingming }}</td><td>{{ item.huliRenyuanXingming }}</td><td>{{ item.xiangmuMingcheng }}</td><td>{{ item.huliShijian }}</td><td>{{ item.huliShuliang }}</td><td>{{ item.huliNeirong || '-' }}</td><td>{{ item.laorenFankui || '-' }}</td><td>{{ item.zhuangtai }}</td>
          </tr>
          <tr v-if="!huliList.length"><td colspan="8" class="kong">暂无护理记录</td></tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getJiashuChuangweiInfo, getJiashuHuliJiluList, getJiashuLaorenXinxi } from '../../api/jiashu'

const query = reactive({ laorenId: '' })
const laorenList = ref([])
const chuangweiList = ref([])
const huliList = ref([])

async function loadLaoren() {
  const res = await getJiashuLaorenXinxi()
  laorenList.value = res.data || []
}

async function loadAll() {
  const params = query.laorenId ? { laorenId: query.laorenId } : {}
  const [chuangwei, huli] = await Promise.all([getJiashuChuangweiInfo(params), getJiashuHuliJiluList(params)])
  chuangweiList.value = chuangwei.data || []
  huliList.value = huli.data || []
}

onMounted(async () => {
  await loadLaoren()
  await loadAll()
})
</script>

<style scoped>
.kong { color: #94a3b8; text-align: center; }
</style>
