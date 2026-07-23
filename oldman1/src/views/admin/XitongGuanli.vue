<template>
  <section class="zhanwei-page xitong-guanli-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>系统管理</h2>
      <p>维护系统用户、角色和账号关联关系。当前只做基础账号权限维护，不做菜单权限树。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="tabs">
      <button type="button" :class="{ active: activeTab === 'yonghu' }" @click="activeTab = 'yonghu'">用户管理</button>
      <button type="button" :class="{ active: activeTab === 'juese' }" @click="activeTab = 'juese'">角色管理</button>
    </div>

    <section v-if="activeTab === 'yonghu'" class="guanli-kuai">
      <div class="kuai-heading">
        <h3>用户管理</h3>
        <button type="button" @click="openYonghu()">新增用户</button>
      </div>
      <div class="chaxunqu">
        <label><span>账号</span><input v-model.trim="yonghuQuery.dengluZhanghao" placeholder="登录账号" /></label>
        <label><span>姓名</span><input v-model.trim="yonghuQuery.xingming" placeholder="姓名" /></label>
        <label>
          <span>角色</span>
          <select v-model="yonghuQuery.jueseBianma">
            <option value="">全部</option>
            <option v-for="item in jueseList" :key="item.id" :value="item.jueseBianma">{{ item.jueseMingcheng }}</option>
          </select>
        </label>
        <label>
          <span>状态</span>
          <select v-model="yonghuQuery.zhuangtai"><option value="">全部</option><option>启用</option><option>停用</option></select>
        </label>
        <button type="button" @click="loadYonghu">查询</button>
        <button type="button" class="plain" @click="resetYonghuQuery">重置</button>
      </div>

      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>ID</th><th>账号</th><th>姓名</th><th>手机号</th><th>类型</th><th>角色</th><th>状态</th><th>创建时间</th><th>业务关联</th><th>操作</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in yonghuList" :key="item.id">
              <td>{{ item.id }}</td>
              <td>{{ item.dengluZhanghao }}</td>
              <td>{{ item.xingming }}</td>
              <td>{{ item.shouji || '-' }}</td>
              <td>{{ item.yonghuLeixing }}</td>
              <td>{{ item.jueseMingchengList || '-' }}</td>
              <td>{{ item.zhuangtai }}</td>
              <td>{{ item.chuangjianShijian }}</td>
              <td>{{ guanlianText(item) }}</td>
              <td class="caozuo">
                <button type="button" @click="openYonghu(item)">编辑</button>
                <button type="button" @click="toggleYonghuStatus(item)">{{ item.zhuangtai === '启用' ? '停用' : '启用' }}</button>
                <button type="button" @click="openPassword(item)">重置密码</button>
                <button type="button" @click="openRole(item)">分配角色</button>
              </td>
            </tr>
            <tr v-if="!yonghuList.length"><td colspan="10" class="kong">暂无用户数据</td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <section v-if="activeTab === 'juese'" class="guanli-kuai">
      <div class="kuai-heading">
        <div>
          <h3>角色管理</h3>
          <p>当前数据库角色表无状态和删除字段，因此启停、逻辑删除会提示需补字段。</p>
        </div>
        <button type="button" @click="openJuese()">新增角色</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>ID</th><th>角色名称</th><th>角色编码</th><th>状态</th><th>备注</th><th>创建时间</th><th>操作</th></tr></thead>
          <tbody>
            <tr v-for="item in jueseList" :key="item.id">
              <td>{{ item.id }}</td>
              <td>{{ item.jueseMingcheng }}</td>
              <td>{{ item.jueseBianma }}</td>
              <td>{{ item.zhuangtai || '启用' }}</td>
              <td>{{ item.miaoshu || '-' }}</td>
              <td>{{ item.chuangjianShijian }}</td>
              <td class="caozuo">
                <button type="button" @click="openJuese(item)">编辑</button>
                <button type="button" @click="toggleJueseStatus(item)">启停</button>
                <button type="button" @click="removeJuese(item)">删除</button>
              </td>
            </tr>
            <tr v-if="!jueseList.length"><td colspan="7" class="kong">暂无角色数据</td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <div v-if="yonghuDialogVisible" class="modal-mask">
      <div class="modal-panel wide">
        <h3>{{ yonghuForm.id ? '编辑用户' : '新增用户' }}</h3>
        <div class="modal-form">
          <label><span>登录账号</span><input v-model.trim="yonghuForm.dengluZhanghao" /></label>
          <label v-if="!yonghuForm.id"><span>初始密码</span><input v-model="yonghuForm.mima" type="password" /></label>
          <label><span>姓名</span><input v-model.trim="yonghuForm.xingming" /></label>
          <label><span>性别</span><select v-model="yonghuForm.xingbie"><option>男</option><option>女</option></select></label>
          <label><span>手机号</span><input v-model.trim="yonghuForm.shouji" /></label>
          <label><span>邮箱</span><input v-model.trim="yonghuForm.youxiang" /></label>
          <label class="avatar-field">
            <span>头像</span>
            <img :src="avatarUrl(yonghuForm.touxiang)" alt="用户头像" @error="fallbackAvatar" />
            <input type="file" accept=".jpg,.jpeg,.png,.webp,image/*" @change="uploadAvatar" />
          </label>
          <label v-if="!yonghuForm.id"><span>状态</span><select v-model="yonghuForm.zhuangtai"><option>启用</option><option>停用</option></select></label>
          <label>
            <span>关联护理员</span>
            <select v-model="yonghuForm.huliRenyuanId">
              <option value="">不关联</option>
              <option v-for="item in huliOptions" :key="item.id" :value="item.id">{{ item.gonghao }} / {{ item.xingming }}</option>
            </select>
          </label>
          <label>
            <span>关联家属</span>
            <select v-model="yonghuForm.jiashuId">
              <option value="">不关联</option>
              <option v-for="item in jiashuOptions" :key="item.id" :value="item.id">{{ item.xingming }} / {{ item.guanxi }} / {{ item.laorenXingming }}</option>
            </select>
          </label>
          <div v-if="!yonghuForm.id" class="full role-checks">
            <span>分配角色</span>
            <label v-for="item in jueseList" :key="item.id"><input v-model="yonghuForm.jueseIds" type="checkbox" :value="item.id" />{{ item.jueseMingcheng }}</label>
          </div>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveYonghu">保存</button>
          <button type="button" class="plain" @click="yonghuDialogVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="passwordDialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>重置密码</h3>
        <p class="modal-hint">将为 {{ currentYonghu?.xingming }} 重置登录密码。</p>
        <label class="field"><span>新密码</span><input v-model="passwordForm.mima" type="password" /></label>
        <div class="modal-actions">
          <button type="button" @click="savePassword">确认重置</button>
          <button type="button" class="plain" @click="passwordDialogVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="roleDialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>分配角色</h3>
        <p class="modal-hint">{{ currentYonghu?.xingming }} / {{ currentYonghu?.dengluZhanghao }}</p>
        <div class="role-checks">
          <label v-for="item in jueseList" :key="item.id"><input v-model="roleForm.jueseIds" type="checkbox" :value="item.id" />{{ item.jueseMingcheng }}</label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveRole">保存角色</button>
          <button type="button" class="plain" @click="roleDialogVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="jueseDialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>{{ jueseForm.id ? '编辑角色' : '新增角色' }}</h3>
        <div class="modal-form one">
          <label><span>角色编码</span><input v-model.trim="jueseForm.jueseBianma" placeholder="admin / huli / jiashu" /></label>
          <label><span>角色名称</span><input v-model.trim="jueseForm.jueseMingcheng" /></label>
          <label class="full"><span>备注</span><textarea v-model="jueseForm.miaoshu"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveJuese">保存</button>
          <button type="button" class="plain" @click="jueseDialogVisible = false">取消</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import {
  addXitongJuese,
  addXitongYonghu,
  assignXitongYonghuRole,
  changeXitongJueseStatus,
  changeXitongYonghuStatus,
  deleteXitongJuese,
  getHuliRenyuanOptions,
  getJiashuOptions,
  getXitongJueseList,
  getXitongYonghuList,
  resetXitongYonghuPassword,
  updateXitongJuese,
  updateXitongYonghu
} from '../../api/xitong'
import { uploadImage } from '../../api/wenjian'

