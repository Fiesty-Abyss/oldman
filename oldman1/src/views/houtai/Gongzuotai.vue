<template>
  <section class="houtai-home">
    <section class="tongji-grid">
      <article v-for="item in tongjiCards" :key="item.key" class="tongji-card">
        <span>{{ item.label }}</span>
        <strong>{{ tongji[item.key] ?? '-' }}</strong>
      </article>
    </section>

    <section class="gongzuoqu">
      <h2>{{ gongzuotaiBiaoti }}</h2>
      <div class="renwu-grid">
        <button v-for="item in quickLinks" :key="item.path" type="button" class="renwu-card link-card" @click="router.push(item.path)">
          <h3>{{ item.label }}</h3>
          <p>{{ item.desc }}</p>
        </button>
      </div>
      <div class="home-info-grid">
        <article class="home-info">
          <h3>{{ primaryTitle }}</h3>
          <ul>
            <li v-for="item in primaryTips" :key="item">{{ item }}</li>
          </ul>
        </article>
        <article class="home-info">
          <h3>{{ secondaryTitle }}</h3>
          <ul>
            <li v-for="item in secondaryTips" :key="item">{{ item }}</li>
          </ul>
        </article>
      </div>
    </section>
  </section>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getHuliyuanTongji, getJiashuTongji, getKanbanTongji } from '../../api/kanban'
import { getDangqianYonghu } from '../../stores/dengluStore'

const router = useRouter()
const yonghu = ref(getDangqianYonghu())
const tongji = reactive({})

const tongjiCardMap = {
  admin: [
    { key: 'laorenZongshu', label: '老人总数' },
    { key: 'zaizhuLaoren', label: '在住老人' },
    { key: 'kongxianChuangwei', label: '空闲床位' },
    { key: 'huliRenyuan', label: '在职护理员' },
    { key: 'daishenpiWaichu', label: '外出待审批' },
    { key: 'daishenpiTuizhu', label: '退住待审批' }
  ],
  huli: [
    { key: 'wodeLaoren', label: '我的服务老人' },
    { key: 'jinriHuliJilu', label: '今日护理记录' },
    { key: 'jinriJiankangJiance', label: '今日健康监测' },
    { key: 'jinriFuyao', label: '今日用药执行' },
    { key: 'daishenpiWaichu', label: '我提交外出待审批' },
    { key: 'daishenpiTuizhu', label: '我提交退住待审批' }
  ],
  jiashu: [
    { key: 'bangdingLaoren', label: '绑定老人' },
    { key: 'zaizhuLaoren', label: '当前在住老人' },
    { key: 'zuijinHuliJilu', label: '最近护理记录' },
    { key: 'zuijinJiankangJiance', label: '最近健康监测' },
    { key: 'daishenpiWaichu', label: '外出待审批' },
    { key: 'daishenpiTuizhu', label: '退住待审批' }
  ]
}

