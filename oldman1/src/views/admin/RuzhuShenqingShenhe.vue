<template>
  <section class="zhanwei-page admin-ruzhu-shenqing-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>入住申请审核</h2>
      <p>审核对外提交的在线入住申请；通过后创建基础档案，正式入住和床位安排仍在老人入住登记中办理。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="chaxunqu">
      <label><span>老人姓名</span><input v-model.trim="query.laorenXingming" placeholder="老人姓名" /></label>
      <label><span>家属手机号</span><input v-model.trim="query.jiashuShouji" placeholder="家属手机号" /></label>
      <label>
        <span>审核状态</span>
        <select v-model="query.shenqingZhuangtai">
          <option value="">全部</option>
          <option>待审核</option>
          <option>已通过</option>
          <option>已驳回</option>
        </select>
      </label>
      <button type="button" @click="loadList">查询</button>
      <button type="button" class="plain" @click="resetQuery">重置</button>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead>
          <tr>
            <th>老人</th><th>性别</th><th>年龄</th><th>联系电话</th><th>家属</th><th>家属手机号</th>
            <th>护理需求</th><th>期望入住</th><th>状态</th><th>申请时间</th><th>审核人</th><th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in list" :key="item.id">
            <td>{{ item.laorenXingming }}</td>
            <td>{{ item.xingbie || '-' }}</td>
            <td>{{ item.nianling || '-' }}</td>
            <td>{{ item.lianxiDianhua || '-' }}</td>
            <td>{{ item.jiashuXingming }} / {{ item.yuLaorenGuanxi || '-' }}</td>
            <td>{{ item.jiashuShouji }}</td>
            <td>{{ item.huliXuqiu || '-' }}</td>
            <td>{{ item.qiwangRuzhuShijian || '-' }}</td>
            <td>{{ item.shenqingZhuangtai }}</td>
            <td>{{ item.chuangjianShijian }}</td>
            <td>{{ item.shenheRenXingming || '-' }}</td>
            <td class="caozuo">
              <button type="button" @click="openDetail(item)">详情</button>
              <button v-if="item.shenqingZhuangtai === '待审核'" type="button" @click="openShenhe('pass', item)">通过</button>
              <button v-if="item.shenqingZhuangtai === '待审核'" type="button" @click="openShenhe('reject', item)">驳回</button>
              <button type="button" @click="removeShenqing(item)">删除</button>
            </td>
          </tr>
          <tr v-if="!list.length"><td colspan="12" class="kong">暂无入住申请</td></tr>
        </tbody>
      </table>
    </div>

    <div v-if="detailVisible" class="modal-mask">
      <div class="modal-panel wide">
        <h3>申请详情</h3>
        <dl class="detail-list">
          <div><dt>老人姓名</dt><dd>{{ detail.laorenXingming }}</dd></div>
          <div><dt>性别</dt><dd>{{ detail.xingbie || '-' }}</dd></div>
          <div><dt>出生日期</dt><dd>{{ detail.chushengRiqi || '-' }}</dd></div>
          <div><dt>年龄</dt><dd>{{ detail.nianling || '-' }}</dd></div>
          <div><dt>身份证号</dt><dd>{{ detail.shenfenzhenghao || '-' }}</dd></div>
          <div><dt>联系电话</dt><dd>{{ detail.lianxiDianhua || '-' }}</dd></div>
          <div><dt>家庭住址</dt><dd>{{ detail.jiatingZhuzhi || '-' }}</dd></div>
          <div><dt>血型</dt><dd>{{ detail.xuexing || '-' }}</dd></div>
          <div><dt>过敏史</dt><dd>{{ detail.guominshi || '-' }}</dd></div>
          <div><dt>既往病史</dt><dd>{{ detail.jiwangbingshi || '-' }}</dd></div>
          <div><dt>护理需求</dt><dd>{{ detail.huliXuqiu || '-' }}</dd></div>
          <div><dt>期望入住</dt><dd>{{ detail.qiwangRuzhuShijian || '-' }}</dd></div>
          <div><dt>家属姓名</dt><dd>{{ detail.jiashuXingming }}</dd></div>
          <div><dt>家属手机号</dt><dd>{{ detail.jiashuShouji }}</dd></div>
          <div><dt>与老人关系</dt><dd>{{ detail.yuLaorenGuanxi || '-' }}</dd></div>
          <div><dt>审核状态</dt><dd>{{ detail.shenqingZhuangtai }}</dd></div>
          <div><dt>审核意见</dt><dd>{{ detail.shenheYijian || '-' }}</dd></div>
          <div><dt>备注</dt><dd>{{ detail.beizhu || '-' }}</dd></div>
        </dl>
        <div class="modal-actions">
          <button type="button" class="plain" @click="detailVisible = false">关闭</button>
        </div>
      </div>
    </div>

    <div v-if="shenheVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>{{ shenheType === 'pass' ? '审核通过' : '审核驳回' }}</h3>
        <p class="modal-hint">{{ currentItem?.laorenXingming }} / {{ currentItem?.jiashuXingming }}</p>
        <label>
          <span>审核意见</span>
          <textarea v-model.trim="shenheYijian" placeholder="请输入审核意见"></textarea>
        </label>
        <div class="modal-actions">
          <button type="button" @click="submitShenhe">确认</button>
          <button type="button" class="plain" @click="shenheVisible = false">取消</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import {
  getAdminRuzhuShenqingDetail,
  getAdminRuzhuShenqingList,
  deleteAdminRuzhuShenqing,
  passAdminRuzhuShenqing,
  rejectAdminRuzhuShenqing
} from '../../api/admin'

