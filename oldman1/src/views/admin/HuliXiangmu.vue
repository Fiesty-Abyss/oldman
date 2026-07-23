<template>
  <section class="zhanwei-page huli-guanli-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>护理项目与级别</h2>
      <p>维护护理项目、护理级别、级别项目绑定关系，并为老人设置当前护理级别。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="gongneng-kuai">
      <div class="kuai-heading">
        <div>
          <h3>护理项目管理</h3>
          <p>停用项目不会影响历史记录；删除项目会先移除级别绑定关系。</p>
        </div>
        <button type="button" @click="openXiangmuAdd">新增护理项目</button>
      </div>
      <div class="chaxunqu">
        <label><span>项目名称</span><input v-model="xiangmuQuery.xiangmuMingcheng" placeholder="请输入项目名称" /></label>
        <label>
          <span>状态</span>
          <select v-model="xiangmuQuery.zhuangtai">
            <option value="">全部</option>
            <option>启用</option>
            <option>停用</option>
          </select>
        </label>
        <button type="button" @click="loadXiangmu">查询</button>
        <button type="button" class="plain" @click="resetXiangmuQuery">重置</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>ID</th><th>编号</th><th>名称</th><th>价格</th><th>周期</th><th>次数</th><th>单位</th><th>状态</th><th>操作</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in xiangmuList" :key="item.id">
              <td>{{ item.id }}</td>
              <td>{{ item.xiangmuBianhao }}</td>
              <td>{{ item.xiangmuMingcheng }}</td>
              <td>{{ item.jiage }}</td>
              <td>{{ item.zhixingZhouqi || '-' }}</td>
              <td>{{ item.zhixingCishu }}</td>
              <td>{{ item.danwei }}</td>
              <td><span :class="['zhuangtai-biaoji', item.zhuangtai === '启用' ? 'is-ok' : 'is-stop']">{{ item.zhuangtai }}</span></td>
              <td>
                <button @click="openXiangmuEdit(item)">编辑</button>
                <button @click="toggleXiangmu(item)">{{ item.zhuangtai === '启用' ? '停用' : '启用' }}</button>
                <button @click="removeXiangmu(item.id)">删除</button>
              </td>
            </tr>
            <tr v-if="!xiangmuList.length"><td colspan="9" class="kong">暂无护理项目</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="gongneng-kuai">
      <div class="kuai-heading">
        <div>
          <h3>护理级别管理</h3>
          <p>只有启用状态的护理级别可以分配给老人。</p>
        </div>
        <button type="button" @click="openJibieAdd">新增护理级别</button>
      </div>
      <div class="chaxunqu">
        <label><span>级别名称</span><input v-model="jibieQuery.jibieMingcheng" placeholder="请输入级别名称" /></label>
        <label>
          <span>状态</span>
          <select v-model="jibieQuery.zhuangtai">
            <option value="">全部</option>
            <option>启用</option>
            <option>停用</option>
          </select>
        </label>
        <button type="button" @click="loadJibie">查询</button>
        <button type="button" class="plain" @click="resetJibieQuery">重置</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>ID</th><th>编号</th><th>名称</th><th>月费用</th><th>状态</th><th>说明</th><th>操作</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in jibieList" :key="item.id">
              <td>{{ item.id }}</td>
              <td>{{ item.jibieBianhao }}</td>
              <td>{{ item.jibieMingcheng }}</td>
              <td>{{ item.yueFeiyong }}</td>
              <td><span :class="['zhuangtai-biaoji', item.zhuangtai === '启用' ? 'is-ok' : 'is-stop']">{{ item.zhuangtai }}</span></td>
              <td>{{ item.miaoshu || '-' }}</td>
              <td>
                <button @click="openJibieEdit(item)">编辑</button>
                <button @click="toggleJibie(item)">{{ item.zhuangtai === '启用' ? '停用' : '启用' }}</button>
                <button @click="removeJibie(item.id)">删除</button>
                <button @click="chooseJibie(item.id)">绑定项目</button>
              </td>
            </tr>
            <tr v-if="!jibieList.length"><td colspan="7" class="kong">暂无护理级别</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="gongneng-kuai">
      <div class="kuai-heading">
        <div>
          <h3>级别项目绑定</h3>
          <p>只能绑定启用且尚未绑定到当前级别的护理项目。</p>
        </div>
        <label class="jibie-xuanze">
          <span>护理级别</span>
          <select v-model="selectedJibieId" @change="loadBangding">
            <option value="">请选择护理级别</option>
            <option v-for="item in jibieList" :key="item.id" :value="item.id">{{ item.jibieMingcheng }}</option>
          </select>
        </label>
      </div>
      <div class="bangding-grid">
        <div class="bangding-card">
          <h4>已绑定项目</h4>
          <table>
            <thead><tr><th>项目</th><th>状态</th><th>操作</th></tr></thead>
            <tbody>
              <tr v-for="item in boundXiangmuList" :key="item.id">
                <td>{{ item.xiangmuMingcheng }}</td>
                <td>{{ item.zhuangtai }}</td>
                <td><button @click="unbindXiangmu(item.id)">移除项目</button></td>
              </tr>
              <tr v-if="!boundXiangmuList.length"><td colspan="3" class="kong">暂无绑定项目</td></tr>
            </tbody>
          </table>
        </div>
        <div class="bangding-card">
          <h4>可绑定项目</h4>
          <table>
            <thead><tr><th>项目</th><th>价格</th><th>操作</th></tr></thead>
            <tbody>
              <tr v-for="item in unboundXiangmuList" :key="item.id">
                <td>{{ item.xiangmuMingcheng }}</td>
                <td>{{ item.jiage }}</td>
                <td><button @click="bindXiangmu(item.id)">绑定项目</button></td>
              </tr>
              <tr v-if="!unboundXiangmuList.length"><td colspan="3" class="kong">暂无可绑定项目</td></tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div class="gongneng-kuai">
      <div class="kuai-heading">
        <div>
          <h3>老人护理设置</h3>
          <p>一个老人同一时间只能有一个有效护理级别；如需更换，请先移除旧级别。</p>
        </div>
      </div>
      <div class="chaxunqu">
        <label>
          <span>老人</span>
          <select v-model="laorenHuliForm.laorenId">
            <option value="">请选择老人</option>
            <option v-for="item in laorenList" :key="item.laorenId" :value="item.laorenId">{{ item.laorenXingming }}（{{ item.laorenBianhao }}）</option>
          </select>
        </label>
        <label>
          <span>护理级别</span>
          <select v-model="laorenHuliForm.jibieId">
            <option value="">请选择护理级别</option>
            <option v-for="item in enabledJibieList" :key="item.id" :value="item.id">{{ item.jibieMingcheng }}</option>
          </select>
        </label>
        <label><span>设置日期</span><input v-model="laorenHuliForm.shezhiRiqi" type="date" /></label>
        <label><span>到期日期</span><input v-model="laorenHuliForm.daoqiRiqi" type="date" /></label>
        <label><span>备注</span><input v-model="laorenHuliForm.beizhu" placeholder="可选" /></label>
        <button type="button" @click="setLaorenJibie">设置护理级别</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>老人编号</th><th>姓名</th><th>老人类型</th><th>入住状态</th><th>当前级别</th><th>设置日期</th><th>状态</th><th>操作</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in laorenList" :key="item.laorenId">
              <td>{{ item.laorenBianhao }}</td>
              <td>{{ item.laorenXingming }}</td>
              <td>{{ item.laorenLeixing }}</td>
              <td>{{ item.ruzhuZhuangtai }}</td>
              <td>{{ item.jibieMingcheng || '-' }}</td>
              <td>{{ item.shezhiRiqi || '-' }}</td>
              <td>{{ item.huliZhuangtai || '-' }}</td>
              <td>
                <button @click="selectLaoren(item)">选择</button>
                <button @click="chakanDangqianHuli(item.laorenId)">查看级别</button>
                <button @click="removeLaorenJibie(item.laorenId)">移除级别</button>
              </td>
            </tr>
            <tr v-if="!laorenList.length"><td colspan="8" class="kong">暂无老人数据</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="xiangmuDialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>{{ xiangmuDialogTitle }}</h3>
        <div class="modal-form">
          <label><span>项目编号</span><input v-model="xiangmuForm.xiangmuBianhao" /></label>
          <label><span>项目名称</span><input v-model="xiangmuForm.xiangmuMingcheng" /></label>
          <label><span>价格</span><input v-model="xiangmuForm.jiage" type="number" /></label>
          <label><span>执行周期</span><input v-model="xiangmuForm.zhixingZhouqi" placeholder="每日/每周/按需" /></label>
          <label><span>执行次数</span><input v-model="xiangmuForm.zhixingCishu" type="number" /></label>
          <label><span>单位</span><input v-model="xiangmuForm.danwei" /></label>
          <label><span>状态</span><select v-model="xiangmuForm.zhuangtai"><option>启用</option><option>停用</option></select></label>
          <label class="full"><span>说明</span><textarea v-model="xiangmuForm.miaoshu"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveXiangmu">保存</button>
          <button type="button" class="plain" @click="xiangmuDialogVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="jibieDialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>{{ jibieDialogTitle }}</h3>
        <div class="modal-form">
          <label><span>级别编号</span><input v-model="jibieForm.jibieBianhao" /></label>
          <label><span>级别名称</span><input v-model="jibieForm.jibieMingcheng" /></label>
          <label><span>月费用</span><input v-model="jibieForm.yueFeiyong" type="number" /></label>
          <label><span>状态</span><select v-model="jibieForm.zhuangtai"><option>启用</option><option>停用</option></select></label>
          <label class="full"><span>说明</span><textarea v-model="jibieForm.miaoshu"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveJibie">保存</button>
          <button type="button" class="plain" @click="jibieDialogVisible = false">取消</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import {
  addHuliJibie,
  addHuliXiangmu,
  bindJibieXiangmu,
  changeHuliJibieStatus,
  changeHuliXiangmuStatus,
  deleteHuliJibie,
  deleteHuliXiangmu,
  getHuliJibieList,
  getHuliXiangmuList,
  getJibieUnboundXiangmuList,
  getJibieXiangmuList,
  getLaorenDangqianHuli,
  getLaorenHuliList,
  removeLaorenHuliJibie,
  setLaorenHuliJibie,
  unbindJibieXiangmu,
  updateHuliJibie,
  updateHuliXiangmu
} from '../../api/huliguanli'

