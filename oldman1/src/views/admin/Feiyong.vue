<template>
  <section class="zhanwei-page admin-feiyong-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>费用账单管理</h2>
      <p>维护老人费用账单，登记现金、银行卡或转账等线下缴费记录；家属端仅可查询。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="feiyong-kuai">
      <div class="kuai-heading">
        <h3>费用账单</h3>
        <button type="button" @click="openZhangdan()">新增账单</button>
      </div>
      <div class="chaxunqu">
        <label><span>老人姓名</span><input v-model.trim="zhangdanQuery.laorenXingming" placeholder="老人姓名" /></label>
        <label><span>账单月份</span><input v-model.trim="zhangdanQuery.zhangdanYuefen" placeholder="2026-06" /></label>
        <label>
          <span>缴费状态</span>
          <select v-model="zhangdanQuery.zhuangtai">
            <option value="">全部</option>
            <option>待缴费</option>
            <option>部分缴费</option>
            <option>已结清</option>
            <option>已取消</option>
          </select>
        </label>
        <button type="button" @click="loadZhangdan">查询</button>
        <button type="button" class="plain" @click="resetZhangdanQuery">重置</button>
      </div>

      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr>
              <th>老人</th><th>月份</th><th>床位费</th><th>护理费</th><th>药品费</th><th>其他</th>
              <th>应收</th><th>已缴</th><th>未缴</th><th>状态</th><th>备注</th><th>创建时间</th><th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in zhangdanList" :key="item.id" :class="{ selected: currentZhangdan?.id === item.id }">
              <td>{{ item.laorenXingming }}</td>
              <td>{{ item.zhangdanYuefen }}</td>
              <td>{{ money(item.chuangweiFei) }}</td>
              <td>{{ money(item.huliFei) }}</td>
              <td>{{ money(item.yaopinFei) }}</td>
              <td>{{ money(item.qitaFei) }}</td>
              <td>{{ money(item.zongjine) }}</td>
              <td>{{ money(item.yijiaoJine) }}</td>
              <td>{{ money(item.weiJiaoJine) }}</td>
              <td>{{ item.zhuangtai }}</td>
              <td>{{ item.beizhu || '-' }}</td>
              <td>{{ item.shengchengShijian }}</td>
              <td class="caozuo">
                <button type="button" @click="selectZhangdan(item)">缴费记录</button>
                <button type="button" @click="openDetail(item)">详情</button>
                <button type="button" @click="openZhangdan(item)" :disabled="item.zhuangtai === '已取消'">编辑</button>
                <button type="button" @click="openJiaofei(item)" :disabled="!canJiaofei(item)">登记缴费</button>
                <button type="button" @click="removeZhangdan(item)" :disabled="item.zhuangtai === '已取消'">删除</button>
              </td>
            </tr>
            <tr v-if="!zhangdanList.length"><td colspan="13" class="kong">暂无费用账单</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="feiyong-kuai">
      <div class="kuai-heading">
        <div>
          <h3>线下缴费记录</h3>
          <p>{{ currentZhangdan ? `${currentZhangdan.laorenXingming} / ${currentZhangdan.zhangdanYuefen}` : '默认展示全部缴费记录，可从账单行选择查看单条账单记录。' }}</p>
        </div>
        <button v-if="currentZhangdan && canJiaofei(currentZhangdan)" type="button" @click="openJiaofei(currentZhangdan)">登记缴费</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>老人</th><th>账单月份</th><th>缴费金额</th><th>缴费方式</th><th>缴费时间</th><th>登记人</th><th>备注</th><th>操作</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in jiaofeiList" :key="item.id">
              <td>{{ item.laorenXingming }}</td>
              <td>{{ item.zhangdanYuefen }}</td>
              <td>{{ money(item.jiaofeiJine) }}</td>
              <td>{{ item.jiaofeiFangshi || '-' }}</td>
              <td>{{ item.jiaofeiShijian }}</td>
              <td>{{ item.caozuorenXingming || '-' }}</td>
              <td>{{ item.beizhu || '-' }}</td>
              <td><button type="button" @click="removeJiaofei(item)">删除</button></td>
            </tr>
            <tr v-if="!jiaofeiList.length"><td colspan="8" class="kong">暂无缴费记录</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="zhangdanDialogVisible" class="modal-mask">
      <div class="modal-panel wide">
        <h3>{{ zhangdanForm.id ? '编辑账单' : '新增账单' }}</h3>
        <div class="modal-form">
          <label>
            <span>老人</span>
            <select v-model="zhangdanForm.laorenId">
              <option value="">请选择老人</option>
              <option v-for="item in laorenList" :key="item.id" :value="item.id">{{ item.xingming }} / {{ item.ruzhuZhuangtai }}</option>
            </select>
          </label>
          <label><span>账单月份</span><input v-model.trim="zhangdanForm.zhangdanYuefen" placeholder="2026-06" /></label>
          <label><span>床位费</span><input v-model="zhangdanForm.chuangweiFei" type="number" min="0" step="0.01" /></label>
          <label><span>护理费</span><input v-model="zhangdanForm.huliFei" type="number" min="0" step="0.01" /></label>
          <label><span>药品费</span><input v-model="zhangdanForm.yaopinFei" type="number" min="0" step="0.01" /></label>
          <label><span>其他费用</span><input v-model="zhangdanForm.qitaFei" type="number" min="0" step="0.01" /></label>
          <label><span>应收金额</span><input :value="formZongjine" disabled /></label>
          <label class="full"><span>备注</span><textarea v-model="zhangdanForm.beizhu"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveZhangdan">保存</button>
          <button type="button" class="plain" @click="zhangdanDialogVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="jiaofeiDialogVisible" class="modal-mask">
      <div class="modal-panel wide">
        <h3>登记线下缴费</h3>
        <p class="modal-hint">{{ currentZhangdan?.laorenXingming }} / {{ currentZhangdan?.zhangdanYuefen }}，未缴金额 {{ money(currentZhangdan?.weiJiaoJine) }}</p>
        <div class="modal-form">
          <label><span>缴费金额</span><input v-model="jiaofeiForm.jiaofeiJine" type="number" min="0" step="0.01" /></label>
          <label>
            <span>缴费方式</span>
            <select v-model="jiaofeiForm.jiaofeiFangshi">
              <option>现金</option>
              <option>银行卡</option>
              <option>微信转账</option>
              <option>支付宝转账</option>
              <option>其他</option>
            </select>
          </label>
          <label><span>缴费时间</span><input v-model="jiaofeiForm.jiaofeiShijian" type="datetime-local" /></label>
          <label class="full"><span>备注</span><textarea v-model="jiaofeiForm.beizhu"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveJiaofei">登记</button>
          <button type="button" class="plain" @click="jiaofeiDialogVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="detailDialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>账单详情</h3>
        <dl class="detail-list">
          <div><dt>老人</dt><dd>{{ detail.laorenXingming }}</dd></div>
          <div><dt>账单月份</dt><dd>{{ detail.zhangdanYuefen }}</dd></div>
          <div><dt>应收金额</dt><dd>{{ money(detail.zongjine) }}</dd></div>
          <div><dt>已缴金额</dt><dd>{{ money(detail.yijiaoJine) }}</dd></div>
          <div><dt>未缴金额</dt><dd>{{ money(detail.weiJiaoJine) }}</dd></div>
          <div><dt>缴费状态</dt><dd>{{ detail.zhuangtai }}</dd></div>
          <div><dt>备注</dt><dd>{{ detail.beizhu || '-' }}</dd></div>
        </dl>
        <div class="modal-actions">
          <button type="button" class="plain" @click="detailDialogVisible = false">关闭</button>
        </div>
      </div>
    </div>

  </section>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import {
  addAdminFeiyongZhangdan,
  addAdminJiaofei,
  deleteAdminFeiyongZhangdan,
  deleteAdminJiaofei,
  getAdminFeiyongZhangdanDetail,
  getAdminFeiyongZhangdanList,
  getAdminJiaofeiList,
  updateAdminFeiyongZhangdan
} from '../../api/admin'
import { getLaorenXinxiList } from '../../api/laoren'

