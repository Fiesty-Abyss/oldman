<template>
  <main class="duiwai-page">
    <section class="duiwai-hero zanzhu-hero">
      <div>
        <p class="xitong-tag">公益赞助</p>
        <h1>让老人有所依，让照护者有所为</h1>
        <p>让无处可去的人重新拥有生活的位置。每一笔赞助、每一份物资，都会尽量公开用途和流向。</p>
        <div class="duiwai-actions">
          <RouterLink to="/">返回首页</RouterLink>
          <button type="button" @click="openYixiang">我要赞助</button>
          <RouterLink to="/denglu" class="secondary">登录后台</RouterLink>
        </div>
      </div>
    </section>

    <section class="duiwai-section">
      <h2>赞助说明</h2>
      <p>欧德曼养老院接受资金、物资、服务和志愿陪伴等多种形式赞助，优先用于困难老人生活支持、护理用品补充、健康监测设备、环境维护和员工培训。</p>
    </section>

    <section class="duiwai-grid">
      <article>
        <h3>赞助用途</h3>
        <p>改善老人居住环境、采购护理用品、补贴困难老人餐费护理费、组织陪伴活动。</p>
      </article>
      <article>
        <h3>可赞助项目</h3>
        <p>防滑用品、护理垫、血压计、血糖仪、节日慰问物资、公益岗位培训。</p>
      </article>
      <article>
        <h3>物资捐赠</h3>
        <p>食品、衣物、护理用品和设备需要登记名称、数量、保质期、入库状态和估值。</p>
      </article>
    </section>

    <section class="duiwai-section">
      <h2>公开赞助记录</h2>
      <div class="gongkai-list">
        <article v-for="item in gongkaiList" :key="item.zanzhuJiluId" class="zanzhu-record">
          <img :src="imageOrDefault(item.touxiangUrl, defaultImages.donor)" alt="赞助人头像" @error="fallbackImage($event, defaultImages.donor)" />
          <div>
            <strong>{{ item.zanzhuBiaoti }}</strong>
            <span>{{ item.zanzhuRenming }} · {{ item.zanzhuFangshi }} · {{ item.zanzhuRiqi }}</span>
            <p>{{ item.zanzhuShuoming || item.zanzhuYongtu || '感谢这份支持。' }}</p>
          </div>
        </article>
        <p v-if="!gongkaiList.length" class="kong">暂无公开赞助记录</p>
      </div>
    </section>

    <section class="duiwai-section">
      <h2>赞助用途公开</h2>
      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>用途</th><th>类别</th><th>金额</th><th>日期</th><th>说明</th></tr></thead>
          <tbody>
            <tr v-for="item in yongtuList" :key="item.yongtuId">
              <td>{{ item.yongtuMingcheng }}</td>
              <td>{{ item.yongtuLeibie }}</td>
              <td>{{ item.shiyongJine }}</td>
              <td>{{ item.shiyongRiqi }}</td>
              <td>{{ item.shiyongShuoming || '-' }}</td>
            </tr>
            <tr v-if="!yongtuList.length"><td colspan="5" class="kong">暂无公开用途</td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <section class="duiwai-section">
      <h2>物资捐赠展示</h2>
      <div class="biaoge-wrap">
        <table>
          <thead><tr><th>物资</th><th>类别</th><th>数量</th><th>估值</th><th>入库状态</th><th>赞助方</th></tr></thead>
          <tbody>
            <tr v-for="item in wuziList" :key="item.wuziId">
              <td>{{ item.wuziMingcheng }}</td>
              <td>{{ item.wuziLeibie }}</td>
              <td>{{ item.shuliang }} {{ item.danwei }}</td>
              <td>{{ item.gujiJiazhi }}</td>
              <td>{{ item.rukuZhuangtai }}</td>
              <td>{{ item.zanzhuRenming }}</td>
            </tr>
            <tr v-if="!wuziList.length"><td colspan="6" class="kong">暂无物资捐赠记录</td></tr>
          </tbody>
        </table>
      </div>
    </section>

    <section class="duiwai-section zanzhu-action-section">
      <h2>愿意提供帮助？</h2>
      <p>请先提交赞助意向，工作人员后续将联系您确认具体方式。本页面仅收集赞助意向，不处理付款。</p>
      <button type="button" @click="openYixiang">我要赞助</button>
    </section>

    <div v-if="dialogVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>提交赞助意向</h3>
        <p v-if="tishi" class="caozuo-tishi">{{ tishi }}</p>
        <div class="modal-form">
          <label><span>赞助人姓名</span><input v-model.trim="form.xingming" /></label>
          <label><span>联系电话</span><input v-model.trim="form.lianxiDianhua" /></label>
          <label>
            <span>赞助方式</span>
            <select v-model="form.zanzhuFangshi">
              <option>资金赞助</option>
              <option>物资捐赠</option>
              <option>志愿服务</option>
              <option>其他</option>
            </select>
          </label>
          <label class="full"><span>赞助说明</span><textarea v-model.trim="form.zanzhuShuoming"></textarea></label>
          <label class="full"><span>备注</span><textarea v-model.trim="form.beizhu"></textarea></label>
        </div>
        <div class="modal-actions">
          <button type="button" @click="submitYixiang">提交</button>
          <button type="button" class="plain" @click="dialogVisible = false">取消</button>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { addZanzhuYixiang, getZanzhuGongkaiList, getZanzhuWuziList, getZanzhuYongtuList } from '../../api/zanzhu'