const tishi = ref('')
const xiangmuList = ref([])
const jibieList = ref([])
const boundXiangmuList = ref([])
const unboundXiangmuList = ref([])
const laorenList = ref([])
const selectedJibieId = ref('')

const xiangmuQuery = reactive({ xiangmuMingcheng: '', zhuangtai: '' })
const jibieQuery = reactive({ jibieMingcheng: '', zhuangtai: '' })
const xiangmuDialogVisible = ref(false)
const jibieDialogVisible = ref(false)
const xiangmuDialogTitle = ref('新增护理项目')
const jibieDialogTitle = ref('新增护理级别')
const xiangmuForm = reactive(emptyXiangmu())
const jibieForm = reactive(emptyJibie())
const laorenHuliForm = reactive({ laorenId: '', jibieId: '', shezhiRiqi: '', daoqiRiqi: '', beizhu: '' })
const enabledJibieList = computed(() => jibieList.value.filter((item) => item.zhuangtai === '启用'))

function emptyXiangmu() {
  return { id: '', xiangmuBianhao: '', xiangmuMingcheng: '', jiage: 0, zhixingZhouqi: '每日', zhixingCishu: 1, danwei: '次', zhuangtai: '启用', miaoshu: '' }
}

function emptyJibie() {
  return { id: '', jibieBianhao: '', jibieMingcheng: '', yueFeiyong: 0, zhuangtai: '启用', miaoshu: '' }
}

