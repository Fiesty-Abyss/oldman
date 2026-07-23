<template>
  <section class="zhanwei-page zanzhu-guanli-page">
    <div class="page-heading">
      <p class="xitong-tag">管理员端</p>
      <h2>公益赞助管理</h2>
      <p>维护公开赞助展示数据，并处理前台提交的赞助意向。不包含在线支付。</p>
      <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
    </div>

    <div class="tabs">
      <button v-for="tab in tabs" :key="tab.key" type="button" :class="{ active: activeTab === tab.key }" @click="switchTab(tab.key)">
        {{ tab.label }}
      </button>
    </div>

    <section v-if="activeTab === 'ren'" class="guanli-kuai">
      <div class="kuai-heading">
        <h3>赞助人管理</h3>
        <button type="button" @click="openRen()">新增赞助人</button>
      </div>
      <div class="chaxunqu">
        <label><span>关键词</span><input v-model.trim="renQuery.keyword" placeholder="名称 / 联系人 / 电话" /></label>
        <button type="button" @click="loadRen">查询</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>头像</th><th>名称</th><th>类型</th><th>联系人</th><th>电话</th><th>公开</th><th>操作</th></tr></thead>
          <tbody>
            <tr v-for="item in renList" :key="item.id">
              <td>
                <button type="button" class="avatar-button" @click="openImagePreview(avatarUrl(item.touxiangUrl), item.zanzhuRenming)">
                  <img :src="avatarUrl(item.touxiangUrl)" :alt="item.zanzhuRenming" @error="fallbackAvatar" />
                </button>
              </td>
              <td>{{ item.zanzhuRenming }}</td>
              <td>{{ item.zanzhuLeixing }}</td>
              <td>{{ item.lianxiRen || '-' }}</td>
              <td>{{ item.lianxiDianhua || '-' }}</td>
              <td>{{ publicText(item.gongkaiZhuangtai) }}</td>
              <td class="caozuo"><button @click="openRen(item)">编辑</button><button @click="removeRen(item)">删除</button></td>
            </tr>
            <tr v-if="!renList.length"><td colspan="7" class="kong">暂无赞助人数据</td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <section v-if="activeTab === 'jilu'" class="guanli-kuai">
      <div class="kuai-heading">
        <h3>赞助记录管理</h3>
        <button type="button" @click="openJilu()">新增赞助记录</button>
      </div>
      <div class="chaxunqu">
        <label><span>关键词</span><input v-model.trim="jiluQuery.keyword" placeholder="标题 / 赞助人 / 方式" /></label>
        <button type="button" @click="loadJilu">查询</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>标题</th><th>赞助人</th><th>方式</th><th>金额</th><th>日期</th><th>公开</th><th>操作</th></tr></thead>
          <tbody>
            <tr v-for="item in jiluList" :key="item.id">
              <td>{{ item.zanzhuBiaoti }}</td>
              <td>{{ item.zanzhuRenming }}</td>
              <td>{{ item.zanzhuFangshi }}</td>
              <td>{{ item.zanzhuJine }}</td>
              <td>{{ dateOnly(item.zanzhuRiqi) }}</td>
              <td>{{ publicText(item.gongkaiZhuangtai) }}</td>
              <td class="caozuo"><button @click="openJilu(item)">编辑</button><button @click="removeJilu(item)">删除</button></td>
            </tr>
            <tr v-if="!jiluList.length"><td colspan="7" class="kong">暂无赞助记录</td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <section v-if="activeTab === 'wuzi'" class="guanli-kuai">
      <div class="kuai-heading">
        <h3>物资捐赠管理</h3>
        <button type="button" @click="openWuzi()">新增物资</button>
      </div>
      <div class="chaxunqu">
        <label><span>关键词</span><input v-model.trim="wuziQuery.keyword" placeholder="物资 / 类别 / 赞助人" /></label>
        <button type="button" @click="loadWuzi">查询</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>物资</th><th>类别</th><th>数量</th><th>估值</th><th>入库状态</th><th>操作</th></tr></thead>
          <tbody>
            <tr v-for="item in wuziList" :key="item.id">
              <td>{{ item.wuziMingcheng }}</td>
              <td>{{ item.wuziLeibie }}</td>
              <td>{{ item.shuliang }} {{ item.danwei }}</td>
              <td>{{ item.gujiJiazhi }}</td>
              <td>{{ item.rukuZhuangtai }}</td>
              <td class="caozuo"><button @click="openWuzi(item)">编辑</button><button @click="removeWuzi(item)">删除</button></td>
            </tr>
            <tr v-if="!wuziList.length"><td colspan="6" class="kong">暂无物资数据</td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <section v-if="activeTab === 'yongtu'" class="guanli-kuai">
      <div class="kuai-heading">
        <h3>赞助用途管理</h3>
        <button type="button" @click="openYongtu()">新增用途</button>
      </div>
      <div class="chaxunqu">
        <label><span>关键词</span><input v-model.trim="yongtuQuery.keyword" placeholder="用途 / 类别 / 赞助人" /></label>
        <button type="button" @click="loadYongtu">查询</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>用途</th><th>类别</th><th>金额</th><th>日期</th><th>公开</th><th>操作</th></tr></thead>
          <tbody>
            <tr v-for="item in yongtuList" :key="item.id">
              <td>{{ item.yongtuMingcheng }}</td>
              <td>{{ item.yongtuLeibie }}</td>
              <td>{{ item.shiyongJine }}</td>
              <td>{{ dateOnly(item.shiyongRiqi) }}</td>
              <td>{{ publicText(item.gongkaiZhuangtai) }}</td>
              <td class="caozuo"><button @click="openYongtu(item)">编辑</button><button @click="removeYongtu(item)">删除</button></td>
            </tr>
            <tr v-if="!yongtuList.length"><td colspan="6" class="kong">暂无用途数据</td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <section v-if="activeTab === 'yixiang'" class="guanli-kuai">
      <div class="kuai-heading">
        <h3>赞助意向管理</h3>
      </div>
      <div class="chaxunqu">
        <label><span>关键词</span><input v-model.trim="yixiangQuery.keyword" placeholder="姓名 / 电话 / 方式" /></label>
        <label>
          <span>处理状态</span>
          <select v-model="yixiangQuery.chuliZhuangtai">
            <option value="">全部</option>
            <option>待联系</option><option>已联系</option><option>已确认</option><option>已取消</option>
          </select>
        </label>
        <button type="button" @click="loadYixiang">查询</button>
      </div>
      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>姓名</th><th>电话</th><th>方式</th><th>说明</th><th>状态</th><th>提交时间</th><th>操作</th></tr></thead>
          <tbody>
            <tr v-for="item in yixiangList" :key="item.id">
              <td>{{ item.xingming }}</td>
              <td>{{ item.lianxiDianhua }}</td>
              <td>{{ item.zanzhuFangshi }}</td>
              <td>{{ item.zanzhuShuoming || '-' }}</td>
              <td>{{ item.chuliZhuangtai }}</td>
              <td>{{ item.chuangjianShijian }}</td>
              <td class="caozuo"><button @click="openYixiang(item)">处理</button><button @click="removeYixiang(item)">删除</button></td>
            </tr>
            <tr v-if="!yixiangList.length"><td colspan="7" class="kong">暂无赞助意向</td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <div v-if="dialogVisible" class="modal-mask">
      <div class="modal-panel wide">
        <h3>{{ dialogTitle }}</h3>
        <div v-if="dialogType === 'ren'" class="modal-form">
          <label><span>赞助人名称</span><input v-model.trim="renForm.zanzhuRenming" /></label>
          <label><span>赞助人类型</span><input v-model.trim="renForm.zanzhuLeixing" placeholder="个人 / 企业 / 机构" /></label>
          <label><span>联系人</span><input v-model.trim="renForm.lianxiRen" /></label>
          <label><span>联系电话</span><input v-model.trim="renForm.lianxiDianhua" /></label>
          <label><span>邮箱</span><input v-model.trim="renForm.youxiang" /></label>
          <label><span>公开展示</span><select v-model="renForm.gongkaiZhuangtai"><option :value="1">公开</option><option :value="0">不公开</option></select></label>
          <label class="full image-field">
            <span>赞助人头像</span>
            <img class="form-image-preview" :src="avatarUrl(renForm.touxiangUrl)" alt="赞助人头像" @error="fallbackAvatar" />
            <input type="file" accept=".jpg,.jpeg,.png,.webp,image/*" @change="uploadToForm($event, renForm, 'touxiangUrl')" />
          </label>
          <label class="full"><span>简介</span><textarea v-model.trim="renForm.jianjie"></textarea></label>
          <label class="full"><span>地址</span><input v-model.trim="renForm.dizhi" /></label>
        </div>

        <div v-if="dialogType === 'jilu'" class="modal-form">
          <label>
            <span>赞助人</span>
            <select v-model="jiluForm.zanzhuRenId">
              <option value="">请选择</option>
              <option v-for="item in renList" :key="item.id" :value="item.id">{{ item.zanzhuRenming }}</option>
            </select>
          </label>
          <label><span>赞助标题</span><input v-model.trim="jiluForm.zanzhuBiaoti" /></label>
          <label><span>赞助方式</span><select v-model="jiluForm.zanzhuFangshi"><option>资金赞助</option><option>物资捐赠</option><option>志愿服务</option><option>其他</option></select></label>
          <label><span>赞助金额</span><input v-model.number="jiluForm.zanzhuJine" type="number" min="0" /></label>
          <label><span>赞助日期</span><input v-model="jiluForm.zanzhuRiqi" type="date" /></label>
          <label><span>公开展示</span><select v-model="jiluForm.gongkaiZhuangtai"><option :value="1">公开</option><option :value="0">不公开</option></select></label>
          <label class="full"><span>赞助用途</span><input v-model.trim="jiluForm.zanzhuYongtu" /></label>
          <label class="full"><span>赞助说明</span><textarea v-model.trim="jiluForm.zanzhuShuoming"></textarea></label>
        </div>

        <div v-if="dialogType === 'wuzi'" class="modal-form">
          <label>
            <span>关联赞助记录</span>
            <select v-model="wuziForm.zanzhuJiluId">
              <option value="">请选择</option>
              <option v-for="item in jiluList" :key="item.id" :value="item.id">{{ item.zanzhuBiaoti }}</option>
            </select>
          </label>
          <label><span>物资名称</span><input v-model.trim="wuziForm.wuziMingcheng" /></label>
          <label><span>物资类别</span><input v-model.trim="wuziForm.wuziLeibie" /></label>
          <label><span>数量</span><input v-model.number="wuziForm.shuliang" type="number" min="0" /></label>
          <label><span>单位</span><input v-model.trim="wuziForm.danwei" /></label>
          <label><span>估计价值</span><input v-model.number="wuziForm.gujiJiazhi" type="number" min="0" /></label>
          <label><span>保质期</span><input v-model="wuziForm.baozhiqi" type="date" /></label>
          <label><span>入库状态</span><select v-model="wuziForm.rukuZhuangtai"><option>已入库</option><option>待入库</option><option>已发放</option></select></label>
          <label class="full"><span>备注</span><textarea v-model.trim="wuziForm.beizhu"></textarea></label>
        </div>

        <div v-if="dialogType === 'yongtu'" class="modal-form">
          <label>
            <span>关联赞助记录</span>
            <select v-model="yongtuForm.zanzhuJiluId">
              <option value="">请选择</option>
              <option v-for="item in jiluList" :key="item.id" :value="item.id">{{ item.zanzhuBiaoti }}</option>
            </select>
          </label>
          <label><span>用途名称</span><input v-model.trim="yongtuForm.yongtuMingcheng" /></label>
          <label><span>用途类别</span><input v-model.trim="yongtuForm.yongtuLeibie" /></label>
          <label><span>使用金额</span><input v-model.number="yongtuForm.shiyongJine" type="number" min="0" /></label>
          <label><span>使用日期</span><input v-model="yongtuForm.shiyongRiqi" type="date" /></label>
          <label><span>公开展示</span><select v-model="yongtuForm.gongkaiZhuangtai"><option :value="1">公开</option><option :value="0">不公开</option></select></label>
          <label class="full"><span>使用说明</span><textarea v-model.trim="yongtuForm.shiyongShuoming"></textarea></label>
        </div>

        <div v-if="dialogType === 'yixiang'" class="modal-form">
          <label><span>赞助人</span><input v-model="yixiangForm.xingming" disabled /></label>
          <label><span>联系电话</span><input v-model="yixiangForm.lianxiDianhua" disabled /></label>
          <label><span>赞助方式</span><input v-model="yixiangForm.zanzhuFangshi" disabled /></label>
          <label>
            <span>处理状态</span>
            <select v-model="yixiangForm.chuliZhuangtai">
              <option>待联系</option><option>已联系</option><option>已确认</option><option>已取消</option>
            </select>
          </label>
          <label class="full"><span>赞助说明</span><textarea v-model="yixiangForm.zanzhuShuoming" disabled></textarea></label>
          <label class="full"><span>处理备注</span><textarea v-model.trim="yixiangForm.chuliBeizhu"></textarea></label>
        </div>

        <div class="modal-actions">
          <button type="button" @click="saveDialog">保存</button>
          <button type="button" class="plain" @click="dialogVisible = false">取消</button>
        </div>
      </div>
    </div>

    <div v-if="imagePreviewVisible" class="modal-mask" @click.self="imagePreviewVisible = false">
      <div class="modal-panel image-preview-panel">
        <h3>{{ imagePreviewTitle }}</h3>
        <img :src="imagePreviewUrl" :alt="imagePreviewTitle" @error="fallbackAvatar" />
        <div class="modal-actions">
          <button type="button" class="plain" @click="imagePreviewVisible = false">关闭</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import {
  addAdminZanzhuJilu,
  addAdminZanzhuRen,
  addAdminZanzhuWuzi,
  addAdminZanzhuYongtu,
  deleteAdminZanzhuJilu,
  deleteAdminZanzhuRen,
  deleteAdminZanzhuWuzi,
  deleteAdminZanzhuYixiang,
  deleteAdminZanzhuYongtu,
  getAdminZanzhuJiluList,
  getAdminZanzhuRenList,
  getAdminZanzhuWuziList,
  getAdminZanzhuYixiangDetail,
  getAdminZanzhuYixiangList,
  getAdminZanzhuYongtuList,
  updateAdminZanzhuJilu,
  updateAdminZanzhuRen,
  updateAdminZanzhuWuzi,
  updateAdminZanzhuYixiangStatus,
  updateAdminZanzhuYongtu
} from '../../api/zanzhu'
import { uploadImage } from '../../api/wenjian'

