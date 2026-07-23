<template>
  <section class="zhanwei-page admin-jilu-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>健康档案与用药</h2>
      <p>本页先用于监管护理员录入的护理记录、健康监测记录和用药执行记录。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="jilu-kuai">
      <div class="kuai-heading">
        <h3>护理记录</h3>
      </div>
      <div class="chaxunqu">
        <label><span>老人姓名</span><input v-model.trim="huliQuery.laorenXingming" /></label>
        <label><span>护理员</span><input v-model.trim="huliQuery.huliRenyuanXingming" /></label>
        <label><span>护理项目</span><input v-model.trim="huliQuery.xiangmuMingcheng" /></label>
        <label><span>日期</span><input v-model="huliQuery.riqi" type="date" /></label>
        <button type="button" @click="loadHuli">查询</button>
        <button type="button" class="plain" @click="resetHuli">重置</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>老人</th><th>护理员</th><th>项目</th><th>时间</th><th>数量</th><th>内容</th><th>反馈</th><th>状态</th><th>操作</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in huliList" :key="item.id">
              <td>{{ item.laorenXingming }}</td>
              <td>{{ item.huliRenyuanXingming }}</td>
              <td>{{ item.xiangmuMingcheng }}</td>
              <td>{{ item.huliShijian }}</td>
              <td>{{ item.huliShuliang }}</td>
              <td>{{ item.huliNeirong || '-' }}</td>
              <td>{{ item.laorenFankui || '-' }}</td>
              <td>{{ item.zhuangtai }}</td>
              <td><button @click="deleteHuli(item.id)">删除</button></td>
            </tr>
            <tr v-if="!huliList.length"><td colspan="9" class="kong">暂无护理记录</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="jilu-kuai">
      <div class="kuai-heading">
        <h3>健康监测记录</h3>
      </div>
      <div class="chaxunqu">
        <label><span>老人姓名</span><input v-model.trim="jiankangQuery.laorenXingming" /></label>
        <label><span>日期</span><input v-model="jiankangQuery.riqi" type="date" /></label>
        <button type="button" @click="loadJiankang">查询</button>
        <button type="button" class="plain" @click="resetJiankang">重置</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>老人</th><th>护理员</th><th>时间</th><th>体温</th><th>血压</th><th>心率</th><th>血氧</th><th>血糖</th><th>备注</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in jiankangList" :key="item.id">
              <td>{{ item.laorenXingming }}</td>
              <td>{{ item.huliRenyuanXingming || '-' }}</td>
              <td>{{ item.jianceShijian }}</td>
              <td>{{ item.tiwen || '-' }}</td>
              <td>{{ item.gaoya || '-' }}/{{ item.diya || '-' }}</td>
              <td>{{ item.xinlv || '-' }}</td>
              <td>{{ item.xueyang || '-' }}</td>
              <td>{{ item.xuetang || '-' }}</td>
              <td>{{ item.qingkuang || '-' }}</td>
            </tr>
            <tr v-if="!jiankangList.length"><td colspan="9" class="kong">暂无健康监测记录</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="jilu-kuai">
      <div class="kuai-heading">
        <h3>用药执行记录</h3>
      </div>
      <div class="chaxunqu">
        <label><span>老人姓名</span><input v-model.trim="fuyaoQuery.laorenXingming" /></label>
        <label><span>药品名称</span><input v-model.trim="fuyaoQuery.yaopinMingcheng" /></label>
        <label>
          <span>执行状态</span>
          <select v-model="fuyaoQuery.zhixingZhuangtai">
            <option value="">全部</option>
            <option>已服</option>
            <option>已服药</option>
            <option>漏服</option>
            <option>拒服</option>
            <option>异常</option>
            <option>延期</option>
          </select>
        </label>
        <label><span>日期</span><input v-model="fuyaoQuery.riqi" type="date" /></label>
        <button type="button" @click="loadFuyao">查询</button>
        <button type="button" class="plain" @click="resetFuyao">重置</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>老人</th><th>护理员</th><th>药品</th><th>规格</th><th>计划时间</th><th>实际时间</th><th>状态</th><th>备注</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in fuyaoList" :key="item.id">
              <td>{{ item.laorenXingming }}</td>
              <td>{{ item.huliRenyuanXingming || '-' }}</td>
              <td>{{ item.yaopinMingcheng }}</td>
              <td>{{ item.guige || '-' }}</td>
              <td>{{ item.jihuaShijian }}</td>
              <td>{{ item.shijiShijian || '-' }}</td>
              <td>{{ item.zhixingZhuangtai }}</td>
              <td>{{ item.beizhu || '-' }}</td>
            </tr>
            <tr v-if="!fuyaoList.length"><td colspan="8" class="kong">暂无用药执行记录</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="jilu-kuai">
      <div class="kuai-heading inline-heading">
        <div>
          <h3>药品管理</h3>
          <p>维护当前可用药品基础信息，不涉及库存流水。</p>
        </div>
        <button type="button" @click="openYaopinAdd">新增药品</button>
      </div>
      <div class="chaxunqu">
        <label><span>药品名称</span><input v-model.trim="yaopinQuery.yaopinMingcheng" placeholder="请输入药品名称" /></label>
        <button type="button" @click="loadYaopin">查询</button>
        <button type="button" class="plain" @click="resetYaopin">重置</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead>
            <tr><th>药品名称</th><th>药品状态</th><th>规格</th><th>单位</th><th>库存数量</th><th>生产厂家</th><th>备注</th><th>操作</th></tr>
          </thead>
          <tbody>
            <tr v-for="item in yaopinList" :key="item.id">
              <td>{{ item.yaopinMingcheng }}</td>
              <td>{{ item.zhuangtai || '启用' }}</td>
              <td>{{ item.guige || '-' }}</td>
              <td>{{ item.danwei || '-' }}</td>
              <td>{{ item.kucun ?? 0 }}</td>
              <td>{{ item.changjia || '-' }}</td>
              <td>{{ item.zhuyiShixiang || '-' }}</td>
              <td><button type="button" @click="deleteYaopin(item)">删除</button></td>
            </tr>
            <tr v-if="!yaopinList.length"><td colspan="8" class="kong">暂无药品信息</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="yaopinDialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>新增药品</h3>
        <label><span>药品名称</span><input v-model.trim="yaopinForm.yaopinMingcheng" placeholder="请输入药品名称" /></label>
        <label><span>规格</span><input v-model.trim="yaopinForm.guige" placeholder="例如：0.25g×24片" /></label>
        <label><span>单位</span><input v-model.trim="yaopinForm.danwei" placeholder="盒" /></label>
        <label><span>库存数量</span><input v-model="yaopinForm.kucun" type="number" min="0" /></label>
        <label><span>生产厂家</span><input v-model.trim="yaopinForm.changjia" placeholder="生产厂家" /></label>
        <label><span>备注</span><textarea v-model.trim="yaopinForm.zhuyiShixiang" placeholder="用药注意事项或备注"></textarea></label>
        <div class="modal-actions">
          <button type="button" @click="submitYaopin">保存</button>
          <button type="button" class="plain" @click="yaopinDialogVisible = false">取消</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import {
  addAdminYaopin,
  deleteAdminHuliJilu,
  deleteAdminYaopin,
  getAdminFuyaoList,
  getAdminHuliJiluList,
  getAdminJiankangList,
  getAdminYaopinList
} from '../../api/admin'

