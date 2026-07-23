<template>
  <main class="duiwai-form-page zhuce-page">
    <section class="duiwai-form">
      <p class="xitong-tag">家属注册</p>
      <h1>创建家属账号</h1>
      <p>注册成功后可直接使用账号登录家属端；老人档案先标记为未入住，正式入住仍由管理员办理。</p>
      <p v-if="tishi" :class="['form-tishi', tishiLeixing]">{{ tishi }}</p>

      <div class="duiwai-form-grid">
        <label><span>登录账号</span><input v-model.trim="form.dengluZhanghao" placeholder="请输入登录账号" /></label>
        <label><span>登录密码</span><input v-model.trim="form.mima" type="password" placeholder="请输入登录密码" /></label>
        <label><span>家属姓名</span><input v-model.trim="form.jiashuXingming" placeholder="请输入家属姓名" /></label>
        <label><span>手机号</span><input v-model.trim="form.shouji" placeholder="请输入手机号" /></label>
        <label><span>与老人关系</span><input v-model.trim="form.guanxi" placeholder="例如：女儿、儿子" /></label>
        <label><span>联系地址</span><input v-model.trim="form.dizhi" placeholder="请输入联系地址" /></label>
        <label><span>老人姓名</span><input v-model.trim="form.laorenXingming" placeholder="请输入老人姓名" /></label>
        <label>
          <span>老人性别</span>
          <select v-model="form.xingbie">
            <option>男</option>
            <option>女</option>
            <option>未填写</option>
          </select>
        </label>
        <label><span>老人年龄</span><input v-model="form.nianling" type="number" min="0" placeholder="可选" /></label>
        <label><span>老人电话</span><input v-model.trim="form.lianxiDianhua" placeholder="可选" /></label>
        <label class="full"><span>老人家庭住址</span><input v-model.trim="form.jiatingZhuzhi" placeholder="可选，默认可与联系地址一致" /></label>
      </div>

      <HuakuaiYanzheng v-model="yanzhengTongguo" :reset-key="yanzhengResetKey" />

      <button type="button" :disabled="loading || !yanzhengTongguo" @click="submit">{{ loading ? '提交中...' : '提交注册' }}</button>
      <div class="duiwai-links">
        <RouterLink to="/denglu">去登录</RouterLink>
        <RouterLink to="/">返回首页</RouterLink>
      </div>
    </section>
  </main>
</template>

<script setup>
import { reactive, ref, watch } from 'vue'
import { jiashuZhuce } from '../../api/gongkai'
import HuakuaiYanzheng from '../../components/HuakuaiYanzheng.vue'

const form = reactive({
  dengluZhanghao: '',
  mima: '',
  jiashuXingming: '',
  shouji: '',
  guanxi: '',
  dizhi: '',
  laorenXingming: '',
  xingbie: '未填写',
  nianling: '',
  lianxiDianhua: '',
  jiatingZhuzhi: ''
})
const loading = ref(false)
const tishi = ref('')
const tishiLeixing = ref('success')
const yanzhengTongguo = ref(false)
const yanzhengResetKey = ref(0)

function show(message, type = 'success') {
  tishi.value = message
  tishiLeixing.value = type
}

function validate() {
  if (!yanzhengTongguo.value) {
    show('请先完成滑块验证', 'error')
    return false
  }
  const required = [
    ['dengluZhanghao', '请输入登录账号'],
    ['mima', '请输入登录密码'],
    ['jiashuXingming', '请输入家属姓名'],
    ['shouji', '请输入手机号'],
    ['guanxi', '请输入与老人关系'],
    ['laorenXingming', '请输入老人姓名']
  ]
  for (const [key, message] of required) {
    if (!String(form[key] || '').trim()) {
      show(message, 'error')
      return false
    }
  }
  return true
}

function resetYanzheng() {
  yanzhengResetKey.value += 1
}

watch(() => [form.dengluZhanghao, form.mima, form.shouji], () => {
  if (yanzhengTongguo.value) resetYanzheng()
})

async function submit() {
  if (!validate()) return
  loading.value = true
  try {
    const payload = { ...form }
    if (!payload.jiatingZhuzhi) payload.jiatingZhuzhi = payload.dizhi
    await jiashuZhuce(payload)
    show('注册成功，请使用新账号登录家属端')
  } catch (error) {
    show(error.message || '注册失败', 'error')
    resetYanzheng()
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.zhuce-page {
  background:
    radial-gradient(circle at 12% 10%, rgba(199, 231, 221, 0.58), transparent 30%),
    linear-gradient(135deg, #f7faf8 0%, #eef5f1 48%, #f8fafc 100%);
}

.duiwai-form {
  background: #ffffff;
  box-shadow: 0 18px 50px rgba(15, 23, 42, 0.1);
}

.duiwai-form-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 14px; }
.duiwai-form-grid .full { grid-column: 1 / -1; }
.duiwai-form select { width: 100%; border: 1px solid #cbd5e1; border-radius: 6px; padding: 10px 12px; background: #f8fafc; }
.form-tishi { margin: 0; border-radius: 6px; padding: 10px 12px; }
.form-tishi.success { border: 1px solid #bbf7d0; color: #166534; background: #f0fdf4; }
.form-tishi.error { border: 1px solid #fecaca; color: #991b1b; background: #fff1f2; }
.duiwai-links { display: flex; flex-wrap: wrap; gap: 10px; }
@media (max-width: 680px) { .duiwai-form-grid { grid-template-columns: 1fr; } }
</style>
