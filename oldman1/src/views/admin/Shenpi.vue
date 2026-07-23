<template>
  <section class="zhanwei-page admin-shenpi-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>外出与退住审批</h2>
      <p>审批护理员代提交的外出和退住申请，并联动老人入住状态与床位状态。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="shenpi-kuai">
      <div class="kuai-heading">
        <div>
          <h3>外出申请</h3>
          <p>通过后床位标记为外出；回院登记后床位恢复为有人。</p>
        </div>
      </div>
      <div class="chaxunqu">
        <label><span>老人姓名</span><input v-model.trim="waichuQuery.laorenXingming" placeholder="老人姓名" /></label>
        <label>
          <span>审批状态</span>
          <select v-model="waichuQuery.shenpiZhuangtai">
            <option value="">全部</option>
            <option>待审批</option>
            <option>通过</option>
            <option>不通过</option>
            <option>已回院</option>
          </select>
        </label>
        <button type="button" @click="loadWaichu">查询</button>
        <button type="button" class="plain" @click="resetWaichu">重置</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>老人</th><th>护理员</th><th>申请人</th><th>事由</th><th>外出时间</th><th>预计回院</th><th>床位</th><th>状态</th><th>审批意见</th><th>操作</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in waichuList" :key="item.id">
              <td>{{ item.laorenXingming }}</td>
              <td>{{ item.huliRenyuanXingming || '-' }}</td>
              <td>{{ item.shenqingRenLeixing || '历史数据' }} / {{ item.shenqingRenXingming || '-' }}</td>
              <td>{{ item.waichuShiyou }}</td>
              <td>{{ item.waichuShijian }}</td>
              <td>{{ item.yujiHuiyuanShijian || '-' }}</td>
              <td>{{ item.chuangweihao || '-' }} / {{ item.chuangweiZhuangtai || '-' }}</td>
              <td>{{ item.shenpiZhuangtai }}</td>
              <td>{{ item.shenpiYijian || '-' }}</td>
              <td>
                <button v-if="item.shenpiZhuangtai === '待审批'" @click="openDialog('waichu-pass', item)">通过</button>
                <button v-if="item.shenpiZhuangtai === '待审批'" @click="openDialog('waichu-reject', item)">驳回</button>
                <button v-if="item.shenpiZhuangtai === '通过'" @click="huiyuan(item)">回院</button>
                <button @click="removeWaichu(item)">删除</button>
              </td>
            </tr>
            <tr v-if="!waichuList.length"><td colspan="10" class="kong">暂无外出申请</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="shenpi-kuai">
      <div class="kuai-heading">
        <div>
          <h3>退住申请</h3>
          <p>通过后释放当前床位，结束床位使用记录，并更新老人入住状态为退住。</p>
        </div>
      </div>
      <div class="chaxunqu">
        <label><span>老人姓名</span><input v-model.trim="tuizhuQuery.laorenXingming" placeholder="老人姓名" /></label>
        <label>
          <span>审批状态</span>
          <select v-model="tuizhuQuery.shenpiZhuangtai">
            <option value="">全部</option>
            <option>待审批</option>
            <option>通过</option>
            <option>不通过</option>
          </select>
        </label>
        <button type="button" @click="loadTuizhu">查询</button>
        <button type="button" class="plain" @click="resetTuizhu">重置</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>老人</th><th>护理员</th><th>申请人</th><th>类型</th><th>原因</th><th>计划退住</th><th>床位</th><th>状态</th><th>审批意见</th><th>操作</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in tuizhuList" :key="item.id">
              <td>{{ item.laorenXingming }}</td>
              <td>{{ item.huliRenyuanXingming || '-' }}</td>
              <td>{{ item.shenqingRenLeixing || '历史数据' }} / {{ item.shenqingRenXingming || '-' }}</td>
              <td>{{ item.tuizhuLeixing }}</td>
              <td>{{ item.tuizhuYuanyin || '-' }}</td>
              <td>{{ item.tuizhuShijian }}</td>
              <td>{{ item.chuangweihao || '-' }} / {{ item.chuangweiZhuangtai || '-' }}</td>
              <td>{{ item.shenpiZhuangtai }}</td>
              <td>{{ item.shenpiYijian || '-' }}</td>
              <td>
                <button v-if="item.shenpiZhuangtai === '待审批'" @click="openDialog('tuizhu-pass', item)">通过</button>
                <button v-if="item.shenpiZhuangtai === '待审批'" @click="openDialog('tuizhu-reject', item)">驳回</button>
                <button @click="removeTuizhu(item)">删除</button>
              </td>
            </tr>
            <tr v-if="!tuizhuList.length"><td colspan="10" class="kong">暂无退住申请</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="dialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>{{ dialogTitle }}</h3>
        <label>
          <span>审批意见</span>
          <textarea v-model="shenpiYijian" placeholder="请输入审批意见"></textarea>
        </label>
        <div class="modal-actions">
          <button type="button" @click="submitDialog">确认</button>
          <button type="button" class="plain" @click="dialogVisible = false">取消</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { deleteAdminTuizhu, deleteAdminWaichu, getAdminTuizhuList, getAdminWaichuList, huiyuanAdminWaichu, passAdminTuizhu, passAdminWaichu, rejectAdminTuizhu, rejectAdminWaichu } from '../../api/admin'