function showTishi(message) {
  tishi.value = message
  window.setTimeout(() => {
    if (tishi.value === message) tishi.value = ''
  }, 2600)
}

async function run(action, message) {
  try {
    await action()
    if (message) showTishi(message)
  } catch (error) {
    showTishi(error.message || '操作失败')
  }
}

async function loadXiangmu() {
  const res = await getHuliXiangmuList(xiangmuQuery)
  xiangmuList.value = res.data || []
}

async function loadJibie() {
  const res = await getHuliJibieList(jibieQuery)
  jibieList.value = res.data || []
  if (!selectedJibieId.value && jibieList.value.length) selectedJibieId.value = jibieList.value[0].id
  await loadBangding()
}

async function loadBangding() {
  if (!selectedJibieId.value) {
    boundXiangmuList.value = []
    unboundXiangmuList.value = []
    return
  }
  const [bound, unbound] = await Promise.all([
    getJibieXiangmuList(selectedJibieId.value),
    getJibieUnboundXiangmuList(selectedJibieId.value)
  ])
  boundXiangmuList.value = bound.data || []
  unboundXiangmuList.value = unbound.data || []
}

async function loadLaoren() {
  const res = await getLaorenHuliList()
  laorenList.value = res.data || []
}

function resetXiangmuQuery() {
  Object.assign(xiangmuQuery, { xiangmuMingcheng: '', zhuangtai: '' })
  loadXiangmu()
}

