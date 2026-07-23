<template>
  <section class="zhanwei-page chuangwei-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>床位分配管理</h2>
      <p>查看房间和床位状态，维护房间、床位基础资料，并完成床位分配、调换和释放。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="guanli-kuai">
      <div class="kuai-heading">
        <h3>床位分配</h3>
      </div>
      <div class="chaxunqu">
        <label>
          <span>老人</span>
          <select v-model="fenpeiForm.laorenId">
            <option value="">请选择老人</option>
            <option v-for="item in laorenOptions" :key="item.id" :value="item.id">{{ item.xingming }} / {{ item.ruzhuZhuangtai }}</option>
          </select>
        </label>
        <label>
          <span>房间</span>
          <select v-model="fenpeiForm.fangjianId" @change="onFenpeiFangjianChange">
            <option value="">请选择房间</option>
            <option v-for="item in fangjianOptions" :key="item.id" :value="item.id">{{ item.fangjianhao }} / {{ item.louceng }}层</option>
          </select>
        </label>
        <label>
          <span>空闲床位</span>
          <select v-model="fenpeiForm.chuangweiId" @change="syncFangjianByChuangwei">
            <option value="">请选择空闲床位</option>
            <option v-for="item in kongxianChuangweiOptions" :key="item.id" :value="item.id">{{ item.fangjianhao }} / {{ item.chuangweihao }}</option>
          </select>
        </label>
        <button type="button" @click="fenpei">分配床位</button>
        <button type="button" @click="tiaohuan">调换床位</button>
        <button type="button" class="plain" @click="shifang">释放床位</button>
      </div>

      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>楼层</th><th>房间</th><th>房型</th><th>房间ID</th><th>床位号</th><th>床位ID</th><th>状态</th><th>老人</th></tr></thead>
          <tbody>
            <tr v-for="item in list" :key="item.chuangweiId">
              <td>{{ item.louceng }}</td>
              <td>{{ item.fangjianhao }}</td>
              <td>{{ item.fangjianLeixing }}</td>
              <td>{{ item.fangjianId }}</td>
              <td>{{ item.chuangweihao }}</td>
              <td>{{ item.chuangweiId }}</td>
              <td>{{ item.chuangweiZhuangtai }}</td>
              <td>{{ item.laorenXingming || '-' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="guanli-kuai">
      <div class="kuai-heading">
        <h3>房间管理</h3>
        <button type="button" @click="openFangjian()">新增房间</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>ID</th><th>楼栋</th><th>楼层</th><th>房间号</th><th>房型</th><th>床位数</th><th>朝向</th><th>状态</th><th>备注</th><th>操作</th></tr></thead>
          <tbody>
            <tr v-for="item in fangjianList" :key="item.id">
              <td>{{ item.id }}</td>
              <td>{{ item.loudong }}</td>
              <td>{{ item.louceng }}</td>
              <td>{{ item.fangjianhao }}</td>
              <td>{{ item.fangjianLeixing || '-' }}</td>
              <td>{{ item.chuangweiShu }}</td>
              <td>{{ item.chaoxiang || '-' }}</td>
              <td>{{ item.zhuangtai }}</td>
              <td>{{ item.beizhu || '-' }}</td>
              <td>
                <button type="button" @click="openFangjian(item)">编辑</button>
                <button type="button" @click="removeFangjian(item)">删除</button>
              </td>
            </tr>
            <tr v-if="!fangjianList.length"><td colspan="10" class="kong">暂无房间</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="guanli-kuai">
      <div class="kuai-heading">
        <h3>床位管理</h3>
        <button type="button" @click="openChuangwei()">新增床位</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>ID</th><th>房间</th><th>楼层</th><th>床位号</th><th>状态</th><th>备注</th><th>操作</th></tr></thead>
          <tbody>
            <tr v-for="item in chuangweiBaseList" :key="item.id">
              <td>{{ item.id }}</td>
              <td>{{ item.fangjianhao }} / {{ item.fangjianId }}</td>
              <td>{{ item.louceng }}</td>
              <td>{{ item.chuangweihao }}</td>
              <td>{{ item.zhuangtai }}</td>
              <td>{{ item.beizhu || '-' }}</td>
              <td>
                <button type="button" @click="openChuangwei(item)">编辑</button>
                <button type="button" @click="removeChuangwei(item)">删除</button>
              </td>
            </tr>
            <tr v-if="!chuangweiBaseList.length"><td colspan="7" class="kong">暂无床位</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="fangjianDialogVisible" class="modal-mask">
      <div class="modal-panel wide">
        <h3>{{ fangjianForm.id ? '编辑房间' : '新增房间' }}</h3>
        <div class="modal-form">
          <label><span>楼栋</span><input v-model.trim="fangjianForm.loudong" /></label>
          <label><span>楼层</span><input v-model="fangjianForm.louceng" type="number" /></label>
          <label><span>房间号</span><input v-model.trim="fangjianForm.fangjianhao" /></label>
          <label><span>房型</span><input v-model.trim="fangjianForm.fangjianLeixing" /></label>
          <label><span>朝向</span><input v-model.trim="fangjianForm.chaoxiang" /></label>
          <label>
            <span>状态</span>
            <select v-model="fangjianForm.zhuangtai"><option>正常</option><option>维修</option></select>
          </label>
          <label class="full"><span>备注</span><textarea v-model.trim="fangjianForm.beizhu"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveFangjian">保存</button>
          <button type="button" class="plain" @click="fangjianDialogVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="chuangweiDialogVisible" class="modal-mask">
      <div class="modal-panel wide">
        <h3>{{ chuangweiForm.id ? '编辑床位' : '新增床位' }}</h3>
        <div class="modal-form">
          <label>
            <span>所属房间</span>
            <select v-model="chuangweiForm.fangjianId">
              <option value="">请选择房间</option>
              <option v-for="item in fangjianList" :key="item.id" :value="item.id">{{ item.fangjianhao }} / {{ item.louceng }}层</option>
            </select>
          </label>
          <label><span>床位号</span><input v-model.trim="chuangweiForm.chuangweihao" /></label>
          <label>
            <span>状态</span>
            <select v-model="chuangweiForm.zhuangtai">
              <option>空闲</option><option>有人</option><option>外出</option><option>维修</option>
            </select>
          </label>
          <label class="full"><span>备注</span><textarea v-model.trim="chuangweiForm.beizhu"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="saveChuangwei">保存</button>
          <button type="button" class="plain" @click="chuangweiDialogVisible = false">取消</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import {
  addChuangwei,
  addFangjian,
  deleteChuangwei,
  deleteFangjian,
  fenpeiChuangwei,
  getChuangweiBaseList,
  getChuangweiList,
  getFangjianList,
  getFangjianOptions,
  getKongxianChuangweiOptions,
  shifangChuangwei,
  tiaohuanChuangwei,
  updateChuangwei,
  updateFangjian
} from '../../api/chuangwei'
import { getLaorenOptions } from '../../api/laoren'

const list = ref([])
const fangjianList = ref([])
const chuangweiBaseList = ref([])
const laorenOptions = ref([])
const fangjianOptions = ref([])
const kongxianChuangweiOptions = ref([])
const fenpeiForm = reactive({ laorenId: '', fangjianId: '', chuangweiId: '' })
const fangjianForm = reactive(blankFangjian())
const chuangweiForm = reactive(blankChuangwei())
const fangjianDialogVisible = ref(false)
const chuangweiDialogVisible = ref(false)
const tishi = ref('')

function blankFangjian() {
  return { id: '', loudong: '606号楼', louceng: '', fangjianhao: '', fangjianLeixing: '', chaoxiang: '', zhuangtai: '正常', beizhu: '' }
}

function blankChuangwei() {
  return { id: '', fangjianId: '', chuangweihao: '', zhuangtai: '空闲', beizhu: '' }
}

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 2400)
}