const query = reactive({ laorenXingming: '', jiashuShouji: '', shenqingZhuangtai: '' })
const list = ref([])
const detail = ref({})
const currentItem = ref(null)
const detailVisible = ref(false)
const shenheVisible = ref(false)
const shenheType = ref('pass')
const shenheYijian = ref('')
const tishi = ref('')

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 2400)
}

async function loadList() {
  const res = await getAdminRuzhuShenqingList(query)
  list.value = res.data || []
}

function resetQuery() {
  Object.assign(query, { laorenXingming: '', jiashuShouji: '', shenqingZhuangtai: '' })
  loadList()
}

async function openDetail(item) {
  const res = await getAdminRuzhuShenqingDetail(item.id)
  detail.value = res.data || {}
  detailVisible.value = true
}

function openShenhe(type, item) {
  shenheType.value = type
  currentItem.value = item
  shenheYijian.value = ''
  shenheVisible.value = true
}

async function submitShenhe() {
  const payload = { id: currentItem.value.id, shenheYijian: shenheYijian.value }
  if (shenheType.value === 'pass') {
    await passAdminRuzhuShenqing(payload)
    show('已审核通过，基础档案已准备')
  } else {
    await rejectAdminRuzhuShenqing(payload)
    show('已驳回入住申请')
  }
  shenheVisible.value = false
  await loadList()
}

async function removeShenqing(item) {
  if (!window.confirm(`确认删除 ${item.laorenXingming} 的入住申请记录吗？`)) return
  try {
    await deleteAdminRuzhuShenqing(item.id)
    await loadList()
    show('入住申请记录已删除')
  } catch (error) {
    show(error.message || '删除失败')
  }
}

onMounted(loadList)
</script>

<style scoped>
.admin-ruzhu-shenqing-page { gap: 20px; }
.caozuo-tishi { margin-top: 12px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.kong { color: #94a3b8; text-align: center; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(560px, 100%); display: grid; gap: 16px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel.wide { width: min(920px, 100%); }
.modal-panel h3 { margin: 0; }
.modal-hint { margin: 0; color: #64748b; }
.modal-panel label { display: grid; gap: 8px; color: #334155; font-weight: 700; }
.modal-panel textarea { min-height: 110px; border: 1px solid #cbd5e1; border-radius: 6px; padding: 10px; background: #f8fafc; resize: vertical; }
.detail-list { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 10px 18px; margin: 0; }
.detail-list div { display: grid; gap: 5px; }
.detail-list dt { color: #64748b; font-size: 13px; }
.detail-list dd { margin: 0; color: #1f2937; line-height: 1.6; word-break: break-word; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.modal-actions button.plain { border: 1px solid #cbd5e1; color: #334155; background: #fff; }
@media (max-width: 720px) { .detail-list { grid-template-columns: 1fr; } }
</style>
