<template>
  <section class="zhanwei-page jiashu-laoren-page">
    <div class="page-heading">
      <p class="xitong-tag">家属端</p>
      <h2>老人基本信息</h2>
      <p>只展示当前登录家属绑定的老人和家属联系信息；信息变更需提交申请，管理员审核通过后生效。</p>
    </div>

    <div class="chaxunqu">
      <button type="button" @click="loadAll">刷新</button>
    </div>
    <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>

    <div class="xinxi-grid">
      <article v-for="item in list" :key="item.laorenId" class="xinxi-card">
        <div class="laoren-avatar">
          <img :src="avatarUrl(item.touxiang)" :alt="item.laorenXingming" @error="fallbackAvatar" />
        </div>
        <div class="laoren-detail">
          <div class="card-title">
            <h3>{{ item.laorenXingming }}</h3>
            <button type="button" @click="openApply(item)">申请修改信息</button>
          </div>
          <p>编号：{{ item.laorenBianhao || '-' }}</p>
          <p>性别：{{ item.xingbie || '-' }}　年龄：{{ item.nianling || '-' }}</p>
          <p>老人类型：{{ item.laorenLeixing || '-' }}</p>
          <p>入住状态：{{ item.ruzhuZhuangtai || '-' }}</p>
          <p>联系电话：{{ item.lianxiDianhua || '-' }}</p>
          <p>家庭住址：{{ item.jiatingZhuzhi || '-' }}</p>
          <p>备注：{{ item.beizhu || '-' }}</p>
          <div class="jiashu-info">
            <strong>家属信息</strong>
            <span>{{ item.jiashuXingming }} / {{ item.guanxi }} / {{ item.jiashuShouji }}</span>
            <span>{{ item.jiashuDizhi || '-' }}</span>
          </div>
        </div>
      </article>
      <p v-if="!list.length" class="kong">暂无绑定老人信息</p>
    </div>

    <div class="shenqing-section">
      <div class="section-title">
        <h3>信息变更申请记录</h3>
        <button type="button" class="plain" @click="loadShenqingList">刷新记录</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>老人</th><th>新联系电话</th><th>新家庭住址</th><th>新头像</th><th>状态</th><th>申请时间</th><th>审核意见</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in shenqingList" :key="item.id">
              <td>{{ item.laorenXingming }}</td>
              <td>{{ item.xinLianxiDianhua || '-' }}</td>
              <td>{{ item.xinJiatingZhuzhi || '-' }}</td>
              <td><img class="avatar-mini" :src="avatarUrl(item.xinTouxiang || item.yuanTouxiang)" alt="老人头像" @error="fallbackAvatar" /></td>
              <td>{{ item.shenqingZhuangtai }}</td>
              <td>{{ item.chuangjianShijian }}</td>
              <td>{{ item.shenheYijian || '-' }}</td>
            </tr>
            <tr v-if="!shenqingList.length"><td colspan="7" class="kong">暂无信息变更申请</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="applyVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>申请修改老人信息</h3>
        <p class="modal-hint">{{ currentItem?.laorenXingming }} 的变更将在管理员审核通过后生效。</p>
        <div class="apply-avatar">
          <img :src="avatarUrl(applyForm.xinTouxiang || currentItem?.touxiang)" alt="申请头像" @error="fallbackAvatar" />
          <label>
            <span>上传新头像</span>
            <input type="file" accept=".jpg,.jpeg,.png,.webp,image/*" @change="uploadApplyAvatar" />
          </label>
        </div>
        <label><span>联系电话</span><input v-model.trim="applyForm.xinLianxiDianhua" placeholder="联系电话" /></label>
        <label><span>家庭住址</span><input v-model.trim="applyForm.xinJiatingZhuzhi" placeholder="家庭住址" /></label>
        <label><span>备注</span><textarea v-model.trim="applyForm.xinBeizhu" placeholder="老人备注"></textarea></label>
        <label><span>修改说明</span><textarea v-model.trim="applyForm.xiugaiShuoming" placeholder="请简要说明修改原因"></textarea></label>
        <div class="modal-actions">
          <button type="button" @click="submitApply">提交申请</button>
          <button type="button" class="plain" @click="applyVisible = false">取消</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import {
  addJiashuLaorenXiugaiShenqing,
  getJiashuLaorenXinxi,
  getJiashuLaorenXiugaiShenqingList,
  uploadJiashuLaorenXiugaiTouxiang
} from '../../api/jiashu'

const avatarFallback = '/uploads/avatar/user-default.jpg'
const list = ref([])
const shenqingList = ref([])
const currentItem = ref(null)
const applyVisible = ref(false)
const applyForm = reactive({
  laorenId: null,
  xinLianxiDianhua: '',
  xinJiatingZhuzhi: '',
  xinBeizhu: '',
  xinTouxiang: '',
  xiugaiShuoming: ''
})
const tishi = ref('')

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 2600)
}

async function loadList() {
  const res = await getJiashuLaorenXinxi()
  list.value = res.data || []
}

