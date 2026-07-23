<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">护理员端</p>
      <h2>健康监测记录</h2>
      <p>记录自己负责老人的体温、血压、血糖、心率等基础监测数据。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="chaxunqu">
      <label>
        <span>老人</span>
        <select v-model="query.laorenId">
          <option value="">全部</option>
          <option v-for="item in laorenList" :key="item.laorenId" :value="item.laorenId">{{ item.laorenXingming }}</option>
        </select>
      </label>
      <button type="button" @click="load">查询</button>
      <button type="button" @click="openAdd">新增监测记录</button>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead>
          <tr><th>老人</th><th>监测时间</th><th>体温</th><th>血压</th><th>心率</th><th>血氧</th><th>血糖</th><th>备注</th></tr>
        </thead>
        <tbody>
          <tr v-for="item in list" :key="item.id">
            <td>{{ item.laorenXingming }}</td>
            <td>{{ item.jianceShijian }}</td>
            <td>{{ item.tiwen || '-' }}</td>
            <td>{{ item.gaoya || '-' }}/{{ item.diya || '-' }}</td>
            <td>{{ item.xinlv || '-' }}</td>
            <td>{{ item.xueyang || '-' }}</td>
            <td>{{ item.xuetang || '-' }}</td>
            <td>{{ item.qingkuang || '-' }}</td>
          </tr>
          <tr v-if="!list.length"><td colspan="8" class="kong">暂无健康监测记录</td></tr>
        </tbody>
      </table>
    </div>

    <div v-if="dialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>新增健康监测记录</h3>
        <div class="modal-form">
          <label>
            <span>老人</span>
            <select v-model="form.laorenId">
              <option value="">请选择老人</option>
              <option v-for="item in laorenList" :key="item.laorenId" :value="item.laorenId">{{ item.laorenXingming }}</option>
            </select>
          </label>
          <label><span>监测时间</span><input v-model="form.jianceShijian" type="datetime-local" /></label>
          <label><span>体温</span><input v-model="form.tiwen" type="number" step="0.1" /></label>
          <label><span>高压</span><input v-model="form.gaoya" type="number" /></label>
          <label><span>低压</span><input v-model="form.diya" type="number" /></label>
          <label><span>心率</span><input v-model="form.xinlv" type="number" /></label>
          <label><span>血氧</span><input v-model="form.xueyang" type="number" /></label>
          <label><span>血糖</span><input v-model="form.xuetang" type="number" step="0.1" /></label>
          <label class="full"><span>备注</span><textarea v-model="form.qingkuang"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="save">保存</button>
          <button type="button" class="plain" @click="dialogVisible = false">取消</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { addHuliyuanJiankangJiance, getHuliyuanJiankangJianceList, getHuliyuanLaorenList } from '../../api/huliyuan'

const query = reactive({ laorenId: '' })
const form = reactive({ laorenId: '', jianceShijian: '', tiwen: '', gaoya: '', diya: '', xinlv: '', xueyang: '', xuetang: '', qingkuang: '' })
const laorenList = ref([])
const list = ref([])
const dialogVisible = ref(false)
const tishi = ref('')

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 2400)
}

async function loadLaoren() {
  const res = await getHuliyuanLaorenList()
  laorenList.value = res.data || []
}

async function load() {
  const res = await getHuliyuanJiankangJianceList(query.laorenId ? { laorenId: query.laorenId } : {})
  list.value = res.data || []
}

function openAdd() {
  Object.assign(form, { laorenId: query.laorenId || '', jianceShijian: '', tiwen: '', gaoya: '', diya: '', xinlv: '', xueyang: '', xuetang: '', qingkuang: '' })
  dialogVisible.value = true
}

function toNull(value) {
  return value === '' ? null : value
}

function formatDateTime(value) {
  if (!value) return null
  return value.replace('T', ' ') + (value.length === 16 ? ':00' : '')
}

async function save() {
  try {
    await addHuliyuanJiankangJiance({
      ...form,
      jianceShijian: formatDateTime(form.jianceShijian),
      tiwen: toNull(form.tiwen),
      gaoya: toNull(form.gaoya),
      diya: toNull(form.diya),
      xinlv: toNull(form.xinlv),
      xueyang: toNull(form.xueyang),
      xuetang: toNull(form.xuetang)
    })
    dialogVisible.value = false
    await load()
    show('健康监测记录已保存')
  } catch (error) {
    show(error.message)
  }
}

onMounted(async () => {
  await loadLaoren()
  await load()
})
</script>

<style scoped>
.caozuo-tishi { margin-top: 12px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.kong { color: #94a3b8; text-align: center; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(720px, 100%); display: grid; gap: 18px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel h3 { margin: 0; }
.modal-form { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 14px; }
.modal-form label { display: grid; gap: 6px; color: #334155; font-weight: 700; }
.modal-form input, .modal-form select, .modal-form textarea { width: 100%; border: 1px solid #cbd5e1; border-radius: 6px; padding: 9px 10px; background: #f8fafc; }
.modal-form textarea { min-height: 86px; resize: vertical; }
.modal-form .full { grid-column: 1 / -1; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.modal-actions button.plain { border: 1px solid #cbd5e1; color: #334155; background: #fff; }
@media (max-width: 760px) { .modal-form { grid-template-columns: 1fr; } }
</style>