const jueseBianma = computed(() => yonghu.value?.jueseBianmaList?.[0] || 'jiashu')
const jueseMingcheng = computed(() => yonghu.value?.jueseMingchengList?.[0] || '老人家属')
const tongjiCards = computed(() => tongjiCardMap[jueseBianma.value] || tongjiCardMap.jiashu)
const gongzuotaiBiaoti = computed(() => {
  if (jueseBianma.value === 'admin') return '系统管理员工作台'
  if (jueseBianma.value === 'huli') return '护理员工作台'
  return '老人家属工作台'
})
const quickLinkMap = {
  admin: [
    { label: '老人入住登记', path: '/admin/ruzhu-dengji', desc: '办理正式入住并分配空闲床位' },
    { label: '床位分配管理', path: '/admin/chuangwei-fenpei', desc: '查看房间、床位和使用状态' },
    { label: '护理员管理', path: '/admin/huli-renyuan', desc: '维护护理员并分配服务老人' },
    { label: '外出与退住审批', path: '/admin/shenpi', desc: '处理家属和护理员提交的申请' },
    { label: '费用账单管理', path: '/admin/feiyong', desc: '维护账单并登记线下缴费' },
    { label: '公益赞助管理', path: '/admin/zanzhu', desc: '维护赞助人、记录、物资和用途' }
  ],
  huli: [
    { label: '我的服务老人', path: '/huliyuan/wode-laoren', desc: '查看本人负责的老人资料' },
    { label: '日常护理记录', path: '/huliyuan/huli-jilu', desc: '记录护理项目和老人反馈' },
    { label: '健康监测', path: '/huliyuan/jiankang-jiance', desc: '登记体温、血压、心率等数据' },
    { label: '用药执行', path: '/huliyuan/fuyao-jilu', desc: '核对计划并完成服药登记' },
    { label: '代办申请', path: '/huliyuan/daiban-shenqing', desc: '为负责老人提交外出或退住申请' }
  ],
  jiashu: [
    { label: '老人基本信息', path: '/jiashu/laoren-xinxi', desc: '查看绑定老人的基本资料' },
    { label: '护理记录', path: '/jiashu/huli-jilu', desc: '查看床位和日常护理记录' },
    { label: '健康与用药', path: '/jiashu/jiankang-yongyao', desc: '了解健康监测和用药执行情况' },
    { label: '费用账单', path: '/jiashu/feiyong', desc: '查看账单、缴费记录和凭证' },
    { label: '外出退住申请', path: '/jiashu/shenqing', desc: '提交并跟踪外出或退住申请' }
  ]
}
const tipMap = {
  admin: {
    primaryTitle: '常用管理入口',
    secondaryTitle: '今日工作提示',
    primaryTips: ['入住、床位、护理员、审批、费用和赞助数据需要保持关联完整', '进入对应模块可直接处理当前业务数据'],
    secondaryTips: ['及时处理待审核入住申请', '检查空闲和维修床位状态', '核对待缴费账单', '查看护理与健康记录']
  },
  huli: {
    primaryTitle: '今日工作清单',
    secondaryTitle: '护理提醒',
    primaryTips: ['查看本人服务老人', '完成日常护理记录', '完成健康监测', '核对用药计划', '处理需要代办的外出或退住申请'],
    secondaryTips: ['护理记录应及时填写', '异常健康数据及时上报', '用药执行需要核对老人和药品', '不得查看非本人负责老人资料']
  },
  jiashu: {
    primaryTitle: '家属服务入口',
    secondaryTitle: '温馨提示',
    primaryTips: ['查看老人基本信息', '查看护理记录', '查看健康与用药', '查看费用账单', '提交外出或退住申请'],
    secondaryTips: ['请及时关注老人健康监测记录', '费用账单以系统登记为准', '外出和退住申请需要管理员审批', '如联系方式变化，请及时更新资料']
  }
}
const quickLinks = computed(() => quickLinkMap[jueseBianma.value] || quickLinkMap.jiashu)
const currentTips = computed(() => tipMap[jueseBianma.value] || tipMap.jiashu)
const primaryTitle = computed(() => currentTips.value.primaryTitle)
const secondaryTitle = computed(() => currentTips.value.secondaryTitle)
const primaryTips = computed(() => currentTips.value.primaryTips)
const secondaryTips = computed(() => currentTips.value.secondaryTips)

async function loadTongji() {
  const res = jueseBianma.value === 'admin'
    ? await getKanbanTongji()
    : jueseBianma.value === 'huli'
      ? await getHuliyuanTongji()
      : await getJiashuTongji()
  Object.assign(tongji, res.data)
}

onMounted(loadTongji)
</script>

<style scoped>
.link-card {
  text-align: left;
}
.home-info-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}
.home-info {
  border: 1px solid #dce5df;
  border-radius: 8px;
  padding: 18px;
  background: #ffffff;
}
.home-info h3 {
  margin: 0 0 12px;
}
.home-info ul {
  display: grid;
  gap: 8px;
  margin: 0;
  padding-left: 20px;
  color: #64748b;
  line-height: 1.7;
}
@media (max-width: 760px) {
  .home-info-grid {
    grid-template-columns: 1fr;
  }
}
</style>
