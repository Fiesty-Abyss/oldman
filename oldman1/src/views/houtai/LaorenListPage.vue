<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">{{ mokuaiming }}</p>
      <h2>{{ biaoti }}</h2>
      <p>{{ shuoming }}</p>
    </div>

    <div class="chaxunqu">
      <label>
        <span>老人姓名</span>
        <input v-model.trim="keyword" placeholder="请输入老人姓名或编号" />
      </label>
      <button type="button" @click="loadList">刷新</button>
    </div>

    <div v-if="cuowu" class="form-cuowu">{{ cuowu }}</div>

    <div class="biaoge-wrap">
      <table>
        <thead>
          <tr>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>老人类型</th>
            <th>入住状态</th>
            <th>家庭住址</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in guolvList" :key="item.id">
            <td>{{ item.laorenBianhao }}</td>
            <td>{{ item.xingming }}</td>
            <td>{{ item.xingbie }}</td>
            <td>{{ item.nianling }}</td>
            <td>{{ item.laorenLeixing }}</td>
            <td>{{ item.ruzhuZhuangtai }}</td>
            <td>{{ item.jiatingZhuzhi }}</td>
          </tr>
          <tr v-if="!loading && guolvList.length === 0">
            <td colspan="7">暂无可查看的老人信息。</td>
          </tr>
          <tr v-if="loading">
            <td colspan="7">正在加载...</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { getLaorenXinxiList } from '../../api/laoren'

defineProps({
  mokuaiming: {
    type: String,
    default: '老人信息'
  },
  biaoti: {
    type: String,
    required: true
  },
  shuoming: {
    type: String,
    default: '该列表由后端根据当前登录角色自动控制数据范围。'
  }
})

const loading = ref(false)
const cuowu = ref('')
const keyword = ref('')
const list = ref([])

const guolvList = computed(() => {
  if (!keyword.value) return list.value
  return list.value.filter((item) =>
    item.xingming?.includes(keyword.value) || item.laorenBianhao?.includes(keyword.value)
  )
})

async function loadList() {
  loading.value = true
  cuowu.value = ''

  try {
    const res = await getLaorenXinxiList()
    list.value = res.data || []
  } catch (err) {
    cuowu.value = err.message
  } finally {
    loading.value = false
  }
}

onMounted(loadList)
</script>
