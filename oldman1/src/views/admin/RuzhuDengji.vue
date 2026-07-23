<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>老人入住登记</h2>
      <p>维护老人基础资料和默认家属联系人，并可为老人办理入住登记。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="chaxunqu">
      <button type="button" @click="openShenqingDialog">选择已通过入住申请</button>
      <label><span>老人编号</span><input v-model="form.laorenBianhao" placeholder="LR202606007" /></label>
      <label><span>姓名</span><input v-model="form.xingming" placeholder="老人姓名" /></label>
      <label><span>性别</span><select v-model="form.xingbie"><option>男</option><option>女</option></select></label>
      <label><span>年龄</span><input v-model="form.nianling" type="number" /></label>
      <label><span>老人类型</span><select v-model="form.laorenLeixing"><option>自理老人</option><option>护理老人</option><option>重点关注</option></select></label>
      <label><span>入住状态</span><select v-model="form.ruzhuZhuangtai"><option>未入住</option><option>在住</option><option>外出</option><option>退住</option></select></label>
      <label><span>家庭住址</span><input v-model="form.jiatingZhuzhi" /></label>
      <label><span>家属姓名</span><input v-model="form.jiashuXingming" placeholder="新增时可填" /></label>
      <label><span>家属手机</span><input v-model="form.jiashuShouji" /></label>
      <label class="avatar-field">
        <span>老人头像</span>
        <img :src="avatarUrl(form.touxiang)" alt="老人头像" @error="fallbackAvatar" />
        <input type="file" accept=".jpg,.jpeg,.png,.webp,image/*" @change="uploadAdminAvatar" />
      </label>
      <button type="button" @click="save">{{ form.id ? '修改老人' : '新增老人' }}</button>
      <button type="button" class="plain" @click="reset">清空</button>
    </div>

    <div class="chaxunqu">
      <label>
        <span>入住老人</span>
        <select v-model="ruzhuForm.laorenId">
          <option value="">请选择老人</option>
          <option v-for="item in keRuzhuLaorenOptions" :key="item.id" :value="item.id">{{ item.xingming }} / {{ item.ruzhuZhuangtai }}</option>
        </select>
      </label>
      <label>
        <span>房间</span>
        <select v-model="ruzhuForm.fangjianId" @change="onRuzhuFangjianChange">
          <option value="">请选择房间</option>
          <option v-for="item in fangjianOptions" :key="item.id" :value="item.id">{{ item.fangjianhao }} / {{ item.louceng }}层</option>
        </select>
      </label>
      <label>
        <span>空闲床位</span>
        <select v-model="ruzhuForm.chuangweiId" @change="syncFangjianByChuangwei">
          <option value="">请选择空闲床位</option>
          <option v-for="item in kongxianChuangweiOptions" :key="item.id" :value="item.id">{{ item.fangjianhao }} / {{ item.chuangweihao }}</option>
        </select>
      </label>
      <label><span>入住日期</span><input v-model="ruzhuForm.ruzhuShijian" type="date" /></label>
      <label><span>合同到期</span><input v-model="ruzhuForm.hetongDaoqi" type="date" /></label>
      <label><span>来源申请ID</span><input v-model="ruzhuForm.shenqingId" placeholder="可选" disabled /></label>
      <button type="button" @click="submitRuzhu">办理入住</button>
    </div>

    <div class="chaxunqu laoren-query">
      <label>
        <span>关键词</span>
        <input v-model.trim="queryForm.keyword" placeholder="请输入老人姓名或编号" @keyup.enter="searchLaoren" />
      </label>
      <label>
        <span>老人类型</span>
        <select v-model="queryForm.laorenLeixing">
          <option value="">全部</option>
          <option v-for="item in laorenLeixingOptions" :key="item" :value="item">{{ item }}</option>
        </select>
      </label>
      <label>
        <span>入住状态</span>
        <select v-model="queryForm.ruzhuZhuangtai">
          <option value="">全部</option>
          <option v-for="item in ruzhuZhuangtaiOptions" :key="item" :value="item">{{ item }}</option>
        </select>
      </label>
      <button type="button" @click="searchLaoren">查询</button>
      <button type="button" class="plain" @click="resetLaorenQuery">重置</button>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead><tr><th>ID</th><th>头像</th><th>编号</th><th>姓名</th><th>性别</th><th>年龄</th><th>类型</th><th>入住状态</th><th>操作</th></tr></thead>
        <tbody>
          <tr v-for="item in pagedLaorenList" :key="item.id">
            <td>{{ item.id }}</td><td><img class="table-avatar" :src="avatarUrl(item.touxiang)" alt="老人头像" @error="fallbackAvatar" /></td><td>{{ item.laorenBianhao || '未编号' }}</td><td>{{ item.xingming }}</td><td>{{ item.xingbie }}</td><td>{{ item.nianling }}</td><td>{{ item.laorenLeixing || '未设置' }}</td><td>{{ item.ruzhuZhuangtai || '未入住' }}</td>
            <td><button @click="edit(item)">编辑</button><button @click="remove(item.id)">删除</button><button @click="chooseRuzhu(item)">入住</button></td>
          </tr>
          <tr v-if="!filteredLaorenList.length"><td colspan="9" class="kong">暂无符合条件的老人信息</td></tr>
        </tbody>
      </table>
    </div>

    <div v-if="filteredLaorenList.length" class="pagination-bar">
      <span>共 {{ filteredLaorenList.length }} 条</span>
      <button type="button" class="plain" :disabled="currentPage === 1" @click="goPage(currentPage - 1)">上一页</button>
      <button
        v-for="page in totalPages"
        :key="page"
        type="button"
        :class="{ active: page === currentPage }"
        @click="goPage(page)"
      >{{ page }}</button>
      <button type="button" class="plain" :disabled="currentPage === totalPages" @click="goPage(currentPage + 1)">下一页</button>
      <span>第 {{ currentPage }} / {{ totalPages }} 页</span>
    </div>

    <div v-if="shenqingDialogVisible" class="modal-mask">
      <div class="modal-panel wide">
        <h3>选择已通过入住申请</h3>
        <div class="chaxunqu inline">
          <label><span>老人姓名</span><input v-model.trim="shenqingQuery.laorenXingming" placeholder="老人姓名" /></label>
          <label><span>家属手机号</span><input v-model.trim="shenqingQuery.jiashuShouji" placeholder="家属手机号" /></label>
          <button type="button" @click="loadShenqingList">查询</button>
          <button type="button" class="plain" @click="resetShenqingQuery">重置</button>
        </div>
        <div class="biaoge-wrap">
          <table>
            <thead>
              <tr>
                <th>申请ID</th><th>老人</th><th>性别</th><th>出生日期/年龄</th><th>联系电话</th>
                <th>家属</th><th>家属手机号</th><th>护理需求</th><th>期望入住</th><th>申请状态</th><th>办理状态</th><th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in shenqingList" :key="item.id">
                <td>{{ item.id }}</td>
                <td>{{ item.laorenXingming }}</td>
                <td>{{ item.xingbie || '-' }}</td>
                <td>{{ item.chushengRiqi || '-' }} / {{ item.nianling || '-' }}</td>
                <td>{{ item.lianxiDianhua || '-' }}</td>
                <td>{{ item.jiashuXingming }} / {{ item.yuLaorenGuanxi || '-' }}</td>
                <td>{{ item.jiashuShouji }}</td>
                <td>{{ item.huliXuqiu || '-' }}</td>
                <td>{{ item.qiwangRuzhuShijian || '-' }}</td>
                <td>{{ item.shenqingZhuangtai }}</td>
                <td>{{ item.banliZhuangtai }}</td>
                <td><button type="button" @click="bringShenqing(item)">带入</button></td>
              </tr>
              <tr v-if="!shenqingList.length"><td colspan="12" class="kong">暂无可办理的已通过入住申请</td></tr>
            </tbody>
          </table>
        </div>
        <div class="modal-actions">
          <button type="button" class="plain" @click="shenqingDialogVisible = false">关闭</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { getAdminRuzhuShenqingBring, getAdminRuzhuShenqingPassedList } from '../../api/admin'