const gongkaiList = ref([])
const yongtuList = ref([])
const wuziList = ref([])
const dialogVisible = ref(false)
const tishi = ref('')
const form = reactive(blankForm())
const defaultImages = {
  donor: '/uploads/zanzhu/donor-default.jpg'
}

function blankForm() {
  return { xingming: '', lianxiDianhua: '', zanzhuFangshi: '资金赞助', zanzhuShuoming: '', beizhu: '' }
}

function show(message) {
  tishi.value = message
  window.setTimeout(() => { if (tishi.value === message) tishi.value = '' }, 3000)
}

function imageOrDefault(path, fallback) {
  return path || fallback
}

function fallbackImage(event, fallback) {
  if (event.target.src.endsWith(fallback)) return
  event.target.src = fallback
}

function openYixiang() {
  Object.assign(form, blankForm())
  tishi.value = ''
  dialogVisible.value = true
}

async function submitYixiang() {
  if (!form.xingming || !form.lianxiDianhua || !form.zanzhuFangshi) {
    show('请填写赞助人姓名、联系电话和赞助方式')
    return
  }
  try {
    await addZanzhuYixiang({ ...form })
    show('提交成功，工作人员后续将联系您')
    window.setTimeout(() => {
      dialogVisible.value = false
    }, 900)
  } catch (error) {
    show(error.message)
  }
}

async function loadData() {
  const [gongkai, yongtu, wuzi] = await Promise.all([
    getZanzhuGongkaiList(),
    getZanzhuYongtuList(),
    getZanzhuWuziList()
  ])
  gongkaiList.value = gongkai.data || []
  yongtuList.value = yongtu.data || []
  wuziList.value = wuzi.data || []
}

onMounted(loadData)
</script>

<style scoped>
.zanzhu-action-section {
  display: grid;
  gap: 12px;
}
.zanzhu-action-section button,
.duiwai-actions button {
  min-height: 42px;
  border: 0;
  border-radius: 6px;
  padding: 0 18px;
  color: #fff;
  background: #0f766e;
  font-weight: 700;
  cursor: pointer;
}
.kong {
  color: #64748b;
  text-align: center;
}
.zanzhu-record {
  grid-template-columns: 132px minmax(0, 1fr);
  align-items: center;
}
.zanzhu-record img {
  width: 132px;
  height: 92px;
  display: block;
  border-radius: 6px;
  object-fit: cover;
  background: #eef5f1;
}
.zanzhu-record > div {
  display: grid;
  gap: 6px;
}
.modal-mask {
  position: fixed;
  inset: 0;
  z-index: 30;
  display: grid;
  place-items: center;
  padding: 20px;
  background: rgba(15, 23, 42, 0.42);
}
.modal-panel {
  width: min(680px, 100%);
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
.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
.modal-actions button {
  min-height: 38px;
  border: 0;
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
.caozuo-tishi {
  border: 1px solid #bbf7d0;
  border-radius: 6px;
  padding: 9px 12px;
  color: #166534;
  background: #f0fdf4;
}
@media (max-width: 760px) {
  .modal-form {
    grid-template-columns: 1fr;
  }
  .zanzhu-record {
    grid-template-columns: 1fr;
  }
  .zanzhu-record img {
    width: 100%;
    height: auto;
    aspect-ratio: 4 / 3;
  }
}
</style>