async function loadShenqingList() {
  const res = await getJiashuLaorenXiugaiShenqingList()
  shenqingList.value = res.data || []
}

async function loadAll() {
  await Promise.all([loadList(), loadShenqingList()])
}

function avatarUrl(url) {
  return url || avatarFallback
}

function fallbackAvatar(event) {
  if (event.target.src.includes(avatarFallback)) return
  event.target.src = avatarFallback
}

function openApply(item) {
  currentItem.value = item
  Object.assign(applyForm, {
    laorenId: item.laorenId,
    xinLianxiDianhua: item.lianxiDianhua || '',
    xinJiatingZhuzhi: item.jiatingZhuzhi || '',
    xinBeizhu: item.beizhu || '',
    xinTouxiang: '',
    xiugaiShuoming: ''
  })
  applyVisible.value = true
}

async function uploadApplyAvatar(event) {
  const file = event.target.files?.[0]
  if (!file) return
  const extension = file.name.split('.').pop()?.toLowerCase()
  if (!['jpg', 'jpeg', 'png', 'webp'].includes(extension)) {
    show('只允许上传 jpg、jpeg、png、webp 格式图片')
    event.target.value = ''
    return
  }
  if (file.size > 5 * 1024 * 1024) {
    show('头像大小不能超过 5MB')
    event.target.value = ''
    return
  }
  try {
    const res = await uploadJiashuLaorenXiugaiTouxiang(applyForm.laorenId, file)
    applyForm.xinTouxiang = res.data?.url || ''
    show('头像已上传，审核通过后生效')
  } catch (error) {
    show(error.message || '头像上传失败')
  } finally {
    event.target.value = ''
  }
}

async function submitApply() {
  try {
    await addJiashuLaorenXiugaiShenqing({ ...applyForm })
    applyVisible.value = false
    await loadShenqingList()
    show('修改申请已提交，等待管理员审核')
  } catch (error) {
    show(error.message || '提交失败')
  }
}

onMounted(loadAll)
</script>

<style scoped>
.jiashu-laoren-page { gap: 18px; }
.xinxi-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 14px; }
.xinxi-card { display: grid; grid-template-columns: 132px minmax(0, 1fr); gap: 18px; align-items: start; border: 1px solid #dce5df; border-radius: 8px; padding: 18px; background: #fff; }
.laoren-avatar { width: 120px; height: 120px; overflow: hidden; border-radius: 16px; background: #eef5f1; }
.laoren-avatar img { width: 100%; height: 100%; display: block; object-fit: cover; }
.laoren-detail { min-width: 0; display: grid; gap: 8px; }
.card-title { display: flex; align-items: center; justify-content: space-between; gap: 10px; flex-wrap: wrap; }
.card-title h3, .xinxi-card p { margin: 0; }
.card-title button { min-height: 34px; border-radius: 6px; padding: 0 12px; color: #fff; background: #0f766e; font-weight: 700; }
.xinxi-card p { color: #475569; line-height: 1.7; }
.jiashu-info { display: grid; gap: 6px; margin-top: 8px; border-top: 1px solid #e2e8f0; padding-top: 12px; color: #334155; }
.jiashu-info span { color: #64748b; }
.shenqing-section { display: grid; gap: 12px; border: 1px solid #dce5df; border-radius: 8px; padding: 16px; background: #fff; }
.section-title { display: flex; align-items: center; justify-content: space-between; gap: 10px; }
.section-title h3 { margin: 0; }
.section-title button { min-height: 34px; border-radius: 6px; padding: 0 12px; }
.avatar-mini { width: 44px; height: 44px; border-radius: 8px; object-fit: cover; background: #eef5f1; }
.caozuo-tishi { margin: 0 0 14px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.kong { color: #94a3b8; text-align: center; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(620px, 100%); max-height: calc(100vh - 40px); overflow: auto; display: grid; gap: 14px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel h3, .modal-hint { margin: 0; }
.modal-hint { color: #64748b; }
.modal-panel label { display: grid; gap: 8px; color: #334155; font-weight: 700; }
.modal-panel input, .modal-panel textarea { border: 1px solid #cbd5e1; border-radius: 6px; padding: 10px; background: #f8fafc; }
.modal-panel textarea { min-height: 86px; resize: vertical; }
.apply-avatar { display: flex; align-items: center; gap: 16px; flex-wrap: wrap; }
.apply-avatar img { width: 112px; height: 112px; border-radius: 14px; object-fit: cover; background: #eef5f1; }
.apply-avatar label { width: fit-content; min-height: 38px; display: inline-flex; align-items: center; border-radius: 6px; padding: 0 14px; color: #fff; background: #0f766e; cursor: pointer; }
.apply-avatar input { display: none; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
button.plain, .modal-actions button.plain { border: 1px solid #cbd5e1; color: #334155; background: #fff; }
@media (max-width: 760px) {
  .xinxi-grid { grid-template-columns: 1fr; }
  .xinxi-card { grid-template-columns: 1fr; }
}
</style>