const tabs = [
  { key: 'ren', label: '赞助人管理' },
  { key: 'jilu', label: '赞助记录管理' },
  { key: 'wuzi', label: '物资捐赠管理' },
  { key: 'yongtu', label: '赞助用途管理' },
  { key: 'yixiang', label: '赞助意向管理' }
]

const activeTab = ref('ren')
const tishi = ref('')
const dialogVisible = ref(false)
const dialogType = ref('')
const imagePreviewVisible = ref(false)
const imagePreviewUrl = ref('')
const imagePreviewTitle = ref('')
const renList = ref([])
const jiluList = ref([])
const wuziList = ref([])
const yongtuList = ref([])
const yixiangList = ref([])

const renQuery = reactive({ keyword: '' })
const jiluQuery = reactive({ keyword: '' })
const wuziQuery = reactive({ keyword: '' })
const yongtuQuery = reactive({ keyword: '' })
const yixiangQuery = reactive({ keyword: '', chuliZhuangtai: '' })

const renForm = reactive(blankRen())
const jiluForm = reactive(blankJilu())
const wuziForm = reactive(blankWuzi())
const yongtuForm = reactive(blankYongtu())
const yixiangForm = reactive(blankYixiang())
const donorFallback = '/uploads/zanzhu/donor-default.jpg'

