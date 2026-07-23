<template>
  <section class="zhanwei-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>护理员管理</h2>
      <p>维护专业护理员、后勤人员、志愿者和培训员工资料，并给护理员分配服务老人。</p>
    </div>

    <div class="chaxunqu">
      <label><span>工号</span><input v-model="form.gonghao" placeholder="HL2026001" /></label>
      <label><span>姓名</span><input v-model="form.xingming" placeholder="请输入姓名" /></label>
      <label><span>岗位</span><select v-model="form.zhicheng"><option>专业护理员</option><option>后勤人员</option><option>志愿者</option><option>培训员工</option></select></label>
      <button type="button" @click="save">{{ form.id ? '修改' : '新增' }}</button>
      <button type="button" class="plain" @click="reset">清空</button>
    </div>

    <div class="biaoge-wrap">
      <table>
        <thead><tr><th>工号</th><th>姓名</th><th>性别</th><th>手机</th><th>岗位</th><th>状态</th><th>操作</th></tr></thead>
        <tbody>
          <tr v-for="item in list" :key="item.id">
            <td>{{ item.gonghao }}</td><td>{{ item.xingming }}</td><td>{{ item.xingbie }}</td><td>{{ item.shouji }}</td><td>{{ item.zhicheng }}</td><td>{{ item.zhuangtai }}</td>
            <td><button @click="edit(item)">编辑</button><button @click="remove(item.id)">离职</button><button @click="selectHuli(item)">分配老人</button></td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-if="selected" class="page-heading">
      <h2>给 {{ selected.xingming }} 分配老人</h2>
      <div class="chaxunqu inline">
        <label><span>老人姓名</span><input v-model.trim="assignQuery.laorenXingming" placeholder="老人姓名" /></label>
        <button type="button" @click="loadUnassignedLaoren">查询可分配老人</button>
        <button type="button" class="plain" @click="resetAssignQuery">重置</button>
      </div>
      <p>当前服务老人：{{ fuwuList.map((item) => item.laorenXingming).join('、') || '暂无' }}</p>
      <div class="biaoge-wrap assign-table">
        <table>
          <thead><tr><th>ID</th><th>编号</th><th>姓名</th><th>性别</th><th>年龄</th><th>入住状态</th><th>操作</th></tr></thead>
          <tbody>
            <tr v-for="item in unassignedList" :key="item.id">
              <td>{{ item.id }}</td>
              <td>{{ item.laorenBianhao }}</td>
              <td>{{ item.xingming }}</td>
              <td>{{ item.xingbie }}</td>
              <td>{{ item.nianling || '-' }}</td>
              <td>{{ item.ruzhuZhuangtai }}</td>
              <td><button type="button" @click="assign(item)">分配</button></td>
            </tr>
            <tr v-if="!unassignedList.length"><td colspan="7" class="kong">暂无可分配老人</td></tr>
          </tbody>
        </table>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { assignLaoren, addHuliRenyuan, deleteHuliRenyuan, getHuliFuwuLaoren, getHuliRenyuanList, getUnassignedLaorenOptions, updateHuliRenyuan } from '../../api/huli'

const list = ref([])
const selected = ref(null)
const fuwuList = ref([])
const unassignedList = ref([])
const form = reactive({ id: '', gonghao: '', xingming: '', xingbie: '女', shouji: '', zhicheng: '专业护理员', ruzhiRiqi: '', zhuangtai: '在职', beizhu: '' })
const assignForm = reactive({ huliRenyuanId: '', laorenId: '' })
const assignQuery = reactive({ laorenXingming: '' })

async function load() {
  const res = await getHuliRenyuanList()
  list.value = res.data || []
}

function reset() {
  Object.assign(form, { id: '', gonghao: '', xingming: '', xingbie: '女', shouji: '', zhicheng: '专业护理员', ruzhiRiqi: '', zhuangtai: '在职', beizhu: '' })
}

function edit(item) {
  Object.assign(form, item)
}

async function save() {
  if (!String(form.xingming || '').trim()) return
  if (!String(form.shouji || '').trim()) return
  if (form.id) await updateHuliRenyuan(form)
  else await addHuliRenyuan(form)
  reset()
  await load()
}

async function remove(id) {
  await deleteHuliRenyuan(id)
  await load()
}

async function selectHuli(item) {
  selected.value = item
  assignForm.huliRenyuanId = item.id
  const res = await getHuliFuwuLaoren(item.id)
  fuwuList.value = res.data || []
  await loadUnassignedLaoren()
}

async function loadUnassignedLaoren() {
  if (!selected.value) return
  const res = await getUnassignedLaorenOptions(selected.value.id, assignQuery)
  unassignedList.value = res.data || []
}

function resetAssignQuery() {
  assignQuery.laorenXingming = ''
  loadUnassignedLaoren()
}

async function assign(item) {
  assignForm.laorenId = item.id
  await assignLaoren(assignForm)
  await selectHuli(selected.value)
}

onMounted(load)
</script>

<style scoped>
.assign-table { margin-top: 14px; }
.kong { color: #94a3b8; text-align: center; }
</style>
