<template>
  <section class="zhanwei-page laoren-xiugai-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>老人信息变更审核</h2>
      <p>审核家属提交的老人联系方式、家庭住址、备注和头像变更申请。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="chaxunqu">
      <label><span>老人姓名</span><input v-model.trim="query.laorenXingming" placeholder="老人姓名" /></label>
      <label><span>家属姓名</span><input v-model.trim="query.jiashuXingming" placeholder="家属姓名" /></label>
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
            <th>老人</th><th>家属</th><th>联系电话</th><th>家庭住址</th><th>头像</th><th>状态</th><th>申请时间</th><th>审核人</th><th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in list" :key="item.id">
            <td>{{ item.laorenXingming }}</td>
            <td>{{ item.jiashuXingming }}</td>
            <td>{{ item.xinLianxiDianhua || '-' }}</td>
            <td class="ellipsis">{{ item.xinJiatingZhuzhi || '-' }}</td>
            <td>
              <img class="avatar-mini" :src="avatarUrl(item.xinTouxiang || item.yuanTouxiang)" alt="老人头像" @error="fallbackAvatar" />
            </td>
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
          <tr v-if="!list.length"><td colspan="9" class="kong">暂无老人信息变更申请</td></tr>
        </tbody>
      </table>
    </div>

    <div v-if="detailVisible" class="modal-mask">
      <div class="modal-panel wide">
        <h3>变更申请详情</h3>
        <div class="compare-grid">
          <div class="avatar-compare">
            <div>
              <span>原头像</span>
              <img :src="avatarUrl(detail.yuanTouxiang)" alt="原头像" @error="fallbackAvatar" />
            </div>
            <div>
              <span>新头像</span>
              <img :src="avatarUrl(detail.xinTouxiang || detail.yuanTouxiang)" alt="新头像" @error="fallbackAvatar" />
            </div>
          </div>
          <dl class="detail-list">
            <div><dt>老人姓名</dt><dd>{{ detail.laorenXingming }}</dd></div>
            <div><dt>家属姓名</dt><dd>{{ detail.jiashuXingming }}</dd></div>
            <div><dt>原联系电话</dt><dd>{{ detail.yuanLianxiDianhua || '-' }}</dd></div>
            <div><dt>新联系电话</dt><dd>{{ detail.xinLianxiDianhua || '-' }}</dd></div>
            <div><dt>原家庭住址</dt><dd>{{ detail.yuanJiatingZhuzhi || '-' }}</dd></div>
            <div><dt>新家庭住址</dt><dd>{{ detail.xinJiatingZhuzhi || '-' }}</dd></div>
            <div><dt>原备注</dt><dd>{{ detail.yuanBeizhu || '-' }}</dd></div>
            <div><dt>新备注</dt><dd>{{ detail.xinBeizhu || '-' }}</dd></div>
            <div><dt>修改说明</dt><dd>{{ detail.xiugaiShuoming || '-' }}</dd></div>
            <div><dt>审核状态</dt><dd>{{ detail.shenqingZhuangtai }}</dd></div>
            <div><dt>审核意见</dt><dd>{{ detail.shenheYijian || '-' }}</dd></div>
            <div><dt>申请时间</dt><dd>{{ detail.chuangjianShijian || '-' }}</dd></div>
          </dl>
        </div>
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
  deleteAdminLaorenXiugaiShenqing,
  getAdminLaorenXiugaiShenqingDetail,
  getAdminLaorenXiugaiShenqingList,
  passAdminLaorenXiugaiShenqing,
  rejectAdminLaorenXiugaiShenqing
} from '../../api/admin'

const avatarFallback = '/uploads/avatar/user-default.jpg'
const query = reactive({ laorenXingming: '', jiashuXingming: '', shenqingZhuangtai: '' })
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

function avatarUrl(url) {
  return url || avatarFallback
}

function fallbackAvatar(event) {
  if (event.target.src.includes(avatarFallback)) return
  event.target.src = avatarFallback
}

async function loadList() {
  const res = await getAdminLaorenXiugaiShenqingList(query)
  list.value = res.data || []
}

function resetQuery() {
  Object.assign(query, { laorenXingming: '', jiashuXingming: '', shenqingZhuangtai: '' })
  loadList()
}

async function openDetail(item) {
  const res = await getAdminLaorenXiugaiShenqingDetail(item.id)
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
    await passAdminLaorenXiugaiShenqing(payload)
    show('已审核通过，老人档案已更新')
  } else {
    await rejectAdminLaorenXiugaiShenqing(payload)
    show('已驳回，老人档案未修改')
  }
  shenheVisible.value = false
  await loadList()
}

async function removeShenqing(item) {
  if (!window.confirm(`确认删除 ${item.laorenXingming} 的信息变更申请吗？`)) return
  await deleteAdminLaorenXiugaiShenqing(item.id)
  await loadList()
  show('申请记录已删除')
}

onMounted(loadList)
</script>

<style scoped>
.laoren-xiugai-page { gap: 20px; }
.caozuo-tishi { margin-top: 12px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.ellipsis { max-width: 240px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.avatar-mini { width: 44px; height: 44px; border-radius: 8px; object-fit: cover; background: #eef5f1; }
.kong { color: #94a3b8; text-align: center; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(560px, 100%); display: grid; gap: 16px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel.wide { width: min(980px, 100%); }
.modal-panel h3 { margin: 0; }
.modal-hint { margin: 0; color: #64748b; }
.modal-panel label { display: grid; gap: 8px; color: #334155; font-weight: 700; }
.modal-panel textarea { min-height: 110px; border: 1px solid #cbd5e1; border-radius: 6px; padding: 10px; background: #f8fafc; resize: vertical; }
.compare-grid { display: grid; gap: 18px; }
.avatar-compare { display: flex; gap: 16px; flex-wrap: wrap; }
.avatar-compare div { display: grid; gap: 8px; color: #64748b; }
.avatar-compare img { width: 112px; height: 112px; border-radius: 14px; object-fit: cover; background: #eef5f1; }
.detail-list { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 10px 18px; margin: 0; }
.detail-list div { display: grid; gap: 5px; }
.detail-list dt { color: #64748b; font-size: 13px; }
.detail-list dd { margin: 0; color: #1f2937; line-height: 1.6; word-break: break-word; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.modal-actions button.plain { border: 1px solid #cbd5e1; color: #334155; background: #fff; }
@media (max-width: 720px) { .detail-list { grid-template-columns: 1fr; } }
</style>