const dialogTitle = computed(() => {
  const map = { ren: '赞助人信息', jilu: '赞助记录', wuzi: '物资捐赠', yongtu: '赞助用途', yixiang: '处理赞助意向' }
  return map[dialogType.value] || '公益赞助管理'
})

function blankRen() {
  return { id: '', zanzhuRenming: '', zanzhuLeixing: '个人', lianxiRen: '', lianxiDianhua: '', youxiang: '', dizhi: '', jianjie: '', touxiangUrl: '/uploads/zanzhu/donor-default.jpg', gongkaiZhuangtai: 1 }
}

function blankJilu() {
  return { id: '', zanzhuRenId: '', zanzhuBiaoti: '', zanzhuFangshi: '资金赞助', zanzhuJine: 0, zanzhuRiqi: '', zanzhuYongtu: '', zanzhuShuoming: '', pingzhengTupian: null, gongkaiZhuangtai: 1, shenheZhuangtai: '已确认' }
}

function blankWuzi() {
  return { id: '', zanzhuJiluId: '', wuziMingcheng: '', wuziLeibie: '', shuliang: 1, danwei: '件', gujiJiazhi: 0, baozhiqi: '', rukuZhuangtai: '已入库', tupianUrl: null, beizhu: '' }
}

function blankYongtu() {
  return { id: '', zanzhuJiluId: '', yongtuMingcheng: '', yongtuLeibie: '', shiyongJine: 0, shiyongRiqi: '', shiyongShuoming: '', gongkaiZhuangtai: 1, pingzhengTupian: null }
}