const huliQuery = reactive({ laorenXingming: '', huliRenyuanXingming: '', xiangmuMingcheng: '', riqi: '' })
const jiankangQuery = reactive({ laorenXingming: '', riqi: '' })
const fuyaoQuery = reactive({ laorenXingming: '', yaopinMingcheng: '', zhixingZhuangtai: '', riqi: '' })
const yaopinQuery = reactive({ yaopinMingcheng: '' })
const huliList = ref([])
const jiankangList = ref([])
const fuyaoList = ref([])
const yaopinList = ref([])
const yaopinDialogVisible = ref(false)
const yaopinForm = reactive(blankYaopin())
const tishi = ref('')

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 2400)
}

async function loadHuli() {
  const res = await getAdminHuliJiluList(huliQuery)
  huliList.value = res.data || []
}

async function loadJiankang() {
  const res = await getAdminJiankangList(jiankangQuery)
  jiankangList.value = res.data || []
}

async function loadFuyao() {
  const res = await getAdminFuyaoList(fuyaoQuery)
  fuyaoList.value = res.data || []
}

async function loadYaopin() {
  const res = await getAdminYaopinList(yaopinQuery)
  yaopinList.value = res.data || []
}

function resetHuli() {
  Object.assign(huliQuery, { laorenXingming: '', huliRenyuanXingming: '', xiangmuMingcheng: '', riqi: '' })
  loadHuli()
}