const zhangdanQuery = reactive({ laorenXingming: '', zhangdanYuefen: '', zhuangtai: '' })
const zhangdanList = ref([])
const jiaofeiList = ref([])
const laorenList = ref([])
const currentZhangdan = ref(null)
const tishi = ref('')

const zhangdanDialogVisible = ref(false)
const jiaofeiDialogVisible = ref(false)
const detailDialogVisible = ref(false)
const detail = ref({})
const zhangdanForm = reactive(blankZhangdan())
const jiaofeiForm = reactive(blankJiaofei())

const formZongjine = computed(() => {
  return money(Number(zhangdanForm.chuangweiFei || 0) + Number(zhangdanForm.huliFei || 0) + Number(zhangdanForm.yaopinFei || 0) + Number(zhangdanForm.qitaFei || 0))
})

function blankZhangdan() {
  return { id: '', laorenId: '', zhangdanYuefen: '', chuangweiFei: 0, huliFei: 0, yaopinFei: 0, qitaFei: 0, beizhu: '' }
}

function blankJiaofei() {
  return { zhangdanId: '', jiaofeiJine: '', jiaofeiFangshi: '现金', jiaofeiShijian: '', beizhu: '' }
}

function money(value) {
  const number = Number(value || 0)
  return number.toFixed(2)
}

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 2400)
}