function blankYixiang() {
  return { id: '', xingming: '', lianxiDianhua: '', zanzhuFangshi: '', zanzhuShuoming: '', chuliZhuangtai: '待联系', chuliBeizhu: '' }
}

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 2600)
}

function publicText(value) {
  return Number(value) === 1 ? '公开' : '不公开'
}

function dateOnly(value) {
  return value ? value.toString().slice(0, 10) : ''
}

function avatarUrl(value) {
  return value || donorFallback
}

function fallbackAvatar(event) {
  if (event.target.src.includes(donorFallback)) return
  event.target.src = donorFallback
}

function openImagePreview(url, title) {
  imagePreviewUrl.value = avatarUrl(url)
  imagePreviewTitle.value = title || '图片预览'
  imagePreviewVisible.value = true
}

async function uploadToForm(event, targetForm, fieldName) {
  const file = event.target.files?.[0]
  if (!file) return
  try {
    const res = await uploadImage(file, 'zanzhu')
    targetForm[fieldName] = res.data?.url || ''
    show('图片上传成功')
  } catch (error) {
    show(error.message || '图片上传失败')
  } finally {
    event.target.value = ''
  }
}

function switchTab(key) {
  activeTab.value = key
  loadCurrent()
}

async function loadRen() {
  const res = await getAdminZanzhuRenList(renQuery)
  renList.value = res.data || []
}