const activeTab = ref('yonghu')
const tishi = ref('')
const yonghuQuery = reactive({ dengluZhanghao: '', xingming: '', jueseBianma: '', zhuangtai: '' })
const yonghuList = ref([])
const jueseList = ref([])
const huliOptions = ref([])
const jiashuOptions = ref([])
const currentYonghu = ref(null)

const yonghuDialogVisible = ref(false)
const passwordDialogVisible = ref(false)
const roleDialogVisible = ref(false)
const jueseDialogVisible = ref(false)
const yonghuForm = reactive(blankYonghu())
const passwordForm = reactive({ mima: '' })
const roleForm = reactive({ jueseIds: [] })
const jueseForm = reactive(blankJuese())
const avatarFallback = '/uploads/avatar/user-default.jpg'

function blankYonghu() {
  return { id: '', dengluZhanghao: '', mima: '123456', xingming: '', xingbie: '男', shouji: '', youxiang: '', touxiang: '/uploads/avatar/user-default.jpg', zhuangtai: '启用', huliRenyuanId: '', jiashuId: '', jueseIds: [] }
}

function blankJuese() {
  return { id: '', jueseBianma: '', jueseMingcheng: '', miaoshu: '' }
}

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 3000)
}

function guanlianText(item) {
  if (item.huliRenyuanId) return `护理员ID ${item.huliRenyuanId}`
  if (item.jiashuId) return `家属ID ${item.jiashuId}`
  return '-'
}