function canJiaofei(item) {
  return item && item.zhuangtai !== '已取消' && item.zhuangtai !== '已结清' && Number(item.weiJiaoJine || 0) > 0
}

async function loadLaoren() {
  const res = await getLaorenXinxiList()
  laorenList.value = res.data || []
}

async function loadZhangdan() {
  const res = await getAdminFeiyongZhangdanList(zhangdanQuery)
  zhangdanList.value = res.data || []
  if (currentZhangdan.value) {
    currentZhangdan.value = zhangdanList.value.find((item) => item.id === currentZhangdan.value.id) || null
  }
}

async function loadJiaofei() {
  const params = currentZhangdan.value ? { zhangdanId: currentZhangdan.value.id } : {}
  const res = await getAdminJiaofeiList(params)
  jiaofeiList.value = res.data || []
}

async function loadAll() {
  await loadZhangdan()
  await loadJiaofei()
}

function resetZhangdanQuery() {
  Object.assign(zhangdanQuery, { laorenXingming: '', zhangdanYuefen: '', zhuangtai: '' })
  loadZhangdan()
}

function openZhangdan(item) {
  Object.assign(zhangdanForm, blankZhangdan(), item ? {
    id: item.id,
    laorenId: item.laorenId,
    zhangdanYuefen: item.zhangdanYuefen,
    chuangweiFei: item.chuangweiFei,
    huliFei: item.huliFei,
    yaopinFei: item.yaopinFei,
    qitaFei: item.qitaFei,
    beizhu: item.beizhu || ''
  } : {})
  zhangdanDialogVisible.value = true
}

async function saveZhangdan() {
  try {
    if (!zhangdanForm.laorenId) {
      show('请选择老人')
      return
    }
    if ([zhangdanForm.chuangweiFei, zhangdanForm.huliFei, zhangdanForm.yaopinFei, zhangdanForm.qitaFei].some((value) => Number(value || 0) < 0)) {
      show('金额不能为负数')
      return
    }
    if (zhangdanForm.id) await updateAdminFeiyongZhangdan(zhangdanForm)
    else await addAdminFeiyongZhangdan(zhangdanForm)
    zhangdanDialogVisible.value = false
    await loadAll()
    show('账单已保存')
  } catch (error) {
    show(error.message)
  }
}

