<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">护理员端</p>
      <h2>日常护理记录</h2>
      <p>录入和查看自己负责老人的护理记录，护理项目来自老人当前护理级别。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="chaxunqu">
      <label>
        <span>老人</span>
        <select v-model="query.laorenId" @change="loadXiangmu">
          <option value="">全部</option>
          <option v-for="item in laorenList" :key="item.laorenId" :value="item.laorenId">{{ item.laorenXingming }}</option>
        </select>
      </label>
      <button type="button" @click="loadJilu">查询</button>
      <button type="button" @click="openAdd">新增护理记录</button>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead>
          <tr><th>老人</th><th>护理项目</th><th>护理时间</th><th>数量</th><th>内容</th><th>反馈</th><th>状态</th><th>操作</th></tr>
        </thead>
        <tbody>
          <tr v-for="item in jiluList" :key="item.id">
            <td>{{ item.laorenXingming }}</td>
            <td>{{ item.xiangmuMingcheng }}</td>
            <td>{{ item.huliShijian }}</td>
            <td>{{ item.huliShuliang }}</td>
            <td>{{ item.huliNeirong || '-' }}</td>
            <td>{{ item.laorenFankui || '-' }}</td>
            <td>{{ item.zhuangtai }}</td>
            <td><button @click="remove(item.id)">删除</button></td>
          </tr>
          <tr v-if="!jiluList.length"><td colspan="8" class="kong">暂无护理记录</td></tr>
        </tbody>
      </table>
    </div>

    <div v-if="dialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>新增护理记录</h3>
        <div class="modal-form">
          <label>
            <span>老人</span>
            <select v-model="form.laorenId" @change="loadXiangmuForForm">
              <option value="">请选择老人</option>
              <option v-for="item in laorenList" :key="item.laorenId" :value="item.laorenId">{{ item.laorenXingming }}</option>
            </select>
          </label>
          <label>
            <span>护理项目</span>
            <select v-model="form.xiangmuId">
              <option value="">请选择护理项目</option>
              <option v-for="item in xiangmuList" :key="item.id" :value="item.id">{{ item.xiangmuMingcheng }}</option>
            </select>
          </label>
          <label><span>护理时间</span><input v-model="form.huliShijian" type="datetime-local" /></label>
          <label><span>护理数量</span><input v-model="form.huliShuliang" type="number" min="1" /></label>
          <label>
            <span>状态</span>
            <select v-model="form.zhuangtai"><option>已完成</option><option>异常</option></select>
          </label>
          <label class="full"><span>护理内容</span><textarea v-model="form.huliNeirong"></textarea></label>
          <label class="full"><span>老人反馈</span><textarea v-model="form.laorenFankui"></textarea></label>
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
import { addHuliyuanHuliJilu, deleteHuliyuanHuliJilu, getHuliyuanHuliJiluList, getHuliyuanLaorenList, getLaorenHuliXiangmuList } from '../../api/huliyuan'

const query = reactive({ laorenId: '' })
const form = reactive({ laorenId: '', xiangmuId: '', huliShijian: '', huliShuliang: 1, huliNeirong: '', laorenFankui: '', zhuangtai: '已完成' })
const laorenList = ref([])
const xiangmuList = ref([])
const jiluList = ref([])
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

async function loadXiangmu() {
  if (!query.laorenId) return
  const res = await getLaorenHuliXiangmuList(query.laorenId)
  xiangmuList.value = res.data || []
}

async function loadXiangmuForForm() {
  form.xiangmuId = ''
  if (!form.laorenId) {
    xiangmuList.value = []
    return
  }
  const res = await getLaorenHuliXiangmuList(form.laorenId)
  xiangmuList.value = res.data || []
}

async function loadJilu() {
  const params = query.laorenId ? { laorenId: query.laorenId } : {}
  const res = await getHuliyuanHuliJiluList(params)
  jiluList.value = res.data || []
}

function openAdd() {
  Object.assign(form, { laorenId: query.laorenId || '', xiangmuId: '', huliShijian: '', huliShuliang: 1, huliNeirong: '', laorenFankui: '', zhuangtai: '已完成' })
  dialogVisible.value = true
  loadXiangmuForForm()
}

async function save() {
  try {
    await addHuliyuanHuliJilu({ ...form, huliShijian: formatDateTime(form.huliShijian) })
    dialogVisible.value = false
    await loadJilu()
    show('护理记录已保存')
  } catch (error) {
    show(error.message)
  }
}

function formatDateTime(value) {
  if (!value) return null
  return value.replace('T', ' ') + (value.length === 16 ? ':00' : '')
}

async function remove(id) {
  if (!window.confirm('确认删除这条护理记录吗？')) return
  await deleteHuliyuanHuliJilu(id)
  await loadJilu()
  show('护理记录已删除')
}

onMounted(async () => {
  await loadLaoren()
  await loadJilu()
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
