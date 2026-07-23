<template>
  <main class="duiwai-form-page">
    <section class="duiwai-form ruzhu-form">
      <p class="xitong-tag">在线入住申请</p>
      <h1>提交入住意向</h1>
      <p>提交后进入待审核状态；审核通过后只建立基础档案，正式入住和床位安排仍由管理员办理。</p>
      <p v-if="tishi" :class="['form-tishi', tishiLeixing]">{{ tishi }}</p>

      <div class="duiwai-form-grid">
        <label><span>老人姓名</span><input v-model.trim="form.laorenXingming" placeholder="请输入老人姓名" /></label>
        <label>
          <span>性别</span>
          <select v-model="form.xingbie">
            <option value="">请选择</option>
            <option>男</option>
            <option>女</option>
          </select>
        </label>
        <label><span>出生日期</span><input v-model="form.chushengRiqi" type="date" /></label>
        <label><span>年龄</span><input v-model="form.nianling" type="number" min="0" /></label>
        <label><span>身份证号</span><input v-model.trim="form.shenfenzhenghao" placeholder="可选" /></label>
        <label><span>联系电话</span><input v-model.trim="form.lianxiDianhua" placeholder="老人或家庭联系电话" /></label>
        <label class="full"><span>家庭住址</span><input v-model.trim="form.jiatingZhuzhi" placeholder="请输入家庭住址" /></label>
        <label><span>血型</span><input v-model.trim="form.xuexing" placeholder="可选" /></label>
        <label><span>期望入住时间</span><input v-model="form.qiwangRuzhuShijian" type="date" /></label>
        <label><span>家属姓名</span><input v-model.trim="form.jiashuXingming" placeholder="请输入家属姓名" /></label>
        <label><span>家属手机号</span><input v-model.trim="form.jiashuShouji" placeholder="请输入家属手机号" /></label>
        <label><span>与老人关系</span><input v-model.trim="form.yuLaorenGuanxi" placeholder="例如：女儿、儿子" /></label>
        <label class="full"><span>过敏史</span><textarea v-model.trim="form.guominshi" placeholder="如无可不填"></textarea></label>
        <label class="full"><span>既往病史</span><textarea v-model.trim="form.jiwangbingshi" placeholder="请简要说明既往病史"></textarea></label>
        <label class="full"><span>申请护理需求</span><textarea v-model.trim="form.huliXuqiu" placeholder="请简要说明身体情况、护理需求或入住时间"></textarea></label>
        <label class="full"><span>备注</span><textarea v-model.trim="form.beizhu" placeholder="其他补充说明"></textarea></label>
      </div>

      <button type="button" :disabled="loading" @click="submit">{{ loading ? '提交中...' : '提交申请' }}</button>
      <div class="duiwai-links">
        <RouterLink to="/">返回首页</RouterLink>
        <RouterLink to="/denglu">登录入口</RouterLink>
      </div>
    </section>
  </main>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ruzhuShenqingAdd } from '../../api/gongkai'

const form = reactive({
  laorenXingming: '',
  xingbie: '',
  chushengRiqi: '',
  nianling: '',
  shenfenzhenghao: '',
  lianxiDianhua: '',
  jiatingZhuzhi: '',
  xuexing: '',
  guominshi: '',
  jiwangbingshi: '',
  huliXuqiu: '',
  qiwangRuzhuShijian: '',
  jiashuXingming: '',
  jiashuShouji: '',
  yuLaorenGuanxi: '',
  beizhu: ''
})
const loading = ref(false)
const tishi = ref('')
const tishiLeixing = ref('success')

function show(message, type = 'success') {
  tishi.value = message
  tishiLeixing.value = type
}

function validate() {
  const required = [
    ['laorenXingming', '请输入老人姓名'],
    ['jiashuXingming', '请输入家属姓名'],
    ['jiashuShouji', '请输入家属手机号']
  ]
  for (const [key, message] of required) {
    if (!String(form[key] || '').trim()) {
      show(message, 'error')
      return false
    }
  }
  return true
}

async function submit() {
  if (!validate()) return
  loading.value = true
  try {
    await ruzhuShenqingAdd({ ...form })
    show('入住申请已提交，请等待管理员审核')
  } catch (error) {
    show(error.message || '提交失败', 'error')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.ruzhu-form { width: min(860px, 100%); }
.duiwai-form-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 14px; }
.duiwai-form-grid .full { grid-column: 1 / -1; }
.duiwai-form select { width: 100%; border: 1px solid #cbd5e1; border-radius: 6px; padding: 10px 12px; background: #f8fafc; }
.form-tishi { margin: 0; border-radius: 6px; padding: 10px 12px; }
.form-tishi.success { border: 1px solid #bbf7d0; color: #166534; background: #f0fdf4; }
.form-tishi.error { border: 1px solid #fecaca; color: #991b1b; background: #fff1f2; }
.duiwai-links { display: flex; flex-wrap: wrap; gap: 10px; }
@media (max-width: 680px) { .duiwai-form-grid { grid-template-columns: 1fr; } }
</style>