async function removeZhangdan(item) {
  if (!window.confirm(`确认取消 ${item.laorenXingming} ${item.zhangdanYuefen} 的账单？`)) return
  await deleteAdminFeiyongZhangdan(item.id)
  if (currentZhangdan.value?.id === item.id) currentZhangdan.value = null
  await loadAll()
  show('账单已逻辑删除')
}

async function openDetail(item) {
  const res = await getAdminFeiyongZhangdanDetail(item.id)
  detail.value = res.data || {}
  detailDialogVisible.value = true
}

async function selectZhangdan(item) {
  currentZhangdan.value = item
  await loadJiaofei()
}

function openJiaofei(item) {
  currentZhangdan.value = item
  Object.assign(jiaofeiForm, blankJiaofei(), { zhangdanId: item.id })
  jiaofeiDialogVisible.value = true
}

function formatDateTime(value) {
  if (!value) return null
  return value.replace('T', ' ') + (value.length === 16 ? ':00' : '')
}

async function saveJiaofei() {
  try {
    const jine = Number(jiaofeiForm.jiaofeiJine || 0)
    const weiJiao = Number(currentZhangdan.value?.weiJiaoJine || 0)
    if (jine <= 0) {
      show('缴费金额必须大于0')
      return
    }
    if (jine > weiJiao) {
      show('缴费金额不能大于未缴金额')
      return
    }
    await addAdminJiaofei({ ...jiaofeiForm, jiaofeiShijian: formatDateTime(jiaofeiForm.jiaofeiShijian) })
    jiaofeiDialogVisible.value = false
    await loadAll()
    show('线下缴费已登记')
  } catch (error) {
    show(error.message)
  }
}

async function removeJiaofei(item) {
  if (!window.confirm('确认删除该缴费记录并重新计算账单状态？')) return
  await deleteAdminJiaofei(item.id)
  await loadAll()
  show('缴费记录已删除，账单状态已重算')
}

onMounted(async () => {
  await loadLaoren()
  await loadAll()
})
</script>

<style scoped>
.admin-feiyong-page { gap: 22px; }
.caozuo-tishi { margin-top: 12px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.feiyong-kuai { display: grid; gap: 14px; border: 1px solid #dce5df; border-radius: 8px; padding: 18px; background: #fff; }
.kuai-heading { display: flex; align-items: center; justify-content: space-between; gap: 16px; }
.kuai-heading h3 { margin: 0; }
.kuai-heading p { margin: 6px 0 0; color: #64748b; }
.selected { background: #f0fdfa; }
.caozuo { display: flex; flex-wrap: wrap; gap: 6px; min-width: 280px; }
.caozuo button:disabled, .kuai-heading button:disabled { opacity: .45; cursor: not-allowed; }
.kong { color: #94a3b8; text-align: center; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(560px, 100%); display: grid; gap: 16px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel.wide { width: min(780px, 100%); }
.modal-panel h3 { margin: 0; }
.modal-hint { margin: 0; color: #64748b; }
.modal-form { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 14px; }
.modal-form label { display: grid; gap: 6px; color: #334155; font-weight: 700; }
.modal-form input, .modal-form select, .modal-form textarea { width: 100%; border: 1px solid #cbd5e1; border-radius: 6px; padding: 9px 10px; background: #f8fafc; }
.modal-form input:disabled { color: #475569; background: #e2e8f0; }
.modal-form textarea { min-height: 86px; resize: vertical; }
.modal-form .full { grid-column: 1 / -1; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.modal-actions button.plain { border: 1px solid #cbd5e1; color: #334155; background: #fff; }
.detail-list { display: grid; gap: 10px; margin: 0; }
.detail-list div { display: grid; grid-template-columns: 110px 1fr; gap: 12px; border-bottom: 1px solid #e2e8f0; padding-bottom: 8px; }
.detail-list dt { color: #64748b; font-weight: 700; }
.detail-list dd { margin: 0; color: #0f172a; }
@media (max-width: 760px) { .modal-form { grid-template-columns: 1fr; } }
</style>
