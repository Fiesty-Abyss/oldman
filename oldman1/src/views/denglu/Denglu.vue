<template>
  <main class="denglu-page" :style="pageStyle">
    <RouterLink class="fanhui-shouye" to="/">返回首页</RouterLink>
    <section class="denglu-panel">
      <div class="denglu-brand">
        <p class="xitong-tag">欧德曼养老院管理系统</p>
        <h1>登录</h1>
        <p>管理员、护理员、家属使用统一入口进入系统。</p>
      </div>

      <form class="denglu-form" @submit.prevent="submitDenglu">
        <label>
          <span>登录账号</span>
          <input v-model.trim="form.dengluZhanghao" autocomplete="username" placeholder="admin" />
        </label>

        <label>
          <span>登录密码</span>
          <input v-model="form.mima" type="password" autocomplete="current-password" placeholder="admin123" />
        </label>

        <HuakuaiYanzheng v-model="yanzhengTongguo" :reset-key="yanzhengResetKey" />

        <p v-if="cuowu" class="form-cuowu">{{ cuowu }}</p>

        <button type="submit" :disabled="loading || !yanzhengTongguo">
          {{ loading ? '登录中...' : '登录系统' }}
        </button>
      </form>

      <div class="ceshi-zhanghao">
        <span>账号</span>
        <button type="button" @click="fillDemo('admin', 'admin123')">管理员</button>
        <button type="button" @click="fillDemo('huli001', '123456')">护理员</button>
        <button type="button" @click="fillDemo('jiashu001', '123456')">家属</button>
      </div>
    </section>
  </main>
</template>

<script setup>
import { computed, reactive, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { denglu } from '../../api/denglu'
import { baocunDenglu } from '../../stores/dengluStore'
import HuakuaiYanzheng from '../../components/HuakuaiYanzheng.vue'
import { withTupianBanben } from '../../utils/tupianBanben'

const router = useRouter()
const loading = ref(false)
const cuowu = ref('')
const yanzhengTongguo = ref(false)
const yanzhengResetKey = ref(0)
const form = reactive({
  dengluZhanghao: 'admin',
  mima: 'admin123'
})
const pageStyle = computed(() => ({
  backgroundImage: `linear-gradient(rgba(239, 246, 243, 0.72), rgba(239, 246, 243, 0.86)), url("${withTupianBanben('/uploads/login/login-bg.jpg')}"), linear-gradient(135deg, #e5f3ef, #f8fafc)`
}))

function fillDemo(dengluZhanghao, mima) {
  form.dengluZhanghao = dengluZhanghao
  form.mima = mima
  cuowu.value = ''
}

function resetYanzheng() {
  yanzhengResetKey.value += 1
}

watch(() => [form.dengluZhanghao, form.mima], () => {
  if (yanzhengTongguo.value) resetYanzheng()
})

async function submitDenglu() {
  if (!yanzhengTongguo.value) {
    cuowu.value = '请先完成滑块验证'
    return
  }
  loading.value = true
  cuowu.value = ''

  try {
    const res = await denglu(form)
    baocunDenglu(res.data)
    router.push(jueseShouye(res.data.jueseBianmaList?.[0]))
  } catch (err) {
    cuowu.value = err.message
    resetYanzheng()
  } finally {
    loading.value = false
  }
}

function jueseShouye(jueseBianma) {
  if (jueseBianma === 'admin') return '/admin'
  if (jueseBianma === 'huli') return '/huliyuan'
  return '/jiashu'
}
</script>

<style scoped>
.denglu-page {
  position: relative;
  background:
    linear-gradient(rgba(239, 246, 243, 0.72), rgba(239, 246, 243, 0.86)),
    url("/uploads/login/login-bg.jpg"),
    linear-gradient(135deg, #e5f3ef, #f8fafc);
  background-size: cover;
  background-position: center;
}

.fanhui-shouye {
  position: absolute;
  top: 24px;
  left: 24px;
  display: inline-flex;
  align-items: center;
  min-height: 38px;
  border: 1px solid rgba(15, 61, 58, 0.18);
  border-radius: 6px;
  padding: 0 14px;
  color: #0f3d3a;
  background: rgba(255, 255, 255, 0.76);
  font-weight: 700;
  text-decoration: none;
  backdrop-filter: blur(6px);
}

.denglu-panel {
  background: rgba(255, 255, 255, 0.92);
  backdrop-filter: blur(6px);
}
</style>
