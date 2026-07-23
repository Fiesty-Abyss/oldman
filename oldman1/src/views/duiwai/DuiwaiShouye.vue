<template>
  <main class="duiwai-page">
    <section class="duiwai-hero shouye-hero" :style="heroStyle">
      <div class="hero-mask">
        <p class="xitong-tag">欧德曼养老院</p>
        <h1>让照护有记录，让家属更安心</h1>
        <p>面向老人入住、床位安排、护理执行、健康监测、用药照护、外出退住和费用管理的养老院运营系统。</p>
        <div class="duiwai-actions">
          <RouterLink to="/denglu">登录</RouterLink>
          <RouterLink to="/zhuce" class="secondary">家属注册</RouterLink>
          <RouterLink to="/ruzhu-shenqing" class="secondary">在线入住申请</RouterLink>
          <RouterLink to="/zanzhu" class="secondary">公益赞助</RouterLink>
        </div>
        <div class="hero-dots" aria-label="首页轮播图">
          <button
            v-for="(item, index) in bannerList"
            :key="item"
            type="button"
            :class="{ active: index === currentBannerIndex }"
            @click="currentBannerIndex = index"
          ></button>
        </div>
      </div>
    </section>

    <section class="duiwai-section">
      <h2>机构理念</h2>
      <p>欧德曼养老院希望建立一个让长者安心生活的地方。这里不仅接纳需要照护的老人，也为暂时无处可去、愿意接受培训的人提供工作机会。我们相信，照护不是单向的施舍，而是一种人与人之间重新建立连接的方式。</p>
      <p>让老人有所依，让照护者有所为，让无处可去的人重新拥有生活的位置。</p>
    </section>

    <section class="duiwai-section">
      <h2>机构介绍</h2>
      <p>欧德曼养老院以入住老人全周期管理为主线，关注生活照护、健康档案、用药提醒、家属沟通和费用透明。</p>
    </section>

    <section class="zhaohu-section">
      <div class="zhaohu-heading">
        <p class="xitong-tag">服务与照护</p>
        <h2>服务与照护</h2>
        <p>围绕长者生活、健康记录、家属沟通和入住办理，形成清楚有序的照护流程。</p>
      </div>
      <div class="zhaohu-grid">
        <article v-for="(item, index) in zhaohuList" :key="item.title">
          <span>{{ String(index + 1).padStart(2, '0') }}</span>
          <h3>{{ item.title }}</h3>
          <p>{{ item.text }}</p>
        </article>
      </div>
    </section>

    <section class="huanjing-placeholder huanjing-section">
      <h2>养老院环境</h2>
      <div class="huanjing-current">
        <img :src="imageUrl(currentEnvironment.src)" :alt="currentEnvironment.title" @error="useEnvironmentFallback" />
        <div>
          <strong>{{ currentEnvironment.title }}</strong>
          <span>点击下方缩略图可切换查看不同空间。</span>
        </div>
      </div>
      <div class="huanjing-thumbs">
        <button
          v-for="(item, index) in environmentList"
          :key="item.src"
          type="button"
          :class="{ active: index === currentEnvironmentIndex }"
          @click="selectEnvironment(index)"
        >
          <img :src="imageUrl(item.src)" :alt="item.title" @error="handleThumbError" />
          <span>{{ item.title }}</span>
        </button>
      </div>
    </section>
  </main>
</template>

<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue'
import { getTupianBanben, withTupianBanben } from '../../utils/tupianBanben'

const bannerList = [
  '/uploads/home/banner-1.jpg',
  '/uploads/home/banner-2.jpg',
  '/uploads/home/banner-3.jpg'
]

const environmentFallback = '/uploads/default/room-default.jpg'
const environmentList = [
  { title: '居住房间', src: '/uploads/environment/room-1.jpg' },
  { title: '双人护理间', src: '/uploads/environment/room-2.jpg' },
  { title: '护理站', src: '/uploads/environment/nurse-station.jpg' },
  { title: '活动空间', src: '/uploads/environment/activity-room.jpg' },
  { title: '餐厅环境', src: '/uploads/environment/dining-room.jpg' },
  { title: '公共走廊', src: '/uploads/environment/corridor.jpg' }
]

const zhaohuList = [
  { title: '生活照护', text: '协助长者完成日常起居、饮食照看、房间整理和安全巡视，让每一天的生活都有稳定照应。' },
  { title: '健康记录', text: '持续记录长者健康监测、用药执行和护理情况，便于护理员跟进，也方便家属及时了解。' },
  { title: '家属沟通', text: '家属可查看老人近况、费用账单和申请进度，减少信息不透明，让关心有处可循。' },
  { title: '入住办理', text: '支持线上提交入住意向，管理员审核后再办理正式入住、床位安排和护理设置。' }
]

const currentBannerIndex = ref(0)
const currentEnvironmentIndex = ref(0)
const tupianBanben = ref(getTupianBanben())
let bannerTimer = null

