import { createRouter, createWebHistory } from 'vue-router'
import DuiwaiShouye from '../views/duiwai/DuiwaiShouye.vue'
import Zhuce from '../views/duiwai/Zhuce.vue'
import RuzhuShenqing from '../views/duiwai/RuzhuShenqing.vue'
import Zanzhu from '../views/duiwai/Zanzhu.vue'
import Denglu from '../views/denglu/Denglu.vue'
import XitongShouye from '../views/shouye/XitongShouye.vue'
import Gongzuotai from '../views/houtai/Gongzuotai.vue'
import AdminRuzhuDengji from '../views/admin/RuzhuDengji.vue'
import AdminChuangweiFenpei from '../views/admin/ChuangweiFenpei.vue'
import AdminHuliXiangmu from '../views/admin/HuliXiangmu.vue'
import AdminHuliRenyuan from '../views/admin/HuliRenyuan.vue'
import AdminShenpi from '../views/admin/Shenpi.vue'
import AdminJiankangYongyao from '../views/admin/JiankangYongyao.vue'
import AdminFeiyong from '../views/admin/Feiyong.vue'
import AdminXitongGuanli from '../views/admin/XitongGuanli.vue'
import AdminRuzhuShenqingShenhe from '../views/admin/RuzhuShenqingShenhe.vue'
import AdminLaorenXiugaiShenqing from '../views/admin/LaorenXiugaiShenqing.vue'
import AdminZanzhuGuanli from '../views/admin/ZanzhuGuanli.vue'
import AdminTupianGuanli from '../views/admin/TupianGuanli.vue'
import HuliyuanWodeLaoren from '../views/huliyuan/WodeLaoren.vue'
import HuliyuanHuliJilu from '../views/huliyuan/HuliJilu.vue'
import HuliyuanJiankangJiance from '../views/huliyuan/JiankangJiance.vue'
import HuliyuanFuyaoJilu from '../views/huliyuan/FuyaoJilu.vue'
import HuliyuanDaibanShenqing from '../views/huliyuan/DaibanShenqing.vue'
import JiashuLaorenXinxi from '../views/jiashu/LaorenXinxi.vue'
import JiashuHuliJilu from '../views/jiashu/HuliJilu.vue'
import JiashuJiankangYongyao from '../views/jiashu/JiankangYongyao.vue'
import JiashuFeiyong from '../views/jiashu/Feiyong.vue'
import JiashuShenqing from '../views/jiashu/Shenqing.vue'
import { getDangqianYonghu, yiDenglu } from '../stores/dengluStore'

const routes = [
  { path: '/', name: 'duiwai-shouye', component: DuiwaiShouye, meta: { public: true } },
  { path: '/denglu', name: 'denglu', component: Denglu, meta: { public: true } },
  { path: '/zhuce', name: 'zhuce', component: Zhuce, meta: { public: true } },
  { path: '/ruzhu-shenqing', name: 'ruzhu-shenqing', component: RuzhuShenqing, meta: { public: true } },
  { path: '/zanzhu', name: 'zanzhu', component: Zanzhu, meta: { public: true } },
  {
    path: '/admin',
    component: XitongShouye,
    meta: { juese: 'admin' },
    children: [
      { path: '', name: 'admin', component: Gongzuotai },
      { path: 'ruzhu-dengji', name: 'admin-ruzhu-dengji', component: AdminRuzhuDengji },
      { path: 'chuangwei-fenpei', name: 'admin-chuangwei-fenpei', component: AdminChuangweiFenpei },
      { path: 'huli-xiangmu', name: 'admin-huli-xiangmu', component: AdminHuliXiangmu },
      { path: 'huli-renyuan', name: 'admin-huli-renyuan', component: AdminHuliRenyuan },
      { path: 'shenpi', name: 'admin-shenpi', component: AdminShenpi },
      { path: 'jiankang-yongyao', name: 'admin-jiankang-yongyao', component: AdminJiankangYongyao },
      { path: 'feiyong', name: 'admin-feiyong', component: AdminFeiyong },
      { path: 'zanzhu', name: 'admin-zanzhu', component: AdminZanzhuGuanli },
      { path: 'tupian', name: 'admin-tupian', component: AdminTupianGuanli },
      { path: 'xitong', name: 'admin-xitong', component: AdminXitongGuanli },
      { path: 'ruzhu-shenqing', name: 'admin-ruzhu-shenqing', component: AdminRuzhuShenqingShenhe },
      { path: 'laoren-xiugai-shenqing', name: 'admin-laoren-xiugai-shenqing', component: AdminLaorenXiugaiShenqing }
    ]
  },
  {
    path: '/huliyuan',
    component: XitongShouye,
    meta: { juese: 'huli' },
    children: [
      { path: '', name: 'huliyuan', component: Gongzuotai },
      { path: 'wode-laoren', name: 'huliyuan-wode-laoren', component: HuliyuanWodeLaoren },
      { path: 'huli-jilu', name: 'huliyuan-huli-jilu', component: HuliyuanHuliJilu },
      { path: 'jiankang-jiance', name: 'huliyuan-jiankang-jiance', component: HuliyuanJiankangJiance },
      { path: 'fuyao-jilu', name: 'huliyuan-fuyao-jilu', component: HuliyuanFuyaoJilu },
      { path: 'daiban-shenqing', name: 'huliyuan-daiban-shenqing', component: HuliyuanDaibanShenqing }
    ]
  },
  {
    path: '/jiashu',
    component: XitongShouye,
    meta: { juese: 'jiashu' },
    children: [
      { path: '', name: 'jiashu', component: Gongzuotai },
      { path: 'laoren-xinxi', name: 'jiashu-laoren-xinxi', component: JiashuLaorenXinxi },
      { path: 'huli-jilu', name: 'jiashu-huli-jilu', component: JiashuHuliJilu },
      { path: 'jiankang-yongyao', name: 'jiashu-jiankang-yongyao', component: JiashuJiankangYongyao },
      { path: 'feiyong', name: 'jiashu-feiyong', component: JiashuFeiyong },
      { path: 'shenqing', name: 'jiashu-shenqing', component: JiashuShenqing }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

function jueseShouye(jueseBianma) {
  if (jueseBianma === 'admin') return '/admin'
  if (jueseBianma === 'huli') return '/huliyuan'
  return '/jiashu'
}

router.beforeEach((to) => {
  const yonghu = getDangqianYonghu()
  const jueseBianma = yonghu?.jueseBianmaList?.[0]

  if (to.path === '/denglu' && yiDenglu()) {
    return jueseShouye(jueseBianma)
  }

  if (to.meta.public) {
    return true
  }

  if (!yiDenglu()) {
    return '/denglu'
  }

  const xuyaoJuese = to.matched.find((item) => item.meta?.juese)?.meta?.juese
  if (xuyaoJuese && xuyaoJuese !== jueseBianma) {
    return jueseShouye(jueseBianma)
  }

  return true
})

export default router