import { getFangjianOptions, getKongxianChuangweiOptions } from '../../api/chuangwei'
import { addLaorenXinxi, deleteLaorenXinxi, getLaorenOptions, getLaorenXinxiList, ruzhuDengji, updateLaorenXinxi } from '../../api/laoren'
import { uploadImage } from '../../api/wenjian'

const avatarFallback = '/uploads/avatar/user-default.jpg'
const list = ref([])
const currentPage = ref(1)
const pageSize = 5
const laorenOptions = ref([])
const fangjianOptions = ref([])
const kongxianChuangweiOptions = ref([])
const form = reactive({ id: '', laorenBianhao: '', xingming: '', xingbie: '男', nianling: '', laorenLeixing: '自理老人', ruzhuZhuangtai: '未入住', jiatingZhuzhi: '', jiashuXingming: '', jiashuShouji: '', guanxi: '子女', touxiang: '' })
const ruzhuForm = reactive({ laorenId: '', fangjianId: '', chuangweiId: '', ruzhuShijian: '', hetongDaoqi: '', ruzhuLeixing: '正式入住', yajin: 0, shenqingId: '' })
const shenqingDialogVisible = ref(false)
const shenqingQuery = reactive({ laorenXingming: '', jiashuShouji: '' })
const shenqingList = ref([])
const tishi = ref('')
const queryForm = reactive({ keyword: '', laorenLeixing: '', ruzhuZhuangtai: '' })
const activeQuery = reactive({ keyword: '', laorenLeixing: '', ruzhuZhuangtai: '' })
const laorenLeixingOptions = ['自理老人', '护理老人', '重点关注', '待完善']
const ruzhuZhuangtaiOptions = ['未入住', '在住', '外出', '退住']
const filteredLaorenList = computed(() => {
  const keyword = String(activeQuery.keyword || '').trim().toLowerCase()
  const leixing = String(activeQuery.laorenLeixing || '')
  const zhuangtai = String(activeQuery.ruzhuZhuangtai || '')
  return list.value.filter((item) => {
    const bianhao = String(item.laorenBianhao || '').toLowerCase()
    const xingming = String(item.xingming || '').toLowerCase()
    const itemLeixing = String(item.laorenLeixing || '')
    const itemZhuangtai = String(item.ruzhuZhuangtai || '')
    const keywordMatched = !keyword || bianhao.includes(keyword) || xingming.includes(keyword)
    const leixingMatched = !leixing || itemLeixing === leixing
    const zhuangtaiMatched = !zhuangtai || itemZhuangtai === zhuangtai
    return keywordMatched && leixingMatched && zhuangtaiMatched
  })
})
const totalPages = computed(() => Math.max(1, Math.ceil(filteredLaorenList.value.length / pageSize)))
const pagedLaorenList = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredLaorenList.value.slice(start, start + pageSize)
})
const keRuzhuLaorenOptions = computed(() => laorenOptions.value.filter((item) => ['未入住', '待完善'].includes(item.ruzhuZhuangtai)))

