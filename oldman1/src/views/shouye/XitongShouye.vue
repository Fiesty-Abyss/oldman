<template>
  <main class="xitong-layout">
    <aside class="cebian">
      <div class="yuanming">
        <strong>欧德曼养老院</strong>
        <span>{{ jueseMingcheng }}后台</span>
      </div>

      <nav>
        <button
          v-for="menu in dangqianMenu"
          :key="menu.path"
          type="button"
          :class="{ active: route.path === menu.path }"
          @click="router.push(menu.path)"
        >
          <span>{{ menu.mingcheng }}</span>
        </button>
      </nav>
    </aside>

    <section class="zhuqu">
      <header class="dingbu">
        <div>
          <p class="xitong-tag">当前用户</p>
          <h1>{{ yonghu?.xingming }}，欢迎进入系统</h1>
          <p>{{ jueseMingcheng }} · {{ yonghu?.dengluZhanghao }}</p>
        </div>
        <button type="button" class="tuichu" @click="tuichu">退出登录</button>
      </header>

      <RouterView />
    </section>
  </main>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getDangqianYonghu, qingchuDenglu } from '../../stores/dengluStore'

const router = useRouter()
const route = useRoute()
const yonghu = ref(getDangqianYonghu())

const menuMap = {
  admin: [
    { mingcheng: '老人入住登记', path: '/admin/ruzhu-dengji' },
    { mingcheng: '床位分配管理', path: '/admin/chuangwei-fenpei' },
    { mingcheng: '护理项目与级别', path: '/admin/huli-xiangmu' },
    { mingcheng: '护理员管理', path: '/admin/huli-renyuan' },
    { mingcheng: '外出与退住审批', path: '/admin/shenpi' },
    { mingcheng: '入住申请审核', path: '/admin/ruzhu-shenqing' },
    { mingcheng: '老人信息变更审核', path: '/admin/laoren-xiugai-shenqing' },
    { mingcheng: '健康档案与用药', path: '/admin/jiankang-yongyao' },
    { mingcheng: '费用账单管理', path: '/admin/feiyong' },
    { mingcheng: '公益赞助管理', path: '/admin/zanzhu' },
    { mingcheng: '图片管理', path: '/admin/tupian' },
    { mingcheng: '系统管理', path: '/admin/xitong' }
  ],
  huli: [
    { mingcheng: '我的服务老人', path: '/huliyuan/wode-laoren' },
    { mingcheng: '日常护理记录', path: '/huliyuan/huli-jilu' },
    { mingcheng: '健康监测记录', path: '/huliyuan/jiankang-jiance' },
    { mingcheng: '用药执行记录', path: '/huliyuan/fuyao-jilu' },
    { mingcheng: '代办外出退住', path: '/huliyuan/daiban-shenqing' }
  ],
  jiashu: [
    { mingcheng: '老人基本信息', path: '/jiashu/laoren-xinxi' },
    { mingcheng: '床位与护理记录', path: '/jiashu/huli-jilu' },
    { mingcheng: '健康与用药记录', path: '/jiashu/jiankang-yongyao' },
    { mingcheng: '费用账单', path: '/jiashu/feiyong' },
    { mingcheng: '外出退住申请', path: '/jiashu/shenqing' }
  ]
}

const jueseBianma = computed(() => yonghu.value?.jueseBianmaList?.[0] || 'jiashu')
const jueseMingcheng = computed(() => yonghu.value?.jueseMingchengList?.[0] || '老人家属')
const dangqianMenu = computed(() => menuMap[jueseBianma.value] || menuMap.jiashu)

function tuichu() {
  qingchuDenglu()
  router.push('/denglu')
}
</script>
