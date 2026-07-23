<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">护理员端</p>
      <h2>用药执行记录</h2>
      <p>查询自己负责老人的用药计划，并登记服药执行状态。</p>
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
      <button type="button" @click="loadAll">查询</button>
      <button type="button" @click="openAdd">新增服药记录</button>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead>
          <tr><th>老人</th><th>药品</th><th>规格</th><th>用法</th><th>用量</th><th>用药时间</th><th>计划状态</th></tr>
        </thead>
        <tbody>
          <tr v-for="item in jihuaList" :key="item.id">
            <td>{{ item.laorenXingming }}</td>
            <td>{{ item.yaopinMingcheng }}</td>
            <td>{{ item.guige }}</td>
            <td>{{ item.yongfa }}</td>
            <td>{{ item.meiciYongliang }}</td>
            <td>{{ item.yongyaoShijian }}</td>
            <td>{{ item.zhuangtai }}</td>
          </tr>
          <tr v-if="!jihuaList.length"><td colspan="7" class="kong">暂无用药计划</td></tr>
        </tbody>
      </table>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead>
          <tr><th>老人</th><th>药品</th><th>计划时间</th><th>实际时间</th><th>执行状态</th><th>备注</th></tr>
        </thead>
        <tbody>
          <tr v-for="item in jiluList" :key="item.id">
            <td>{{ item.laorenXingming }}</td>
            <td>{{ item.yaopinMingcheng }}</td>
            <td>{{ item.jihuaShijian }}</td>
            <td>{{ item.shijiShijian || '-' }}</td>
            <td>{{ item.zhixingZhuangtai }}</td>
            <td>{{ item.beizhu || '-' }}</td>
          </tr>
          <tr v-if="!jiluList.length"><td colspan="6" class="kong">暂无服药记录</td></tr>
        </tbody>
      </table>
    </div>

    <div v-if="dialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>新增服药执行记录</h3>
        <div class="modal-form">
          <label>
            <span>用药计划</span>
            <select v-model="form.jihuaId">
              <option value="">请选择用药计划</option>
              <option v-for="item in jihuaList" :key="item.id" :value="item.id">{{ item.laorenXingming }} - {{ item.yaopinMingcheng }}</option>
            </select>
          </label>
          <label><span>计划服药时间</span><input v-model="form.jihuaShijian" type="datetime-local" /></label>
          <label><span>实际服药时间</span><input v-model="form.shijiShijian" type="datetime-local" /></label>
          <label>
            <span>执行状态</span>
            <select v-model="form.zhixingZhuangtai"><option>已服</option><option>漏服</option><option>拒服</option><option>异常</option></select>
          </label>
          <label class="full"><span>备注</span><textarea v-model="form.beizhu"></textarea></label>
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
import { addHuliyuanFuyaoJilu, getHuliyuanFuyaoJiluList, getHuliyuanLaorenList, getHuliyuanYongyaoJihuaList } from '../../api/huliyuan'

const query = reactive({ laorenId: '' })
const form = reactive({ jihuaId: '', jihuaShijian: '', shijiShijian: '', zhixingZhuangtai: '已服', beizhu: '' })
const laorenList = ref([])
const jihuaList = ref([])
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

async function loadAll() {
  const params = query.laorenId ? { laorenId: query.laorenId } : {}
  const [jihua, jilu] = await Promise.all([
    getHuliyuanYongyaoJihuaList(params),
    getHuliyuanFuyaoJiluList(params)
  ])
  jihuaList.value = jihua.data || []
  jiluList.value = jilu.data || []
}

function openAdd() {
  Object.assign(form, { jihuaId: '', jihuaShijian: '', shijiShijian: '', zhixingZhuangtai: '已服', beizhu: '' })
  dialogVisible.value = true
}

async function save() {
  try {
    await addHuliyuanFuyaoJilu({ ...form, jihuaShijian: formatDateTime(form.jihuaShijian), shijiShijian: formatDateTime(form.shijiShijian) })
    dialogVisible.value = false
    await loadAll()
    show('服药记录已保存')
  } catch (error) {
    show(error.message)
  }
}

function formatDateTime(value) {
  if (!value) return null
  return value.replace('T', ' ') + (value.length === 16 ? ':00' : '')
}

onMounted(async () => {
  await loadLaoren()
  await loadAll()
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