function resetJibieQuery() {
  Object.assign(jibieQuery, { jibieMingcheng: '', zhuangtai: '' })
  loadJibie()
}

function openXiangmuAdd() {
  xiangmuDialogTitle.value = '新增护理项目'
  Object.assign(xiangmuForm, emptyXiangmu())
  xiangmuDialogVisible.value = true
}

function openXiangmuEdit(item) {
  xiangmuDialogTitle.value = '编辑护理项目'
  Object.assign(xiangmuForm, item)
  xiangmuDialogVisible.value = true
}

async function saveXiangmu() {
  await run(async () => {
    if (xiangmuForm.id) await updateHuliXiangmu(xiangmuForm)
    else await addHuliXiangmu(xiangmuForm)
    xiangmuDialogVisible.value = false
    await loadXiangmu()
    await loadBangding()
  }, '护理项目已保存')
}

async function toggleXiangmu(item) {
  const zhuangtai = item.zhuangtai === '启用' ? '停用' : '启用'
  await run(async () => {
    await changeHuliXiangmuStatus({ id: item.id, zhuangtai })
    await loadXiangmu()
    await loadBangding()
  }, `护理项目已${zhuangtai}`)
}

async function removeXiangmu(id) {
  if (!window.confirm('确认逻辑删除该护理项目，并移除它和护理级别的绑定关系吗？')) return
  await run(async () => {
    await deleteHuliXiangmu(id)
    await loadXiangmu()
    await loadBangding()
  }, '护理项目已删除')
}

function openJibieAdd() {
  jibieDialogTitle.value = '新增护理级别'
  Object.assign(jibieForm, emptyJibie())
  jibieDialogVisible.value = true
}

function openJibieEdit(item) {
  jibieDialogTitle.value = '编辑护理级别'
  Object.assign(jibieForm, item)
  jibieDialogVisible.value = true
}

async function saveJibie() {
  await run(async () => {
    if (jibieForm.id) await updateHuliJibie(jibieForm)
    else await addHuliJibie(jibieForm)
    jibieDialogVisible.value = false
    await loadJibie()
  }, '护理级别已保存')
}

async function toggleJibie(item) {
  const zhuangtai = item.zhuangtai === '启用' ? '停用' : '启用'
  await run(async () => {
    await changeHuliJibieStatus({ id: item.id, zhuangtai })
    await loadJibie()
  }, `护理级别已${zhuangtai}`)
}

async function removeJibie(id) {
  if (!window.confirm('确认逻辑删除该护理级别，并移除它绑定的护理项目吗？')) return
  await run(async () => {
    await deleteHuliJibie(id)
    if (String(selectedJibieId.value) === String(id)) selectedJibieId.value = ''
    await loadJibie()
    await loadLaoren()
  }, '护理级别已删除')
}

function chooseJibie(id) {
  selectedJibieId.value = id
  loadBangding()
}

async function bindXiangmu(xiangmuId) {
  await run(async () => {
    await bindJibieXiangmu({ jibieId: selectedJibieId.value, xiangmuId })
    await loadBangding()
  }, '护理项目已绑定')
}

async function unbindXiangmu(xiangmuId) {
  await run(async () => {
    await unbindJibieXiangmu(selectedJibieId.value, xiangmuId)
    await loadBangding()
  }, '护理项目已移除')
}

function selectLaoren(item) {
  laorenHuliForm.laorenId = item.laorenId
  if (item.jibieId) laorenHuliForm.jibieId = item.jibieId
}