const waichuQuery = reactive({ laorenXingming: '', shenpiZhuangtai: '' })
const tuizhuQuery = reactive({ laorenXingming: '', shenpiZhuangtai: '' })
const waichuList = ref([])
const tuizhuList = ref([])
const dialogVisible = ref(false)
const dialogType = ref('')
const currentItem = ref(null)
const shenpiYijian = ref('')
const dialogTitle = ref('')
const tishi = ref('')

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 2400)
}

async function loadWaichu() {
  const res = await getAdminWaichuList(waichuQuery)
  waichuList.value = res.data || []
}

async function loadTuizhu() {
  const res = await getAdminTuizhuList(tuizhuQuery)
  tuizhuList.value = res.data || []
}

function resetWaichu() {
  Object.assign(waichuQuery, { laorenXingming: '', shenpiZhuangtai: '' })
  loadWaichu()
}

function resetTuizhu() {
  Object.assign(tuizhuQuery, { laorenXingming: '', shenpiZhuangtai: '' })
  loadTuizhu()
}

function openDialog(type, item) {
  dialogType.value = type
  currentItem.value = item
  shenpiYijian.value = ''
  dialogTitle.value = type.includes('pass') ? '审批通过' : '审批驳回'
  dialogVisible.value = true
}

async function submitDialog() {
  const payload = { id: currentItem.value.id, shenpiYijian: shenpiYijian.value }
  if (dialogType.value === 'waichu-pass') await passAdminWaichu(payload)
  if (dialogType.value === 'waichu-reject') await rejectAdminWaichu(payload)
  if (dialogType.value === 'tuizhu-pass') await passAdminTuizhu(payload)
  if (dialogType.value === 'tuizhu-reject') await rejectAdminTuizhu(payload)
  dialogVisible.value = false
  await Promise.all([loadWaichu(), loadTuizhu()])
  show('审批已处理')
}

async function huiyuan(item) {
  await huiyuanAdminWaichu({ id: item.id })
  await loadWaichu()
  show('已登记回院')
}

async function removeWaichu(item) {
  if (!window.confirm(`确认删除 ${item.laorenXingming} 的外出申请记录吗？`)) return
  try {
    await deleteAdminWaichu(item.id)
    await loadWaichu()
    show('外出申请记录已删除')
  } catch (error) {
    show(error.message || '删除失败')
  }
}

async function removeTuizhu(item) {
  if (!window.confirm(`确认删除 ${item.laorenXingming} 的退住申请记录吗？`)) return
  try {
    await deleteAdminTuizhu(item.id)
    await loadTuizhu()
    show('退住申请记录已删除')
  } catch (error) {
    show(error.message || '删除失败')
  }
}

onMounted(async () => {
  await Promise.all([loadWaichu(), loadTuizhu()])
})
</script>

<style scoped>
.admin-shenpi-page { gap: 22px; }
.caozuo-tishi { margin-top: 12px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.shenpi-kuai { display: grid; gap: 14px; border: 1px solid #dce5df; border-radius: 8px; padding: 18px; background: #fff; }
.kuai-heading { display: flex; align-items: center; justify-content: space-between; gap: 16px; }
.kuai-heading h3 { margin: 0; }
.kuai-heading p { margin: 6px 0 0; color: #64748b; line-height: 1.6; }
.kong { color: #94a3b8; text-align: center; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(520px, 100%); display: grid; gap: 16px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel h3 { margin: 0; }
.modal-panel label { display: grid; gap: 8px; color: #334155; font-weight: 700; }
.modal-panel textarea { min-height: 110px; border: 1px solid #cbd5e1; border-radius: 6px; padding: 10px; background: #f8fafc; resize: vertical; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.modal-actions button.plain { border: 1px solid #cbd5e1; color: #334155; background: #fff; }
</style>