async function load() {
  const res = await getLaorenXinxiList()
  list.value = res.data || []
  clampCurrentPage()
}

async function loadOptions() {
  const [laorenRes, fangjianRes] = await Promise.all([getLaorenOptions(), getFangjianOptions()])
  laorenOptions.value = laorenRes.data || []
  fangjianOptions.value = fangjianRes.data || []
  await loadKongxianChuangwei()
}

async function loadKongxianChuangwei() {
  const params = ruzhuForm.fangjianId ? { fangjianId: ruzhuForm.fangjianId } : {}
  const res = await getKongxianChuangweiOptions(params)
  kongxianChuangweiOptions.value = res.data || []
}

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 2400)
}

function clampCurrentPage() {
  if (currentPage.value > totalPages.value) currentPage.value = totalPages.value
  if (currentPage.value < 1) currentPage.value = 1
}

function goPage(page) {
  currentPage.value = Math.min(Math.max(page, 1), totalPages.value)
}

function searchLaoren() {
  Object.assign(activeQuery, {
    keyword: String(queryForm.keyword || '').trim(),
    laorenLeixing: queryForm.laorenLeixing,
    ruzhuZhuangtai: queryForm.ruzhuZhuangtai
  })
  currentPage.value = 1
}

function resetLaorenQuery() {
  Object.assign(queryForm, { keyword: '', laorenLeixing: '', ruzhuZhuangtai: '' })
  Object.assign(activeQuery, { keyword: '', laorenLeixing: '', ruzhuZhuangtai: '' })
  currentPage.value = 1
}

function reset() {
  Object.assign(form, { id: '', laorenBianhao: '', xingming: '', xingbie: '男', nianling: '', laorenLeixing: '自理老人', ruzhuZhuangtai: '未入住', jiatingZhuzhi: '', jiashuXingming: '', jiashuShouji: '', guanxi: '子女', touxiang: '' })
  Object.assign(ruzhuForm, { laorenId: '', fangjianId: '', chuangweiId: '', ruzhuShijian: '', hetongDaoqi: '', ruzhuLeixing: '正式入住', yajin: 0, shenqingId: '' })
}

function avatarUrl(url) {
  return url || avatarFallback
}

function fallbackAvatar(event) {
  if (event.target.src.includes(avatarFallback)) return
  event.target.src = avatarFallback
}

async function uploadAdminAvatar(event) {
  const file = event.target.files?.[0]
  if (!file) return
  try {
    const res = await uploadImage(file, 'avatar')
    form.touxiang = res.data?.url || ''
    show('头像已上传')
  } catch (error) {
    show(error.message || '头像上传失败')
  } finally {
    event.target.value = ''
  }
}

function edit(item) {
  Object.assign(form, item)
}

function chooseRuzhu(item) {
  if (!['未入住', '待完善'].includes(item.ruzhuZhuangtai)) {
    show('该老人当前状态不能重复办理入住')
    return
  }
  ruzhuForm.laorenId = item.id
  ruzhuForm.shenqingId = ''
}