function avatarUrl(value) {
  return value || avatarFallback
}

function fallbackAvatar(event) {
  if (event.target.src.includes(avatarFallback)) return
  event.target.src = avatarFallback
}

async function uploadAvatar(event) {
  const file = event.target.files?.[0]
  if (!file) return
  try {
    const res = await uploadImage(file, 'avatar')
    yonghuForm.touxiang = res.data?.url || avatarFallback
    show('头像已上传')
  } catch (error) {
    show(error.message || '头像上传失败')
  } finally {
    event.target.value = ''
  }
}

function parseIds(value) {
  if (!value) return []
  return value.toString().split(',').filter(Boolean).map((item) => Number(item))
}

async function loadYonghu() {
  const res = await getXitongYonghuList(yonghuQuery)
  yonghuList.value = res.data || []
}

async function loadJuese() {
  const res = await getXitongJueseList()
  jueseList.value = res.data || []
}

async function loadOptions() {
  const [huli, jiashu] = await Promise.all([getHuliRenyuanOptions(), getJiashuOptions()])
  huliOptions.value = huli.data || []
  jiashuOptions.value = jiashu.data || []
}

async function loadAll() {
  await Promise.all([loadJuese(), loadYonghu(), loadOptions()])
}

function resetYonghuQuery() {
  Object.assign(yonghuQuery, { dengluZhanghao: '', xingming: '', jueseBianma: '', zhuangtai: '' })
  loadYonghu()
}

function openYonghu(item) {
  Object.assign(yonghuForm, blankYonghu(), item ? {
    id: item.id,
    dengluZhanghao: item.dengluZhanghao,
    xingming: item.xingming,
    xingbie: item.xingbie || '男',
    shouji: item.shouji || '',
    youxiang: item.youxiang || '',
    touxiang: item.touxiang || '/uploads/avatar/user-default.jpg',
    zhuangtai: item.zhuangtai || '启用',
    huliRenyuanId: item.huliRenyuanId || '',
    jiashuId: item.jiashuId || '',
    jueseIds: parseIds(item.jueseIds)
  } : {})
  yonghuDialogVisible.value = true
}

async function saveYonghu() {
  try {
    if (yonghuForm.id) await updateXitongYonghu(yonghuForm)
    else await addXitongYonghu(yonghuForm)
    yonghuDialogVisible.value = false
    await loadYonghu()
    await loadOptions()
    show('用户已保存')
  } catch (error) {
    show(error.message)
  }
}

async function toggleYonghuStatus(item) {
  const next = item.zhuangtai === '启用' ? '停用' : '启用'
  if (!window.confirm(`确认将 ${item.xingming} ${next}？`)) return
  try {
    await changeXitongYonghuStatus({ id: item.id, zhuangtai: next })
    await loadYonghu()
    show(`用户已${next}`)
  } catch (error) {
    show(error.message)
  }
}