async function loadAll() {
  const [statusRes, fangjianRes, chuangweiRes, laorenRes, fangjianOptionRes] = await Promise.all([
    getChuangweiList(),
    getFangjianList(),
    getChuangweiBaseList(),
    getLaorenOptions(),
    getFangjianOptions()
  ])
  list.value = statusRes.data || []
  fangjianList.value = fangjianRes.data || []
  chuangweiBaseList.value = chuangweiRes.data || []
  laorenOptions.value = laorenRes.data || []
  fangjianOptions.value = fangjianOptionRes.data || []
  await loadKongxianChuangwei()
}

async function loadKongxianChuangwei() {
  const params = fenpeiForm.fangjianId ? { fangjianId: fenpeiForm.fangjianId } : {}
  const res = await getKongxianChuangweiOptions(params)
  kongxianChuangweiOptions.value = res.data || []
}

async function fenpei() {
  if (!fenpeiForm.laorenId || !fenpeiForm.chuangweiId) {
    show('请选择老人和空闲床位')
    return
  }
  await fenpeiChuangwei(fenpeiForm)
  await loadAll()
  show('床位已分配')
}

async function tiaohuan() {
  if (!fenpeiForm.laorenId || !fenpeiForm.chuangweiId) {
    show('请选择老人和新的空闲床位')
    return
  }
  await tiaohuanChuangwei(fenpeiForm)
  await loadAll()
  show('床位已调换')
}