async function loadJilu() {
  const res = await getAdminZanzhuJiluList(jiluQuery)
  jiluList.value = res.data || []
}

async function loadWuzi() {
  const res = await getAdminZanzhuWuziList(wuziQuery)
  wuziList.value = res.data || []
}

async function loadYongtu() {
  const res = await getAdminZanzhuYongtuList(yongtuQuery)
  yongtuList.value = res.data || []
}

async function loadYixiang() {
  const res = await getAdminZanzhuYixiangList(yixiangQuery)
  yixiangList.value = res.data || []
}

async function loadCurrent() {
  if (activeTab.value === 'ren') await loadRen()
  if (activeTab.value === 'jilu') await Promise.all([loadRen(), loadJilu()])
  if (activeTab.value === 'wuzi') await Promise.all([loadJilu(), loadWuzi()])
  if (activeTab.value === 'yongtu') await Promise.all([loadJilu(), loadYongtu()])
  if (activeTab.value === 'yixiang') await loadYixiang()
}

function openRen(item) {
  Object.assign(renForm, blankRen(), item || {})
  dialogType.value = 'ren'
  dialogVisible.value = true
}

function openJilu(item) {
  Object.assign(jiluForm, blankJilu(), item || {}, { zanzhuRiqi: dateOnly(item?.zanzhuRiqi), pingzhengTupian: null })
  dialogType.value = 'jilu'
  dialogVisible.value = true
}

function openWuzi(item) {
  Object.assign(wuziForm, blankWuzi(), item || {}, { baozhiqi: dateOnly(item?.baozhiqi), tupianUrl: null })
  dialogType.value = 'wuzi'
  dialogVisible.value = true
}

function openYongtu(item) {
  Object.assign(yongtuForm, blankYongtu(), item || {}, { shiyongRiqi: dateOnly(item?.shiyongRiqi), pingzhengTupian: null })
  dialogType.value = 'yongtu'
  dialogVisible.value = true
}

async function openYixiang(item) {
  const res = await getAdminZanzhuYixiangDetail(item.id)
  Object.assign(yixiangForm, blankYixiang(), res.data || {})
  dialogType.value = 'yixiang'
  dialogVisible.value = true
}