function openPassword(item) {
  currentYonghu.value = item
  passwordForm.mima = '123456'
  passwordDialogVisible.value = true
}

async function savePassword() {
  if (!window.confirm('确认重置该用户密码？')) return
  try {
    await resetXitongYonghuPassword({ id: currentYonghu.value.id, mima: passwordForm.mima })
    passwordDialogVisible.value = false
    show('密码已重置')
  } catch (error) {
    show(error.message)
  }
}

function openRole(item) {
  currentYonghu.value = item
  roleForm.jueseIds = parseIds(item.jueseIds)
  roleDialogVisible.value = true
}

async function saveRole() {
  try {
    await assignXitongYonghuRole({ yonghuId: currentYonghu.value.id, jueseIds: roleForm.jueseIds })
    roleDialogVisible.value = false
    await loadYonghu()
    show('角色已分配')
  } catch (error) {
    show(error.message)
  }
}

function openJuese(item) {
  Object.assign(jueseForm, blankJuese(), item ? item : {})
  jueseDialogVisible.value = true
}

async function saveJuese() {
  try {
    if (jueseForm.id) await updateXitongJuese(jueseForm)
    else await addXitongJuese(jueseForm)
    jueseDialogVisible.value = false
    await loadJuese()
    show('角色已保存')
  } catch (error) {
    show(error.message)
  }
}

async function toggleJueseStatus(item) {
  if (!window.confirm(`确认处理角色 ${item.jueseMingcheng} 的启停？`)) return
  try {
    await changeXitongJueseStatus({ id: item.id })
  } catch (error) {
    show(error.message)
  }
}

async function removeJuese(item) {
  if (!window.confirm(`确认删除角色 ${item.jueseMingcheng}？`)) return
  try {
    await deleteXitongJuese(item.id)
  } catch (error) {
    show(error.message)
  }
}

onMounted(loadAll)
</script>

<style scoped>
.xitong-guanli-page { gap: 22px; }
.caozuo-tishi { margin-top: 12px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.tabs { display: flex; gap: 8px; border-bottom: 1px solid #dce5df; }
.tabs button { border-radius: 6px 6px 0 0; border: 1px solid #dce5df; border-bottom: 0; padding: 10px 18px; color: #334155; background: #fff; font-weight: 700; }
.tabs button.active { color: #fff; background: #0f766e; }
.guanli-kuai { display: grid; gap: 14px; border: 1px solid #dce5df; border-radius: 8px; padding: 18px; background: #fff; }
.kuai-heading { display: flex; align-items: center; justify-content: space-between; gap: 16px; }
.kuai-heading h3 { margin: 0; }
.kuai-heading p { margin: 6px 0 0; color: #64748b; }
.caozuo { display: flex; flex-wrap: wrap; gap: 6px; min-width: 280px; }
.kong { color: #94a3b8; text-align: center; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(560px, 100%); display: grid; gap: 16px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel.wide { width: min(840px, 100%); }
.modal-panel h3 { margin: 0; }
.modal-hint { margin: 0; color: #64748b; }
.modal-form { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 14px; }
.modal-form.one { grid-template-columns: 1fr; }
.modal-form label, .field { display: grid; gap: 6px; color: #334155; font-weight: 700; }
.modal-form input, .modal-form select, .modal-form textarea, .field input { width: 100%; border: 1px solid #cbd5e1; border-radius: 6px; padding: 9px 10px; background: #f8fafc; }
.modal-form textarea { min-height: 86px; resize: vertical; }
.modal-form .full { grid-column: 1 / -1; }
.role-checks { display: flex; flex-wrap: wrap; gap: 12px; align-items: center; }
.role-checks > span { width: 100%; color: #334155; font-weight: 700; }
.role-checks label { display: flex; align-items: center; gap: 6px; font-weight: 700; color: #334155; }
.role-checks input { width: auto; }
.avatar-field img { width: 72px; height: 72px; border: 1px solid #dce5df; border-radius: 50%; object-fit: cover; background: #eef5f1; }
.avatar-field input[type='file'] { padding: 8px 10px; background: #fff; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.modal-actions button.plain { border: 1px solid #cbd5e1; color: #334155; background: #fff; }
@media (max-width: 760px) { .modal-form { grid-template-columns: 1fr; } }
</style>