async function shifang() {
  if (!fenpeiForm.laorenId) {
    show('请选择需要释放床位的老人')
    return
  }
  await shifangChuangwei(fenpeiForm.laorenId)
  await loadAll()
  show('床位已释放')
}

async function onFenpeiFangjianChange() {
  fenpeiForm.chuangweiId = ''
  await loadKongxianChuangwei()
}

function syncFangjianByChuangwei() {
  const chuangwei = kongxianChuangweiOptions.value.find((item) => String(item.id) === String(fenpeiForm.chuangweiId))
  if (chuangwei) {
    fenpeiForm.fangjianId = chuangwei.fangjianId
  }
}

function openFangjian(item = null) {
  Object.assign(fangjianForm, blankFangjian(), item || {})
  fangjianDialogVisible.value = true
}

async function saveFangjian() {
  if (fangjianForm.id) await updateFangjian(fangjianForm)
  else await addFangjian(fangjianForm)
  fangjianDialogVisible.value = false
  await loadAll()
  show('房间信息已保存')
}

async function removeFangjian(item) {
  if (!window.confirm(`确认删除房间 ${item.fangjianhao} 吗？`)) return
  try {
    await deleteFangjian(item.id)
    await loadAll()
    show('房间已删除')
  } catch (error) {
    show(error.message || '删除失败')
  }
}

function openChuangwei(item = null) {
  Object.assign(chuangweiForm, blankChuangwei(), item || {})
  chuangweiDialogVisible.value = true
}

async function saveChuangwei() {
  if (chuangweiForm.id) await updateChuangwei(chuangweiForm)
  else await addChuangwei(chuangweiForm)
  chuangweiDialogVisible.value = false
  await loadAll()
  show('床位信息已保存')
}

async function removeChuangwei(item) {
  if (!window.confirm(`确认删除床位 ${item.chuangweihao} 吗？`)) return
  try {
    await deleteChuangwei(item.id)
    await loadAll()
    show('床位已删除')
  } catch (error) {
    show(error.message || '删除失败')
  }
}

onMounted(loadAll)
</script>

<style scoped>
.chuangwei-page { gap: 22px; }
.caozuo-tishi { margin-top: 12px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.guanli-kuai { display: grid; gap: 14px; border: 1px solid #dce5df; border-radius: 8px; padding: 18px; background: #fff; }
.kuai-heading { display: flex; align-items: center; justify-content: space-between; gap: 14px; }
.kuai-heading h3 { margin: 0; }
.kuai-heading button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.kong { color: #94a3b8; text-align: center; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(560px, 100%); display: grid; gap: 16px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel.wide { width: min(760px, 100%); }
.modal-panel h3 { margin: 0; }
.modal-form { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 14px; }
.modal-form label { display: grid; gap: 7px; color: #334155; font-weight: 700; }
.modal-form .full { grid-column: 1 / -1; }
.modal-form input, .modal-form select, .modal-form textarea { width: 100%; border: 1px solid #cbd5e1; border-radius: 6px; padding: 10px 12px; background: #f8fafc; }
.modal-form textarea { min-height: 90px; resize: vertical; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.modal-actions button.plain { border: 1px solid #cbd5e1; color: #334155; background: #fff; }
@media (max-width: 720px) { .modal-form { grid-template-columns: 1fr; } }
</style>
