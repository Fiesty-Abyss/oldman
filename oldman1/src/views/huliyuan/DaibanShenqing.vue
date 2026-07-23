<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">护理员端</p>
      <h2>代办外出退住</h2>
      <p>护理员只能为自己负责的老人代提交外出申请和退住申请，申请提交后等待管理员审批。</p>
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
      <button type="button" @click="openWaichu">代办外出申请</button>
      <button type="button" @click="openTuizhu">代办退住申请</button>
    </div>

    <div class="shenqing-grid">
      <div class="shenqing-card">
        <h3>外出申请</h3>
        <div class="biaoge-wrap">
          <table>
            <thead>
              <tr><th>老人</th><th>申请人</th><th>外出事由</th><th>外出时间</th><th>预计回院</th><th>陪同人</th><th>审批状态</th><th>审批意见</th></tr>
            </thead>
            <tbody>
              <tr v-for="item in waichuList" :key="item.id">
                <td>{{ item.laorenXingming }}</td>
                <td>{{ item.shenqingRenLeixing || '历史数据' }} / {{ item.shenqingRenXingming || '-' }}</td>
                <td>{{ item.waichuShiyou }}</td>
                <td>{{ item.waichuShijian }}</td>
                <td>{{ item.yujiHuiyuanShijian || '-' }}</td>
                <td>{{ item.peitongRen || '-' }}</td>
                <td>{{ item.shenpiZhuangtai }}</td>
                <td>{{ item.shenpiYijian || '-' }}</td>
              </tr>
              <tr v-if="!waichuList.length"><td colspan="8" class="kong">暂无外出申请</td></tr>
            </tbody>
          </table>
        </div>
      </div>

      <div class="shenqing-card">
        <h3>退住申请</h3>
        <div class="biaoge-wrap">
          <table>
            <thead>
              <tr><th>老人</th><th>申请人</th><th>退住类型</th><th>退住原因</th><th>计划退住</th><th>审批状态</th><th>审批意见</th></tr>
            </thead>
            <tbody>
              <tr v-for="item in tuizhuList" :key="item.id">
                <td>{{ item.laorenXingming }}</td>
                <td>{{ item.shenqingRenLeixing || '历史数据' }} / {{ item.shenqingRenXingming || '-' }}</td>
                <td>{{ item.tuizhuLeixing }}</td>
                <td>{{ item.tuizhuYuanyin || '-' }}</td>
                <td>{{ item.tuizhuShijian }}</td>
                <td>{{ item.shenpiZhuangtai }}</td>
                <td>{{ item.shenpiYijian || '-' }}</td>
              </tr>
              <tr v-if="!tuizhuList.length"><td colspan="7" class="kong">暂无退住申请</td></tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div v-if="waichuDialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>代办外出申请</h3>
        <div class="modal-form">
          <label>
            <span>老人</span>
            <select v-model="waichuForm.laorenId">
              <option value="">请选择老人</option>
              <option v-for="item in laorenList" :key="item.laorenId" :value="item.laorenId">{{ item.laorenXingming }}</option>
            </select>
          </label>
          <label><span>外出时间</span><input v-model="waichuForm.waichuShijian" type="datetime-local" /></label>
          <label><span>预计回院</span><input v-model="waichuForm.yujiHuiyuanShijian" type="datetime-local" /></label>
          <label><span>陪同人</span><input v-model="waichuForm.peitongRen" /></label>
          <label><span>陪同电话</span><input v-model="waichuForm.peitongDianhua" /></label>
          <label class="full"><span>外出事由</span><textarea v-model="waichuForm.waichuShiyou"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveWaichu">提交申请</button>
          <button type="button" class="plain" @click="waichuDialogVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="tuizhuDialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>代办退住申请</h3>
        <div class="modal-form">
          <label>
            <span>老人</span>
            <select v-model="tuizhuForm.laorenId">
              <option value="">请选择老人</option>
              <option v-for="item in laorenList" :key="item.laorenId" :value="item.laorenId">{{ item.laorenXingming }}</option>
            </select>
          </label>
          <label>
            <span>退住类型</span>
            <select v-model="tuizhuForm.tuizhuLeixing"><option>正常退住</option><option>死亡退住</option><option>保留床位</option></select>
          </label>
          <label><span>计划退住</span><input v-model="tuizhuForm.tuizhuShijian" type="date" /></label>
          <label class="full"><span>退住原因</span><textarea v-model="tuizhuForm.tuizhuYuanyin"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveTuizhu">提交申请</button>
          <button type="button" class="plain" @click="tuizhuDialogVisible = false">取消</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { addHuliyuanTuizhu, addHuliyuanWaichu, getHuliyuanLaorenList, getHuliyuanTuizhuList, getHuliyuanWaichuList } from '../../api/huliyuan'

const query = reactive({ laorenId: '' })
const waichuForm = reactive({ laorenId: '', waichuShiyou: '', waichuShijian: '', yujiHuiyuanShijian: '', peitongRen: '', peitongDianhua: '' })
const tuizhuForm = reactive({ laorenId: '', tuizhuLeixing: '正常退住', tuizhuYuanyin: '', tuizhuShijian: '' })
const laorenList = ref([])
const waichuList = ref([])
const tuizhuList = ref([])
const waichuDialogVisible = ref(false)
const tuizhuDialogVisible = ref(false)
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
  const [waichu, tuizhu] = await Promise.all([
    getHuliyuanWaichuList(params),
    getHuliyuanTuizhuList(params)
  ])
  waichuList.value = waichu.data || []
  tuizhuList.value = tuizhu.data || []
}

function openWaichu() {
  Object.assign(waichuForm, { laorenId: query.laorenId || '', waichuShiyou: '', waichuShijian: '', yujiHuiyuanShijian: '', peitongRen: '', peitongDianhua: '' })
  waichuDialogVisible.value = true
}

function openTuizhu() {
  Object.assign(tuizhuForm, { laorenId: query.laorenId || '', tuizhuLeixing: '正常退住', tuizhuYuanyin: '', tuizhuShijian: '' })
  tuizhuDialogVisible.value = true
}

async function saveWaichu() {
  try {
    await addHuliyuanWaichu({ ...waichuForm, waichuShijian: formatDateTime(waichuForm.waichuShijian), yujiHuiyuanShijian: formatDateTime(waichuForm.yujiHuiyuanShijian) })
    waichuDialogVisible.value = false
    await loadAll()
    show('外出申请已提交')
  } catch (error) {
    show(error.message)
  }
}

function formatDateTime(value) {
  if (!value) return null
  return value.replace('T', ' ') + (value.length === 16 ? ':00' : '')
}

async function saveTuizhu() {
  try {
    await addHuliyuanTuizhu({ ...tuizhuForm, tuizhuShijian: tuizhuForm.tuizhuShijian || null })
    tuizhuDialogVisible.value = false
    await loadAll()
    show('退住申请已提交')
  } catch (error) {
    show(error.message)
  }
}

onMounted(async () => {
  await loadLaoren()
  await loadAll()
})
</script>

<style scoped>
.caozuo-tishi { margin-top: 12px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.kong { color: #94a3b8; text-align: center; }
.shenqing-grid { display: grid; gap: 18px; }
.shenqing-card { display: grid; gap: 12px; }
.shenqing-card h3 { margin: 0; }
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