async function setLaorenJibie() {
  await run(async () => {
    const payload = {
      ...laorenHuliForm,
      shezhiRiqi: laorenHuliForm.shezhiRiqi || null,
      daoqiRiqi: laorenHuliForm.daoqiRiqi || null
    }
    await setLaorenHuliJibie(payload)
    Object.assign(laorenHuliForm, { laorenId: '', jibieId: '', shezhiRiqi: '', daoqiRiqi: '', beizhu: '' })
    await loadLaoren()
  }, '老人护理级别已设置')
}

async function chakanDangqianHuli(laorenId) {
  await run(async () => {
    const res = await getLaorenDangqianHuli(laorenId)
    const huli = res.data
    showTishi(huli ? `当前护理级别：${huli.jibieMingcheng}` : '该老人当前未设置护理级别')
  })
}

async function removeLaorenJibie(laorenId) {
  if (!window.confirm('确认移除该老人的当前护理级别吗？')) return
  await run(async () => {
    await removeLaorenHuliJibie(laorenId)
    await loadLaoren()
  }, '老人护理级别已移除')
}

onMounted(async () => {
  await Promise.all([loadXiangmu(), loadJibie(), loadLaoren()])
})
</script>

<style scoped>
.huli-guanli-page {
  gap: 22px;
}

.caozuo-tishi {
  margin-top: 12px;
  border: 1px solid #bbf7d0;
  border-radius: 6px;
  padding: 9px 12px;
  color: #166534;
  background: #f0fdf4;
}

.gongneng-kuai {
  display: grid;
  gap: 14px;
  border: 1px solid #dce5df;
  border-radius: 8px;
  padding: 18px;
  background: #ffffff;
}

.kuai-heading {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.kuai-heading h3,
.bangding-card h4,
.modal-panel h3 {
  margin: 0;
}

.kuai-heading p {
  margin: 6px 0 0;
  color: #64748b;
  line-height: 1.6;
}

.kuai-heading button,
.modal-actions button {
  min-height: 38px;
  border-radius: 6px;
  padding: 0 16px;
  color: #ffffff;
  background: #0f766e;
  font-weight: 700;
}

.jibie-xuanze {
  display: grid;
  gap: 6px;
  color: #334155;
  font-size: 14px;
  font-weight: 700;
}

.jibie-xuanze select {
  min-width: 240px;
  height: 38px;
  border: 1px solid #cbd5e1;
  border-radius: 6px;
  padding: 0 10px;
  background: #f8fafc;
}

.bangding-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.bangding-card {
  overflow: auto;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
}

.bangding-card h4 {
  padding: 14px;
  border-bottom: 1px solid #e2e8f0;
  background: #f8fafc;
}

.zhuangtai-biaoji {
  display: inline-flex;
  align-items: center;
  min-height: 26px;
  border-radius: 999px;
  padding: 0 10px;
  font-size: 13px;
  font-weight: 700;
}

.zhuangtai-biaoji.is-ok {
  color: #166534;
  background: #dcfce7;
}

.zhuangtai-biaoji.is-stop {
  color: #92400e;
  background: #fef3c7;
}

.kong {
  color: #94a3b8;
  text-align: center;
}

.modal-mask {
  position: fixed;
  inset: 0;
  z-index: 20;
  display: grid;
  place-items: center;
  padding: 20px;
  background: rgba(15, 23, 42, 0.42);
}

.modal-panel {
  width: min(720px, 100%);
  display: grid;
  gap: 18px;
  border-radius: 8px;
  padding: 22px;
  background: #ffffff;
  box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24);
}

.modal-form {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.modal-form label {
  display: grid;
  gap: 6px;
  color: #334155;
  font-weight: 700;
}

.modal-form input,
.modal-form select,
.modal-form textarea {
  width: 100%;
  border: 1px solid #cbd5e1;
  border-radius: 6px;
  padding: 9px 10px;
  background: #f8fafc;
}

.modal-form textarea {
  min-height: 86px;
  resize: vertical;
}

.modal-form .full {
  grid-column: 1 / -1;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.modal-actions button.plain {
  border: 1px solid #cbd5e1;
  color: #334155;
  background: #ffffff;
}

@media (max-width: 760px) {
  .kuai-heading,
  .bangding-grid,
  .modal-form {
    grid-template-columns: 1fr;
  }

  .kuai-heading {
    display: grid;
  }
}
</style>