function resetJiankang() {
  Object.assign(jiankangQuery, { laorenXingming: '', riqi: '' })
  loadJiankang()
}

function resetFuyao() {
  Object.assign(fuyaoQuery, { laorenXingming: '', yaopinMingcheng: '', zhixingZhuangtai: '', riqi: '' })
  loadFuyao()
}

function resetYaopin() {
  Object.assign(yaopinQuery, { yaopinMingcheng: '' })
  loadYaopin()
}

function blankYaopin() {
  return { yaopinMingcheng: '', guige: '', danwei: '盒', kucun: 0, changjia: '', zhuyiShixiang: '' }
}

function openYaopinAdd() {
  Object.assign(yaopinForm, blankYaopin())
  yaopinDialogVisible.value = true
}

async function submitYaopin() {
  if (!String(yaopinForm.yaopinMingcheng || '').trim()) {
    show('药品名称不能为空')
    return
  }
  if (Number(yaopinForm.kucun || 0) < 0) {
    show('库存数量不能为负数')
    return
  }
  await addAdminYaopin({ ...yaopinForm, kucun: Number(yaopinForm.kucun || 0) })
  yaopinDialogVisible.value = false
  await loadYaopin()
  show('药品已新增')
}

async function deleteHuli(id) {
  if (!window.confirm('确认逻辑删除这条护理记录吗？')) return
  await deleteAdminHuliJilu(id)
  await loadHuli()
  show('护理记录已删除')
}

async function deleteYaopin(item) {
  if (!window.confirm(`确认删除药品“${item.yaopinMingcheng}”吗？`)) return
  await deleteAdminYaopin(item.id)
  await loadYaopin()
  show('药品已删除')
}

onMounted(async () => {
  await Promise.all([loadHuli(), loadJiankang(), loadFuyao(), loadYaopin()])
})
</script>

<style scoped>
.admin-jilu-page { gap: 22px; }
.caozuo-tishi { margin-top: 12px; border: 1px solid #bbf7d0; border-radius: 6px; padding: 9px 12px; color: #166534; background: #f0fdf4; }
.jilu-kuai { display: grid; gap: 14px; border: 1px solid #dce5df; border-radius: 8px; padding: 18px; background: #fff; }
.kuai-heading h3 { margin: 0; }
.kuai-heading p { margin: 6px 0 0; color: #64748b; line-height: 1.6; }
.inline-heading { display: flex; align-items: center; justify-content: space-between; gap: 14px; flex-wrap: wrap; }
.inline-heading button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.kong { color: #94a3b8; text-align: center; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(560px, 100%); display: grid; gap: 14px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel h3 { margin: 0; }
.modal-panel label { display: grid; gap: 8px; color: #334155; font-weight: 700; }
.modal-panel input, .modal-panel textarea { width: 100%; border: 1px solid #cbd5e1; border-radius: 6px; padding: 10px 12px; background: #f8fafc; }
.modal-panel textarea { min-height: 92px; resize: vertical; }
.modal-actions { display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; color: #fff; background: #0f766e; font-weight: 700; }
.modal-actions button.plain { border: 1px solid #cbd5e1; color: #334155; background: #fff; }
</style>