async function save() {
  if (!String(form.xingming || '').trim()) {
    show('老人姓名不能为空')
    return
  }
  if (form.jiashuXingming && !String(form.jiashuShouji || '').trim()) {
    show('填写家属姓名时手机号不能为空')
    return
  }
  const isEdit = Boolean(form.id)
  if (isEdit) await updateLaorenXinxi(form)
  else await addLaorenXinxi(form)
  reset()
  if (!isEdit) currentPage.value = 1
  await load()
  await loadOptions()
}

async function remove(id) {
  await deleteLaorenXinxi(id)
  await load()
}

async function submitRuzhu() {
  if (!ruzhuForm.laorenId) {
    show('请选择入住老人')
    return
  }
  if (!ruzhuForm.chuangweiId) {
    show('请选择空闲床位')
    return
  }
  if (!ruzhuForm.ruzhuShijian) {
    show('入住日期不能为空')
    return
  }
  await ruzhuDengji(ruzhuForm)
  if (ruzhuForm.shenqingId) {
    await loadShenqingList()
  }
  Object.assign(ruzhuForm, { laorenId: '', fangjianId: '', chuangweiId: '', ruzhuShijian: '', hetongDaoqi: '', ruzhuLeixing: '正式入住', yajin: 0, shenqingId: '' })
  await load()
  await loadOptions()
  show('入住办理成功')
}

async function onRuzhuFangjianChange() {
  ruzhuForm.chuangweiId = ''
  await loadKongxianChuangwei()
}

function syncFangjianByChuangwei() {
  const chuangwei = kongxianChuangweiOptions.value.find((item) => String(item.id) === String(ruzhuForm.chuangweiId))
  if (chuangwei) {
    ruzhuForm.fangjianId = chuangwei.fangjianId
  }
}

async function openShenqingDialog() {
  shenqingDialogVisible.value = true
  await loadShenqingList()
}

async function loadShenqingList() {
  const res = await getAdminRuzhuShenqingPassedList(shenqingQuery)
  shenqingList.value = res.data || []
}

function resetShenqingQuery() {
  Object.assign(shenqingQuery, { laorenXingming: '', jiashuShouji: '' })
  loadShenqingList()
}

async function bringShenqing(item) {
  const res = await getAdminRuzhuShenqingBring(item.id)
  const data = res.data || {}
  const laoren = list.value.find((one) => String(one.id) === String(data.laorenId)) || {}
  Object.assign(form, {
    id: data.laorenId || '',
    laorenBianhao: laoren.laorenBianhao || '',
    xingming: data.laorenXingming || '',
    xingbie: data.xingbie || '男',
    nianling: data.nianling || '',
    laorenLeixing: data.huliXuqiu ? '护理老人' : '自理老人',
    ruzhuZhuangtai: '未入住',
    jiatingZhuzhi: data.jiatingZhuzhi || '',
    jiashuXingming: data.jiashuXingming || '',
    jiashuShouji: data.jiashuShouji || '',
    guanxi: data.yuLaorenGuanxi || '子女',
    touxiang: laoren.touxiang || ''
  })
  ruzhuForm.laorenId = data.laorenId || ''
  ruzhuForm.shenqingId = data.id || ''
  shenqingDialogVisible.value = false
  show('已带入申请资料，请选择房间、床位和入住日期后办理入住')
}

onMounted(async () => {
  await load()
  await loadOptions()
})
</script>

<style scoped>
.caozuo-tishi { margin-top: 12px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.avatar-field img { width: 64px; height: 64px; border-radius: 12px; object-fit: cover; background: #eef5f1; }
.avatar-field input { padding: 8px 10px; background: #fff; }
.table-avatar { width: 42px; height: 42px; border-radius: 10px; object-fit: cover; background: #eef5f1; }
.pagination-bar { display: flex; align-items: center; justify-content: flex-end; flex-wrap: wrap; gap: 8px; margin-top: 12px; color: #475569; }
.pagination-bar button { min-width: 36px; min-height: 34px; border: 1px solid #cbd5e1; border-radius: 6px; padding: 0 10px; color: #334155; background: #fff; font-weight: 700; }
.pagination-bar button.active { border-color: #0f766e; color: #fff; background: #0f766e; }
.pagination-bar button:disabled { opacity: .45; cursor: not-allowed; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(560px, 100%); display: grid; gap: 16px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel.wide { width: min(1100px, 100%); }
.modal-panel h3 { margin: 0; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.modal-actions button.plain { border: 1px solid #cbd5e1; color: #334155; background: #fff; }
.kong { color: #94a3b8; text-align: center; }
</style>