async function saveDialog() {
  try {
    if (dialogType.value === 'ren') renForm.id ? await updateAdminZanzhuRen(renForm) : await addAdminZanzhuRen(renForm)
    if (dialogType.value === 'jilu') jiluForm.id ? await updateAdminZanzhuJilu(jiluForm) : await addAdminZanzhuJilu(jiluForm)
    if (dialogType.value === 'wuzi') wuziForm.id ? await updateAdminZanzhuWuzi(wuziForm) : await addAdminZanzhuWuzi(wuziForm)
    if (dialogType.value === 'yongtu') yongtuForm.id ? await updateAdminZanzhuYongtu(yongtuForm) : await addAdminZanzhuYongtu(yongtuForm)
    if (dialogType.value === 'yixiang') await updateAdminZanzhuYixiangStatus(yixiangForm)
    dialogVisible.value = false
    await loadCurrent()
    show('保存成功')
  } catch (error) {
    show(error.message)
  }
}

async function removeRen(item) {
  if (!window.confirm(`确认删除赞助人 ${item.zanzhuRenming}？`)) return
  await deleteAdminZanzhuRen(item.id)
  await loadRen()
  show('删除成功')
}

async function removeJilu(item) {
  if (!window.confirm(`确认删除赞助记录 ${item.zanzhuBiaoti}？`)) return
  await deleteAdminZanzhuJilu(item.id)
  await loadJilu()
  show('删除成功')
}

async function removeWuzi(item) {
  if (!window.confirm(`确认删除物资 ${item.wuziMingcheng}？`)) return
  await deleteAdminZanzhuWuzi(item.id)
  await loadWuzi()
  show('删除成功')
}

async function removeYongtu(item) {
  if (!window.confirm(`确认删除用途 ${item.yongtuMingcheng}？`)) return
  await deleteAdminZanzhuYongtu(item.id)
  await loadYongtu()
  show('删除成功')
}

async function removeYixiang(item) {
  if (!window.confirm(`确认删除 ${item.xingming} 的赞助意向？`)) return
  await deleteAdminZanzhuYixiang(item.id)
  await loadYixiang()
  show('删除成功')
}

onMounted(loadCurrent)
</script>

<style scoped>
.zanzhu-guanli-page {
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
.tabs {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  border-bottom: 1px solid #dce5df;
}
.tabs button {
  border: 1px solid #dce5df;
  border-bottom: 0;
  border-radius: 6px 6px 0 0;
  padding: 10px 18px;
  color: #334155;
  background: #fff;
  font-weight: 700;
}
.tabs button.active {
  color: #fff;
  background: #0f766e;
}
.guanli-kuai {
  display: grid;
  gap: 14px;
  border: 1px solid #dce5df;
  border-radius: 8px;
  padding: 18px;
  background: #fff;
}
.kuai-heading {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}
.kuai-heading h3 {
  margin: 0;
}
.caozuo {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}
.avatar-button {
  width: 48px;
  height: 48px;
  padding: 0;
  border-radius: 50%;
  overflow: hidden;
  background: #eef5f1;
}
.avatar-button img,
.form-image-preview {
  width: 100%;
  height: 100%;
  display: block;
  object-fit: cover;
}
.form-image-preview {
  width: 92px;
  height: 92px;
  border: 1px solid #dce5df;
  border-radius: 8px;
  background: #eef5f1;
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
  width: min(840px, 100%);
  display: grid;
  gap: 16px;
  border-radius: 8px;
  padding: 22px;
  background: #fff;
  box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24);
}
.modal-panel h3 {
  margin: 0;
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
.image-field input[type='file'] {
  padding: 8px 10px;
  background: #ffffff;
}
.image-preview-panel {
  width: min(680px, 100%);
}
.image-preview-panel img {
  width: 100%;
  max-height: 68vh;
  border-radius: 8px;
  object-fit: contain;
  background: #eef5f1;
}
.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
.modal-actions button {
  min-height: 38px;
  border-radius: 6px;
  padding: 0 16px;
  color: #fff;
  background: #0f766e;
  font-weight: 700;
}
.modal-actions button.plain {
  border: 1px solid #cbd5e1;
  color: #334155;
  background: #fff;
}
@media (max-width: 760px) {
  .modal-form {
    grid-template-columns: 1fr;
  }
}
</style>