const heroStyle = computed(() => ({
  backgroundImage: `linear-gradient(90deg, rgba(15, 61, 58, 0.88), rgba(15, 61, 58, 0.42)), url("${imageUrl(bannerList[currentBannerIndex.value])}"), linear-gradient(135deg, #0f3d3a, #2f7d69)`
}))

const currentEnvironment = computed(() => environmentList[currentEnvironmentIndex.value] || environmentList[0])

function imageUrl(url) {
  return withTupianBanben(url, tupianBanben.value)
}

function selectEnvironment(index) {
  currentEnvironmentIndex.value = index
}

function useEnvironmentFallback(event) {
  if (event.target.src.includes(environmentFallback)) return
  event.target.src = imageUrl(environmentFallback)
}

function handleThumbError(event) {
  if (event.target.src.includes(environmentFallback)) return
  event.target.src = imageUrl(environmentFallback)
}

function onImageVersionChange(event) {
  tupianBanben.value = event.detail || getTupianBanben()
}

onMounted(() => {
  window.addEventListener('oldman-image-version-change', onImageVersionChange)
  bannerTimer = window.setInterval(() => {
    currentBannerIndex.value = (currentBannerIndex.value + 1) % bannerList.length
  }, 5000)
})

onBeforeUnmount(() => {
  window.removeEventListener('oldman-image-version-change', onImageVersionChange)
  if (bannerTimer) window.clearInterval(bannerTimer)
})
</script>

<style scoped>
.shouye-hero {
  position: relative;
  background-color: #0f3d3a;
  background-size: cover;
  background-position: center;
  transition: background-image 0.45s ease;
}

.hero-mask {
  max-width: 720px;
}

.hero-dots {
  display: flex;
  gap: 8px;
  margin-top: 24px;
}

.hero-dots button {
  width: 28px;
  height: 4px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.45);
}

.hero-dots button.active {
  background: #ffffff;
}

.zhaohu-section {
  display: grid;
  gap: 22px;
  padding: 42px min(7vw, 88px);
  background: #f7f3ec;
}

.zhaohu-heading {
  max-width: 760px;
}

.zhaohu-heading h2 {
  margin: 0 0 10px;
  color: #0f3d3a;
}

.zhaohu-heading p:last-child {
  margin: 0;
  color: #64748b;
  line-height: 1.8;
}

.zhaohu-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 14px;
}

.zhaohu-grid article {
  display: grid;
  align-content: start;
  gap: 12px;
  min-height: 210px;
  border: 1px solid rgba(15, 61, 58, 0.1);
  border-radius: 8px;
  padding: 22px;
  background: rgba(255, 255, 255, 0.72);
}

.zhaohu-grid span {
  width: 38px;
  height: 38px;
  display: grid;
  place-items: center;
  border-radius: 50%;
  color: #0f766e;
  background: #e4f2ec;
  font-size: 13px;
  font-weight: 800;
}

.zhaohu-grid h3 {
  margin: 0;
  color: #0f3d3a;
}

.zhaohu-grid p {
  margin: 0;
  color: #64748b;
  line-height: 1.75;
}

.huanjing-section {
  display: grid;
  gap: 18px;
}

.huanjing-current {
  display: grid;
  grid-template-columns: minmax(0, 2fr) minmax(240px, 0.8fr);
  gap: 18px;
  align-items: stretch;
}

.huanjing-current img {
  width: 100%;
  aspect-ratio: 16 / 9;
  display: block;
  border-radius: 8px;
  object-fit: cover;
  background: #eef5f1;
}

.huanjing-current > div {
  display: grid;
  align-content: center;
  gap: 10px;
  border: 1px solid #dce5df;
  border-radius: 8px;
  padding: 22px;
  background: #ffffff;
}

.huanjing-current strong {
  color: #0f3d3a;
  font-size: 24px;
}

.huanjing-current span {
  color: #64748b;
  line-height: 1.7;
}

.huanjing-thumbs {
  display: grid;
  grid-template-columns: repeat(6, minmax(0, 1fr));
  gap: 12px;
}

.huanjing-thumbs button {
  display: grid;
  gap: 8px;
  border: 1px solid #dce5df;
  border-radius: 8px;
  padding: 8px;
  color: #334155;
  background: #ffffff;
  text-align: left;
}

.huanjing-thumbs button.active {
  border-color: #0f766e;
  box-shadow: 0 0 0 2px rgba(15, 118, 110, 0.12);
}

.huanjing-thumbs img {
  width: 100%;
  aspect-ratio: 4 / 3;
  display: block;
  border-radius: 6px;
  object-fit: cover;
  background: #eef5f1;
}

.huanjing-thumbs span {
  font-size: 13px;
  font-weight: 700;
}

@media (max-width: 920px) {
  .zhaohu-grid,
  .huanjing-current,
  .huanjing-thumbs {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 560px) {
  .zhaohu-grid,
  .huanjing-current,
  .huanjing-thumbs {
    grid-template-columns: 1fr;
  }
}
</style>
